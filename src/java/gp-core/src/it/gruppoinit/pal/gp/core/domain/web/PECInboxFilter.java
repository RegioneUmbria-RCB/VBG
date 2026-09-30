package it.gruppoinit.pal.gp.core.domain.web;

import java.util.Date;

/**
 * Oggetto che memorizza le impostazioni filtro da utilizzare per interrogare la tabella PEC_INBOX. I filtri possono
 * essere fati per : - data ricezione da (arrotondata alle 0:00:00) a (arrotondata alle 23:59:59) - letti/non
 * letti/tutti - elaborati/non elaborati/tutti (sono elaborati se FLAG_PROCESSATO = true) - lavorati/non lavorati/tutti
 * (sono lavorate le PEC per cui esiste un'istanza, un movimento o un protocollo) - escludere le ricevute o no - oggetto
 * (per contiene case insensitive) - numero protocollo - mittente (per contiene case insensitive) L'oggetto serve anche
 * per specificare in quale account devono essere ricercati i messaggi PEC, se vuoto vengono presi tutti
 * indipendentemente dall'account
 * 
 * @author francol
 *
 */
public class PECInboxFilter {

    public enum FiltroPECSiNoTuttiEnum {
	SI, NO, TUTTI;

	public String value() {

	    return name();
	}

	public static FiltroPECSiNoTuttiEnum fromValue(String v) {

	    try {
		return valueOf(v);
	    } catch (IllegalArgumentException e) {
		// se il valore passato non è valido allora rtestituisco il valore di default TUTTI
		return FiltroPECSiNoTuttiEnum.TUTTI;
	    }
	}
    }

    private Date dataRicezioneDa;
    private Date dataRicezioneA;
    private FiltroPECSiNoTuttiEnum lettaNonLetta = FiltroPECSiNoTuttiEnum.TUTTI;
    private FiltroPECSiNoTuttiEnum elaborati = FiltroPECSiNoTuttiEnum.TUTTI;
    private FiltroPECSiNoTuttiEnum lavorati = FiltroPECSiNoTuttiEnum.TUTTI;
    private Boolean flagRicevute = Boolean.FALSE;
    private String oggetto;
    private String numeroProtocollo;
    private String mittente;
    private String jMesaParamString = "";
    private String mailAccount;
    private String descMailConfigDefault;
    private String destinatarioCC;
    private String destinatario;
    private Integer idMailConfig;

    public Boolean getFlagLetti() {

	return this.lettaNonLetta.equals(FiltroPECSiNoTuttiEnum.SI) || this.lettaNonLetta.equals(FiltroPECSiNoTuttiEnum.TUTTI);
    }

    public Boolean getFlagNonLetti() {

	return this.lettaNonLetta.equals(FiltroPECSiNoTuttiEnum.NO) || this.lettaNonLetta.equals(FiltroPECSiNoTuttiEnum.TUTTI);
    }

    public Boolean getFlagElaborati() {

	return this.elaborati.equals(FiltroPECSiNoTuttiEnum.SI) || this.elaborati.equals(FiltroPECSiNoTuttiEnum.TUTTI);
    }

    public Boolean getFlagNonElaborati() {

	return this.elaborati.equals(FiltroPECSiNoTuttiEnum.NO) || this.elaborati.equals(FiltroPECSiNoTuttiEnum.TUTTI);
    }

    public Boolean getFlagLavorati() {

	return this.lavorati.equals(FiltroPECSiNoTuttiEnum.SI) || this.lavorati.equals(FiltroPECSiNoTuttiEnum.TUTTI);
    }

    public Boolean getFlagNonLavorati() {

	return this.lavorati.equals(FiltroPECSiNoTuttiEnum.NO) || this.lavorati.equals(FiltroPECSiNoTuttiEnum.TUTTI);
    }

    public Boolean getFlagRicevute() {

	return flagRicevute;
    }

    public void setFlagRicevute(Boolean flagRicevute) {

	this.flagRicevute = flagRicevute;
    }

    public Date getDataRicezioneDa() {

	return dataRicezioneDa;
    }

    public void setDataRicezioneDa(Date dataRicezioneDa) {

	this.dataRicezioneDa = dataRicezioneDa;
    }

