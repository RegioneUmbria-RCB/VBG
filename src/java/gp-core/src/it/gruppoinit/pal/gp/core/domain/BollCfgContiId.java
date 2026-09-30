package it.gruppoinit.pal.gp.core.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

@Embeddable
public class BollCfgContiId implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8426786045140147369L;
    private String idcomune;
    private Integer fkBollcfgtipoId;
    private Integer fkContoId;

    public BollCfgContiId() {

	this.idcomune = ORMHelper.getIdcomune();
    }

    public BollCfgContiId(Integer fkBollcfgtipoId, Integer fkContoId) {

	this();
	this.idcomune = ORMHelper.getIdcomune();
	this.fkBollcfgtipoId = fkBollcfgtipoId;
	this.fkContoId = fkContoId;
    }

    public BollCfgContiId(String idcomune, Integer fkBollcfgtipoId, Integer fkContoId) {

	this();
	this.idcomune = idcomune;
	this.fkBollcfgtipoId = fkBollcfgtipoId;
	this.fkContoId = fkContoId;
    }

    @Column(name = "IDCOMUNE", nullable = false, length = 6)
    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    @Column(name = "FK_BOLLCFGTIPO_ID", nullable = false, length = 4)
    public Integer getFkBollcfgtipoId() {

	return fkBollcfgtipoId;
    }

    public void setFkBollcfgtipoId(Integer fkBollcfgtipoId) {

	this.fkBollcfgtipoId = fkBollcfgtipoId;
    }

    @Column(name = "FK_CONTO_ID", nullable = false, length = 6)
    public Integer getFkContoId() {

	return fkContoId;
    }

    public void setFkContoId(Integer fkContoId) {

	this.fkContoId = fkContoId;
    }

    public boolean equals(Object other) {

	if ((this == other))
	    return true;
	if ((other == null))
	    return false;
	if (!(other instanceof BollCfgContiId))
	    return false;
	BollCfgContiId castOther = (BollCfgContiId) other;
	return ((this.getIdcomune() == castOther.getIdcomune())
		|| (this.getIdcomune() != null && castOther.getIdcomune() != null && this.getIdcomune().equals(castOther.getIdcomune())))
		&& ((this.getFkBollcfgtipoId() == castOther.getFkBollcfgtipoId()) || (this.getFkBollcfgtipoId() != null
			&& castOther.getFkBollcfgtipoId() != null && this.getFkBollcfgtipoId().equals(castOther.getFkBollcfgtipoId())))
		&& ((this.getFkContoId() == castOther.getFkContoId())
			|| (this.getFkContoId() != null && castOther.getFkContoId() != null && this.getFkContoId().equals(castOther.getFkContoId())));
    }

    public int hashCode() {

	int result = 17;
	result = 37 * result + (getIdcomune() == null ? 0 : this.getIdcomune().hashCode());
	result = 37 * result + (getFkBollcfgtipoId() == null ? 0 : this.getFkBollcfgtipoId().hashCode());
	result = 37 * result + (getFkContoId() == null ? 0 : this.getFkContoId().hashCode());
	return result;
    }
}
