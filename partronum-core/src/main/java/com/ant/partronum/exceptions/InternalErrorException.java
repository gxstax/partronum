package com.ant.partronum.exceptions;

/**
 * <p>
 * 自定义内部异常
 * </p>
 *
 * @author GaoXin
 * @since 2021/9/25 5:50 下午
 */
public class InternalErrorException extends RuntimeException{
    private String message;

    public InternalErrorException(String message) {
        super(message);
    }

    public InternalErrorException(String message, Throwable cause) {
        super(message, cause);
    }

}
