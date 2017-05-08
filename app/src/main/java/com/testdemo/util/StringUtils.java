package com.testdemo.util;

import java.util.Random;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 04 12 16:14
 * @DESC：
 */

public class StringUtils {

    public static String getRandomLenName(String name) {
        return getRandomLenName(name,10);
    }

    public static String getRandomLenName(String name, int randomLen) {
        Random random = new Random();
        int len = random.nextInt(randomLen) + 1;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < len; i++) {
            sb.append(name);
        }
        return sb.toString();
    }
}
