/*
 * Copyright (c) 2020-2029, Snowball Securities and/or its affiliates. All rights reserved.
 * Snowball Securities PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.snowballsecurities.snowx.api.sdk.provider;

import com.snowballsecurities.snowx.api.sdk.constant.SnowXConstant;
import com.snowballsecurities.snowx.api.sdk.domain.result.SnowXOrderResult.ConcludedResult;
import com.snowballsecurities.snowx.api.sdk.domain.result.SnowXPageResult;
import com.snowballsecurities.snowx.api.sdk.exception.SnowXException;
import com.snowballsecurities.snowx.api.sdk.util.SnowXJsonConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: Trade provider
 * @author: snowx developer
 * @create: 2020-05-21 11:29
 **/
public class SnowXTradeProvider extends AbstractProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(SnowXTradeProvider.class);

    private SnowXAuthProvider snowXAuthProvider;

    public SnowXTradeProvider(String urlPrefix, String accountId, String secretKey, SnowXJsonConverter converter) {
        super(urlPrefix, accountId, converter);
        this.snowXAuthProvider = new SnowXAuthProvider(urlPrefix, accountId, secretKey, converter);
    }

    /**
     * 成交订单查询
     *
     * @param side         交易方向
     * @param orderTimeMin 下单时间左区间
     * @param orderTimeMax 下单时间右区间
     * @param page         当前页数，最小为1
     * @param size         每页条数，最小为1，最大为500
     * @param accessToken  访问token，不传自动获取
     * @return
     * @throws SnowXException
     */
    public SnowXPageResult<ConcludedResult> getTransactionList(
            SnowXConstant.OrderSide side,
            Long orderTimeMin,
            Long orderTimeMax,
            Integer page,
            Integer size,
            String accessToken
    ) throws SnowXException {
        ResultData resultData = new ResultData();
        try {
            if (page != null && page < 1) {
                throw new IllegalArgumentException("page must be greater than 0.");
            }
            if (size != null && (size < 1 || size > 500)) {
                throw new IllegalArgumentException("size value range of 1-500.");
            }

            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("account_id", accountId);
            paramMap.put("side", side != null ? side.name() : null);
            paramMap.put("order_time_min", orderTimeMin != null ? orderTimeMin.toString() : null);
            paramMap.put("order_time_max", orderTimeMax != null ? orderTimeMax.toString() : null);
            paramMap.put("page", page != null ? page.toString() : null);
            paramMap.put("size", size != null ? size.toString() : null);

            if (SnowXUtils.isBlank(accessToken)) {
                paramMap.put("access_token", snowXAuthProvider.getAccessToken());
            } else {
                paramMap.put("access_token", accessToken);
            }

            SnowXHttpUtils.Response response = SnowXHttpUtils.doGet(urlPrefix.concat("/trade"), paramMap);
            resultData = handleResponse(response);

            if (resultData.isSuccess()) {
                return converter.convertToPage(resultData.getResultData(), ConcludedResult.class);
            }
            throw new SnowXException(resultData.getHttpStatus(), resultData.getResultCode(), resultData.getMsg());
        } catch (SnowXException sx) {
            LOGGER.error("get transaction list exception", sx);
            throw sx;
        } catch (Throwable t) {
            LOGGER.error("get transaction list exception", t);
            throw new SnowXException(resultData.getHttpStatus(), resultData.getResultCode(), resultData.getMsg(), t);
        }
    }
}
