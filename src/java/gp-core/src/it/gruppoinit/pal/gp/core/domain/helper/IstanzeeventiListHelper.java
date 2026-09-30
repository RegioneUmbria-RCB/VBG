package it.gruppoinit.pal.gp.core.domain.helper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class IstanzeeventiListHelper implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3476214905521092635L;
    private BigDecimal id;
    private String idcomune;
    private String fkidcategoriaevento;
    private String categoria;
    private Date dataevento;
    private String evento;
    private Integer flag_letto;
    private Integer codiceistanza;
    private String numeroistanza;
    private String codicesoftware;
    private String descrizionesoftware;
    private Integer codicemovimento;
    private String movimento;
    private String tipomovimento;
    private String nomeRichiedente;
    private String nominativoRichiedente;
    private String nomeTitolareLegale;
    private String nominativoTitolareLegale;
    private String inQualitaDi;
    private String richiedente;

    public BigDecimal getId() {

	return id;
    }

    public void setId(BigDecimal id) {

	this.id = id;
    }

    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    public String getFkidcategoriaevento() {

	return fkidcategoriaevento;
    }

    public void setFkidcategoriaevento(String fkidcategoriaevento) {

	this.fkidcategoriaevento = fkidcategoriaevento;
    }

    public String getCategoria() {

	return categoria;
    }

    public void setCategoria(String categoria) {

	this.categoria = categoria;
    }

    public Date getDataevento() {

	return dataevento;
    }

    public void setDataevento(Date dataevento) {

	this.dataevento = dataevento;
    }

    public String getEvento() {

	return evento;
    }

    public void setEvento(String evento) {

	this.evento = evento;
    }

    public Integer getFlag_letto() {

	return flag_letto;
    }

    public void setFlag_letto(Integer flag_letto) {

	this.flag_letto = flag_letto;
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

    public String getCodicesoftware() {

	return codicesoftware;
    }

    public void setCodicesoftware(String codicesoftware) {

	this.codicesoftware = codicesoftware;
    }

    public String getDescrizionesoftware() {

	return descrizionesoftware;
    }

    public void setDescrizionesoftware(String descrizionesoftware) {

	this.descrizionesoftware = descrizionesoftware;
    }

    public Integer getCodicemovimento() {

	return codicemovimento;
    }

    public void setCodicemovimento(Integer codicemovimento) {

	this.codicemovimento = codicemovimento;
    }

    public String getMovimento() {

	return movimento;
    }

    public void setMovimento(String movimento) {

	this.movimento = movimento;
    }

    public String getTipomovimento() {

	return tipomovimento;
    }

    public void setTipomovimento(String tipomovimento) {

	this.tipomovimento = tipomovimento;
    }

    public String getNomeRichiedente() {

	return nomeRichiedente;
    }

    public void setNomeRichiedente(String nomeRichiedente) {

	this.nomeRichiedente = nomeRichiedente;
    }

    public String getNominativoRichiedente() {

	return nominativoRichiedente;
    }

    public void setNominativoRichiedente(String nominativoRichiedente) {

	this.nominativoRichiedente = nominativoRichiedente;
    }

    public String getNomeTitolareLegale() {

	return nomeTitolareLegale;
    }

    public void setNomeTitolareLegale(String nomeTitolareLegale) {

	this.nomeTitolareLegale = nomeTitolareLegale;
    }

    public String getNominativoTitolareLegale() {

	return nominativoTitolareLegale;
    }

    public void setNominativoTitolareLegale(String nominativoTitolareLegale) {

	this.nominativoTitolareLegale = nominativoTitolareLegale;
    }

    public String getInQualitaDi() {

	return inQualitaDi;
    }

    public void setInQualitaDi(String inQualitaDi) {

	this.inQualitaDi = inQualitaDi;
    }

    public String getRichiedente() {

	StringBuffer rich = new StringBuffer();
	if (StringUtils.isNotBlank(getNomeRichiedente())) {
	    rich.append(StringUtils.defaultIfEmpty(getNomeRichiedente(), "")).append(" ")
		    .append(StringUtils.defaultIfEmpty(getNominativoRichiedente(), ""));
	}
	if (StringUtils.isNotBlank(getInQualitaDi())) {
	    rich.append(" ").append(StringUtils.defaultIfEmpty(getInQualitaDi(), " "));
	}
	if (StringUtils.isNotBlank(getNominativoTitolareLegale())) {
	    rich.append(StringUtils.defaultIfEmpty(getNomeTitolareLegale(), " ")).append(" ").append(getNominativoTitolareLegale());
	}
	return rich.toString();
    }

    public void setRichiedente(String richiedente) {

	this.richiedente = richiedente;
    }
}
