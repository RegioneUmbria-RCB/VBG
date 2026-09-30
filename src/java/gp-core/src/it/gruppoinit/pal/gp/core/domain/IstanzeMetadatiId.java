package it.gruppoinit.pal.gp.core.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

@Embeddable
public class IstanzeMetadatiId implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -986544125478019761L;
    private String idcomune;
    private Integer codiceistanza;
    private String chiave;

    public IstanzeMetadatiId() {

	this.idcomune = ORMHelper.getIdcomune();
    }

    public IstanzeMetadatiId(Integer codiceistanza, String chiave) {

	this.idcomune = ORMHelper.getIdcomune();
	this.codiceistanza = codiceistanza;
	this.chiave = chiave;
    }

    public IstanzeMetadatiId(String idcomune, Integer codiceistanza, String chiave) {

	this.idcomune = idcomune;
	this.codiceistanza = codiceistanza;
	this.chiave = chiave;
    }

    @Column(name = "IDCOMUNE", nullable = false, length = 6)
    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    @Column(name = "CODICEISTANZA", nullable = false, precision = 6, scale = 0)
    public Integer getCodiceistanza() {

	return codiceistanza;
    }

    public void setCodiceistanza(Integer codiceistanza) {

	this.codiceistanza = codiceistanza;
    }

    @Column(name = "CHIAVE", nullable = false, length = 60)
    public String getChiave() {

	return chiave;
    }

    public void setChiave(String chiave) {

	this.chiave = chiave;
    }

    public boolean equals(Object other) {

	if ((this == other))
	    return true;
	if ((other == null))
	    return false;
	if (!(other instanceof IstanzeMetadatiId))
	    return false;
	IstanzeMetadatiId castOther = (IstanzeMetadatiId) other;
	return ((this.getIdcomune() == castOther.getIdcomune())
		|| (this.getIdcomune() != null && castOther.getIdcomune() != null && this.getIdcomune().equals(castOther.getIdcomune())))
		&& ((this.getCodiceistanza() == castOther.getCodiceistanza()) || (this.getCodiceistanza() != null
			&& castOther.getCodiceistanza() != null && this.getCodiceistanza().equals(castOther.getCodiceistanza())))
		&& ((this.getChiave() == castOther.getChiave())
			|| (this.getChiave() != null && castOther.getChiave() != null && this.getChiave().equals(castOther.getChiave())));
    }

    public int hashCode() {

	int result = 17;
	result = 37 * result + (getIdcomune() == null ? 0 : this.getIdcomune().hashCode());
	result = 37 * result + (getCodiceistanza() == null ? 0 : this.getCodiceistanza().hashCode());
	result = 37 * result + (getChiave() == null ? 0 : this.getChiave().hashCode());
	return result;
    }
}
