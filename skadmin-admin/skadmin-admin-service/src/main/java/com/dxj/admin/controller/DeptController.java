package com.dxj.admin.controller;

import com.dxj.admin.config.DataScope;
import com.dxj.admin.domain.Dept;
import com.dxj.admin.dto.DeptDTO;
import com.dxj.admin.query.DeptQuery;
import com.dxj.admin.service.DeptService;
import com.dxj.common.enums.CommEnum;
import com.dxj.common.exception.BadRequestException;
import com.dxj.log.annotation.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author dxj
 * @date 2019-03-25
 */
@RestController
@RequestMapping("api")
public class DeptController {

    private final DeptService deptService;

    private final DataScope dataScope;

    @Autowired
    public DeptController(DeptService deptService, DataScope dataScope) {
        this.deptService = deptService;
        this.dataScope = dataScope;
    }

    @Log("查询部门")
    @GetMapping(value = "/dept")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT','DEPT_ALL','DEPT_SELECT')")
    public ResponseEntity<Map<String, Object>> getDept(DeptQuery query) {
        // 数据权限
        query.setIds(dataScope.getDeptIds());
        List<DeptDTO> deptDTOList = deptService.queryAll(query);
        return new ResponseEntity<>(deptService.buildTree(deptDTOList), HttpStatus.OK);
    }

    @Log("新增部门")
    @PostMapping(value = "/dept")
    @PreAuthorize("hasAnyRole('ADMIN','DEPT_ALL','DEPT_CREATE')")
    public ResponseEntity<DeptDTO> create(@Validated @RequestBody Dept resources) {
        if (resources.getId() != null) {
            throw new BadRequestException("A new " + CommEnum.DEPT_ENTITY + " cannot already have an ID");
        }
        return new ResponseEntity<>(deptService.create(resources), HttpStatus.CREATED);
    }

    @Log("修改部门")
    @PutMapping(value = "/dept")
    @PreAuthorize("hasAnyRole('ADMIN','DEPT_ALL','DEPT_EDIT')")
    public ResponseEntity<Void> update(@Validated(Dept.Update.class) @RequestBody Dept resources) {
        deptService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除部门")
    @DeleteMapping(value = "/dept/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DEPT_ALL','DEPT_DELETE')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deptService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
