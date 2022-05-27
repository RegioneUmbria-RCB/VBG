package com.paevolution.appiogateway.connector.ioitalia.client.model;

import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * CreatedMessageWithoutContent
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-12-24T13:31:55.506+01:00[Europe/Berlin]")
public class CreatedMessageWithoutContent {

    @JsonProperty("id")
    private String id;
    @JsonProperty("fiscal_code")
    private String fiscalCode;
    @JsonProperty("time_to_live")
    private Integer timeToLive = 3600;
    @JsonProperty("created_at")
    private java.sql.Timestamp createdAt;
    @JsonProperty("sender_service_id")
    private String senderServiceId;

    public CreatedMessageWithoutContent id(String id) {

	this.id = id;
	return this;
    }

    /**
     * Get id
     * 
     * @return id
     */
    @ApiModelProperty(required = true, value = "")
    @NotNull
    public String getId() {

	return id;
    }

    public void setId(String id) {

	this.id = id;
    }

    public CreatedMessageWithoutContent fiscalCode(String fiscalCode) {

	this.fiscalCode = fiscalCode;
	return this;
    }

    /**
     * User's fiscal code.
     * 
     * @return fiscalCode
     */
    @ApiModelProperty(example = "SPNDNL80R13C555X", required = true, value = "User's fiscal code.")
    @NotNull
    public String getFiscalCode() {

	return fiscalCode;
    }

    public void setFiscalCode(String fiscalCode) {

	this.fiscalCode = fiscalCode;
    }

    public CreatedMessageWithoutContent timeToLive(Integer timeToLive) {

	this.timeToLive = timeToLive;
	return this;
    }

    /**
     * This parameter specifies for how long (in seconds) the system will try to deliver the message to the channels
     * configured by the user. minimum: 3600 maximum: 604800
     * 
     * @return timeToLive
     */
    @ApiModelProperty(example = "3600", value = "This parameter specifies for how long (in seconds) the system will try to deliver the message to the channels configured by the user.")
    @Min(3600)
    @Max(604800)
    public Integer getTimeToLive() {

	return timeToLive;
    }

    public void setTimeToLive(Integer timeToLive) {

	this.timeToLive = timeToLive;
    }

    public CreatedMessageWithoutContent createdAt(java.sql.Timestamp createdAt) {

	this.createdAt = createdAt;
	return this;
    }

    /**
     * Get createdAt
     * 
     * @return createdAt
     */
    @ApiModelProperty(required = true, value = "")
    @NotNull
    @Valid
    public java.sql.Timestamp getCreatedAt() {

	return createdAt;
    }

    public void setCreatedAt(java.sql.Timestamp createdAt) {

	this.createdAt = createdAt;
    }

    public CreatedMessageWithoutContent senderServiceId(String senderServiceId) {

	this.senderServiceId = senderServiceId;
	return this;
    }

    /**
     * Get senderServiceId
     * 
     * @return senderServiceId
     */
    @ApiModelProperty(required = true, value = "")
    @NotNull
    public String getSenderServiceId() {

	return senderServiceId;
    }

    public void setSenderServiceId(String senderServiceId) {

	this.senderServiceId = senderServiceId;
    }

    @Override
    public boolean equals(java.lang.Object o) {

	if (this == o) {
	    return true;
	}
	if (o == null || getClass() != o.getClass()) {
	    return false;
	}
	CreatedMessageWithoutContent createdMessageWithoutContent = (CreatedMessageWithoutContent) o;
	return Objects.equals(this.id, createdMessageWithoutContent.id) && Objects.equals(this.fiscalCode, createdMessageWithoutContent.fiscalCode)
		&& Objects.equals(this.timeToLive, createdMessageWithoutContent.timeToLive)
		&& Objects.equals(this.createdAt, createdMessageWithoutContent.createdAt)
		&& Objects.equals(this.senderServiceId, createdMessageWithoutContent.senderServiceId);
    }

    @Override
    public int hashCode() {

	return Objects.hash(id, fiscalCode, timeToLive, createdAt, senderServiceId);
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class CreatedMessageWithoutContent {\n");
	sb.append("    id: ").append(toIndentedString(id)).append("\n");
	sb.append("    fiscalCode: ").append(toIndentedString(fiscalCode)).append("\n");
	sb.append("    timeToLive: ").append(toIndentedString(timeToLive)).append("\n");
	sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
	sb.append("    senderServiceId: ").append(toIndentedString(senderServiceId)).append("\n");
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
