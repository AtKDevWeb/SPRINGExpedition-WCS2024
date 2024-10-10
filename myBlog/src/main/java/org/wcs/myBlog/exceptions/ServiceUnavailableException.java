package org.wcs.myBlog.exceptions;

public class ServiceUnavailableException extends  RuntimeException{
    public ServiceUnavailableException(String message){
        super(message);
    }
}