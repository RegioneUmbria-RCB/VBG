package it.gruppoinit.pal.gp.core.domain.web;

import java.util.ArrayList;
import java.util.List;

public class JSONAteco {

    private String identifier;
    private String label;
    private List<AtecoCommand> items = new ArrayList<AtecoCommand>();

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

    public List<AtecoCommand> getItems() {

	return items;
    }

    public void setItems(List<AtecoCommand> items) {

	this.items = items;
    }
}
