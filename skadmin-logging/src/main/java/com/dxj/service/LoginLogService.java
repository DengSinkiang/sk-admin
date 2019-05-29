package com.dxj.service;

import cn.hutool.json.JSONObject;
import com.dxj.domain.LoginLog;
import com.dxj.repository.LoginLogRepository;
import com.dxj.service.spec.LoginLogSpec;
import com.dxj.utils.RequestHolder;
import com.dxj.utils.SecurityContextHolder;
import com.dxj.utils.StringUtils;
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
 * @date 2018-11-24
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
        com.dxj.aop.log.LoginLog aopLog = method.getAnnotation(com.dxj.aop.log.LoginLog.class);

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

    public Page queryAll(LoginLog log, Pageable pageable){
        return logRepository.findAll(LoginLogSpec.getSpec(log), pageable);
    }
}
