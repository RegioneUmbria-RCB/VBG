package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import javax.xml.bind.annotation.XmlElement;

public class NotificaEsitiRegistrazioneTypeElenco {

    /**
     * Identificativo della posizione debitoria, univoco nell'ambito di un servizio di pagamento attivo presso un Ente
     * Creditore identificativo_posizione_debitoria:
     **/
    private String idDebito = null;
    private StatoPagamentoPosizioneDebitoriaType statoPagamentoPosizioneDebitoria = null;
    private Problem problem = null;

    /**
     * Identificativo della posizione debitoria, univoco nell&#x27;ambito di un servizio di pagamento attivo presso un
     * Ente Creditore identificativo_posizione_debitoria:
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

    public NotificaEsitiRegistrazioneTypeElenco idDebito(String idDebito) {

	this.idDebito = idDebito;
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

    public NotificaEsitiRegistrazioneTypeElenco statoPagamentoPosizioneDebitoria(
	    StatoPagamentoPosizioneDebitoriaType statoPagamentoPosizioneDebitoria) {

	this.statoPagamentoPosizioneDebitoria = statoPagamentoPosizioneDebitoria;
	return this;
    }

    /**
     * Get problem
     * 
     * @return problem
     **/
    @XmlElement(name = "problem")
    public Problem getProblem() {

	return problem;
    }

    public void setProblem(Problem problem) {

	this.problem = problem;
    }

    public NotificaEsitiRegistrazioneTypeElenco problem(Problem problem) {

	this.problem = problem;
	return this;
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class NotificaEsitiRegistrazioneTypeElenco {\n");
	sb.append("    idDebito: ").append(toIndentedString(idDebito)).append("\n");
	sb.append("    statoPagamentoPosizioneDebitoria: ").append(toIndentedString(statoPagamentoPosizioneDebitoria)).append("\n");
	sb.append("    problem: ").append(toIndentedString(problem)).append("\n");
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
