/*
 * Copyright (c) 2020-2029, Snowball Securities and/or its affiliates. All rights reserved.
 * Snowball Securities PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.snowballsecurities.snowx.api.sdk.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Map;

/**
 * @description: HttpUtils
 * @author: snowx developer
 * @create: 2020-05-15 15:07
 **/
class SnowXHttpUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(SnowXHttpUtils.class);

    /**
     * 默认60秒
     */
    private static int CONNECTION_TIME_OUT = 60000;

    /**
     * 默认60秒
     */
    private static int READ_TIME_OUT = 60000;

    /**
     * 设置全局超时时间
     *
     * @param readTimeout    READ_TIME_OUT
     * @param connectTimeout CONNECTION_TIME_OUT
     */
    public static synchronized void setTimeout(int readTimeout, int connectTimeout) {
        if (readTimeout < 0 || connectTimeout < 0) {
            throw new IllegalArgumentException("read timeout and connect timeout cannot be less than 0");
        }
        SnowXHttpUtils.READ_TIME_OUT = readTimeout;
        SnowXHttpUtils.CONNECTION_TIME_OUT = connectTimeout;
    }

    /**
     * 获取HttpURLConnection
     *
     * @param url 完整的Http访问url
     * @return
     * @throws IOException
     */
    private static HttpURLConnection getConnection(String url) throws IOException, NoSuchAlgorithmException, KeyManagementException {
        URL u = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) u.openConnection();
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setReadTimeout(READ_TIME_OUT);
        conn.setConnectTimeout(CONNECTION_TIME_OUT);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        conn.setRequestProperty("User-Agent", "SnowX-SDK");
        conn.setRequestProperty("Accept", "application/vnd.snowx+json; version=1.0");
        return conn;
    }

    /**
     * 发送post请求
     *
     * @param url    完整的Http访问url
     * @param params 参数map
     * @return
     * @throws IOException
     */
    public static Response doPost(String url, Map<String, String> params) throws Exception {
        LOGGER.debug("snowx http access | url:{} | method:{} | params:{}", url, "post", params);
        url = joinParam(url, params);
        HttpURLConnection connection = getConnection(url);
        connection.setRequestMethod("POST");
        return execute(connection);
    }

    /**
     * 发送get请求
     *
     * @param url    完整的Http访问url
     * @param params 参数map
     * @return
     * @throws IOException
     */
    public static Response doGet(String url, Map<String, String> params) throws Exception {
        LOGGER.debug("snowx http access | url:{} | method:{} | params:{}", url, "get", params);
        url = joinParam(url, params);
        HttpURLConnection connection = getConnection(url);
        connection.setRequestMethod("GET");
        return execute(connection);
    }

    /**
     * 发送delete请求
     *
     * @param url    完整的Http访问url
     * @param params 参数map
     * @return
     * @throws IOException
     */
    public static Response doDelete(String url, Map<String, String> params) throws Exception {
        LOGGER.debug("snowx http access | url:{} | method:{} | params:{}", url, "delete", params);
        url = joinParam(url, params);
        HttpURLConnection connection = getConnection(url);
        connection.setRequestMethod("DELETE");
        return execute(connection);
    }

    /**
     * 执行http连接请求，执行完毕后断开连接释放资源
     *
     * @param connection HttpURLConnection对象
     * @return
     * @throws IOException
     */
    private static Response execute(HttpURLConnection connection) throws IOException {
        InputStream inputStream = null;
        try {
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                inputStream = connection.getInputStream();
            } else {
                inputStream = connection.getErrorStream();
            }

            byte[] data = new byte[1024 * 8];
            int len;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            while ((len = inputStream.read(data)) != -1) {
                out.write(data, 0, len);
            }
            String responseString = new String(out.toByteArray(), "UTF-8");
            LOGGER.debug("snowx http access | status:{} | context:{}", responseCode, responseString);
            return new Response(responseCode, responseString);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    // ignore
                }
            }
            connection.disconnect();
        }
    }

    /**
     * 拼接请求参数
     *
     * @param url    http访问url
     * @param params 参数
     * @return
     * @throws UnsupportedEncodingException
     */
    private static String joinParam(String url, Map<String, String> params) throws UnsupportedEncodingException {

        if (params == null || params.size() == 0) {
            return url;
        }

        StringBuilder sb = new StringBuilder(url);
        if (!url.endsWith("?")) {
            sb.append("?");
        }

        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (!SnowXUtils.isAnyBlank(entry.getKey(), entry.getValue())) {
                sb.append(URLEncoder.encode(entry.getKey(), "UTF-8"))
                        .append("=")
                        .append(URLEncoder.encode(entry.getValue(), "UTF-8"))
                        .append("&");
            }
        }

        if ('&' == sb.charAt(sb.length() - 1)) {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }

    public static class Response {

        private Integer statusCode;
        private String responseContext;

        public Response() {
        }

        public Response(Integer statusCode, String responseContext) {
            this.statusCode = statusCode;
            this.responseContext = responseContext;
        }

        public Integer getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(Integer statusCode) {
            this.statusCode = statusCode;
        }

        public String getResponseContext() {
            return responseContext;
        }

        public void setResponseContext(String responseContext) {
            this.responseContext = responseContext;
        }

        public boolean isOk() {
            return this.statusCode != null && 200 == this.statusCode;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Response{");
            sb.append("statusCode=").append(statusCode);
            sb.append(", responseContext='").append(responseContext).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }
}
