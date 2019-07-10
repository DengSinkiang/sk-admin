package com.dxj.log.service;

import cn.hutool.json.JSONObject;
import com.dxj.common.util.BaseQuery;
import com.dxj.common.util.RequestHolder;
import com.dxj.common.util.SecurityContextHolder;
import com.dxj.common.util.StringUtils;
import com.dxj.log.domain.LoginLog;
import com.dxj.log.repository.LoginLogRepository;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

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
     * @param joinPoint
     * @param log
     */
    @Async
    @Transactional(rollbackFor = Exception.class)
    public void save(ProceedingJoinPoint joinPoint, LoginLog log){

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

        log.setRequestIp(StringUtils.getIP(request));

        if(!"login".equals(signature.getName())){
            UserDetails userDetails = SecurityContextHolder.getUserDetails();
            username = userDetails.getUsername();
        } else {
            try {
                username = new JSONObject(joinPoint.getArgs()[0]).get("username").toString();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        // username
        log.setUsername(username);
        logRepository.save(log);
    }

    public Page<LoginLog> queryAll(LoginLog log, String startDate, String endDate, Pageable pageable){
        if (startDate != null && !startDate.trim().equals("") && endDate != null && !endDate.trim().equals("")) {
            return logRepository.findLoginLog(startDate, endDate, pageable);
        }
        return logRepository.findAll(((root, criteriaQuery, cb) -> BaseQuery.getPredicate(root, log, cb)),pageable);
    }
}
