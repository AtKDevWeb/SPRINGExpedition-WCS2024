package org.wcs.myBlog.exceptions;

public class ForbiddenAccessException extends RuntimeException{
    public ForbiddenAccessException(String message){
        super(message);
    }
}
