package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.PkId;

import java.math.BigDecimal;

public class MercatiDDTO {

    private PkId id;
    private String codiceposteggio;
    private BigDecimal larghezza;
    private BigDecimal lunghezza;
    private BigDecimal superficie;
    private String tipoSpazio;
    private Boolean disabilitato;
    private String note;

    public MercatiDDTO() {

	this.id = new PkId();
    }

    public PkId getId() {

	return id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    public String getCodiceposteggio() {

	return codiceposteggio;
    }

    public void setCodiceposteggio(String codiceposteggio) {

	this.codiceposteggio = codiceposteggio;
    }

    public BigDecimal getLarghezza() {

	return larghezza;
    }

    public void setLarghezza(BigDecimal larghezza) {

	this.larghezza = larghezza;
    }

    public BigDecimal getLunghezza() {

	return lunghezza;
    }

    public void setLunghezza(BigDecimal lunghezza) {

	this.lunghezza = lunghezza;
    }

    public BigDecimal getSuperficie() {

	return superficie;
    }

    public void setSuperficie(BigDecimal superficie) {

	this.superficie = superficie;
    }

    public String getTipoSpazio() {

	return tipoSpazio;
    }

    public void setTipoSpazio(String tipoSpazio) {

	this.tipoSpazio = tipoSpazio;
    }

    public Boolean getDisabilitato() {

	return disabilitato;
    }

    public void setDisabilitato(Boolean disabilitato) {

	this.disabilitato = disabilitato;
    }

    public String getNote() {

	return note;
    }

    public void setNote(String note) {

	this.note = note;
    }
}
