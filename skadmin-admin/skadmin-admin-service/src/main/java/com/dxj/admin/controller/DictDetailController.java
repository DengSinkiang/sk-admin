package com.dxj.admin.controller;

import com.dxj.admin.entity.DictDetail;
import com.dxj.admin.entity.dto.DictDetailDTO;
import com.dxj.admin.query.DictDetailQuery;
import com.dxj.admin.service.DictDetailService;
import com.dxj.common.enums.CommEnum;
import com.dxj.common.exception.BadRequestException;
import com.dxj.log.annotation.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
    public ResponseEntity<Map<String, Object>> getDictDetail(DictDetailQuery query,
                                                             @PageableDefault(sort = {"sort"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return new ResponseEntity<>(dictDetailService.queryAll(query, pageable), HttpStatus.OK);
    }

    @Log("查询多个字典详情")
    @GetMapping(value = "/dictDetail/map")
    public ResponseEntity<Map<String, Object>> getDictDetailMaps(DictDetailQuery criteria,
                                            @PageableDefault(sort = {"sort"}, direction = Sort.Direction.ASC) Pageable pageable) {
        String[] nameArr = criteria.getDictName().split(",");
        Map<String, Object> map = new HashMap<>(nameArr.length);
        for (String name : nameArr) {
            map.put(name, dictDetailService.queryAll(criteria, pageable).get("content"));
        }
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @Log("新增字典详情")
    @PostMapping(value = "/dictDetail")
    @PreAuthorize("hasAnyRole('ADMIN','DICT_ALL','DICT_CREATE')")
    public ResponseEntity<DictDetailDTO> create(@Validated @RequestBody DictDetail resources) {
        if (resources.getId() != null) {
            throw new BadRequestException("A new " + CommEnum.DICT_DETAIL_ENTITY + " cannot already have an ID");
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
