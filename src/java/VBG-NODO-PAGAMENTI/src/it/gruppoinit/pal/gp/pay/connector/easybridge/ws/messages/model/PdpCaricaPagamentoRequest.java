package it.gruppoinit.pal.gp.pay.connector.easybridge.ws.messages.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "pdpCaricaPagamento", namespace = "http://easybridge.eu/bridge/")
public class PdpCaricaPagamentoRequest {

    @XmlElement(name = "datiPagamentoInAttesa")
    private DatiPagamentoInAttesa datiPagamentoInAttesa;

    public DatiPagamentoInAttesa getDatiPagamentoInAttesa() {

	return datiPagamentoInAttesa;
    }

    public void setDatiPagamentoInAttesa(DatiPagamentoInAttesa datiPagamentoInAttesa) {

	this.datiPagamentoInAttesa = datiPagamentoInAttesa;
    }
}
