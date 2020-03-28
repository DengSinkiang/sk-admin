package com.dxj.strategy.strategy;

import com.dxj.strategy.RechargeTypeEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @AUTHOR: sinkiang
 * @DATE: 2020-01-19 18:56
 */
public class StrategyFactory {

    private static StrategyFactory factory = new StrategyFactory();

    private StrategyFactory() {
    }

    private static Map<Integer, Strategy> strategyMap = new HashMap<>(8);

    static {
        strategyMap.put(RechargeTypeEnum.E_BANK.value(), new EBankStrategy());
        strategyMap.put(RechargeTypeEnum.BUSI_ACCOUNTS.value(), new BusiAcctStrategy());
        strategyMap.put(RechargeTypeEnum.MOBILE.value(), new MobileStrategy());
        strategyMap.put(RechargeTypeEnum.CARD_RECHARGE.value(), new CardStrategy());
    }

    public Strategy creator(Integer type) {
        return strategyMap.get(type);
    }

    public static StrategyFactory getInstance() {
        return factory;
    }
}
