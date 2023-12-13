package com.example.demo.controller;

import com.example.demo.dto.MessageReceiver;
import com.example.demo.dto.TaskReceiver;
import com.example.demo.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class MessageController {
    @Autowired
    private MessageService service;
    @PostMapping("/message")
    @PreAuthorize("hasAnyAuthority('user')")
    public ResponseEntity<?> sendMessage(@RequestBody MessageReceiver t){
        return service.sendMessage(t);
    }
    @GetMapping("/message/out")
    @PreAuthorize("hasAnyAuthority('user')")
    public ResponseEntity<?> getMessageOut(){
        return service.readMessageOut();
    }
    @GetMapping("/message/in")
    @PreAuthorize("hasAnyAuthority('user')")
    public ResponseEntity<?> getMessageReceive(){
        return service.readMessageIn();
    }
}
