package it.gruppoinit.pal.gp.pay.connector.piemontepay.rest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@XmlRootElement
public class GetPaymentNoticeResponse {

    @XmlElement(name = "paymentnotice")
    private String paymentnotice;
    @XmlElement(name = "result")
    private RestAPIResult result;

    public String getPaymentnotice() {

	return paymentnotice;
    }

    public void setPaymentnotice(String paymentnotice) {

	this.paymentnotice = paymentnotice;
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
