package com.example.shadow.domain.member.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
//@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본생성자 제한으로 누락 방지
@EntityListeners(AuditingEntityListener.class) // entity crud 전, 후 이벤트 처리
@Table(name = "member_tbl")
public class Member {

    @Id
    @Column(name="member_uid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="member_id", nullable = false, length = 20, unique = true)
    private String username;

    @Column(name = "member_role")
    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Column(name="member_pwd", nullable = false)
    private String password;

    @Column(name="member_name", nullable = false, length = 20)
    private String name;

    @Column(name="member_email")
    private String email;

    @Builder
    public Member(String username, String name, String password, String email) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public void updateUsername(String username){
        this.username = username;
    }
    public void updateName(String name){
        this.name = name;
    }
    public void updateEmail(String email){
        this.email = email;
    }
    public void setEncryptedPassword(String encryptedPassword) {
        this.password = encryptedPassword;
    }


}