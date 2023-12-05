package com.example.projet_de_fin_odk3.exception;

public class AlreadyExistException extends RuntimeException{
    public AlreadyExistException(String message){
        super(message);
    }
}
