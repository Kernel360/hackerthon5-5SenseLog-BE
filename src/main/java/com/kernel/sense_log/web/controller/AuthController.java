package com.kernel.sense_log.web.controller;

import com.kernel.sense_log.common.dto.ResponseDTO;
import com.kernel.sense_log.common.dto.ResultObject;
import com.kernel.sense_log.domain.auth.jwt.JwtUtil;
import com.kernel.sense_log.domain.entity.User;
import com.kernel.sense_log.domain.service.AuthService;
import com.kernel.sense_log.domain.service.UserServiceImpl;
import com.kernel.sense_log.web.dto.LoginResDTO;
import com.kernel.sense_log.web.dto.UserReqDTO;
import com.kernel.sense_log.web.dto.UserResDTO;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.kernel.sense_log.common.exception.ResultType.UNAUTHORIZED;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtUtil jwtUtil;
    private final UserServiceImpl userServiceImpl;
    private final AuthService authService;

    @GetMapping(value = "/me", produces = "application/json")
    public ResponseDTO<UserResDTO> loginCheck(final User loginUser) {
        log.info("loginUser : {} ", loginUser);
        log.info("user_id : {} ", loginUser.getId());
        return ResponseDTO.ok(UserResDTO.from(loginUser));
    }

    @PostMapping(value = "/join", produces = "application/json")
    public ResponseDTO<UserResDTO> createUser(@RequestBody UserReqDTO userReqDTO) {
        User joinUser = userReqDTO.toEntity();
        User joinedUser = userServiceImpl.saveUser(joinUser);
        return ResponseDTO.ok(UserResDTO.from(joinedUser));
    }

    @PostMapping(value = "/login", produces = "application/json")
    public ResponseDTO<LoginResDTO> login(@RequestBody UserReqDTO userReqDTO, HttpServletResponse response) {
        User user = userReqDTO.toEntity();
        String token = authService.login(user);

        if (token == null) {
            return new ResponseDTO<>(new ResultObject(UNAUTHORIZED, "Login failed: token is null"));
        }

        jwtUtil.addTokenToCookie(response, token);
        return ResponseDTO.ok(LoginResDTO.from(user));
    }


}