package com.example.shadow.service;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberSecurityService implements UserDetailsService {

    private final MemberRepository memberRepository;


    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        Member member = findByMemberId(memberId);
        List<GrantedAuthority> authorities = getAuthorities(member);
        return new User(member.getMemberId(), member.getMemberPwd(), authorities);
    }

    private Member findByMemberId(String memberId){
        Optional<Member> _member= this.memberRepository.findByMemberId(memberId);
        if (_member.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
        }
        return _member.get();
    }
    private List<GrantedAuthority> getAuthorities(Member member){
        String memberId=member.getMemberId();
        List<GrantedAuthority> authorities = new ArrayList<>();
        if ("admin".equals(memberId)) {
            authorities.add(new SimpleGrantedAuthority(MemberRole.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(MemberRole.MEMBER.getValue()));
        }
        return authorities;
    }

}
