package com.dxj.modules.system.service;

import com.dxj.exception.BadRequestException;
import com.dxj.modules.system.domain.Dept;
import com.dxj.modules.system.query.DeptSpec;
import com.dxj.utils.ValidationUtil;
import com.dxj.modules.system.repository.DeptRepository;
import com.dxj.modules.system.dto.DeptDTO;
import com.dxj.modules.system.mapper.DeptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author dxj
 * @date 2019-03-25
 */
@Service
@CacheConfig(cacheNames = "dept")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DeptService {

    private final DeptRepository deptRepository;

    private final DeptMapper deptMapper;

    @Autowired
    public DeptService(DeptRepository deptRepository, DeptMapper deptMapper) {
        this.deptRepository = deptRepository;
        this.deptMapper = deptMapper;
    }

    @Cacheable(key = "#p0")
    public DeptDTO findById(Long id) {
        Optional<Dept> dept = deptRepository.findById(id);
        ValidationUtil.isNull(dept, "Dept", "id", id);
        return deptMapper.toDto(dept.orElse(null));
    }

    @Cacheable(keyGenerator = "keyGenerator")
    public List<Dept> findByPid(long pid) {
        return deptRepository.findByPid(pid);
    }

    @Cacheable(keyGenerator = "keyGenerator")
    public Object buildTree(List<DeptDTO> deptDTOS) {
        Set<DeptDTO> trees = new LinkedHashSet<>();
        Set<DeptDTO> depts = new LinkedHashSet<>();
        boolean isChild;
        for (DeptDTO deptDTO : deptDTOS) {
            isChild = false;
            if ("0".equals(deptDTO.getPid().toString())) {
                trees.add(deptDTO);
            }
            for (DeptDTO it : deptDTOS) {
                if (it.getPid().equals(deptDTO.getId())) {
                    isChild = true;
                    if (deptDTO.getChildren() == null) {
                        deptDTO.setChildren(new ArrayList<>());
                    }
                    deptDTO.getChildren().add(it);
                }
            }
            if (isChild) {
                depts.add(deptDTO);
            }
        }

        if (CollectionUtils.isEmpty(trees)) {
            trees = depts;
        }

        Integer totalElements = deptDTOS.size();

        Map<String, Object> map = new HashMap<>();
        map.put("totalElements", totalElements);
        map.put("content", CollectionUtils.isEmpty(trees) ? deptDTOS : trees);
        return map;
    }

    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public DeptDTO create(Dept resources) {
        return deptMapper.toDto(deptRepository.save(resources));
    }

    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(Dept resources) {
        if (resources.getId().equals(resources.getPid())) {
            throw new BadRequestException("上级不能为自己");
        }
        Optional<Dept> optionalDept = deptRepository.findById(resources.getId());
        ValidationUtil.isNull(optionalDept, "Dept", "id", resources.getId());

        Dept dept = optionalDept.orElse(null);
        // 此处需自己修改
        assert dept != null;
        resources.setId(dept.getId());
        deptRepository.save(resources);
    }

    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        deptRepository.deleteById(id);
    }

    /**
     * 不分页
     */
    @Cacheable(keyGenerator = "keyGenerator")
    public List queryAll(DeptDTO dept, Set<Long> deptIds) {
        return deptMapper.toDto(deptRepository.findAll(DeptSpec.getSpec(dept, deptIds)));
    }
}
