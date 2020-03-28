package com.dxj.dao;

import com.dxj.base.BaseDao;
import com.dxj.domain.entity.VerificationCode;

/**
 * @author Sinkiang
 * @date 2018-12-26
 */
public interface VerificationCodeDao extends BaseDao<VerificationCode, Long> {

    /**
     * 获取有效的验证码
     * @param scenes 业务场景，如重置密码，重置邮箱等等
     * @param type 类型
     * @param value 值
     * @return VerificationCode
     */
    VerificationCode findByScenesAndTypeAndValueAndStatusIsTrue(String scenes, String type, String value);
}
