package com.olbati.utils;

/**
 * An exception for Email API.
 *
 * @author MAZIGH Med Belhassen
 */
public class MyException extends Exception{
    /**
     * Creates a new {@code MyException}.
     */
    public MyException()
    {
        //Empty constructor.
    }

    /**
     * Creates a new {@code MyException}.
     *
     * @param message the message.
     */
    public MyException(String message)
    {
        super(message);
    }

    /**
     * Creates a new {@code MyException}.
     *
     * @param message the message.
     * @param cause the envelopped exception.
     */
    public MyException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
