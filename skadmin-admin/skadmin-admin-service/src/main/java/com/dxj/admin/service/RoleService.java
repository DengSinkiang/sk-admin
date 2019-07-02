package com.dxj.admin.service;

import com.dxj.admin.domain.Menu;
import com.dxj.admin.domain.Role;
import com.dxj.admin.dto.RoleDTO;
import com.dxj.admin.dto.RoleSmallDTO;
import com.dxj.admin.mapper.RoleMapper;
import com.dxj.admin.mapper.RoleSmallMapper;
import com.dxj.admin.query.CommonQuery;
import com.dxj.admin.repository.RoleRepository;
import com.dxj.admin.service.spec.RoleSpec;
import com.dxj.common.exception.EntityExistException;
import com.dxj.common.util.BaseQuery;
import com.dxj.common.util.PageUtils;
import com.dxj.common.util.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author dxj
 * @date 2019-04-03
 */
@Service
@CacheConfig(cacheNames = "role")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class RoleService {

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;

    private final RoleSmallMapper roleSmallMapper;

    @Autowired
    public RoleService(RoleRepository roleRepository, RoleMapper roleMapper, RoleSmallMapper roleSmallMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
        this.roleSmallMapper = roleSmallMapper;
    }

    @Cacheable(key = "#p0")
    public RoleDTO findById(long id) {
        Optional<Role> role = roleRepository.findById(id);
        ValidationUtils.isNull(role, "Role", "id", id);
        return roleMapper.toDto(role.orElse(null));
    }

    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public RoleDTO create(Role resources) {
        if (roleRepository.findByName(resources.getName()) != null) {
            throw new EntityExistException(Role.class, "username", resources.getName());
        }
        return roleMapper.toDto(roleRepository.save(resources));
    }

    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(Role resources) {

        Optional<Role> optionalRole = roleRepository.findById(resources.getId());
        ValidationUtils.isNull(optionalRole, "Role", "id", resources.getId());

        Role role = optionalRole.orElse(null);

        Role role1 = roleRepository.findByName(resources.getName());

        assert role != null;
        if (role1 != null && !role1.getId().equals(role.getId())) {
            throw new EntityExistException(Role.class, "username", resources.getName());
        }

        role.setName(resources.getName());
        role.setRemark(resources.getRemark());
        role.setDataScope(resources.getDataScope());
        role.setDepts(resources.getDepts());
        role.setLevel(resources.getLevel());
        roleRepository.save(role);
    }

    @CacheEvict(allEntries = true)
    public void updatePermission(Role resources, RoleDTO roleDTO) {
        Role role = roleMapper.toEntity(roleDTO);
        role.setPermissions(resources.getPermissions());
        roleRepository.save(role);
    }

    @CacheEvict(allEntries = true)
    public void updateMenu(Role resources, RoleDTO roleDTO) {
        Role role = roleMapper.toEntity(roleDTO);
        role.setMenus(resources.getMenus());
        roleRepository.save(role);
    }

    @CacheEvict(allEntries = true)
    public void untiedMenu(Menu menu) {
        Set<Role> roles = roleRepository.findByMenus_Id(menu.getId());
        for (Role role : roles) {
            menu.getRoles().remove(role);
            role.getMenus().remove(menu);
            roleRepository.save(role);
        }
    }

    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        roleRepository.deleteById(id);
    }

    @Cacheable(key = "'findByUsers_Id:' + #p0")
    public List<RoleSmallDTO> findByUsers_Id(Long id) {
        return roleSmallMapper.toDto(new ArrayList<>(roleRepository.findByUsers_Id(id)));
    }
    @Cacheable(keyGenerator = "keyGenerator")
    public Integer findByRoles(Set<Role> roles) {
        Set<RoleDTO> roleDTOS = new HashSet<>();
        for (Role role : roles) {
            roleDTOS.add(findById(role.getId()));
        }
        return Collections.min(roleDTOS.stream().map(RoleDTO::getLevel).collect(Collectors.toList()));
    }

    /**
     * 分页
     */
    @Cacheable(keyGenerator = "keyGenerator")
    public Map<String, Object> queryAll(CommonQuery query, Pageable pageable) {
        Page<Role> page = roleRepository.findAll((root, criteriaQuery, criteriaBuilder) -> BaseQuery.getPredicate(root, query, criteriaBuilder), pageable);
        return PageUtils.toPage(page.map(roleMapper::toDto));
    }

    /**
     * 不分页
     */
    @Cacheable(keyGenerator = "keyGenerator")
    public List<RoleDTO> queryAll(Pageable pageable){
        List<Role> roles = roleRepository.findAll(pageable).getContent();
        return roleMapper.toDto(roles);
    }
}
