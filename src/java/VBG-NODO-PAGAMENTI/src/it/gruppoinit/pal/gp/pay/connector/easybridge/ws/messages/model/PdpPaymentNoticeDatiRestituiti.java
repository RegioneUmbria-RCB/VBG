package it.gruppoinit.pal.gp.pay.connector.easybridge.ws.messages.model;

import javax.xml.bind.annotation.XmlElement;

public class PdpPaymentNoticeDatiRestituiti {

    @XmlElement(name = "avvisoDiPagamento", required = false)
    private String avvisoDiPagamento;

    public String getAvvisoDiPagamento() {

	return avvisoDiPagamento;
    }

    public void setAvvisoDiPagamento(String avvisoDiPagamento) {

	this.avvisoDiPagamento = avvisoDiPagamento;
    }
}
