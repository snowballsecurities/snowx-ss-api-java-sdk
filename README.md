# 雪盈证券Open API Java SDK接入说明书

## 1. 接入准备

### 1.1开户入金

开发者在接入雪盈证券开发平台之前，需要提前开通雪盈账号。账号开通后，您可以自己的账号ID（以后统称为：account_id）作为您账号的唯一标识。

开户链接：<https://www.snowballsecurities.com/xy-account-open/phone-verify>

查看账号ID：登录雪盈证券APP，查找“我的-设置-账号与安全”，即可看到雪盈账号ID。

### 1.2申请密钥

获取自己的account_id后可以在雪盈官网-申请API，来注册开发者信息，注册后将获得您自己的专属密钥（以后统称为：secret_key）作为您登录雪盈开发平台的唯一凭证，请妥善保存。

注册地址：https://www.snowballsecurities.com

### 1.3引入代码

获取secret key后即可进行Java SDK接入，有以下几种获取SDK的方式：

maven：
```
<repositories>
        <repository>
                <id>jitpack.io</id>
                <url>https://jitpack.io</url>
        </repository>
</repositories>

<dependencies>
        <dependency>
                <groupId>com.github.snowballsecurities</groupId>
                <artifactId>snowx-ss-api-java-sdk</artifactId>
                <version>latest version</version>
        </dependency>
</dependencies>
```

github：https://github.com/snowballsecurities/snowx-ss-api-java-sdk

### 1.4环境说明

雪盈证券为API开发者提供两套环境，分别是sit测试环境和prod正式环境，现对这两套环境做分别说明。

**sit：**

环境名称：测试环境

访问域名：https://sandbox.snbsecurities.com

账号获取：联系雪盈客服获得

环境说明：雪盈提供了一批测试账号供开发者使用，这些账号的资金为虚拟资金。同一个账号可能出现多个开发者共用的现象，因此订单、持仓等数据可能与用户预期不符，出现此类问题请忽略。

**prod:**

环境名称：正式环境

访问域名：https://openapi.snbsecurities.com

账号获取：参考1.1和1.2

环境说明：用户账号、资金均为真实账号、资金，所做操作全部真实有效，请勿做测试操作。

## 2. 快速开始

### 2.1调用示例

根据业务划分，SDK提供了五个Provider类作为SDK工作的入口类，开发者通过创建Provider对象并调用其相关方法来完成相应的功能。以订单查询为例：

```
public static void main(String[] args) {

    // 雪盈API URL前缀，可以通过这个参数控制访问正式环境还是测试环境
    String urlPrefix = "https://sandbox.snbsecurities.com";
    
    // 用户account id
    String accountId = "DU876749";
    
    // 用户 secret key
    String secretKey = "123456";
    
    // JSON转换器，SDK提供基于FastJson的实现，用户可以自行实现
    SnowXJsonConverter converter = new SnowXFastJsonConverter();
    
    // 创建Provider对象，所有Provider的创建方式都是一样的
    SnowXOrderProvider provider = new SnowXOrderProvider(urlPrefix, accountId, secretKey,converter);

    try {
    
        // 方法参数，订单id
        String orderId = "sdk000000001";
        
        // 所有Provider的方法中，accessToken都是不是必填的；当用户传递该参数时，SDK将使用传入的token访问API；如果不传递该参数，SDK将自动获取token
        String accessToken = null;
        
        // 调用Provider中的方法，完成订单查询
        OrderResult order = provider.getOrderById( orderId, accessToken);
        
        System.out.println(order);
        
    } catch (SnowXException sx) {
        /* 
         * Provider的方法只会抛出SnowXException，用户可以在sx里获得：
         *   httpStatus: API调用时的http状态码，如200等；获取方式：sx.getHttpStatus();
         *   resultCode: API返回的错误码；获取方式：sx.getResultCode();
         *   resultMsg:  API返回的错误提示信息；获取方式：sx.getResultMsg();
         *
         * 用户可以结合httpStatus和resultCode排查错误原因，如果遇到任何问题，可以将这三个信息和日志中的异常堆栈信息提供给客服以获取帮助
         */
        logger.error("get order exception.", sx);
    }
}
```

**Provider方法概览：**


