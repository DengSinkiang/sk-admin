package com.dxj.tools.controller;

import com.dxj.tools.domain.SmsConfig;
import com.dxj.tools.domain.vo.SmsVo;
import com.dxj.tools.service.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: dxj
 * @Date: 2019-05-10 14:39
 */

@Slf4j
@RestController
@RequestMapping("api")
public class SmsController {

    private final SmsService smsService;

    @Autowired
    public SmsController(SmsService smsService) {
        this.smsService = smsService;
    }

    /**
     * 获取短信配置
     * @return
     */
    @GetMapping(value = "/sms")
    public ResponseEntity<SmsConfig> get() {
        return new ResponseEntity(smsService.find(), HttpStatus.OK);
    }

    /**
     * 短信配置
     * @param smsConfig
     * @return
     */
    @PutMapping(value = "/sms")
    public ResponseEntity<SmsConfig> configSms(@Validated @RequestBody SmsConfig smsConfig) {
        System.out.println("真真正正");

        smsService.update(smsConfig);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 发送短信
     * @param smsVo
     * @return
     */
    @PostMapping(value = "/sms")
    public ResponseEntity<Void> send(@Validated @RequestBody SmsVo smsVo) {
        log.warn("REST request to send Sms : {}" +smsVo);
        smsService.send(smsVo, smsService.find());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
