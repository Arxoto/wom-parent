package com.example.util;

import java.util.Random;

public class RandomStringUtil {
    public static final Random random = new Random();
    public static final String randomCollections = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private static char getRandomChar() {
        String str = RandomStringUtil.randomCollections;
        int number = random.nextInt(str.length());
        return str.charAt(number);
    }

    private static void appendRandomName(StringBuilder stringBuilder) {
        int len = random.nextInt(3, 6);
        for (int j = 0; j < len; j++) {
            stringBuilder.append(getRandomChar());
        }
    }

    public static String getRandomName() {
        StringBuilder stringBuilder = new StringBuilder();
        appendRandomName(stringBuilder);
        stringBuilder.append('_');
        appendRandomName(stringBuilder);

        return stringBuilder.toString();
    }
}
