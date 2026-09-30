package com.paevolution.appiogateway.connector.ioitalia.client.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * A Service tied to an user&#39;s subscription.
 */
@ApiModel(description = "A Service tied to an user's subscription.")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-12-24T13:31:55.506+01:00[Europe/Berlin]")
public class Service {

    @JsonProperty("service_id")
    private String serviceId;
    @JsonProperty("service_name")
    private String serviceName;
    @JsonProperty("organization_name")
    private String organizationName;
    @JsonProperty("department_name")
    private String departmentName;
    @JsonProperty("authorized_cidrs")
    @Valid
    private List<String> authorizedCidrs = new ArrayList<>();
    @JsonProperty("authorized_recipients")
    @Valid
    private List<String> authorizedRecipients = new ArrayList<>();
    @JsonProperty("is_visible")
    private Boolean isVisible = false;
    @JsonProperty("max_allowed_payment_amount")
    private Integer maxAllowedPaymentAmount = 0;
    @JsonProperty("organization_fiscal_code")
    private String organizationFiscalCode;
    @JsonProperty("version")
    private Integer version;
    @JsonProperty("id")
    private String id;
    @JsonProperty("require_secure_channels")
    private Boolean requireSecureChannels = false;
    @JsonProperty("service_metadata")
    private ServiceMetadata serviceMetadata;

