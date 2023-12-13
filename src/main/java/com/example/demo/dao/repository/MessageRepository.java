package com.example.demo.dao.repository;

import com.example.demo.dao.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message,Integer> {
    public List<Message> findAllByReceive(Integer id);
    public List<Message> findAllBySend(Integer id);
}
