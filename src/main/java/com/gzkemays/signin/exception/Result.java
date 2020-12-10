package com.gzkemays.signin.exception;

import lombok.Data;

@Data
public class Result<T> {
    private Integer code;
    private Object msg = "";
    private Object data = "";
}
