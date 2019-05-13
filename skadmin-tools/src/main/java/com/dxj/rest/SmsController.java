package com.dxj.rest;

import com.dxj.domain.SmsConfig;
import com.dxj.domain.vo.SmsVo;
import com.dxj.service.SmsService;
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

    @GetMapping(value = "/sms")
    public ResponseEntity<SmsConfig> get() {
        return new ResponseEntity(smsService.find(), HttpStatus.OK);
    }
    @PutMapping(value = "/sms")
    public ResponseEntity<SmsConfig> configSms(@Validated @RequestBody SmsConfig smsConfig) {
        smsService.update(smsConfig, smsService.find());
        return new ResponseEntity(HttpStatus.OK);
    }
    @PostMapping(value = "/sms")
    public ResponseEntity<Void> send(@Validated @RequestBody SmsVo smsVo) {
        log.warn("REST request to send Sms : {}" +smsVo);
        smsService.send(smsVo, smsService.find());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
