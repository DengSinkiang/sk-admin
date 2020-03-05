package com.dxj.admin.service;

import cn.hutool.core.util.StrUtil;
import com.dxj.admin.domain.entity.Menu;
import com.dxj.admin.domain.vo.MenuMetaVo;
import com.dxj.admin.domain.vo.MenuVo;
import com.dxj.admin.domain.dto.MenuDTO;
import com.dxj.admin.domain.dto.RoleSmallDTO;
import com.dxj.admin.mapper.MenuMapper;
import com.dxj.admin.domain.query.CommonQuery;
import com.dxj.admin.repository.MenuRepository;
import com.dxj.common.exception.BadRequestException;
import com.dxj.common.exception.EntityExistException;
import com.dxj.common.util.BaseQuery;
import com.dxj.common.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author dxj
 * @date 2019-4-1
 */
@Service
@CacheConfig(cacheNames = "menu")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MenuService {

    private final MenuRepository menuRepository;

    private final MenuMapper menuMapper;

    @Autowired
    public MenuService(MenuRepository menuRepository, MenuMapper menuMapper) {
        this.menuRepository = menuRepository;
        this.menuMapper = menuMapper;
    }

    @Cacheable(key = "#p0")
    public MenuDTO findById(long id) {
        Optional<Menu> menu = menuRepository.findById(id);
        ValidationUtil.isNull(menu, "Menu", "id", id);
        return menuMapper.toDto(menu.orElse(null));
    }
    public List<MenuDTO> findByRoles(List<RoleSmallDTO> roles) {
        Set<Menu> menus = new LinkedHashSet<>();
        for (RoleSmallDTO role : roles) {
            List<Menu> menus1 = new ArrayList<>(menuRepository.findByRoles_IdOrderBySortAsc(role.getId()));
            menus.addAll(menus1);
        }
        return menus.stream().map(menuMapper::toDto).collect(Collectors.toList());
    }

    @CacheEvict(allEntries = true)
    public MenuDTO create(Menu resources) {
        if (menuRepository.findByName(resources.getName()) != null) {
            throw new EntityExistException(Menu.class, "name", resources.getName());
        }
        if (resources.getIFrame()) {
            if (!(resources.getPath().toLowerCase().startsWith("http://") || resources.getPath().toLowerCase().startsWith("https://"))) {
                throw new BadRequestException("外链必须以http://或者https://开头");
            }
        }
        return menuMapper.toDto(menuRepository.save(resources));
    }

    @CacheEvict(allEntries = true)
    public void update(Menu resources) {
        if (resources.getId().equals(resources.getPid())) {
            throw new BadRequestException("上级不能为自己");
        }
        Optional<Menu> optionalPermission = menuRepository.findById(resources.getId());
        ValidationUtil.isNull(optionalPermission, "Permission", "id", resources.getId());

        if (resources.getIFrame()) {
            if (!(resources.getPath().toLowerCase().startsWith("http://") || resources.getPath().toLowerCase().startsWith("https://"))) {
                throw new BadRequestException("外链必须以http://或者https://开头");
            }
        }
        Menu menu = optionalPermission.orElse(null);
        Menu menu1 = menuRepository.findByName(resources.getName());

        assert menu != null;
        if (menu1 != null && !menu1.getId().equals(menu.getId())) {
            throw new EntityExistException(Menu.class, "name", resources.getName());
        }
        menu.setName(resources.getName());
        menu.setComponent(resources.getComponent());
        menu.setPath(resources.getPath());
        menu.setIcon(resources.getIcon());
        menu.setIFrame(resources.getIFrame());
        menu.setPid(resources.getPid());
        menu.setSort(resources.getSort());
        menuRepository.save(menu);
    }

    @CacheEvict(allEntries = true)
    public void delete(Long id) {
        menuRepository.deleteById(id);
    }

    // 获取需要删除的菜单及子菜单
    public  List<Menu> treeList(List<Menu> menuList, Long id) {

        menuList.add(findOne(id));

        List<Menu> menu1 = findByPid(id);

        if (menu1 == null || menu1.size() == 0) {
            return menuList;
        }

        for(Menu menu2 : menu1) {
            treeList(menuList, menu2.getId());
        }
        return menuList;
    }

    @Cacheable(key = "'tree'")
    public List<Map<String, Object>> getMenuTree(List<Menu> menus) {
        List<Map<String, Object>> list = new LinkedList<>();
        menus.forEach(menu -> {
                    if (menu != null) {
                        List<Menu> menuList = menuRepository.findByPid(menu.getId());
                        Map<String, Object> map = new HashMap<>();
                        map.put("id", menu.getId());
                        map.put("label", menu.getName());
                        if (menuList != null && menuList.size() != 0) {
                            map.put("children", getMenuTree(menuList));
                        }
                        list.add(map);
                    }
                }
        );
        return list;
    }

    @Cacheable(key = "'pid:'+#p0")
    public List<Menu> findByPid(long pid) {
        return menuRepository.findByPid(pid);
    }

    public Map<String, Object> buildTree(List<MenuDTO> menuDTOs) {
        List<MenuDTO> trees = new ArrayList<>();

        for (MenuDTO menuDTO : menuDTOs) {

            if ("0".equals(menuDTO.getPid().toString())) {
                trees.add(menuDTO);
            }

            for (MenuDTO it : menuDTOs) {
                if (it.getPid().equals(menuDTO.getId())) {
                    if (menuDTO.getChildren() == null) {
                        menuDTO.setChildren(new ArrayList<>());
                    }
                    menuDTO.getChildren().add(it);
                }
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("content", trees.size() == 0 ? menuDTOs : trees);
        map.put("totalElements", menuDTOs.size());
        return map;
    }

    public List<MenuVo> buildMenus(List<MenuDTO> menuDTOS) {
        List<MenuVo> list = new LinkedList<>();
        menuDTOS.forEach(menuDTO -> {
                    if (menuDTO != null) {
                        List<MenuDTO> menuDTOList = menuDTO.getChildren();
                        MenuVo menuVo = new MenuVo();
                        menuVo.setName(menuDTO.getName());
                        menuVo.setPath(menuDTO.getPath());

                        // 如果不是外链
                        if (!menuDTO.getIFrame()) {
                            if (menuDTO.getPid().equals(0L)) {
                                //一级目录需要加斜杠，不然访问 会跳转404页面
                                menuVo.setPath("/" + menuDTO.getPath());
                                menuVo.setComponent(StrUtil.isEmpty(menuDTO.getComponent()) ? "Layout" : menuDTO.getComponent());
                            } else if (!StrUtil.isEmpty(menuDTO.getComponent())) {
                                menuVo.setComponent(menuDTO.getComponent());
                            }
                        }
                        menuVo.setMeta(new MenuMetaVo(menuDTO.getName(), menuDTO.getIcon()));
                        if (menuDTOList != null && menuDTOList.size() != 0) {
                            menuVo.setAlwaysShow(true);
                            menuVo.setRedirect("noredirect");
                            menuVo.setChildren(buildMenus(menuDTOList));
                            // 处理是一级菜单并且没有子菜单的情况
                        } else if (menuDTO.getPid().equals(0L)) {
                            MenuVo menuVo1 = new MenuVo();
                            menuVo1.setMeta(menuVo.getMeta());
                            // 非外链
                            if (!menuDTO.getIFrame()) {
                                menuVo1.setPath("index");
                                menuVo1.setName(menuVo.getName());
                                menuVo1.setComponent(menuVo.getComponent());
                            } else {
                                menuVo1.setPath(menuDTO.getPath());
                            }
                            menuVo.setName(null);
                            menuVo.setMeta(null);
                            menuVo.setComponent("Layout");
                            List<MenuVo> list1 = new ArrayList<>();
                            list1.add(menuVo1);
                            menuVo.setChildren(list1);
                        }
                        list.add(menuVo);
                    }
                }
        );
        return list;
    }

    public Menu findOne(Long id) {
        Optional<Menu> menu = menuRepository.findById(id);
        ValidationUtil.isNull(menu, "Menu", "id", id);
        return menu.orElse(null);
    }

    /**
     * 不分页
     */
    @Cacheable(key = "'queryAll:'+#p0")
    public List<MenuDTO> queryAll(CommonQuery query) {
        return menuMapper.toDto(menuRepository.findAll((root, criteriaQuery, criteriaBuilder) -> BaseQuery.getPredicate(root, query, criteriaBuilder)));
    }
}