| 类名                  | 方法名             | 描述                                   |
|-----------------------|--------------------|----------------------------------------|
| SnowXAuthProvider     | getAccessToken     | 接返回一个可用token，会使用本地缓存    |
|                       | createAccessToken  | 访问API生成一个新token，不会使用缓存   |
|                       | accessToken        | 查询token，一般用于查询token的过期时间 |
|                       | clearExpiryToken   | 静态方法，用于清除缓存中的过期token    |
| SnowXOrderProvider    | placeOrder         | 下单                                   |
|                       | getOrderById       | 订单查询，单条                         |
|                       | getOrderList       | 订单查询，批量                         |
|                       | cancelOrder        | 撤销订单                               |
| SnowXAssetProvider    | getPositionList    | 持仓查询                               |
|                       | getBalance         | 资产查询                               |
| SnowXSecurityProvider | getSecurityDetail  | 证券信息查询                           |
| SnowXTradeProvider    | getTransactionList | 成交查询                               |

### 2.2 token管理

token是一串无序加密的字符串，形如:pwQxtqj3Bl1q3ThX3I5rRJyUyQxffWX9。在访问API时，用户需携带该token作为身份凭证。用户获取token的个数没有限制，但服务器仅为每个用户保存10个有效token，再用户连续申请第11个token时，第一个token开始失效，以此类推。

SnowXAuthProvider中封装了token相关的方法，createAccessToken方法会直接访问API获取一个Auth对象，包含了token和过期时间，且token不会加入本地缓存，由用户自行管理。getAccessToken方法直接返回一个token字符串，由SDK自动管理，调用时会优先从本地缓存中获取，本地缓存无可用token才会去访问API，缓存为内存级别，JVM退出内存失效。

### 2.3日志管理

SDK采用sfl4j + logback的方式输出日志，并在classpath的logback-test.xml来配置日志打印，用户可以通过在classpath下添加logback.xml来覆盖这些配置。

## 方法描述

### 3.1 SnowXAuthProvider

#### createAccessToken

方法说明：访问API服务，创建新的token，不会访问本地缓存

方法参数：无

返回结果：

| 变量名      | 类型   | 必填 | 描述                                       | 最小支持版本 |
|-------------|--------|------|--------------------------------------------|--------------|
| accessToken | String | 是   | 用户token                                  | 1.0.0        |
| expiryTime  | Long   | 是   | 过期时间，expiryTime小于当前时间毫秒值失效 | 1.0.0        |

API返回示例：
```
{
    "result_code": "60000",
    "msg": null,
    "result_data": {
        "access_token": "9fk3HoyaD2EgxAC1sPg1dP2QjOwJDPSn",
        "expiry_time": 1591696693584
    }
}

```

#### getAccessToken

方法说明：获取token，如果缓存中存在可用token则直接返回，如果缓存中无可用token则访问API创建新token并加入缓存

方法参数：无

返回结果：

| 变量名 | 类型   | 必填 | 描述      | 最小支持版本 |
|--------|--------|------|-----------|--------------|
|        | String | 是   | 用户token | 1.0.0        |

API返回示例：不直接访问API，缓存无可用值时调用createAccessToken


#### accessToken

方法说明：查询token信息，一般用于获取token的过期时间

方法参数：

| 变量名      | 类型   | 必填 | 描述      | 最小支持版本 |
|-------------|--------|------|-----------|--------------|
| accessToken | String | 是   | 用户token | 1.0.0        |

返回结果：

| 变量名      | 类型   | 必填 | 描述                                             | 最小支持版本 |
|-------------|--------|------|--------------------------------------------------|--------------|
| accessToken | String | 是   | 用户token                                        | 1.0.0        |
| expiryTime  | Long   | 是   | 过期时间，expiryTime小于当前时间毫秒值时过期失效 | 1.0.0        |

API返回示例：
```
{
    "result_code": "60000",
    "msg": null,
    "result_data": {
        "access_token": "9fk3HoyaD2EgxAC1sPg1dP2QjOwJDPSn",
        "expiry_time": 1591696693584
    }
}
```


#### clearExpiryToken

方法说明：静态方法，清除本地缓存中过期的的token，如果用户SDK长期运行在后台JVM进程中，如web项目，则需要用户定期清除过期token，否则可能内存溢出；

