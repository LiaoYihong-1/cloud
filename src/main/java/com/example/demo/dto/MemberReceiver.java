package com.example.demo.dto;

import com.example.demo.dao.model.Member;
import lombok.Data;

import javax.persistence.Column;
@Data
public class MemberReceiver {
    private String name;
    private String nickname;
    private String password;
    public static MemberReceiver covert(Member m){
        MemberReceiver r = new MemberReceiver();
        r.setName(m.getName());
        r.setPassword(m.getPassword());
        r.setNickname(m.getNickname());
        return r;
    }
}
