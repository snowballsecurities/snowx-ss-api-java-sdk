/*
 * Copyright (c) 2020-2029, Snowball Securities and/or its affiliates. All rights reserved.
 * Snowball Securities PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.snowballsecurities.snowx.api.sdk.domain.result;

import java.io.Serializable;

/**
 * @description: 雪盈API服务返回结果包装对象
 * @author: snowx developer
 * @create: 2020-05-18 20:37
 **/
public class SnowXResult<T> implements Serializable, Cloneable {

    private static final long serialVersionUID = -4166671318881379882L;

    public static final String SUCCESS_CODE = "60000";

    private String resultCode;

    private String msg;

    private T resultData;

    public SnowXResult() {
    }

    public SnowXResult(String resultCode, String msg, T resultData) {
        this.resultCode = resultCode;
        this.msg = msg;
        this.resultData = resultData;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResultData() {
        return resultData;
    }

    public void setResultData(T resultData) {
        this.resultData = resultData;
    }

    public boolean isSuccess() {
        return SUCCESS_CODE.equals(this.getResultCode());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SnowXResult{");
        sb.append("resultCode='").append(resultCode).append('\'');
        sb.append(", msg='").append(msg).append('\'');
        sb.append(", resultData=").append(resultData);
        sb.append('}');
        return sb.toString();
    }
}