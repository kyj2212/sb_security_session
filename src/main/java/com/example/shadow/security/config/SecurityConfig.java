package com.example.shadow.security.config;

import com.example.shadow.security.handler.CustomFailureHandler;
import com.example.shadow.security.handler.CustomSuccessHandler;
import com.example.shadow.security.service.MemberSecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] AUTH_WHITELIST_STATIC = {"/static/css/**", "/static/js/**", "/assert/*.ico"};
    private static final String[] AUTH_ADMIN_LIST = {"/admin"};
    private static final String[] AUTH_ALL_LIST = {"/test"};
    private static final String[] AUTH_AUTHENTICATED_LIST = {"/shadows/**", "/flowcharts/**", "/main/**"};

    private final MemberSecurityService memberSecurityService;


    @Override
    public void configure(WebSecurity web) throws Exception {
        // static 파일은 인증 무시 ( = 항상통과 )
        web.ignoring().antMatchers(AUTH_WHITELIST_STATIC);
    }


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers(AUTH_ADMIN_LIST).hasRole("ROLE_ADMIN")
            .antMatchers(AUTH_AUTHENTICATED_LIST).authenticated()
            .antMatchers(AUTH_ALL_LIST).permitAll();
        http
            .csrf().ignoringAntMatchers("/h2-console/**");
        http
            .headers()
            .addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN));
        http
            .formLogin()
            .loginPage("/login")
            .successHandler(customSuccessHandler());
        http
            .logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .logoutSuccessUrl("/test")
            .invalidateHttpSession(true);
        http
            .exceptionHandling()
            .accessDeniedPage("/restrict");

    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(customAuthenticationProvider)
//            .authenticationProvider(daoAuthenticationProvider());
        auth.userDetailsService(memberSecurityService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public AuthenticationSuccessHandler customSuccessHandler(){
        return new CustomSuccessHandler("/");
    }
    @Bean
    public AuthenticationFailureHandler customFailureHandler(){
        return new CustomFailureHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }




}