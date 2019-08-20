package com.dxj.social.repository;

import com.dxj.social.domain.Github;
import org.springframework.stereotype.Repository;

/**
 * @AUTHOR: sinkiang
 * @DATE: 2019-08-02 19:00
 */
@Repository
public interface GithubRepository extends BaseRepository<Github, String> {

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
     * 通过username删除
     *
     * @param username
     */
    void deleteByUsername(String username);
}
