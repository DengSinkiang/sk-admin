package com.dxj.modules.system.rest;

import com.dxj.aop.log.Log;
import com.dxj.enums.EntityEnums;
import com.dxj.modules.system.domain.Permission;
import com.dxj.exception.BadRequestException;
import com.dxj.modules.system.service.PermissionService;
import com.dxj.modules.system.dto.PermissionDTO;
import com.dxj.modules.system.query.PermissionQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author dxj
 * @date 2018-12-03
 */
@RestController
@RequestMapping("api")
public class PermissionController {

    private final PermissionService permissionService;

    private final PermissionQueryService permissionQueryService;

    @Autowired
    public PermissionController(PermissionService permissionService, PermissionQueryService permissionQueryService) {
        this.permissionService = permissionService;
        this.permissionQueryService = permissionQueryService;
    }

    /**
     * 返回全部的权限，新增角色时下拉选择
     *
     * @return
     */
    @GetMapping(value = "/permissions/tree")
    @PreAuthorize("hasAnyRole('ADMIN','PERMISSION_ALL','PERMISSION_CREATE','PERMISSION_EDIT','ROLES_SELECT','ROLES_ALL')")
    public ResponseEntity<Object> getTree() {
        return new ResponseEntity<>(permissionService.getPermissionTree(permissionService.findByPid(0L)), HttpStatus.OK);
    }

    @Log("查询权限")
    @GetMapping(value = "/permissions")
    @PreAuthorize("hasAnyRole('ADMIN','PERMISSION_ALL','PERMISSION_SELECT')")
    public ResponseEntity<Object> getPermissions(@RequestParam(required = false) String name) {
        List<PermissionDTO> permissionDTOS = permissionQueryService.queryAll(name);
        return new ResponseEntity<>(permissionService.buildTree(permissionDTOS), HttpStatus.OK);
    }

    @Log("新增权限")
    @PostMapping(value = "/permissions")
    @PreAuthorize("hasAnyRole('ADMIN','PERMISSION_ALL','PERMISSION_CREATE')")
    public ResponseEntity<PermissionDTO> create(@Validated @RequestBody Permission resources) {
        if (resources.getId() != null) {
            throw new BadRequestException("A new " + EntityEnums.PERMISSION_ENTITY + " cannot already have an ID");
        }
        return new ResponseEntity<>(permissionService.create(resources), HttpStatus.CREATED);
    }

    @Log("修改权限")
    @PutMapping(value = "/permissions")
    @PreAuthorize("hasAnyRole('ADMIN','PERMISSION_ALL','PERMISSION_EDIT')")
    public ResponseEntity<Void> update(@Validated(Permission.Update.class) @RequestBody Permission resources) {
        permissionService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除权限")
    @DeleteMapping(value = "/permissions/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','PERMISSION_ALL','PERMISSION_DELETE')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        permissionService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