方法参数：无

返回结果：无

API返回示例：无

### 3.2 SnowXOrderProvider

#### placeOrder

方法说明：下单

方法参数：SnowXOrderParameter.CreateParameter对象，CreateParameter属性如下

| 变量名          | 类型            | 父级            | 必填 | 描述                                  | 最小支持版本 |
|-----------------|-----------------|-----------------|------|---------------------------------------|--------------|
| createParameter | CreateParameter |                 | 是   | 下单参数对象                          | 1.0.0        |
| id              | String          | createParameter | 是   | 订单id，字母或者数字组成，1-20位      | 1.0.0        |
| securityType    | enum            | createParameter | 是   | 证券类型，参见数据字典：SecurityType  | 1.0.0        |
| symbol          | String          | createParameter | 是   | 证券代码                              | 1.0.0        |
| exchange        | String          | createParameter | 是   | 交易所代码                            | 1.0.0        |
| orderType       | enum            | createParameter | 是   | 订单类型，参见：数据字典OrderType     | 1.0.0        |
| side            | enum            | createParameter | 是   | 交易方向，参见：数据字典OrderSide     | 1.0.0        |
| currency        | enum            | createParameter | 是   | 币种，参见：数据字典Currency          | 1.0.0        |
| quantity        | Integer         | createParameter | 是   | 交易数量 ，最小为1                    | 1.0.0        |
| price           | Double          | createParameter | 否   | 限价下单必传，市价下单传任何均为0     | 1.0.0        |
| tif             | enum            | createParameter | 否   | 订单有效期，参见：数据字典TimeInForce | 1.0.0        |
| rth             | Boolean         | createParameter | 否   | 是否仅限盘中交易                      | 1.0.0        |
| accessToken     | String          |                 | 否   | 用户token，可不传                     | 1.0.0        |

返回结果：

| 变量名 | 类型   | 必填 | 描述                              | 最小支持版本 |
|--------|--------|------|-----------------------------------|--------------|
| id     | String | 是   | 订单ID                            | 1.0.0        |
| status | enum   | 是   | 订单状态，参见数据字典：OrderType | 1.0.0        |
| memo   | String | 否   | 服务器提示信息                    | 1.0.0        |

API返回示例：

```
{
    "result_code": "60000",
    "msg": null,
    "result_data": {
        "memo": "",
        "id": "sdk000000021",
        "status": "REPORTED"
    }
}
```

#### getOrderById

方法说明：订单查询，单条

方法参数：

| 变量名      | 类型   | 必填 | 描述                             | 最小支持版本 |
|-------------|--------|------|----------------------------------|--------------|
| id          | String | 是   | 订单ID，字母或者数字组成，1-20位 | 1.0.0        |
| accessToken | String | 否   | 用户token，可不传                | 1.0.0        |

返回结果：

| 变量名         | 类型    | 必填 | 描述                                  | 最小支持版本 |
|----------------|---------|------|---------------------------------------|--------------|
| id             | String  | 是   | 订单ID，字母或者数字组成，1-20位      | 1.0.0        |
| accountId      | String  | 是   | 账号ID                                | 1.0.0        |
| securityType   | enum    | 是   | 证券类型，参见：数据字典SecurityType  | 1.0.0        |
| symbol         | String  | 是   | 证券代码                              | 1.0.0        |
| exchange       | String  | 是   | 交易所代码                            | 1.0.0        |
| orderType      | enum    | 是   | 订单类型，参见：数据字典OrderType     | 1.0.0        |
| side           | enum    | 是   | 交易方向，参见：数据字典OrderSide     | 1.0.0        |
| currency       | enum    | 是   | 币种，参见：数据字典Currency          | 1.0.0        |
| quantity       | Integer | 是   | 交易数量 ，最小为1                    | 1.0.0        |
| price          | Double  | 否   | 价格,不传默认为0，如果传递不能小于0   | 1.0.0        |
| tif            | enum    | 否   | 订单有效期，参见：数据字典TimeInForce | 1.0.0        |
| rth            | Boolean | 否   | 是否仅限盘中交易                      | 1.0.0        |
| status         | enum    | 是   | 订单状态，参见:数据字典OrderStatus    | 1.0.0        |
| filledQuantity | Integer | 否   | 已成交数量                            | 1.0.0        |
| orderTime      | Long    | 否   | 下单时间，毫秒值                      | 1.0.0        |

