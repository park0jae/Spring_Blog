package com.zerozae.blog.controller.exception;

import com.zerozae.blog.exception.ValidateTokenException;
import com.zerozae.blog.exception.auth.AccessDeniedException;
import com.zerozae.blog.exception.auth.AuthenticationEntryPointException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ExceptionController {

    @GetMapping("/exception/access-denied")
    public void accessDenied() {
        throw new AccessDeniedException();
    }

    @GetMapping("/exception/entry-point")
    public void authenticateException() {
        throw new AuthenticationEntryPointException();
    }

    @GetMapping("/exception/invalid-token")
    public void validateTokenException() {
        throw new ValidateTokenException();
    }

}
