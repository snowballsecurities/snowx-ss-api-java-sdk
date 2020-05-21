/*
 * Copyright (c) 2020-2029, Snowball Securities and/or its affiliates. All rights reserved.
 * Snowball Securities PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.snowballsecurities.snowx.api.sdk.domain.result;

import com.snowballsecurities.snowx.api.sdk.constant.SnowXConstant;

import java.io.Serializable;
import java.util.Objects;

/**
 * @description: 订单服务返回结果
 * @author: snowx developer
 * @create: 2020-05-18 20:37
 **/
public class SnowXOrderResult {

    public static class PlaceOrderResult implements Serializable,Cloneable {

        private static final long serialVersionUID = -6000736513341059245L;

        private String id;
        private SnowXConstant.OrderStatus status;
        private String memo;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public SnowXConstant.OrderStatus getStatus() {
            return status;
        }

        public void setStatus(SnowXConstant.OrderStatus status) {
            this.status = status;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PlaceOrderResult that = (PlaceOrderResult) o;
            return Objects.equals(id, that.id) &&
                    status == that.status &&
                    Objects.equals(memo, that.memo);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, status, memo);
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("PlaceOrderResult{");
            sb.append("id='").append(id).append('\'');
            sb.append(", status=").append(status);
            sb.append(", memo='").append(memo).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }

    public static class OrderResult implements Serializable,Cloneable {

        private static final long serialVersionUID = -576899648328001479L;

        private String id;
        private String accountId;
        private SnowXConstant.SecurityType securityType;
        private String symbol;
        private String exchange;
        private SnowXConstant.OrderType orderType;
        private SnowXConstant.OrderSide side;
        private SnowXConstant.Currency currency;
        private Integer quantity;
        private Double price;
        private SnowXConstant.TimeInForce tif;
        private Boolean rth;
        private SnowXConstant.OrderStatus status;
        private Integer filledQuantity;
        private Long orderTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

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

        public SnowXConstant.OrderType getOrderType() {
            return orderType;
        }

        public void setOrderType(SnowXConstant.OrderType orderType) {
            this.orderType = orderType;
        }

        public SnowXConstant.OrderSide getSide() {
            return side;
        }

        public void setSide(SnowXConstant.OrderSide side) {
            this.side = side;
        }

        public SnowXConstant.Currency getCurrency() {
            return currency;
        }

        public void setCurrency(SnowXConstant.Currency currency) {
            this.currency = currency;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public SnowXConstant.TimeInForce getTif() {
            return tif;
        }

        public void setTif(SnowXConstant.TimeInForce tif) {
            this.tif = tif;
        }

        public Boolean getRth() {
            return rth;
        }

        public void setRth(Boolean rth) {
            this.rth = rth;
        }

        public SnowXConstant.OrderStatus getStatus() {
            return status;
        }

        public void setStatus(SnowXConstant.OrderStatus status) {
            this.status = status;
        }

        public Integer getFilledQuantity() {
            return filledQuantity;
        }

        public void setFilledQuantity(Integer filledQuantity) {
            this.filledQuantity = filledQuantity;
        }

        public Long getOrderTime() {
            return orderTime;
        }

        public void setOrderTime(Long orderTime) {
            this.orderTime = orderTime;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            OrderResult that = (OrderResult) o;
            return Objects.equals(id, that.id) &&
                    Objects.equals(accountId, that.accountId) &&
                    securityType == that.securityType &&
                    Objects.equals(symbol, that.symbol) &&
                    Objects.equals(exchange, that.exchange) &&
                    orderType == that.orderType &&
                    side == that.side &&
                    currency == that.currency &&
                    Objects.equals(quantity, that.quantity) &&
                    Objects.equals(price, that.price) &&
                    tif == that.tif &&
                    Objects.equals(rth, that.rth) &&
                    status == that.status &&
                    Objects.equals(filledQuantity, that.filledQuantity) &&
                    Objects.equals(orderTime, that.orderTime);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, accountId, securityType, symbol, exchange, orderType, side, currency, quantity, price, tif, rth, status, filledQuantity, orderTime);
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("OrderResult{");
            sb.append("id='").append(id).append('\'');
            sb.append(", accountId='").append(accountId).append('\'');
            sb.append(", securityType=").append(securityType);
            sb.append(", symbol='").append(symbol).append('\'');
            sb.append(", exchange='").append(exchange).append('\'');
            sb.append(", orderType=").append(orderType);
            sb.append(", side=").append(side);
            sb.append(", currency=").append(currency);
            sb.append(", quantity=").append(quantity);
            sb.append(", price=").append(price);
            sb.append(", tif=").append(tif);
            sb.append(", rth=").append(rth);
            sb.append(", status=").append(status);
            sb.append(", filledQuantity=").append(filledQuantity);
            sb.append(", orderTime=").append(orderTime);
            sb.append('}');
            return sb.toString();
        }
    }

