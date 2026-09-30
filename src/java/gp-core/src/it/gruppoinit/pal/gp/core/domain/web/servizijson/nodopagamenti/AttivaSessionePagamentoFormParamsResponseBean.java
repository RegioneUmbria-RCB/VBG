package it.gruppoinit.pal.gp.core.domain.web.servizijson.nodopagamenti;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

public class AttivaSessionePagamentoFormParamsResponseBean {

    @XmlElement(name = "paramName")
    private String paramName;
    @XmlElement(name = "value")
    private String value;

    @XmlTransient
    public String getParamName() {

	return paramName;
    }

    public void setParamName(String paramName) {

	this.paramName = paramName;
    }

    @XmlTransient
    public String getValue() {

	return value;
    }

    public void setValue(String value) {

	this.value = value;
    }
}
