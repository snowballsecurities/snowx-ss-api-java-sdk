/*
 * Copyright (c) 2020-2029, Snowball Securities and/or its affiliates. All rights reserved.
 * Snowball Securities PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.snowballsecurities.snowx.api.sdk.provider;

import com.snowballsecurities.snowx.api.sdk.domain.result.SnowXAuthResult;
import com.snowballsecurities.snowx.api.sdk.domain.result.SnowXAuthResult.Auth;
import com.snowballsecurities.snowx.api.sdk.util.SnowXJsonConverter;
import com.snowballsecurities.snowx.api.sdk.exception.SnowXException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: 用户权限provider
 * @author: snowx developer
 * @create: 2020-05-18 21:01
 **/
public class SnowXAuthProvider extends AbstractProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(SnowXAuthProvider.class);

    private static final Map<TokenKey, Auth> TOKEN_MAP = new ConcurrentHashMap<>();

    private String secretKey;

    public SnowXAuthProvider(String urlPrefix, String accountId, String secretKey, SnowXJsonConverter converter) {
        super(urlPrefix, accountId, converter);

        if (SnowXUtils.isBlank(secretKey)) {
            throw new IllegalArgumentException("required parameter cannot be blank.");
        }
        this.secretKey = secretKey;
    }

    /**
     * 获取token, 优先从 TOKEN_MAP 中获取，如果 TOKEN_MAP 中不存在或者剩余过期时间不超过10分钟，这更新token
     *
     * @return 用户token
     * @throws SnowXException
     */
    public String getAccessToken() throws SnowXException {
        TokenKey tokenKey = new TokenKey(urlPrefix, accountId);
        SnowXAuthResult.Auth auth = TOKEN_MAP.get(tokenKey);
        if (auth != null && auth.getExpiryTime() != null && System.currentTimeMillis() < auth.getExpiryTime()) {
            return auth.getAccessToken();
        }

        LOGGER.info("get token miss from cache | url_prefix:{} | account:{}", urlPrefix, accountId);
        Auth newAuth = this.createAccessToken();

        if (newAuth != null && newAuth.getAccessToken() != null && newAuth.getExpiryTime() != null) {
            TOKEN_MAP.put(tokenKey, newAuth);
            LOGGER.debug("create new token | url_prefix:{} | account:{} | token:{} | expiry:{}",
                    urlPrefix, accountId, newAuth.getAccessToken(), newAuth.getExpiryTime());
            return newAuth.getAccessToken();
        }
        throw new SnowXException("get access token exception");
    }

    /**
     * 查询token
     *
     * @param accessToken 用户token
     * @return
     * @throws SnowXException
     */
    public Auth accessToken(String accessToken) throws SnowXException {
        ResultData resultData = new ResultData();
        try {
            if (SnowXUtils.isBlank(accessToken)) {
                throw new IllegalArgumentException("required parameter cannot be empty.");
            }

            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("account_id", accountId);
            paramMap.put("access_token", accessToken);

            String url = super.urlPrefix.concat("/auth/{account_id}/access-token/{access_token}")
                    .replace("{account_id}", accountId)
                    .replace("{access_token}", accessToken);

            SnowXHttpUtils.Response response = SnowXHttpUtils.doGet(url, paramMap);
            resultData = handleResponse(response);
            if (resultData.isSuccess()) {
                return converter.convertToBean(resultData.getResultData(), Auth.class);
            }
            throw new SnowXException(resultData.getHttpStatus(), resultData.getResultCode(), resultData.getMsg());
        } catch (SnowXException sx) {
            LOGGER.error("access token exception | account:{} | token:{}", accountId, accessToken, sx);
            throw sx;
        } catch (Throwable t) {
            LOGGER.error("access token exception | account:{} | token:{}", accountId, accessToken, t);
            throw new SnowXException(resultData.getHttpStatus(), resultData.getResultCode(), resultData.getMsg(), t);
        }
    }

    /**
     * 获取新的access token
     *
     * @return
     * @throws SnowXException
     */
    public Auth createAccessToken() throws SnowXException {
        ResultData resultData = new ResultData();
        try {
            if (SnowXUtils.isAnyBlank(accountId, secretKey)) {
                throw new IllegalArgumentException("required parameter cannot be empty.");
            }

            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("secret_key", secretKey);
            String url = urlPrefix.concat("/auth/{account_id}/access-token").replace("{account_id}", accountId);

            SnowXHttpUtils.Response response = SnowXHttpUtils.doPost(url, paramMap);

            resultData = handleResponse(response);
            if (resultData.isSuccess()) {
                return converter.convertToBean(resultData.getResultData(), Auth.class);
            }
            throw new SnowXException(resultData.getHttpStatus(), resultData.getResultCode(), resultData.getMsg());
        } catch (SnowXException sx) {
            LOGGER.error("create access token exception | account:{} | secret_key:{}", accountId, secretKey, sx);
            throw sx;
        } catch (Throwable t) {
            LOGGER.error("create access token exception | account:{} | secret_key:{}", accountId, secretKey, t);
            throw new SnowXException(resultData.getHttpStatus(), resultData.getResultCode(), resultData.getMsg(), t);
        }
    }

    /**
     * 清除过期token，可以定时清除过期token，释放内存
     */
    public static void clearExpiryToken() {
        Iterator<Entry<TokenKey, Auth>> iterator = TOKEN_MAP.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<TokenKey, Auth> entry = iterator.next();
            if (System.currentTimeMillis() - entry.getValue().getExpiryTime() >= 0) {
                iterator.remove();
                LOGGER.debug("remove token from cache | url_prefix:{} | account:{} | token:{} | expiry:{}",
                        entry.getKey().getUrlPrefix(), entry.getKey().getUrlPrefix(),
                        entry.getValue().getAccessToken(), entry.getValue().getExpiryTime()
                );
            }
        }
    }

    static class TokenKey {

        private String urlPrefix;
        private String accountId;

        public TokenKey(String urlPrefix, String accountId) {
            this.urlPrefix = urlPrefix;
            this.accountId = accountId;
        }

        public String getUrlPrefix() {
            return urlPrefix;
        }

        public String getAccountId() {
            return accountId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TokenKey tokenKey = (TokenKey) o;
            return Objects.equals(urlPrefix, tokenKey.urlPrefix) &&
                    Objects.equals(accountId, tokenKey.accountId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(urlPrefix, accountId);
        }
    }
}
