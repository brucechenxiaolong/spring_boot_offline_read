package com.pde.pdes.offline.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class BaseTools {

    public static String base64Encoder(String str) {
        return Base64.getUrlEncoder().encodeToString(str.getBytes(StandardCharsets.UTF_8));
    }

    public static String base64Decoder(String str) {
        return new String(Base64.getUrlDecoder().decode(str), StandardCharsets.UTF_8);
    }
}
