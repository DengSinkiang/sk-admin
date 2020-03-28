package com.dxj.dao;

import com.dxj.base.BaseDao;
import com.dxj.domain.entity.Picture;

/**
 * @author Sinkiang
 * @date 2018-12-27
 */
public interface PictureDao extends BaseDao<Picture, Long> {

    /**
     * 根据 Mds 值查询文件
     * @param code 值
     * @return /
     */
    Picture findByMd5Code(String code);

    /**
     * 根据连接地址查询
     * @param url /
     * @return /
     */
    boolean existsByUrl(String url);
}
