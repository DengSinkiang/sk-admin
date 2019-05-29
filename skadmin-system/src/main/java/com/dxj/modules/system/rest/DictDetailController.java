package com.dxj.modules.system.rest;

import com.dxj.aop.log.Log;
import com.dxj.enums.EntityEnums;
import com.dxj.exception.BadRequestException;
import com.dxj.modules.system.domain.DictDetail;
import com.dxj.modules.system.service.DictDetailService;
import com.dxj.modules.system.dto.DictDetailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
public class DictDetailController {

    private final DictDetailService dictDetailService;


    @Autowired
    public DictDetailController(DictDetailService dictDetailService) {
        this.dictDetailService = dictDetailService;
    }

    @Log("查询字典详情")
    @GetMapping(value = "/dictDetail")
    public ResponseEntity<Object> getDictDetails(DictDetailDTO resources,
                                                 @PageableDefault(value = 10, sort = {"sort"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return new ResponseEntity<>(dictDetailService.queryAll(resources, pageable), HttpStatus.OK);
    }

    @Log("新增字典详情")
    @PostMapping(value = "/dictDetail")
    @PreAuthorize("hasAnyRole('ADMIN','DICT_ALL','DICT_CREATE')")
    public ResponseEntity<DictDetailDTO> create(@Validated @RequestBody DictDetail resources) {
        if (resources.getId() != null) {
            throw new BadRequestException("A new " + EntityEnums.DICTDETAIL_ENTITY + " cannot already have an ID");
        }
        return new ResponseEntity<>(dictDetailService.create(resources), HttpStatus.CREATED);
    }

    @Log("修改字典详情")
    @PutMapping(value = "/dictDetail")
    @PreAuthorize("hasAnyRole('ADMIN','DICT_ALL','DICT_EDIT')")
    public ResponseEntity<Void> update(@Validated(DictDetail.Update.class) @RequestBody DictDetail resources) {
        dictDetailService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除字典详情")
    @DeleteMapping(value = "/dictDetail/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DICT_ALL','DICT_DELETE')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        dictDetailService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
