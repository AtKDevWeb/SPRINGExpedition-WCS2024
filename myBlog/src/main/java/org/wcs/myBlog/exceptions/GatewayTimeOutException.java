package org.wcs.myBlog.exceptions;

public class GatewayTimeOutException extends RuntimeException{
    public GatewayTimeOutException(String message){
        super(message);
    }
}
