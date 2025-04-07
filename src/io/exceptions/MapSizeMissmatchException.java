package io.exceptions;

public class MapSizeMissmatchException extends Exception{
    public MapSizeMissmatchException(){
        super();
    }
    public MapSizeMissmatchException(String message){
        super(message);
    }

    public MapSizeMissmatchException(String message, Throwable cause){
        super(message,cause);
    }

}
