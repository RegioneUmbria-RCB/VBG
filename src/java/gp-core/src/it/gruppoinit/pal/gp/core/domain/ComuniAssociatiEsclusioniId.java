package it.gruppoinit.pal.gp.core.domain;

import javax.persistence.Column;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

public class ComuniAssociatiEsclusioniId implements java.io.Serializable {

    private static final long serialVersionUID = 4010064775876660403L;
    private String idcomune;
    private String codicecomune;
    private String software;

    public ComuniAssociatiEsclusioniId() {

	this.idcomune = ORMHelper.getIdcomune();
    }

    public ComuniAssociatiEsclusioniId(String codicecomune) {

	this.idcomune = ORMHelper.getIdcomune();
	this.codicecomune = codicecomune;
	this.software = ORMHelper.getSoftware();
    }

    @Column(name = "IDCOMUNE", nullable = false, length = 6)
    public String getIdcomune() {

	return this.idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    @Column(name = "CODICECOMUNE", nullable = false, length = 5)
    public String getCodicecomune() {

	return this.codicecomune;
    }

    public void setCodicecomune(String codicecomune) {

	this.codicecomune = codicecomune;
    }

    @Column(name = "SOFTWARE", nullable = false, length = 2)
    public String getSoftware() {

	return software;
    }

    public void setSoftware(String software) {

	this.software = software;
    }

    public boolean equals(Object other) {

	if ((this == other))
	    return true;
	if ((other == null))
	    return false;
	if (!(other instanceof ComuniAssociatiEsclusioniId))
	    return false;
	ComuniAssociatiEsclusioniId castOther = (ComuniAssociatiEsclusioniId) other;
	return ((this.getIdcomune() == castOther.getIdcomune())
		|| (this.getIdcomune() != null && castOther.getIdcomune() != null && this.getIdcomune().equals(castOther.getIdcomune())))
		&& ((this.getCodicecomune() == castOther.getCodicecomune()) || (this.getCodicecomune() != null && castOther.getCodicecomune() != null
			&& this.getCodicecomune().equals(castOther.getCodicecomune())));
    }

    public int hashCode() {

	int result = 17;
	result = 37 * result + (getIdcomune() == null ? 0 : this.getIdcomune().hashCode());
	result = 37 * result + (getCodicecomune() == null ? 0 : this.getCodicecomune().hashCode());
	return result;
    }
}
