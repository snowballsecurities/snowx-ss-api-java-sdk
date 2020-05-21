/*
 * Copyright (c) 2020-2029, Snowball Securities and/or its affiliates. All rights reserved.
 * Snowball Securities PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.snowballsecurities.snowx.api.sdk.test;

import com.snowballsecurities.snowx.api.sdk.domain.result.SnowXSecurityResult;
import com.snowballsecurities.snowx.api.sdk.exception.SnowXException;
import com.snowballsecurities.snowx.api.sdk.provider.SnowXSecurityProvider;
import com.snowballsecurities.snowx.api.sdk.util.SnowXFastJsonConverter;
import com.snowballsecurities.snowx.api.sdk.util.SnowXJsonConverter;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @description: 证券信息测试类
 * @author: snowx developer
 * @create: 2020-05-20 22:16
 **/
public class SnowXSecurityProviderTest {

    private static final Logger logger = LoggerFactory.getLogger(SnowXSecurityProviderTest.class);

    private SnowXSecurityProvider provider;

    @Before
    public void createClient() {
        String urlPrefix = "https://sandbox.snbsecurities.com";
        String accountId = "xxxxxx";
        String secretKey = "123456";
        SnowXJsonConverter converter = new SnowXFastJsonConverter();
        provider = new SnowXSecurityProvider(urlPrefix, accountId, secretKey, converter);
    }

    @Test
    public void getSecurityDetail() {
        try {
            List<SnowXSecurityResult.Security> security = provider.getSecurityDetail(new String[]{"OIS"}, null);
        } catch (SnowXException sx) {
            logger.error("get security detail exception.", sx);
        }
    }
}
