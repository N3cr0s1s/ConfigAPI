package me.necrosis.configapi.exceptions;

public class FieldValueNotExistException extends Exception{

    private final String message;

    public FieldValueNotExistException(String message){
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
