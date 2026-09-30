package it.gruppoinit.pal.gp.core.domain;

import java.io.Serializable;

import javax.persistence.Column;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

public class TipicausalioneridettaglioId implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4209239052890989166L;
    private String idcomune;
    private Integer fkContiId;
    private Integer fkCausaliId;

    public TipicausalioneridettaglioId() {

	super();
	this.idcomune = ORMHelper.getIdcomune();
    }

    public TipicausalioneridettaglioId(Integer fkContiId, Integer fkCausaliId) {

	super();
	this.idcomune = ORMHelper.getIdcomune();
	this.fkContiId = fkContiId;
	this.fkCausaliId = fkCausaliId;
    }

    public TipicausalioneridettaglioId(String idcomune, Integer fkContiId, Integer fkCausaliId) {

	super();
	this.idcomune = idcomune;
	this.fkContiId = fkContiId;
	this.fkCausaliId = fkCausaliId;
    }

    @Column(name = "IDCOMUNE", nullable = false, length = 6)
    public String getIdcomune() {

	return idcomune;
    }

    @Column(name = "FKCONTO", nullable = false, precision = 6, scale = 0)
    public Integer getFkContiId() {

	return fkContiId;
    }

    @Column(name = "FKCAUSALE", nullable = false, precision = 10, scale = 0)
    public Integer getFkCausaliId() {

	return fkCausaliId;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    public void setFkContiId(Integer fkContiId) {

	this.fkContiId = fkContiId;
    }

    public void setFkCausaliId(Integer fkCausaliId) {

	this.fkCausaliId = fkCausaliId;
    }

    public boolean equals(Object other) {

	if ((this == other))
	    return true;
	if ((other == null))
	    return false;
	if (!(other instanceof BollCfgRuoliId))
	    return false;
	TipicausalioneridettaglioId castOther = (TipicausalioneridettaglioId) other;
	return ((this.getIdcomune() == castOther.getIdcomune())
		|| (this.getIdcomune() != null && castOther.getIdcomune() != null && this.getIdcomune().equals(castOther.getIdcomune())))
		&& ((this.getFkCausaliId() == castOther.getFkCausaliId()) || (this.getFkCausaliId() != null && castOther.getFkCausaliId() != null
			&& this.getFkCausaliId().equals(castOther.getFkCausaliId())))
		&& ((this.getFkContiId() == castOther.getFkContiId())
			|| (this.getFkContiId() != null && castOther.getFkContiId() != null && this.getFkContiId().equals(castOther.getFkContiId())));
    }

    public int hashCode() {

	int result = 17;
	result = 37 * result + (getIdcomune() == null ? 0 : this.getIdcomune().hashCode());
	result = 37 * result + (getFkCausaliId() == null ? 0 : this.getFkCausaliId().hashCode());
	result = 37 * result + (getFkContiId() == null ? 0 : this.getFkContiId().hashCode());
	return result;
    }
}
