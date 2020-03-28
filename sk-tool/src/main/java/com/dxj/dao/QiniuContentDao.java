package com.dxj.dao;

import com.dxj.base.BaseDao;
import com.dxj.domain.entity.QiniuContent;

/**
 * @author Sinkiang
 * @date 2018-12-31
 */
public interface QiniuContentDao extends BaseDao<QiniuContent, Long> {

    /**
     * 根据key查询
     *
     * @param key 文件名
     * @return QiniuContent
     */
    QiniuContent findByKey(String key);
}
