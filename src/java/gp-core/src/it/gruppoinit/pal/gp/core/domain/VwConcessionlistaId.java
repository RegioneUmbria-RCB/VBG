package it.gruppoinit.pal.gp.core.domain;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class VwConcessionlistaId implements Serializable {

    private static final long serialVersionUID = 117599779020975442L;
    private String idcomune;
    private Integer concId;
    private Integer progressivo;

    public VwConcessionlistaId() {

	super();
	this.idcomune = ORMHelper.getIdcomune();
    }

    @Column(name = "IDCOMUNE", nullable = false, length = 6)
    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    @Column(name = "CONC_ID", nullable = false, precision = 6, scale = 0)
    public Integer getConcId() {

	return concId;
    }

    public void setConcId(Integer concId) {

	this.concId = concId;
    }

    @Column(name = "PROGRESSIVO", nullable = false, precision = 10, scale = 0)
    public Integer getProgressivo() {

	return progressivo;
    }

    public void setProgressivo(Integer progressivo) {

	this.progressivo = progressivo;
    }

    @Override
    public int hashCode() {

	int result = 1;
	result = 37 * result + (getConcId() == null ? 0 : this.getConcId().hashCode());
	result = 37 * result + (getIdcomune() == null ? 0 : this.getIdcomune().hashCode());
	result = 37 * result + (getProgressivo() == null ? 0 : this.getProgressivo().hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {

	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	VwConcessionlistaId castOther = (VwConcessionlistaId) obj;
	return ((this.getIdcomune() == castOther.getIdcomune()) || (this.getIdcomune() != null && castOther.getIdcomune() != null && this
		.getIdcomune().equals(castOther.getIdcomune())))
		&& ((this.getConcId() == castOther.getConcId()) || (this.getConcId() != null && castOther.getConcId() != null && this.getConcId()
			.equals(castOther.getConcId())))
		&& ((this.getProgressivo() == castOther.getProgressivo()) || (this.getProgressivo() != null && castOther.getProgressivo() != null && this
			.getProgressivo().equals(castOther.getProgressivo())));
    }
}
