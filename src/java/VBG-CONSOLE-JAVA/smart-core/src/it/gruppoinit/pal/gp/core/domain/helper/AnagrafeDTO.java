package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.PkId;

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
