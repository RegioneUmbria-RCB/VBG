package it.gruppoinit.pal.gp.core.domain;

import java.io.Serializable;

import javax.persistence.Column;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

public class AppIoCodaMovimentiId implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7923372418150040329L;
    private String idcomune;
    private Integer codiceMovimento;
    private String guidCoda;

    public AppIoCodaMovimentiId() {

	super();
	this.idcomune = ORMHelper.getIdcomune();
    }

    public AppIoCodaMovimentiId(Integer codiceMovimento, String guidCoda) {

	this();
	this.codiceMovimento = codiceMovimento;
	this.guidCoda = guidCoda;
    }

    public AppIoCodaMovimentiId(String idcomune, Integer codiceMovimento, String guidCoda) {

	this();
	this.idcomune = idcomune;
	this.codiceMovimento = codiceMovimento;
	this.guidCoda = guidCoda;
    }

    @Column(name = "IDCOMUNE", nullable = false, length = 6)
    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    @Column(name = "CODICEMOVIMENTO", nullable = false, length = 8)
    public Integer getCodiceMovimento() {

	return codiceMovimento;
    }

    public void setCodiceMovimento(Integer codiceMovimento) {

	this.codiceMovimento = codiceMovimento;
    }

    @Column(name = "GUID", nullable = false, length = 40)
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
	if (!(other instanceof AppIoCodaMovimentiId))
	    return false;
	AppIoCodaMovimentiId castOther = (AppIoCodaMovimentiId) other;
	return ((this.getIdcomune() == castOther.getIdcomune())
		|| (this.getIdcomune() != null && castOther.getIdcomune() != null && this.getIdcomune().equals(castOther.getIdcomune())))
		&& ((this.getCodiceMovimento() == castOther.getCodiceMovimento()) || (this.getCodiceMovimento() != null
			&& castOther.getCodiceMovimento() != null && this.getCodiceMovimento().equals(castOther.getCodiceMovimento())))
		&& ((this.getGuidCoda() == castOther.getGuidCoda())
			|| (this.getGuidCoda() != null && castOther.getGuidCoda() != null && this.getGuidCoda().equals(castOther.getGuidCoda())));
    }

    @Override
    public int hashCode() {

	int result = 17;
	result = 37 * result + (getIdcomune() == null ? 0 : this.getIdcomune().hashCode());
	result = 37 * result + (getCodiceMovimento() == null ? 0 : this.getCodiceMovimento().hashCode());
	result = 37 * result + (getGuidCoda() == null ? 0 : this.getGuidCoda().hashCode());
	return result;
    }
}
