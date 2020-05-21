/*
 * Copyright (c) 2020-2029, Snowball Securities and/or its affiliates. All rights reserved.
 * Snowball Securities PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.snowballsecurities.snowx.api.sdk.provider;

import com.snowballsecurities.snowx.api.sdk.constant.SnowXConstant.SecurityType;
import com.snowballsecurities.snowx.api.sdk.domain.result.SnowXAssetResult.*;
import com.snowballsecurities.snowx.api.sdk.util.SnowXJsonConverter;
import com.snowballsecurities.snowx.api.sdk.exception.SnowXException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 用户资产provider
 * @author: snowx developer
 * @create: 2020-05-15 18:31
 **/
public class SnowXAssetProvider extends AbstractProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(SnowXAssetProvider.class);

    private SnowXAuthProvider snowXAuthProvider;

    public SnowXAssetProvider(String urlPrefix, String accountId, String secretKey, SnowXJsonConverter converter) {
        super(urlPrefix, accountId, converter);
        this.snowXAuthProvider = new SnowXAuthProvider(urlPrefix, accountId, secretKey, converter);
    }

    /**
     * 用户持仓查询
     *
     * @param securityTypes 筛选条件，证券类型
     * @param accessToken   访问token，不传自动获取
     * @return
     * @throws SnowXException
     */
    public List<Position> getPositionList(SecurityType[] securityTypes, String accessToken) throws SnowXException {
        ResultData resultData = new ResultData();
        try {
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("security_type", SnowXUtils.join(securityTypes));
            paramMap.put("account_id", accountId);

            if (SnowXUtils.isBlank(accessToken)) {
                paramMap.put("access_token", snowXAuthProvider.getAccessToken());
            } else {
                paramMap.put("access_token", accessToken);
            }

            SnowXHttpUtils.Response response = SnowXHttpUtils.doGet(urlPrefix.concat("/position"), paramMap);

            resultData = handleResponse(response);
            if (resultData.isSuccess()) {
                return converter.convertToList(resultData.getResultData(), Position.class);
            }
            throw new SnowXException(resultData.getHttpStatus(), resultData.getResultCode(), resultData.getMsg());
        } catch (SnowXException sx) {
            LOGGER.error("get position list exception", sx);
            throw sx;
        } catch (Throwable t) {
            LOGGER.error("get position list exception", t);
            throw new SnowXException(resultData.getHttpStatus(), resultData.getResultCode(), resultData.getMsg(), t);
        }
    }

    /**
     * 用户资产查询
     *
     * @param accessToken 访问token，不传自动获取
     * @return
     * @throws SnowXException
     */
    public Balance getBalance(String accessToken) throws SnowXException {
        ResultData resultData = new ResultData();
        try {
            Map<String, String> paramMap = new HashMap<>();
            if (SnowXUtils.isBlank(accessToken)) {
                paramMap.put("access_token", snowXAuthProvider.getAccessToken());
            } else {
                paramMap.put("access_token", accessToken);
            }
            paramMap.put("account_id", accountId);

            SnowXHttpUtils.Response response = SnowXHttpUtils.doGet(urlPrefix.concat("/funds"), paramMap);
            resultData = handleResponse(response);
            if (resultData.isSuccess()) {
                return converter.convertToBean(resultData.getResultData(), Balance.class);
            }
            throw new SnowXException(resultData.getHttpStatus(), resultData.getResultCode(), resultData.getMsg());
        } catch (SnowXException sx) {
            LOGGER.error("get balance exception", sx);
            throw sx;
        } catch (Throwable t) {
            LOGGER.error("get balance exception", t);
            throw new SnowXException(resultData.getHttpStatus(), resultData.getResultCode(), resultData.getMsg(), t);
        }
    }

}
