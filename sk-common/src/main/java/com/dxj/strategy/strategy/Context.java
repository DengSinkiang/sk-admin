package com.dxj.strategy.strategy;

import com.dxj.strategy.RechargeTypeEnum;

/**
 * @AUTHOR: sinkiang
 * @DATE: 2020-01-19 18:55
 */
public class Context {

    private Strategy strategy;

    public Double calRecharge(Double charge, Integer type) {
        strategy = StrategyFactory.getInstance().creator(type);
        return strategy.calRecharge(charge, RechargeTypeEnum.valueOf(type));
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

}
