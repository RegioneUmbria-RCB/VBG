package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import javax.xml.bind.annotation.XmlElement;

public class NotificaEsitiPagamentoTypeElenco {

    /**
     * Identificativo della posizione debitoria, univoco nell'ambito di un servizio di pagamento attivo presso un Ente
     * Creditore
     **/
    private String idDebito = null;
    private String iuv = null;
    private StatoPagamentoPosizioneDebitoriaType statoPagamentoPosizioneDebitoria = null;

    /**
     * Identificativo della posizione debitoria, univoco nell&#x27;ambito di un servizio di pagamento attivo presso un
     * Ente Creditore
     * 
     * @return idDebito
     **/
    @XmlElement(name = "id_debito")
    public String getIdDebito() {

	return idDebito;
    }

    public void setIdDebito(String idDebito) {

	this.idDebito = idDebito;
    }

    public NotificaEsitiPagamentoTypeElenco idDebito(String idDebito) {

	this.idDebito = idDebito;
	return this;
    }

    /**
     * Get iuv
     * 
     * @return iuv
     **/
    @XmlElement(name = "iuv")
    public String getIuv() {

	return iuv;
    }

    public void setIuv(String iuv) {

	this.iuv = iuv;
    }

    public NotificaEsitiPagamentoTypeElenco iuv(String iuv) {

	this.iuv = iuv;
	return this;
    }

    /**
     * Get statoPagamentoPosizioneDebitoria
     * 
     * @return statoPagamentoPosizioneDebitoria
     **/
    @XmlElement(name = "stato_pagamento_posizione_debitoria")
    public StatoPagamentoPosizioneDebitoriaType getStatoPagamentoPosizioneDebitoria() {

	return statoPagamentoPosizioneDebitoria;
    }

    public void setStatoPagamentoPosizioneDebitoria(StatoPagamentoPosizioneDebitoriaType statoPagamentoPosizioneDebitoria) {

	this.statoPagamentoPosizioneDebitoria = statoPagamentoPosizioneDebitoria;
    }

    public NotificaEsitiPagamentoTypeElenco statoPagamentoPosizioneDebitoria(StatoPagamentoPosizioneDebitoriaType statoPagamentoPosizioneDebitoria) {

	this.statoPagamentoPosizioneDebitoria = statoPagamentoPosizioneDebitoria;
	return this;
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class NotificaEsitiPagamentoTypeElenco {\n");
	sb.append("    idDebito: ").append(toIndentedString(idDebito)).append("\n");
	sb.append("    iuv: ").append(toIndentedString(iuv)).append("\n");
	sb.append("    statoPagamentoPosizioneDebitoria: ").append(toIndentedString(statoPagamentoPosizioneDebitoria)).append("\n");
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
