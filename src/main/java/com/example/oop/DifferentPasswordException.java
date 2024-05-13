package com.example.oop;

public class DifferentPasswordException extends Exception{
    public DifferentPasswordException() {
        super("Both passwords aren't the same, check again");
    }
}
