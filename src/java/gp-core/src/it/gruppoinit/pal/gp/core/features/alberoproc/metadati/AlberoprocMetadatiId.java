package it.gruppoinit.pal.gp.core.features.alberoproc.metadati;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.AlberoprocLimitiId;

@Embeddable
public class AlberoprocMetadatiId implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 226327017523902180L;
    private String idcomune;
    private Integer fkScid;
    private String chiave;

    public AlberoprocMetadatiId() {

	this.idcomune = ORMHelper.getIdcomune();
    }

    public AlberoprocMetadatiId(String idcomune, Integer fkScId, String chiave) {

	super();
	this.idcomune = idcomune;
	this.fkScid = fkScId;
	this.setChiave(chiave);
    }

    @Column(name = "IDCOMUNE", nullable = false, length = 6)
    public String getIdcomune() {

	return this.idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    @Column(name = "FK_SCID ", nullable = false, precision = 10, scale = 0)
    public Integer getFkScid() {

	return fkScid;
    }

    public void setFkScid(Integer fkScid) {

	this.fkScid = fkScid;
    }

    @Column(name = "CHIAVE", nullable = false, length = 100)
    public String getChiave() {

	return chiave;
    }

    public void setChiave(String chiave) {

	if (!StringUtils.isEmpty(chiave)) {
	    this.chiave = chiave.toUpperCase();
	} else {
	    this.chiave = null;
	}
    }

    @Override
    public int hashCode() {

	int result = 17;
	result = 37 * result + (getIdcomune() == null ? 0 : this.getIdcomune().hashCode());
	result = 37 * result + (getFkScid() == null ? 0 : this.getFkScid().hashCode());
	result = 37 * result + (getChiave() == null ? 0 : this.getChiave().hashCode());
	return result;
    }

    @Override
    public boolean equals(Object other) {

	if ((this == other))
	    return true;
	if ((other == null))
	    return false;
	if (!(other instanceof AlberoprocLimitiId))
	    return false;
	AlberoprocMetadatiId castOther = (AlberoprocMetadatiId) other;
	return ((this.getIdcomune() == castOther.getIdcomune())
		|| (this.getIdcomune() != null && castOther.getIdcomune() != null && this.getIdcomune().equals(castOther.getIdcomune())))
		&& ((this.getFkScid() == castOther.getFkScid())
			|| (this.getFkScid() != null && castOther.getFkScid() != null && this.getFkScid().equals(castOther.getFkScid())))
		&& ((this.getChiave() == castOther.getChiave())
			|| (this.getChiave() != null && castOther.getChiave() != null && this.getChiave().equals(castOther.getChiave())));
    }
}
