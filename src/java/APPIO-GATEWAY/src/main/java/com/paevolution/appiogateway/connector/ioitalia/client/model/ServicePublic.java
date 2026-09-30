package com.paevolution.appiogateway.connector.ioitalia.client.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * A Service associated to an user&#39;s subscription.
 */
@ApiModel(description = "A Service associated to an user's subscription.")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-12-24T13:31:55.506+01:00[Europe/Berlin]")
public class ServicePublic {

    @JsonProperty("service_id")
    private String serviceId;
    @JsonProperty("service_name")
    private String serviceName;
    @JsonProperty("organization_name")
    private String organizationName;
    @JsonProperty("department_name")
    private String departmentName;
    @JsonProperty("organization_fiscal_code")
    private String organizationFiscalCode;
    @JsonProperty("available_notification_channels")
    @Valid
    private List<String> availableNotificationChannels = null;
    @JsonProperty("version")
    private Integer version;
    @JsonProperty("service_metadata")
    private ServiceMetadata serviceMetadata;

    public ServicePublic serviceId(String serviceId) {

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

    public ServicePublic serviceName(String serviceName) {

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

    public ServicePublic organizationName(String organizationName) {

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

    public ServicePublic departmentName(String departmentName) {

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

    public ServicePublic organizationFiscalCode(String organizationFiscalCode) {

	this.organizationFiscalCode = organizationFiscalCode;
	return this;
    }

    /**
     * Organization fiscal code.
     * 
     * @return organizationFiscalCode
     */
    @ApiModelProperty(example = "12345678901", required = true, value = "Organization fiscal code.")
    @NotNull
    public String getOrganizationFiscalCode() {

	return organizationFiscalCode;
    }

    public void setOrganizationFiscalCode(String organizationFiscalCode) {

	this.organizationFiscalCode = organizationFiscalCode;
    }

    public ServicePublic availableNotificationChannels(List<String> availableNotificationChannels) {

	this.availableNotificationChannels = availableNotificationChannels;
	return this;
    }

    public ServicePublic addAvailableNotificationChannelsItem(String availableNotificationChannelsItem) {

	if (this.availableNotificationChannels == null) {
	    this.availableNotificationChannels = new ArrayList<>();
	}
	this.availableNotificationChannels.add(availableNotificationChannelsItem);
	return this;
    }

    /**
     * All the notification channels available for a service.
     * 
     * @return availableNotificationChannels
     */
    @ApiModelProperty(value = "All the notification channels available for a service.")
    public List<String> getAvailableNotificationChannels() {

	return availableNotificationChannels;
    }

    public void setAvailableNotificationChannels(List<String> availableNotificationChannels) {

	this.availableNotificationChannels = availableNotificationChannels;
    }

    public ServicePublic version(Integer version) {

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

    public ServicePublic serviceMetadata(ServiceMetadata serviceMetadata) {

	this.serviceMetadata = serviceMetadata;
	return this;
    }

    /**
     * Get serviceMetadata
     * 
     * @return serviceMetadata
     */
    @ApiModelProperty(value = "")
    @Valid
    public ServiceMetadata getServiceMetadata() {

	return serviceMetadata;
    }

    public void setServiceMetadata(ServiceMetadata serviceMetadata) {

	this.serviceMetadata = serviceMetadata;
    }

    @Override
    public boolean equals(java.lang.Object o) {

	if (this == o) {
	    return true;
	}
	if (o == null || getClass() != o.getClass()) {
	    return false;
	}
	ServicePublic servicePublic = (ServicePublic) o;
	return Objects.equals(this.serviceId, servicePublic.serviceId) && Objects.equals(this.serviceName, servicePublic.serviceName)
		&& Objects.equals(this.organizationName, servicePublic.organizationName)
		&& Objects.equals(this.departmentName, servicePublic.departmentName)
		&& Objects.equals(this.organizationFiscalCode, servicePublic.organizationFiscalCode)
		&& Objects.equals(this.availableNotificationChannels, servicePublic.availableNotificationChannels)
		&& Objects.equals(this.version, servicePublic.version) && Objects.equals(this.serviceMetadata, servicePublic.serviceMetadata);
    }

    @Override
    public int hashCode() {

	return Objects.hash(serviceId, serviceName, organizationName, departmentName, organizationFiscalCode, availableNotificationChannels, version,
		serviceMetadata);
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class ServicePublic {\n");
	sb.append("    serviceId: ").append(toIndentedString(serviceId)).append("\n");
	sb.append("    serviceName: ").append(toIndentedString(serviceName)).append("\n");
	sb.append("    organizationName: ").append(toIndentedString(organizationName)).append("\n");
	sb.append("    departmentName: ").append(toIndentedString(departmentName)).append("\n");
	sb.append("    organizationFiscalCode: ").append(toIndentedString(organizationFiscalCode)).append("\n");
	sb.append("    availableNotificationChannels: ").append(toIndentedString(availableNotificationChannels)).append("\n");
	sb.append("    version: ").append(toIndentedString(version)).append("\n");
	sb.append("    serviceMetadata: ").append(toIndentedString(serviceMetadata)).append("\n");
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
