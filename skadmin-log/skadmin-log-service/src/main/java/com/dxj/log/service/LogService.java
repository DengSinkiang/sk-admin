package com.dxj.log.service;

import cn.hutool.core.lang.Dict;
import cn.hutool.json.JSONObject;
import com.dxj.common.util.*;
import com.dxj.log.entity.Log;
import com.dxj.log.repository.LogRepository;
import com.dxj.log.mapper.LogErrorMapper;
import com.dxj.log.mapper.LogSmallMapper;
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
public class LogService {

    private final LogRepository logRepository;

    private final LogSmallMapper logSmallMapper;

    private final LogErrorMapper logErrorMapper;


    @Autowired
    public LogService(LogRepository logRepository, LogSmallMapper logSmallMapper, LogErrorMapper logErrorMapper) {
        this.logRepository = logRepository;
        this.logSmallMapper = logSmallMapper;
        this.logErrorMapper = logErrorMapper;
    }

    /**
     * 新增日志
     *
     * @param joinPoint
     * @param log
     */
    @Async
    @Transactional(rollbackFor = Exception.class)
    public void save(ProceedingJoinPoint joinPoint, Log log) {

        // 获取request
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        com.dxj.log.annotation.Log aopLog = method.getAnnotation(com.dxj.log.annotation.Log.class);
        assert log != null;
        // 描述
        if (aopLog != null) {
            log.setDescription(aopLog.value());
        }


        // 方法路径
        String methodName = joinPoint.getTarget().getClass().getName() + "." + signature.getName() + "()";

        StringBuilder params = new StringBuilder("{");
        //参数值
        Object[] argValues = joinPoint.getArgs();
        //参数名称
        String[] argNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        // 用户名
        String username = "";

        assert argValues != null;
        for (int i = 0; i < argValues.length; i++) {
            params.append(" ").append(argNames[i]).append(": ").append(argValues[i]);
        }

        // 获取IP地址
        log.setRequestIp(IpInfoUtil.getIpAddr(request));

        String loginPath = "login";
        if (!loginPath.equals(signature.getName())) {
            UserDetails userDetails = SecurityContextHolder.getUserDetails();
            username = userDetails.getUsername();
        } else {
            try {
                username = new JSONObject(argValues[0]).get("username").toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        log.setMethod(methodName);
        log.setUsername(username);
        log.setParams(params + " }");
        logRepository.save(log);
    }

    public Dict findByErrDetail(Long id) {
        return Dict.create().set("exception", logRepository.findExceptionById(id));
    }

    public Object queryAll(Log logQuery, String timeRange, Pageable pageable) {

        Page<Log> page = logRepository.findAll(getSpec(logQuery, timeRange), pageable);
        if (logQuery.getLogType().equals("ERROR")) {
            return PageUtil.toPage(page.map(logErrorMapper::toDto));
        }

        return page;
    }

    /**
     * 查询条件
     *
     * @param log
     * @param timeRange
     * @return
     */
    private static Specification<Log> getSpec(Log log, String timeRange) {
        return (Specification<Log>) (root, query, cb) -> {
            List<Predicate> list = new ArrayList<>();

            if (!ObjectUtils.isEmpty(log.getUsername())) {
                list.add(cb.like(root.get("username").as(String.class), "%" + log.getUsername() + "%"));
            }
            if (!ObjectUtils.isEmpty(log.getLogType())) {
                list.add(cb.equal(root.get("logType").as(String.class), log.getLogType()));
            }
            if (!ObjectUtils.isEmpty(timeRange)) {
                String startTime = timeRange.split("\\|")[0];
                String endTime = timeRange.split("\\|")[1];

                //起始日期
                list.add(cb.greaterThanOrEqualTo(root.get("createTime").as(String.class), startTime));
                //结束日期
                list.add(cb.lessThanOrEqualTo(root.get("createTime").as(String.class), endTime));
            }
            Predicate[] p = new Predicate[list.size()];
            return cb.and(list.toArray(p));
        };
    }

    public Object queryAllByUser(Log log, Pageable pageable) {
        Page<Log> page = logRepository.findAll(((root, criteriaQuery, cb) -> BaseQuery.getPredicate(root, log, cb)), pageable);
        return PageUtil.toPage(page.map(logSmallMapper::toDto));
    }
}
