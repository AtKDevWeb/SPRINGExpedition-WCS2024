package org.wcs.myBlog.exceptions;

public class BadGatewayException extends RuntimeException{
    public BadGatewayException(String message){
        super(message);
    }
}
