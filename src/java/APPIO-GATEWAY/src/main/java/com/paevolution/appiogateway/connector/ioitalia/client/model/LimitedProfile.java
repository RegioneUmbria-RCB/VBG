package com.paevolution.appiogateway.connector.ioitalia.client.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Describes the citizen&#39;s profile, mostly interesting for preferences attributes.
 */
@ApiModel(description = "Describes the citizen's profile, mostly interesting for preferences attributes.")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-12-24T13:31:55.506+01:00[Europe/Berlin]")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LimitedProfile {

    @JsonProperty("sender_allowed")
    private Boolean senderAllowed;
    @JsonProperty("preferred_languages")
    @Valid
    private List<String> preferredLanguages = null;

    public LimitedProfile senderAllowed(Boolean senderAllowed) {

	this.senderAllowed = senderAllowed;
	return this;
    }

    /**
     * True in case the service that made the request can send messages to the user identified by this profile (false
     * otherwise).
     * 
     * @return senderAllowed
     */
    @ApiModelProperty(required = true, value = "True in case the service that made the request can send messages to the user identified by this profile (false otherwise).")
    @NotNull
    public Boolean getSenderAllowed() {

	return senderAllowed;
    }

    public void setSenderAllowed(Boolean senderAllowed) {

	this.senderAllowed = senderAllowed;
    }

    public LimitedProfile preferredLanguages(List<String> preferredLanguages) {

	this.preferredLanguages = preferredLanguages;
	return this;
    }

    public LimitedProfile addPreferredLanguagesItem(String preferredLanguagesItem) {

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

    @Override
    public boolean equals(java.lang.Object o) {

	if (this == o) {
	    return true;
	}
	if (o == null || getClass() != o.getClass()) {
	    return false;
	}
	LimitedProfile limitedProfile = (LimitedProfile) o;
	return Objects.equals(this.senderAllowed, limitedProfile.senderAllowed)
		&& Objects.equals(this.preferredLanguages, limitedProfile.preferredLanguages);
    }

    @Override
    public int hashCode() {

	return Objects.hash(senderAllowed, preferredLanguages);
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class LimitedProfile {\n");
	sb.append("    senderAllowed: ").append(toIndentedString(senderAllowed)).append("\n");
	sb.append("    preferredLanguages: ").append(toIndentedString(preferredLanguages)).append("\n");
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
