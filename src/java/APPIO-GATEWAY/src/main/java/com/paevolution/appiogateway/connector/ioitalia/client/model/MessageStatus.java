package com.paevolution.appiogateway.connector.ioitalia.client.model;

import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * MessageStatus
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-12-24T13:31:55.506+01:00[Europe/Berlin]")
public class MessageStatus {

    @JsonProperty("status")
    private String status;
    @JsonProperty("updated_at")
    private java.sql.Timestamp updatedAt;
    @JsonProperty("version")
    private Integer version;

    public MessageStatus status(String status) {

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
    @ApiModelProperty(example = "ACCEPTED", required = true, value = "The processing status of a message. \"ACCEPTED\": the message has been accepted and will be processed for delivery;   we'll try to store its content in the user's inbox and notify him on his preferred channels \"THROTTLED\": a temporary failure caused a retry during the message processing;   any notification associated with this message will be delayed for a maximum of 7 days \"FAILED\": a permanent failure caused the process to exit with an error, no notification will be sent for this message \"PROCESSED\": the message was succesfully processed and is now stored in the user's inbox;   we'll try to send a notification for each of the selected channels \"REJECTED\": either the recipient does not exist, or the sender has been blocked")
    @NotNull
    public String getStatus() {

	return status;
    }

    public void setStatus(String status) {

	this.status = status;
    }

    public MessageStatus updatedAt(java.sql.Timestamp updatedAt) {

	this.updatedAt = updatedAt;
	return this;
    }

    /**
     * Get updatedAt
     * 
     * @return updatedAt
     */
    @ApiModelProperty(required = true, value = "")
    @NotNull
    @Valid
    public java.sql.Timestamp getUpdatedAt() {

	return updatedAt;
    }

    public void setUpdatedAt(java.sql.Timestamp updatedAt) {

	this.updatedAt = updatedAt;
    }

    public MessageStatus version(Integer version) {

	this.version = version;
	return this;
    }

    /**
     * Get version
     * 
     * @return version
     */
    @ApiModelProperty(value = "")
    public Integer getVersion() {

	return version;
    }

    public void setVersion(Integer version) {

	this.version = version;
    }

    @Override
    public boolean equals(java.lang.Object o) {

	if (this == o) {
	    return true;
	}
	if (o == null || getClass() != o.getClass()) {
	    return false;
	}
	MessageStatus messageStatus = (MessageStatus) o;
	return Objects.equals(this.status, messageStatus.status) && Objects.equals(this.updatedAt, messageStatus.updatedAt)
		&& Objects.equals(this.version, messageStatus.version);
    }

    @Override
    public int hashCode() {

	return Objects.hash(status, updatedAt, version);
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class MessageStatus {\n");
	sb.append("    status: ").append(toIndentedString(status)).append("\n");
	sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
	sb.append("    version: ").append(toIndentedString(version)).append("\n");
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
