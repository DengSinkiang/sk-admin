package com.dxj.module.system.service;

import com.dxj.module.system.domain.entity.DictDetail;
import com.dxj.module.system.domain.dto.DictDetailDTO;
import com.dxj.module.system.domain.query.DictDetailQuery;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
* @author Sinkiang
* @date 2019-04-10
*/
public interface DictDetailService {

    /**
     * 根据ID查询
     * @param id /
     * @return /
     */
    DictDetailDTO findById(Long id);

    /**
     * 创建
     * @param resources /
     * @return /
     */
    DictDetailDTO create(DictDetail resources);

    /**
     * 编辑
     * @param resources /
     */
    void update(DictDetail resources);

    /**
     * 删除
     * @param id /
     */
    void delete(Long id);

    /**
     * 分页查询
     * @param criteria 条件
     * @param pageable 分页参数
     * @return /
     */
    Map<String,Object> queryAll(DictDetailQuery criteria, Pageable pageable);
}
