package com.paevolution.appiogateway.connector.ioitalia.client.model;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * NewMessage
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-12-24T13:31:55.506+01:00[Europe/Berlin]")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewMessage implements Serializable {

    private static final long serialVersionUID = 8968846779698676302L;
    @JsonProperty("time_to_live")
    private Integer timeToLive = 3600;
    @JsonProperty("content")
    private MessageContent content;
    @JsonProperty("default_addresses")
    private NewMessageDefaultAddresses defaultAddresses;
    @JsonProperty("fiscal_code")
    private String fiscalCode;

    public NewMessage timeToLive(Integer timeToLive) {

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

    public NewMessage content(MessageContent content) {

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

    public NewMessage defaultAddresses(NewMessageDefaultAddresses defaultAddresses) {

	this.defaultAddresses = defaultAddresses;
	return this;
    }

    /**
     * Get defaultAddresses
     * 
     * @return defaultAddresses
     */
    @ApiModelProperty(value = "")
    @Valid
    public NewMessageDefaultAddresses getDefaultAddresses() {

	return defaultAddresses;
    }

    public void setDefaultAddresses(NewMessageDefaultAddresses defaultAddresses) {

	this.defaultAddresses = defaultAddresses;
    }

    public NewMessage fiscalCode(String fiscalCode) {

	this.fiscalCode = fiscalCode;
	return this;
    }

    /**
     * User's fiscal code.
     * 
     * @return fiscalCode
     */
    @ApiModelProperty(example = "SPNDNL80R13C555X", value = "User's fiscal code.")
    public String getFiscalCode() {

	return fiscalCode;
    }

    public void setFiscalCode(String fiscalCode) {

	this.fiscalCode = fiscalCode;
    }

    @Override
    public boolean equals(java.lang.Object o) {

	if (this == o) {
	    return true;
	}
	if (o == null || getClass() != o.getClass()) {
	    return false;
	}
	NewMessage newMessage = (NewMessage) o;
	return Objects.equals(this.timeToLive, newMessage.timeToLive) && Objects.equals(this.content, newMessage.content)
		&& Objects.equals(this.defaultAddresses, newMessage.defaultAddresses) && Objects.equals(this.fiscalCode, newMessage.fiscalCode);
    }

    @Override
    public int hashCode() {

	return Objects.hash(timeToLive, content, defaultAddresses, fiscalCode);
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class NewMessage {\n");
	sb.append("    timeToLive: ").append(toIndentedString(timeToLive)).append("\n");
	sb.append("    content: ").append(toIndentedString(content)).append("\n");
	sb.append("    defaultAddresses: ").append(toIndentedString(defaultAddresses)).append("\n");
	sb.append("    fiscalCode: ").append(toIndentedString(fiscalCode)).append("\n");
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
