package com.dxj.system.service;

import com.dxj.service.RedisService;
import com.dxj.system.domain.User;
import com.dxj.exception.EntityExistException;
import com.dxj.exception.EntityNotFoundException;
import com.dxj.system.repository.UserRepository;
import com.dxj.system.dto.UserDTO;
import com.dxj.system.mapper.UserMapper;
import com.dxj.system.spec.UserSpec;
import com.dxj.utils.PageUtil;
import com.dxj.utils.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * @author dxj
 * @date 2018-11-23
 */
@Service
@CacheConfig(cacheNames = "user")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final RedisService redisService;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper, RedisService redisService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.redisService = redisService;
    }

    @Cacheable(key = "#p0")
    public UserDTO findById(long id) {
        Optional<User> user = userRepository.findById(id);
        ValidationUtil.isNull(user, "User", "id", id);
        return userMapper.toDto(user.orElse(null));
    }

    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public UserDTO create(User resources) {

        if (userRepository.findByUsername(resources.getUsername()) != null) {
            throw new EntityExistException(User.class, "username", resources.getUsername());
        }

        if (userRepository.findByEmail(resources.getEmail()) != null) {
            throw new EntityExistException(User.class, "email", resources.getEmail());
        }

        // 默认密码 123456，此密码是加密后的字符
        resources.setPassword("e10adc3949ba59abbe56e057f20f883e");
        resources.setAvatar("https://aurora-1255840532.cos.ap-chengdu.myqcloud.com/8918a306ea314404835a9196585c4b75.jpeg");
        return userMapper.toDto(userRepository.save(resources));
    }

    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(User resources) {
        Optional<User> userOptional = userRepository.findById(resources.getId());
        ValidationUtil.isNull(userOptional, "User", "id", resources.getId());

        User user = userOptional.orElse(null);

        assert user != null;
        User user1 = userRepository.findByUsername(user.getUsername());
        User user2 = userRepository.findByEmail(user.getEmail());

        if (user1 != null && !user.getId().equals(user1.getId())) {
            throw new EntityExistException(User.class, "username", resources.getUsername());
        }

        if (user2 != null && !user.getId().equals(user2.getId())) {
            throw new EntityExistException(User.class, "email", resources.getEmail());
        }

        // 如果用户的角色改变了，需要手动清理下缓存
        if (!resources.getRoles().equals(user.getRoles())) {
            String key = "role::loadPermissionByUser:" + user.getUsername();
            redisService.delete(key);
            key = "role::findByUsers_Id:" + user.getId();
            redisService.delete(key);
        }

        user.setUsername(resources.getUsername());
        user.setEmail(resources.getEmail());
        user.setEnabled(resources.getEnabled());
        user.setRoles(resources.getRoles());
        user.setDept(resources.getDept());
        user.setJob(resources.getJob());
        user.setPhone(resources.getPhone());
        userRepository.save(user);
    }

    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Cacheable(key = "'loadUserByUsername:'+#p0")
    public UserDTO findByName(String userName) {
        User user;
        if(ValidationUtil.isEmail(userName)){
            user = userRepository.findByEmail(userName);
        } else {
            user = userRepository.findByUsername(userName);
        }
        if (user == null) {
            throw new EntityNotFoundException(User.class, "name", userName);
        } else {
            return userMapper.toDto(user);
        }
    }

    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void updatePass(String username, String pass) {
        userRepository.updatePass(username, pass, new Date());
    }

    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void updateAvatar(String username, String url) {
        userRepository.updateAvatar(username, url);
    }

    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void updateEmail(String username, String email) {
        userRepository.updateEmail(username, email);
    }

    /**
     * 分页
     */
    @Cacheable(keyGenerator = "keyGenerator")
    public Map<String, Object> queryAll(UserDTO user, Set<Long> deptIds, Pageable pageable) {
        Page<User> page = userRepository.findAll(UserSpec.getSpec(user, deptIds), pageable);
        return PageUtil.toPage(page.map(userMapper::toDto));
    }
}
