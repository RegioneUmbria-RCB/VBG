package it.gruppoinit.pal.gp.pay.connector.openweb.model.esito;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "schema", //
	"fragment", //
	"message", //
	"failedAttribute" //
})
public class Errore {

    @XmlElement(name = "schema")
    private EsitoSchema schema;
    @XmlElement
    private String fragment;
    @XmlElement
    private String message;
    @XmlElement(name = "failed_attribute")
    private String failedAttribute;

    public EsitoSchema getSchema() {

	return schema;
    }

    public void setSchema(EsitoSchema schema) {

	this.schema = schema;
    }

    public String getFragment() {

	return fragment;
    }

    public void setFragment(String fragment) {

	this.fragment = fragment;
    }

    public String getMessage() {

	return message;
    }

    public void setMessage(String message) {

	this.message = message;
    }

    public String getFailedAttribute() {

	return failedAttribute;
    }

    public void setFailedAttribute(String failedAttribute) {

	this.failedAttribute = failedAttribute;
    }

    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
