package it.gruppoinit.pal.gp.core.domain;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class BollCfgMercatiId implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7929642507927539431L;
    private String idcomune;
    private Integer fk_bollcfgtipo_id;
    private Integer fk_codicemercato;

    public BollCfgMercatiId() {

	super();
	this.idcomune = ORMHelper.getIdcomune();
    }

    public BollCfgMercatiId(int fk_bollcfgtipo_id, int fk_codicemercato) {

	this();
	this.fk_bollcfgtipo_id = fk_bollcfgtipo_id;
	this.fk_codicemercato = fk_codicemercato;
    }

    public BollCfgMercatiId(String idcomune, int fk_bollcfgtipo_id, int fk_codicemercato) {

	this();
	this.idcomune = idcomune;
	this.fk_bollcfgtipo_id = fk_bollcfgtipo_id;
	this.fk_codicemercato = fk_codicemercato;
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

    @Column(name = "FK_CODICEMERCATO", nullable = false, length = 4)
    public Integer getFk_codicemercato() {

	return fk_codicemercato;
    }

    public void setFk_codicemercato(Integer fk_codicemercato) {

	this.fk_codicemercato = fk_codicemercato;
    }

    public boolean equals(Object other) {

	if ((this == other))
	    return true;
	if ((other == null))
	    return false;
	if (!(other instanceof BollCfgMercatiId))
	    return false;
	BollCfgMercatiId castOther = (BollCfgMercatiId) other;
	return ((this.getIdcomune() == castOther.getIdcomune()) || (this.getIdcomune() != null && castOther.getIdcomune() != null && this
		.getIdcomune().equals(castOther.getIdcomune())))
		&& ((this.getFk_bollcfgtipo_id() == castOther.getFk_bollcfgtipo_id()) || (this.getFk_bollcfgtipo_id() != null
			&& castOther.getFk_bollcfgtipo_id() != null && this.getFk_bollcfgtipo_id().equals(castOther.getFk_bollcfgtipo_id())))
		&& ((this.getFk_codicemercato() == castOther.getFk_codicemercato()) || (this.getFk_codicemercato() != null
			&& castOther.getFk_codicemercato() != null && this.getFk_codicemercato().equals(castOther.getFk_codicemercato())));
    }

    public int hashCode() {

	int result = 17;
	result = 37 * result + (getIdcomune() == null ? 0 : this.getIdcomune().hashCode());
	result = 37 * result + (getFk_bollcfgtipo_id() == null ? 0 : this.getFk_bollcfgtipo_id().hashCode());
	result = 37 * result + (getFk_codicemercato() == null ? 0 : this.getFk_codicemercato().hashCode());
	return result;
    }
}
