package com.kernel.sense_log.web.controller;

import com.kernel.sense_log.domain.service.AuthService;
import com.kernel.sense_log.web.dto.TokenResDTO;
import com.kernel.sense_log.web.dto.UserReqDTO;
import com.kernel.sense_log.domain.auth.jwt.JwtUtil;
import com.kernel.sense_log.domain.entity.User;
import com.kernel.sense_log.domain.service.UserServiceImpl;
import com.kernel.sense_log.web.dto.UserResDTO;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtUtil jwtUtil;
    private final UserServiceImpl userServiceImpl;
    private final AuthService authService;

    @GetMapping(value = "/me", produces = "application/json")
    public ResponseEntity<UserResDTO> loginCheck(final User loginUser) {
        return ResponseEntity.ok(UserResDTO.from(loginUser));
    }

    @PostMapping(value = "/join", produces = "application/json")
    public ResponseEntity<UserResDTO> createUser(@RequestBody UserReqDTO userReqDTO) {
        User joinUser = userReqDTO.toEntity();
        User joinedUser = userServiceImpl.saveUser(joinUser);
        return ResponseEntity.ok(UserResDTO.from(joinedUser));
    }

    @PostMapping(value = "/login", produces = "application/json")
    public ResponseEntity<TokenResDTO> login(@RequestBody UserReqDTO userReqDTO) {
        User user = userReqDTO.toEntity();
        Map<String, Object> result = authService.login(user);
        return ResponseEntity.ok(TokenResDTO.from(result));
    }
}