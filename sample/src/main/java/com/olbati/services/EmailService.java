package com.olbati.services;

import com.olbati.utils.Email;
import com.olbati.utils.MyException;

/**
 * Email services interface.
 *
 * @author Mazigh Med Belhassen
 */
public interface EmailService
{
    /**
     * Send email.
     *
     * @param email the email to send.
     * @throws MyException on error.
     */
    void send(Email email) throws MyException;
}
