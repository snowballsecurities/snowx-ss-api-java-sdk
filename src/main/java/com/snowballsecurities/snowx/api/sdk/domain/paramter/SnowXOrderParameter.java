/*
 * Copyright (c) 2020-2029, Snowball Securities and/or its affiliates. All rights reserved.
 * Snowball Securities PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.snowballsecurities.snowx.api.sdk.domain.paramter;

import com.snowballsecurities.snowx.api.sdk.constant.SnowXConstant;

/**
 * @description: 订单服务请求参数包装对象
 * @author: snowx developer
 * @create: 2020-05-18 20:37
 **/
public class SnowXOrderParameter {

    public static class PlaceOrderParameter {
        private String id;
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

        private String parent;

        private Double stopPrice;

        public Double getStopPrice() {
            return stopPrice;
        }

        public void setStopPrice(Double stopPrice) {
            this.stopPrice = stopPrice;
        }

        public String getParent() {
            return parent;
        }

        public void setParent(String parent) {
            this.parent = parent;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

    }

}
