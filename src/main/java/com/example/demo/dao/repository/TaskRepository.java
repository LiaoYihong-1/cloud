package com.example.demo.dao.repository;

import com.example.demo.dao.model.Task;
import com.example.demo.dao.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findAllByTeam(Integer team);
}
