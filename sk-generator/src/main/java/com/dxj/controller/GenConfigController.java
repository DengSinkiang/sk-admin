package com.dxj.controller;

import com.dxj.domain.entity.GenConfig;
import com.dxj.service.GenConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Sinkiang
 * @date 2019-01-14
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/genConfig")
@Api(tags = "系统：代码生成器配置管理")
public class GenConfigController {

    private final GenConfigService genConfigService;

    @ApiOperation("查询")
    @GetMapping(value = "/{tableName}")
    public ResponseEntity<Object> query(@PathVariable String tableName) {
        return new ResponseEntity<>(genConfigService.find(tableName), HttpStatus.OK);
    }

    @ApiOperation("修改")
    @PutMapping
    public ResponseEntity<Object> update(@Validated @RequestBody GenConfig genConfig) {
        return new ResponseEntity<>(genConfigService.update(genConfig.getTableName(), genConfig), HttpStatus.OK);
    }
}
