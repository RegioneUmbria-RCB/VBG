package it.gruppoinit.pal.gp.core.domain.helper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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
}
