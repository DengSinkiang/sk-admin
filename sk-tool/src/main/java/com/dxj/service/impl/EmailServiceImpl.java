package com.dxj.service.impl;

import cn.hutool.extra.mail.Mail;
import cn.hutool.extra.mail.MailAccount;
import com.dxj.dao.EmailDao;
import com.dxj.domain.entity.EmailConfig;
import com.dxj.domain.vo.EmailVo;
import com.dxj.exception.SkException;
import com.dxj.service.EmailService;
import com.dxj.util.EncryptUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author Sinkiang
 * @date 2018-12-26
 */
@Service
@CacheConfig(cacheNames = "email")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class EmailServiceImpl implements EmailService {

    private final EmailDao emailDao;

    public EmailServiceImpl(EmailDao emailDao) {
        this.emailDao = emailDao;
    }

    @Override
    @CachePut(key = "'1'")
    @Transactional(rollbackFor = Exception.class)
    public EmailConfig update(EmailConfig emailConfig, EmailConfig old) {
        try {
            if(!emailConfig.getPass().equals(old.getPass())){
                // 对称加密
                emailConfig.setPass(EncryptUtils.desEncrypt(emailConfig.getPass()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return emailDao.save(emailConfig);
    }

    @Override
    @Cacheable(key = "'1'")
    public EmailConfig find() {
        Optional<EmailConfig> emailConfig = emailDao.findById(1L);
        return emailConfig.orElseGet(EmailConfig::new);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void send(EmailVo emailVo, EmailConfig emailConfig){
        if(emailConfig == null){
            throw new SkException("请先配置，再操作");
        }
        // 封装
        MailAccount account = new MailAccount();
        account.setHost(emailConfig.getHost());
        account.setPort(Integer.parseInt(emailConfig.getPort()));
        account.setAuth(true);
        try {
            // 对称解密
            account.setPass(EncryptUtils.desDecrypt(emailConfig.getPass()));
        } catch (Exception e) {
            throw new SkException(e.getMessage());
        }
        account.setFrom(emailConfig.getUser()+"<"+emailConfig.getFromUser()+">");
        // ssl方式发送
        account.setSslEnable(true);
        String content = emailVo.getContent();
        // 发送
        try {
            int size = emailVo.getTos().size();
            Mail.create(account)
                    .setTos(emailVo.getTos().toArray(new String[size]))
                    .setTitle(emailVo.getSubject())
                    .setContent(content)
                    .setHtml(true)
                    //关闭session
                    .setUseGlobalSession(false)
                    .send();
        }catch (Exception e){
            throw new SkException(e.getMessage());
        }
    }

}
