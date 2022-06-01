package com.example.springsecurityintro;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * パスワードをBCryptでハッシュ化して表示します。
 */
public class PasswordGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("general"));
        System.out.println(passwordEncoder.encode("admin"));
    }
}
