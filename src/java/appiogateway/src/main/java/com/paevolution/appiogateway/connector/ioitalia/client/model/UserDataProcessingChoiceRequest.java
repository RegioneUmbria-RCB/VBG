package com.paevolution.appiogateway.connector.ioitalia.client.model;

import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * A request wrapper for User data processing choice
 */
@ApiModel(description = "A request wrapper for User data processing choice")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-12-24T13:31:55.506+01:00[Europe/Berlin]")
public class UserDataProcessingChoiceRequest {

    @JsonProperty("choice")
    private UserDataProcessingChoice choice;

    public UserDataProcessingChoiceRequest choice(UserDataProcessingChoice choice) {

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

    @Override
    public boolean equals(java.lang.Object o) {

	if (this == o) {
	    return true;
	}
	if (o == null || getClass() != o.getClass()) {
	    return false;
	}
	UserDataProcessingChoiceRequest userDataProcessingChoiceRequest = (UserDataProcessingChoiceRequest) o;
	return Objects.equals(this.choice, userDataProcessingChoiceRequest.choice);
    }

    @Override
    public int hashCode() {

	return Objects.hash(choice);
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class UserDataProcessingChoiceRequest {\n");
	sb.append("    choice: ").append(toIndentedString(choice)).append("\n");
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
