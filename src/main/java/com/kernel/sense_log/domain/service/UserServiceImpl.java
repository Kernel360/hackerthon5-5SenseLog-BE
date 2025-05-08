package com.kernel.sense_log.domain.service;


import com.kernel.sense_log.domain.auth.PasswordEncoder;
import com.kernel.sense_log.domain.entity.User;
import com.kernel.sense_log.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Transactional
    public User saveUser(User user) {
        // 이메일 중복 검사
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null) {
            throw new com.kernel.sense_log.common.exception.DuplicateEmailException("이미 사용 중인 이메일입니다: " + user.getEmail());
        }
        
        // 비밀번호 암호화
        user.encodePassword(PasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Transactional
    public boolean validatePassword(String rawPassword, User user) {
        return PasswordEncoder.matches(rawPassword, user.getPassword());
    }
}
