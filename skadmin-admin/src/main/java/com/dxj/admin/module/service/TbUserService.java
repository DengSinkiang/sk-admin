package com.dxj.admin.module.service;

import com.dxj.admin.module.domain.TbUser;
import com.dxj.admin.module.dto.TbUserDTO;
import com.dxj.admin.module.dto.TbUserQueryCriteria;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.dxj.common.util.ValidationUtils;
import com.dxj.admin.module.repository.TbUserRepository;
import com.dxj.admin.module.mapper.TbUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.dxj.common.util.PageUtils;
import com.dxj.common.util.BaseQuery;

/**
* @author sinkiang
* @date 2019-08-28
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
@CacheConfig(cacheNames = "tbUser")
public class TbUserService {

    @Autowired
    private TbUserRepository tbUserRepository;

    @Autowired
    private TbUserMapper tbUserMapper;

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    @Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(TbUserQueryCriteria criteria, Pageable pageable) {
        Page<TbUser> page = tbUserRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(tbUserMapper::toDto));
    }

    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    @Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(TbUserQueryCriteria criteria) {
        return tbUserMapper.toDto(tbUserRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));

    }

    /**
     * findById
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    TbUserDTO findById(Integer id) {
        Optional<TbUser> tbUser = tbUserRepository.findById(id);
        ValidationUtil.isNull(tbUser,"TbUser","id",id);
        return tbUserMapper.toDto(tbUser.get());
    }

    /**
     * create
     * @param resources
     * @return
     */
    @CacheEvict(allEntries = true)
    TbUserDTO create(TbUser resources) {
        return tbUserMapper.toDto(tbUserRepository.save(resources));
    }

    /**
     * update
     * @param resources
     */
    @CacheEvict(allEntries = true)
    void update(TbUser resources) {
        Optional<TbUser> optionalTbUser = tbUserRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalTbUser,"TbUser","id",resources.getId());

TbUser tbUser = optionalTbUser.get();
        // 此处需自己修改
        resources.setId(tbUser.getId());
        tbUserRepository.save(resources);
    }

    /**
     * delete
     * @param id
     */
    @CacheEvict(allEntries = true)
    void delete(Integer id) {
        tbUserRepository.deleteById(id);
    }
}
