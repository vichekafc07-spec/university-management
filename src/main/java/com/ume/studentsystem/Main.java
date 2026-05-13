package com.ume.studentsystem;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Main {
    public static void main(String[] args) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String rawPassword = "12345678";

        String encodedPassword = encoder.encode(rawPassword);

        System.out.println("Raw Password : " + rawPassword);
        System.out.println("Encoded Password : " + encodedPassword);
    }
}
