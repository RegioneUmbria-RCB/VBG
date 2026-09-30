package it.gruppoinit.pal.gp.core.features.bollettazione.gestione.model;

import org.apache.commons.lang.StringUtils;

public class VerificaCausaliDettaglioBollettazioneBean {

    private Integer iddettaglio;
    private String descrizione;
    private Integer codiceanagrafe;
    private String nominativo;
    private String nome;
    private String codicefiscale;
    private String partitaiva;
    private Integer idcausaleonere;
    private Boolean attivo;
    private String mappaturanodopag;
    private String conto;
    private Integer idconto;

    public Integer getIddettaglio() {

	return iddettaglio;
    }

    public void setIddettaglio(Integer iddettaglio) {

	this.iddettaglio = iddettaglio;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public Integer getCodiceanagrafe() {

	return codiceanagrafe;
    }

    public void setCodiceanagrafe(Integer codiceanagrafe) {

	this.codiceanagrafe = codiceanagrafe;
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

    public Integer getIdcausaleonere() {

	return idcausaleonere;
    }

    public void setIdcausaleonere(Integer idcausaleonere) {

	this.idcausaleonere = idcausaleonere;
    }

    public Boolean getAttivo() {

	return attivo;
    }

    public void setAttivo(Boolean attivo) {

	this.attivo = attivo;
    }

    public String getMappaturanodopag() {

	return mappaturanodopag;
    }

    public void setMappaturanodopag(String mappaturanodopag) {

	this.mappaturanodopag = mappaturanodopag;
    }

    public String getConto() {

	return conto;
    }

    public void setConto(String conto) {

	this.conto = conto;
    }

 

    public Integer getIdconto() {

	return idconto;
    }

    public void setIdconto(Integer idconto) {

	this.idconto = idconto;
    }

    public String getDescrizioneConto() {

	if (StringUtils.isBlank(this.conto)) {
	    return "";
	}
	String descrizioneConto = this.conto;
	return descrizioneConto;
    }

    public String getDescrizioneRichiedenteBreve() {

	String answer = getNominativo();
	if (StringUtils.isNotBlank(getNome())) {
	    answer += " " + getNome();
	}
	if (StringUtils.isNotBlank(codicefiscale)) {
	    answer += " [cf: " + codicefiscale + "]";
	}
	if (StringUtils.isNotBlank(partitaiva)) {
	    answer += " [piva: " + partitaiva + "]";
	}
	return answer;
    }
}
