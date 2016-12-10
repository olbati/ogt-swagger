package com.olbati.utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;

/**
 * Email document.
 *
 * @author MAZIGH Med Belhassen.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel
public class Email {

    @Id private String id;

    @NotNull(message = "Email_ERROR_NULL")
    @org.hibernate.validator.constraints.Email(message = "ERROR_EMAIL_IS_NOT_VALID")
    @ApiModelProperty(position = 1, name = "address", required = true, value = "The email address of recipient",
        example = "test00@yopmail.com")
    private String address;

    @NotNull(message = "BODY_ERROR_NULL")
    @ApiModelProperty(position = 3, name = "body", required = true, value = "The text to send", example = "Hello,")
    private String body;

    @NotNull(message = "SUBJECT_ERROR_NULL")
    @ApiModelProperty(position = 2, name = "subject", required = true, value = "The subject of email", example = "TEST")
    private String subject;

    @ApiModelProperty(position = 5, name = "htmlBody", value = "The HTML text to send")
    private String htmlBody;

    @ApiModelProperty(position = 4, name = "withHtmlBody", required = true, value = "The text to send is in html",
            allowableValues = "true, fase", example = "false")
    @NotNull(message = "WITHHTML_ERROR_NULL")
    private Boolean withHtmlBody;


    /**
     * Gets address
     *
     * @return value of address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the id
     *
     * @param id the id to set.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets id
     *
     * @return value of id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the address
     *
     * @param address the address to set.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets body
     *
     * @return value of body
     */
    public String getBody() {
        return body;
    }

    /**
     * Sets the body
     *
     * @param body the body to set.
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * Gets subject
     *
     * @return value of subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets the subject
     *
     * @param subject the subject to set.
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Gets htmlBody
     *
     * @return value of htmlBody
     */
    public String getHtmlBody() {
        return htmlBody;
    }

    /**
     * Sets the htmlBody
     *
     * @param htmlBody the htmlBody to set.
     */
    public void setHtmlBody(String htmlBody) {
        this.htmlBody = htmlBody;
    }

    /**
     * Gets withHtmlBody
     *
     * @return value of withHtmlBody
     */
    public Boolean getWithHtmlBody() {
        return withHtmlBody;
    }

    /**
     * Sets the withHtmlBody
     *
     * @param withHtmlBody the withHtmlBody to set.
     */
    public void setWithHtmlBody(Boolean withHtmlBody) {
        this.withHtmlBody = withHtmlBody;
    }
}
