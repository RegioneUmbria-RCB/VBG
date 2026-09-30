package it.gruppoinit.pal.gp.core.domain.web.servizijson.rest.v2;

import java.io.Serializable;
import java.util.Date;

public class MovimentoRestBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7830708248802819696L;
    private Integer codice_movimento;
    private Integer codice_istanza;
    private String tipomovimento;
    private Integer codice_inventario;
    private Integer codice_amministrazione;
    private Date data;
    private String parere;
    private Boolean esito;
    private String note;
    private Boolean pubblica;
    private String numeroprotocollo;
    private Date dataprotocollo;
    private Integer codice_responsabile;
    private String movimento;
    private Boolean pubblicaparere;
    private Date dataScadenza;

    public Integer getCodice_movimento() {

	return codice_movimento;
    }

    public void setCodice_movimento(Integer codice_movimento) {

	this.codice_movimento = codice_movimento;
    }

    public Integer getCodice_istanza() {

	return codice_istanza;
    }

    public void setCodice_istanza(Integer codice_istanza) {

	this.codice_istanza = codice_istanza;
    }

    public String getTipomovimento() {

	return tipomovimento;
    }

    public void setTipomovimento(String tipomovimento) {

	this.tipomovimento = tipomovimento;
    }

    public Integer getCodice_inventario() {

	return codice_inventario;
    }

    public void setCodice_inventario(Integer codice_inventario) {

	this.codice_inventario = codice_inventario;
    }

    public Integer getCodice_amministrazione() {

	return codice_amministrazione;
    }

    public void setCodice_amministrazione(Integer codice_amministrazione) {

	this.codice_amministrazione = codice_amministrazione;
    }

    public Date getData() {

	return data;
    }

    public void setData(Date data) {

	this.data = data;
    }

    public String getParere() {

	return parere;
    }

    public void setParere(String parere) {

	this.parere = parere;
    }

    public Boolean getEsito() {

	return esito;
    }

    public void setEsito(Boolean esito) {

	this.esito = esito;
    }

    public String getNote() {

	return note;
    }

    public void setNote(String note) {

	this.note = note;
    }

    public Boolean getPubblica() {

	return pubblica;
    }

    public void setPubblica(Boolean pubblica) {

	this.pubblica = pubblica;
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

    public Integer getCodice_responsabile() {

	return codice_responsabile;
    }

    public void setCodice_responsabile(Integer codice_responsabile) {

	this.codice_responsabile = codice_responsabile;
    }

    public String getMovimento() {

	return movimento;
    }

    public void setMovimento(String movimento) {

	this.movimento = movimento;
    }

    public Boolean getPubblicaparere() {

	return pubblicaparere;
    }

    public void setPubblicaparere(Boolean pubblicaparere) {

	this.pubblicaparere = pubblicaparere;
    }

    public Date getDataScadenza() {

	return dataScadenza;
    }

    public void setDataScadenza(Date dataScadenza) {

	this.dataScadenza = dataScadenza;
    }

    public static long getSerialversionuid() {

	return serialVersionUID;
    }
}
