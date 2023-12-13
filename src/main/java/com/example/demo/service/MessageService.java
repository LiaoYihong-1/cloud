package com.example.demo.service;

import com.example.demo.dao.model.Member;
import com.example.demo.dao.model.Message;
import com.example.demo.dao.repository.MemberRepository;
import com.example.demo.dao.repository.MessageRepository;
import com.example.demo.dto.MessageReceiver;
import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MessageService {
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    MemberRepository memberRepository;
    public ResponseEntity<?> sendMessage(MessageReceiver messageReceiver) throws ResourceNotFoundException{
        Message message = new Message();
        Optional<Member> m = memberRepository.findById(messageReceiver.getReceive());
        if(m.isEmpty()){
            throw new ResourceNotFoundException("You are sending a message to a user,which is not existed");
        }
        Member account = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        message.setContent(messageReceiver.getContent());
        message.setReceive(messageReceiver.getReceive());
        message.setSend(account.getId());
        messageRepository.save(message);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public ResponseEntity<?> readMessageOut(){
        Member account = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(messageRepository.findAllBySend(account.getId()));
    }

    public ResponseEntity<?> readMessageIn(){
        Member account = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(messageRepository.findAllByReceive(account.getId()));
    }
}