    public Service serviceId(String serviceId) {

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

    public Service serviceName(String serviceName) {

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

    public Service organizationName(String organizationName) {

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

    public Service departmentName(String departmentName) {

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

    public Service authorizedCidrs(List<String> authorizedCidrs) {

	this.authorizedCidrs = authorizedCidrs;
	return this;
    }

    public Service addAuthorizedCidrsItem(String authorizedCidrsItem) {

	this.authorizedCidrs.add(authorizedCidrsItem);
	return this;
    }

    /**
     * Allowed source IPs or CIDRs for this service.
     * 
     * @return authorizedCidrs
     */
    @ApiModelProperty(required = true, value = "Allowed source IPs or CIDRs for this service.")
    @NotNull
    public List<String> getAuthorizedCidrs() {

	return authorizedCidrs;
    }

    public void setAuthorizedCidrs(List<String> authorizedCidrs) {

	this.authorizedCidrs = authorizedCidrs;
    }

    public Service authorizedRecipients(List<String> authorizedRecipients) {

	this.authorizedRecipients = authorizedRecipients;
	return this;
    }

    public Service addAuthorizedRecipientsItem(String authorizedRecipientsItem) {

	this.authorizedRecipients.add(authorizedRecipientsItem);
	return this;
    }

    /**
     * If non empty, the service will be able to send messages only to these fiscal codes.
     * 
     * @return authorizedRecipients
     */
    @ApiModelProperty(required = true, value = "If non empty, the service will be able to send messages only to these fiscal codes.")
    @NotNull
    public List<String> getAuthorizedRecipients() {

	return authorizedRecipients;
    }

    public void setAuthorizedRecipients(List<String> authorizedRecipients) {

	this.authorizedRecipients = authorizedRecipients;
    }

    public Service isVisible(Boolean isVisible) {

	this.isVisible = isVisible;
	return this;
    }

    /**
     * Get isVisible
     * 
     * @return isVisible
     */
    @ApiModelProperty(value = "")
    public Boolean getIsVisible() {

	return isVisible;
    }

    public void setIsVisible(Boolean isVisible) {

	this.isVisible = isVisible;
    }

    public Service maxAllowedPaymentAmount(Integer maxAllowedPaymentAmount) {

	this.maxAllowedPaymentAmount = maxAllowedPaymentAmount;
	return this;
    }

    /**
     * Maximum amount in euro cents that a service is allowed to charge to a user. minimum: 0 maximum: 9999999999
     * 
     * @return maxAllowedPaymentAmount
     */
    @ApiModelProperty(value = "Maximum amount in euro cents that a service is allowed to charge to a user.")
    @Min(0)
    @Max(Integer.MAX_VALUE)
    public Integer getMaxAllowedPaymentAmount() {

	return maxAllowedPaymentAmount;
    }

    public void setMaxAllowedPaymentAmount(Integer maxAllowedPaymentAmount) {

	this.maxAllowedPaymentAmount = maxAllowedPaymentAmount;
    }

    public Service organizationFiscalCode(String organizationFiscalCode) {

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

    public Service version(Integer version) {

	this.version = version;
	return this;
    }

    /**
     * Get version
     * 
     * @return version
     */
    @ApiModelProperty(value = "")
    public Integer getVersion() {

	return version;
    }

    public void setVersion(Integer version) {

	this.version = version;
    }

    public Service id(String id) {

	this.id = id;
	return this;
    }

    /**
     * Get id
     * 
     * @return id
     */
    @ApiModelProperty(value = "")
    public String getId() {

	return id;
    }

    public void setId(String id) {

	this.id = id;
    }

    public Service requireSecureChannels(Boolean requireSecureChannels) {

	this.requireSecureChannels = requireSecureChannels;
	return this;
    }

    /**
     * Get requireSecureChannels
     * 
     * @return requireSecureChannels
     */
    @ApiModelProperty(value = "")
    public Boolean getRequireSecureChannels() {

	return requireSecureChannels;
    }

    public void setRequireSecureChannels(Boolean requireSecureChannels) {

	this.requireSecureChannels = requireSecureChannels;
    }

    public Service serviceMetadata(ServiceMetadata serviceMetadata) {

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
	Service service = (Service) o;
	return Objects.equals(this.serviceId, service.serviceId) && Objects.equals(this.serviceName, service.serviceName)
		&& Objects.equals(this.organizationName, service.organizationName) && Objects.equals(this.departmentName, service.departmentName)
		&& Objects.equals(this.authorizedCidrs, service.authorizedCidrs)
		&& Objects.equals(this.authorizedRecipients, service.authorizedRecipients) && Objects.equals(this.isVisible, service.isVisible)
		&& Objects.equals(this.maxAllowedPaymentAmount, service.maxAllowedPaymentAmount)
		&& Objects.equals(this.organizationFiscalCode, service.organizationFiscalCode) && Objects.equals(this.version, service.version)
		&& Objects.equals(this.id, service.id) && Objects.equals(this.requireSecureChannels, service.requireSecureChannels)
		&& Objects.equals(this.serviceMetadata, service.serviceMetadata);
    }

    @Override
    public int hashCode() {

	return Objects.hash(serviceId, serviceName, organizationName, departmentName, authorizedCidrs, authorizedRecipients, isVisible,
		maxAllowedPaymentAmount, organizationFiscalCode, version, id, requireSecureChannels, serviceMetadata);
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class Service {\n");
	sb.append("    serviceId: ").append(toIndentedString(serviceId)).append("\n");
	sb.append("    serviceName: ").append(toIndentedString(serviceName)).append("\n");
	sb.append("    organizationName: ").append(toIndentedString(organizationName)).append("\n");
	sb.append("    departmentName: ").append(toIndentedString(departmentName)).append("\n");
	sb.append("    authorizedCidrs: ").append(toIndentedString(authorizedCidrs)).append("\n");
	sb.append("    authorizedRecipients: ").append(toIndentedString(authorizedRecipients)).append("\n");
	sb.append("    isVisible: ").append(toIndentedString(isVisible)).append("\n");
	sb.append("    maxAllowedPaymentAmount: ").append(toIndentedString(maxAllowedPaymentAmount)).append("\n");
	sb.append("    organizationFiscalCode: ").append(toIndentedString(organizationFiscalCode)).append("\n");
	sb.append("    version: ").append(toIndentedString(version)).append("\n");
	sb.append("    id: ").append(toIndentedString(id)).append("\n");
	sb.append("    requireSecureChannels: ").append(toIndentedString(requireSecureChannels)).append("\n");
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
