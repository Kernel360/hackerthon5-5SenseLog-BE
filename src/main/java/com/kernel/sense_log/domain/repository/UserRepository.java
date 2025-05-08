package com.kernel.sense_log.domain.repository;

import com.kernel.sense_log.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
