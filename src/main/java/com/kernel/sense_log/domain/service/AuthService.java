package com.kernel.sense_log.domain.service;

import com.kernel.sense_log.common.exception.UnauthenticatedException;
import com.kernel.sense_log.domain.auth.jwt.JwtUtil;
import com.kernel.sense_log.domain.entity.User;
import com.kernel.sense_log.domain.repository.UserRepository;
import com.kernel.sense_log.web.dto.UserResDTO;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtUtil jwtUtil;
    private final UserServiceImpl userServiceImpl;
    private final UserRepository userRepository;

        public String login(User user) {
        User dbUser = userRepository.findByEmail(user.getEmail());
        
        if (dbUser == null) {
            throw new UnauthenticatedException("해당 이메일의 사용자가 존재하지 않습니다.");
        }

        if (!userServiceImpl.validatePassword(user.getPassword(), dbUser)) {
            throw new UnauthenticatedException("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtUtil.createAccessToken(dbUser);
        return token;
    }
}
