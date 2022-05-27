package com.paevolution.appiogateway.connector.ioitalia.client.model;

import java.util.Objects;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Describes a new citizen&#39;s profile. Used for profile creation.
 */
@ApiModel(description = "Describes a new citizen's profile. Used for profile creation.")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-12-24T13:31:55.506+01:00[Europe/Berlin]")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewProfile {

    @JsonProperty("email")
    private String email;
    @JsonProperty("is_email_validated")
    private Boolean isEmailValidated;

    public NewProfile email(String email) {

	this.email = email;
	return this;
    }

    /**
     * Get email
     * 
     * @return email
     */
    @ApiModelProperty(example = "foobar@example.com", value = "")
    @javax.validation.constraints.Email
    public String getEmail() {

	return email;
    }

    public void setEmail(String email) {

	this.email = email;
    }

    public NewProfile isEmailValidated(Boolean isEmailValidated) {

	this.isEmailValidated = isEmailValidated;
	return this;
    }

    /**
     * True if the user email has been validated.
     * 
     * @return isEmailValidated
     */
    @ApiModelProperty(required = true, value = "True if the user email has been validated.")
    @NotNull
    public Boolean getIsEmailValidated() {

	return isEmailValidated;
    }

    public void setIsEmailValidated(Boolean isEmailValidated) {

	this.isEmailValidated = isEmailValidated;
    }

    @Override
    public boolean equals(java.lang.Object o) {

	if (this == o) {
	    return true;
	}
	if (o == null || getClass() != o.getClass()) {
	    return false;
	}
	NewProfile newProfile = (NewProfile) o;
	return Objects.equals(this.email, newProfile.email) && Objects.equals(this.isEmailValidated, newProfile.isEmailValidated);
    }

    @Override
    public int hashCode() {

	return Objects.hash(email, isEmailValidated);
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class NewProfile {\n");
	sb.append("    email: ").append(toIndentedString(email)).append("\n");
	sb.append("    isEmailValidated: ").append(toIndentedString(isEmailValidated)).append("\n");
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