    public static class CancelResult implements Serializable,Cloneable {

        private static final long serialVersionUID = -3120277373499325714L;

        private String id;
        private SnowXConstant.OrderStatus status;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public SnowXConstant.OrderStatus getStatus() {
            return status;
        }

        public void setStatus(SnowXConstant.OrderStatus status) {
            this.status = status;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CancelResult that = (CancelResult) o;
            return Objects.equals(id, that.id) &&
                    status == that.status;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, status);
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("CancelResult{");
            sb.append("id='").append(id).append('\'');
            sb.append(", status=").append(status);
            sb.append('}');
            return sb.toString();
        }
    }

    public static class ConcludedResult implements Serializable,Cloneable {

        private static final long serialVersionUID = 1589737005654544519L;

        private String id;
        private String accountId;
        private String securityType;
        private String symbol;
        private String exchange;
        private SnowXConstant.OrderType orderType;
        private SnowXConstant.OrderSide side;
        private SnowXConstant.Currency currency;
        private Integer quantity;
        private Double price;
        private SnowXConstant.TimeInForce tif;
        private Boolean rth;
        private SnowXConstant.OrderStatus status;
        private Long tradeTime;
        private Long orderTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAccountId() {
            return accountId;
        }

        public void setAccountId(String accountId) {
            this.accountId = accountId;
        }

        public String getSecurityType() {
            return securityType;
        }

        public void setSecurityType(String securityType) {
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

        public SnowXConstant.OrderType getOrderType() {
            return orderType;
        }

        public void setOrderType(SnowXConstant.OrderType orderType) {
            this.orderType = orderType;
        }

        public SnowXConstant.OrderSide getSide() {
            return side;
        }

        public void setSide(SnowXConstant.OrderSide side) {
            this.side = side;
        }

        public SnowXConstant.Currency getCurrency() {
            return currency;
        }

        public void setCurrency(SnowXConstant.Currency currency) {
            this.currency = currency;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public SnowXConstant.TimeInForce getTif() {
            return tif;
        }

        public void setTif(SnowXConstant.TimeInForce tif) {
            this.tif = tif;
        }

        public Boolean getRth() {
            return rth;
        }

        public void setRth(Boolean rth) {
            this.rth = rth;
        }

        public SnowXConstant.OrderStatus getStatus() {
            return status;
        }

        public void setStatus(SnowXConstant.OrderStatus status) {
            this.status = status;
        }

        public Long getTradeTime() {
            return tradeTime;
        }

        public void setTradeTime(Long tradeTime) {
            this.tradeTime = tradeTime;
        }

        public Long getOrderTime() {
            return orderTime;
        }

        public void setOrderTime(Long orderTime) {
            this.orderTime = orderTime;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ConcludedResult that = (ConcludedResult) o;
            return Objects.equals(id, that.id) &&
                    Objects.equals(accountId, that.accountId) &&
                    Objects.equals(securityType, that.securityType) &&
                    Objects.equals(symbol, that.symbol) &&
                    Objects.equals(exchange, that.exchange) &&
                    orderType == that.orderType &&
                    side == that.side &&
                    currency == that.currency &&
                    Objects.equals(quantity, that.quantity) &&
                    Objects.equals(price, that.price) &&
                    tif == that.tif &&
                    Objects.equals(rth, that.rth) &&
                    status == that.status &&
                    Objects.equals(tradeTime, that.tradeTime) &&
                    Objects.equals(orderTime, that.orderTime);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, accountId, securityType, symbol, exchange, orderType, side, currency, quantity, price, tif, rth, status, tradeTime, orderTime);
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("ConcludedResult{");
            sb.append("id='").append(id).append('\'');
            sb.append(", accountId='").append(accountId).append('\'');
            sb.append(", securityType='").append(securityType).append('\'');
            sb.append(", symbol='").append(symbol).append('\'');
            sb.append(", exchange='").append(exchange).append('\'');
            sb.append(", orderType=").append(orderType);
            sb.append(", side=").append(side);
            sb.append(", currency=").append(currency);
            sb.append(", quantity=").append(quantity);
            sb.append(", price=").append(price);
            sb.append(", tif=").append(tif);
            sb.append(", rth=").append(rth);
            sb.append(", status=").append(status);
            sb.append(", tradeTime=").append(tradeTime);
            sb.append(", orderTime=").append(orderTime);
            sb.append('}');
            return sb.toString();
        }
    }
}
