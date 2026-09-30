package it.gruppoinit.pal.gp.core.domain.web;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class AnagrafeInterdettiCommand extends BaseCommand {

    private String nome;
    private String cognome;
    private Date dataNascita;
    private String comuneNascita;
    private String idComune;
    private String sesso;
    private Date dataInizioInterdizione;
    private Date dataFineInterdizione;
    private String codiceFiscale;
    //Il valore non è presente nelle file excel passato, ma permette di memorizzare quale sono le incongruenze 
    // che non permettono di aggiornare o inserire l'anagrafica come interdetta.
    private String erroriIncongruenze;
    private String nominativo;

    public String getNome() {

	return nome;
    }

    public void setNome(String nome) {

	this.nome = nome;
    }

    public String getCognome() {

	return cognome;
    }

    public void setCongnome(String cognome) {

	this.cognome = cognome;
    }

    public Date getDataNascita() {

	return dataNascita;
    }

    public void setDataNascita(Date dataNascita) {

	this.dataNascita = dataNascita;
    }

    public String getComuneNascita() {

	return comuneNascita;
    }

    public void setComuneNascita(String comuneNascita) {

	this.comuneNascita = comuneNascita;
    }

    public String getIdComune() {

	return idComune;
    }

    public void setIdComune(String idComune) {

	this.idComune = idComune;
    }

    public String getSesso() {

	return sesso;
    }

    public void setSesso(String sesso) {

	this.sesso = sesso;
    }

    public Date getDataInizioInterdizione() {

	return dataInizioInterdizione;
    }

    public void setDataInizioInterdizione(Date dataInizioInterdizione) {

	this.dataInizioInterdizione = dataInizioInterdizione;
    }

    public Date getDataFineInterdizione() {

	return dataFineInterdizione;
    }

    public void setDataFineInterdizione(Date dataFineInterdizione) {

	this.dataFineInterdizione = dataFineInterdizione;
    }

    public String getCodiceFiscale() {

	return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {

	this.codiceFiscale = codiceFiscale;
    }

    public String getErroriIncongruenze() {

	return erroriIncongruenze;
    }

    public void setErroriIncongruenze(String erroriIncongruenze) {

	this.erroriIncongruenze = erroriIncongruenze;
    }

    public String getNominativo() {

	StringBuffer answer = new StringBuffer();
	if (StringUtils.isNotBlank(getCognome())) {
	    answer.append(getCognome());
	}
	if (StringUtils.isNotBlank(getNome())) {
	    answer.append(" ").append(getNome());
	}
	if (StringUtils.isNotBlank(getCodiceFiscale()) && StringUtils.isNotBlank(getCodiceFiscale())) {
	    answer.append(" (").append(getCodiceFiscale()).append(")");
	}
	setNominativo(answer.toString());
	return nominativo;
    }

    public void setNominativo(String nominativo) {

	this.nominativo = nominativo;
    }
}
