package com.dxj.module.system.service;

import com.dxj.module.system.domain.dto.DictDTO;
import com.dxj.module.system.domain.entity.Dict;
import com.dxj.module.system.domain.query.DictQuery;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
* @author Sinkiang
* @date 2019-04-10
*/
public interface DictService {

    /**
     * 分页查询
     * @param criteria 条件
     * @param pageable 分页参数
     * @return /
     */
    Map<String,Object> queryAll(DictQuery criteria, Pageable pageable);

    /**
     * 查询全部数据
     * @param dict /
     * @return /
     */
    List<DictDTO> queryAll(DictQuery dict);

    /**
     * 创建
     * @param resources /
     * @return /
     */
    void create(Dict resources);

    /**
     * 编辑
     * @param resources /
     */
    void update(Dict resources);

    /**
     * 删除
     * @param ids /
     */
    void delete(Set<Long> ids);

    /**
     * 导出数据
     * @param queryAll 待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<DictDTO> queryAll, HttpServletResponse response) throws IOException;
}
