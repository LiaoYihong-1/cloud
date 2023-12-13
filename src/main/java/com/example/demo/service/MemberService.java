package com.example.demo.service;

import com.example.demo.dao.model.Member;
import com.example.demo.dao.model.Team;
import com.example.demo.dao.repository.MemberRepository;
import com.example.demo.dao.repository.TeamMemberRepository;
import com.example.demo.dao.repository.TeamRepository;
import com.example.demo.dto.MemberReceiver;
import com.example.demo.dto.MyUserDetails;
import com.example.demo.exception.InvalidParameterException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MemberService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MemberRepository repository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private TeamMemberRepository teamMemberRepository;
    public ResponseEntity<?> getAllMembers(){
        return ResponseEntity.ok(repository.findAll());
    }

    public ResponseEntity<?> register(MemberReceiver receiver) throws InvalidParameterException {
        List<Member> m = repository.findAllByName(receiver.getName());
        if(!m.isEmpty()){
            throw new InvalidParameterException("This user name is used");
        }
        Member newMember = new Member();
        newMember.convert(receiver);
        repository.save(newMember);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public ResponseEntity<?> login(MemberReceiver receiver) throws InvalidParameterException {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(receiver.getName(),receiver.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);
        if(Objects.isNull(authentication)){
            throw new ResourceNotFoundException("Failed login");
        }
        MyUserDetails details = (MyUserDetails) authentication.getPrincipal();
        Member securityUser = details.getUser();
        /**
         * token is made up of id + type
         */
        String userId = securityUser.getId().toString();
        /**
         * create token
         */
        String jwtToken = JwtUtils.createToken(userId);
        Map<String,String> tokenMap = new HashMap<>();
        tokenMap.put("token",jwtToken);
        return ResponseEntity.ok(tokenMap);
    }
    public ResponseEntity<?> quitFromTeam(Integer team) throws InvalidParameterException, ResourceNotFoundException{
        Member account = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Team> teamOptional = teamRepository.findById(team);
        if(teamOptional.isEmpty()){
            throw new NoSuchElementException("No this team");
        }
        if(!new TeamService().checkMemberInTeam(account.getId(),team)){
            throw new InvalidParameterException("You are not in this team");
        }
        teamMemberRepository.delete(teamMemberRepository.findTeamMemberByMemberAndTeam(account.getId(),team).get(0));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
