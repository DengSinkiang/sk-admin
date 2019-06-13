package com.dxj.tool.controller;

import com.dxj.tool.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import com.dxj.log.annotation.Log;
import com.dxj.tool.domain.EmailConfig;
import com.dxj.tool.domain.vo.EmailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 发送邮件
 * @author dxj
 * @date 2019/04/28 6:55:53
 */
@Slf4j
@RestController
@RequestMapping("api")
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping(value = "/email")
    public ResponseEntity<EmailConfig> get(){
        return new ResponseEntity<>(emailService.find(),HttpStatus.OK);
    }

    @Log("配置邮件")
    @PutMapping(value = "/email")
    public ResponseEntity<EmailConfig> emailConfig(@Validated @RequestBody EmailConfig emailConfig){
        emailService.update(emailConfig);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Log("发送邮件")
    @PostMapping(value = "/email")
    public ResponseEntity<Void> send(@Validated @RequestBody EmailVo emailVo) {
        log.warn("REST request to send Email : {}" +emailVo);
        emailService.send(emailVo, emailService.find());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
