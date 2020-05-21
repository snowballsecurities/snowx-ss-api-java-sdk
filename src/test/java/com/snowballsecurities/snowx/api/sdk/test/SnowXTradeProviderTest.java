package com.snowballsecurities.snowx.api.sdk.test;

import com.snowballsecurities.snowx.api.sdk.constant.SnowXConstant;
import com.snowballsecurities.snowx.api.sdk.domain.result.SnowXOrderResult;
import com.snowballsecurities.snowx.api.sdk.domain.result.SnowXPageResult;
import com.snowballsecurities.snowx.api.sdk.exception.SnowXException;
import com.snowballsecurities.snowx.api.sdk.provider.SnowXTradeProvider;
import com.snowballsecurities.snowx.api.sdk.util.SnowXFastJsonConverter;
import com.snowballsecurities.snowx.api.sdk.util.SnowXJsonConverter;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description: 交易测试类
 * @author: BYFei
 * @create: 2020-05-21 11:40
 **/
public class SnowXTradeProviderTest {

    private static final Logger logger = LoggerFactory.getLogger(SnowXSecurityProviderTest.class);

    private SnowXTradeProvider provider;

    @Before
    public void createClient() {
        String urlPrefix = "https://sandbox.snbsecurities.com";
        String accountId = "xxxxxx";
        String secretKey = "123456";
        SnowXJsonConverter converter = new SnowXFastJsonConverter();
        provider = new SnowXTradeProvider(urlPrefix, accountId, secretKey, converter);
    }

    @Test
    public void getTransactionList() {
        try {
            SnowXPageResult<SnowXOrderResult.ConcludedResult> transactionList = provider.getTransactionList(
                    SnowXConstant.OrderSide.SELL,
                    null,
                    null,
                    1,
                    500,
                    null
            );
        } catch (SnowXException sx) {
            logger.error("get transaction list exception.", sx);
        }
    }
}
