package com.dxj.tool.controller;

import com.dxj.log.annotation.Log;
import com.dxj.tool.domain.AliConfig;
import com.dxj.tool.domain.AliContent;
import com.dxj.tool.service.AliService;
import lombok.extern.slf4j.Slf4j;
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
 * 阿里云上传
 * @AUTHOR: sinkiang
 * @DATE: 2019-06-21 21:08
 */
@Slf4j
@RestController
@RequestMapping("api")
public class AliController {

    @Autowired
    private AliService aliService;

    @GetMapping(value = "/aliYunConfig")
    public ResponseEntity<Void> get(){
        return new ResponseEntity<>(aliService.find(), HttpStatus.OK);
    }

    @Log("配置阿里云存储")
    @PutMapping(value = "/aliYunConfig")
    public ResponseEntity<Void> aliConfig(@Validated @RequestBody AliConfig aliConfig){
        aliService.update(aliConfig);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Log("查询文件")
    @GetMapping(value = "/aliYunContent")
    public ResponseEntity<Object> getRoles(AliContent resources, Pageable pageable){
        return new ResponseEntity<>(aliService.queryAll(resources, pageable), HttpStatus.OK);
    }

    /**
     * 上传文件到阿里云
     * @param file
     * @return
     */
    @Log("上传文件")
    @PostMapping(value = "/aliYunContent")
    public ResponseEntity<Map<String, Object>> upload(@RequestParam MultipartFile file){
        AliContent aliContent = aliService.upload(file, aliService.find());
        Map<String, Object> map = new HashMap<>();
        map.put("id", aliContent.getId());
        map.put("code", 0);
        map.put("data", new String[]{aliContent.getUrl()});
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    /**
     * 同步阿里云数据到数据库
     * @return
     */
    @Log("同步阿里云数据")
    @PostMapping(value = "/aliYunContent/synchronize")
    public ResponseEntity<Void> synchronize(){
        log.warn("REST request to synchronize qiNiu : {}");
        aliService.synchronize(aliService.find());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 下载阿里云文件
     * @param id
     * @return
     * @throws Exception
     */
    @Log("下载阿里云文件")
    @GetMapping(value = "/aliYunContent/download/{id}")
    public ResponseEntity<Map<String, Object>> download(@PathVariable Long id){

        Map<String, Object> map = new HashMap<>();
        map.put("url", aliService.download(aliService.findByContentId(id), aliService.find()));
        return new ResponseEntity<>(map,HttpStatus.OK);
    }

    /**
     * 删除阿里云文件
     * @param id
     * @return
     * @throws Exception
     */
    @Log("删除文件")
    @DeleteMapping(value = "/aliYunContent/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        aliService.delete(aliService.findByContentId(id),aliService.find());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 批量删除文件
     * @param ids
     * @return
     */
    @Log("批量删除文件")
    @DeleteMapping(value = "/aliYunContent")
    public ResponseEntity<Void> deleteAll(@RequestBody Long[] ids) {
        aliService.deleteAll(ids, aliService.find());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
