package it.gruppoinit.pal.gp.pay.connector.easybridge.ws.messages.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "pdpCaricaPagamentoResult", namespace = "http://easybridge.eu/bridge/")
public class PdpCaricaPagamentoResult extends EsitoOperazione {

    public PdpCaricaPagamentoDatiRestituiti getDatiRestituiti() {

	return datiRestituiti;
    }

    public void setDatiRestituiti(PdpCaricaPagamentoDatiRestituiti datiRestituiti) {

	this.datiRestituiti = datiRestituiti;
    }

    @XmlElement(name = "datiRestituiti")
    private PdpCaricaPagamentoDatiRestituiti datiRestituiti;
}
