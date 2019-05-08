package com.dxj.service;

import cn.hutool.json.JSONObject;
import com.dxj.domain.LoginLog;
import com.dxj.repository.LoginLogRepository;
import com.dxj.utils.RequestHolder;
import com.dxj.utils.SecurityContextHolder;
import com.dxj.utils.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
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

        // 操作类型
        if (log != null) {
            log.setOperation(aopLog.value());
        }

        // agent
        String agent = request.getHeader("User-Agent");
        if (log != null) {
            log.setUserAgent(agent);
        }

        StringBuilder params = new StringBuilder("{");
        //参数值
        Object[] argValues = joinPoint.getArgs();
        //参数名称
        String[] argNames = ((MethodSignature)joinPoint.getSignature()).getParameterNames();
        // 用户名
        String username = "";

        if(argValues != null){
            for (int i = 0; i < argValues.length; i++) {
                params.append(" ").append(argNames[i]).append(": ").append(argValues[i]);
            }
        }

        // 获取IP地址
        assert log != null;
        log.setRequestIp(StringUtils.getIP(request));

        String LOGINPATH = "login";
        if(!LOGINPATH.equals(signature.getName())){
            UserDetails userDetails = SecurityContextHolder.getUserDetails();
            username = userDetails.getUsername();
        } else {
            try {
                JSONObject jsonObject = new JSONObject(argValues[0]);
                username = jsonObject.get("username").toString();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        log.setUsername(username);
        logRepository.save(log);
    }
}
