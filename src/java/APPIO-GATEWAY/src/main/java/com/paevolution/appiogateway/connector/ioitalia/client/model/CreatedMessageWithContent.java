package com.paevolution.appiogateway.connector.ioitalia.client.model;

import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * CreatedMessageWithContent
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-12-24T13:31:55.506+01:00[Europe/Berlin]")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreatedMessageWithContent {

    @JsonProperty("id")
    private String id;
    @JsonProperty("fiscal_code")
    private String fiscalCode;
    @JsonProperty("time_to_live")
    private Integer timeToLive = 3600;
    @JsonProperty("created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private java.sql.Timestamp createdAt;
    @JsonProperty("content")
    private MessageContent content;
    @JsonProperty("sender_service_id")
    private String senderServiceId;

    public CreatedMessageWithContent id(String id) {

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

    public CreatedMessageWithContent fiscalCode(String fiscalCode) {

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

    public CreatedMessageWithContent timeToLive(Integer timeToLive) {

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

    public CreatedMessageWithContent createdAt(java.sql.Timestamp createdAt) {

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

    public CreatedMessageWithContent content(MessageContent content) {

	this.content = content;
	return this;
    }

    /**
     * Get content
     * 
     * @return content
     */
    @ApiModelProperty(required = true, value = "")
    @NotNull
    @Valid
    public MessageContent getContent() {

	return content;
    }

    public void setContent(MessageContent content) {

	this.content = content;
    }

    public CreatedMessageWithContent senderServiceId(String senderServiceId) {

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
	CreatedMessageWithContent createdMessageWithContent = (CreatedMessageWithContent) o;
	return Objects.equals(this.id, createdMessageWithContent.id) && Objects.equals(this.fiscalCode, createdMessageWithContent.fiscalCode)
		&& Objects.equals(this.timeToLive, createdMessageWithContent.timeToLive)
		&& Objects.equals(this.createdAt, createdMessageWithContent.createdAt)
		&& Objects.equals(this.content, createdMessageWithContent.content)
		&& Objects.equals(this.senderServiceId, createdMessageWithContent.senderServiceId);
    }

    @Override
    public int hashCode() {

	return Objects.hash(id, fiscalCode, timeToLive, createdAt, content, senderServiceId);
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class CreatedMessageWithContent {\n");
	sb.append("    id: ").append(toIndentedString(id)).append("\n");
	sb.append("    fiscalCode: ").append(toIndentedString(fiscalCode)).append("\n");
	sb.append("    timeToLive: ").append(toIndentedString(timeToLive)).append("\n");
	sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
	sb.append("    content: ").append(toIndentedString(content)).append("\n");
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
