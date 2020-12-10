package com.gzkemays.signin.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MushroomException extends RuntimeException {
    protected Integer code;
    protected Object msg;
}
