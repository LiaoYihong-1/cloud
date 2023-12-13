package com.example.demo.dao.repository;

import com.example.demo.dao.model.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.swing.text.html.Option;
import java.util.List;

@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember,Integer> {
    @Query("select t.id from TeamMember t where t.member =?1 and t.team=?2")
    public List<Integer> findTeamMemberByMember(Integer member, Integer team);

    public List<TeamMember> findTeamMemberByMemberAndTeam(Integer member, Integer team);

    public List<TeamMember> findTeamMemberByTeam(Integer team);
}
