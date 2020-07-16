package com.dxj.module.system.service;

import com.dxj.module.system.domain.dto.DictDetailDTO;
import com.dxj.module.system.domain.entity.DictDetail;
import com.dxj.module.system.domain.query.DictDetailQuery;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
* @author Sinkiang
* @date 2019-04-10
*/
public interface DictDetailService {

    /**
     * 创建
     * @param resources /
     */
    void create(DictDetail resources);

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

    /**
     * 根据字典名称获取字典详情
     * @param name 字典名称
     * @return /
     */
    List<DictDetailDTO> getDictByName(String name);
}
