package com.example.demo.dao.repository;

import com.example.demo.dao.model.TaskDispatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskDispatchRepository extends JpaRepository<TaskDispatch, Integer> {
    public List<TaskDispatch> findAllByTask(Integer task);
}
