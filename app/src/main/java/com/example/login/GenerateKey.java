package com.example.login;

import java.security.SecureRandom;

public class GenerateKey {

    private static SecureRandom random = new SecureRandom();

    private static final String NUMERIC = "0123456789";
    private static final String ALPHA = "abcdefghijklmnopqrstuvwxyz";

    public static String generateKey(){
        String result = "";

        for (int i = 0; i < 7; i++){
            int index = random.nextInt(NUMERIC.length());
            result += NUMERIC.charAt(index);
        }

        return result;
    }
}
