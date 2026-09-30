package it.gruppoinit.pal.gp.core.domain;

import java.io.Serializable;

import javax.persistence.Column;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

public class AppIoCodaMassiveDId implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2741756869761410252L;
    private String idcomune;
    private Integer idDettaglioComunicazione;
    private String guidCoda;

    public AppIoCodaMassiveDId() {

	super();
	this.idcomune = ORMHelper.getIdcomune();
    }

    public AppIoCodaMassiveDId(Integer idDettaglioComunicazione, String guidCoda) {

	this();
	this.idcomune = ORMHelper.getIdcomune();
	this.idDettaglioComunicazione = idDettaglioComunicazione;
	this.guidCoda = guidCoda;
    }

    public AppIoCodaMassiveDId(String idcomune, Integer idDettaglioComunicazione, String guidCoda) {

	this();
	this.idcomune = idcomune;
	this.idDettaglioComunicazione = idDettaglioComunicazione;
	this.guidCoda = guidCoda;
    }

    @Column(name = "IDCOMUNE", nullable = false, length = 6)
    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    @Column(name = "FK_MASSIVE_D", nullable = false, length = 6)
    public Integer getIdDettaglioComunicazione() {

	return idDettaglioComunicazione;
    }

    public void setIdDettaglioComunicazione(Integer idDettaglioComunicazione) {

	this.idDettaglioComunicazione = idDettaglioComunicazione;
    }

    @Column(name = "FK_GUIDCODA", nullable = false, length = 40)
    public String getGuidCoda() {

	return guidCoda;
    }

    public void setGuidCoda(String guidCoda) {

	this.guidCoda = guidCoda;
    }

    @Override
    public boolean equals(Object other) {

	if ((this == other))
	    return true;
	if ((other == null))
	    return false;
	if (!(other instanceof AppIoCodaMassiveDId))
	    return false;
	AppIoCodaMassiveDId castOther = (AppIoCodaMassiveDId) other;
	return ((this.getIdcomune() == castOther.getIdcomune())
		|| (this.getIdcomune() != null && castOther.getIdcomune() != null && this.getIdcomune().equals(castOther.getIdcomune())))
		&& ((this.getIdDettaglioComunicazione() == castOther.getIdDettaglioComunicazione())
			|| (this.getIdDettaglioComunicazione() != null && castOther.getIdDettaglioComunicazione() != null
				&& this.getIdDettaglioComunicazione().equals(castOther.getIdDettaglioComunicazione())))
		&& ((this.getGuidCoda() == castOther.getGuidCoda())
			|| (this.getGuidCoda() != null && castOther.getGuidCoda() != null && this.getGuidCoda().equals(castOther.getGuidCoda())));
    }

    @Override
    public int hashCode() {

	int result = 17;
	result = 37 * result + (getIdcomune() == null ? 0 : this.getIdcomune().hashCode());
	result = 37 * result + (getIdDettaglioComunicazione() == null ? 0 : this.getIdDettaglioComunicazione().hashCode());
	result = 37 * result + (getGuidCoda() == null ? 0 : this.getGuidCoda().hashCode());
	return result;
    }
}
