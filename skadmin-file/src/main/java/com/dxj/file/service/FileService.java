package com.dxj.file.service;

import com.dxj.file.entity.File;
import com.dxj.file.vo.SearchVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 文件管理接口
 *
 * @author Sinkiang
 */
public interface FileService extends SbootBaseService<File, String> {

    /**
     * 多条件获取列表
     *
     * @param file
     * @param searchVo
     * @param pageable
     * @return
     */
    Page<File> findByCondition(File file, SearchVo searchVo, Pageable pageable);
}
