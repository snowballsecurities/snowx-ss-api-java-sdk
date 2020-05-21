/*
 * Copyright (c) 2020-2029, Snowball Securities and/or its affiliates. All rights reserved.
 * Snowball Securities PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.snowballsecurities.snowx.api.sdk.exception;

/**
 * @description: 雪盈API SDK定义异常
 * @author: snowx developer
 * @create: 2020-05-15 23:00
 **/
public class SnowXException extends Exception {

    private Integer httpStatus;

    private String resultCode;

    private String resultMsg;

    public SnowXException(String message) {
        super(message);
    }

    public SnowXException(Throwable cause) {
        super(cause);
    }

    public SnowXException(String message, Throwable cause) {
        super(message, cause);
    }

    public SnowXException(Integer httpStatus, String resultCode, String resultMsg) {
        this(httpStatus, resultCode, resultMsg, "");
    }

    public SnowXException(Integer httpStatus, String resultCode, String resultMsg, Throwable cause) {
        super(new StringBuilder()
                .append("http_status:")
                .append(httpStatus)
                .append(",result_code:")
                .append(resultCode)
                .append(",result_msg:")
                .append(resultMsg)
                .toString(), cause);
        this.httpStatus = httpStatus;
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    public SnowXException(Integer httpStatus, String resultCode, String resultMsg, String exceptionMessage) {
        super(new StringBuilder()
                .append(exceptionMessage != null ? exceptionMessage : "")
                .append("http_status:")
                .append(httpStatus)
                .append(",result_code:")
                .append(resultCode)
                .append(",result_msg:")
                .append(resultMsg)
                .toString());
        this.httpStatus = httpStatus;
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    public Integer getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(Integer httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }
}
