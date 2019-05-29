package com.dxj.modules.system.rest;

import com.dxj.aop.log.Log;
import com.dxj.enums.EntityEnums;
import com.dxj.exception.BadRequestException;
import com.dxj.modules.system.domain.Dict;
import com.dxj.modules.system.service.DictService;
import com.dxj.modules.system.dto.DictDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author dxj
 * @date 2019-04-10
 */
@RestController
@RequestMapping("api")
public class DictController {

    private final DictService dictService;


    @Autowired
    public DictController(DictService dictService) {
        this.dictService = dictService;
    }

    @Log("查询字典")
    @GetMapping(value = "/dict")
    @PreAuthorize("hasAnyRole('ADMIN','DICT_ALL','DICT_SELECT')")
    public ResponseEntity<Object> getDict(DictDTO resources, Pageable pageable) {
        return new ResponseEntity<>(dictService.queryAll(resources, pageable), HttpStatus.OK);
    }

    @Log("新增字典")
    @PostMapping(value = "/dict")
    @PreAuthorize("hasAnyRole('ADMIN','DICT_ALL','DICT_CREATE')")
    public ResponseEntity<DictDTO> create(@Validated @RequestBody Dict resources) {
        if (resources.getId() != null) {
            throw new BadRequestException("A new " + EntityEnums.DICT_ENTITY + " cannot already have an ID");
        }
        return new ResponseEntity<>(dictService.create(resources), HttpStatus.CREATED);
    }

    @Log("修改字典")
    @PutMapping(value = "/dict")
    @PreAuthorize("hasAnyRole('ADMIN','DICT_ALL','DICT_EDIT')")
    public ResponseEntity<Void> update(@Validated(Dict.Update.class) @RequestBody Dict resources) {
        dictService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除字典")
    @DeleteMapping(value = "/dict/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DICT_ALL','DICT_DELETE')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        dictService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
