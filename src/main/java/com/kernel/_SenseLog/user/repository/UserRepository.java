package com.kernel._SenseLog.user.repository;

import com.kernel._SenseLog.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
