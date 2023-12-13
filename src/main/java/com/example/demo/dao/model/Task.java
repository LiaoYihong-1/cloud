package com.example.demo.dao.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Data
@NoArgsConstructor
@Table(name = "Task")
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Integer id;
    @Column(name = "creator", nullable = false)
    private Integer creator;
    @Column(name = "team", nullable = false)
    private Integer team;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "finished", nullable = false)
    private Boolean finished;
}
