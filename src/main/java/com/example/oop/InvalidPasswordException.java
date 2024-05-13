package com.example.oop;

public class InvalidPasswordException extends Exception{
    public InvalidPasswordException() {
        super("Incorrect password, try again.");
    }
}
