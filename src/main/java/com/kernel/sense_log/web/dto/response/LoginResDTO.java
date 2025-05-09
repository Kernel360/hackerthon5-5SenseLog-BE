package com.kernel.sense_log.web.dto.response;

import com.kernel.sense_log.domain.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginResDTO {

    private Long userId;

    @Builder
    public LoginResDTO(Long userId) {
        this.userId = userId;
    }

    public static LoginResDTO from(User user) {
        return LoginResDTO.builder()
                .userId(user.getId())
                .build();
    }
}
