package com.dxj.service.impl;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;
import com.dxj.dao.VerificationCodeDao;
import com.dxj.domain.entity.VerificationCode;
import com.dxj.domain.vo.EmailVo;
import com.dxj.exception.SkException;
import com.dxj.service.VerificationCodeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Sinkiang
 * @date 2018-12-26
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class VerificationCodeServiceImpl implements VerificationCodeService {

    private final VerificationCodeDao verificationCodeDao;

    @Value("${code.expiration}")
    private Integer expiration;

    public VerificationCodeServiceImpl(VerificationCodeDao verificationCodeDao) {
        this.verificationCodeDao = verificationCodeDao;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public EmailVo sendEmail(VerificationCode code) {
        EmailVo emailVo;
        String content;
        VerificationCode verificationCode = verificationCodeDao.findByScenesAndTypeAndValueAndStatusIsTrue(code.getScenes(), code.getType(), code.getValue());
        // 如果不存在有效的验证码，就创建一个新的
        TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig("template", TemplateConfig.ResourceMode.CLASSPATH));
        Template template = engine.getTemplate("email/email.ftl");
        if (verificationCode == null) {
            code.setCode(RandomUtil.randomNumbers(6));
            content = template.render(Dict.create().set("code", code.getCode()));
            emailVo = new EmailVo(Collections.singletonList(code.getValue()), "eladmin后台管理系统", content);
            timedDestruction(verificationCodeDao.save(code));
            // 存在就再次发送原来的验证码
        } else {
            content = template.render(Dict.create().set("code", verificationCode.getCode()));
            emailVo = new EmailVo(Collections.singletonList(verificationCode.getValue()), "eladmin后台管理系统", content);
        }
        return emailVo;
    }

    @Override
    public void validated(VerificationCode code) {
        VerificationCode verificationCode = verificationCodeDao.findByScenesAndTypeAndValueAndStatusIsTrue(code.getScenes(), code.getType(), code.getValue());
        if (verificationCode == null || !verificationCode.getCode().equals(code.getCode())) {
            throw new SkException("无效验证码");
        } else {
            verificationCode.setStatus(false);
            verificationCodeDao.save(verificationCode);
        }
    }

    /**
     * 定时任务，指定分钟后改变验证码状态
     *
     * @param verifyCode 验证码
     */
    private void timedDestruction(VerificationCode verifyCode) {
        //以下示例为程序调用结束继续运行
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        try {
            executorService.schedule(() -> {
                verifyCode.setStatus(false);
                verificationCodeDao.save(verifyCode);
            }, expiration * 60 * 1000L, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
