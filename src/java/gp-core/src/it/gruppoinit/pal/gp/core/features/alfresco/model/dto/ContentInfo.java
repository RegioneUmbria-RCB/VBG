package it.gruppoinit.pal.gp.core.features.alfresco.model.dto;

import javax.xml.bind.annotation.XmlElement;

public class ContentInfo implements java.io.Serializable {

    private static final long serialVersionUID = -6028643983076957802L;
    @XmlElement
    private String mimeType;
    @XmlElement
    private String mimeTypeName;
    @XmlElement
    private Integer sizeInBytes;
    @XmlElement
    private String encoding; //optional

    public String getMimeType() {

	return mimeType;
    }

    public void setMimeType(String mimeType) {

	this.mimeType = mimeType;
    }

    public String getMimeTypeName() {

	return mimeTypeName;
    }

    public void setMimeTypeName(String mimeTypeName) {

	this.mimeTypeName = mimeTypeName;
    }

    public Integer getSizeInBytes() {

	return sizeInBytes;
    }

    public void setSizeInBytes(Integer sizeInBytes) {

	this.sizeInBytes = sizeInBytes;
    }

    public String getEncoding() {

	return encoding;
    }

    public void setEncoding(String encoding) {

	this.encoding = encoding;
    }
}
