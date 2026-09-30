package com.paevolution.appiogateway.connector.ioitalia.client.model;

import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * NotificationChannelStatus
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-12-24T13:31:55.506+01:00[Europe/Berlin]")
public class NotificationChannelStatus {

    @JsonProperty("channel")
    private String channel;
    @JsonProperty("status")
    private String status;
    @JsonProperty("updated_at")
    private java.sql.Timestamp updatedAt;
    @JsonProperty("version")
    private Integer version;

    public NotificationChannelStatus channel(String channel) {

	this.channel = channel;
	return this;
    }

    /**
     * All notification channels.
     * 
     * @return channel
     */
    @ApiModelProperty(example = "EMAIL", required = true, value = "All notification channels.")
    @NotNull
    public String getChannel() {

	return channel;
    }

    public void setChannel(String channel) {

	this.channel = channel;
    }

    public NotificationChannelStatus status(String status) {

	this.status = status;
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
     * @return status
     */
    @ApiModelProperty(example = "SENT", required = true, value = "The status of a notification (one for each channel). \"SENT\": the notification was succesfully sent to the channel (ie. email or push notification) \"THROTTLED\": a temporary failure caused a retry during the notification processing;   the notification associated with this channel will be delayed for a maximum of 7 days or until the message expires \"EXPIRED\": the message expired before the notification could be sent;   this means that the maximum message time to live was reached; no notification will be sent to this channel \"FAILED\": a permanent failure caused the process to exit with an error, no notification will be sent to this channel")
    @NotNull
    public String getStatus() {

	return status;
    }

    public void setStatus(String status) {

	this.status = status;
    }

    public NotificationChannelStatus updatedAt(java.sql.Timestamp updatedAt) {

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

    public NotificationChannelStatus version(Integer version) {

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
	NotificationChannelStatus notificationChannelStatus = (NotificationChannelStatus) o;
	return Objects.equals(this.channel, notificationChannelStatus.channel) && Objects.equals(this.status, notificationChannelStatus.status)
		&& Objects.equals(this.updatedAt, notificationChannelStatus.updatedAt)
		&& Objects.equals(this.version, notificationChannelStatus.version);
    }

    @Override
    public int hashCode() {

	return Objects.hash(channel, status, updatedAt, version);
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class NotificationChannelStatus {\n");
	sb.append("    channel: ").append(toIndentedString(channel)).append("\n");
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
