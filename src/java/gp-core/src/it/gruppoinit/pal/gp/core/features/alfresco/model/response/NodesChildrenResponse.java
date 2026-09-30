package it.gruppoinit.pal.gp.core.features.alfresco.model.response;

import it.gruppoinit.pal.gp.core.features.alfresco.model.dto.ListNodesChildren;

public class NodesChildrenResponse implements java.io.Serializable {

    private static final long serialVersionUID = 8343068571909193631L;
    private ListNodesChildren list;
    private int code;
    private String description;

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

    public ListNodesChildren getList() {

	return list;
    }

    public void setList(ListNodesChildren list) {

	this.list = list;
    }
}
