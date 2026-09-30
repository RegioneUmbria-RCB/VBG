package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RichiestaPagamentoImmediatoType {

    private DettaglioRichiestaPagamentoImmediatoType dettaglioRichiestaPagamentoImmediato = null;
    private IdentificativoPosizioneDebitoriaType identificativoPosizioneDebitoria = null;
    private SoggettoPagatoreType soggettoPagatore = null;

    /**
     * Get dettaglioRichiestaPagamentoImmediato
     * 
     * @return dettaglioRichiestaPagamentoImmediato
     **/
    @XmlElement(name = "dettaglio_richiesta_pagamento_immediato")
    public DettaglioRichiestaPagamentoImmediatoType getDettaglioRichiestaPagamentoImmediato() {

	return dettaglioRichiestaPagamentoImmediato;
    }

    public void setDettaglioRichiestaPagamentoImmediato(DettaglioRichiestaPagamentoImmediatoType dettaglioRichiestaPagamentoImmediato) {

	this.dettaglioRichiestaPagamentoImmediato = dettaglioRichiestaPagamentoImmediato;
    }

    public RichiestaPagamentoImmediatoType dettaglioRichiestaPagamentoImmediato(
	    DettaglioRichiestaPagamentoImmediatoType dettaglioRichiestaPagamentoImmediato) {

	this.dettaglioRichiestaPagamentoImmediato = dettaglioRichiestaPagamentoImmediato;
	return this;
    }

    /**
     * Get identificativoPosizioneDebitoria
     * 
     * @return identificativoPosizioneDebitoria
     **/
    @XmlElement(name = "identificativo_posizione_debitoria")
    public IdentificativoPosizioneDebitoriaType getIdentificativoPosizioneDebitoria() {

	return identificativoPosizioneDebitoria;
    }

    public void setIdentificativoPosizioneDebitoria(IdentificativoPosizioneDebitoriaType identificativoPosizioneDebitoria) {

	this.identificativoPosizioneDebitoria = identificativoPosizioneDebitoria;
    }

    public RichiestaPagamentoImmediatoType identificativoPosizioneDebitoria(IdentificativoPosizioneDebitoriaType identificativoPosizioneDebitoria) {

	this.identificativoPosizioneDebitoria = identificativoPosizioneDebitoria;
	return this;
    }

    /**
     * Get soggettoPagatore
     * 
     * @return soggettoPagatore
     **/
    @XmlElement(name = "soggetto_pagatore")
    public SoggettoPagatoreType getSoggettoPagatore() {

	return soggettoPagatore;
    }

    public void setSoggettoPagatore(SoggettoPagatoreType soggettoPagatore) {

	this.soggettoPagatore = soggettoPagatore;
    }

    public RichiestaPagamentoImmediatoType soggettoPagatore(SoggettoPagatoreType soggettoPagatore) {

	this.soggettoPagatore = soggettoPagatore;
	return this;
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class RichiestaPagamentoImmediatoType {\n");
	sb.append("    dettaglioRichiestaPagamentoImmediato: ").append(toIndentedString(dettaglioRichiestaPagamentoImmediato)).append("\n");
	sb.append("    identificativoPosizioneDebitoria: ").append(toIndentedString(identificativoPosizioneDebitoria)).append("\n");
	sb.append("    soggettoPagatore: ").append(toIndentedString(soggettoPagatore)).append("\n");
	sb.append("}");
	return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces (except the first line).
     */
    private static String toIndentedString(java.lang.Object o) {

	if (o == null) {
	    return "null";
	}
	return o.toString().replace("\n", "\n    ");
    }
}
