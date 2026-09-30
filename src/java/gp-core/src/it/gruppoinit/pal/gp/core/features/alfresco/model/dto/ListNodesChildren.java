package it.gruppoinit.pal.gp.core.features.alfresco.model.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement(name = "list")
@XmlSeeAlso({ Entry.class, Pagination.class })
public class ListNodesChildren implements java.io.Serializable {

    private static final long serialVersionUID = -4589999509592180729L;
    @XmlElement(name = "pagination")
    private Pagination pagination;
    @XmlElementWrapper(name = "entries")
    @XmlElement(name = "entry")
    private List<Entry> entries;

    public List<Entry> getEntries() {

	return entries;
    }

    public void setEntries(List<Entry> entries) {

	this.entries = entries;
    }

    public Pagination getPagination() {

	return pagination;
    }

    public void setPagination(Pagination pagination) {

	this.pagination = pagination;
    }
}