API返回示例：

```
{
    "result_code": "60000",
    "msg": null,
    "result_data": {
        "id": "sdk000000050",
        "account_id": "DU876749",
        "security_type": "STK",
        "symbol": "00700",
        "exchange": "HKEX",
        "order_type": "MARKET",
        "side": "BUY",
        "currency": "HKD",
        "quantity": 100,
        "price": 0.0,
        "tif": "DAY",
        "rth": true,
        "status": "CONCLUDED",
        "filled_quantity": 100,
        "order_time": 1591768595000
    }
}
```


#### getOrderList

方法说明：订单查询，批量

方法参数：

| 名称          | 必填 | 类型    | 描述                                                                             | 最小支持版本 |
|---------------|------|---------|----------------------------------------------------------------------------------|--------------|
| page          | 否   | Integer | 页码，从1开始计数，默认值：1                                                     | 1.0.0        |
| size          | 否   | Integer | 每页条数，最大500，默认值：10                                                    | 1.0.0        |
| status        | 否   | enum    | 订单状态，参见:数据字典OrderStatus，支持输入REPORTED, CONCLUDED, WITHDRAWED, ALL | 1.0.0        |
| securityTypes | 否   | enum[]  | 证券类型，数组，参见:数据字典SecurityType                                        | 1.0.0        |
| accessToken   | 否   | String  | 用户token，可不传                                                                | 1.0.0        |

返回结果:

| 名称           | 父级  | 必填 | 类型    | 描述                                  | 最小支持版本 |
|----------------|-------|------|---------|---------------------------------------|--------------|
| page           |       | 是   | int     | 页码                                  | 1.0.0        |
| size           |       | 是   | int     | 每页显示条数                          | 1.0.0        |
| count          |       | 是   | long    | 总条数                                | 1.0.0        |
| items          |       | 是   | List    | 订单list                              | 1.0.0        |
| id             | items | 是   | string  | 订单ID，字母或者数字组成，1-20位      | 1.0.0        |
| accountId      | items | 是   | string  | 账号ID                                | 1.0.0        |
| securityType   | items | 是   | string  | 证券类型，参见：数据字典SecurityType  | 1.0.0        |
| symbol         | items | 是   | string  | 证券代码                              | 1.0.0        |
| exchange       | items | 是   | string  | 交易所代码                            | 1.0.0        |
| orderType      | items | 是   | enum    | 订单类型，参见：数据字典OrderType     | 1.0.0        |
| side           | items | 是   | enum    | 交易方向，参见：数据字典OrderSide     | 1.0.0        |
| currency       | items | 是   | enum    | 币种，参见：数据字典Currency          | 1.0.0        |
| quantity       | items | 是   | int     | 交易数量 ，最小为1                    | 1.0.0        |
| price          | items | 否   | double  | 价格,不传默认为0，如果传递不能小于0   | 1.0.0        |
| tif            | items | 否   | enum    | 订单有效期，参见：数据字典TimeInForce | 1.0.0        |
| rth            | items | 否   | boolean | 是否仅限盘中交易                      | 1.0.0        |
| status         | items | 是   | enum    | 订单状态，参见:数据字典OrderStatus    | 1.0.0.0      |
| filledQuantity | items | 是   | Integer | 已成交数量                            | 1.0.0        |
| orderTime      | items | 是   | long    | 下单时间，毫秒值                      | 1.0.0        |

API返回示例：

```
{
    "result_code": "60000",
    "msg": null,
    "result_data": {
        "page": 1,
        "size": 2,
        "count": 703,
        "items": [
            {
                "id": "sdk000000021",
                "account_id": "DU876749",
                "security_type": "STK",
                "symbol": "OIS",
                "exchange": "USEX",
                "order_type": "LIMIT",
                "side": "BUY",
                "currency": "USD",
                "quantity": 12,
                "price": 12.01,
                "tif": "DAY",
                "rth": false,
                "status": "WITHDRAWED",
                "filled_quantity": 0,
                "order_time": 1591608905000
            },
            ...
        ]
    }
}
```


