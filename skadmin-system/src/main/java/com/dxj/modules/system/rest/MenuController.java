package com.dxj.modules.system.rest;

import com.dxj.aop.log.Log;
import com.dxj.enums.EntityEnums;
import com.dxj.modules.system.domain.Menu;
import com.dxj.modules.system.domain.User;
import com.dxj.exception.BadRequestException;
import com.dxj.modules.system.domain.vo.MenuVo;
import com.dxj.modules.system.service.MenuService;
import com.dxj.modules.system.service.RoleService;
import com.dxj.modules.system.service.UserService;
import com.dxj.modules.system.dto.MenuDTO;
import com.dxj.modules.system.mapper.MenuMapper;
import com.dxj.modules.system.query.MenuQueryService;
import com.dxj.utils.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @author dxj
 * @date 2018-12-03
 */
@RestController
@RequestMapping("api")
public class MenuController {

    private final MenuService menuService;

    private final MenuQueryService menuQueryService;

    private final UserService userService;

    private final RoleService roleService;

    private final MenuMapper menuMapper;


    @Autowired
    public MenuController(MenuService menuService, MenuQueryService menuQueryService, UserService userService, RoleService roleService, MenuMapper menuMapper) {
        this.menuService = menuService;
        this.menuQueryService = menuQueryService;
        this.userService = userService;
        this.roleService = roleService;
        this.menuMapper = menuMapper;
    }

    /**
     * 构建前端路由所需要的菜单
     * @return
     */
    @GetMapping(value = "/menus/build")
    public ResponseEntity<List<MenuVo>> buildMenus(){
        UserDetails userDetails = SecurityContextHolder.getUserDetails();
        User user = userService.findByName(userDetails.getUsername());
        List<MenuDTO> menuDTOList = menuService.findByRoles(roleService.findByUsers_Id(user.getId()));
        List<MenuDTO> menuDTOTree = (List<MenuDTO>)menuService.buildTree(menuDTOList).get("content");
        return new ResponseEntity<>(menuService.buildMenus(menuDTOTree),HttpStatus.OK);
    }

    /**
     * 返回全部的菜单
     * @return
     */
    @GetMapping(value = "/menus/tree")
    @PreAuthorize("hasAnyRole('ADMIN','MENU_ALL','MENU_CREATE','MENU_EDIT','ROLES_SELECT','ROLES_ALL')")
    public ResponseEntity<Object> getMenuTree(){
        return new ResponseEntity<>(menuService.getMenuTree(menuService.findByPid(0L)),HttpStatus.OK);
    }

    @Log("查询菜单")
    @GetMapping(value = "/menus")
    @PreAuthorize("hasAnyRole('ADMIN','MENU_ALL','MENU_SELECT')")
    public ResponseEntity<Object> getMenus(@RequestParam(required = false) String name){
        List<MenuDTO> menuDTOList = menuQueryService.queryAll(name);
        return new ResponseEntity<>(menuService.buildTree(menuDTOList),HttpStatus.OK);
    }

    @Log("新增菜单")
    @PostMapping(value = "/menus")
    @PreAuthorize("hasAnyRole('ADMIN','MENU_ALL','MENU_CREATE')")
    public ResponseEntity<MenuDTO> create(@Validated @RequestBody Menu resources){
        if (resources.getId() != null) {
            throw new BadRequestException("A new "+ EntityEnums.MENU_ENTITY +" cannot already have an ID");
        }
        return new ResponseEntity<>(menuService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改菜单")
    @PutMapping(value = "/menus")
    @PreAuthorize("hasAnyRole('ADMIN','MENU_ALL','MENU_EDIT')")
    public ResponseEntity<Void> update(@Validated(Menu.Update.class) @RequestBody Menu resources){
        menuService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除菜单")
    @DeleteMapping(value = "/menus/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MENU_ALL','MENU_DELETE')")
    public ResponseEntity<Void> delete(@PathVariable Long id){
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
