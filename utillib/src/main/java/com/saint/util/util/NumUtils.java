package com.saint.util.util;

import java.text.DecimalFormat;

public class NumUtils {
    private static DecimalFormat decimalFormat = new DecimalFormat("#0.00");

    public static String toDisNum(double num) {
        return decimalFormat.format(num) + "km";
    }
}
