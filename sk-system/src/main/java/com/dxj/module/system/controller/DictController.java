package com.dxj.module.system.controller;

import com.dxj.annotation.Log;
import com.dxj.exception.SkException;
import com.dxj.module.system.domain.entity.Dict;
import com.dxj.module.system.domain.query.DictQuery;
import com.dxj.module.system.service.DictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Sinkiang
 * @date 2019-04-10
 */
@Api(tags = "系统：字典管理")
@RestController
@RequestMapping("/api/dict")
public class DictController {

    private final DictService dictService;

    private static final String ENTITY_NAME = "dict";

    public DictController(DictService dictService) {
        this.dictService = dictService;
    }

    @Log("导出字典数据")
    @ApiOperation("导出字典数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@sk.check('dict:list')")
    public void download(HttpServletResponse response, DictQuery criteria) throws IOException {
        dictService.download(dictService.queryAll(criteria), response);
    }

    @Log("查询字典")
    @ApiOperation("查询字典")
    @GetMapping(value = "/all")
    @PreAuthorize("@sk.check('dict:list')")
    public ResponseEntity<Object> all() {
        return new ResponseEntity<>(dictService.queryAll(new DictQuery()), HttpStatus.OK);
    }

    @Log("查询字典")
    @ApiOperation("查询字典")
    @GetMapping
    @PreAuthorize("@sk.check('dict:list')")
    public ResponseEntity<Object> getDicts(DictQuery resources, Pageable pageable) {
        return new ResponseEntity<>(dictService.queryAll(resources, pageable), HttpStatus.OK);
    }

    @Log("新增字典")
    @ApiOperation("新增字典")
    @PostMapping
    @PreAuthorize("@sk.check('dict:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Dict resources) {
        if (resources.getId() != null) {
            throw new SkException("A new " + ENTITY_NAME + " cannot already have an ID");
        }
        return new ResponseEntity<>(dictService.create(resources), HttpStatus.CREATED);
    }

    @Log("修改字典")
    @ApiOperation("修改字典")
    @PutMapping
    @PreAuthorize("@sk.check('dict:edit')")
    public ResponseEntity<Void> update(@Validated(Dict.Update.class) @RequestBody Dict resources) {
        dictService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除字典")
    @ApiOperation("删除字典")
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("@sk.check('dict:del')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        dictService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
