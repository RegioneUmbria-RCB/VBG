package com.paevolution.appiogateway.connector.ioitalia.client.model;

import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * MessageResponseWithoutContent
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-12-24T13:31:55.506+01:00[Europe/Berlin]")
public class MessageResponseWithoutContent {

    @JsonProperty("message")
    private CreatedMessageWithoutContent message;
    @JsonProperty("notification")
    private MessageResponseNotificationStatus notification;
    @JsonProperty("status")
    private String status;

    public MessageResponseWithoutContent message(CreatedMessageWithoutContent message) {

	this.message = message;
	return this;
    }

    /**
     * Get message
     * 
     * @return message
     */
    @ApiModelProperty(required = true, value = "")
    @NotNull
    @Valid
    public CreatedMessageWithoutContent getMessage() {

	return message;
    }

    public void setMessage(CreatedMessageWithoutContent message) {

	this.message = message;
    }

    public MessageResponseWithoutContent notification(MessageResponseNotificationStatus notification) {

	this.notification = notification;
	return this;
    }

    /**
     * Get notification
     * 
     * @return notification
     */
    @ApiModelProperty(value = "")
    @Valid
    public MessageResponseNotificationStatus getNotification() {

	return notification;
    }

    public void setNotification(MessageResponseNotificationStatus notification) {

	this.notification = notification;
    }

    public MessageResponseWithoutContent status(String status) {

	this.status = status;
	return this;
    }

    /**
     * The processing status of a message. \"ACCEPTED\": the message has been accepted and will be processed for
     * delivery; we'll try to store its content in the user's inbox and notify him on his preferred channels
     * \"THROTTLED\": a temporary failure caused a retry during the message processing; any notification associated with
     * this message will be delayed for a maximum of 7 days \"FAILED\": a permanent failure caused the process to exit
     * with an error, no notification will be sent for this message \"PROCESSED\": the message was succesfully processed
     * and is now stored in the user's inbox; we'll try to send a notification for each of the selected channels
     * \"REJECTED\": either the recipient does not exist, or the sender has been blocked
     * 
     * @return status
     */
    @ApiModelProperty(example = "ACCEPTED", value = "The processing status of a message. \"ACCEPTED\": the message has been accepted and will be processed for delivery;   we'll try to store its content in the user's inbox and notify him on his preferred channels \"THROTTLED\": a temporary failure caused a retry during the message processing;   any notification associated with this message will be delayed for a maximum of 7 days \"FAILED\": a permanent failure caused the process to exit with an error, no notification will be sent for this message \"PROCESSED\": the message was succesfully processed and is now stored in the user's inbox;   we'll try to send a notification for each of the selected channels \"REJECTED\": either the recipient does not exist, or the sender has been blocked")
    public String getStatus() {

	return status;
    }

    public void setStatus(String status) {

	this.status = status;
    }

    @Override
    public boolean equals(java.lang.Object o) {

	if (this == o) {
	    return true;
	}
	if (o == null || getClass() != o.getClass()) {
	    return false;
	}
	MessageResponseWithoutContent messageResponseWithoutContent = (MessageResponseWithoutContent) o;
	return Objects.equals(this.message, messageResponseWithoutContent.message)
		&& Objects.equals(this.notification, messageResponseWithoutContent.notification)
		&& Objects.equals(this.status, messageResponseWithoutContent.status);
    }

    @Override
    public int hashCode() {

	return Objects.hash(message, notification, status);
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class MessageResponseWithoutContent {\n");
	sb.append("    message: ").append(toIndentedString(message)).append("\n");
	sb.append("    notification: ").append(toIndentedString(notification)).append("\n");
	sb.append("    status: ").append(toIndentedString(status)).append("\n");
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
