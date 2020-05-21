/*
 * Copyright (c) 2020-2029, Snowball Securities and/or its affiliates. All rights reserved.
 * Snowball Securities PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.snowballsecurities.snowx.api.sdk.provider;

import com.snowballsecurities.snowx.api.sdk.domain.result.SnowXResult;
import com.snowballsecurities.snowx.api.sdk.util.SnowXJsonConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description: provider abstract class
 * @author: snowx developer
 * @create: 2020-06-04 09:50
 **/
public abstract class AbstractProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractProvider.class);

    protected String urlPrefix;

    protected String accountId;

    protected SnowXJsonConverter converter;

    public AbstractProvider() {
    }

    public AbstractProvider(String urlPrefix, String accountId, SnowXJsonConverter converter) {
        if (SnowXUtils.isAnyBlank(urlPrefix, accountId, converter)) {
            throw new IllegalArgumentException("required parameter cannot be blank.");
        }
        this.urlPrefix = urlPrefix;
        this.accountId = accountId;
        this.converter = converter;
    }

    public ResultData handleResponse(SnowXHttpUtils.Response response) {
        ResultData resultData = new ResultData();
        try {
            resultData = converter.convertToBean(response.getResponseContext(), ResultData.class);
        } catch (Exception e) {
            LOGGER.error("parse json exception，source={}", response.getResponseContext(), e);
        }
        resultData.setHttpStatus(response.getStatusCode());
        resultData.setHttpContext(response.getResponseContext());
        return resultData;
    }

    protected static class ResultData {

        private Integer httpStatus;
        private String httpContext;
        private String resultCode;
        private String msg;
        private String resultData;

        public ResultData() {
        }

        public Integer getHttpStatus() {
            return httpStatus;
        }

        public void setHttpStatus(Integer httpStatus) {
            this.httpStatus = httpStatus;
        }

        public String getHttpContext() {
            return httpContext;
        }

        public void setHttpContext(String httpContext) {
            this.httpContext = httpContext;
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

        public String getResultData() {
            return resultData;
        }

        public void setResultData(String resultData) {
            this.resultData = resultData;
        }

        protected boolean isSuccess() {
            return this.httpStatus != null && this.getHttpStatus().intValue() == 200 && SnowXResult.SUCCESS_CODE.equals(this.resultCode);
        }
    }

}
