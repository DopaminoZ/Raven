package com.example.oop;

public class InvalidEmailException extends Exception{
    public InvalidEmailException(){
        super("Invalid email address...");
    }
}
