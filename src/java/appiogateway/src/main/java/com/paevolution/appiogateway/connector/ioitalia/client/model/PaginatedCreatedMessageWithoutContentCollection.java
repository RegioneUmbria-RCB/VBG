package com.paevolution.appiogateway.connector.ioitalia.client.model;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * A paginated collection of messages
 */
@ApiModel(description = "A paginated collection of messages")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-12-24T13:31:55.506+01:00[Europe/Berlin]")
public class PaginatedCreatedMessageWithoutContentCollection {

    @JsonProperty("items")
    @Valid
    private List<CreatedMessageWithoutContent> items = new ArrayList<>();
    @JsonProperty("page_size")
    private Integer pageSize;
    @JsonProperty("next")
    private URI next;

    public PaginatedCreatedMessageWithoutContentCollection items(List<CreatedMessageWithoutContent> items) {

	this.items = items;
	return this;
    }

    public PaginatedCreatedMessageWithoutContentCollection addItemsItem(CreatedMessageWithoutContent itemsItem) {

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

    public PaginatedCreatedMessageWithoutContentCollection pageSize(Integer pageSize) {

	this.pageSize = pageSize;
	return this;
    }

    /**
     * Number of items returned for each page. minimum: 1
     * 
     * @return pageSize
     */
    @ApiModelProperty(example = "2", value = "Number of items returned for each page.")
    @Min(1)
    public Integer getPageSize() {

	return pageSize;
    }

    public void setPageSize(Integer pageSize) {

	this.pageSize = pageSize;
    }

    public PaginatedCreatedMessageWithoutContentCollection next(URI next) {

	this.next = next;
	return this;
    }

    /**
     * Contains an URL to GET the next results page in the retrieved collection of items.
     * 
     * @return next
     */
    @ApiModelProperty(example = "https://example.com/?p=0XXX2", value = "Contains an URL to GET the next results page in the retrieved collection of items.")
    @Valid
    public URI getNext() {

	return next;
    }

    public void setNext(URI next) {

	this.next = next;
    }

    @Override
    public boolean equals(java.lang.Object o) {

	if (this == o) {
	    return true;
	}
	if (o == null || getClass() != o.getClass()) {
	    return false;
	}
	PaginatedCreatedMessageWithoutContentCollection paginatedCreatedMessageWithoutContentCollection = (PaginatedCreatedMessageWithoutContentCollection) o;
	return Objects.equals(this.items, paginatedCreatedMessageWithoutContentCollection.items)
		&& Objects.equals(this.pageSize, paginatedCreatedMessageWithoutContentCollection.pageSize)
		&& Objects.equals(this.next, paginatedCreatedMessageWithoutContentCollection.next);
    }

    @Override
    public int hashCode() {

	return Objects.hash(items, pageSize, next);
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class PaginatedCreatedMessageWithoutContentCollection {\n");
	sb.append("    items: ").append(toIndentedString(items)).append("\n");
	sb.append("    pageSize: ").append(toIndentedString(pageSize)).append("\n");
	sb.append("    next: ").append(toIndentedString(next)).append("\n");
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