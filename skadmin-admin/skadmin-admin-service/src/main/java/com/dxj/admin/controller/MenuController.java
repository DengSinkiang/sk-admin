package com.dxj.admin.controller;

import com.dxj.admin.entity.Menu;
import com.dxj.admin.entity.vo.MenuVo;
import com.dxj.admin.entity.dto.MenuDTO;
import com.dxj.admin.entity.dto.UserDTO;
import com.dxj.admin.query.CommonQuery;
import com.dxj.admin.service.MenuService;
import com.dxj.admin.service.RoleService;
import com.dxj.admin.service.UserService;
import com.dxj.common.enums.CommEnum;
import com.dxj.common.exception.BadRequestException;
import com.dxj.common.util.SecurityContextHolder;
import com.dxj.log.annotation.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author dxj
 * @date 2019-04-03
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
    @GetMapping(value = "/menu/build")
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
    @GetMapping(value = "/menu/tree")
    @PreAuthorize("hasAnyRole('ADMIN','MENU_ALL','MENU_CREATE','MENU_EDIT','ROLES_SELECT','ROLES_ALL')")
    public ResponseEntity<Object> getMenuTree() {
        return new ResponseEntity<>(menuService.getMenuTree(menuService.findByPid(0L)), HttpStatus.OK);
    }

    @Log("查询菜单")
    @GetMapping(value = "/menu")
    @PreAuthorize("hasAnyRole('ADMIN','MENU_ALL','MENU_SELECT')")
    public ResponseEntity<Map<String, Object>> getMenus(CommonQuery query) {
        List<MenuDTO> menuDTOList = menuService.queryAll(query);
        return new ResponseEntity<>(menuService.buildTree(menuDTOList), HttpStatus.OK);
    }

    @Log("新增菜单")
    @PostMapping(value = "/menu")
    @PreAuthorize("hasAnyRole('ADMIN','MENU_ALL','MENU_CREATE')")
    public ResponseEntity<MenuDTO> create(@Validated @RequestBody Menu resources) {
        if (resources.getId() != null) {
            throw new BadRequestException("A new " + CommEnum.MENU_ENTITY + " cannot already have an ID");
        }
        return new ResponseEntity<>(menuService.create(resources), HttpStatus.CREATED);
    }

    @Log("修改菜单")
    @PutMapping(value = "/menu")
    @PreAuthorize("hasAnyRole('ADMIN','MENU_ALL','MENU_EDIT')")
    public ResponseEntity<Void> update(@Validated(Menu.Update.class) @RequestBody Menu resources) {
        menuService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除菜单")
    @DeleteMapping(value = "/menu/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MENU_ALL','MENU_DELETE')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        List<Menu> test = new ArrayList<>();
        for (Menu menu : menuService.treeList(test, id)) {
            roleService.untiedMenu(menu);
            menuService.delete(menu.getId());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
