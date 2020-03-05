package com.dxj.tool.controller;

import com.dxj.log.annotation.Log;
import com.dxj.tool.domain.entity.Picture;
import com.dxj.tool.service.PictureService;
import com.dxj.common.util.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dxj
 * @date 2019/04/20 14:13:32
 */
@RestController
@RequestMapping("api")
public class PictureController {

    private final PictureService pictureService;

    @Autowired
    public PictureController(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    @Log("查询图片")
    @PreAuthorize("hasAnyRole('ADMIN', 'PICTURE_ALL', 'PICTURE_SELECT')")
    @GetMapping(value = "/picture")
    public ResponseEntity<Map<String, Object>> getRoles(Picture query, Pageable pageable) {
        return new ResponseEntity<>(pictureService.queryAll(query, pageable), HttpStatus.OK);
    }

    /**
     * 上传图片
     *
     * @param file
     * @return
     * @throws Exception
     */
    @Log("上传图片")
    @PreAuthorize("hasAnyRole('ADMIN','PICTURE_ALL','PICTURE_UPLOAD')")
    @PostMapping(value = "/picture")
    public ResponseEntity<Map<String, Object>> upload(@RequestParam MultipartFile file) {
        String userName = SecurityContextHolder.getUsername();
        Picture picture = pictureService.upload(file, userName);
        Map<String, Object> map = new HashMap<>();
        map.put("errno", 0);
        map.put("id", picture.getId());
        map.put("data", new String[]{picture.getUrl()});
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    /**
     * 删除图片
     *
     * @param id
     * @return
     */
    @Log("删除图片")
    @PreAuthorize("hasAnyRole('ADMIN','PICTURE_ALL','PICTURE_DELETE')")
    @DeleteMapping(value = "/picture/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        pictureService.delete(pictureService.findById(id));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 删除多张图片
     *
     * @param ids
     * @return
     */
    @Log("删除图片")
    @PreAuthorize("hasAnyRole('ADMIN', 'PICTURE_ALL', 'PICTURE_DELETE')")
    @DeleteMapping(value = "/picture")
    public ResponseEntity<Void> deleteAll(@RequestBody Long[] ids) {
        pictureService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
