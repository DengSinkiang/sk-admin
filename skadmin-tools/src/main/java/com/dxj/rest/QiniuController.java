package com.dxj.rest;

import com.dxj.service.QiNiuService;
import lombok.extern.slf4j.Slf4j;
import com.dxj.aop.log.Log;
import com.dxj.domain.QiniuConfig;
import com.dxj.domain.QiniuContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * 七牛云上传
 * @author dxj
 * @date 2018/09/28 6:55:53
 */
@Slf4j
@RestController
@RequestMapping("api")
public class QiniuController {

    private final QiNiuService qiNiuService;


    @Autowired
    public QiniuController(QiNiuService qiNiuService) {
        this.qiNiuService = qiNiuService;
    }

    @GetMapping(value = "/qiNiuConfig")
    public ResponseEntity<QiniuConfig> get(){
        return new ResponseEntity<>(qiNiuService.find(), HttpStatus.OK);
    }

    @Log("配置七牛云存储")
    @PutMapping(value = "/qiNiuConfig")
    public ResponseEntity<Void> emailConfig(@Validated @RequestBody QiniuConfig qiniuConfig){
        qiNiuService.update(qiniuConfig);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Log("查询文件")
    @GetMapping(value = "/qiNiuContent")
    public ResponseEntity<Object> getRoles(QiniuContent resources, Pageable pageable){
        return new ResponseEntity<>(qiNiuService.queryAll(resources, pageable), HttpStatus.OK);
    }

    /**
     * 上传文件到七牛云
     * @param file
     * @return
     */
    @Log("上传文件")
    @PostMapping(value = "/qiNiuContent")
    public ResponseEntity<Map<String, Object>> upload(@RequestParam MultipartFile file){
        QiniuContent qiniuContent = qiNiuService.upload(file, qiNiuService.find());
        Map<String, Object> map = new HashMap<>();
        map.put("id", qiniuContent.getId());
        map.put("code", 0);
        map.put("data", new String[]{qiniuContent.getUrl()});
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    /**
     * 同步七牛云数据到数据库
     * @return
     */
    @Log("同步七牛云数据")
    @PostMapping(value = "/qiNiuContent/synchronize")
    public ResponseEntity<Void> synchronize(){
        log.warn("REST request to synchronize qiNiu : {}");
        qiNiuService.synchronize(qiNiuService.find());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 下载七牛云文件
     * @param id
     * @return
     * @throws Exception
     */
    @Log("下载文件")
    @GetMapping(value = "/qiNiuContent/download/{id}")
    public ResponseEntity<Map<String, Object>> download(@PathVariable Long id){

        Map<String, Object> map = new HashMap<>();
        map.put("url", qiNiuService.download(qiNiuService.findByContentId(id), qiNiuService.find()));
        return new ResponseEntity<>(map,HttpStatus.OK);
    }

    /**
     * 删除七牛云文件
     * @param id
     * @return
     * @throws Exception
     */
    @Log("删除文件")
    @DeleteMapping(value = "/qiNiuContent/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        qiNiuService.delete(qiNiuService.findByContentId(id),qiNiuService.find());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 批量删除文件
     * @param ids
     * @return
     */
    @Log("批量删除文件")
    @DeleteMapping(value = "/qiNiuContent")
    public ResponseEntity<Void> deleteAll(@RequestBody Long[] ids) {
        qiNiuService.deleteAll(ids, qiNiuService.find());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
