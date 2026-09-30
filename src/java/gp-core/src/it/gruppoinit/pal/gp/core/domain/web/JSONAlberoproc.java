package it.gruppoinit.pal.gp.core.domain.web;

import java.util.ArrayList;
import java.util.List;

public class JSONAlberoproc {

    private String identifier;
    private String label;
    private List<AlberoprocCommand> items = new ArrayList<AlberoprocCommand>();

    public String getIdentifier() {

	return identifier;
    }

    public void setIdentifier(String identifier) {

	this.identifier = identifier;
    }

    public String getLabel() {

	return label;
    }

    public void setLabel(String label) {

	this.label = label;
    }

    public List<AlberoprocCommand> getItems() {

	return items;
    }

    public void setItems(List<AlberoprocCommand> items) {

	this.items = items;
    }
}
