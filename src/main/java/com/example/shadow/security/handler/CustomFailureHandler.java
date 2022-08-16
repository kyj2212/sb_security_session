package com.example.shadow.security.handler;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {

        String message = "Invaild Username or Password";

        if (exception instanceof BadCredentialsException) {
            message = "Invaild Username or Password";
        } else if (exception instanceof InsufficientAuthenticationException) {
            message = "Invalid Secret Key";
        }

        setDefaultFailureUrl("/member/login?error=true");

        super.onAuthenticationFailure(request, response, exception);

    }
}
