package it.gruppoinit.pal.gp.core.domain;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class IstanzeAccessoAttiAnagrafeId implements Serializable {

    private static final long serialVersionUID = -8838763946043903826L;
    private String idcomune;
    private Integer istanzeAccessoAttiT;
    private Integer codiceanagrafe;

    public IstanzeAccessoAttiAnagrafeId() {

	super();
	this.idcomune = ORMHelper.getIdcomune();
    }

    public IstanzeAccessoAttiAnagrafeId(Integer istanzeAccessoAttiT, Integer codiceanagrafe) {

	this();
	this.istanzeAccessoAttiT = istanzeAccessoAttiT;
	this.codiceanagrafe = codiceanagrafe;
    }

    public IstanzeAccessoAttiAnagrafeId(String idcomune, Integer istanzeAccessoAttiT, Integer codiceanagrafe) {

	this();
	this.idcomune = idcomune;
	this.istanzeAccessoAttiT = istanzeAccessoAttiT;
	this.codiceanagrafe = codiceanagrafe;
    }

    @Column(name = "IDCOMUNE", nullable = false, length = 6)
    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    @Column(name = "FK_IDACCESSO_ATTI", nullable = false, precision = 9, scale = 0)
    public Integer getIstanzeAccessoAttiT() {

	return istanzeAccessoAttiT;
    }

    public void setIstanzeAccessoAttiT(Integer istanzeAccessoAttiT) {

	this.istanzeAccessoAttiT = istanzeAccessoAttiT;
    }

    @Column(name = "CODICEANAGRAFE", nullable = false, precision = 6, scale = 0)
    public Integer getCodiceanagrafe() {

	return codiceanagrafe;
    }

    public void setCodiceanagrafe(Integer codiceanagrafe) {

	this.codiceanagrafe = codiceanagrafe;
    }

    /**
     * private Integer istanzeAccessoAttiT; private Integer codiceanagrafe;
     */
    public boolean equals(Object other) {

	if ((this == other))
	    return true;
	if ((other == null))
	    return false;
	if (!(other instanceof IstanzeAccessoAttiAnagrafeId))
	    return false;
	IstanzeAccessoAttiAnagrafeId castOther = (IstanzeAccessoAttiAnagrafeId) other;
	return ((this.getIdcomune() == castOther.getIdcomune()) || (this.getIdcomune() != null && castOther.getIdcomune() != null && this
		.getIdcomune().equals(castOther.getIdcomune())))
		&& ((this.getIstanzeAccessoAttiT() == castOther.getIstanzeAccessoAttiT()) || (this.getIstanzeAccessoAttiT() != null
			&& castOther.getIstanzeAccessoAttiT() != null && this.getIstanzeAccessoAttiT().equals(castOther.getIstanzeAccessoAttiT())))
		&& ((this.getCodiceanagrafe() == castOther.getCodiceanagrafe()) || (this.getCodiceanagrafe() != null
			&& castOther.getCodiceanagrafe() != null && this.getCodiceanagrafe().equals(castOther.getCodiceanagrafe())));
    }

    public int hashCode() {

	int result = 17;
	result = 37 * result + (getIdcomune() == null ? 0 : this.getIdcomune().hashCode());
	result = 37 * result + (getIstanzeAccessoAttiT() == null ? 0 : this.getIstanzeAccessoAttiT().hashCode());
	result = 37 * result + (getCodiceanagrafe() == null ? 0 : this.getCodiceanagrafe().hashCode());
	return result;
    }
}
