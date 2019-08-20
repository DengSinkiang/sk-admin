package com.dxj.social.service;

import com.dxj.social.domain.Github;
import com.dxj.social.vo.SearchVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Github登录接口
 *
 * @AUTHOR: sinkiang
 * @DATE: 2019-08-02 18:57
 */

public interface GithubService extends BaseService<Github, String> {

    /**
     * 通过openId获取
     *
     * @param openId
     * @return
     */
    Github findByOpenId(String openId);

    /**
     * 通过username获取
     *
     * @param username
     * @return
     */
    Github findByRelateUsername(String username);

    /**
     * 分页多条件获取
     *
     * @param username
     * @param relateUsername
     * @param searchVo
     * @param pageable
     * @return
     */
    Page<Github> findByCondition(String username, String relateUsername, SearchVo searchVo, Pageable pageable);



    /**
     * 通过username删除
     *
     * @param username
     */
    void deleteByUsername(String username);
}

