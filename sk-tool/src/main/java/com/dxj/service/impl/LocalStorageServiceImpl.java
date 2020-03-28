package com.dxj.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.dxj.dao.LocalStorageDao;
import com.dxj.domain.entity.LocalStorage;
import com.dxj.domain.dto.LocalStorageDTO;
import com.dxj.domain.dto.LocalStorageQuery;
import com.dxj.exception.SkException;
import com.dxj.service.LocalStorageService;
import com.dxj.service.mapper.LocalStorageMapper;
import com.dxj.util.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
* @author Sinkiang
* @date 2019-09-05
*/
@Service
@CacheConfig(cacheNames = "localStorage")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class LocalStorageServiceImpl implements LocalStorageService {

    private final LocalStorageDao localStorageDao;

    private final LocalStorageMapper localStorageMapper;

    @Value("${file.path}")
    private String path;

    @Value("${file.maxSize}")
    private long maxSize;

    public LocalStorageServiceImpl(LocalStorageDao localStorageDao, LocalStorageMapper localStorageMapper) {
        this.localStorageDao = localStorageDao;
        this.localStorageMapper = localStorageMapper;
    }

    @Override
    @Cacheable
    public Object queryAll(LocalStorageQuery criteria, Pageable pageable){
        Page<LocalStorage> page = localStorageDao.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(localStorageMapper::toDto));
    }

    @Override
    @Cacheable
    public List<LocalStorageDTO> queryAll(LocalStorageQuery criteria){
        return localStorageMapper.toDto(localStorageDao.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Cacheable(key = "#p0")
    public LocalStorageDTO findById(Long id){
        LocalStorage localStorage = localStorageDao.findById(id).orElseGet(LocalStorage::new);
        ValidationUtil.isNull(localStorage.getId(),"LocalStorage","id",id);
        return localStorageMapper.toDto(localStorage);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public LocalStorageDTO create(String name, MultipartFile multipartFile) {
        FileUtils.checkSize(maxSize, multipartFile.getSize());
        String suffix = FileUtils.getExtensionName(multipartFile.getOriginalFilename());
        String type = FileUtils.getFileType(suffix);
        File file = FileUtils.upload(multipartFile, path + type +  File.separator);
        if(ObjectUtil.isNull(file)){
            throw new SkException("上传失败");
        }
        try {
            name = StringUtils.isBlank(name) ? FileUtils.getFileNameNoEx(multipartFile.getOriginalFilename()) : name;
            LocalStorage localStorage = new LocalStorage(
                    file.getName(),
                    name,
                    suffix,
                    file.getPath(),
                    type,
                    FileUtils.getSize(multipartFile.getSize()),
                    SecurityUtils.getUsername()
            );
            return localStorageMapper.toDto(localStorageDao.save(localStorage));
        }catch (Exception e){
            FileUtils.del(file);
            throw e;
        }
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(LocalStorage resources) {
        LocalStorage localStorage = localStorageDao.findById(resources.getId()).orElseGet(LocalStorage::new);
        ValidationUtil.isNull( localStorage.getId(),"LocalStorage","id",resources.getId());
        localStorage.copy(resources);
        localStorageDao.save(localStorage);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            LocalStorage storage = localStorageDao.findById(id).orElseGet(LocalStorage::new);
            FileUtils.del(storage.getPath());
            localStorageDao.delete(storage);
        }
    }

    @Override
    public void download(List<LocalStorageDTO> queryAll, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (LocalStorageDTO localStorageDTO : queryAll) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("文件名", localStorageDTO.getRealName());
            map.put("备注名", localStorageDTO.getName());
            map.put("文件类型", localStorageDTO.getType());
            map.put("文件大小", localStorageDTO.getSize());
            map.put("操作人", localStorageDTO.getOperate());
            map.put("创建日期", localStorageDTO.getCreateTime());
            list.add(map);
        }
        FileUtils.downloadExcel(list, response);
    }
}
