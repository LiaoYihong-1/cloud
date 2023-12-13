package com.example.demo.controller;

import com.example.demo.dto.MemberReceiver;
import com.example.demo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class MemberController {
    @Autowired
    private MemberService memberService;

    @PostMapping("/members/register")
    public ResponseEntity<?> register(@RequestBody MemberReceiver receiver){
        return memberService.register(receiver);
    }
    @GetMapping("/members/login")
    public ResponseEntity<?> login(@RequestBody MemberReceiver receiver){
        return memberService.login(receiver);
    }

    @DeleteMapping("/member/{team}")
    @PreAuthorize("hasAnyAuthority('user')")
    public ResponseEntity<?> quitTeam(@PathVariable Integer team){
        return memberService.quitFromTeam(team);
    }

}
