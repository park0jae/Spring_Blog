package com.zerozae.blog.controller.member;

import com.zerozae.blog.dto.member.LoginRequestDto;
import com.zerozae.blog.dto.member.MemberCreateRequestDto;
import com.zerozae.blog.dto.response.Response;
import com.zerozae.blog.service.member.SignService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
public class SignController {

    private final SignService signService;

    @PostMapping("/auth/sign-up")
    @ResponseStatus(CREATED)
    public Response signUp(@Valid @RequestBody MemberCreateRequestDto memberCreateRequestDto){
        signService.signUp(memberCreateRequestDto);
        return Response.success(CREATED.value());
    }

    @PostMapping("/auth/login")
    @ResponseStatus(OK)
    public Response login(@Valid @RequestBody LoginRequestDto loginRequestDto){
        return Response.success(OK.value(), signService.login(loginRequestDto));
    }
}
