package it.gruppoinit.pal.gp.pay.connector.piemontepay.rest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@XmlRootElement
public class RestAPIResult {

    @XmlElement(name = "code")
    private String code;
    @XmlElement(name = "description")
    private String description;

    public String getCode() {

	return code;
    }

    public void setCode(String code) {

	this.code = code;
    }

    public String getDescription() {

	return description;
    }

    public void setDescription(String description) {

	this.description = description;
    }

    @XmlTransient
    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
