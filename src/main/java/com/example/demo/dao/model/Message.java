package com.example.demo.dao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@NoArgsConstructor
@Table(name = "Message")
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Integer id;
    @Column(name = "content", nullable = false)
    private String content;
    @Column(name = "receive", nullable = false)
    private Integer receive;
    @Column(name = "send", nullable = false)
    private Integer send;
}
