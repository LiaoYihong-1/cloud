package com.example.demo.controller;

import com.example.demo.dto.TeamReceiver;
import com.example.demo.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@RestController
public class TeamController {
    @Autowired
    private TeamService service;
    @PostMapping("/team")
    @PreAuthorize("hasAnyAuthority('user')")
    public ResponseEntity<?> createTeam(@RequestBody TeamReceiver t){
        return service.createTeam(t);
    }
    @PostMapping("/team/{member}/{team}")
    @PreAuthorize("hasAnyAuthority('user')")
    public ResponseEntity<?> addMember(@PathVariable Integer member,@PathVariable Integer team){
        return service.addMember(member,team);
    }
    @DeleteMapping("/team/{member}/{team}")
    @PreAuthorize("hasAnyAuthority('user')")
    public ResponseEntity<?> deleteMember(@PathVariable Integer member,@PathVariable Integer team){
        return service.deleteMember(member,team);
    }
    @DeleteMapping("/team/{team}")
    @PreAuthorize("hasAnyAuthority('user')")
    public ResponseEntity<?> deleteTeam(@PathVariable Integer team){
        return service.deleteTeam(team);
    }
}
