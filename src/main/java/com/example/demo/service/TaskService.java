package com.example.demo.service;

import com.example.demo.dao.model.*;
import com.example.demo.dao.repository.MemberRepository;
import com.example.demo.dao.repository.TaskDispatchRepository;
import com.example.demo.dao.repository.TaskRepository;
import com.example.demo.dao.repository.TeamRepository;
import com.example.demo.dto.TaskReceiver;
import com.example.demo.exception.InvalidParameterException;
import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private TaskDispatchRepository taskDispatchRepository;
    @Autowired
    private TeamService teamService;
    @Transactional
    public ResponseEntity<?> createTask(TaskReceiver receiver) throws ResourceNotFoundException, InvalidParameterException {
        Member account = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Team> teamOptional = teamRepository.findById(receiver.getTeam());
        if(teamOptional.isEmpty()){
            throw new ResourceNotFoundException("No this team");
        }
        Team t = teamOptional.get();
        if(!t.getCreator().equals(account.getId())){
            throw new InvalidParameterException("You are not admin of this team");
        }
        Task task = new Task();
        task.setCreator(account.getId());
        task.setTeam(t.getId());
        task.setDescription(receiver.getDescription());
        task.setFinished(Boolean.FALSE);
        taskRepository.save(task);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    public ResponseEntity<?> checkTask() throws ResourceNotFoundException{
        Member account = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(memberRepository.findById(account.getId()).isEmpty()){
            throw new ResourceNotFoundException("This account is not valid now");
        }
        account = memberRepository.findById(account.getId()).get();
        return ResponseEntity.ok(account.getTasks());
    }
    public ResponseEntity<?> dispatchTask(Integer task, Integer member) throws ResourceNotFoundException{
        Member account = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(memberRepository.findById(account.getId()).isEmpty()||memberRepository.findById(member).isEmpty()){
            throw new ResourceNotFoundException("This member is not existed");
        }
        if(taskRepository.findById(task).isEmpty()){
            throw new ResourceNotFoundException("This task is not existed");
        }
        Task t = taskRepository.findById(task).get();
        if(!t.getCreator().equals(account.getId())){
            throw new InvalidParameterException("You don't have privilege to dispatch this task");
        }
        if(!teamService.checkMemberInTeam(member,t.getTeam())){
            throw new InvalidParameterException("You are inviting a member, who is not belong to this team");
        }
        TaskDispatch taskDispatch = new TaskDispatch();
        taskDispatch.setTask(task);
        taskDispatch.setMember(member);
        taskDispatchRepository.save(taskDispatch);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public ResponseEntity<?> checkTeamProgress(Integer team) throws InvalidParameterException, ResourceNotFoundException{
        Member account = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Team> teamOptional = teamRepository.findById(team);
        if(teamOptional.isEmpty()){
            throw new ResourceNotFoundException("No this team");
        }
        if(!teamService.checkMemberInTeam(account.getId(),team)){
            throw new InvalidParameterException("You are not belong to this team");
        }
        return ResponseEntity.ok(taskRepository.findAllByTeam(teamOptional.get().getId()));
    }

    public ResponseEntity<?> changeStatus(Integer task, Boolean status) throws InvalidParameterException, ResourceNotFoundException{
        Member account = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Task> taskOptional = taskRepository.findById(task);
        if(taskOptional.isEmpty()){
            throw new ResourceNotFoundException("No this task");
        }
        if(!taskOptional.get().getCreator().equals(account.getId())){
            throw new InvalidParameterException("You don't have privilege to change status of this task");
        }
        Task t = taskOptional.get();
        t.setFinished(status);
        taskRepository.save(t);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    public ResponseEntity<?> deleteTask(Integer task) throws ResourceNotFoundException, InvalidParameterException{
        Member account = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Task> taskOptional = taskRepository.findById(task);
        if(taskOptional.isEmpty()) {
            throw new ResourceNotFoundException("No this task");
        }
        if(!taskOptional.get().getCreator().equals(account.getId())){
            throw new InvalidParameterException("You don't have privilege to delete this task");
        }
        List<TaskDispatch> taskDispatches = taskDispatchRepository.findAllByTask(task);
        taskDispatchRepository.deleteAll(taskDispatches);
        taskRepository.delete(taskOptional.get());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
