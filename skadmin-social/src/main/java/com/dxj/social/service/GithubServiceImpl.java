package com.dxj.social.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.dxj.social.domain.Github;
import com.dxj.social.repository.GithubRepository;
import com.dxj.social.vo.SearchVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @AUTHOR: sinkiang
 * @DATE: 2019-08-02 19:50
 */
@Slf4j
@Service
@Transactional
public class GithubServiceImpl implements GithubService {

    @Autowired
    private GithubRepository githubRepository;

    @Override
    public GithubRepository getRepository() {
        return githubRepository;
    }

    @Override
    public Github findByOpenId(String openId) {

        return githubRepository.findByOpenId(openId);
    }

    @Override
    public Github findByRelateUsername(String username) {

        return githubRepository.findByRelateUsername(username);
    }

    @Override
    public Page<Github> findByCondition(String username, String relateUsername, SearchVo searchVo, Pageable pageable) {
        return githubRepository.findAll(getSpec(username, relateUsername, searchVo), pageable);
    }

    private Specification<Github> getSpec(String username, String relateUsername, SearchVo searchVo) {
        return (Specification<Github>) (root, query, cb) -> {

            Path<String> usernameField = root.get("username");
            Path<String> relateUsernameField = root.get("relateUsername");
            Path<Date> createTimeField=root.get("createTime");
            List<Predicate> list = new ArrayList<>();

            if (!StrUtil.isNotBlank(username)) {
                list.add(cb.like(usernameField, "%" + username + "%"));
            }

            if (!StrUtil.isNotBlank(relateUsername)) {
                list.add(cb.like(relateUsernameField,'%'+ relateUsername + '%'));
            }
            //创建时间
            if(StrUtil.isNotBlank(searchVo.getStartDate())&&StrUtil.isNotBlank(searchVo.getEndDate())){
                Date start = DateUtil.parse(searchVo.getStartDate());
                Date end = DateUtil.parse(searchVo.getEndDate());
                list.add(cb.between(createTimeField, start, DateUtil.endOfDay(end)));
            }
            Predicate[] p = new Predicate[list.size()];
            return cb.and(list.toArray(p));
        };
    }

    @Override
    public void deleteByUsername(String username) {

        githubRepository.deleteByUsername(username);
    }
}
