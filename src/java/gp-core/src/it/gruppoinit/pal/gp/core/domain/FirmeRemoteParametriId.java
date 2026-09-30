package it.gruppoinit.pal.gp.core.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

@Embeddable
public class FirmeRemoteParametriId implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -6078800444609426622L;
    private String idcomune;
    private Integer fkIdFirmaRemota;
    private String chiave;

    public FirmeRemoteParametriId() {

	this.idcomune = ORMHelper.getIdcomune();
    }

    public FirmeRemoteParametriId(String idcomune, Integer fkIdFirmaRemota, String chiave) {

	this.idcomune = idcomune;
	this.fkIdFirmaRemota = fkIdFirmaRemota;
	this.chiave = chiave;
    }

    @Column(name = "IDCOMUNE", nullable = false, length = 6)
    public String getIdcomune() {

	return this.idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    @Column(name = "FK_IDFIRMAREMOTA", nullable = false, precision = 10, scale = 0)
    public Integer getFkIdFirmaRemota() {

	return fkIdFirmaRemota;
    }

    public void setFkIdFirmaRemota(Integer fkIdFirmaRemota) {

	this.fkIdFirmaRemota = fkIdFirmaRemota;
    }

    @Column(name = "CHIAVE", nullable = false, length = 40)
    public String getChiave() {

	return chiave;
    }

    public void setChiave(String chiave) {

	this.chiave = chiave;
    }

    @Override
    public boolean equals(Object other) {

	if ((this == other))
	    return true;
	if ((other == null))
	    return false;
	if (!(other instanceof FirmeRemoteParametriId))
	    return false;
	FirmeRemoteParametriId castOther = (FirmeRemoteParametriId) other;
	return ((this.getIdcomune() == castOther.getIdcomune())
		|| (this.getIdcomune() != null && castOther.getIdcomune() != null && this.getIdcomune().equals(castOther.getIdcomune())))
		&& ((this.getFkIdFirmaRemota() == castOther.getFkIdFirmaRemota()) || (this.getFkIdFirmaRemota() != null
			&& castOther.getFkIdFirmaRemota() != null && this.getFkIdFirmaRemota().equals(castOther.getFkIdFirmaRemota())))
		&& ((this.getChiave() == castOther.getChiave())
			|| (this.getChiave() != null && castOther.getChiave() != null && this.getChiave().equals(castOther.getChiave())));
    }

    @Override
    public int hashCode() {

	int result = 17;
	result = 37 * result + (this.getIdcomune() == null ? 0 : this.getIdcomune().hashCode());
	result = 37 * result + (this.getFkIdFirmaRemota() == null ? 0 : this.getFkIdFirmaRemota().hashCode());
	result = 37 * result + (this.getChiave() == null ? 0 : this.getChiave().hashCode());
	return result;
    }

    @Override
    public String toString() {

	ToStringBuilder toStringBuilder = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
	toStringBuilder.append("idcomune", this.idcomune);
	toStringBuilder.append("fkIdFirmaRemota", this.fkIdFirmaRemota);
	toStringBuilder.append("chiave", this.chiave);
	return toStringBuilder.toString();
    }
}
