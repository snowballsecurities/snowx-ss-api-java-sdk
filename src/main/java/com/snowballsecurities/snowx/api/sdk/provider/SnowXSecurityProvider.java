/*
 * Copyright (c) 2020-2029, Snowball Securities and/or its affiliates. All rights reserved.
 * Snowball Securities PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.snowballsecurities.snowx.api.sdk.provider;

import com.snowballsecurities.snowx.api.sdk.domain.result.SnowXSecurityResult.Security;
import com.snowballsecurities.snowx.api.sdk.util.SnowXJsonConverter;
import com.snowballsecurities.snowx.api.sdk.exception.SnowXException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 证券provider
 * @author: snowx developer
 * @create: 2020-05-18 20:33
 **/
public class SnowXSecurityProvider extends AbstractProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(SnowXSecurityProvider.class);

    private SnowXAuthProvider snowXAuthProvider;

    public SnowXSecurityProvider(String urlPrefix, String accountId, String secretKey, SnowXJsonConverter converter) {
        super(urlPrefix, accountId, converter);
        this.snowXAuthProvider = new SnowXAuthProvider(urlPrefix, accountId, secretKey, converter);
    }

    /**
     * @param symbols 证券代码数组，不能为空，单次查询最多20支
     * @return
     * @throws SnowXException
     */
    public List<Security> getSecurityDetail(String[] symbols, String accessToken) throws SnowXException {
        ResultData resultData = new ResultData();
        try {
            if (symbols == null || symbols.length == 0) {
                throw new IllegalArgumentException("required parameter cannot be blank.");
            }
            if (symbols.length > 20) {
                throw new IllegalArgumentException("symbols length max 20.");
            }

            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("symbol", SnowXUtils.join(symbols));
            paramMap.put("account_id", accountId);
            if (SnowXUtils.isBlank(accessToken)) {
                paramMap.put("access_token", snowXAuthProvider.getAccessToken());
            } else {
                paramMap.put("access_token", accessToken);
            }

            SnowXHttpUtils.Response response = SnowXHttpUtils.doGet(urlPrefix.concat("/security/details"), paramMap);

            resultData = handleResponse(response);
            if (resultData.isSuccess()) {
                return converter.convertToList(resultData.getResultData(), Security.class);
            }
            throw new SnowXException(resultData.getHttpStatus(), resultData.getResultCode(), resultData.getMsg());
        } catch (SnowXException sx) {
            LOGGER.error("get security detail exception", sx);
            throw sx;
        } catch (Throwable t) {
            LOGGER.error("get security detail exception", t);
            throw new SnowXException(resultData.getHttpStatus(), resultData.getResultCode(), resultData.getMsg(), t);
        }
    }
}
