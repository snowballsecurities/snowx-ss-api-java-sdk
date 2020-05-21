/*
 * Copyright (c) 2020-2029, Snowball Securities and/or its affiliates. All rights reserved.
 * Snowball Securities PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.snowballsecurities.snowx.api.sdk.test;

import com.snowballsecurities.snowx.api.sdk.domain.result.SnowXAuthResult;
import com.snowballsecurities.snowx.api.sdk.exception.SnowXException;
import com.snowballsecurities.snowx.api.sdk.provider.SnowXAuthProvider;
import com.snowballsecurities.snowx.api.sdk.util.SnowXFastJsonConverter;
import com.snowballsecurities.snowx.api.sdk.util.SnowXJsonConverter;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @description: 权限测试类
 * @author: snowx developer
 * @create: 2020-05-20 22:57
 **/
public class SnowXAuthProviderTest {

    private static final Logger logger = LoggerFactory.getLogger(SnowXOrderProviderTest.class);

    private SnowXAuthProvider provider;

    @Before
    public void createClient() {
        String urlPrefix = "https://sandbox.snbsecurities.com";
        String accountId = "xxxxxx";
        String secretKey = "123456";
        SnowXJsonConverter converter = new SnowXFastJsonConverter();
        provider = new SnowXAuthProvider(urlPrefix, accountId, secretKey, converter);
    }

    @Test
    public void getAccessToken() {
        try {
            String accessToken = provider.getAccessToken();
        } catch (SnowXException sx) {
            logger.error("ge access token exception", sx);
        }
    }

    @Test
    public void createAccessToken() {
        try {
            SnowXAuthResult.Auth auth = provider.createAccessToken();
        } catch (SnowXException sx) {
            logger.error("access token exception", sx);
        }
    }

    @Test
    public void accessToken() throws SnowXException {
        String accessToken = "z2gkzzTk9LEpd80hUUWkhvr4sfvKnh5M";
        try {
            SnowXAuthResult.Auth auth = provider.accessToken(accessToken);
        } catch (SnowXException sx) {
            logger.error("access token exception, token:{}", accessToken, sx);
        }
    }

    @Test
    public void clearExpiryToken() {
        // 该方法用于清理缓存中过期的token，用户可以使用定时任务定期清理
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(new Runnable() {
            public void run() {
                SnowXAuthProvider.clearExpiryToken();
            }
        }, 10, 60, TimeUnit.MINUTES);
    }
}
