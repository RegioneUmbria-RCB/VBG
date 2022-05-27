package com.paevolution.appiogateway.connector.ioitalia.client.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Describes the citizen&#39;s profile, mostly interesting for preferences attributes.
 */
@ApiModel(description = "Describes the citizen's profile, mostly interesting for preferences attributes.")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-12-24T13:31:55.506+01:00[Europe/Berlin]")
public class ExtendedProfile {

    @JsonProperty("email")
    private String email;
    @JsonProperty("blocked_inbox_or_channels")
    @Valid
    private Map<String, List<String>> blockedInboxOrChannels = null;
    @JsonProperty("preferred_languages")
    @Valid
    private List<String> preferredLanguages = null;
    @JsonProperty("is_inbox_enabled")
    private Boolean isInboxEnabled;
    @JsonProperty("accepted_tos_version")
    private BigDecimal acceptedTosVersion;
    @JsonProperty("is_webhook_enabled")
    private Boolean isWebhookEnabled;
    @JsonProperty("is_email_enabled")
    private Boolean isEmailEnabled;
    @JsonProperty("is_email_validated")
    private Boolean isEmailValidated;
    @JsonProperty("version")
    private Integer version;

    public ExtendedProfile email(String email) {

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

    public ExtendedProfile blockedInboxOrChannels(Map<String, List<String>> blockedInboxOrChannels) {

	this.blockedInboxOrChannels = blockedInboxOrChannels;
	return this;
    }

    public ExtendedProfile putBlockedInboxOrChannelsItem(String key, List<String> blockedInboxOrChannelsItem) {

	if (this.blockedInboxOrChannels == null) {
	    this.blockedInboxOrChannels = new HashMap<>();
	}
	this.blockedInboxOrChannels.put(key, blockedInboxOrChannelsItem);
	return this;
    }

    /**
     * All the notification channels blocked by the user. Each channel is related to a specific service (sender).
     * 
     * @return blockedInboxOrChannels
     */
    @ApiModelProperty(value = "All the notification channels blocked by the user. Each channel is related to a specific service (sender).")
    @Valid
    public Map<String, List<String>> getBlockedInboxOrChannels() {

	return blockedInboxOrChannels;
    }

    public void setBlockedInboxOrChannels(Map<String, List<String>> blockedInboxOrChannels) {

	this.blockedInboxOrChannels = blockedInboxOrChannels;
    }

    public ExtendedProfile preferredLanguages(List<String> preferredLanguages) {

	this.preferredLanguages = preferredLanguages;
	return this;
    }

    public ExtendedProfile addPreferredLanguagesItem(String preferredLanguagesItem) {

	if (this.preferredLanguages == null) {
	    this.preferredLanguages = new ArrayList<>();
	}
	this.preferredLanguages.add(preferredLanguagesItem);
	return this;
    }

    /**
     * Indicates the User's preferred written or spoken languages in order of preference. Generally used for selecting a
     * localized User interface. Valid values are concatenation of the ISO 639-1 two letter language code, an
     * underscore, and the ISO 3166-1 2 letter country code; e.g., 'en_US' specifies the language English and country
     * US.
     * 
     * @return preferredLanguages
     */
    @ApiModelProperty(value = "Indicates the User's preferred written or spoken languages in order of preference. Generally used for selecting a localized User interface. Valid values are concatenation of the ISO 639-1 two letter language code, an underscore, and the ISO 3166-1 2 letter country code; e.g., 'en_US' specifies the language English and country US.")
    public List<String> getPreferredLanguages() {

	return preferredLanguages;
    }

    public void setPreferredLanguages(List<String> preferredLanguages) {

	this.preferredLanguages = preferredLanguages;
    }

    public ExtendedProfile isInboxEnabled(Boolean isInboxEnabled) {

	this.isInboxEnabled = isInboxEnabled;
	return this;
    }

    /**
     * True if the recipient of a message wants to store its content for later retrieval.
     * 
     * @return isInboxEnabled
     */
    @ApiModelProperty(required = true, value = "True if the recipient of a message wants to store its content for later retrieval.")
    @NotNull
    public Boolean getIsInboxEnabled() {

	return isInboxEnabled;
    }

    public void setIsInboxEnabled(Boolean isInboxEnabled) {

	this.isInboxEnabled = isInboxEnabled;
    }

    public ExtendedProfile acceptedTosVersion(BigDecimal acceptedTosVersion) {

	this.acceptedTosVersion = acceptedTosVersion;
	return this;
    }

    /**
     * Version of latest terms of service accepted by the user. minimum: 1
     * 
     * @return acceptedTosVersion
     */
    @ApiModelProperty(value = "Version of latest terms of service accepted by the user.")
    @Valid
    @DecimalMin("1")
    public BigDecimal getAcceptedTosVersion() {

	return acceptedTosVersion;
    }

    public void setAcceptedTosVersion(BigDecimal acceptedTosVersion) {

	this.acceptedTosVersion = acceptedTosVersion;
    }

    public ExtendedProfile isWebhookEnabled(Boolean isWebhookEnabled) {

	this.isWebhookEnabled = isWebhookEnabled;
	return this;
    }

    /**
     * True if the recipient of a message wants to forward the notifications to the default webhook.
     * 
     * @return isWebhookEnabled
     */
    @ApiModelProperty(required = true, value = "True if the recipient of a message wants to forward the notifications to the default webhook.")
    @NotNull
    public Boolean getIsWebhookEnabled() {

	return isWebhookEnabled;
    }

    public void setIsWebhookEnabled(Boolean isWebhookEnabled) {

	this.isWebhookEnabled = isWebhookEnabled;
    }

    public ExtendedProfile isEmailEnabled(Boolean isEmailEnabled) {

	this.isEmailEnabled = isEmailEnabled;
	return this;
    }

    /**
     * True if the recipient of a message wants to forward the notifications to his email address.
     * 
     * @return isEmailEnabled
     */
    @ApiModelProperty(required = true, value = "True if the recipient of a message wants to forward the notifications to his email address.")
    @NotNull
    public Boolean getIsEmailEnabled() {

	return isEmailEnabled;
    }

    public void setIsEmailEnabled(Boolean isEmailEnabled) {

	this.isEmailEnabled = isEmailEnabled;
    }

    public ExtendedProfile isEmailValidated(Boolean isEmailValidated) {

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

    public ExtendedProfile version(Integer version) {

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
	ExtendedProfile extendedProfile = (ExtendedProfile) o;
	return Objects.equals(this.email, extendedProfile.email)
		&& Objects.equals(this.blockedInboxOrChannels, extendedProfile.blockedInboxOrChannels)
		&& Objects.equals(this.preferredLanguages, extendedProfile.preferredLanguages)
		&& Objects.equals(this.isInboxEnabled, extendedProfile.isInboxEnabled)
		&& Objects.equals(this.acceptedTosVersion, extendedProfile.acceptedTosVersion)
		&& Objects.equals(this.isWebhookEnabled, extendedProfile.isWebhookEnabled)
		&& Objects.equals(this.isEmailEnabled, extendedProfile.isEmailEnabled)
		&& Objects.equals(this.isEmailValidated, extendedProfile.isEmailValidated) && Objects.equals(this.version, extendedProfile.version);
    }

    @Override
    public int hashCode() {

	return Objects.hash(email, blockedInboxOrChannels, preferredLanguages, isInboxEnabled, acceptedTosVersion, isWebhookEnabled, isEmailEnabled,
		isEmailValidated, version);
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class ExtendedProfile {\n");
	sb.append("    email: ").append(toIndentedString(email)).append("\n");
	sb.append("    blockedInboxOrChannels: ").append(toIndentedString(blockedInboxOrChannels)).append("\n");
	sb.append("    preferredLanguages: ").append(toIndentedString(preferredLanguages)).append("\n");
	sb.append("    isInboxEnabled: ").append(toIndentedString(isInboxEnabled)).append("\n");
	sb.append("    acceptedTosVersion: ").append(toIndentedString(acceptedTosVersion)).append("\n");
	sb.append("    isWebhookEnabled: ").append(toIndentedString(isWebhookEnabled)).append("\n");
	sb.append("    isEmailEnabled: ").append(toIndentedString(isEmailEnabled)).append("\n");
	sb.append("    isEmailValidated: ").append(toIndentedString(isEmailValidated)).append("\n");
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
