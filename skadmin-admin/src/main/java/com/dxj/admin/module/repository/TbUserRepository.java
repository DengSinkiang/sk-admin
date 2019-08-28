package com.dxj.admin.module.repository;

import com.dxj.admin.module.domain.TbUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author sinkiang
* @date 2019-08-28
*/
public interface TbUserRepository extends JpaRepository<TbUser, Integer>, JpaSpecificationExecutor {
}