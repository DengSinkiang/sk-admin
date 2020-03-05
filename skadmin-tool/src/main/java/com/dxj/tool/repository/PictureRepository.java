package com.dxj.tool.repository;

import com.dxj.tool.domain.entity.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author dxj
 * @date 2019-04-27
 */
public interface PictureRepository extends JpaRepository<Picture, Long>, JpaSpecificationExecutor<Picture> {
}
