package com.dxj.service.impl;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import com.dxj.dao.LogDao;
import com.dxj.domain.entity.Log;
import com.dxj.domain.dto.LogQuery;
import com.dxj.domain.mapper.LogErrorMapper;
import com.dxj.domain.mapper.LogSmallMapper;
import com.dxj.service.LogService;
import com.dxj.util.*;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Sinkiang
 * @date 2018-11-24
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class LogServiceImpl implements LogService {

    private final LogDao logDao;

    private final LogErrorMapper logErrorMapper;

    private final LogSmallMapper logSmallMapper;

    public LogServiceImpl(LogDao logDao, LogErrorMapper logErrorMapper, LogSmallMapper logSmallMapper) {
        this.logDao = logDao;
        this.logErrorMapper = logErrorMapper;
        this.logSmallMapper = logSmallMapper;
    }

    @Override
    public Object queryAll(LogQuery criteria, Pageable pageable) {
        Page<Log> page = logDao.findAll(((root, criteriaQuery, cb) -> QueryHelp.getPredicate(root, criteria, cb)), pageable);
        String status = "ERROR";
        if (status.equals(criteria.getLogType())) {
            return PageUtil.toPage(page.map(logErrorMapper::toDto));
        }
        return page;
    }

    @Override
    public List<Log> queryAll(LogQuery criteria) {
        return logDao.findAll(((root, criteriaQuery, cb) -> QueryHelp.getPredicate(root, criteria, cb)));
    }

    @Override
    public Object queryAllByUser(LogQuery criteria, Pageable pageable) {
        Page<Log> page = logDao.findAll(((root, criteriaQuery, cb) -> QueryHelp.getPredicate(root, criteria, cb)), pageable);
        return PageUtil.toPage(page.map(logSmallMapper::toDto));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(String username, String browser, String ip, ProceedingJoinPoint joinPoint, Log log) {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        com.dxj.annotation.Log aopLog = method.getAnnotation(com.dxj.annotation.Log.class);

        // 方法路径
        String methodName = joinPoint.getTarget().getClass().getName() + "." + signature.getName() + "()";

        StringBuilder params = new StringBuilder("{");
        //参数值
        Object[] argValues = joinPoint.getArgs();
        //参数名称
        String[] argNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        if (argValues != null) {
            for (int i = 0; i < argValues.length; i++) {
                params.append(" ").append(argNames[i]).append(": ").append(argValues[i]);
            }
        }
        // 描述
        if (log != null) {
            log.setDescription(aopLog.value());
        }
        assert log != null;
        log.setRequestIp(ip);

        String loginPath = "login";
        if (loginPath.equals(signature.getName())) {
            try {
                assert argValues != null;
                username = new JSONObject(argValues[0]).get("username").toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        log.setAddress(StringUtils.getCityInfo(log.getRequestIp()));
        log.setMethod(methodName);
        log.setUsername(username);
        log.setParams(params.toString() + " }");
        log.setBrowser(browser);
        logDao.save(log);
    }

    @Override
    public Object findByErrDetail(Long id) {
        Log log = logDao.findById(id).orElseGet(Log::new);
        ValidationUtil.isNull(log.getId(), "Log", "id", id);
        byte[] details = log.getExceptionDetail();
        return Dict.create().set("exception", new String(ObjectUtil.isNotNull(details) ? details : "".getBytes()));
    }

    @Override
    public void download(List<Log> logs, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (Log log : logs) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("用户名", log.getUsername());
            map.put("IP", log.getRequestIp());
            map.put("IP来源", log.getAddress());
            map.put("描述", log.getDescription());
            map.put("浏览器", log.getBrowser());
            map.put("请求耗时/毫秒", log.getTime());
            map.put("异常详情", new String(ObjectUtil.isNotNull(log.getExceptionDetail()) ? log.getExceptionDetail() : "".getBytes()));
            map.put("创建日期", log.getCreateTime());
            list.add(map);
        }
        FileUtils.downloadExcel(list, response);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delAllByError() {
        logDao.deleteByLogType("ERROR");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delAllByInfo() {
        logDao.deleteByLogType("INFO");
    }
}
