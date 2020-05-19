package com.sskim.eatgo.domain;

public class UserExistedException extends RuntimeException {
    public UserExistedException(String email){
        super("Email is already registered: " + email);
    }
}