package com.paevolution.appiogateway.connector.ioitalia.client.model;

import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Service identifier and version used to return list of services.
 */
@ApiModel(description = "Service identifier and version used to return list of services.")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-12-24T13:31:55.506+01:00[Europe/Berlin]")
public class ServiceTuple {

    @JsonProperty("service_id")
    private String serviceId;
    @JsonProperty("scope")
    private String scope;
    @JsonProperty("version")
    private Integer version;

    public ServiceTuple serviceId(String serviceId) {

	this.serviceId = serviceId;
	return this;
    }

    /**
     * The ID of the Service. Equals the subscriptionId of a registered API user.
     * 
     * @return serviceId
     */
    @ApiModelProperty(required = true, value = "The ID of the Service. Equals the subscriptionId of a registered API user.")
    @NotNull
    @Size(min = 1)
    public String getServiceId() {

	return serviceId;
    }

    public void setServiceId(String serviceId) {

	this.serviceId = serviceId;
    }

    public ServiceTuple scope(String scope) {

	this.scope = scope;
	return this;
    }

    /**
     * Get scope
     * 
     * @return scope
     */
    @ApiModelProperty(value = "")
    public String getScope() {

	return scope;
    }

    public void setScope(String scope) {

	this.scope = scope;
    }

    public ServiceTuple version(Integer version) {

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
	ServiceTuple serviceTuple = (ServiceTuple) o;
	return Objects.equals(this.serviceId, serviceTuple.serviceId) && Objects.equals(this.scope, serviceTuple.scope)
		&& Objects.equals(this.version, serviceTuple.version);
    }

    @Override
    public int hashCode() {

	return Objects.hash(serviceId, scope, version);
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class ServiceTuple {\n");
	sb.append("    serviceId: ").append(toIndentedString(serviceId)).append("\n");
	sb.append("    scope: ").append(toIndentedString(scope)).append("\n");
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
