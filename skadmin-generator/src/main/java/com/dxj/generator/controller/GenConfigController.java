package com.dxj.generator.controller;

import com.dxj.generator.domain.GenConfig;
import com.dxj.generator.service.GenConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author dxj
 * @date 2019-01-14
 */
@RestController
@RequestMapping("api")
public class GenConfigController {

    private final GenConfigService genConfigService;

    @Autowired
    public GenConfigController(GenConfigService genConfigService) {
        this.genConfigService = genConfigService;
    }

    /**
     * 查询生成器配置
     *
     * @return
     */
    @GetMapping(value = "/genConfig")
    public ResponseEntity<GenConfig> get() {
        return new ResponseEntity<>(genConfigService.find(), HttpStatus.OK);
    }

    @PutMapping(value = "/genConfig")
    public ResponseEntity<GenConfig> genConfig(@Validated @RequestBody GenConfig genConfig) {
        return new ResponseEntity<>(genConfigService.update(genConfig), HttpStatus.OK);
    }
}
