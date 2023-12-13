package com.example.demo.controller;

import com.example.demo.dto.TaskReceiver;
import com.example.demo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class TaskController {
    @Autowired
    private TaskService service;
    @PostMapping("/task")
    @PreAuthorize("hasAnyAuthority('user')")
    public ResponseEntity<?> createTask(@RequestBody TaskReceiver t){
        return service.createTask(t);
    }
    @GetMapping("/task")
    @PreAuthorize("hasAnyAuthority('user')")
    public ResponseEntity<?> checkTask(){
        return service.checkTask();
    }
    @PostMapping("/task/dispatch/{task}/{member}")
    @PreAuthorize("hasAnyAuthority('user')")
    public ResponseEntity<?> dispatchTask(@PathVariable Integer task, @PathVariable Integer member){
        return service.dispatchTask(task, member);
    }
    @GetMapping("/task/{team}")
    @PreAuthorize("hasAnyAuthority('user')")
    public ResponseEntity<?> checkTeam(@PathVariable Integer team){
        return service.checkTeamProgress(team);
    }
    @PutMapping("/task/{task}/{status}")
    @PreAuthorize("hasAnyAuthority('user')")
    public ResponseEntity<?> changeStatus(@PathVariable Integer task,@PathVariable Boolean status){
        return service.changeStatus(task,status);
    }
    @DeleteMapping("/task/{task}")
    @PreAuthorize("hasAnyAuthority('user')")
    public ResponseEntity<?> deleteTask(@PathVariable Integer task){
        return service.deleteTask(task);
    }
}
