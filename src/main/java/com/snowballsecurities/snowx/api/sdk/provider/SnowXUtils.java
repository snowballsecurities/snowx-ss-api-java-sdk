/*
 * Copyright (c) 2020-2029, Snowball Securities and/or its affiliates. All rights reserved.
 * Snowball Securities PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.snowballsecurities.snowx.api.sdk.provider;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * @description: internal use only
 * @author: snowx developer
 * @create: 2020-05-18 10:25
 **/
class SnowXUtils {

    public static boolean isBlank(String string) {
        if (string == null || string.trim().length() == 0) {
            return true;
        }
        return false;
    }

    private static boolean isBlank(Collection c) {
        if (c == null || c.size() == 0) {
            return true;
        }
        return false;
    }

    public static boolean isAnyBlank(String... arr) {
        if (arr == null || arr.length == 0) {
            return true;
        }
        for (String item : arr) {
            if (isBlank(item)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAnyBlank(Object... arr) {
        if (arr == null || arr.length == 0) {
            return true;
        }

        for (Object o : arr) {
            if (o == null) {
                return true;
            } else if (o instanceof String) {
                if (isBlank((String) o)) {
                    return true;
                }
            } else if (o instanceof Collections) {
                if (isBlank((Collection) o)) {
                    return true;
                }
            } else if (o.getClass().isArray()) {
                if (Array.getLength(o) == 0) {
                    return true;
                }
            } else if (o instanceof Map) {
                if (((Map) o).size() == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String join(Object... arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == null) {
                continue;
            }
            Object o = arr[i];
            String value = o instanceof Enum ? ((Enum) o).name() : o.toString();
            sb.append(value);
            if (i != arr.length - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

}
