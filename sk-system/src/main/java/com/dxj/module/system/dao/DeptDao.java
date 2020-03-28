package com.dxj.module.system.dao;

import com.dxj.base.BaseDao;
import com.dxj.module.system.domain.entity.Dept;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

/**
* @author Sinkiang
* @date 2019-03-25
*/
@SuppressWarnings("all")
public interface DeptDao extends BaseDao<Dept, Long> {

    /**
     * 根据 PID 查询
     * @param id pid
     * @return /
     */
    List<Dept> findByPid(Long id);

    /**
     * 根据ID查询名称
     * @param id ID
     * @return /
     */
    @Query(value = "select name from dept where id = ?1",nativeQuery = true)
    String findNameById(Long id);

    /**
     * 根据角色ID 查询
     * @param id 角色ID
     * @return /
     */
    Set<Dept> findByRoles_Id(Long id);
}
