package it.gruppoinit.pal.gp.pay.connector.openweb.model.esito;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "rowNumber", //
	"errors" })
public class Errori {

    @XmlElement(name = "row_number")
    private Integer rowNumber;
    @XmlElement(name = "errors")
    private List<Errore> errors;

    public Integer getRowNumber() {

	return rowNumber;
    }

    public void setRowNumber(Integer rowNumber) {

	this.rowNumber = rowNumber;
    }

    public List<Errore> getErrors() {

	if (this.errors == null) {
	    this.errors = new ArrayList<Errore>();
	}
	return errors;
    }

    public void setErrors(List<Errore> errors) {

	this.errors = errors;
    }

    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
