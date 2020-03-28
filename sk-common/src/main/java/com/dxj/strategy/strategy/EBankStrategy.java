package com.dxj.strategy.strategy;

/**
 * @AUTHOR: sinkiang
 * @DATE: 2020-01-19 18:52
 */

import com.dxj.strategy.RechargeTypeEnum;
import com.dxj.strategy.strategy.Strategy;

/**
 *
 *作者：alaric
 *时间：2013-8-5上午11:14:23
 *描述：网银充值
 */
public class EBankStrategy implements Strategy {

    @Override
    public Double calRecharge(Double charge, RechargeTypeEnum type) {
        return charge*0.85;
    }



}
