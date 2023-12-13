package com.example.demo.service;

import com.example.demo.dao.model.Member;
import com.example.demo.dao.model.Team;
import com.example.demo.dao.model.TeamMember;
import com.example.demo.dao.repository.MemberRepository;
import com.example.demo.dao.repository.TeamMemberRepository;
import com.example.demo.dao.repository.TeamRepository;
import com.example.demo.dao.type.Identity;
import com.example.demo.dto.TeamReceiver;
import com.example.demo.exception.InvalidParameterException;
import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private TeamMemberRepository teamMemberRepository;
    @Transactional
    public ResponseEntity<?> createTeam(TeamReceiver receiver){
        Member account = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Team t = new Team();
        t.setCreator(account.getId());
        t.setName(receiver.getName());
        teamRepository.save(t);
        TeamMember teamMember = new TeamMember();
        teamMember.setTeam(teamRepository.findById(teamRepository.findTeamByMaxId()).get().getId());
        teamMember.setMember(account.getId());
        teamMember.setIdentity(Identity.ADMIN);
        teamMemberRepository.save(teamMember);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @Transactional
    public ResponseEntity<?> addMember(Integer member, Integer team) throws NoSuchElementException, InvalidParameterException {
        Member account = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Team> teamOptional = teamRepository.findById(team);
        Optional<Member> memberOptional = memberRepository.findById(member);
        if(teamOptional.isEmpty() || memberOptional.isEmpty()){
            throw new NoSuchElementException("No this team");
        }
        if(!teamOptional.get().getCreator().equals(account.getId())){
            throw new InvalidParameterException("You are not admin of this team.");
        }
        TeamMember teamMember = new TeamMember();
        teamMember.setTeam(team);
        teamMember.setMember(member);
        teamMember.setIdentity(Identity.MEMBER);
        teamMemberRepository.save(teamMember);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    public Boolean checkMemberInTeam(Integer member, Integer team){
        List<Integer> teams = teamMemberRepository.findTeamMemberByMember(member,team);
        return !teams.isEmpty();
    }
    @Transactional
    public ResponseEntity<?> deleteMember(Integer member,Integer team){
        Member account = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Team> teamOptional = teamRepository.findById(team);
        Optional<Member> memberOptional = memberRepository.findById(member);
        if(teamOptional.isEmpty()){
            throw new NoSuchElementException("No this team");
        }
        if(memberOptional.isEmpty()){
            throw new NoSuchElementException("No this member");
        }
        if(!teamOptional.get().getCreator().equals(account.getId())){
            throw new InvalidParameterException("You are not admin of this team.");
        }
        if(!checkMemberInTeam(member,team)){
            throw new InvalidParameterException("Member not in this team");
        }
        teamMemberRepository.delete(teamMemberRepository.findTeamMemberByMemberAndTeam(member,team).get(0));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Transactional
    public ResponseEntity<?> deleteTeam(Integer team) throws ResourceNotFoundException, InvalidParameterException {
        Member account = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Team> teamOptional = teamRepository.findById(team);
        if(teamOptional.isEmpty()){
            throw new NoSuchElementException("No this team");
        }
        if(!teamOptional.get().getCreator().equals(account.getId())){
            throw new InvalidParameterException("You are not admin of this team.");
        }
        teamMemberRepository.deleteAll(teamMemberRepository.findTeamMemberByTeam(team));
        teamRepository.delete(teamOptional.get());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
