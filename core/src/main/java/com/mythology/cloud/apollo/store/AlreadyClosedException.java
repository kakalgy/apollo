package com.mythology.cloud.apollo.store;

/**
 * This exception is thrown when there is an attempt to
 * access something that has already been closed.
 *
 * @Author kakalgy
 * @Date 2019/12/10 21:42
 **/
public class AlreadyClosedException extends IllegalStateException {
    public AlreadyClosedException(String message) {
        super(message);
    }

    public AlreadyClosedException(String message, Throwable cause) {
        super(message, cause);
    }
}
