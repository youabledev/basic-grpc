package com.youable.bank_server.common.util;

import java.math.BigDecimal;

public class BigDecimalConverter {
    public static BigDecimal fromString(String value) {
        return new BigDecimal(value);
    }

    public static String toString(BigDecimal value) {
        return value.toPlainString();
    }
}
