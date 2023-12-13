package com.example.demo.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Error {
    public Error(String message,Integer code){
        this.code = code;
        this.message = message;
    }
    public String message;
    public Integer code;
}
