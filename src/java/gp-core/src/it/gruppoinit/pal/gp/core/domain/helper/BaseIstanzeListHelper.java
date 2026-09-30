package it.gruppoinit.pal.gp.core.domain.helper;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

public class BaseIstanzeListHelper {

    protected String richiedentenominativo;
    protected String richiedentenome;
    protected String aziendanominativo;
    protected String aziendanome;
    protected String tiposoggetto;
    protected String descrizionesoggetto;
    protected String richstoriconominativo;
    protected String richstoriconome;
    protected String azstoriconominativo;
    protected String azstoriconome;

    public String getRichstoriconominativo() {

	return richstoriconominativo;
    }

    public void setRichstoriconominativo(String richstoriconominativo) {

	this.richstoriconominativo = richstoriconominativo;
    }

    public String getRichstoriconome() {

	return richstoriconome;
    }

    public void setRichstoriconome(String richstoriconome) {

	this.richstoriconome = richstoriconome;
    }

    public String getAzstoriconominativo() {

	return azstoriconominativo;
    }

    public void setAzstoriconominativo(String azstoriconominativo) {

	this.azstoriconominativo = azstoriconominativo;
    }

    public String getAzstoriconome() {

	return azstoriconome;
    }

    public void setAzstoriconome(String azstoriconome) {

	this.azstoriconome = azstoriconome;
    }

    private BigDecimal codicestradarioprimario;
    private String codiceviarioprimario;
    private String stradarioprefisso;
    private String stradariodescrizione;
    private String stradariocivico;
    private String stradariocap;
    private String stradarioesponente;
    private String stradariocolore;
    private String stradariolocalitafrazione;
    private String stradarioscala;
    private String stradariointerno;
    private String stradarioespinterno;
    private String stradariopiano;
    private String stradarioquartiere;
    private String stradariokm;
    private String transientDescrizioneRichiedenteQualitaAzienda;
    private String transientDescrizioneLocalizzazione;
    private String transientDescrizioneRichiedenteAziendaStorico;

    public String getRichiedentenominativo() {

	return richiedentenominativo;
    }

    public void setRichiedentenominativo(String richiedentenominativo) {

	this.richiedentenominativo = richiedentenominativo;
    }

    public String getRichiedentenome() {

	return richiedentenome;
    }

    public void setRichiedentenome(String richiedentenome) {

	this.richiedentenome = richiedentenome;
    }

    public String getAziendanominativo() {

	return aziendanominativo;
    }

    public void setAziendanominativo(String aziendanominativo) {

	this.aziendanominativo = aziendanominativo;
    }

    public String getAziendanome() {

	return aziendanome;
    }

    public void setAziendanome(String aziendanome) {

	this.aziendanome = aziendanome;
    }

    public String getTiposoggetto() {

	return tiposoggetto;
    }

    public void setTiposoggetto(String tiposoggetto) {

	this.tiposoggetto = tiposoggetto;
    }

    public String getDescrizionesoggetto() {

	return descrizionesoggetto;
    }

    public void setDescrizionesoggetto(String descrizionesoggetto) {

	this.descrizionesoggetto = descrizionesoggetto;
    }

    public BigDecimal getCodicestradarioprimario() {

	return codicestradarioprimario;
    }

    public void setCodicestradarioprimario(BigDecimal codicestradarioprimario) {

	this.codicestradarioprimario = codicestradarioprimario;
    }

    public String getCodiceviarioprimario() {

	return codiceviarioprimario;
    }

    public void setCodiceviarioprimario(String codiceviarioprimario) {

	this.codiceviarioprimario = codiceviarioprimario;
    }

    public String getStradarioprefisso() {

	return stradarioprefisso;
    }

    public void setStradarioprefisso(String stradarioprefisso) {

	this.stradarioprefisso = stradarioprefisso;
    }

    public String getStradariodescrizione() {

	return stradariodescrizione;
    }

    public void setStradariodescrizione(String stradariodescrizione) {

	this.stradariodescrizione = stradariodescrizione;
    }

    public String getStradariocivico() {

	return stradariocivico;
    }

    public void setStradariocivico(String stradariocivico) {

	this.stradariocivico = stradariocivico;
    }

    public String getStradariocap() {

	return stradariocap;
    }

    public void setStradariocap(String stradariocap) {

	this.stradariocap = stradariocap;
    }

    public String getStradarioesponente() {

	return stradarioesponente;
    }

    public void setStradarioesponente(String stradarioesponente) {

	this.stradarioesponente = stradarioesponente;
    }

    public String getStradariocolore() {

	return stradariocolore;
    }

    public void setStradariocolore(String stradariocolore) {

	this.stradariocolore = stradariocolore;
    }

    public String getStradariolocalitafrazione() {

	return stradariolocalitafrazione;
    }

    public void setStradariolocalitafrazione(String stradariolocalitafrazione) {

	this.stradariolocalitafrazione = stradariolocalitafrazione;
    }

    public String getStradarioscala() {

	return stradarioscala;
    }

    public void setStradarioscala(String stradarioscala) {

	this.stradarioscala = stradarioscala;
    }

    public String getStradariointerno() {

	return stradariointerno;
    }

    public void setStradariointerno(String stradariointerno) {

	this.stradariointerno = stradariointerno;
    }

