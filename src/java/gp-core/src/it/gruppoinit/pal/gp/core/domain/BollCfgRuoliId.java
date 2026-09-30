package it.gruppoinit.pal.gp.core.domain;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class BollCfgRuoliId implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 9117615742115948198L;
    private String idcomune;
    private Integer fkBollcfgtipoId;
    private Integer fkRuoliId;

    public BollCfgRuoliId() {

	super();
	this.idcomune = ORMHelper.getIdcomune();
    }

    public BollCfgRuoliId(Integer fkBollcfgtipoId, Integer fkRuoliId) {

	this();
	this.fkBollcfgtipoId = fkBollcfgtipoId;
	this.fkRuoliId = fkRuoliId;
    }

    public BollCfgRuoliId(String idcomune, Integer fkBollcfgtipoId, Integer fkRuoliId) {

	this();
	this.idcomune = idcomune;
	this.fkBollcfgtipoId = fkBollcfgtipoId;
	this.fkRuoliId = fkRuoliId;
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

    @Column(name = "FK_RUOLI_ID", nullable = false, length = 4)
    public Integer getFkRuoliId() {

	return fkRuoliId;
    }

    public void setFkRuoliId(Integer fkRuoliId) {

	this.fkRuoliId = fkRuoliId;
    }

    public boolean equals(Object other) {

	if ((this == other))
	    return true;
	if ((other == null))
	    return false;
	if (!(other instanceof BollCfgRuoliId))
	    return false;
	BollCfgRuoliId castOther = (BollCfgRuoliId) other;
	return ((this.getIdcomune() == castOther.getIdcomune()) || (this.getIdcomune() != null && castOther.getIdcomune() != null && this
		.getIdcomune().equals(castOther.getIdcomune())))
		&& ((this.getFkBollcfgtipoId() == castOther.getFkBollcfgtipoId()) || (this.getFkBollcfgtipoId() != null
			&& castOther.getFkBollcfgtipoId() != null && this.getFkBollcfgtipoId().equals(castOther.getFkBollcfgtipoId())))
		&& ((this.getFkRuoliId() == castOther.getFkRuoliId()) || (this.getFkRuoliId() != null && castOther.getFkRuoliId() != null && this
			.getFkRuoliId().equals(castOther.getFkRuoliId())));
    }

    public int hashCode() {

	int result = 17;
	result = 37 * result + (getIdcomune() == null ? 0 : this.getIdcomune().hashCode());
	result = 37 * result + (getFkBollcfgtipoId() == null ? 0 : this.getFkBollcfgtipoId().hashCode());
	result = 37 * result + (getFkRuoliId() == null ? 0 : this.getFkRuoliId().hashCode());
	return result;
    }
}
