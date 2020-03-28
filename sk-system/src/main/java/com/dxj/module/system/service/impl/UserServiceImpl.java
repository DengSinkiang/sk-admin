package com.dxj.module.system.service.impl;

import com.dxj.exception.EntityExistException;
import com.dxj.exception.EntityNotFoundException;
import com.dxj.module.system.dao.UserAvatarDao;
import com.dxj.module.system.dao.UserDao;
import com.dxj.module.system.domain.entity.User;
import com.dxj.module.system.domain.entity.UserAvatar;
import com.dxj.module.system.service.UserService;
import com.dxj.module.system.domain.dto.RoleSmallDTO;
import com.dxj.module.system.domain.dto.UserDTO;
import com.dxj.module.system.domain.query.UserQuery;
import com.dxj.module.system.domain.mapper.UserMapper;
import com.dxj.util.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Sinkiang
 * @date 2018-11-23
 */
@Service
@CacheConfig(cacheNames = "user")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final UserMapper userMapper;
    private final RedisUtils redisUtils;
    private final UserAvatarDao userAvatarRepository;

    @Value("${file.avatar}")
    private String avatar;

    public UserServiceImpl(UserDao userDao, UserMapper userMapper, RedisUtils redisUtils, UserAvatarDao userAvatarRepository) {
        this.userDao = userDao;
        this.userMapper = userMapper;
        this.redisUtils = redisUtils;
        this.userAvatarRepository = userAvatarRepository;
    }

    @Override
    @Cacheable
    public Object queryAll(UserQuery criteria, Pageable pageable) {
        Page<User> page = userDao.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
        return PageUtil.toPage(page.map(userMapper::toDto));
    }

    @Override
    @Cacheable
    public List<UserDTO> queryAll(UserQuery criteria) {
        List<User> users = userDao.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder));
        return userMapper.toDto(users);
    }

    @Override
    @Cacheable(key = "#p0")
    public UserDTO findById(long id) {
        User user = userDao.findById(id).orElseGet(User::new);
        ValidationUtil.isNull(user.getId(), "User", "id", id);
        return userMapper.toDto(user);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public UserDTO create(User resources) {
        if (userDao.findByUsername(resources.getUsername()) != null) {
            throw new EntityExistException(User.class, "username", resources.getUsername());
        }
        if (userDao.findByEmail(resources.getEmail()) != null) {
            throw new EntityExistException(User.class, "email", resources.getEmail());
        }
        return userMapper.toDto(userDao.save(resources));
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(User resources) {
        User user = userDao.findById(resources.getId()).orElseGet(User::new);
        ValidationUtil.isNull(user.getId(), "User", "id", resources.getId());
        User user1 = userDao.findByUsername(user.getUsername());
        User user2 = userDao.findByEmail(user.getEmail());

        if (user1 != null && !user.getId().equals(user1.getId())) {
            throw new EntityExistException(User.class, "username", resources.getUsername());
        }

        if (user2 != null && !user.getId().equals(user2.getId())) {
            throw new EntityExistException(User.class, "email", resources.getEmail());
        }

        // 如果用户的角色改变了，需要手动清理下缓存
        if (!resources.getRoles().equals(user.getRoles())) {
            String key = "role::loadPermissionByUser:" + user.getUsername();
            redisUtils.del(key);
            key = "role::findByUsers_Id:" + user.getId();
            redisUtils.del(key);
        }

        user.setUsername(resources.getUsername());
        user.setEmail(resources.getEmail());
        user.setEnabled(resources.getEnabled());
        user.setRoles(resources.getRoles());
        user.setDept(resources.getDept());
        user.setJob(resources.getJob());
        user.setPhone(resources.getPhone());
        user.setNickName(resources.getNickName());
        user.setSex(resources.getSex());
        userDao.save(user);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void updateCenter(User resources) {
        User user = userDao.findById(resources.getId()).orElseGet(User::new);
        user.setNickName(resources.getNickName());
        user.setPhone(resources.getPhone());
        user.setSex(resources.getSex());
        userDao.save(user);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> ids) {
        for (Long id : ids) {
            userDao.deleteById(id);
        }
    }

    @Override
    @Cacheable(key = "'loadUserByUsername:'+#p0")
    public UserDTO findByName(String userName) {
        User user;
        if (ValidationUtil.isEmail(userName)) {
            user = userDao.findByEmail(userName);
        } else {
            user = userDao.findByUsername(userName);
        }
        if (user == null) {
            throw new EntityNotFoundException(User.class, "name", userName);
        } else {
            return userMapper.toDto(user);
        }
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void updatePass(String username, String pass) {
        userDao.updatePass(username, pass, new Date());
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void updateAvatar(MultipartFile multipartFile) {
        User user = userDao.findByUsername(SecurityUtils.getUsername());
        UserAvatar userAvatar = user.getUserAvatar();
        String oldPath = "";
        if (userAvatar != null) {
            oldPath = userAvatar.getPath();
        }
        File file = FileUtils.upload(multipartFile, avatar);
        assert file != null;
        userAvatar = userAvatarRepository.save(new UserAvatar(userAvatar, file.getName(), file.getPath(), FileUtils.getSize(multipartFile.getSize())));
        user.setUserAvatar(userAvatar);
        userDao.save(user);
        if (StringUtils.isNotBlank(oldPath)) {
            FileUtils.del(oldPath);
        }
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void updateEmail(String username, String email) {
        userDao.updateEmail(username, email);
    }

    @Override
    public void download(List<UserDTO> queryAll, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (UserDTO userDTO : queryAll) {
            List<String> roles = userDTO.getRoles().stream().map(RoleSmallDTO::getName).collect(Collectors.toList());
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("用户名", userDTO.getUsername());
            map.put("头像", userDTO.getAvatar());
            map.put("邮箱", userDTO.getEmail());
            map.put("状态", userDTO.getEnabled() ? "启用" : "禁用");
            map.put("手机号码", userDTO.getPhone());
            map.put("角色", roles);
            map.put("部门", userDTO.getDept().getName());
            map.put("岗位", userDTO.getJob().getName());
            map.put("最后修改密码的时间", userDTO.getLastPasswordResetTime());
            map.put("创建日期", userDTO.getCreateTime());
            list.add(map);
        }
        FileUtils.downloadExcel(list, response);
    }
}
