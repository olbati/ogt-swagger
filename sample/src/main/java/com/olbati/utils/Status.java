package com.olbati.utils;

/**
 * A report of rest service response.
 *
 * @author MAZIGH Med Belhassen
 */

public class Status
{
    /** The returned code when the service is successfully called. */
    public static final int SUCCESS = 1;
    /** The returned code when the service is failed. */
    public static final int ERROR = 2;

    private int code;
    private String message;

    /**
     * Creates a new {@code Status}.
     */
    public Status()
    {
    }

    /**
     * Creates a new {@code Status}.
     *
     * @param code the result code.
     * @param message a descriptive message
     */
    public Status(int code, String message)
    {
        this.code = code;
        this.message = message;
    }

    /**
     * Returns the result code.
     *
     * @return  the result code.
     */
    public int getCode()
    {
        return code;
    }

    /**
     * Sets the result code.
     *
     * @param code the result code.
     */
    public void setCode(int code)
    {
        this.code = code;
    }

    /**
     * Returns a descriptive message.
     *
     * @return a descriptive message.
     */
    public String getMessage()
    {
        return message;
    }

    /**
     * Sets a descriptive message.
     *
     * @param message a descriptive message.
     */
    public void setMessage(String message)
    {
        this.message = message;
    }
}
