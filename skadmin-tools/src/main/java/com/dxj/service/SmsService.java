package com.dxj.service;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.dxj.domain.SmsConfig;;
import com.dxj.domain.vo.SmsVo;
import com.dxj.exception.BadRequestException;
import com.dxj.repository.SmsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @Author: dxj
 * @Date: 2019-05-10 15:02
 */
@Service
@CacheConfig(cacheNames = "sms")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class SmsService {

    private final SmsRepository smsRepository;

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
     * @param old
     * @return
     */
    @CachePut(key = "'1'")
    @Transactional(rollbackFor = Exception.class)
    public SmsConfig update(SmsConfig smsConfig, SmsConfig old) {

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
    public void send(SmsVo smsVo, SmsConfig smsConfig){

        if(smsConfig == null){
            throw new BadRequestException("请先配置，再操作");
        }

        DefaultProfile profile = DefaultProfile.getProfile("default", "<accessKeyId>", "<accessSecret>");
        IAcsClient client = new DefaultAcsClient(profile);
        String phoneNums= String.join(",", smsVo.getTos());
        System.out.println(smsVo.getContent());
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

        request.putQueryParameter("PhoneNumbers", phoneNums);
        request.putQueryParameter("SignName", smsConfig.getSignName());
        request.putQueryParameter("TemplateCode", smsConfig.getTemplateCode());
        request.putQueryParameter("TemplateParam", smsVo.getContent());
        try {
            CommonResponse response = client.getCommonResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }

    }
}
