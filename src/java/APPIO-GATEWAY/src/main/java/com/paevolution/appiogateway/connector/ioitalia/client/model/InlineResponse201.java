package com.paevolution.appiogateway.connector.ioitalia.client.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * InlineResponse201
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-12-24T13:31:55.506+01:00[Europe/Berlin]")
public class InlineResponse201 {

    @JsonProperty("id")
    private String id;

    public InlineResponse201 id(String id) {

	this.id = id;
	return this;
    }

    /**
     * The identifier of the created message.
     * 
     * @return id
     */
    @ApiModelProperty(value = "The identifier of the created message.")
    public String getId() {

	return id;
    }

    public void setId(String id) {

	this.id = id;
    }

    @Override
    public boolean equals(java.lang.Object o) {

	if (this == o) {
	    return true;
	}
	if (o == null || getClass() != o.getClass()) {
	    return false;
	}
	InlineResponse201 inlineResponse201 = (InlineResponse201) o;
	return Objects.equals(this.id, inlineResponse201.id);
    }

    @Override
    public int hashCode() {

	return Objects.hash(id);
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class InlineResponse201 {\n");
	sb.append("    id: ").append(toIndentedString(id)).append("\n");
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
