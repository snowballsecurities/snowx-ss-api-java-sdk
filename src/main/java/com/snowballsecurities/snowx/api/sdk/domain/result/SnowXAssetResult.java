/*
 * Copyright (c) 2020-2029, Snowball Securities and/or its affiliates. All rights reserved.
 * Snowball Securities PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.snowballsecurities.snowx.api.sdk.domain.result;

import com.snowballsecurities.snowx.api.sdk.constant.SnowXConstant;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * @description: 资产服务返回结果
 * @author: snowx developer
 * @create: 2020-05-18 20:37
 **/
public class SnowXAssetResult {

    public static class Position implements Serializable,Cloneable {

        private static final long serialVersionUID = 2925032904388123708L;

        private String accountId;           //是	string	账户ID	1.0
        private SnowXConstant.SecurityType securityType;        //是	string	证券类型	1.0
        private String symbol;              //是	string	证券代码	1.0
        private String exchange;            //是	string	市场	1.0
        private Integer position;           //是	int	持仓数量	1.0
        private Double averagePrice;        //是	double	持仓均价	1.0
        private Double marketPrice;         //是	double	市场价	1.0
        private Double realizedPnl;         //是	double	已实现盈亏	1.0

        public String getAccountId() {
            return accountId;
        }

        public void setAccountId(String accountId) {
            this.accountId = accountId;
        }

        public SnowXConstant.SecurityType getSecurityType() {
            return securityType;
        }

        public void setSecurityType(SnowXConstant.SecurityType securityType) {
            this.securityType = securityType;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public String getExchange() {
            return exchange;
        }

        public void setExchange(String exchange) {
            this.exchange = exchange;
        }

        public Integer getPosition() {
            return position;
        }

        public void setPosition(Integer position) {
            this.position = position;
        }

        public Double getAveragePrice() {
            return averagePrice;
        }

        public void setAveragePrice(Double averagePrice) {
            this.averagePrice = averagePrice;
        }

        public Double getMarketPrice() {
            return marketPrice;
        }

        public void setMarketPrice(Double marketPrice) {
            this.marketPrice = marketPrice;
        }

        public Double getRealizedPnl() {
            return realizedPnl;
        }

        public void setRealizedPnl(Double realizedPnl) {
            this.realizedPnl = realizedPnl;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position that = (Position) o;
            return Objects.equals(accountId, that.accountId) &&
                    securityType == that.securityType &&
                    Objects.equals(symbol, that.symbol) &&
                    Objects.equals(exchange, that.exchange) &&
                    Objects.equals(position, that.position) &&
                    Objects.equals(averagePrice, that.averagePrice) &&
                    Objects.equals(marketPrice, that.marketPrice) &&
                    Objects.equals(realizedPnl, that.realizedPnl);
        }

        @Override
        public int hashCode() {
            return Objects.hash(accountId, securityType, symbol, exchange, position, averagePrice, marketPrice, realizedPnl);
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("PositionResult{");
            sb.append("accountId='").append(accountId).append('\'');
            sb.append(", securityType=").append(securityType);
            sb.append(", symbol='").append(symbol).append('\'');
            sb.append(", exchange='").append(exchange).append('\'');
            sb.append(", position=").append(position);
            sb.append(", averagePrice=").append(averagePrice);
            sb.append(", marketPrice=").append(marketPrice);
            sb.append(", realizedPnl=").append(realizedPnl);
            sb.append('}');
            return sb.toString();
        }
    }

    public static class BalanceDetail implements Serializable, Cloneable {
        private BigDecimal cash;
        private SnowXConstant.Currency currency;

        public BigDecimal getCash() {
            return cash;
        }

        public void setCash(BigDecimal cash) {
            this.cash = cash;
        }

        public SnowXConstant.Currency getCurrency() {
            return currency;
        }

        public void setCurrency(SnowXConstant.Currency currency) {
            this.currency = currency;
        }
    }
    public static class Balance implements Serializable,Cloneable{

        private static final long serialVersionUID = 6632559645063418656L;

        private Double netLiquidationValue;                 //是	double	净资产	1.0
        private Double equityWithLoanValue;                 //是	double	总资产	1.0
        private Double previousDayEquityWithLoanValue;      //是	double	昨日总资产	1.0
        private Double securitiesGrossPositionValue;        //是	double	证券总价值	1.0
        private Double sma;                                 // 是	double		1.0
        private Double cash;                                // 是	double	账户金额	1.0
        private Double currentAvailableFunds;               // 是	double	可用资金	1.0
        private Double currentExcessLiquidity;              // 是	double	剩余流动性	1.0
        private Double leverage;                            // 是	double	杠杆，GPV/NL	1.0
        private Double currentInitialMargin;                // 是	double	初始保证金	1.0
        private Double currentMaintenanceMargin;            // 是	double	维持保证金	1.0
        private SnowXConstant.Currency currency;            // 是	enum	币种	1.0

        private List<BalanceDetail> balanceDetailItems;

