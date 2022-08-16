package com.example.shadow.security.service;

import com.example.shadow.domain.member.entity.Member;
import com.example.shadow.domain.member.entity.MemberRole;
import com.example.shadow.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberSecurityService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("사용자을 찾을 수 없습니다."));

        List<GrantedAuthority> authorities = new ArrayList<>();

        if (("admin").equals(username)) {
            authorities.add(new SimpleGrantedAuthority(MemberRole.ADMIN.getValue()));
            //member.setRole(MemberRole.ADMIN);
        } else {
            authorities.add(new SimpleGrantedAuthority(MemberRole.MEMBER.getValue()));
            //member.setRole(MemberRole.MEMBER);
        }
        return new User(member.getUsername(), member.getPassword(), authorities);
    }

}
