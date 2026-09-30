package it.gruppoinit.pal.gp.pay.connector.payer.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "ResponseErr")
@XmlType(propOrder = { "error", "rid" })
@XmlAccessorType(XmlAccessType.FIELD)
public class ResponseErr {

    @XmlElement(name = "error")
    private String error;
    @XmlElement(name = "RID")
    private String rid;

    public String getError() {

	return error;
    }

    public void setError(String error) {

	this.error = error;
    }

    public String getRid() {

	return rid;
    }

    public void setRid(String rid) {

	this.rid = rid;
    }
}
