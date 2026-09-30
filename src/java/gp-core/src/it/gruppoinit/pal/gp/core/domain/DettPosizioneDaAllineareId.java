package it.gruppoinit.pal.gp.core.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

@Embeddable
public class DettPosizioneDaAllineareId implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6858557820970835240L;
    private String idcomune;
    private Integer fkDettposizionedebitoriaid;

    public DettPosizioneDaAllineareId() {

	super();
	this.idcomune = ORMHelper.getIdcomune();
    }

    public DettPosizioneDaAllineareId(Integer fkDettposizionedebitoriaid) {

	this();
	this.idcomune = ORMHelper.getIdcomune();
	this.fkDettposizionedebitoriaid = fkDettposizionedebitoriaid;
    }

    public DettPosizioneDaAllineareId(String idcomune, Integer fkDettposizionedebitoriaid) {

	this();
	this.idcomune = idcomune;
	this.fkDettposizionedebitoriaid = fkDettposizionedebitoriaid;
    }

    @Column(name = "IDCOMUNE", nullable = false, length = 6)
    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    @Column(name = "FK_DETTPOSIZIONEDEBITORIAID", nullable = false, precision = 10, scale = 0)
    public Integer getFkDettposizionedebitoriaid() {

	return fkDettposizionedebitoriaid;
    }

    public void setFkDettposizionedebitoriaid(Integer fkDettposizionedebitoriaid) {

	this.fkDettposizionedebitoriaid = fkDettposizionedebitoriaid;
    }

    public boolean equals(Object other) {

	if ((this == other))
	    return true;
	if ((other == null))
	    return false;
	if (!(other instanceof DettPosizioneDaAllineareId))
	    return false;
	DettPosizioneDaAllineareId castOther = (DettPosizioneDaAllineareId) other;
	return ((this.getIdcomune() == castOther.getIdcomune())
		|| (this.getIdcomune() != null && castOther.getIdcomune() != null && this.getIdcomune().equals(castOther.getIdcomune())))
		&& ((this.getFkDettposizionedebitoriaid() == castOther.getFkDettposizionedebitoriaid())
			|| (this.getFkDettposizionedebitoriaid() != null && castOther.getFkDettposizionedebitoriaid() != null
				&& this.getFkDettposizionedebitoriaid().equals(castOther.getFkDettposizionedebitoriaid())));
    }

    public int hashCode() {

	int result = 17;
	result = 37 * result + (getIdcomune() == null ? 0 : this.getIdcomune().hashCode());
	result = 37 * result + (getFkDettposizionedebitoriaid() == null ? 0 : this.getFkDettposizionedebitoriaid().hashCode());
	return result;
    }
}
