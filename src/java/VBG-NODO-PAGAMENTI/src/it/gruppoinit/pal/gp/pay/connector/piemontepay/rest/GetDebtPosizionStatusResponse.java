package it.gruppoinit.pal.gp.pay.connector.piemontepay.rest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@XmlRootElement
public class GetDebtPosizionStatusResponse {

    @XmlElement(name = "code")
    private String code;
    @XmlElement(name = "description")
    private String description;
    @XmlElement(name = "result")
    private RestAPIResult result;

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

    public RestAPIResult getResult() {

	return result;
    }

    public void setResult(RestAPIResult result) {

	this.result = result;
    }

    @XmlTransient
    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
