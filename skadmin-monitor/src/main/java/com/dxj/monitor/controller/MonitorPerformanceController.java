package com.dxj.monitor.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 性能监控
 *
 * @author:Sinkiang
 * @date :2019/09/17 13:37
 */
@Controller
@RequestMapping("api/monitor/performance")
public class MonitorPerformanceController {

    @Value("api")
    private String apiPrefix;

    private static final String forwardPath = "forward:/actuator/metrics/";

    private static List<String> systemParamsList = new ArrayList<>();

    private static List<String> jvmParamsList = new ArrayList<>();

    private static List<String> tomcatParamsList = new ArrayList<>();

    static {
        systemParamsList.add("system.cpu.count");
        systemParamsList.add("system.cpu.usage");
        systemParamsList.add("process.start.time");
        systemParamsList.add("process.uptime");
        systemParamsList.add("process.cpu.usage");

        jvmParamsList.add("jvm.memory.max");
        jvmParamsList.add("jvm.memory.committed");
        jvmParamsList.add("jvm.memory.used");
        jvmParamsList.add("jvm.buffer.memory.used");
        jvmParamsList.add("jvm.buffer.count");
        jvmParamsList.add("jvm.threads.daemon");
        jvmParamsList.add("jvm.threads.live");
        jvmParamsList.add("jvm.threads.peak");
        jvmParamsList.add("jvm.classes.loaded");
        jvmParamsList.add("jvm.classes.unloaded");
        jvmParamsList.add("jvm.gc.memory.allocated");
        jvmParamsList.add("jvm.gc.memory.promoted");
        jvmParamsList.add("jvm.gc.max.data.size");
        jvmParamsList.add("jvm.gc.live.data.size");
        jvmParamsList.add("jvm.gc.pause");

        tomcatParamsList.add("tomcat.sessions.created");
        tomcatParamsList.add("tomcat.sessions.expired");
        tomcatParamsList.add("tomcat.sessions.active.current");
        tomcatParamsList.add("tomcat.sessions.active.max");
        tomcatParamsList.add("tomcat.sessions.rejected");
        tomcatParamsList.add("tomcat.global.sent");
        tomcatParamsList.add("tomcat.global.request.max");
        tomcatParamsList.add("tomcat.global.request");
        tomcatParamsList.add("tomcat.servlet.request");
        tomcatParamsList.add("tomcat.servlet.request.max");
        tomcatParamsList.add("tomcat.threads.current");
        tomcatParamsList.add("tomcat.threads.config.max");
    }

    @GetMapping("/system/{actuator}")
    public ResponseEntity<String> system(@PathVariable String actuator) {
        return getStringResponseEntity(actuator, systemParamsList);
    }

    @GetMapping("/jvm/{actuator}")
    public ResponseEntity<String> jvm(@PathVariable String actuator) {
        return getStringResponseEntity(actuator);
    }

    private ResponseEntity<String> getStringResponseEntity(@PathVariable String actuator) {
        return getStringResponseEntity(actuator, jvmParamsList);
    }

    private ResponseEntity<String> getStringResponseEntity(@PathVariable String actuator, List<String> jvmParamsList) {
        String forwardApi = "forward:" + apiPrefix + "/monitor/performance/error";
        if (validateChain(jvmParamsList, actuator)) {
            forwardApi = forwardPath + actuator;
        }
        return new ResponseEntity<>(forwardApi, HttpStatus.OK);
    }

    @GetMapping("/tomcat/{actuator}")
    public ResponseEntity<String> tomcat(@PathVariable String actuator) {
        return getStringResponseEntity(actuator, tomcatParamsList);
    }

    @GetMapping("/error")
    @ResponseBody
    public ResponseEntity<String> error() {
        return new ResponseEntity<>("当前监控请求参数无授权", HttpStatus.BAD_REQUEST);
    }

    private boolean validateChain(List<String> paramsList, String actuator) {
        return paramsList.stream().anyMatch(actuator::equals);
    }
}
