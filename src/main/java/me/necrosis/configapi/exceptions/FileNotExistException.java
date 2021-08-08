package me.necrosis.configapi.exceptions;

public class FileNotExistException extends Exception{

    private final String message;

    public FileNotExistException(String message){
        super(message);
        this.message    =   message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
