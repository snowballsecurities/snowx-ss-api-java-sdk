/*
 * Copyright (c) 2020-2029, Snowball Securities and/or its affiliates. All rights reserved.
 * Snowball Securities PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.snowballsecurities.snowx.api.sdk.test;

import com.snowballsecurities.snowx.api.sdk.constant.SnowXConstant;
import com.snowballsecurities.snowx.api.sdk.domain.result.SnowXAssetResult;
import com.snowballsecurities.snowx.api.sdk.exception.SnowXException;
import com.snowballsecurities.snowx.api.sdk.provider.SnowXAssetProvider;
import com.snowballsecurities.snowx.api.sdk.util.SnowXFastJsonConverter;
import com.snowballsecurities.snowx.api.sdk.util.SnowXJsonConverter;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @description: 资产测试类
 * @author: snowx developer
 * @create: 2020-05-20 21:31
 **/
public class SnowXAssetProviderTest {

    private static final Logger logger = LoggerFactory.getLogger(SnowXAssetProviderTest.class);

    private SnowXAssetProvider provider;

    @Before
    public void createClient() {
        String urlPrefix = "https://sandbox.snbsecurities.com";
        String accountId = "xxxxxx";
        String secretKey = "123456";
        SnowXJsonConverter converter = new SnowXFastJsonConverter();
        provider = new SnowXAssetProvider(urlPrefix, accountId, secretKey, converter);
    }

    @Test
    public void getPositionList() {
        try {
            SnowXConstant.SecurityType[] securityTypes = {};
            List<SnowXAssetResult.Position> positionList = provider.getPositionList(securityTypes, null);
        } catch (SnowXException sx) {
            logger.error("get position list exception", sx);
        }
    }

    @Test
    public void getBalance() {
        try {
            SnowXAssetResult.Balance balance = provider.getBalance(null);
        } catch (SnowXException sx) {
            logger.error("get balance exception", sx);
        }
    }
}
