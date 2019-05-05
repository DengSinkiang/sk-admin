package com.dxj.modules.system.service;

import com.dxj.modules.system.domain.Permission;
import com.dxj.exception.BadRequestException;
import com.dxj.exception.EntityExistException;
import com.dxj.modules.system.repository.PermissionRepository;
import com.dxj.modules.system.dto.PermissionDTO;
import com.dxj.modules.system.mapper.PermissionMapper;
import com.dxj.utils.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

/**
 * @author dxj
 * @date 2018-12-03
 */
@Service
@CacheConfig(cacheNames = "permission")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class PermissionService {

    private final PermissionRepository permissionRepository;

    private final PermissionMapper permissionMapper;

    @Autowired
    public PermissionService(PermissionRepository permissionRepository, PermissionMapper permissionMapper) {
        this.permissionRepository = permissionRepository;
        this.permissionMapper = permissionMapper;
    }

    @Cacheable(key = "#p0")
    public PermissionDTO findById(long id) {
        Optional<Permission> permission = permissionRepository.findById(id);
        ValidationUtil.isNull(permission,"Permission","id",id);
        return permissionMapper.toDto(permission.orElse(null));
    }

    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public PermissionDTO create(Permission resources) {
        if(permissionRepository.findByName(resources.getName()) != null){
            throw new EntityExistException(Permission.class,"name",resources.getName());
        }
        return permissionMapper.toDto(permissionRepository.save(resources));
    }

    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(Permission resources) {
        Optional<Permission> optionalPermission = permissionRepository.findById(resources.getId());
        if(resources.getId().equals(resources.getPid())) {
            throw new BadRequestException("上级不能为自己");
        }
        ValidationUtil.isNull(optionalPermission,"Permission","id",resources.getId());

        Permission permission = optionalPermission.orElse(null);

        Permission permission1 = permissionRepository.findByName(resources.getName());

        assert permission != null;
        if(permission1 != null && !permission1.getId().equals(permission.getId())){
            throw new EntityExistException(Permission.class,"name",resources.getName());
        }

        permission.setName(resources.getName());
        permission.setAlias(resources.getAlias());
        permission.setPid(resources.getPid());
        permissionRepository.save(permission);
    }

    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        List<Permission> permissionList = permissionRepository.findByPid(id);
        for (Permission permission : permissionList) {
            permissionRepository.delete(permission);
        }
        permissionRepository.deleteById(id);
    }

    @Cacheable(key = "'tree'")
    public Object getPermissionTree(List<Permission> permissions) {
        List<Map<String,Object>> list = new LinkedList<>();
        permissions.forEach(permission -> {
                    if (permission!=null){
                        List<Permission> permissionList = permissionRepository.findByPid(permission.getId());
                        Map<String,Object> map = new HashMap<>();
                        map.put("id",permission.getId());
                        map.put("label",permission.getAlias());
                        if(permissionList!=null && permissionList.size()!=0){
                            map.put("children",getPermissionTree(permissionList));
                        }
                        list.add(map);
                    }
                }
        );
        return list;
    }

    @Cacheable(key = "'pid:'+#p0")
    public List<Permission> findByPid(long pid) {
        return permissionRepository.findByPid(pid);
    }

    @Cacheable(keyGenerator = "keyGenerator")
    public Object buildTree(List<PermissionDTO> permissionDTOs) {

        List<PermissionDTO> trees = new ArrayList<>();

        for (PermissionDTO permissionDTO : permissionDTOs) {

            if ("0".equals(permissionDTO.getPid().toString())) {
                trees.add(permissionDTO);
            }

            for (PermissionDTO it : permissionDTOs) {
                if (it.getPid().equals(permissionDTO.getId())) {
                    if (permissionDTO.getChildren() == null) {
                        permissionDTO.setChildren(new ArrayList<>());
                    }
                    permissionDTO.getChildren().add(it);
                }
            }
        }

        Integer totalElements = permissionDTOs.size();

        Map<String, Object> map = new HashMap<>();
        map.put("content", trees.size() == 0?permissionDTOs:trees);
        map.put("totalElements", totalElements);
        return map;
    }
}
