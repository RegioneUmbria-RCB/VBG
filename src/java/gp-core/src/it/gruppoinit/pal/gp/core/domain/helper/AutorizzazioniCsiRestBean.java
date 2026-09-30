package it.gruppoinit.pal.gp.core.domain.helper;

import java.util.Date;

public class AutorizzazioniCsiRestBean {

    private String numeroAutorizzazione;
    private Integer codiceGerente;
    private String nomeGerente;
    private String emailGerente;
    private String statoAutorizzazione;
    private String statoWarning;
    private Date dataSospDa;
    private Date dataSospA;
    private Date dataFineGerenza;
    private String causaleSospensione;
    private Boolean validaSpunta;

    //    private Date dataInizioGerenza;
    public String getNumeroAutorizzazione() {

	return numeroAutorizzazione;
    }

    public void setNumeroAutorizzazione(String numeroAutorizzazione) {

	this.numeroAutorizzazione = numeroAutorizzazione;
    }

    public Integer getCodiceGerente() {

	return codiceGerente;
    }

    public void setCodiceGerente(Integer codiceGerente) {

	this.codiceGerente = codiceGerente;
    }

    public String getNomeGerente() {

	return nomeGerente;
    }

    public void setNomeGerente(String nomeGerente) {

	this.nomeGerente = nomeGerente;
    }

    public String getEmailGerente() {

	return emailGerente;
    }

    public void setEmailGerente(String emailGerente) {

	this.emailGerente = emailGerente;
    }

    public String getStatoAutorizzazione() {

	return statoAutorizzazione;
    }

    public void setStatoAutorizzazione(String statoAutorizzazione) {

	this.statoAutorizzazione = statoAutorizzazione;
    }

    public String getStatoWarning() {

	return statoWarning;
    }

    public void setStatoWarning(String statoWarning) {

	this.statoWarning = statoWarning;
    }

    public Date getDataSospDa() {

	return dataSospDa;
    }

    public void setDataSospDa(Date dataSospDa) {

	this.dataSospDa = dataSospDa;
    }

    public Date getDataSospA() {

	return dataSospA;
    }

    public void setDataSospA(Date dataSospA) {

	this.dataSospA = dataSospA;
    }

    public Date getDataFineGerenza() {

	return dataFineGerenza;
    }

    public void setDataFineGerenza(Date dataFineGerenza) {

	this.dataFineGerenza = dataFineGerenza;
    }

    public String getCausaleSospensione() {

	return causaleSospensione;
    }

    public void setCausaleSospensione(String causaleSospensione) {

	this.causaleSospensione = causaleSospensione;
    }

    public Boolean getValidaSpunta() {

	return validaSpunta;
    }

    public void setValidaSpunta(Boolean validaSpunta) {

	this.validaSpunta = validaSpunta;
    }
}
