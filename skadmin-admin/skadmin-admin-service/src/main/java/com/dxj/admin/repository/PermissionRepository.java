package com.dxj.admin.repository;

import com.dxj.admin.domain.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author dxj
 * @date 2019-04-03
 */
public interface PermissionRepository extends JpaRepository<Permission, Long>, JpaSpecificationExecutor<Permission> {

    /**
     * findByName
     * @param name
     * @return
     */
    Permission findByName(String name);

    /**
     * findByPid
     * @param pid
     * @return
     */
    List<Permission> findByPid(long pid);

}
