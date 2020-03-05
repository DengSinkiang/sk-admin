package com.dxj.tool.service;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.dxj.common.enums.CommEnum;
import com.dxj.common.util.BaseQuery;
import com.dxj.common.util.PageUtil;
import com.dxj.tool.domain.entity.Picture;
import lombok.extern.slf4j.Slf4j;
import com.dxj.common.exception.BadRequestException;
import com.dxj.tool.repository.PictureRepository;
import com.dxj.common.util.FileUtil;
import com.dxj.common.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author dxj
 * @date 2019-04-27
 */
@Slf4j
@Service(value = "pictureService")
@CacheConfig(cacheNames = "picture")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class PictureService {

    private final PictureRepository pictureRepository;

    private static final String SUCCESS = "success";

    private static final String CODE = "code";

    private static final String MSG = "msg";

    @Autowired
    public PictureService(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

    /**
     * 上传图片
     *
     * @param multipartFile
     * @param username
     * @return
     */
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public Picture upload(MultipartFile multipartFile, String username) {
        File file = FileUtil.toFile(multipartFile);

        HashMap<String, Object> paramMap = new HashMap<>();

        paramMap.put("smfile", file);
        String result = HttpUtil.post(CommEnum.SM_MS_URL.getEntityName(), paramMap);

        JSONObject jsonObject = JSONUtil.parseObj(result);
        Picture picture;
        if (!jsonObject.get(CODE).toString().equals(SUCCESS)) {
            throw new BadRequestException(jsonObject.get(MSG).toString());
        }
        //转成实体类
        picture = JSON.parseObject(jsonObject.get("data").toString(), Picture.class);
        picture.setSize(FileUtil.getSize(Integer.valueOf(picture.getSize())));
        picture.setUsername(username);
        picture.setFilename(FileUtil.getFileNameNoEx(multipartFile.getOriginalFilename()) + "." + FileUtil.getExtensionName(multipartFile.getOriginalFilename()));
        pictureRepository.save(picture);
        //删除临时文件
        FileUtil.deleteFile(file);
        return picture;

    }

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    public Picture findById(Long id) {
        Optional<Picture> picture = pictureRepository.findById(id);
        ValidationUtil.isNull(picture, "Picture", "id", id);
        return picture.orElse(null);
    }

    /**
     * 删除图片
     *
     * @param picture
     */
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void delete(Picture picture) {
        try {
            HttpUtil.get(picture.getDelete());
            pictureRepository.delete(picture);
        } catch (Exception e) {
            log.error("删除图片失败原因：" + e);
        }

    }

    /**
     * 批量删除图片
     *
     * @param ids
     */
    @CacheEvict(allEntries = true)
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            delete(findById(id));
        }
    }

    /**
     * 分页查询
     */
    @Cacheable(keyGenerator = "keyGenerator")
    public Map<String, Object> queryAll(Picture query, Pageable pageable) {
        return PageUtil.toPage(pictureRepository.findAll((root, criteriaQuery, criteriaBuilder) -> BaseQuery.getPredicate(root, query, criteriaBuilder), pageable));
    }
}
