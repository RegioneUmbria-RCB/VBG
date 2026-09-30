package com.paevolution.appiogateway.connector.ioitalia.client.model;

import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * ServiceMetadata
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-12-24T13:31:55.506+01:00[Europe/Berlin]")
public class ServiceMetadata {

    @JsonProperty("description")
    private String description;
    @JsonProperty("web_url")
    private String webUrl;
    @JsonProperty("app_ios")
    private String appIos;
    @JsonProperty("app_android")
    private String appAndroid;
    @JsonProperty("tos_url")
    private String tosUrl;
    @JsonProperty("privacy_url")
    private String privacyUrl;
    @JsonProperty("address")
    private String address;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("email")
    private String email;
    @JsonProperty("pec")
    private String pec;
    @JsonProperty("scope")
    private String scope;

    public ServiceMetadata description(String description) {

	this.description = description;
	return this;
    }

    /**
     * Get description
     * 
     * @return description
     */
    @ApiModelProperty(value = "")
    @Size(min = 1)
    public String getDescription() {

	return description;
    }

    public void setDescription(String description) {

	this.description = description;
    }

    public ServiceMetadata webUrl(String webUrl) {

	this.webUrl = webUrl;
	return this;
    }

    /**
     * Get webUrl
     * 
     * @return webUrl
     */
    @ApiModelProperty(value = "")
    @Size(min = 1)
    public String getWebUrl() {

	return webUrl;
    }

    public void setWebUrl(String webUrl) {

	this.webUrl = webUrl;
    }

    public ServiceMetadata appIos(String appIos) {

	this.appIos = appIos;
	return this;
    }

    /**
     * Get appIos
     * 
     * @return appIos
     */
    @ApiModelProperty(value = "")
    @Size(min = 1)
    public String getAppIos() {

	return appIos;
    }

    public void setAppIos(String appIos) {

	this.appIos = appIos;
    }

    public ServiceMetadata appAndroid(String appAndroid) {

	this.appAndroid = appAndroid;
	return this;
    }

    /**
     * Get appAndroid
     * 
     * @return appAndroid
     */
    @ApiModelProperty(value = "")
    @Size(min = 1)
    public String getAppAndroid() {

	return appAndroid;
    }

    public void setAppAndroid(String appAndroid) {

	this.appAndroid = appAndroid;
    }

    public ServiceMetadata tosUrl(String tosUrl) {

	this.tosUrl = tosUrl;
	return this;
    }

    /**
     * Get tosUrl
     * 
     * @return tosUrl
     */
    @ApiModelProperty(value = "")
    @Size(min = 1)
    public String getTosUrl() {

	return tosUrl;
    }

    public void setTosUrl(String tosUrl) {

	this.tosUrl = tosUrl;
    }

    public ServiceMetadata privacyUrl(String privacyUrl) {

	this.privacyUrl = privacyUrl;
	return this;
    }

    /**
     * Get privacyUrl
     * 
     * @return privacyUrl
     */
    @ApiModelProperty(value = "")
    @Size(min = 1)
    public String getPrivacyUrl() {

	return privacyUrl;
    }

    public void setPrivacyUrl(String privacyUrl) {

	this.privacyUrl = privacyUrl;
    }

    public ServiceMetadata address(String address) {

	this.address = address;
	return this;
    }

    /**
     * Get address
     * 
     * @return address
     */
    @ApiModelProperty(value = "")
    @Size(min = 1)
    public String getAddress() {

	return address;
    }

    public void setAddress(String address) {

	this.address = address;
    }

    public ServiceMetadata phone(String phone) {

	this.phone = phone;
	return this;
    }

    /**
     * Get phone
     * 
     * @return phone
     */
    @ApiModelProperty(value = "")
    @Size(min = 1)
    public String getPhone() {

	return phone;
    }

    public void setPhone(String phone) {

	this.phone = phone;
    }

    public ServiceMetadata email(String email) {

	this.email = email;
	return this;
    }

    /**
     * Get email
     * 
     * @return email
     */
    @ApiModelProperty(value = "")
    @Size(min = 1)
    public String getEmail() {

	return email;
    }

    public void setEmail(String email) {

	this.email = email;
    }

    public ServiceMetadata pec(String pec) {

	this.pec = pec;
	return this;
    }

    /**
     * Get pec
     * 
     * @return pec
     */
    @ApiModelProperty(value = "")
    @Size(min = 1)
    public String getPec() {

	return pec;
    }

    public void setPec(String pec) {

	this.pec = pec;
    }

    public ServiceMetadata scope(String scope) {

	this.scope = scope;
	return this;
    }

    /**
     * Get scope
     * 
     * @return scope
     */
    @ApiModelProperty(required = true, value = "")
    @NotNull
    public String getScope() {

	return scope;
    }

    public void setScope(String scope) {

	this.scope = scope;
    }

    @Override
    public boolean equals(java.lang.Object o) {

	if (this == o) {
	    return true;
	}
	if (o == null || getClass() != o.getClass()) {
	    return false;
	}
	ServiceMetadata serviceMetadata = (ServiceMetadata) o;
	return Objects.equals(this.description, serviceMetadata.description) && Objects.equals(this.webUrl, serviceMetadata.webUrl)
		&& Objects.equals(this.appIos, serviceMetadata.appIos) && Objects.equals(this.appAndroid, serviceMetadata.appAndroid)
		&& Objects.equals(this.tosUrl, serviceMetadata.tosUrl) && Objects.equals(this.privacyUrl, serviceMetadata.privacyUrl)
		&& Objects.equals(this.address, serviceMetadata.address) && Objects.equals(this.phone, serviceMetadata.phone)
		&& Objects.equals(this.email, serviceMetadata.email) && Objects.equals(this.pec, serviceMetadata.pec)
		&& Objects.equals(this.scope, serviceMetadata.scope);
    }

    @Override
    public int hashCode() {

	return Objects.hash(description, webUrl, appIos, appAndroid, tosUrl, privacyUrl, address, phone, email, pec, scope);
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class ServiceMetadata {\n");
	sb.append("    description: ").append(toIndentedString(description)).append("\n");
	sb.append("    webUrl: ").append(toIndentedString(webUrl)).append("\n");
	sb.append("    appIos: ").append(toIndentedString(appIos)).append("\n");
	sb.append("    appAndroid: ").append(toIndentedString(appAndroid)).append("\n");
	sb.append("    tosUrl: ").append(toIndentedString(tosUrl)).append("\n");
	sb.append("    privacyUrl: ").append(toIndentedString(privacyUrl)).append("\n");
	sb.append("    address: ").append(toIndentedString(address)).append("\n");
	sb.append("    phone: ").append(toIndentedString(phone)).append("\n");
	sb.append("    email: ").append(toIndentedString(email)).append("\n");
	sb.append("    pec: ").append(toIndentedString(pec)).append("\n");
	sb.append("    scope: ").append(toIndentedString(scope)).append("\n");
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
