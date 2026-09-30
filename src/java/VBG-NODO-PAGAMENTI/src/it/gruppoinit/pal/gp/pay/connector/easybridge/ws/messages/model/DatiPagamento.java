package it.gruppoinit.pal.gp.pay.connector.easybridge.ws.messages.model;

import javax.xml.bind.annotation.XmlElement;

public class DatiPagamento {

    @XmlElement(name = "tipoVersamento")
    private String tipoVersamento;
    @XmlElement(name = "parametriAggiuntvi")
    private String parametriAggiuntvi;
    @XmlElement(name = "causaleVersamentoEsplicitaPSP")
    private String causaleVersamentoEsplicitaPSP;
    @XmlElement(name = "datiSingoloPagamento", required = false)
    private DatiSingoloPagamento datiSingoloPagamento;

    public String getTipoVersamento() {

	return tipoVersamento;
    }

    public void setTipoVersamento(String tipoVersamento) {

	this.tipoVersamento = tipoVersamento;
    }

    public String getParametriAggiuntvi() {

	return parametriAggiuntvi;
    }

    public void setParametriAggiuntvi(String parametriAggiuntvi) {

	this.parametriAggiuntvi = parametriAggiuntvi;
    }

    public String getCausaleVersamentoEsplicitaPSP() {

	return causaleVersamentoEsplicitaPSP;
    }

    public void setCausaleVersamentoEsplicitaPSP(String causaleVersamentoEsplicitaPSP) {

	this.causaleVersamentoEsplicitaPSP = causaleVersamentoEsplicitaPSP;
    }

    public DatiSingoloPagamento getDatiSingoloPagamento() {

	return datiSingoloPagamento;
    }

    public void setDatiSingoloPagamento(DatiSingoloPagamento datiSingoloPagamento) {

	this.datiSingoloPagamento = datiSingoloPagamento;
    }
}
