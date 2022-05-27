package com.paevolution.appiogateway.connector.ioitalia.client.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * MessageResponseNotificationStatus
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-12-24T13:31:55.506+01:00[Europe/Berlin]")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageResponseNotificationStatus {

    @JsonProperty("email")
    private String email;
    @JsonProperty("webhook")
    private String webhook;

    public MessageResponseNotificationStatus email(String email) {

	this.email = email;
	return this;
    }

    /**
     * The status of a notification (one for each channel). \"SENT\": the notification was succesfully sent to the
     * channel (ie. email or push notification) \"THROTTLED\": a temporary failure caused a retry during the
     * notification processing; the notification associated with this channel will be delayed for a maximum of 7 days or
     * until the message expires \"EXPIRED\": the message expired before the notification could be sent; this means that
     * the maximum message time to live was reached; no notification will be sent to this channel \"FAILED\": a
     * permanent failure caused the process to exit with an error, no notification will be sent to this channel
     * 
     * @return email
     */
    @ApiModelProperty(example = "SENT", value = "The status of a notification (one for each channel). \"SENT\": the notification was succesfully sent to the channel (ie. email or push notification) \"THROTTLED\": a temporary failure caused a retry during the notification processing;   the notification associated with this channel will be delayed for a maximum of 7 days or until the message expires \"EXPIRED\": the message expired before the notification could be sent;   this means that the maximum message time to live was reached; no notification will be sent to this channel \"FAILED\": a permanent failure caused the process to exit with an error, no notification will be sent to this channel")
    public String getEmail() {

	return email;
    }

    public void setEmail(String email) {

	this.email = email;
    }

    public MessageResponseNotificationStatus webhook(String webhook) {

	this.webhook = webhook;
	return this;
    }

    /**
     * The status of a notification (one for each channel). \"SENT\": the notification was succesfully sent to the
     * channel (ie. email or push notification) \"THROTTLED\": a temporary failure caused a retry during the
     * notification processing; the notification associated with this channel will be delayed for a maximum of 7 days or
     * until the message expires \"EXPIRED\": the message expired before the notification could be sent; this means that
     * the maximum message time to live was reached; no notification will be sent to this channel \"FAILED\": a
     * permanent failure caused the process to exit with an error, no notification will be sent to this channel
     * 
     * @return webhook
     */
    @ApiModelProperty(example = "SENT", value = "The status of a notification (one for each channel). \"SENT\": the notification was succesfully sent to the channel (ie. email or push notification) \"THROTTLED\": a temporary failure caused a retry during the notification processing;   the notification associated with this channel will be delayed for a maximum of 7 days or until the message expires \"EXPIRED\": the message expired before the notification could be sent;   this means that the maximum message time to live was reached; no notification will be sent to this channel \"FAILED\": a permanent failure caused the process to exit with an error, no notification will be sent to this channel")
    public String getWebhook() {

	return webhook;
    }

    public void setWebhook(String webhook) {

	this.webhook = webhook;
    }

    @Override
    public boolean equals(java.lang.Object o) {

	if (this == o) {
	    return true;
	}
	if (o == null || getClass() != o.getClass()) {
	    return false;
	}
	MessageResponseNotificationStatus messageResponseNotificationStatus = (MessageResponseNotificationStatus) o;
	return Objects.equals(this.email, messageResponseNotificationStatus.email)
		&& Objects.equals(this.webhook, messageResponseNotificationStatus.webhook);
    }

    @Override
    public int hashCode() {

	return Objects.hash(email, webhook);
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class MessageResponseNotificationStatus {\n");
	sb.append("    email: ").append(toIndentedString(email)).append("\n");
	sb.append("    webhook: ").append(toIndentedString(webhook)).append("\n");
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
