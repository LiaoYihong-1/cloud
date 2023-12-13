package com.example.demo.dao.repository;

import com.example.demo.dao.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
    List<Member> findAllByName(String name);
}
