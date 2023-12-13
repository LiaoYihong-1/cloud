package com.example.demo.service;


import com.example.demo.dao.model.Member;
import com.example.demo.dao.repository.MemberRepository;
import com.example.demo.dto.MemberReceiver;
import com.example.demo.dto.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MyLoginUserDetailsService implements UserDetailsService {
    @Autowired
    private MemberRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username == null || "".equals(username)){
            return null;
        }
        List<String> permissions = Arrays.asList("user");;
        List<Member> members = repository.findAllByName(username);
        if(members.isEmpty()){
           throw new NoSuchElementException("No account with this username");
        }
        return new MyUserDetails(members.get(0),permissions);
    }
}