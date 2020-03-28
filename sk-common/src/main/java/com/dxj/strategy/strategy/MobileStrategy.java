package com.dxj.strategy.strategy;

/**
 * @AUTHOR: sinkiang
 * @DATE: 2020-01-19 18:54
 */

import com.dxj.strategy.RechargeTypeEnum;
import com.dxj.strategy.strategy.Strategy;

/**
 *
 *作者：alaric
 *时间：2013-8-5上午11:14:43
 *描述：手机充值
 */
public class MobileStrategy implements Strategy {

    @Override
    public Double calRecharge(Double charge, RechargeTypeEnum type) {
        // TODO Auto-generated method stub
        return charge;
    }

}
