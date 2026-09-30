package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class AnagrafeDTO {

    private PkId id;
    private String tipoanagrafe;
    private String nominativo;
    private String nome;
    private String partitaiva;
    private String codicefiscale;
    private String formagiuridica;
    private Integer tipologia;
    private Integer flagDisabilitato;
    private String descrizioneRichiedente;
    //
    private String indirizzo;
    private String cap;
    private String citta;
    private String provincia;
    private String numiscrrea;
    private Date dataiscrrea;
    private String telefono;
    private Date dataInizioAttivita;
    private String email;

    public AnagrafeDTO() {

	this.id = new PkId();
    }

    public PkId getId() {

	return id;
    }

    public void setId(PkId id) {

	this.id = id;
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

    public String getFormagiuridica() {

	return formagiuridica;
    }

    public void setFormagiuridica(String formagiuridica) {

	this.formagiuridica = formagiuridica;
    }

    public Integer getTipologia() {

	return tipologia;
    }

    public void setTipologia(Integer tipologia) {

	this.tipologia = tipologia;
    }

    public Integer getFlagDisabilitato() {

	return flagDisabilitato;
    }

    public void setFlagDisabilitato(Integer flagDisabilitato) {

	this.flagDisabilitato = flagDisabilitato;
    }

    public String getIndirizzo() {

	return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {

	this.indirizzo = indirizzo;
    }

    public String getCap() {

	return cap;
    }

    public void setCap(String cap) {

	this.cap = cap;
    }

    public String getCitta() {

	return citta;
    }

    public void setCitta(String citta) {

	this.citta = citta;
    }

    public String getProvincia() {

	return provincia;
    }

    public void setProvincia(String provincia) {

	this.provincia = provincia;
    }

    public String getNumiscrrea() {

	return numiscrrea;
    }

    public void setNumiscrrea(String numiscrrea) {

	this.numiscrrea = numiscrrea;
    }

    public Date getDataiscrrea() {

	return dataiscrrea;
    }

    public void setDataiscrrea(Date dataiscrrea) {

	this.dataiscrrea = dataiscrrea;
    }

    public String getTelefono() {

	return telefono;
    }

    public void setTelefono(String telefono) {

	this.telefono = telefono;
    }

    public Date getDataInizioAttivita() {

	return dataInizioAttivita;
    }

    public void setDataInizioAttivita(Date dataInizioAttivita) {

	this.dataInizioAttivita = dataInizioAttivita;
    }

    public String getEmail() {

	return email;
    }

    public void setEmail(String email) {

	this.email = email;
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
	if (getTipologia() != null) {
	    if (getTipologia().intValue() == -1) {
		answer += " T";
	    }
	}
	if (getFlagDisabilitato() != null && getFlagDisabilitato().equals(Integer.valueOf(1))) {
	    answer += " [Disabilitata]";
	}
	descrizioneRichiedente = answer;
	return descrizioneRichiedente;
    }
}
