package it.gruppoinit.pal.gp.pay.connector.piemontepay.rest;

import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.StringUtils;

@XmlRootElement
public class PaymentReceipt {

    @XmlElement
    private String notice;
    @XmlElement
    private RestAPIResult result;

    public String getNotice() {

	return notice;
    }

    public void setNotice(String notice) {

	this.notice = notice;
    }

    public RestAPIResult getResult() {

	return result;
    }

    public void setResult(RestAPIResult result) {

	this.result = result;
    }

    @XmlTransient
    public byte[] ricevutaPDFToByte() {

	if (StringUtils.isBlank(this.getNotice())) {
	    return new byte[0];
	}
	return DatatypeConverter.parseBase64Binary(this.getNotice());
    }
}
