package com.dxj.tool.service;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.dxj.common.util.IpInfoUtil;
import com.dxj.tool.domain.entity.SmsConfig;
import com.dxj.tool.domain.vo.SmsVo;
import com.dxj.common.exception.BadRequestException;
import com.dxj.tool.repository.SmsRepository;
import com.dxj.tool.util.SmsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @Author: dxj
 * @Date: 2019-05-10 15:02
 */
@Service
@Slf4j
@CacheConfig(cacheNames = "sms")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class SmsService {

    private final SmsRepository smsRepository;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private SmsUtil smsUtil;

    @Autowired
    private IpInfoUtil ipInfoUtil;

    @Autowired
    public SmsService(SmsRepository smsRepository) {
        this.smsRepository = smsRepository;
    }

    /**
     * 查询配置
     * @return
     */
    @Cacheable(key = "'1'")
    public SmsConfig find() {
        Optional<SmsConfig> smsConfig = smsRepository.findById(1L);
        return smsConfig.orElseGet(SmsConfig::new);
    }

    /**
     * 更新短信配置
     * @param smsConfig
     * @return
     */
    @CachePut(key = "'1'")
    @Transactional(rollbackFor = Exception.class)
    public SmsConfig update(SmsConfig smsConfig) {

        return smsRepository.save(smsConfig);
    }

    /**
     * 发送短信
     * @param smsVo
     * @param smsConfig
     * @throws Exception
     */
    @Async
    @Transactional(rollbackFor = Exception.class)
    public void send(SmsVo smsVo, SmsConfig smsConfig, HttpServletRequest request){

        if(smsConfig == null){
            throw new BadRequestException("请先配置，再操作");
        }

        try {
            SendSmsResponse response = smsUtil.sendCode("","", "");
            if (response.getCode() != null && ("OK").equals(response.getMessage())) {
                // 请求成功 标记限流
                redisTemplate.opsForValue().set("", "sended", 1L, TimeUnit.MINUTES);
            } else {
                log.error("请求发送验证码失败，" + response.getMessage());
            }
        } catch (ClientException e) {
            log.error("请求发送短信验证码失败，" + e);
        }


    }
}
