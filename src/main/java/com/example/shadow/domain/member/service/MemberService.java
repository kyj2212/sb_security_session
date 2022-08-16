package com.example.shadow.domain.member.service;

import com.example.shadow.domain.member.entity.Member;
import com.example.shadow.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member create(String name, String username, String password, String email) {
        Member member = new Member();
        member.updateName(name);
        member.updateUsername(username);
        member.setEncryptedPassword(passwordEncoder.encode(password));
        member.updateEmail(email);
        this.memberRepository.save(member);
        return member;
    }
}