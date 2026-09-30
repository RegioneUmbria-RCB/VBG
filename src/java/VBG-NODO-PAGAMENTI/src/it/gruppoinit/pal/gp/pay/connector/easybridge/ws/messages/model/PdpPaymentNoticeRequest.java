package it.gruppoinit.pal.gp.pay.connector.easybridge.ws.messages.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "pdpPaymentNotice", namespace = "http://easybridge.eu/bridge/")
public class PdpPaymentNoticeRequest {

    @XmlElement(name = "identificativoUnivocoVersamento", required = true)
    private String identificativoUnivocoVersamento;
    @XmlElement(name = "returnNoticePDF", required = false)
    private String returnNoticePDF;

    public String getIdentificativoUnivocoVersamento() {

	return identificativoUnivocoVersamento;
    }

    public void setIdentificativoUnivocoVersamento(String identificativoUnivocoVersamento) {

	this.identificativoUnivocoVersamento = identificativoUnivocoVersamento;
    }

    public String getReturnNoticePDF() {

	return returnNoticePDF;
    }

    public void setReturnNoticePDF(String returnNoticePDF) {

	this.returnNoticePDF = returnNoticePDF;
    }
}