    public String getStradarioespinterno() {

	return stradarioespinterno;
    }

    public void setStradarioespinterno(String stradarioespinterno) {

	this.stradarioespinterno = stradarioespinterno;
    }

    public String getStradariopiano() {

	return stradariopiano;
    }

    public void setStradariopiano(String stradariopiano) {

	this.stradariopiano = stradariopiano;
    }

    public String getStradarioquartiere() {

	return stradarioquartiere;
    }

    public void setStradarioquartiere(String stradarioquartiere) {

	this.stradarioquartiere = stradarioquartiere;
    }

    public String getStradariokm() {

	return stradariokm;
    }

    public void setStradariokm(String stradariokm) {

	this.stradariokm = stradariokm;
    }

    public String getTransientDescrizioneRichiedenteQualitaAzienda() {

	this.transientDescrizioneRichiedenteQualitaAzienda = getRichiedentenominativo();
	if (StringUtils.isNotBlank(getRichiedentenome())) {
	    this.transientDescrizioneRichiedenteQualitaAzienda += " " + getRichiedentenome();
	}
	if (StringUtils.isNotBlank(getTiposoggetto())) {
	    if (StringUtils.isNotBlank(getDescrizionesoggetto())) {
		this.transientDescrizioneRichiedenteQualitaAzienda += " " + getDescrizionesoggetto();
	    } else {
		this.transientDescrizioneRichiedenteQualitaAzienda += " " + getTiposoggetto();
	    }
	}
	if (StringUtils.isNotBlank(getAziendanominativo())) {
	    this.transientDescrizioneRichiedenteQualitaAzienda += " " + getAziendanominativo();
	}
	return StringUtils.defaultString(transientDescrizioneRichiedenteQualitaAzienda).toUpperCase();
    }

    /**
     * @return the transientDescrizioneLocalizzazione
     */
    public String getTransientDescrizioneLocalizzazione() {

	this.transientDescrizioneLocalizzazione = StringUtils.defaultIfEmpty(getStradarioprefisso(), "");
	if (StringUtils.isNotBlank(getStradariodescrizione())) {
	    this.transientDescrizioneLocalizzazione += " " + getStradariodescrizione();
	}
	if (StringUtils.isNotBlank(getStradariocivico())) {
	    this.transientDescrizioneLocalizzazione += " " + getStradariocivico();
	}
	if (StringUtils.isNotBlank(getStradarioesponente())) {
	    this.transientDescrizioneLocalizzazione += "/" + getStradarioesponente();
	}
	if (StringUtils.isNotBlank(getStradariocolore())) {
	    this.transientDescrizioneLocalizzazione += " " + getStradariocolore();
	}
	///////
	if (StringUtils.isNotBlank(getStradarioscala())) {
	    this.transientDescrizioneLocalizzazione += " " + getStradarioscala();
	}
	if (StringUtils.isNotBlank(getStradariopiano())) {
	    this.transientDescrizioneLocalizzazione += " " + getStradariopiano();
	}
	if (StringUtils.isNotBlank(getStradariointerno())) {
	    this.transientDescrizioneLocalizzazione += " " + getStradariointerno();
	}
	if (StringUtils.isNotBlank(getStradarioespinterno())) {
	    this.transientDescrizioneLocalizzazione += "/" + getStradarioespinterno();
	}
	///////
	if (StringUtils.isNotBlank(getStradariocap())) {
	    this.transientDescrizioneLocalizzazione += " - " + getStradariocap();
	}
	if (StringUtils.isNotBlank(getStradariolocalitafrazione())) {
	    this.transientDescrizioneLocalizzazione += " - " + getStradariolocalitafrazione();
	}
	if (StringUtils.isNotBlank(getStradarioquartiere())) {
	    this.transientDescrizioneLocalizzazione += " (" + getStradarioquartiere() + ")";
	}
	if (StringUtils.isNotBlank(getStradariokm())) {
	    this.transientDescrizioneLocalizzazione += ", km: " + getStradariokm();
	}
	return transientDescrizioneLocalizzazione;
    }

    public String getTransientDescrizioneRichiedenteAziendaStorico() {

	this.transientDescrizioneRichiedenteAziendaStorico = getRichstoriconominativo();
	if (StringUtils.isNotBlank(getRichstoriconome())) {
	    this.transientDescrizioneRichiedenteAziendaStorico += " " + getRichstoriconome();
	}
	if (StringUtils.isNotBlank(getTiposoggetto())) {
	    if (StringUtils.isNotBlank(getDescrizionesoggetto())) {
		this.transientDescrizioneRichiedenteAziendaStorico += " " + getDescrizionesoggetto();
	    } else {
		this.transientDescrizioneRichiedenteAziendaStorico += " " + getTiposoggetto();
	    }
	}
	if (StringUtils.isNotBlank(getAzstoriconominativo())) {
	    this.transientDescrizioneRichiedenteAziendaStorico += " " + getAzstoriconominativo();
	}
	if (StringUtils.isNotBlank(getAzstoriconome())) {
	    this.transientDescrizioneRichiedenteAziendaStorico += " " + getAzstoriconome();
	}
	return StringUtils.defaultString(transientDescrizioneRichiedenteAziendaStorico).toUpperCase();
    }
}
