package com.kernel.sense_log.domain.service;

import com.kernel.sense_log.common.exception.UnauthenticatedException;
import com.kernel.sense_log.domain.auth.jwt.JwtUtil;
import com.kernel.sense_log.domain.entity.User;
import com.kernel.sense_log.domain.repository.UserRepository;
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
            throw new UnauthenticatedException("User does not exist.");
        }

        if (!userServiceImpl.validatePassword(user.getPassword(), dbUser)) {
            throw new UnauthenticatedException("Incorrect password.");
        }

        return jwtUtil.createAccessToken(dbUser);
    }
}
