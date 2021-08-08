package me.necrosis.configapi.exceptions;

public class ObjectCannotMappedException extends Exception{

    private final String message;

    public ObjectCannotMappedException(String message){
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
