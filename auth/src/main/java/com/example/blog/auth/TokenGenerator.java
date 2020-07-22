package com.example.blog.auth;

import java.security.MessageDigest;
import java.util.UUID;

public class TokenGenerator {
    public static String generateValue() {
        return generateValue(UUID.randomUUID().toString());
    }

    public static String generateValue(String param) {
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(param.getBytes());
            byte[] messageDigest = algorithm.digest();
        } catch (Exception e) {
            throw new MyException(ErrorEnum.)
        }
    }

}
