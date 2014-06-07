package com.me.tft_02.zeldachickens.util;

import java.util.Random;

public class Misc {

    public static boolean activationSuccessful(int chance) {
        Random random = new Random();
        return (random.nextInt(100) < chance);
    }
}
