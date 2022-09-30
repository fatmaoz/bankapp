package com.bankapp.exception;

public class RecordNotFoundException extends RuntimeException{

    public RecordNotFoundException(String s) {
        super(s);
    }
}
