package it.gruppoinit.dss.ws.legacy;

import jakarta.activation.DataHandler;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlMimeType;

@XmlAccessorType(XmlAccessType.FIELD)
public class WSDocument {

    @XmlMimeType("application/octet-stream")
    private DataHandler binary;
    private String name;
    private String mimeType;

    public WSDocument() {}

    public DataHandler getBinary() { return binary; }
    public void setBinary(DataHandler binary) { this.binary = binary; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getMimeType() { return mimeType; }
    public void setMimeType(String mimeType) { this.mimeType = mimeType; }
}
