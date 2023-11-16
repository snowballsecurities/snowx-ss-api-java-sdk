/*
 * Copyright (c) 2020-2029, Snowball Securities and/or its affiliates. All rights reserved.
 * Snowball Securities PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.snowballsecurities.snowx.api.sdk.test;

import com.snowballsecurities.snowx.api.sdk.constant.SnowXConstant;
import com.snowballsecurities.snowx.api.sdk.domain.paramter.SnowXOrderParameter;
import com.snowballsecurities.snowx.api.sdk.domain.result.SnowXOrderResult;
import com.snowballsecurities.snowx.api.sdk.domain.result.SnowXOrderResult.*;
import com.snowballsecurities.snowx.api.sdk.domain.result.SnowXPageResult;
import com.snowballsecurities.snowx.api.sdk.exception.SnowXException;
import com.snowballsecurities.snowx.api.sdk.provider.SnowXAssetProvider;
import com.snowballsecurities.snowx.api.sdk.provider.SnowXOrderProvider;
import com.snowballsecurities.snowx.api.sdk.util.SnowXFastJsonConverter;
import com.snowballsecurities.snowx.api.sdk.util.SnowXJsonConverter;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @description: 订单测试类
 * @author: snowx developer
 * @create: 2020-05-18 16:50
 **/
public class SnowXOrderProviderTest {

    private static final Logger logger = LoggerFactory.getLogger(SnowXOrderProviderTest.class);

    private SnowXOrderProvider provider;

    @Before
    public void createClient() {
        String urlPrefix = "https://sandbox.snbsecurities.com";
        String accountId = "DU123456";
        String secretKey = "123456789";
        SnowXJsonConverter converter = new SnowXFastJsonConverter();
        provider = new SnowXOrderProvider(urlPrefix, accountId, secretKey, converter);
    }

