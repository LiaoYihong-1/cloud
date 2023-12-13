package com.example.demo.dao.model;

import com.example.demo.configuration.PostgreSQLEnumType;
import com.example.demo.dao.type.Identity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Table(name = "Team_Members")
@Entity
@TypeDef(name="pgsql_enum",typeClass = PostgreSQLEnumType.class)
public class TeamMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "team", nullable = false)
    private Integer team;
    @Column(name = "member", nullable = false)
    private Integer member;
    @Column(nullable = false,name="identity")
    @Enumerated(EnumType.STRING)
    @Type(type = "pgsql_enum")
    private Identity identity;
}
