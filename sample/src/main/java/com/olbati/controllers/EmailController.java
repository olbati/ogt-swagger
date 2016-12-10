package com.olbati.controllers;

import com.olbati.repositories.EmailRepository;
import com.olbati.services.EmailService;
import com.olbati.utils.Email;
import com.olbati.utils.MyException;
import com.olbati.utils.Status;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * RESTFULL API to sending emails.
 *
 * @author MAZIGH Med Belhassen
 */
@RestController
@RequestMapping("/email")
public class EmailController {

    static final Logger LOGGER = Logger.getLogger(EmailController.class);

    @Autowired
    @Resource(name = "emailService")
    EmailService emailService;

    @Autowired
    EmailRepository emailRepository;

    @ApiOperation(value = "Return a list of emails matching the given email address",
            nickname = "GetEmailsByAddress",
            notes = "Return a list of emails that corresponds to the given address.",
            response = Email.class,
            responseContainer = "List")
    @RequestMapping(path = "/address",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Email.class, responseContainer = "List"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
    @ResponseBody
    public List<Email> getEmailsByAddress(@RequestParam @ApiParam(name = "address", value = "the email address",
            required = true) String address, HttpServletRequest request, HttpServletResponse response)
    {
        try {
            return emailRepository.findByAddressIgnoreCase(address);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ArrayList<>();
        }
    }


    @ApiOperation(value = "Return a list of emails matching the given subject",
            nickname = "GetEmailsBySubject",
            notes = "Return a list of emails that corresponds to the given subject.",
            response = Email.class,
            responseContainer = "List")
    @RequestMapping(path = "/subject",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Email.class, responseContainer = "List"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
    @ResponseBody
    public List<Email> getEmailsBySubject(@RequestParam @ApiParam(name = "subject", value = "the email subject",
            required = true) String subject, HttpServletRequest request, HttpServletResponse response)
    {
        try {
            return emailRepository.findBySubject(subject);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ArrayList<>();
        }
    }

    @ApiOperation(value = "Send email",
            nickname = "sendEmail",
            notes = "Send email by SMTP server.",
            response = Status.class)
    @RequestMapping(path = "/send",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "email",
//                    value = "the email struct",
//                    required = true,
//                    dataType = "Email",
//                    paramType = "Body")
//    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Status.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
    @ResponseBody
    public Status send(@RequestBody @ApiParam(name = "email", value = "the email struct", required = true) Email email,
                       HttpServletRequest request, HttpServletResponse response)
    {
        try {
            validateInput(email);
        } catch (MyException e) {
            return new Status(0, e.getMessage());
        }
        try {
            emailService.send(email);
        } catch (MyException e) {
            LOGGER.error(e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new Status(Status.ERROR, e.toString());
        }
        return new Status(Status.SUCCESS, "Send successfully !");
    }

    @ApiOperation(value = "Remove all emails matching the given address",
            nickname = "deleteEmailsByAddress",
            notes = "Remove all emails matching the given address.",
            response = Status.class)
    @RequestMapping(path = "/address",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Status.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
    @ResponseBody
    public Status deleteEmailsByAddress(@RequestBody @ApiParam(name = "address", value = "the email address",
            required = true) String address, HttpServletRequest request, HttpServletResponse response)
    {
        try {
            List<Email> list = emailRepository.findByAddressIgnoreCase(address);
            emailRepository.delete(list);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new Status(Status.ERROR, e.toString());
        }
        return new Status(Status.SUCCESS, "Removed successfully !");
    }

    private void validateInput(Email email) throws MyException {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Email>> constraintViolations = validator.validate(email);

        if (constraintViolations.size() > 0 ) {
            LOGGER.error("Impossible de valider les donnees du bean : ");
            String error = constraintViolations.stream()
                    .map(c -> c.getRootBeanClass().getSimpleName() + "." + c.getPropertyPath() + " " + c.getMessage())
                    .collect(Collectors.joining(", "));

            LOGGER.error(error);
            throw new MyException(error);
        } else {
            LOGGER.debug("Les donnees du bean sont valides");
        }
    }
}
