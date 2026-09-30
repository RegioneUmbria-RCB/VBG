package it.gruppoinit.pal.gp.core.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class VwIstanzeOpeRuoliId implements Serializable {

    private static final long serialVersionUID = 692619926147775164L;
    private String idcomune;
    private Integer idruolo;
    private Integer codiceresponsabile;
    private Integer codiceistanza;

    @Column(name = "IDCOMUNE", nullable = false, length = 6)
    public String getIdcomune() {

	return this.idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    @Column(name = "IDRUOLO", nullable = false, precision = 6, scale = 0)
    public Integer getIdruolo() {

	return idruolo;
    }

    public void setIdruolo(Integer idruolo) {

	this.idruolo = idruolo;
    }

    @Column(name = "CODICERESPONSABILE", nullable = false, precision = 6, scale = 0)
    public Integer getCodiceresponsabile() {

	return codiceresponsabile;
    }

    public void setCodiceresponsabile(Integer codiceresponsabile) {

	this.codiceresponsabile = codiceresponsabile;
    }

    @Column(name = "CODICEISTANZA", nullable = false, precision = 6, scale = 0)
    public Integer getCodiceistanza() {

	return codiceistanza;
    }

    public void setCodiceistanza(Integer codiceistanza) {

	this.codiceistanza = codiceistanza;
    }

    @Override
    public int hashCode() {

	int result = 1;
	result = 37 * result + (getCodiceistanza() == null ? 0 : this.getCodiceistanza().hashCode());
	result = 37 * result + (getCodiceresponsabile() == null ? 0 : this.getCodiceresponsabile().hashCode());
	result = 37 * result + (getIdcomune() == null ? 0 : this.getIdcomune().hashCode());
	result = 37 * result + (getIdruolo() == null ? 0 : this.getIdruolo().hashCode());
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
	VwIstanzeOpeRuoliId castOther = (VwIstanzeOpeRuoliId) obj;
	return ((this.getCodiceistanza() == castOther.getCodiceistanza()) || (this.getCodiceistanza() != null && castOther.getCodiceistanza() != null && this
		.getCodiceistanza().equals(castOther.getCodiceistanza())))
		&& ((this.getIdcomune() == castOther.getIdcomune()) || (this.getIdcomune() != null && castOther.getIdcomune() != null && this
			.getIdcomune().equals(castOther.getIdcomune())))
		&& ((this.getCodiceresponsabile() == castOther.getCodiceresponsabile()) || (this.getCodiceresponsabile() != null
			&& castOther.getCodiceresponsabile() != null && this.getCodiceresponsabile().equals(castOther.getCodiceresponsabile())))
		&& ((this.getIdruolo() == castOther.getIdruolo()) || (this.getIdruolo() != null && castOther.getIdruolo() != null && this
			.getIdruolo().equals(castOther.getIdruolo())));
    }
}
