package com.dxj.module.system.dao;

import com.dxj.base.BaseDao;
import com.dxj.module.system.domain.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Sinkiang
 * @date 2018-12-17
 */
@SuppressWarnings("all")
public interface MenuDao extends BaseDao<Menu, Long> {

    /**
     * 根据菜单名称查询
     * @param name 菜单名称
     * @return /
     */
    Menu findByName(String name);

    /**
     * 根据组件名称查询
     * @param name 组件名称
     * @return /
     */
    Menu findByComponentName(String name);

    /**
     * 根据菜单的 PID 查询
     * @param pid /
     * @return /
     */
    List<Menu> findByPid(long pid);

    /**
     * 根据角色ID与菜单类型查询菜单
     * @param roleIds roleIDs
     * @param type 类型
     * @return /
     */
    LinkedHashSet<Menu> findByRoles_IdInAndTypeNotOrderBySortAsc(Set<Long> roleIds, int type);
}