#### cancelOrder

方法说明：撤单

方法参数:

| 变量名      | 必填 | 类型   | 描述                                 | 最小支持版本 |
|-------------|------|--------|--------------------------------------|--------------|
| id          | 是   | string | 被撤订单ID，字母或者数字组成，1-20位 | 1.0.0        |
| newId       | 是   | string | 新订单ID，字母或者数字组成，1-20位   | 1.0.0        |
| accessToken | 否   | string | 用户token，可不传                    | 1.0.0        |

返回结果:

| 变量名 | 必填 | 类型   | 描述                                 | 最小支持版本 |
|--------|------|--------|--------------------------------------|--------------|
| id     | 是   | string | 新订单唯一标识                       | 1.0.0        |
| status | 是   | enum   | 新订单状态，参见:数据字典OrderStatus | 1.0.0        |

API返回示例：

```
{
    "result_code": "60000",
    "msg": null,
    "result_data": {
        "id": "sdk000000050",
        "status": "CONCLUDED"
    }
}
```


### 3.3 SnowXTradeProvider

#### getTransactionList

方法说明：成交查询

方法参数：

| 名称         | 必填 | 类型      | 描述                                               | 最小支持版本 |
|--------------|------|-----------|----------------------------------------------------|--------------|
| page         | 否   | Integer   | 页码，从1开始计数，默认值：1                       | 1.0.0        |
| size         | 否   | Integer   | 每页条数，最大500，默认值：10                      | 1.0.0        |
| side         | 否   | OrderSide | 交易方向，不填默认查询全部，参见:数据字典OrderSide | 1.0.0        |
| orderTimeMin | 否   | Long      | 下单时间左区间，时间戳（秒）                       | 1.0.0        |
| orderTimeMax | 否   | Long      | 下单时间右区间，时间戳（秒）                       | 1.0.0        |
| accessToken  | 否   | string    | 用户token，可不传                                  | 1.0.0        |

返回结果：

| 名称         | 父级  | 必填 | 类型    | 描述                    | 最小支持版本 |
|--------------|-------|------|---------|-------------------------|--------------|
| page         |       | 是   | int     | 页码                    | 1.0.0        |
| size         |       | 是   | int     | 每页显示条数            | 1.0.0        |
| count        |       | 是   | long    | 总条数                  | 1.0.0        |
| items        |       | 是   | List    | 多个ConcludedResult对象 | 1.0.0        |
| id           | items | 是   | string  | 订单唯一标识            | 1.0.0        |
| accountId    | items | 是   | string  | 账户ID                  | 1.0.0        |
| securityType | items | 是   | string  | 证券类型                | 1.0.0        |
| symbol       | items | 是   | string  | 证券代码                | 1.0.0        |
| exchange     | items | 是   | string  | 市场                    | 1.0.0        |
| orderType    | items | 是   | enum    | 订单类型                | 1.0.0        |
| side         | items | 是   | enum    | 买卖方向                | 1.0.0        |
| currency     | items | 是   | enum    | 币种                    | 1.0.0        |
| quantity     | items | 是   | int     | 成交数量                | 1.0.0        |
| price        | items | 否   | double  | 成交价格                | 1.0.0        |
| tif          | items | 否   | enum    | 订单有效期              | 1.0.0        |
| rth          | items | 否   | boolean | 仅限盘中交易            | 1.0.0        |
| status       | items | 是   | enum    | 订单状态                | 1.0.0        |
| tradeTime    | items | 是   | Long    | 成交时间                | 1.0.0        |
| orderTime    | items | 是   | Long    | 下单时间                | 1.0.0        |

API返回示例：

```
{
    "result_code": "60000",
    "msg": null,
    "result_data": {
        "page": 1,
        "size": 500,
        "count": 49,
        "items": [
            {
                "id": "",
                "account_id": "DU876749",
                "security_type": "CASH",
                "symbol": "CNH.HKD",
                "exchange": "USEX",
                "order_type": "MARKET",
                "side": "SELL",
                "currency": "HKD",
                "quantity": 106,
                "price": 1.1511,
                "tif": "DAY",
                "rth": false,
                "status": "CONCLUDED",
                "trade_time": 1548137811000,
                "order_time": 1548137811000
            },
               ...
        ]
    }
}
```


