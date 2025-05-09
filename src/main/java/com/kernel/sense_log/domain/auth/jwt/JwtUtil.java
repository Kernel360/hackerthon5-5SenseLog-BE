package com.kernel.sense_log.domain.auth.jwt;

import com.kernel.sense_log.common.exception.ExpiredTokenException;
import com.kernel.sense_log.common.exception.InvalidTokenException;
import com.kernel.sense_log.domain.entity.User;
import com.kernel.sense_log.domain.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final UserRepository userRepository;

    @Value("${env.secure-cookie}")
    private boolean secureCookie;

    private static final String SECRET_KEY = "c294d9d9ac58c5e3c816ccf1c185c745092ff30be8f4d72ba1d7a5d99d2e3aa5";
    private static final long EXPIRATION_TIME_MS = 1000 * 60 * 60 * 24L; // 1일
    private static final String USER_EMAIL_KEY = "userNo";
    private static final String USER_NICKNAME_KEY = "userId";
    private static final String USER_ID_KEY = "userIdNum";

    private final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    /**
     * 액세스 토큰 생성
     */
    public String createAccessToken(User loginUser) {
        return createAccessToken(loginUser, EXPIRATION_TIME_MS);
    }

    public String createAccessToken(User loginUser, long expirationTimeMs) {
        log.info("Creating JWT for user: {} (ID: {})", loginUser.getEmail(), loginUser.getId());

        return Jwts.builder()
                .claim(USER_EMAIL_KEY, loginUser.getEmail())
                .claim(USER_NICKNAME_KEY, loginUser.getNickname())
                .claim(USER_ID_KEY, loginUser.getId() != null ? loginUser.getId().toString() : null)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTimeMs))
                .signWith(key)
                .compact();
    }

    /**
     * 토큰에서 유저 정보 추출
     */
    public User getLoginUserFromAccessToken(String accessToken) {
        Claims claims = getClaims(accessToken);

        String userIdStr = claims.get(USER_ID_KEY, String.class);
        Long userId = null;

        if (userIdStr != null) {
            try {
                userId = Long.parseLong(userIdStr);
            } catch (NumberFormatException e) {
                log.warn("Invalid userId format in token: {}", userIdStr);
            }
        }

        return new User(
                claims.get(USER_EMAIL_KEY, String.class),
                claims.get(USER_NICKNAME_KEY, String.class),
                userId
        );
    }

    /**
     * 토큰에서 Claims 추출
     */
    private Claims getClaims(String accessToken) {
        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(accessToken)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            throw new ExpiredTokenException();
        } catch (Exception e) {
            throw new InvalidTokenException();
        }
    }

    /**
     * 쿠키에 토큰 저장
     */
    public void addTokenToCookie(HttpServletResponse response, String token) {
        log.debug("[JWT] 쿠키에 토큰 추가 시도");

        String cookie = String.format(
                "AUTH_ACCESS_TOKEN=%s; Path=/; HttpOnly; Max-Age=%d%s%s",
                token,
                EXPIRATION_TIME_MS / 1000,
                secureCookie ? "; Secure" : "",
                "; SameSite=Lax" // CORS 허용 필요시 강제
        );

        response.addHeader("Set-Cookie", cookie);
    }

    /**
     * 로그아웃용 쿠키 제거
     */
    public void removeTokenFromCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("AUTH_ACCESS_TOKEN", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(secureCookie);

        response.addCookie(cookie);
    }
}
