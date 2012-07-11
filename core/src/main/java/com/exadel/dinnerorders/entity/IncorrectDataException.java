package com.exadel.dinnerorders.entity;

public class IncorrectDataException extends Exception {
    public IncorrectDataException(String e){
        super(e);
    }

    public IncorrectDataException(){
        super("Incorrect data!!!");
    }
}
