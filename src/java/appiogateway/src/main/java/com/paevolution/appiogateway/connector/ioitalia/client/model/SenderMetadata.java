package com.paevolution.appiogateway.connector.ioitalia.client.model;

import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Metadata associated to a sender (service).
 */
@ApiModel(description = "Metadata associated to a sender (service).")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-12-24T13:31:55.506+01:00[Europe/Berlin]")
public class SenderMetadata {

    @JsonProperty("service_name")
    private String serviceName;
    @JsonProperty("organization_name")
    private String organizationName;
    @JsonProperty("department_name")
    private String departmentName;

    public SenderMetadata serviceName(String serviceName) {

	this.serviceName = serviceName;
	return this;
    }

    /**
     * The name of the service. Will be added to the content of sent messages.
     * 
     * @return serviceName
     */
    @ApiModelProperty(required = true, value = "The name of the service. Will be added to the content of sent messages.")
    @NotNull
    @Size(min = 1)
    public String getServiceName() {

	return serviceName;
    }

    public void setServiceName(String serviceName) {

	this.serviceName = serviceName;
    }

    public SenderMetadata organizationName(String organizationName) {

	this.organizationName = organizationName;
	return this;
    }

    /**
     * The organization that runs the service. Will be added to the content of sent messages to identify the sender.
     * 
     * @return organizationName
     */
    @ApiModelProperty(required = true, value = "The organization that runs the service. Will be added to the content of sent messages to identify the sender.")
    @NotNull
    @Size(min = 1)
    public String getOrganizationName() {

	return organizationName;
    }

    public void setOrganizationName(String organizationName) {

	this.organizationName = organizationName;
    }

    public SenderMetadata departmentName(String departmentName) {

	this.departmentName = departmentName;
	return this;
    }

    /**
     * The department inside the organization that runs the service. Will be added to the content of sent messages.
     * 
     * @return departmentName
     */
    @ApiModelProperty(required = true, value = "The department inside the organization that runs the service. Will be added to the content of sent messages.")
    @NotNull
    @Size(min = 1)
    public String getDepartmentName() {

	return departmentName;
    }

    public void setDepartmentName(String departmentName) {

	this.departmentName = departmentName;
    }

    @Override
    public boolean equals(java.lang.Object o) {

	if (this == o) {
	    return true;
	}
	if (o == null || getClass() != o.getClass()) {
	    return false;
	}
	SenderMetadata senderMetadata = (SenderMetadata) o;
	return Objects.equals(this.serviceName, senderMetadata.serviceName) && Objects.equals(this.organizationName, senderMetadata.organizationName)
		&& Objects.equals(this.departmentName, senderMetadata.departmentName);
    }

    @Override
    public int hashCode() {

	return Objects.hash(serviceName, organizationName, departmentName);
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class SenderMetadata {\n");
	sb.append("    serviceName: ").append(toIndentedString(serviceName)).append("\n");
	sb.append("    organizationName: ").append(toIndentedString(organizationName)).append("\n");
	sb.append("    departmentName: ").append(toIndentedString(departmentName)).append("\n");
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
