package com.dxj.admin.module.controller;

import com.dxj.log.annotation.Log;
import com.dxj.admin.module.domain.TbUser;
import com.dxj.admin.module.service.TbUserService;
import com.dxj.admin.module.dto.TbUserQueryCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
* @author sinkiang
* @date 2019-08-28
*/
@RestController
@RequestMapping("api")
public class TbUserController {

    @Autowired
    private TbUserService tbUserService;

    @Log("查询TbUser")
    @GetMapping(value = "/tbUser")
    @PreAuthorize("hasAnyRole('ADMIN', 'TBUSER_ALL', 'TBUSER_SELECT')")
    public ResponseEntity getTbUsers(TbUserQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity(tbUserService.queryAll(criteria,pageable), HttpStatus.OK);
    }

    @Log("新增TbUser")
    @PostMapping(value = "/tbUser")
    @PreAuthorize("hasAnyRole('ADMIN', 'TBUSER_ALL', 'TBUSER_CREATE')")
    public ResponseEntity<Object> create(@Validated @RequestBody TbUser resources) {
        return new ResponseEntity<>(tbUserService.create(resources), HttpStatus.CREATED);
    }

    @Log("修改TbUser")
    @PutMapping(value = "/tbUser")
    @PreAuthorize("hasAnyRole('ADMIN', 'TBUSER_ALL', 'TBUSER_EDIT')")
    public ResponseEntity<Void> update(@Validated @RequestBody TbUser resources) {
        tbUserService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除TbUser")
    @DeleteMapping(value = "/tbUser/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TBUSER_ALL', 'TBUSER_DELETE')")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        tbUserService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
