package com.redlock.redlock.service;

/**
 * @Author huqi
 * @Description:
 * @Date Create In 14:41 2018/5/17 0017
 */
public class UnableToAquireLockException extends RuntimeException {

    public UnableToAquireLockException() {
    }

    public UnableToAquireLockException(String message) {
        super(message);
    }

    public UnableToAquireLockException(String message, Throwable cause) {
        super(message, cause);
    }
}
