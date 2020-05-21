/*
 * Copyright (c) 2020-2029, Snowball Securities and/or its affiliates. All rights reserved.
 * Snowball Securities PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.snowballsecurities.snowx.api.sdk.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.snowballsecurities.snowx.api.sdk.domain.result.SnowXPageResult;

import java.util.List;

/**
 * @description: FastJson实现JsonConverter
 * @author: snowx developer
 * @create: 2020-05-18 16:56
 **/
public class SnowXFastJsonConverter implements SnowXJsonConverter {

    @Override
    public <T> List<T> convertToList(String resource, Class<T> clazz) {
        return JSONArray.parseArray(resource, clazz);
    }

    @Override
    public <T> T convertToBean(String resource, Class<T> clazz) {
        return JSONObject.parseObject(resource, clazz);
    }

    @Override
    public <T> SnowXPageResult<T> convertToPage(String resource, Class<T> clazz) {
        return JSONObject.parseObject(resource, new TypeReference<SnowXPageResult<T>>(clazz) {
        });
    }

}
