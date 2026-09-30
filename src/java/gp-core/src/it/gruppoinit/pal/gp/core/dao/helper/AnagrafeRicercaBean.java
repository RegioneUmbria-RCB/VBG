package it.gruppoinit.pal.gp.core.dao.helper;

import java.io.Serializable;

import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.constants.WebConstants;

public class AnagrafeRicercaBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4718986974171378356L;
    private Integer codiceanagrafe;
    private String tipoanagrafe;
    private Integer tipologia;
    private String nome;
    private String nominativo;
    private String codicefiscale;
    private String partitaiva;
    private String descrizioneRichiedente;

    public Integer getCodiceanagrafe() {

	return codiceanagrafe;
    }

    public void setCodiceanagrafe(Integer codiceanagrafe) {

	this.codiceanagrafe = codiceanagrafe;
    }

    public String getTipoanagrafe() {

	return tipoanagrafe;
    }

    public void setTipoanagrafe(String tipoanagrafe) {

	this.tipoanagrafe = tipoanagrafe;
    }

    public Integer getTipologia() {

	return tipologia;
    }

    public void setTipologia(Integer tipologia) {

	this.tipologia = tipologia;
    }

    public String getNome() {

	return nome;
    }

    public void setNome(String nome) {

	this.nome = nome;
    }

    public String getNominativo() {

	return nominativo;
    }

    public void setNominativo(String nominativo) {

	this.nominativo = nominativo;
    }

    public String getCodicefiscale() {

	return codicefiscale;
    }

    public void setCodicefiscale(String codicefiscale) {

	this.codicefiscale = codicefiscale;
    }

    public String getPartitaiva() {

	return partitaiva;
    }

    public void setPartitaiva(String partitaiva) {

	this.partitaiva = partitaiva;
    }

    @Transient
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
	if (StringUtils.isNotBlank(getTipoanagrafe())) {
	    answer += " [P." + getTipoanagrafe() + ".]";
	}
	setDescrizioneRichiedente(answer);
	return descrizioneRichiedente;
    }

    public void setDescrizioneRichiedente(String descrizioneRichiedente) {

	this.descrizioneRichiedente = descrizioneRichiedente;
    }
}
