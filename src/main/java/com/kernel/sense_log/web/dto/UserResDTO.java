package com.kernel.sense_log.web.dto;

import com.kernel.sense_log.domain.entity.User;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserResDTO {

    @Column(nullable = false, length = 255)
    private String email;

    @Column(nullable = false, length = 50)
    private String nickname;

    @Builder
    public UserResDTO(String email, String nickname) {
        this.email = email;
        this.nickname = nickname;
    }
    
    public static UserResDTO from(User user) {
        return UserResDTO.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .build();
    }
}