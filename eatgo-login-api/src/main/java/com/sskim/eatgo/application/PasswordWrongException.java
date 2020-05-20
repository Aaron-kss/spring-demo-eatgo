package com.sskim.eatgo.application;

public class PasswordWrongException extends RuntimeException{
    public PasswordWrongException(){
        super("Password is wrong");
    }
}
