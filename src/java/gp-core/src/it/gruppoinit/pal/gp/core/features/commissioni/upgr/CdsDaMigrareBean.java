package it.gruppoinit.pal.gp.core.features.commissioni.upgr;

import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class CdsDaMigrareBean {

    private String idcomune;
    private Integer id;
    private Integer codiceistanza;
    private Integer codicemovimento;
    private String odg;
    private String note;
    private Date dataconvocazione;
    private String numeroistanza;
    private String software;
    private Integer movesistente;

    public boolean esisteMovimento() {

	return movesistente != null;
    }

    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public Integer getCodiceistanza() {

	return codiceistanza;
    }

    public void setCodiceistanza(Integer codiceistanza) {

	this.codiceistanza = codiceistanza;
    }

    public Integer getCodicemovimento() {

	return codicemovimento;
    }

    public void setCodicemovimento(Integer codicemovimento) {

	this.codicemovimento = codicemovimento;
    }

    public String getOdg() {

	return odg;
    }

    public void setOdg(String odg) {

	this.odg = odg;
    }

    public String getNote() {

	return note;
    }

    public void setNote(String note) {

	this.note = note;
    }

    public String getNumeroistanza() {

	return numeroistanza;
    }

    public void setNumeroistanza(String numeroistanza) {

	this.numeroistanza = numeroistanza;
    }

    public String getSoftware() {

	return software;
    }

    public void setSoftware(String software) {

	this.software = software;
    }

    public Date getDataconvocazione() {

	return dataconvocazione;
    }

    public void setDataconvocazione(Date dataconvocazione) {

	this.dataconvocazione = dataconvocazione;
    }

    public Integer getMovesistente() {

	return movesistente;
    }

    public void setMovesistente(Integer movesistente) {

	this.movesistente = movesistente;
    }

    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
