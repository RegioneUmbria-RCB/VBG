package it.gruppoinit.infocamere.schema.legaldocs;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "error")
public class Error {

    private String code;
    private String description;

    @XmlElement()
    public String getCode() {

	return code;
    }

    public void setCode(String code) {

	this.code = code;
    }

    @XmlElement()
    public String getDescription() {

	return description;
    }

    public void setDescription(String description) {

	this.description = description;
    }
}
