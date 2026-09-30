package it.gruppoinit.pal.gp.core.features.alfresco.model.request;

import java.util.Map;

public class UploadNodeDocumentRequest implements java.io.Serializable {

    private static final long serialVersionUID = -5460617723038274032L;
    private String id;
    private byte[] filedata;
    private String name;
    private String nodeType;
    private String parentId;
    private boolean autoRename = false;
    Map<String, String> metadata;
    private boolean isFile = true;

    public String getId() {

	return id;
    }

    public void setId(String id) {

	this.id = id;
    }

    public String getParentId() {

	return parentId;
    }

    public void setParentId(String parentId) {

	this.parentId = parentId;
    }

    public byte[] getFiledata() {

	return filedata;
    }

    public void setFiledata(byte[] filedata) {

	this.filedata = filedata;
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

    public boolean isAutoRename() {

	return autoRename;
    }

    public void setAutoRename(boolean autoRename) {

	this.autoRename = autoRename;
    }

    public Map<String, String> getMetadata() {

	return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {

	this.metadata = metadata;
    }

    public boolean isFile() {

	return isFile;
    }

    public void setFile(boolean isFile) {

	this.isFile = isFile;
    }
}