### 3.4 SnowXAssetProvider

#### getPositionList

方法说明：持仓查询

方法参数:

| 变量名        | 必填 | 类型   | 描述                                 | 最小支持版本 |
|---------------|------|--------|--------------------------------------|--------------|
| securityTypes | 否   | enum[] | 证券类型，参见：数据字典SecurityType | 1.0.0        |
| accessToken   | 否   | String | 用户token，可不传                    | 1.0.0        |

返回结果：

| 变量名       | 必填 | 类型   | 描述                                 | 最小支持版本 |
|--------------|------|--------|--------------------------------------|--------------|
| accountId    | 是   | string | 账户ID                               | 1.0.0        |
| securityType | 是   | enum   | 证券类型，参见数据字典：SecurityType | 1.0.0        |
| symbol       | 是   | string | 证券代码                             | 1.0.0        |
| exchange     | 是   | string | 市场                                 | 1.0.0        |
| position     | 是   | int    | 持仓数量                             | 1.0.0        |
| averagePrice | 是   | double | 持仓均价                             | 1.0.0        |
| marketPrice  | 是   | double | 市场价                               | 1.0.0        |
| realizedPnl  | 是   | double | 已实现盈亏                           | 1.0.0        |

API返回示例：

```
{
    "result_code": "60000",
    "msg": null,
    "result_data": [
        {
            "account_id": "DU876751",
            "security_type": "WAR",
            "symbol": "11389",
            "exchange": "HKEX",
            "position": 1000,
            "average_price": 0.183,
            "market_price": 0.435,
            "realized_pnl": 0.0
        },
        ...
    ]
}
```


#### getBalance

方法说明：用户资产查询

方法参数:

| 变量名      | 必填 | 类型   | 描述              | 最小支持版本 |
|-------------|------|--------|-------------------|--------------|
| accessToken | 否   | String | 用户token，可不传 | 1.0.0        |

返回结果：

| 变量名                         | 必填 | 类型   | 描述         | 最小支持版本 |
|--------------------------------|------|--------|--------------|--------------|
| netLiquidationValue            | 是   | double | 净资产       | 1.0.0        |
| equityWithLoanValue            | 是   | double | 总资产       | 1.0.0        |
| previousDayEquityWithLoanValue | 是   | double | 昨日总资产   | 1.0.0        |
| securitiesGrossPositionValue   | 是   | double | 证券总价值   | 1.0.0        |
| sma                            | 是   | double |              | 1.0.0        |
| cash                           | 是   | double | 账户金额     | 1.0.0        |
| currentAvailableFunds          | 是   | double | 可用资金     | 1.0.0        |
| currentExcessLiquidity         | 是   | double | 剩余流动性   | 1.0.0        |
| leverage                       | 是   | double | 杠杆，GPV/NL | 1.0.0        |
| currentInitialMargin           | 是   | double | 初始保证金   | 1.0.0        |
| currentMaintenanceMargin       | 是   | double | 维持保证金   | 1.0.0        |
| currency                       | 是   | enum   | 币种         | 1.0.0        |

API返回示例：

```
{
    "result_code": "60000",
    "msg": null,
    "result_data": {
        "net_liquidation_value": 1069506.35,
        "equity_with_loan_value": 1069506.35,
        "previous_day_equity_with_loan_value": 1067983.15,
        "securities_gross_position_value": 1337802.22,
        "sma": 420783.12,
        "cash": -259616.53,
        "current_available_funds": 58281.87,
        "current_excess_liquidity": 85321.77,
        "leverage": 1.25,
        "current_initial_margin": 1011224.48,
        "current_maintenance_margin": 984184.58,
        "currency": "USD"
    }
}
```


### 3.5 SnowXSecurityProvider

#### getSecurityDetail

方法说明：证券信息查询

方法参数:

| 变量名      | 必填 | 类型   | 描述                                          | 最小支持版本 |
|-------------|------|--------|-----------------------------------------------|--------------|
| symbols     | 是   | string | 证券代码，多个以半角逗号分隔,单次最多查询20个 | 1.0.0        |
| accessToken | 否   | String | 用户token，可不传                             | 1.0.0        |

