package com.olbati.services.impl;

import com.olbati.repositories.EmailRepository;
import com.olbati.services.EmailService;
import com.olbati.utils.Email;
import com.olbati.utils.MyException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * This class implement the Email services interface.
 *
 * @author Mazigh Med Belhassen
 **/
@Service("emailService")
@PropertySource(value = "classpath:config.properties")
public class EmailServiceImpl implements EmailService
{
    static final Logger LOGGER = Logger.getLogger(EmailServiceImpl.class);
    private JavaMailSender sender;
    private String from;

    @Autowired
    EmailRepository emailRepository;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender)
    {
        sender = javaMailSender;
    }

    @Override
    public void send(Email email) throws MyException
    {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(email.getAddress());
        mail.setFrom(from);
        mail.setSubject(email.getSubject());
        mail.setText(email.getBody());
        try {
            LOGGER.info("Email sended from : " + getFrom() + "to : " + email.getAddress());
            sender.send(mail);
            emailRepository.save(email);
        } catch (MailException e) {
            throw new MyException(e.getMessage(), e);
        }
    }

    /**
     *
     * @return the from.
     */
    public String getFrom() {
        return from;
    }

    /**
     * Sets the from.
     *
     * @param from the from to set.
     */
    @Value("${from}")
    @Required
    public void setFrom(String from) {
        this.from = from;
    }
}
