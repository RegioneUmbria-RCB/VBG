package it.gruppoinit.pal.gp.core.features.alfresco.model.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "pagination")
public class Pagination implements java.io.Serializable {

    private static final long serialVersionUID = -2597994671205782418L;
    @XmlElement
    private int count;
    @XmlElement
    private boolean hasMoreItems;
    private int totalItems;
    @XmlElement
    private int skipCount;
    @XmlElement
    private int maxItems;

    public int getCount() {

	return count;
    }

    public void setCount(int count) {

	this.count = count;
    }

    public boolean isHasMoreItems() {

	return hasMoreItems;
    }

    public void setHasMoreItems(boolean hasMoreItems) {

	this.hasMoreItems = hasMoreItems;
    }

    public int getSkipCount() {

	return skipCount;
    }

    public void setSkipCount(int skipCount) {

	this.skipCount = skipCount;
    }

    public int getMaxItems() {

	return maxItems;
    }

    public void setMaxItems(int maxItems) {

	this.maxItems = maxItems;
    }

    public int getTotalItems() {

	return totalItems;
    }

    public void setTotalItems(int totalItems) {

	this.totalItems = totalItems;
    }
}
