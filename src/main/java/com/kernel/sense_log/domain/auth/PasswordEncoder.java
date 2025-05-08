package com.kernel.sense_log.domain.auth;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncoder {

    // 비밀번호 암호화
    public static String encode(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12)); // 12는 비용 요소(cost factor)
    }

    // 비밀번호 검증
    public static boolean matches(String rawPassword, String encodedPassword) {
        return BCrypt.checkpw(rawPassword, encodedPassword);
    }
}