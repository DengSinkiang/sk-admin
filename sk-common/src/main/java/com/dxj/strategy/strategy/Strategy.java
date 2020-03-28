package com.dxj.strategy.strategy;

import com.dxj.strategy.RechargeTypeEnum;

/**
 * @AUTHOR: sinkiang
 * @DATE: 2020-01-19 18:51
 *描述：策略抽象类
 */
public interface Strategy {

    /**
     *
     *作者：alaric
     *时间：2013-8-5上午11:05:11
     *描述：策略行为方法
     */
    public Double calRecharge(Double charge , RechargeTypeEnum type );
}
