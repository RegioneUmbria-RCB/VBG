package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.constants.WebConstants;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class DomandeSTCScadenzarioDTO {

    //Dati dell'istanza
    private String idcomune;
    private Integer codiceistanza;
    private String numeroistanza;
    private Date data;
    private String numeroprotocollo;
    private Date dataprotocollo;
    private String scDescrizione;
    private String stato;
    private String comune;
    private Date datafine;
    private String intervento;
    private String amministrazionidescrizione;
    private String responsabiledescrizione;
    private String tipomovimentodescrizione;
    private Date termineprocedimento;
    // Dati del software
    private String descrizioneSoftware;
    private String codSoftware;
    // Dati del richiedente
    private String tipoanagrafe;
    private String nominativo;
    private String nome;
    private String partitaiva;
    private String codicefiscale;
    private String tipologia;
    private Boolean flagDisabilitato;
    private String formagiuridica;
    private String idNodo;
    //private AnagrafeDTO richiedente;
    private String mittenteDomanda;
    private String sportelloDomanda;
    private String entemittente;
    private String ultimoerrore;
    private String descrizioneRichiedente;
    private String codiceModuloAndIdMittente;
    private String posizionearchivio;
    private String procedura;
    private String istruttore;

    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    public Integer getCodiceistanza() {

	return codiceistanza;
    }

    public void setCodiceistanza(Integer codiceistanza) {

	this.codiceistanza = codiceistanza;
    }

    public String getNumeroistanza() {

	return numeroistanza;
    }

    public void setNumeroistanza(String numeroistanza) {

	this.numeroistanza = numeroistanza;
    }

    public Date getData() {

	return data;
    }

    public void setData(Date data) {

	this.data = data;
    }

    public String getNumeroprotocollo() {

	return numeroprotocollo;
    }

    public void setNumeroprotocollo(String numeroprotocollo) {

	this.numeroprotocollo = numeroprotocollo;
    }

    public Date getDataprotocollo() {

	return dataprotocollo;
    }

    public void setDataprotocollo(Date dataprotocollo) {

	this.dataprotocollo = dataprotocollo;
    }

    public String getScDescrizione() {

	return scDescrizione;
    }

    public void setScDescrizione(String scDescrizione) {

	this.scDescrizione = scDescrizione;
    }

    public String getStato() {

	return stato;
    }

    public void setStato(String stato) {

	this.stato = stato;
    }

    public String getComune() {

	return comune;
    }

    public void setComune(String comune) {

	this.comune = comune;
    }

    public Date getDatafine() {

	return datafine;
    }

    public void setDatafine(Date datafine) {

	this.datafine = datafine;
    }

    public String getIntervento() {

	return intervento;
    }

    public void setIntervento(String intervento) {

	this.intervento = intervento;
    }

    public String getAmministrazionidescrizione() {

	return amministrazionidescrizione;
    }

    public void setAmministrazionidescrizione(String amministrazionidescrizione) {

	this.amministrazionidescrizione = amministrazionidescrizione;
    }

    public String getResponsabiledescrizione() {

	return responsabiledescrizione;
    }

    public void setResponsabiledescrizione(String responsabiledescrizione) {

	this.responsabiledescrizione = responsabiledescrizione;
    }

    public String getTipomovimentodescrizione() {

	return tipomovimentodescrizione;
    }

    public void setTipomovimentodescrizione(String tipomovimentodescrizione) {

	this.tipomovimentodescrizione = tipomovimentodescrizione;
    }

    public String getDescrizioneSoftware() {

	return descrizioneSoftware;
    }

    public void setDescrizioneSoftware(String descrizioneSoftware) {

	this.descrizioneSoftware = descrizioneSoftware;
    }

    public String getCodSoftware() {

	return codSoftware;
    }

    public void setCodSoftware(String codSoftware) {

	this.codSoftware = codSoftware;
    }

    public String getTipoanagrafe() {

	return tipoanagrafe;
    }

    public void setTipoanagrafe(String tipoanagrafe) {

	this.tipoanagrafe = tipoanagrafe;
    }

    public String getNominativo() {

	return nominativo;
    }

    public void setNominativo(String nominativo) {

	this.nominativo = nominativo;
    }

    public String getNome() {

	return nome;
    }

    public void setNome(String nome) {

	this.nome = nome;
    }

    public String getPartitaiva() {

	return partitaiva;
    }

    public void setPartitaiva(String partitaiva) {

	this.partitaiva = partitaiva;
    }

    public String getCodicefiscale() {

	return codicefiscale;
    }

    public void setCodicefiscale(String codicefiscale) {

	this.codicefiscale = codicefiscale;
    }

    public String getTipologia() {

	return tipologia;
    }

    public void setTipologia(String tipologia) {

	this.tipologia = tipologia;
    }

    public Boolean getFlagDisabilitato() {

	return flagDisabilitato;
    }

    public void setFlagDisabilitato(Boolean flagDisabilitato) {

	this.flagDisabilitato = flagDisabilitato;
    }

    public String getFormagiuridica() {

	return formagiuridica;
    }

    public void setFormagiuridica(String formagiuridica) {

	this.formagiuridica = formagiuridica;
    }

    public String getIdNodo() {

	return idNodo;
    }

    public void setIdNodo(String idNodo) {

	this.idNodo = idNodo;
    }

    public String getMittenteDomanda() {

	return mittenteDomanda;
    }

    public void setMittenteDomanda(String mittenteDomanda) {

	this.mittenteDomanda = mittenteDomanda;
    }

    public String getUltimoerrore() {

	return ultimoerrore;
    }

    public void setUltimoerrore(String ultimoerrore) {

	this.ultimoerrore = ultimoerrore;
    }

    public String getSportelloDomanda() {

	return sportelloDomanda;
    }

    public void setSportelloDomanda(String sportelloDomanda) {

	this.sportelloDomanda = sportelloDomanda;
    }

    public String getDescrizioneRichiedente() {

	String answer = "";
	if (StringUtils.isBlank(getTipoanagrafe())) {
	    return getNominativo();
	}
	String tNominativo = getNominativo() == null ? "" : getNominativo();
	if (getTipoanagrafe().equalsIgnoreCase(WebConstants.PERSONA_FISICA)) {
	    answer = tNominativo + " " + (getNome() == null ? "" : getNome());
	    if (StringUtils.isNotBlank(getCodicefiscale())) {
		answer += " CF: " + getCodicefiscale();
	    }
	} else {
	    answer = tNominativo;
	    String tFormaGiuridica = "";
	    if (getFormagiuridica() != null) {
		tFormaGiuridica = formagiuridica;
	    }
	    answer += " " + tFormaGiuridica;
	    if (StringUtils.isNotBlank(getPartitaiva())) {
		answer += " P.Iva: " + getPartitaiva();
	    }
	    if (StringUtils.isNotBlank(getCodicefiscale())) {
		answer += " CF: " + getCodicefiscale();
	    }
	}
	//	if (getTipologia() != null) {
	//	    if (getTipologia().intValue() == -1) {
	//		answer += " T";
	//	    }
	//	}
	if (getFlagDisabilitato() != null && getFlagDisabilitato().equals(Integer.valueOf(1))) {
	    answer += " [Disabilitata]";
	}
	descrizioneRichiedente = answer;
	return descrizioneRichiedente;
    }

    public String getCodiceModuloAndIdMittente() {

	String risultato = "";
	if (StringUtils.isNotBlank(this.idNodo)) {
	    risultato += this.idNodo;
	} else {
	    risultato = "";
	}
	if (StringUtils.isNotBlank(this.entemittente)) {
	    risultato += "|" + this.entemittente;
	} else {
	    risultato += "|";
	}
	if (StringUtils.isNotBlank(this.sportelloDomanda)) {
	    risultato += "|" + this.sportelloDomanda;
	} else {
	    risultato += "|";
	}
	codiceModuloAndIdMittente = risultato;
	return codiceModuloAndIdMittente;
    }

    public String getPosizionearchivio() {

	return posizionearchivio;
    }

    public void setPosizionearchivio(String posizionearchivio) {

	this.posizionearchivio = posizionearchivio;
    }

    public String getProcedura() {

	return procedura;
    }

    public void setProcedura(String procedura) {

	this.procedura = procedura;
    }

    public Date getTermineprocedimento() {

	return termineprocedimento;
    }

    public void setTermineprocedimento(Date termineprocedimento) {

	this.termineprocedimento = termineprocedimento;
    }

    public String getIstruttore() {

	return istruttore;
    }

    public void setIstruttore(String istruttore) {

	this.istruttore = istruttore;
    }

    public String getEntemittente() {

	return entemittente;
    }

    public void setEntemittente(String entemittente) {

	this.entemittente = entemittente;
    }
}
