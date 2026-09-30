package it.gruppoinit.pal.gp.core.features.alfresco.model.dto;

import javax.xml.bind.annotation.XmlElement;

public class UserInfo implements java.io.Serializable {

    private static final long serialVersionUID = -4191039234626019028L;
    @XmlElement
    private String displayName;
    @XmlElement
    private String id;

    public String getDisplayName() {

	return displayName;
    }

    public void setDisplayName(String displayName) {

	this.displayName = displayName;
    }

    public String getId() {

	return id;
    }

    public void setId(String id) {

	this.id = id;
    }
}
