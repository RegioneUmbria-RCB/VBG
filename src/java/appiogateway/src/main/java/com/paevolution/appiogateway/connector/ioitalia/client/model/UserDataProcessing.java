package com.paevolution.appiogateway.connector.ioitalia.client.model;

import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * A representation of a single user data processing request
 */
@ApiModel(description = "A representation of a single user data processing request")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-12-24T13:31:55.506+01:00[Europe/Berlin]")
public class UserDataProcessing {

    @JsonProperty("choice")
    private UserDataProcessingChoice choice;
    @JsonProperty("status")
    private UserDataProcessingStatus status;
    @JsonProperty("created_at")
    private java.sql.Timestamp createdAt;
    @JsonProperty("updated_at")
    private java.sql.Timestamp updatedAt;
    @JsonProperty("version")
    private Integer version;

    public UserDataProcessing choice(UserDataProcessingChoice choice) {

	this.choice = choice;
	return this;
    }

    /**
     * Get choice
     * 
     * @return choice
     */
    @ApiModelProperty(required = true, value = "")
    @NotNull
    @Valid
    public UserDataProcessingChoice getChoice() {

	return choice;
    }

    public void setChoice(UserDataProcessingChoice choice) {

	this.choice = choice;
    }

    public UserDataProcessing status(UserDataProcessingStatus status) {

	this.status = status;
	return this;
    }

    /**
     * Get status
     * 
     * @return status
     */
    @ApiModelProperty(required = true, value = "")
    @NotNull
    @Valid
    public UserDataProcessingStatus getStatus() {

	return status;
    }

    public void setStatus(UserDataProcessingStatus status) {

	this.status = status;
    }

    public UserDataProcessing createdAt(java.sql.Timestamp createdAt) {

	this.createdAt = createdAt;
	return this;
    }

    /**
     * Get createdAt
     * 
     * @return createdAt
     */
    @ApiModelProperty(value = "")
    @Valid
    public java.sql.Timestamp getCreatedAt() {

	return createdAt;
    }

    public void setCreatedAt(java.sql.Timestamp createdAt) {

	this.createdAt = createdAt;
    }

    public UserDataProcessing updatedAt(java.sql.Timestamp updatedAt) {

	this.updatedAt = updatedAt;
	return this;
    }

    /**
     * Get updatedAt
     * 
     * @return updatedAt
     */
    @ApiModelProperty(value = "")
    @Valid
    public java.sql.Timestamp getUpdatedAt() {

	return updatedAt;
    }

    public void setUpdatedAt(java.sql.Timestamp updatedAt) {

	this.updatedAt = updatedAt;
    }

    public UserDataProcessing version(Integer version) {

	this.version = version;
	return this;
    }

    /**
     * Get version
     * 
     * @return version
     */
    @ApiModelProperty(required = true, value = "")
    @NotNull
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
	UserDataProcessing userDataProcessing = (UserDataProcessing) o;
	return Objects.equals(this.choice, userDataProcessing.choice) && Objects.equals(this.status, userDataProcessing.status)
		&& Objects.equals(this.createdAt, userDataProcessing.createdAt) && Objects.equals(this.updatedAt, userDataProcessing.updatedAt)
		&& Objects.equals(this.version, userDataProcessing.version);
    }

    @Override
    public int hashCode() {

	return Objects.hash(choice, status, createdAt, updatedAt, version);
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class UserDataProcessing {\n");
	sb.append("    choice: ").append(toIndentedString(choice)).append("\n");
	sb.append("    status: ").append(toIndentedString(status)).append("\n");
	sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
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
