package com.kernel.sense_log.domain.service;

import com.kernel.sense_log.domain.entity.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
    Optional<User> findById(Long userId);
}
