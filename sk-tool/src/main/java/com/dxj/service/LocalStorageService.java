package com.dxj.service;

import com.dxj.domain.entity.LocalStorage;
import com.dxj.domain.dto.LocalStorageDTO;
import com.dxj.domain.dto.LocalStorageQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
* @author Sinkiang
* @date 2019-09-05
*/
public interface LocalStorageService {

    /**
     * 分页查询
     * @param criteria 条件
     * @param pageable 分页参数
     * @return /
     */
    Object queryAll(LocalStorageQuery criteria, Pageable pageable);

    /**
     * 查询全部数据
     * @param criteria 条件
     * @return /
     */
    List<LocalStorageDTO> queryAll(LocalStorageQuery criteria);

    /**
     * 根据ID查询
     * @param id /
     * @return /
     */
    LocalStorageDTO findById(Long id);

    /**
     * 上传
     * @param name 文件名称
     * @param file 文件
     */
    void create(String name, MultipartFile file);

    /**
     * 编辑
     * @param resources 文件信息
     */
    void update(LocalStorage resources);

    /**
     * 多选删除
     * @param ids /
     */
    void deleteAll(Long[] ids);

    /**
     * 导出数据
     * @param localStorageDtos 待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<LocalStorageDTO> localStorageDtos, HttpServletResponse response) throws IOException;
}
