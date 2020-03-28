package com.dxj.strategy.strategy;

import com.dxj.strategy.RechargeTypeEnum;
import com.dxj.strategy.strategy.Context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

/**
 * @AUTHOR: sinkiang
 * @DATE: 2020-01-19 18:59
 */
public class Client {

    /**
     * 作者：alaric 时间：2013-8-5上午11:33:52 描述：
     */
    public static void main(String[] args) {

        Context context = new Context();
        // 网银充值100 需要付多少
        Double money = context.calRecharge(100D,
                RechargeTypeEnum.E_BANK.value());
        System.out.println(money);

        // 商户账户充值100 需要付多少
        Double money2 = context.calRecharge(100D,
                RechargeTypeEnum.BUSI_ACCOUNTS.value());
        System.out.println(money2);

        // 手机充值100 需要付多少
        Double money3 = context.calRecharge(100D,
                RechargeTypeEnum.MOBILE.value());
        System.out.println(money3);

        // 充值卡充值100 需要付多少
        Double money4 = context.calRecharge(100D,
                RechargeTypeEnum.CARD_RECHARGE.value());
        System.out.println(money4);

    }
}
