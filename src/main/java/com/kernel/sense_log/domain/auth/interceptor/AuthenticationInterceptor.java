package com.kernel.sense_log.domain.auth.interceptor;

import com.kernel.sense_log.common.exception.ExpiredTokenException;
import com.kernel.sense_log.common.exception.InvalidTokenException;
import com.kernel.sense_log.common.exception.UnauthenticatedException;
import com.kernel.sense_log.domain.auth.jwt.JwtInterceptorHelper;
import com.kernel.sense_log.domain.auth.jwt.JwtUtil;
import com.kernel.sense_log.domain.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {
    private final JwtUtil jwtUtil;
    private final JwtInterceptorHelper jwtInterceptorHelper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        try {
            String accessToken = jwtInterceptorHelper.extractAccessTokenFromRequest(request);
            User loginUser = jwtUtil.getLoginUserFromAccessToken(accessToken);
            request.setAttribute("loginUser", loginUser);
        } catch (ExpiredTokenException ete) {
            // TODO : 만료된 토큰으로 리프레시 토큰으로 토큰 재발급하는 로직을 짜야함
            throw new UnauthenticatedException("Token is expired");
        } catch (InvalidTokenException ite) {
            throw new UnauthenticatedException();
        }
        return true;
    }
}
