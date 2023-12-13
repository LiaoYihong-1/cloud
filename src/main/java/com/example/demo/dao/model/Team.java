package com.example.demo.dao.model;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Table(name = "Team")
@Entity
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "creator", nullable = false)
    private Integer creator;
    @Column(name = "name", nullable = false)
    private String name;
}
