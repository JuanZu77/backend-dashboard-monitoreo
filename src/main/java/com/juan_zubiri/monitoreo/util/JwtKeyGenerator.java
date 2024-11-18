package com.juan_zubiri.monitoreo.util;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

public class JwtKeyGenerator {
    public static void main(String[] args) {
        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256); // debo generar una clave segura
        System.out.println(java.util.Base64.getEncoder().encodeToString(secretKey.getEncoded())); // y mostrarla en Base64
    }
}

//No olvidar que debo ejecurlo individualmente