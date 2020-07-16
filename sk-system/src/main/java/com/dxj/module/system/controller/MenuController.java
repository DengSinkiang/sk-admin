package com.dxj.module.system.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.dxj.annotation.Log;
import com.dxj.exception.SkException;
import com.dxj.module.system.domain.dto.MenuDTO;
import com.dxj.module.system.domain.entity.Menu;
import com.dxj.module.system.domain.mapstruct.MenuMapper;
import com.dxj.module.system.domain.query.MenuQuery;
import com.dxj.module.system.service.MenuService;
import com.dxj.module.system.service.RoleService;
import com.dxj.module.system.service.UserService;
import com.dxj.util.PageUtil;
import com.dxj.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @author Sinkiang
 * @date 2018-12-03
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "系统：菜单管理")
@RequestMapping("/api/menus")
public class MenuController {

    private final MenuService menuService;
    private final MenuMapper menuMapper;
    private static final String ENTITY_NAME = "menu";

    @Log("导出菜单数据")
    @ApiOperation("导出菜单数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@sk.check('menu:list')")
    public void download(HttpServletResponse response, MenuQuery criteria) throws Exception {
        menuService.download(menuService.queryAll(criteria, false), response);
    }

    @GetMapping(value = "/build")
    @ApiOperation("获取前端所需菜单")
    public ResponseEntity<Object> buildMenus(){
        List<MenuDTO> menuDtoList = menuService.findByUser(SecurityUtils.getCurrentUserId());
        List<MenuDTO> menuDtos = menuService.buildTree(menuDtoList);
        return new ResponseEntity<>(menuService.buildMenus(menuDtos),HttpStatus.OK);
    }

    @ApiOperation("返回全部的菜单")
    @GetMapping(value = "/lazy")
    @PreAuthorize("@sk.check('menu:list','roles:list')")
    public ResponseEntity<Object> query(@RequestParam Long pid){
        return new ResponseEntity<>(menuService.getMenus(pid),HttpStatus.OK);
    }

    @Log("查询菜单")
    @ApiOperation("查询菜单")
    @GetMapping
    @PreAuthorize("@sk.check('menu:list')")
    public ResponseEntity<Object> query(MenuQuery criteria) throws Exception {
        List<MenuDTO> menuDtoList = menuService.queryAll(criteria, true);
        return new ResponseEntity<>(PageUtil.toPage(menuDtoList, menuDtoList.size()),HttpStatus.OK);
    }

    @Log("查询菜单")
    @ApiOperation("查询菜单:根据ID获取同级与上级数据")
    @PostMapping("/superior")
    @PreAuthorize("@sk.check('menu:list')")
    public ResponseEntity<Object> getSuperior(@RequestBody List<Long> ids) {
        Set<MenuDTO> menuDtos = new LinkedHashSet<>();
        if(CollectionUtil.isNotEmpty(ids)){
            for (Long id : ids) {
                MenuDTO menuDto = menuService.findById(id);
                menuDtos.addAll(menuService.getSuperior(menuDto, new ArrayList<>()));
            }
            return new ResponseEntity<>(menuService.buildTree(new ArrayList<>(menuDtos)),HttpStatus.OK);
        }
        return new ResponseEntity<>(menuService.getMenus(null),HttpStatus.OK);
    }

    @Log("新增菜单")
    @ApiOperation("新增菜单")
    @PostMapping
    @PreAuthorize("@sk.check('menu:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Menu resources){
        if (resources.getId() != null) {
            throw new SkException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        menuService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Log("修改菜单")
    @ApiOperation("修改菜单")
    @PutMapping
    @PreAuthorize("@sk.check('menu:edit')")
    public ResponseEntity<Object> update(@Validated(Menu.Update.class) @RequestBody Menu resources){
        menuService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除菜单")
    @ApiOperation("删除菜单")
    @DeleteMapping
    @PreAuthorize("@sk.check('menu:del')")
    public ResponseEntity<Object> delete(@RequestBody Set<Long> ids){
        Set<Menu> menuSet = new HashSet<>();
        for (Long id : ids) {
            List<MenuDTO> menuList = menuService.getMenus(id);
            menuSet.add(menuService.findOne(id));
            menuSet = menuService.getDeleteMenus(menuMapper.toEntity(menuList), menuSet);
        }
        menuService.delete(menuSet);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
