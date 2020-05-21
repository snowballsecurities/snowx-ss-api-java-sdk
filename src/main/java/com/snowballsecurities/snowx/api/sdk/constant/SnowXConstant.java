/*
 * Copyright (c) 2020-2029, Snowball Securities and/or its affiliates. All rights reserved.
 * Snowball Securities PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.snowballsecurities.snowx.api.sdk.constant;

/**
 * @description: 常量类
 * @author: snowx developer
 * @create: 2020-05-15 23:18
 **/
public class SnowXConstant {
    public enum AccountType {
        LIMITED_REG_T,
        REG_T,
        CASH,
        PORTFOLIO;

        private AccountType() {
        }
    }

    public enum TransactionTag {
        BTO,
        BTC,
        STO,
        STC,
        SELL,
        BUY,
        T;

        private TransactionTag() {
        }
    }

    public enum GroupBy {
        MARKET,
        CURRENCY;

        private GroupBy() {
        }
    }

    public enum Right {
        C,
        P,
        CB,
        PB;

        private Right() {
        }
    }

    public enum OrderListFilter {
        NORMAL,
        ADVANCED,
        ALL;

        private OrderListFilter() {
        }
    }

    public enum SecurityType {
        STK,
        CS,
        FUT,
        OPT,
        FOP,
        WAR,
        MLEG,
        CASH,
        CFD,
        CMDTY,
        FUND,
        IOPT,
        BOND,
        ALL;

        private SecurityType() {
        }
    }

    public enum OrderStatusCata {
        REPORTED,
        CONCLUDED,
        WITHDRAWED,
        ALL_DISPLAY,
        ALL;

        private OrderStatusCata() {
        }
    }

    public static enum OrderStatus {
        INVALID,
        EXPIRED,
        NO_REPORT,
        WAIT_REPORT,
        REPORTED,
        PART_CONCLUDED,
        CONCLUDED,
        WITHDRAWING,
        WAIT_WITHDRAW,
        PART_WAIT_WITHDRAW,
        PART_WITHDRAW,
        WITHDRAWED,
        REPLACING,
        WAIT_REPLACE,
        REPLACED,
        ALL;

        private OrderStatus() {
        }
    }

    public enum TimeInForce {
        DAY,
        GTC,
        OPG,
        IOC,
        FOK,
        GTX,
        GTD,
        ATC,
        AUC;

        private TimeInForce() {
        }
    }

    public enum OrderType {
        LIMIT,
        MARKET,
        AT,
        ATL,
        SSL,
        SEL,
        STOP,
        STOP_LIMIT,
        TRAIL,
        TRAIL_LIMIT,
        LIMIT_ON_OPENING,
        MARKET_ON_OPENING,
        LIMIT_ON_CLOSE,
        MARKET_ON_CLOSE;

        private OrderType() {
        }
    }

    public enum Currency {
        BASE,
        USX,
        CNY,
        USD,
        SEK,
        SGD,
        TRY,
        ZAR,
        JPY,
        AUD,
        CAD,
        CHF,
        CNH,
        HKD,
        NZD,
        CZK,
        DKK,
        HUF,
        NOK,
        PLN,
        EUR,
        GBP,
        ILS,
        MXN,
        RUB,
        KRW;

        private Currency() {
        }
    }

    public enum TransactionNote {
        NORMAL,
        LIQUIDATION;

        private TransactionNote() {
        }
    }

    public enum OrderSide {
        BUY,
        SELL,
        ALL;

        private OrderSide() {
        }
    }
}
