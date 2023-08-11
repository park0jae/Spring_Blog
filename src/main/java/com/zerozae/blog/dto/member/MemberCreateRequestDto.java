package com.zerozae.blog.dto.member;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCreateRequestDto {

    @NotBlank(message = "이메일을를 입력해주세요.")
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
            message = "비밀번호는 최소 8자리이면서 1개 이상의 알파벳, 숫자, 특수문자를 포함해야합니다.")
    private String password;

    @Length(min = 2, max = 15, message = "닉네임은 최소 2자, 최대 15자로 설정할 수 있습니다.")
    private String nickname;



}
