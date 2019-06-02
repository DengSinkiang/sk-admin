package com.dxj.admin.controller;

import com.dxj.log.annotation.Log;
import com.dxj.common.enums.EntityEnums;
import com.dxj.admin.domain.Menu;
import com.dxj.common.exception.BadRequestException;
import com.dxj.admin.domain.vo.MenuVo;
import com.dxj.admin.dto.UserDTO;
import com.dxj.admin.service.MenuService;
import com.dxj.admin.service.RoleService;
import com.dxj.admin.service.UserService;
import com.dxj.admin.dto.MenuDTO;
import com.dxj.common.util.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author dxj
 * @date 2018-12-03
 */
@RestController
@RequestMapping("api")
public class MenuController {

    private final MenuService menuService;

    private final UserService userService;

    private final RoleService roleService;



    @Autowired
    public MenuController(MenuService menuService, UserService userService, RoleService roleService) {
        this.menuService = menuService;
        this.userService = userService;
        this.roleService = roleService;
    }

    /**
     * 构建前端路由所需要的菜单
     *
     * @return
     */
    @GetMapping(value = "/menus/build")
    public ResponseEntity<List<MenuVo>> buildMenus() {
        UserDTO user = userService.findByName(SecurityContextHolder.getUsername());
        List<MenuDTO> menuDTOList = menuService.findByRoles(roleService.findByUsers_Id(user.getId()));
        List<MenuDTO> menuDTOTree = (List<MenuDTO>) menuService.buildTree(menuDTOList).get("content");
        return new ResponseEntity<>(menuService.buildMenus(menuDTOTree), HttpStatus.OK);
    }

    /**
     * 返回全部的菜单
     *
     * @return
     */
    @GetMapping(value = "/menus/tree")
    @PreAuthorize("hasAnyRole('ADMIN','MENU_ALL','MENU_CREATE','MENU_EDIT','ROLES_SELECT','ROLES_ALL')")
    public ResponseEntity<Object> getMenuTree() {
        return new ResponseEntity<>(menuService.getMenuTree(menuService.findByPid(0L)), HttpStatus.OK);
    }

    @Log("查询菜单")
    @GetMapping(value = "/menus")
    @PreAuthorize("hasAnyRole('ADMIN','MENU_ALL','MENU_SELECT')")
    public ResponseEntity<Map<String, Object>> getMenus(@RequestParam(required = false) String name) {
        List<MenuDTO> menuDTOList = menuService.queryAll(name);
        return new ResponseEntity<>(menuService.buildTree(menuDTOList), HttpStatus.OK);
    }

    @Log("新增菜单")
    @PostMapping(value = "/menus")
    @PreAuthorize("hasAnyRole('ADMIN','MENU_ALL','MENU_CREATE')")
    public ResponseEntity<MenuDTO> create(@Validated @RequestBody Menu resources) {
        if (resources.getId() != null) {
            throw new BadRequestException("A new " + EntityEnums.MENU_ENTITY + " cannot already have an ID");
        }
        return new ResponseEntity<>(menuService.create(resources), HttpStatus.CREATED);
    }

    @Log("修改菜单")
    @PutMapping(value = "/menus")
    @PreAuthorize("hasAnyRole('ADMIN','MENU_ALL','MENU_EDIT')")
    public ResponseEntity<Void> update(@Validated(Menu.Update.class) @RequestBody Menu resources) {
        menuService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除菜单")
    @DeleteMapping(value = "/menus/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MENU_ALL','MENU_DELETE')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        List<Menu> menuList = menuService.findByPid(id);

        // 特殊情况，对级联删除进行处理
        for (Menu menu : menuList) {
            roleService.untiedMenu(menu);
            menuService.delete(menu.getId());
        }
        roleService.untiedMenu(menuService.findOne(id));
        menuService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
