package com.example.demo.dao.repository;

import com.example.demo.dao.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {
    @Query("select max(t.id) from Team t")
    public Integer findTeamByMaxId();

}
