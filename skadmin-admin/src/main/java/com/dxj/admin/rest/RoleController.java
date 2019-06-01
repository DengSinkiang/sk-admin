package com.dxj.admin.rest;

import cn.hutool.core.lang.Dict;
import com.dxj.aop.log.Log;
import com.dxj.enums.EntityEnums;
import com.dxj.admin.domain.Role;
import com.dxj.exception.BadRequestException;
import com.dxj.admin.dto.RoleDTO;
import com.dxj.admin.dto.RoleSmallDTO;
import com.dxj.admin.service.RoleService;
import com.dxj.utils.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dxj
 * @date 2018-12-03
 */
@RestController
@RequestMapping("api")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping(value = "/roles/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ROLES_ALL','ROLES_SELECT')")
    public ResponseEntity<RoleDTO> getRoles(@PathVariable Long id) {
        return new ResponseEntity<>(roleService.findById(id), HttpStatus.OK);
    }

    /**
     * 返回全部的角色，新增用户时下拉选择
     *
     * @return
     */
    @GetMapping(value = "/roles/all")
    @PreAuthorize("hasAnyRole('ADMIN','ROLES_ALL','USER_ALL','USER_CREATE','USER_EDIT')")
    public ResponseEntity<Object> getAll(@PageableDefault(value = 2000, sort = {"level"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return new ResponseEntity<>(roleService.queryAll(pageable), HttpStatus.OK);
    }

    @Log("查询角色")
    @GetMapping(value = "/roles")
    @PreAuthorize("hasAnyRole('ADMIN','ROLES_ALL','ROLES_SELECT')")
    public ResponseEntity<Object> getRoles(@RequestParam(required = false) String name, Pageable pageable) {
        return new ResponseEntity<>(roleService.queryAll(name, pageable), HttpStatus.OK);
    }
    @GetMapping(value = "/roles/level")
    public ResponseEntity<Object> getLevel(){
        List<Integer> levels = roleService.findByUsers_Id(SecurityContextHolder.getUserId()).stream().map(RoleSmallDTO::getLevel).collect(Collectors.toList());
        return new ResponseEntity<>(Dict.create().set("level", Collections.min(levels)), HttpStatus.OK);
    }
    @Log("新增角色")
    @PostMapping(value = "/roles")
    @PreAuthorize("hasAnyRole('ADMIN','ROLES_ALL','ROLES_CREATE')")
    public ResponseEntity<RoleDTO> create(@Validated @RequestBody Role resources) {
        if (resources.getId() != null) {
            throw new BadRequestException("A new " + EntityEnums.ROLE_ENTITY + " cannot already have an ID");
        }
        return new ResponseEntity<>(roleService.create(resources), HttpStatus.CREATED);
    }

    @Log("修改角色")
    @PutMapping(value = "/roles")
    @PreAuthorize("hasAnyRole('ADMIN','ROLES_ALL','ROLES_EDIT')")
    public ResponseEntity<Void> update(@Validated(Role.Update.class) @RequestBody Role resources) {
        roleService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("修改角色权限")
    @PutMapping(value = "/roles/permission")
    @PreAuthorize("hasAnyRole('ADMIN','ROLES_ALL','ROLES_EDIT')")
    public ResponseEntity<Void> updatePermission(@RequestBody Role resources) {
        roleService.updatePermission(resources, roleService.findById(resources.getId()));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("修改角色菜单")
    @PutMapping(value = "/roles/menu")
    @PreAuthorize("hasAnyRole('ADMIN','ROLES_ALL','ROLES_EDIT')")
    public ResponseEntity<Void> updateMenu(@RequestBody Role resources) {
        roleService.updateMenu(resources, roleService.findById(resources.getId()));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除角色")
    @DeleteMapping(value = "/roles/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ROLES_ALL','ROLES_DELETE')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        roleService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
