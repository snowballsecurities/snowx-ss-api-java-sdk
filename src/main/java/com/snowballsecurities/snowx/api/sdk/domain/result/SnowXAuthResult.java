/*
 * Copyright (c) 2020-2029, Snowball Securities and/or its affiliates. All rights reserved.
 * Snowball Securities PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.snowballsecurities.snowx.api.sdk.domain.result;

import java.io.Serializable;
import java.util.Objects;

/**
 * @description: 用户权限服务返回结果
 * @author: snowx developer
 * @create: 2020-05-18 20:37
 **/
public class SnowXAuthResult {

    public static class Auth implements Serializable,Cloneable {

        private static final long serialVersionUID = -176796949248955343L;

        private String accessToken;
        private Long expiryTime;

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public Long getExpiryTime() {
            return expiryTime;
        }

        public void setExpiryTime(Long expiryTime) {
            this.expiryTime = expiryTime;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Auth auth = (Auth) o;
            return Objects.equals(accessToken, auth.accessToken) &&
                    Objects.equals(expiryTime, auth.expiryTime);
        }

        @Override
        public int hashCode() {
            return Objects.hash(accessToken, expiryTime);
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Auth{");
            sb.append("accessToken='").append(accessToken).append('\'');
            sb.append(", expiryTime=").append(expiryTime);
            sb.append('}');
            return sb.toString();
        }
    }
}