    @Test
    public void placeMitOrder() {
        SnowXOrderParameter.PlaceOrderParameter parameter = new SnowXOrderParameter.PlaceOrderParameter();
        String oid = String.valueOf(System.currentTimeMillis());
        parameter.setId(oid);
        parameter.setSecurityType(SnowXConstant.SecurityType.STK);
        parameter.setSymbol("AAPL");
        parameter.setExchange("USEX");
        parameter.setOrderType(SnowXConstant.OrderType.LIMIT_IF_TOUCHED);
        parameter.setSide(SnowXConstant.OrderSide.BUY);
        parameter.setCurrency(SnowXConstant.Currency.USD);
        parameter.setQuantity(1);
        parameter.setPrice(100d);
        parameter.setStopPrice(100d);
        parameter.setTif(SnowXConstant.TimeInForce.DAY);
        parameter.setRth(Boolean.FALSE);

        try {
            SnowXOrderResult.PlaceOrderResult placeOrderResult = provider.placeOrder(parameter, null);
            logger.info("place order result: {}", placeOrderResult);
            assertNotNull(placeOrderResult);
            CancelResult cancelResult = provider.cancelOrder(oid, String.valueOf(System.currentTimeMillis()), null);
            logger.info("cancel order result: {}", cancelResult);
            assertNotNull(cancelResult);
        } catch (SnowXException sx) {
            logger.error("place order exception.", sx);
        }
    }
    @Test
    public void placeStopOrder() {
        SnowXOrderParameter.PlaceOrderParameter parameter = new SnowXOrderParameter.PlaceOrderParameter();
        String oid = String.valueOf(System.currentTimeMillis());
        parameter.setId(oid);
        parameter.setSecurityType(SnowXConstant.SecurityType.STK);
        parameter.setSymbol("AAPL");
        parameter.setExchange("USEX");
        parameter.setOrderType(SnowXConstant.OrderType.STOP_LIMIT);
        parameter.setSide(SnowXConstant.OrderSide.BUY);
        parameter.setCurrency(SnowXConstant.Currency.USD);
        parameter.setQuantity(1);
        parameter.setPrice(100d);
        parameter.setStopPrice(100d);
        parameter.setTif(SnowXConstant.TimeInForce.DAY);
        parameter.setRth(Boolean.FALSE);

        try {
            SnowXOrderResult.PlaceOrderResult placeOrderResult = provider.placeOrder(parameter, null);
            logger.info("place order result: {}", placeOrderResult);
            assertNotNull(placeOrderResult);
            CancelResult cancelResult = provider.cancelOrder(oid, String.valueOf(System.currentTimeMillis()), null);
            logger.info("cancel order result: {}", cancelResult);
            assertNotNull(cancelResult);
        } catch (SnowXException sx) {
            logger.error("place order exception.", sx);
        }
    }
    @Test
    public void placeGroupOrderScenarioOne() {
        SnowXOrderParameter.PlaceOrderParameter parameter = new SnowXOrderParameter.PlaceOrderParameter();
        String oid = String.valueOf(System.currentTimeMillis());
        parameter.setId(oid);
        parameter.setSecurityType(SnowXConstant.SecurityType.STK);
        parameter.setSymbol("AAPL");
        parameter.setExchange("USEX");
        parameter.setOrderType(SnowXConstant.OrderType.LIMIT);
        parameter.setSide(SnowXConstant.OrderSide.BUY);
        parameter.setCurrency(SnowXConstant.Currency.USD);
        parameter.setQuantity(1);
        parameter.setPrice(100d);
        parameter.setTif(SnowXConstant.TimeInForce.DAY);
        parameter.setRth(Boolean.FALSE);

        try {
            SnowXOrderResult.PlaceOrderResult placeOrderResult = provider.placeOrder(parameter, null);
            logger.info("place order result: {}", placeOrderResult);
            assertNotNull(placeOrderResult);

            parameter.setOrderType(SnowXConstant.OrderType.MARKET_IF_TOUCHED);
            parameter.setSide(SnowXConstant.OrderSide.SELL);
            parameter.setStopPrice(200d);
            parameter.setParent(oid);
            parameter.setId(String.valueOf(System.currentTimeMillis()));
            SnowXOrderResult.PlaceOrderResult placeOrderResultC1 = provider.placeOrder(parameter, null);
            logger.info("place order result: {}", placeOrderResultC1);
            assertNotNull(placeOrderResultC1);

            parameter.setOrderType(SnowXConstant.OrderType.STOP);
            parameter.setSide(SnowXConstant.OrderSide.SELL);
            parameter.setStopPrice(100d);
            parameter.setParent(oid);
            parameter.setId(String.valueOf(System.currentTimeMillis()));
            SnowXOrderResult.PlaceOrderResult placeOrderResultC2 = provider.placeOrder(parameter, null);
            logger.info("place order result: {}", placeOrderResultC2);
            assertNotNull(placeOrderResultC2);


            CancelResult cancelResult = provider.cancelOrder(oid, String.valueOf(System.currentTimeMillis()), null);
            logger.info("cancel order result: {}", cancelResult);
            assertNotNull(cancelResult);
        } catch (SnowXException sx) {
            logger.error("place order exception.", sx);
        }
    }

    @Test
    public void getOrderById() {
        try {
            SnowXOrderResult.OrderResult order = provider.getOrderById("sdk000000050", null);
        } catch (SnowXException sx) {
            logger.error("get order exception.", sx);
        }
    }

    @Test
    public void getOrderList() {
        try {
            // REPORTED,CONCLUDED,WITHDRAWED,ALL
            SnowXPageResult<SnowXOrderResult.OrderResult> orderList = provider.getOrderList(
                    SnowXConstant.OrderStatus.ALL,
                    null,
                    1,
                    500,
                    null
            );
        } catch (SnowXException sx) {
            logger.error("get order list exception", sx);
        }
    }

    @Test
    public void cancelOrder() {
        try {
            SnowXOrderResult.CancelResult cancelResult = provider.cancelOrder(
                    "sdk000000050",
                    "sdk0000000051",
                    null
            );
        } catch (SnowXException sx) {
            logger.error("cancel order exception", sx);
        }
    }

}
