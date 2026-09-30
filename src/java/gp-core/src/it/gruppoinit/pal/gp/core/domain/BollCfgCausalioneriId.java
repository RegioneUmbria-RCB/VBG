package it.gruppoinit.pal.gp.core.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

@Embeddable
public class BollCfgCausalioneriId implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8780629303828666568L;
    private String idcomune;
    private Integer fk_bollcfgtipo_id;
    private Integer fkCoId;

    public BollCfgCausalioneriId() {

	super();
	this.idcomune = ORMHelper.getIdcomune();
    }

    public BollCfgCausalioneriId(Integer fk_bollcfgtipo_id, Integer fkCoId) {

	this();
	this.fk_bollcfgtipo_id = fk_bollcfgtipo_id;
	this.fkCoId = fkCoId;
    }

    public BollCfgCausalioneriId(String idcomune, Integer fk_bollcfgtipo_id, Integer fkCoId) {

	this();
	this.idcomune = idcomune;
	this.fk_bollcfgtipo_id = fk_bollcfgtipo_id;
	this.fkCoId = fkCoId;
    }

    @Column(name = "IDCOMUNE", nullable = false, length = 6)
    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    @Column(name = "FK_BOLLCFGTIPO_ID", nullable = false, length = 4)
    public Integer getFk_bollcfgtipo_id() {

	return fk_bollcfgtipo_id;
    }

    public void setFk_bollcfgtipo_id(Integer fk_bollcfgtipo_id) {

	this.fk_bollcfgtipo_id = fk_bollcfgtipo_id;
    }

    @Column(name = "FK_CO_ID", nullable = false, length = 10)
    public Integer getFkCoId() {

	return fkCoId;
    }

    public void setFkCoId(Integer fkCoId) {

	this.fkCoId = fkCoId;
    }

    public boolean equals(Object other) {

	if ((this == other))
	    return true;
	if ((other == null))
	    return false;
	if (!(other instanceof BollCfgCausalioneriId))
	    return false;
	BollCfgCausalioneriId castOther = (BollCfgCausalioneriId) other;
	return ((this.getIdcomune() == castOther.getIdcomune())
		|| (this.getIdcomune() != null && castOther.getIdcomune() != null && this.getIdcomune().equals(castOther.getIdcomune())))
		&& ((this.getFk_bollcfgtipo_id() == castOther.getFk_bollcfgtipo_id()) || (this.getFk_bollcfgtipo_id() != null
			&& castOther.getFk_bollcfgtipo_id() != null && this.getFk_bollcfgtipo_id().equals(castOther.getFk_bollcfgtipo_id())))
		&& ((this.getFkCoId() == castOther.getFkCoId())
			|| (this.getFkCoId() != null && castOther.getFkCoId() != null && this.getFkCoId().equals(castOther.getFkCoId())));
    }

    public int hashCode() {

	int result = 17;
	result = 37 * result + (getIdcomune() == null ? 0 : this.getIdcomune().hashCode());
	result = 37 * result + (getFk_bollcfgtipo_id() == null ? 0 : this.getFk_bollcfgtipo_id().hashCode());
	result = 37 * result + (getFkCoId() == null ? 0 : this.getFkCoId().hashCode());
	return result;
    }
}
