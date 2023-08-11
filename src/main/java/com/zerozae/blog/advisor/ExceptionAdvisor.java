package com.zerozae.blog.advisor;

import com.zerozae.blog.dto.response.Response;
import com.zerozae.blog.exception.ValidateTokenException;
import com.zerozae.blog.exception.auth.AccessDeniedException;
import com.zerozae.blog.exception.auth.AuthenticationEntryPointException;
import com.zerozae.blog.exception.auth.LoginFailureException;
import com.zerozae.blog.exception.member.DuplicateNicknameException;
import com.zerozae.blog.exception.member.DuplicateUsernameException;
import com.zerozae.blog.exception.member.MemberNotFoundException;
import com.zerozae.blog.exception.role.RoleNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvisor {

    @ExceptionHandler(RoleNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response roleNotFoundException(RoleNotFoundException e){
        return Response.failure(404, "권한을 찾을 수 없습니다.");
    }

    // auth
    @ExceptionHandler(LoginFailureException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Response loginFailureException(LoginFailureException e){
        return Response.failure(409, "로그인에 실패했습니다.");
    }

    @ExceptionHandler(ValidateTokenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response validateTokenException(ValidateTokenException e) {
        return Response.failure(400, "검증되지 않은 토큰 정보입니다.");
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response accessDeniedException(AccessDeniedException e){
        return Response.failure(400, "해당 권한으로 수행할 수 없는 작업입니다.");
    }

    @ExceptionHandler(AuthenticationEntryPointException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response authenticationEntryPointException(AuthenticationEntryPointException e){
        return Response.failure(400, "로그인이 필요한 요청입니다.");
    }

    // Member
    @ExceptionHandler(MemberNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response memberNotFoundException(MemberNotFoundException e){
        return Response.failure(404, "해당 유저를 찾을 수 없습니다.");
    }

    @ExceptionHandler(DuplicateUsernameException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Response duplicateUsernameException(DuplicateUsernameException e){
        return Response.failure(409, "이미 존재하는 계정입니다.");
    }

    @ExceptionHandler(DuplicateNicknameException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Response duplicateNicknameException(DuplicateNicknameException e){
        return Response.failure(409, "이미 존재하는 닉네임 입니다.");
    }


}
