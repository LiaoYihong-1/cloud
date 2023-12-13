package com.example.demo.dao.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@NoArgsConstructor
@Table(name = "Task_Dispatch")
@Entity
public class TaskDispatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonIgnore
    private Integer id;
    @Column(name = "member", nullable = false)
    private Integer member;
    @Column(name = "task", nullable = false)
    private Integer task;
}
