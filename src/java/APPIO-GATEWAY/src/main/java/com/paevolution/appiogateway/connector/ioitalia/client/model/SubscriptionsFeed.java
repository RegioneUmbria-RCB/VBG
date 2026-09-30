package com.paevolution.appiogateway.connector.ioitalia.client.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * SubscriptionsFeed
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-12-24T13:31:55.506+01:00[Europe/Berlin]")
public class SubscriptionsFeed {

    @JsonProperty("dateUTC")
    private String dateUTC;
    @JsonProperty("subscriptions")
    @Valid
    private List<String> subscriptions = new ArrayList<>();
    @JsonProperty("unsubscriptions")
    @Valid
    private List<String> unsubscriptions = new ArrayList<>();

    public SubscriptionsFeed dateUTC(String dateUTC) {

	this.dateUTC = dateUTC;
	return this;
    }

    /**
     * A date in the format YYYY-MM-DD.
     * 
     * @return dateUTC
     */
    @ApiModelProperty(required = true, value = "A date in the format YYYY-MM-DD.")
    @NotNull
    @Pattern(regexp = "[0-9]{4}-[0-9]{2}-[0-9]{2}")
    @Size(min = 10, max = 10)
    public String getDateUTC() {

	return dateUTC;
    }

    public void setDateUTC(String dateUTC) {

	this.dateUTC = dateUTC;
    }

    public SubscriptionsFeed subscriptions(List<String> subscriptions) {

	this.subscriptions = subscriptions;
	return this;
    }

    public SubscriptionsFeed addSubscriptionsItem(String subscriptionsItem) {

	this.subscriptions.add(subscriptionsItem);
	return this;
    }

    /**
     * Get subscriptions
     * 
     * @return subscriptions
     */
    @ApiModelProperty(required = true, value = "")
    @NotNull
    public List<String> getSubscriptions() {

	return subscriptions;
    }

    public void setSubscriptions(List<String> subscriptions) {

	this.subscriptions = subscriptions;
    }

    public SubscriptionsFeed unsubscriptions(List<String> unsubscriptions) {

	this.unsubscriptions = unsubscriptions;
	return this;
    }

    public SubscriptionsFeed addUnsubscriptionsItem(String unsubscriptionsItem) {

	this.unsubscriptions.add(unsubscriptionsItem);
	return this;
    }

    /**
     * Get unsubscriptions
     * 
     * @return unsubscriptions
     */
    @ApiModelProperty(required = true, value = "")
    @NotNull
    public List<String> getUnsubscriptions() {

	return unsubscriptions;
    }

    public void setUnsubscriptions(List<String> unsubscriptions) {

	this.unsubscriptions = unsubscriptions;
    }

    @Override
    public boolean equals(java.lang.Object o) {

	if (this == o) {
	    return true;
	}
	if (o == null || getClass() != o.getClass()) {
	    return false;
	}
	SubscriptionsFeed subscriptionsFeed = (SubscriptionsFeed) o;
	return Objects.equals(this.dateUTC, subscriptionsFeed.dateUTC) && Objects.equals(this.subscriptions, subscriptionsFeed.subscriptions)
		&& Objects.equals(this.unsubscriptions, subscriptionsFeed.unsubscriptions);
    }

    @Override
    public int hashCode() {

	return Objects.hash(dateUTC, subscriptions, unsubscriptions);
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class SubscriptionsFeed {\n");
	sb.append("    dateUTC: ").append(toIndentedString(dateUTC)).append("\n");
	sb.append("    subscriptions: ").append(toIndentedString(subscriptions)).append("\n");
	sb.append("    unsubscriptions: ").append(toIndentedString(unsubscriptions)).append("\n");
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
