package com.zerozae.blog.dto.member;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginResponseDto {

    private String accessToken;

    public static LoginResponseDto toDto(String accessToken){
        return new LoginResponseDto(accessToken);
    }

}
