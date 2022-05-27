package com.paevolution.appiogateway.connector.ioitalia.client.model;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * MessageContent
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-12-24T13:31:55.506+01:00[Europe/Berlin]")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageContent implements Serializable {

    private static final long serialVersionUID = 8507494644568512629L;
    @JsonProperty("subject")
    private String subject;
    @JsonProperty("markdown")
    private String markdown;
    @JsonProperty("payment_data")
    private PaymentData paymentData;
    @JsonProperty("prescription_data")
    private PrescriptionData prescriptionData;
    @JsonProperty("due_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private java.sql.Timestamp dueDate;

    public MessageContent subject(String subject) {

	this.subject = subject;
	return this;
    }

    /**
     * The (optional) subject of the message - note that only some notification channels support the display of a
     * subject. When a subject is not provided, one gets generated from the client attributes.
     * 
     * @return subject
     */
    @ApiModelProperty(example = "Welcome new user !", required = true, value = "The (optional) subject of the message - note that only some notification channels support the display of a subject. When a subject is not provided, one gets generated from the client attributes.")
    @NotNull
    @Size(min = 10, max = 120)
    public String getSubject() {

	return subject;
    }

    public void setSubject(String subject) {

	this.subject = subject;
    }

    public MessageContent markdown(String markdown) {

	this.markdown = markdown;
	return this;
    }

    /**
     * The full version of the message, in plain text or Markdown format. The content of this field will be delivered to
     * channels that don't have any limit in terms of content size (e.g. email, etc...).
     * 
     * @return markdown
     */
    @ApiModelProperty(example = "# This is a markdown header  to show how easily markdown can be converted to **HTML**  Remember: this has to be a long text.", required = true, value = "The full version of the message, in plain text or Markdown format. The content of this field will be delivered to channels that don't have any limit in terms of content size (e.g. email, etc...).")
    @NotNull
    @Size(min = 80, max = 10000)
    public String getMarkdown() {

	return markdown;
    }

    public void setMarkdown(String markdown) {

	this.markdown = markdown;
    }

    public MessageContent paymentData(PaymentData paymentData) {

	this.paymentData = paymentData;
	return this;
    }

    /**
     * Get paymentData
     * 
     * @return paymentData
     */
    @ApiModelProperty(value = "")
    @Valid
    public PaymentData getPaymentData() {

	return paymentData;
    }

    public void setPaymentData(PaymentData paymentData) {

	this.paymentData = paymentData;
    }

    public MessageContent prescriptionData(PrescriptionData prescriptionData) {

	this.prescriptionData = prescriptionData;
	return this;
    }

    /**
     * Get prescriptionData
     * 
     * @return prescriptionData
     */
    @ApiModelProperty(value = "")
    @Valid
    public PrescriptionData getPrescriptionData() {

	return prescriptionData;
    }

    public void setPrescriptionData(PrescriptionData prescriptionData) {

	this.prescriptionData = prescriptionData;
    }

    public MessageContent dueDate(java.sql.Timestamp dueDate) {

	this.dueDate = dueDate;
	return this;
    }

    /**
     * Get dueDate
     * 
     * @return dueDate
     */
    @ApiModelProperty(value = "")
    @Valid
    public java.sql.Timestamp getDueDate() {

	return dueDate;
    }

    public void setDueDate(java.sql.Timestamp dueDate) {

	this.dueDate = dueDate;
    }

    @Override
    public boolean equals(java.lang.Object o) {

	if (this == o) {
	    return true;
	}
	if (o == null || getClass() != o.getClass()) {
	    return false;
	}
	MessageContent messageContent = (MessageContent) o;
	return Objects.equals(this.subject, messageContent.subject) && Objects.equals(this.markdown, messageContent.markdown)
		&& Objects.equals(this.paymentData, messageContent.paymentData)
		&& Objects.equals(this.prescriptionData, messageContent.prescriptionData) && Objects.equals(this.dueDate, messageContent.dueDate);
    }

    @Override
    public int hashCode() {

	return Objects.hash(subject, markdown, paymentData, prescriptionData, dueDate);
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class MessageContent {\n");
	sb.append("    subject: ").append(toIndentedString(subject)).append("\n");
	sb.append("    markdown: ").append(toIndentedString(markdown)).append("\n");
	sb.append("    paymentData: ").append(toIndentedString(paymentData)).append("\n");
	sb.append("    prescriptionData: ").append(toIndentedString(prescriptionData)).append("\n");
	sb.append("    dueDate: ").append(toIndentedString(dueDate)).append("\n");
	sb.append("}");
	return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {

	if (o == null) {
	    return "null";
	}
	return o.toString().replace("\n", "\n    ");
    }
}
