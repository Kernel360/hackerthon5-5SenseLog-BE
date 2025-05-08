package com.kernel.sense_log.domain.auth.jwt;

import com.kernel.sense_log.common.exception.ExpiredTokenException;
import com.kernel.sense_log.common.exception.InvalidTokenException;
import com.kernel.sense_log.domain.entity.User;
import com.kernel.sense_log.domain.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {
    private final UserRepository userRepository;

    // 터미널에 `openssl rand -hex 32` 명령어 입력시 랜덤한 값을 얻을수 있다.
    private static final String secretKey = "c294d9d9ac58c5e3c816ccf1c185c745092ff30be8f4d72ba1d7a5d99d2e3aa5";

    private SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

    private static final Long EXPIRATION_TIME_MS = 1000 * 60 * 60 * 24L; // 밀리세컨이라 1000 * 60초 * 60분 * 24시 => 하루
    private static final String USER_EMAIL_KEY_NAME = "userNo";
    private static final String USER_NICKNAME_KEY_NAME = "userId";
    private static final String USER_ID_KEY_NAME = "userIdNum";

    /**
     * 액세스 토큰생성해주는 메서드
     *   별일 없으면 이걸 사용하세요
     * @param loginUser
     * @return
     */
    public String createAccessToken(final User loginUser) {
        return this.createAccessToken(loginUser, EXPIRATION_TIME_MS);
    }

    /**
     * 액세스 토큰생성해주는 메서드 (만료시간을 파라미터로 받는 오버로딩된 메서드)
     *  굳이 만료시간을 다르게 가져가야할 경우만 사용하도록 오버로딩해둠
     *  되도록이면  createAccessToken()를 사용해서 토큰생성바람
     * @param loginUser
     * @param expirationTimeMs
     * @return
     */
    public String createAccessToken(final User loginUser, final long expirationTimeMs) {
        // 디버깅을 위한 로그 추가
        log.info("Creating token for user: {}", loginUser);
        log.info("User ID being stored in token: {}", loginUser.getId());
        
        String token = Jwts.builder()
                .claim(USER_EMAIL_KEY_NAME, loginUser.getEmail())
                .claim(USER_NICKNAME_KEY_NAME, loginUser.getNickname())
                .claim(USER_ID_KEY_NAME, loginUser.getId() != null ? loginUser.getId().toString() : null) // ID를 문자열로 저장
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTimeMs))
                .signWith(key)
                .compact();
        log.debug("created token : {} ", token);
        return token;
    }

    /**
     * 액세스 토큰에서 로그인유저정보 꺼내오기
     * @param accessToken
     * @return
     */
    public User getLoginUserFromAccessToken(final String accessToken) {
        Claims claims = getClaims(accessToken);
        
        // 디버깅을 위한 로그 추가
        log.info("Claims from token: {}", claims);
        
        // 문자열로 가져와서 Long으로 변환
        String userIdStr = claims.get(USER_ID_KEY_NAME, String.class);
        log.info("User ID string from token: '{}'", userIdStr);
        
        Long userId = null;
        if (userIdStr != null) {
            try {
                userId = Long.parseLong(userIdStr);
                log.info("Parsed user ID: {}", userId);
            } catch (NumberFormatException e) {
                log.warn("Failed to parse user ID from token: {}", userIdStr);
            }
        } else {
            log.warn("User ID not found in token");
        }
        
        User user = new User(
            claims.get(USER_EMAIL_KEY_NAME, String.class), 
            claims.get(USER_NICKNAME_KEY_NAME, String.class),
            userId
        );
        log.info("Created User object: {}", user);
        
        return user;
    }

    /**
     * 토큰으로부터 클레임 꺼내기 (예외처리를 위해 별도 메서드로 분리시킴)
     * @param accessToken
     * @return
     */
    private Claims getClaims(final String accessToken) {
        Claims claims ;
        try {
            claims = Jwts.parser()
                    .verifyWith(key) // 단순히 key 타입만 검증하더라...
                    .build()
                    .parseSignedClaims(accessToken)
                    .getPayload();
        } catch(ExpiredJwtException eje) { // 만료된 토큰일 경우 발생하는 Exception
            throw new ExpiredTokenException(); // 내가 만든 Exception으로 바꿔서 던짐 -> 리프레시토큰 로직으로 분기되어야함
        } catch(Exception e) { // 기타 나머지(변조되었거나, 형식이 안맞거나 등등등)는 퉁쳐서 비정상 토큰으로 간주
            throw new InvalidTokenException();
        }
        return claims;
    }
}
