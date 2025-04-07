package io.exceptions;

public class MapMalformedException extends Exception{
    public MapMalformedException(){
        super();
    }

    public MapMalformedException(String message){
        super(message);
    }
    public MapMalformedException(String message,Throwable cause){
        super(message,cause);
    }
}
