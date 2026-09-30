package it.gruppoinit.pal.gp.pay.connector.easybridge.ws.messages.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "pdpCancellaPagamento", namespace = "http://easybridge.eu/bridge/")
public class PdpCancellaPagamentoRequest {

    @XmlElement(name = "identificativoUnivocoVersamento", required = true)
    private String identificativoUnivocoVersamento;

    public String getIdentificativoUnivocoVersamento() {

	return identificativoUnivocoVersamento;
    }

    public void setIdentificativoUnivocoVersamento(String identificativoUnivocoVersamento) {

	this.identificativoUnivocoVersamento = identificativoUnivocoVersamento;
    }
}
