package com.example.shadow.domain.member;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_uid")
    private Long memberUid;

    @Column(name="member_name")
    private String memberName;

    @Column(unique = true,name="member_id")
    private String memberId;

    @Column(name="member_pwd")
    private String memberPwd;

    @Column(unique = true,name="member_email")
    private String memberEmail;

    @Column(name="member_login_status")
    private Boolean memberLoginStatus;

}