    public Date getDataRicezioneA() {

	return dataRicezioneA;
    }

    public void setDataRicezioneA(Date dataRicezioneA) {

	this.dataRicezioneA = dataRicezioneA;
    }

    public FiltroPECSiNoTuttiEnum getLettiNonLetti() {

	return lettaNonLetta;
    }

    public void setLettiNonLetti(FiltroPECSiNoTuttiEnum lettaNonLetta) {

	this.lettaNonLetta = lettaNonLetta;
    }

    public String getLettiNonLettiString() {

	return lettaNonLetta.value();
    }

    public void setLettiNonLettiString(String lettaNonLettaString) {

	FiltroPECSiNoTuttiEnum newVal = null;
	try {
	    newVal = FiltroPECSiNoTuttiEnum.fromValue(lettaNonLettaString);
	} catch (IllegalArgumentException e) {
	    // se il valore passato non è valido allora rtestituisco il valore di default TUTTI
	    newVal = FiltroPECSiNoTuttiEnum.TUTTI;
	}
	this.lettaNonLetta = newVal;
    }

    public FiltroPECSiNoTuttiEnum getElaborati() {

	return elaborati;
    }

    public void setElaborati(FiltroPECSiNoTuttiEnum elaborati) {

	this.elaborati = elaborati;
    }

    public String getElaboratiString() {

	return elaborati.value();
    }

    public void setElaboratiString(String elaboratiString) {

	FiltroPECSiNoTuttiEnum newVal = null;
	try {
	    newVal = FiltroPECSiNoTuttiEnum.fromValue(elaboratiString);
	} catch (IllegalArgumentException e) {
	    // se il valore passato non è valido allora rtestituisco il valore di default TUTTI
	    newVal = FiltroPECSiNoTuttiEnum.TUTTI;
	}
	this.elaborati = newVal;
    }

    public FiltroPECSiNoTuttiEnum getLavorati() {

	return lavorati;
    }

    public void setLavorati(FiltroPECSiNoTuttiEnum lavorati) {

	this.lavorati = lavorati;
    }

    public String getLavoratiString() {

	return lavorati.value();
    }

    public void setLavoratiString(String lavoratiString) {

	FiltroPECSiNoTuttiEnum newVal = null;
	try {
	    newVal = FiltroPECSiNoTuttiEnum.fromValue(lavoratiString);
	} catch (IllegalArgumentException e) {
	    // se il valore passato non è valido allora rtestituisco il valore di default TUTTI
	    newVal = FiltroPECSiNoTuttiEnum.TUTTI;
	}
	this.lavorati = newVal;
    }

    public String getOggetto() {

	return oggetto;
    }

    public void setOggetto(String oggetto) {

	this.oggetto = oggetto;
    }

    public String getNumeroProtocollo() {

	return numeroProtocollo;
    }

    public void setNumeroProtocollo(String numeroProtocollo) {

	this.numeroProtocollo = numeroProtocollo;
    }

    public String getMittente() {

	return mittente;
    }

    public void setMittente(String mittente) {

	this.mittente = mittente;
    }

    public String getjMesaParamString() {

	return jMesaParamString;
    }

    public void setjMesaParamString(String jMesaParamString) {

	this.jMesaParamString = jMesaParamString;
    }

    public String getMailAccount() {

	return mailAccount;
    }

    public void setMailAccount(String mailAccount) {

	this.mailAccount = mailAccount;
    }

    public String getDescMailConfigDefault() {

	return descMailConfigDefault;
    }

    public void setDescMailConfigDefault(String descMailConfigDefault) {

	this.descMailConfigDefault = descMailConfigDefault;
    }

    public String getDestinatarioCC() {

	return destinatarioCC;
    }

    public void setDestinatarioCC(String destinatarioCC) {

	this.destinatarioCC = destinatarioCC;
    }

    public String getDestinatario() {

	return destinatario;
    }

    public void setDestinatario(String destinatario) {

	this.destinatario = destinatario;
    }

    public Integer getIdMailConfig() {

	return idMailConfig;
    }

    public void setIdMailConfig(Integer idMailConfig) {

	this.idMailConfig = idMailConfig;
    }
}
