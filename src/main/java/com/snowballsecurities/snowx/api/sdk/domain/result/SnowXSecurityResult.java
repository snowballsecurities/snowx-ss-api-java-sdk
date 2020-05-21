/*
 * Copyright (c) 2020-2029, Snowball Securities and/or its affiliates. All rights reserved.
 * Snowball Securities PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.snowballsecurities.snowx.api.sdk.domain.result;

import java.io.Serializable;
import java.util.Objects;

/**
 * @description: 证券信息服务返回结果
 * @author: snowx developer
 * @create: 2020-05-18 20:37
 **/
public class SnowXSecurityResult {
    public static class Security implements Serializable,Cloneable {

        private static final long serialVersionUID = -8579302549304067035L;

        private String symbol;

        private String tickSize;

        private String lotSize;

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public String getTickSize() {
            return tickSize;
        }

        public void setTickSize(String tickSize) {
            this.tickSize = tickSize;
        }

        public String getLotSize() {
            return lotSize;
        }

        public void setLotSize(String lotSize) {
            this.lotSize = lotSize;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Security security = (Security) o;
            return Objects.equals(symbol, security.symbol) &&
                    Objects.equals(tickSize, security.tickSize) &&
                    Objects.equals(lotSize, security.lotSize);
        }

        @Override
        public int hashCode() {
            return Objects.hash(symbol, tickSize, lotSize);
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Security{");
            sb.append("symbol='").append(symbol).append('\'');
            sb.append(", tickSize='").append(tickSize).append('\'');
            sb.append(", lotSize='").append(lotSize).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }
}
