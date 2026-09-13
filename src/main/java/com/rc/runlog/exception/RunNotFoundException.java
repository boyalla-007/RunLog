package com.rc.runlog.exception;

public class RunNotFoundException extends RuntimeException{
    public RunNotFoundException(String message){
        super(message);
    }
}
