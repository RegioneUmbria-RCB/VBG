package it.gruppoinit.pal.gp.core.features.alfresco.model.response;

import it.gruppoinit.pal.gp.core.features.alfresco.model.dto.Entry;

public class NodeResponse implements java.io.Serializable {

    private static final long serialVersionUID = -9177674960511058543L;
    private Entry entry;
    private int code;
    private String description;

    public Entry getEntry() {

	return entry;
    }

    public void setEntry(Entry entry) {

	this.entry = entry;
    }

    public int getCode() {

	return code;
    }

    public void setCode(int code) {

	this.code = code;
    }

    public String getDescription() {

	return description;
    }

    public void setDescription(String description) {

	this.description = description;
    }

    public NodeResponse(int code, String description, Entry entry) {

	this.code = code;
	this.description = description;
	this.entry = entry;
    }

    public NodeResponse() {

    }
}
