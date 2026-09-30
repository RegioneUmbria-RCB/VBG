package it.gruppoinit.pal.gp.core.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

@Embeddable
public class MovimentiZipLogicoTestataId implements java.io.Serializable {

    private static final long serialVersionUID = -1;
    private String idcomune;
    private Integer codicemovimento;

    public MovimentiZipLogicoTestataId() {

	this.idcomune = ORMHelper.getIdcomune();
    }

    public MovimentiZipLogicoTestataId(Integer codicemovimento) {

	this.idcomune = ORMHelper.getIdcomune();
	this.codicemovimento = codicemovimento;
    }

    public MovimentiZipLogicoTestataId(String idcomune, Integer codicemovimento) {

	this.idcomune = idcomune;
	this.codicemovimento = codicemovimento;
    }

    @Column(name = "IDCOMUNE", nullable = false, length = 6)
    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    @Column(name = "CODICEMOVIMENTO", nullable = false, precision = 8, scale = 0)
    public Integer getCodicemovimento() {

	return codicemovimento;
    }

    public void setCodicemovimento(Integer codicemovimento) {

	this.codicemovimento = codicemovimento;
    }

    public boolean equals(Object other) {

	if ((this == other))
	    return true;
	if ((other == null))
	    return false;
	if (!(other instanceof MovimentiZipLogicoTestataId))
	    return false;
	MovimentiZipLogicoTestataId castOther = (MovimentiZipLogicoTestataId) other;
	return ((this.getIdcomune() == castOther.getIdcomune())
		|| (this.getIdcomune() != null && castOther.getIdcomune() != null && this.getIdcomune().equals(castOther.getIdcomune())))
		&& ((this.getCodicemovimento() == castOther.getCodicemovimento()) || (this.getCodicemovimento() != null
			&& castOther.getCodicemovimento() != null && this.getCodicemovimento().equals(castOther.getCodicemovimento())));
    }

    public int hashCode() {

	int result = 17;
	result = 37 * result + (this.getIdcomune() == null ? 0 : this.getIdcomune().hashCode());
	result = 37 * result + (this.getCodicemovimento() == null ? 0 : this.getCodicemovimento().hashCode());
	return result;
    }

    @Override
    public String toString() {

	ToStringBuilder toStringBuilder = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
	toStringBuilder.append("idcomune", this.idcomune);
	toStringBuilder.append("codicemovimento", this.codicemovimento);
	return toStringBuilder.toString();
    }
}
