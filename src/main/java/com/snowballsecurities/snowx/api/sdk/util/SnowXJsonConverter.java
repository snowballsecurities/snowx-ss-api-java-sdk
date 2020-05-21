/*
 * Copyright (c) 2020-2029, Snowball Securities and/or its affiliates. All rights reserved.
 * Snowball Securities PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.snowballsecurities.snowx.api.sdk.util;

import com.snowballsecurities.snowx.api.sdk.domain.result.SnowXPageResult;
import com.snowballsecurities.snowx.api.sdk.domain.result.SnowXResult;

import java.util.List;

/**
 * @description: JSON转化器
 * @author: snowx developer
 * @create: 2020-05-16 00:18
 **/
public interface SnowXJsonConverter {

    <T> List<T> convertToList(String resultData, Class<T> clazz);

    <T> T convertToBean(String resultData, Class<T> clazz);

    <T> SnowXPageResult<T> convertToPage(String resultData, Class<T> clazz);
}
