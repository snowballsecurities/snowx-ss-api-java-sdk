/*
 * Copyright (c) 2020-2029, Snowball Securities and/or its affiliates. All rights reserved.
 * Snowball Securities PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.snowballsecurities.snowx.api.sdk.provider;

import com.snowballsecurities.snowx.api.sdk.domain.paramter.SnowXOrderParameter.PlaceOrderParameter;
import com.snowballsecurities.snowx.api.sdk.domain.result.SnowXOrderResult.*;
import com.snowballsecurities.snowx.api.sdk.domain.result.SnowXPageResult;
import com.snowballsecurities.snowx.api.sdk.util.SnowXJsonConverter;
import com.snowballsecurities.snowx.api.sdk.exception.SnowXException;

import java.util.HashMap;
import java.util.Map;

import com.snowballsecurities.snowx.api.sdk.constant.SnowXConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description: 订单Provider
 * @author: snowx developer
 * @create: 2020-05-15 18:31
 **/
public class SnowXOrderProvider extends AbstractProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(SnowXOrderProvider.class);

    private static final String ORDER_ID_REGEX = "[0-9A-Za-z]{1,20}";

    private SnowXAuthProvider snowXAuthProvider;

    public SnowXOrderProvider(String urlPrefix, String accountId, String secretKey, SnowXJsonConverter converter) {
        super(urlPrefix, accountId, converter);
        this.snowXAuthProvider = new SnowXAuthProvider(urlPrefix, accountId, secretKey, converter);
    }

    /**
     * 下单
     *
     * @param parameter   订单参数对象
     * @param accessToken 访问token，不传自动获取
     * @return
     * @throws SnowXException
     */
    public PlaceOrderResult placeOrder(PlaceOrderParameter parameter, String accessToken) throws SnowXException {
        ResultData resultData = new ResultData();
        try {
            if (SnowXUtils.isAnyBlank(parameter, parameter.getId(), parameter.getSecurityType(), parameter.getSymbol(),
                    parameter.getExchange(), parameter.getOrderType(), parameter.getSide(), parameter.getCurrency(),
                    parameter.getQuantity()))
            {
                throw new IllegalArgumentException("required parameter cannot be blank.");
            }

            if ((parameter.getPrice() != null && parameter.getPrice() < 0) || parameter.getQuantity() < 1) {
                throw new IllegalArgumentException("price min 0, quantity min 1.");
            }

            if (!parameter.getId().matches(ORDER_ID_REGEX)) {
                throw new IllegalArgumentException("order id must be a character or number, 1-20 in length.");
            }

            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("account_id", accountId);
            paramMap.put("security_type", parameter.getSecurityType().name());
            paramMap.put("symbol", parameter.getSymbol());
            paramMap.put("exchange", parameter.getExchange());
            paramMap.put("order_type", parameter.getOrderType().name());
            paramMap.put("side", parameter.getSide().name());
            paramMap.put("currency", parameter.getCurrency().name());
            paramMap.put("quantity", parameter.getQuantity().toString());
            paramMap.put("price", parameter.getPrice() != null ? parameter.getPrice().toString() : null);
            paramMap.put("tif", parameter.getTif() != null ? parameter.getTif().toString() : null);
            paramMap.put("rth", parameter.getRth() != null ? parameter.getRth().toString() : null);
            paramMap.put("parent", parameter.getParent() != null ? parameter.getParent() : null);
            paramMap.put("stop_price", parameter.getStopPrice() != null ? parameter.getStopPrice().toString() : null);

            if (SnowXUtils.isBlank(accessToken)) {
                paramMap.put("access_token", snowXAuthProvider.getAccessToken());
            } else {
                paramMap.put("access_token", accessToken);
            }

            String url = urlPrefix.concat("/order/{order_id}").replace("{order_id}", parameter.getId());
            SnowXHttpUtils.Response response = SnowXHttpUtils.doPost(url, paramMap);
            resultData = handleResponse(response);
            if (resultData.isSuccess()) {
                return converter.convertToBean(resultData.getResultData(), PlaceOrderResult.class);
            }
            throw new SnowXException(resultData.getHttpStatus(), resultData.getResultCode(), resultData.getMsg());
        } catch (SnowXException sx) {
            LOGGER.error("place order exception", sx);
            throw sx;
        } catch (Throwable t) {
            LOGGER.error("place order exception", t);
            throw new SnowXException(resultData.getHttpStatus(), resultData.getResultCode(), resultData.getMsg(), t);
        }
    }

    /**
     * 单条订单查询
     *
     * @param id          订单ID
     * @param accessToken 访问token，不传自动获取
     * @return
     * @throws SnowXException
     */
    public OrderResult getOrderById(String id, String accessToken) throws SnowXException {
        ResultData resultData = new ResultData();
        try {
            if (SnowXUtils.isBlank(id)) {
                throw new IllegalArgumentException("required parameter cannot be blank.");
            }

            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("account_id", accountId);

            if (SnowXUtils.isBlank(accessToken)) {
                paramMap.put("access_token", snowXAuthProvider.getAccessToken());
            } else {
                paramMap.put("access_token", accessToken);
            }

            String url = urlPrefix.concat("/order/{order_id}").replace("{order_id}", id);
            SnowXHttpUtils.Response response = SnowXHttpUtils.doGet(url, paramMap);
            resultData = handleResponse(response);
            if (resultData.isSuccess()) {
                return converter.convertToBean(resultData.getResultData(), OrderResult.class);
            }
            throw new SnowXException(resultData.getHttpStatus(), resultData.getResultCode(), resultData.getMsg());
        } catch (SnowXException sx) {
            LOGGER.error("get order by id exception, order_id:{}", id, sx);
            throw sx;
        } catch (Throwable t) {
            LOGGER.error("get order by id exception, order_id:{}", id, t);
            throw new SnowXException(resultData.getHttpStatus(), resultData.getResultCode(), resultData.getMsg(), t);
        }
    }

    /**
     * 多条订单查询
     *
     * @param status        筛选条件，订单状态，可传值为:REPORTED, CONCLUDED, WITHDRAWED, ALL
     * @param securityTypes 证券类型
     * @param page          当前页数，最小为1
     * @param size          每页条数，最小为1，最大为500
     * @param accessToken   访问token，不传自动获取
     * @return
     * @throws SnowXException
     */
    public SnowXPageResult<OrderResult> getOrderList(
            SnowXConstant.OrderStatus status,
            SnowXConstant.SecurityType[] securityTypes,
            Integer page,
            Integer size,
            String accessToken
    ) throws SnowXException {
        ResultData resultData = new ResultData();
        try {
            // 参数校验，最大查询到第100页，每页最多500条，查询状态只有（REPORTED,CONCLUDED,WITHDRAWED,ALL）四种
            if (page != null && page < 1) {
                throw new IllegalArgumentException("page must be greater than 0.");
            }
            if (size != null && (size < 1 || size > 500)) {
                throw new IllegalArgumentException("size value range of 1-500.");
            }

            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("account_id", this.accountId);
            paramMap.put("status", status != null ? status.name() : null);
            paramMap.put("security_type", SnowXUtils.join(securityTypes));
            paramMap.put("size", size != null ? size.toString() : null);
            paramMap.put("page", page != null ? page.toString() : null);

            if (SnowXUtils.isBlank(accessToken)) {
                paramMap.put("access_token", snowXAuthProvider.getAccessToken());
            } else {
                paramMap.put("access_token", accessToken);
            }

            SnowXHttpUtils.Response response = SnowXHttpUtils.doGet(urlPrefix.concat("/order"), paramMap);
            resultData = handleResponse(response);
            if (resultData.isSuccess()) {
                return converter.convertToPage(resultData.getResultData(), OrderResult.class);
            }

            throw new SnowXException(resultData.getHttpStatus(), resultData.getResultCode(), resultData.getMsg());
        } catch (SnowXException sx) {
            LOGGER.error("get order list exception", sx);
            throw sx;
        } catch (Throwable t) {
            LOGGER.error("get order list exception", t);
            throw new SnowXException(resultData.getHttpStatus(), resultData.getResultCode(), resultData.getMsg(), t);
        }
    }

    /**
     * 撤单
     *
     * @param id          被撤订单id
     * @param newId       新订单id
     * @param accessToken 访问token，不传自动获取
     * @return
     * @throws SnowXException
     */
    public CancelResult cancelOrder(String id, String newId, String accessToken) throws SnowXException {
        ResultData resultData = new ResultData();
        try {
            if (SnowXUtils.isAnyBlank(id, newId)) {
                throw new IllegalArgumentException("required parameter cannot be blank.");
            }
            if (!newId.matches(ORDER_ID_REGEX) || !id.matches(ORDER_ID_REGEX)) {
                throw new IllegalArgumentException("order id must be a character or number, 8-10 in length.");
            }

            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("new_id", newId);
            paramMap.put("account_id", accountId);

            if (SnowXUtils.isBlank(accessToken)) {
                paramMap.put("access_token", snowXAuthProvider.getAccessToken());
            } else {
                paramMap.put("access_token", accessToken);
            }

            String url = urlPrefix.concat("/order/{order_id}").replace("{order_id}", id);
            SnowXHttpUtils.Response response = SnowXHttpUtils.doDelete(url, paramMap);
            resultData = handleResponse(response);

            if (resultData.isSuccess()) {
                return converter.convertToBean(resultData.getResultData(), CancelResult.class);
            }
            throw new SnowXException(resultData.getHttpStatus(), resultData.getResultCode(), resultData.getMsg());
        } catch (SnowXException sx) {
            LOGGER.error("cancel order exception", sx);
            throw sx;
        } catch (Throwable t) {
            LOGGER.error("cancel order exception", t);
            throw new SnowXException(resultData.getHttpStatus(), resultData.getResultCode(), resultData.getMsg(), t);
        }
    }

}
