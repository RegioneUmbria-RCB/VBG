package com.paevolution.appiogateway.connector.ioitalia.client.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Default addresses for notifying the recipient of the message in case no address for the related channel is set in his
 * profile.
 */
@ApiModel(description = "Default addresses for notifying the recipient of the message in case no address for the related channel is set in his profile.")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-12-24T13:31:55.506+01:00[Europe/Berlin]")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewMessageDefaultAddresses {

    @JsonProperty("email")
    private String email;

    public NewMessageDefaultAddresses email(String email) {

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

    @Override
    public boolean equals(java.lang.Object o) {

	if (this == o) {
	    return true;
	}
	if (o == null || getClass() != o.getClass()) {
	    return false;
	}
	NewMessageDefaultAddresses newMessageDefaultAddresses = (NewMessageDefaultAddresses) o;
	return Objects.equals(this.email, newMessageDefaultAddresses.email);
    }

    @Override
    public int hashCode() {

	return Objects.hash(email);
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class NewMessageDefaultAddresses {\n");
	sb.append("    email: ").append(toIndentedString(email)).append("\n");
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