        public List<BalanceDetail> getBalanceDetailItems() {
            return balanceDetailItems;
        }

        public void setBalanceDetailItems(List<BalanceDetail> balanceDetailItems) {
            this.balanceDetailItems = balanceDetailItems;
        }

        public Double getNetLiquidationValue() {
            return netLiquidationValue;
        }

        public void setNetLiquidationValue(Double netLiquidationValue) {
            this.netLiquidationValue = netLiquidationValue;
        }

        public Double getEquityWithLoanValue() {
            return equityWithLoanValue;
        }

        public void setEquityWithLoanValue(Double equityWithLoanValue) {
            this.equityWithLoanValue = equityWithLoanValue;
        }

        public Double getPreviousDayEquityWithLoanValue() {
            return previousDayEquityWithLoanValue;
        }

        public void setPreviousDayEquityWithLoanValue(Double previousDayEquityWithLoanValue) {
            this.previousDayEquityWithLoanValue = previousDayEquityWithLoanValue;
        }

        public Double getSecuritiesGrossPositionValue() {
            return securitiesGrossPositionValue;
        }

        public void setSecuritiesGrossPositionValue(Double securitiesGrossPositionValue) {
            this.securitiesGrossPositionValue = securitiesGrossPositionValue;
        }

        public Double getSma() {
            return sma;
        }

        public void setSma(Double sma) {
            this.sma = sma;
        }

        public Double getCash() {
            return cash;
        }

        public void setCash(Double cash) {
            this.cash = cash;
        }

        public Double getCurrentAvailableFunds() {
            return currentAvailableFunds;
        }

        public void setCurrentAvailableFunds(Double currentAvailableFunds) {
            this.currentAvailableFunds = currentAvailableFunds;
        }

        public Double getCurrentExcessLiquidity() {
            return currentExcessLiquidity;
        }

        public void setCurrentExcessLiquidity(Double currentExcessLiquidity) {
            this.currentExcessLiquidity = currentExcessLiquidity;
        }

        public Double getLeverage() {
            return leverage;
        }

        public void setLeverage(Double leverage) {
            this.leverage = leverage;
        }

        public Double getCurrentInitialMargin() {
            return currentInitialMargin;
        }

        public void setCurrentInitialMargin(Double currentInitialMargin) {
            this.currentInitialMargin = currentInitialMargin;
        }

        public Double getCurrentMaintenanceMargin() {
            return currentMaintenanceMargin;
        }

        public void setCurrentMaintenanceMargin(Double currentMaintenanceMargin) {
            this.currentMaintenanceMargin = currentMaintenanceMargin;
        }

        public SnowXConstant.Currency getCurrency() {
            return currency;
        }

        public void setCurrency(SnowXConstant.Currency currency) {
            this.currency = currency;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Balance that = (Balance) o;
            return Objects.equals(netLiquidationValue, that.netLiquidationValue) &&
                    Objects.equals(equityWithLoanValue, that.equityWithLoanValue) &&
                    Objects.equals(previousDayEquityWithLoanValue, that.previousDayEquityWithLoanValue) &&
                    Objects.equals(securitiesGrossPositionValue, that.securitiesGrossPositionValue) &&
                    Objects.equals(sma, that.sma) &&
                    Objects.equals(cash, that.cash) &&
                    Objects.equals(currentAvailableFunds, that.currentAvailableFunds) &&
                    Objects.equals(currentExcessLiquidity, that.currentExcessLiquidity) &&
                    Objects.equals(leverage, that.leverage) &&
                    Objects.equals(currentInitialMargin, that.currentInitialMargin) &&
                    Objects.equals(currentMaintenanceMargin, that.currentMaintenanceMargin) &&
                    currency == that.currency;
        }

        @Override
        public int hashCode() {
            return Objects.hash(netLiquidationValue, equityWithLoanValue, previousDayEquityWithLoanValue, securitiesGrossPositionValue, sma, cash, currentAvailableFunds, currentExcessLiquidity, leverage, currentInitialMargin, currentMaintenanceMargin, currency);
        }

        @Override
        public String toString() {
            return "Balance{" +
                    "netLiquidationValue=" + netLiquidationValue +
                    ", equityWithLoanValue=" + equityWithLoanValue +
                    ", previousDayEquityWithLoanValue=" + previousDayEquityWithLoanValue +
                    ", securitiesGrossPositionValue=" + securitiesGrossPositionValue +
                    ", sma=" + sma +
                    ", cash=" + cash +
                    ", currentAvailableFunds=" + currentAvailableFunds +
                    ", currentExcessLiquidity=" + currentExcessLiquidity +
                    ", leverage=" + leverage +
                    ", currentInitialMargin=" + currentInitialMargin +
                    ", currentMaintenanceMargin=" + currentMaintenanceMargin +
                    ", currency=" + currency +
                    ", balanceDetailItems=" + balanceDetailItems +
                    '}';
        }
    }
}
