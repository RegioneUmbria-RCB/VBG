package it.gruppoinit.pal.gp.pay.connector.easybridge.ws.messages.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "pdpRecuperaRT", namespace = "http://easybridge.eu/bridge/")
public class PdpRecuperaRTRequest {

    @XmlElement(name = "identificativoUnivocoVersamento")
    private String identificativoUnivocoVersamento;
    @XmlElement(name = "codiceContestoPagamento")
    private String codiceContestoPagamento;

    public String getIdentificativoUnivocoVersamento() {

	return identificativoUnivocoVersamento;
    }

    public void setIdentificativoUnivocoVersamento(String identificativoUnivocoVersamento) {

	this.identificativoUnivocoVersamento = identificativoUnivocoVersamento;
    }

    public String getCodiceContestoPagamento() {

	return codiceContestoPagamento;
    }

    public void setCodiceContestoPagamento(String codiceContestoPagamento) {

	this.codiceContestoPagamento = codiceContestoPagamento;
    }
}
