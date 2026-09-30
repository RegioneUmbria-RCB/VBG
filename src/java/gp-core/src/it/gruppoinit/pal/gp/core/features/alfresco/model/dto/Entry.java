package it.gruppoinit.pal.gp.core.features.alfresco.model.dto;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import it.gruppoinit.pal.gp.core.features.alfresco.utilities.AlfrescoApiPropertiesAdapter;

@XmlRootElement(name = "entry")
@XmlType(name = "Entry", propOrder = { "aspectNames", "createdAt", "isFolder", "isFile", "createdByUser", "modifiedAt", "modifiedByUser", "id",
	"name", "nodeType", "parentId", "properties", "content" })
@XmlSeeAlso({ UserInfo.class, ContentInfo.class, AlfrescoApiPropertiesAdapter.class })
public class Entry implements java.io.Serializable {

    private static final long serialVersionUID = 3827769807255627967L;
    @XmlElement(name = "aspectNames")
    private String[] aspectNames;
    @XmlElement(name = "createdAt")
    private String createdAt;
    @XmlElement(name = "isFolder")
    private boolean isFolder;
    @XmlElement(name = "isFile")
    private boolean isFile;
    @XmlElement(name = "createdByUser")
    private UserInfo createdByUser;
    @XmlElement(name = "modifiedAt")
    private String modifiedAt;
    @XmlElement(name = "modifiedByUser")
    private UserInfo modifiedByUser;
    @XmlElement(name = "id")
    private String id;
    @XmlElement(name = "name")
    private String name;
    @XmlElement(name = "nodeType")
    private String nodeType;
    @XmlElement(name = "parentId")
    private String parentId;
    @XmlJavaTypeAdapter(AlfrescoApiPropertiesAdapter.class)
    public Map<String, String> properties = new HashMap<String, String>();
    @XmlElement(name = "content")
    private ContentInfo content;

    public String[] getAspectNames() {

	return aspectNames;
    }

    public void setAspectNames(String[] aspectNames) {

	this.aspectNames = aspectNames;
    }

    public String getCreatedAt() {

	return createdAt;
    }

    public UserInfo getCreatedByUser() {

	return createdByUser;
    }

    public void setCreatedByUser(UserInfo createdByUser) {

	this.createdByUser = createdByUser;
    }

    public String getModifiedAt() {

	return modifiedAt;
    }

    public void setModifiedAt(String modifiedAt) {

	this.modifiedAt = modifiedAt;
    }

    public UserInfo getModifiedByUser() {

	return modifiedByUser;
    }

    public void setModifiedByUser(UserInfo modifiedByUser) {

	this.modifiedByUser = modifiedByUser;
    }

    public String getId() {

	return id;
    }

    public void setId(String id) {

	this.id = id;
    }

    public String getName() {

	return name;
    }

    public void setName(String name) {

	this.name = name;
    }

    public String getNodeType() {

	return nodeType;
    }

    public void setNodeType(String nodeType) {

	this.nodeType = nodeType;
    }

    public String getParentId() {

	return parentId;
    }

    public void setParentId(String parentId) {

	this.parentId = parentId;
    }

    public void setCreatedAt(String createdAt) {

	this.createdAt = createdAt;
    }

    public ContentInfo getContent() {

	return content;
    }

    public void setContent(ContentInfo content) {

	this.content = content;
    }

    public Map<String, String> getProperties() {

	return properties;
    }

    public void setProperties(Map<String, String> properties) {

	this.properties = properties;
    }

    public boolean isFolder() {

	return isFolder;
    }

    public void seteFolder(boolean isFolder) {

	this.isFolder = isFolder;
    }

    public boolean isFile() {

	return isFile;
    }

    public void seteFile(boolean isFile) {

	this.isFile = isFile;
    }
}