返回结果：多个Security对象，单个Security属性如下

| 变量名   | 必填 | 类型   | 描述             | 最小支持版本 |
|----------|------|--------|------------------|--------------|
| symbol   | 是   | string | 证券代码         | 1.0.0        |
| tickSize | 是   | string | 最小报价单位     | 1.0.0        |
| lotSize  | 是   | string | 最小委托数量单位 | 1.0.0        |

API返回示例：

```
{
    "result_code": "60000",
    "msg": null,
    "result_data": [
        {
            "symbol": "OIS",
            "tick_size": "0.01",
            "lot_size": "1"
        }
    ]
}
```


## 4. 数据字典

SDK用到的枚举全部集中在SnowXConstant的内部类中，现对其各属性含义做如下说明

### Currency

| **名称** | **描述**     |
|----------|--------------|
| BASE     | 基础货币     |
| USX      |              |
| CNY      | 人民币       |
| USD      | 美元         |
| SEK      | 瑞典克朗     |
| SGD      | 新加坡币     |
| TRY      | 土耳其里拉   |
| ZAR      | 南非兰特     |
| JPY      | 日元         |
| AUD      | 澳元         |
| CAD      | 加币         |
| CHF      | 瑞士法郎     |
| CNH      | 人民币       |
| HKD      | 港币         |
| NZD      | 新西兰币     |
| CZK      | 捷克克朗     |
| DKK      | 丹麦克朗     |
| HUF      | 匈牙利福林   |
| NOK      | 挪威克朗     |
| PLN      | 波兰兹罗提   |
| EUR      | 欧元         |
| GBP      | 英镑         |
| ILS      | 以色列谢克尔 |
| MXN      | 墨西哥比索   |
| RUB      | 卢布         |
| KRW      | 韩元         |

### SecurityType

| **名称** | **描述**           |
|----------|--------------------|
| STK      | 股票               |
| FUT      | 期货               |
| OPT      | 期权               |
| FOP      | 期货期权           |
| WAR      | 涡轮               |
| MLEG     | 不支持             |
| CASH     | 外汇               |
| CFD      | 差价合约           |
| CMDTY    | 大宗商品           |
| FUND     | 基金               |
| IOPT     | 牛熊证             |
| BOND     | 债券               |
| ALL      | 全部类型(查询条件) |

### OderType

| **名称**          | **描述**   |
|-------------------|------------|
| LIMIT             | 限价单     |
| MARKET            | 市价单     |
| AT                | 不支持     |
| ATL               | 不支持     |
| SSL               | 不支持     |
| SEL               | 不支持     |
| STOP              | 止损单     |
| STOP_LIMIT        | 限价止损单 |
| TRAIL             | 追踪单     |
| TRAIL_LIMIT       | 限价追踪单 |
| LIMIT_ON_OPENING  | 开市限价单 |
| MARKET_ON_OPENING | 开市市价单 |
| LIMIT_ON_CLOSE    | 闭市限价单 |
| MARKET_ON_CLOSE   | 闭市市价单 |

### OrderSide

| **名称** | **描述** |
|----------|----------|
| BUY      | 买入     |
| SELL     | 卖出     |

### OrderStatus

| **名称**           | **描述** |
|--------------------|----------|
| NO_REPORT          | 未报     |
| WAIT_REPORT        | 待报     |
| REPORTED           | 已报     |
| WAIT_WITHDRAW      | 已报待撤 |
| PART_WAIT_WITHDRAW | 部成待撤 |
| PART_WITHDRAW      | 部撤     |
| WITHDRAWED         | 已撤     |
| PART_CONCLUDED     | 部成     |
| CONCLUDED          | 已成     |
| INVALID            | 废单     |

### TimeInFource

| **名称** | **描述**   |
|----------|------------|
| DAY      | 当日有效   |
| GTC      | 撤单前有效 |

## 5. 更新日志

### V1.0.0

| **更新日期** | **更新内容** |
|--------------|--------------|
| 2020-06-08   | 初始化更新   |

## 6. 联系我们

如果您在使用过程中遇到任何问题，可以通过以下方式联系我们获取帮助，雪盈证券客服电话：400-118-8886。

