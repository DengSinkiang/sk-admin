package com.dxj.module.system.service;

import com.dxj.module.system.domain.dto.MenuDTO;
import com.dxj.module.system.domain.entity.Menu;
import com.dxj.module.system.domain.query.MenuQuery;
import com.dxj.module.system.domain.dto.RoleSmallDTO;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Sinkiang
 * @date 2018-12-17
 */
public interface MenuService {

    /**
     * 查询全部数据
     *
     * @param criteria 条件
     * @param isQuery  /
     * @return /
     * @throws Exception /
     */
    List<MenuDTO> queryAll(MenuQuery criteria, Boolean isQuery) throws Exception;

    /**
     * 根据ID查询
     *
     * @param id /
     * @return /
     */
    MenuDTO findById(long id);

    /**
     * 创建
     *
     * @param resources /
     */
    void create(Menu resources);

    /**
     * 编辑
     *
     * @param resources /
     */
    void update(Menu resources);

    /**
     * 获取待删除的菜单
     *
     * @param menuList /
     * @param menuSet  /
     * @return /
     */
    Set<Menu> getDeleteMenus(List<Menu> menuList, Set<Menu> menuSet);

    /**
     * 构建菜单树
     *
     * @param menuDtos 原始数据
     * @return /
     */
    List<MenuDTO> buildTree(List<MenuDTO> menuDtos);

    /**
     * 构建菜单树
     *
     * @param menuDtos /
     * @return /
     */
    Object buildMenus(List<MenuDTO> menuDtos);

    /**
     * 根据ID查询
     *
     * @param id /
     * @return /
     */
    Menu findOne(Long id);

    /**
     * 删除
     *
     * @param menuSet /
     */
    void delete(Set<Menu> menuSet);

    /**
     * 导出
     *
     * @param queryAll 待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<MenuDTO> queryAll, HttpServletResponse response) throws IOException;

    /**
     * 懒加载菜单数据
     *
     * @param pid /
     * @return /
     */
    List<MenuDTO> getMenus(Long pid);

    /**
     * 根据ID获取同级与上级数据
     *
     * @param menuDto /
     * @param objects /
     * @return /
     */
    List<MenuDTO> getSuperior(MenuDTO menuDto, List<Menu> objects);

    /**
     * 根据当前用户获取菜单
     *
     * @param currentUserId /
     * @return /
     */
    List<MenuDTO> findByUser(Long currentUserId);
}
