package com.zerozae.blog.service.member;

import com.zerozae.blog.config.security.jwt.provider.TokenProvider;
import com.zerozae.blog.domain.member.Member;
import com.zerozae.blog.domain.role.Role;
import com.zerozae.blog.domain.role.RoleType;
import com.zerozae.blog.dto.member.LoginRequestDto;
import com.zerozae.blog.dto.member.LoginResponseDto;
import com.zerozae.blog.dto.member.MemberCreateRequestDto;
import com.zerozae.blog.exception.auth.LoginFailureException;
import com.zerozae.blog.exception.member.DuplicateNicknameException;
import com.zerozae.blog.exception.member.DuplicateUsernameException;
import com.zerozae.blog.exception.member.MemberNotFoundException;
import com.zerozae.blog.exception.role.RoleNotFoundException;
import com.zerozae.blog.repository.member.MemberRepository;
import com.zerozae.blog.repository.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SignService {
    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;


    @Transactional
    public void signUp(MemberCreateRequestDto requestDto){
        validateDuplicateUsernameAndNickname(requestDto);
        List<Role> roles = List.of(roleRepository.findByRoleType(RoleType.USER).orElseThrow(RoleNotFoundException::new));

        Member member = new Member(
                requestDto.getUsername(),
                passwordEncoder.encode(requestDto.getPassword()),
                requestDto.getNickname(),
                "None",
                roles);

        memberRepository.save(member);
    }

    @Transactional
    public LoginResponseDto login(LoginRequestDto requestDto) {
        memberRepository.findByUsername(requestDto.getUsername()).orElseThrow(MemberNotFoundException::new);
        String accessToken = jwtLoginRequest(requestDto);
        return LoginResponseDto.toDto(accessToken);
    }

    @Transactional
    public String jwtLoginRequest(LoginRequestDto requestDto){
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(requestDto.getUsername(), requestDto.getPassword());

        try{
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String accessToken = tokenProvider.createAccessToken(authentication);

            if (!StringUtils.hasText(accessToken)) {
                throw new LoginFailureException();
            }
            return accessToken;
        }catch (BadCredentialsException e){
            throw new LoginFailureException();
        }
    }


    private void validateDuplicateUsernameAndNickname(MemberCreateRequestDto requestDto){
        if(memberRepository.existsMemberByUsername(requestDto.getUsername())){
            throw new DuplicateUsernameException();
        }else if(memberRepository.existsMemberByNickname(requestDto.getNickname())){
            throw new DuplicateNicknameException();
        }
    }

}
