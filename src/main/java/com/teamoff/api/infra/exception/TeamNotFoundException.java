package com.teamoff.api.infra.exception;

public class TeamNotFoundException extends RuntimeException {
    public TeamNotFoundException(String message){
        super(message);
    }
}
