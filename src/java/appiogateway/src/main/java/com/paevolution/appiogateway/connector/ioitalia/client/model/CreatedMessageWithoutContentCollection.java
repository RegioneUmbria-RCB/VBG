package com.paevolution.appiogateway.connector.ioitalia.client.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * A collection of messages
 */
@ApiModel(description = "A collection of messages")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-12-24T13:31:55.506+01:00[Europe/Berlin]")
public class CreatedMessageWithoutContentCollection {

    @JsonProperty("items")
    @Valid
    private List<CreatedMessageWithoutContent> items = new ArrayList<>();

    public CreatedMessageWithoutContentCollection items(List<CreatedMessageWithoutContent> items) {

	this.items = items;
	return this;
    }

    public CreatedMessageWithoutContentCollection addItemsItem(CreatedMessageWithoutContent itemsItem) {

	this.items.add(itemsItem);
	return this;
    }

    /**
     * Get items
     * 
     * @return items
     */
    @ApiModelProperty(required = true, value = "")
    @NotNull
    @Valid
    public List<CreatedMessageWithoutContent> getItems() {

	return items;
    }

    public void setItems(List<CreatedMessageWithoutContent> items) {

	this.items = items;
    }

    @Override
    public boolean equals(java.lang.Object o) {

	if (this == o) {
	    return true;
	}
	if (o == null || getClass() != o.getClass()) {
	    return false;
	}
	CreatedMessageWithoutContentCollection createdMessageWithoutContentCollection = (CreatedMessageWithoutContentCollection) o;
	return Objects.equals(this.items, createdMessageWithoutContentCollection.items);
    }

    @Override
    public int hashCode() {

	return Objects.hash(items);
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class CreatedMessageWithoutContentCollection {\n");
	sb.append("    items: ").append(toIndentedString(items)).append("\n");
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
