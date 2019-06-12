package com.dxj.log.aspect;

import com.dxj.common.util.ThrowableUtils;
import com.dxj.log.domain.Log;
import com.dxj.log.domain.LoginLog;
import com.dxj.log.service.LogService;
import com.dxj.log.service.LoginLogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author dxj
 * @date 2019-04-24
 */
@Component
@Aspect //把当前类声明为切面类
@Slf4j
public class DataScopeAspect {

    private final LogService logService;
    private final LoginLogService loginLogService;

    private long currentTime = 0L;

    @Autowired
    public DataScopeAspect(LogService logService, LoginLogService loginLogService) {
        this.logService = logService;
        this.loginLogService = loginLogService;
    }

    /**
     * 配置切入点
     * 该方法无方法体,主要为了让同类中其他方法使用此切入点
     */
    @Pointcut("@annotation(com.dxj.log.annotation.Log)")
    public void logPointcut() {}

    //登录日志
    //指定切入点表达式
    @Pointcut("@annotation(com.dxj.log.annotation.LoginLog)")
    public void loginLogPointcut() {
        // 该方法无方法体,主要为了让同类中其他方法使用此切入点
    }

    /**
     * 配置环绕通知,使用在方法logPointcut()上注册的切入点
     *
     * @param joinPoint
     */
    @Around("logPointcut()") //把当前方法看成是环绕通知。
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {

        currentTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        Log log = new Log("INFO",System.currentTimeMillis() - currentTime);
        logService.save(joinPoint, log);
        return result;
    }

    //登录日志
    @Around("loginLogPointcut()")
    public Object loginLogAround(ProceedingJoinPoint joinPoint) throws Throwable {
        currentTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        //操作日志
        LoginLog loginLog = new LoginLog("INFO",System.currentTimeMillis() - currentTime);
        loginLogService.save(joinPoint, loginLog);
        return result;
    }

    /**
     * 配置异常通知
     *
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(pointcut = "logPointcut()", throwing = "e") // 把当前方法看成是异常通知。
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        currentTime = System.currentTimeMillis();
        //异常日志
        Log log = new Log("ERROR",System.currentTimeMillis() - currentTime);
        log.setExceptionDetail(ThrowableUtils.getStackTrace(e));
        logService.save((ProceedingJoinPoint)joinPoint, log);

    }
    /**
     * 配置登录异常通知
     *
     * @param joinPoint
     */
    @AfterThrowing(pointcut = "loginLogPointcut()", throwing = "e")
    public void loginLogAfterThrowing(JoinPoint joinPoint, Throwable e) {

        long time = System.currentTimeMillis() - currentTime;

        //登录异常日志
        LoginLog loginLog = new LoginLog("ERROR", time);
        loginLogService.save((ProceedingJoinPoint)joinPoint, loginLog);

        //同时记录在异常日志中
        Log log = new Log("ERROR", time);
        log.setDescription("登录");
        log.setExceptionDetail(ThrowableUtils.getStackTrace(e));
        logService.save((ProceedingJoinPoint)joinPoint, log);

    }
}
