package com.dxj.log.service;

import cn.hutool.json.JSONObject;
import com.dxj.common.util.IpInfoUtil;
import com.dxj.common.util.RequestHolder;
import com.dxj.common.util.SecurityContextHolder;
import com.dxj.log.entity.LoginLog;
import com.dxj.log.repository.LoginLogRepository;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dxj
 * @date 2019-04-24
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class LoginLogService {

    private final LoginLogRepository logRepository;

    @Autowired
    public LoginLogService(LoginLogRepository logRepository) {
        this.logRepository = logRepository;
    }

    /**
     * 新增日志
     *
     * @param joinPoint
     * @param log
     */
    @Async
    @Transactional(rollbackFor = Exception.class)
    public void save(ProceedingJoinPoint joinPoint, LoginLog log) {

        // 获取request
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        com.dxj.log.annotation.LoginLog aopLog = method.getAnnotation(com.dxj.log.annotation.LoginLog.class);

        assert log != null;
        // 操作类型
        log.setOperation(aopLog.value());

        // agent
        String agent = request.getHeader("User-Agent");
        log.setUserAgent(agent);

        // 用户名
        String username = "";

        // 获取IP地址

        log.setRequestIp(IpInfoUtil.getIpAddr(request));

        if (!"login".equals(signature.getName())) {
            UserDetails userDetails = SecurityContextHolder.getUserDetails();
            username = userDetails.getUsername();
        } else {
            try {
                username = new JSONObject(joinPoint.getArgs()[0]).get("username").toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // username
        log.setUsername(username);
        logRepository.save(log);
    }

    public Page<LoginLog> queryAll(LoginLog log, String timeRange, Pageable pageable) {
        return logRepository.findAll(getSpec(log, timeRange), pageable);
    }

    /**
     * 查询条件
     *
     * @param log
     * @param timeRange
     * @return
     */
    private static Specification<LoginLog> getSpec(LoginLog log, String timeRange) {
        return (Specification<LoginLog>) (root, query, cb) -> {
            List<Predicate> list = new ArrayList<>();

            if (!ObjectUtils.isEmpty(log.getUsername())) {
                list.add(cb.like(root.get("username").as(String.class), "%" + log.getUsername() + "%"));
            }

            if (!ObjectUtils.isEmpty(log.getLogType())) {
                list.add(cb.equal(root.get("logType").as(String.class), log.getLogType()));
            }
            if (!ObjectUtils.isEmpty(timeRange)) {

                list.add(cb.between(root.get("createTime").as(String.class), timeRange.split("\\|")[0], timeRange.split("\\|")[1]));
            }
            Predicate[] p = new Predicate[list.size()];
            return cb.and(list.toArray(p));
        };
    }

}
