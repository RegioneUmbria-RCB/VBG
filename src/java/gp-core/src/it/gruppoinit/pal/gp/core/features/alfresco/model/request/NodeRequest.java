package it.gruppoinit.pal.gp.core.features.alfresco.model.request;

import it.gruppoinit.pal.gp.core.features.alfresco.model.dto.Entry;

public class NodeRequest implements java.io.Serializable {

    private static final long serialVersionUID = -4344930099907460079L;
    private Entry entry;

    public Entry getEntry() {

	return entry;
    }

    public void setEntry(Entry entry) {

	this.entry = entry;
    }
}
