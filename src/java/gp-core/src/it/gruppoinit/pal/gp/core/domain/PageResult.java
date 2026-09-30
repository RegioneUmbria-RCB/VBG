package it.gruppoinit.pal.gp.core.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(BollettazioneBean.class)
public class PageResult<T> {

    @XmlAnyElement
    private List<T> items;
    private long countElements;
    private long totalCount;
    private int pageNumber;
    private int pageSize;
    private int totalPages;

    public PageResult() {

    }

    public PageResult(List<T> items, long countElements, int pageNumber, int pageSize, long totalCount) {

	this.items = items;
	this.totalCount = totalCount;
	this.pageNumber = pageNumber;
	this.pageSize = pageSize;
	this.totalPages = (int) Math.ceil((double) totalCount / pageSize);
	this.countElements = countElements;
    }

    // Getters e Setters
    public List<T> getItems() {

	return items;
    }

    public void setItems(List<T> items) {

	this.items = items;
    }

    public long getTotalCount() {

	return totalCount;
    }

    public void setTotalCount(long totalCount) {

	this.totalCount = totalCount;
    }

    public int getPageNumber() {

	return pageNumber;
    }

    public void setPageNumber(int pageNumber) {

	this.pageNumber = pageNumber;
    }

    public int getPageSize() {

	return pageSize;
    }

    public void setPageSize(int pageSize) {

	this.pageSize = pageSize;
    }

    public int getTotalPages() {

	return totalPages;
    }

    public void setTotalPages(int totalPages) {

	this.totalPages = totalPages;
    }

    public long getCountElements() {

	return countElements;
    }

    public void setCountElements(long countElements) {

	this.countElements = countElements;
    }

    @Override
    public String toString() {

	StringBuilder builder = new StringBuilder();
	builder.append("PageResult [items=");
	builder.append(items);
	builder.append(", countElements=");
	builder.append(countElements);
	builder.append(", totalCount=");
	builder.append(totalCount);
	builder.append(", pageNumber=");
	builder.append(pageNumber);
	builder.append(", pageSize=");
	builder.append(pageSize);
	builder.append(", totalPages=");
	builder.append(totalPages);
	builder.append("]");
	return builder.toString();
    }
}
