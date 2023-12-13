package com.example.demo.dto;

import lombok.Data;

import javax.persistence.Column;

@Data
public class MessageReceiver {
    private String content;
    private Integer receive;
}
