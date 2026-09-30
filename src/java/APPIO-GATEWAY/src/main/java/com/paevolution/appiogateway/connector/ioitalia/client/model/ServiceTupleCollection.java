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
 * A collection of services tuples (service and version)
 */
@ApiModel(description = "A collection of services tuples (service and version)")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-12-24T13:31:55.506+01:00[Europe/Berlin]")
public class ServiceTupleCollection {

    @JsonProperty("items")
    @Valid
    private List<ServiceTuple> items = new ArrayList<>();

    public ServiceTupleCollection items(List<ServiceTuple> items) {

	this.items = items;
	return this;
    }

    public ServiceTupleCollection addItemsItem(ServiceTuple itemsItem) {

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
    public List<ServiceTuple> getItems() {

	return items;
    }

    public void setItems(List<ServiceTuple> items) {

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
	ServiceTupleCollection serviceTupleCollection = (ServiceTupleCollection) o;
	return Objects.equals(this.items, serviceTupleCollection.items);
    }

    @Override
    public int hashCode() {

	return Objects.hash(items);
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class ServiceTupleCollection {\n");
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
