package it.gruppoinit.pal.gp.core.domain;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ResponsabiliTmScaId implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2718378955985562809L;
    private String idcomune;
    private Integer codiceresponsabile;
    private String tipomovimento;

    public ResponsabiliTmScaId() {

	this.idcomune = ORMHelper.getIdcomune();
    }

    public ResponsabiliTmScaId(Integer codiceresponsabile, String tipomovimento) {

	this.idcomune = ORMHelper.getIdcomune();
	this.codiceresponsabile = codiceresponsabile;
	this.tipomovimento = tipomovimento;
    }

    @Column(name = "IDCOMUNE", length = 6)
    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    @Column(name = "CODICERESPONSABILE", precision = 4, scale = 0)
    public Integer getCodiceresponsabile() {

	return codiceresponsabile;
    }

    public void setCodiceresponsabile(Integer codiceresponsabile) {

	this.codiceresponsabile = codiceresponsabile;
    }

    @Column(name = "TIPOMOVIMENTO", length = 8)
    public String getTipomovimento() {

	return tipomovimento;
    }

    public void setTipomovimento(String tipomovimento) {

	this.tipomovimento = tipomovimento;
    }

    @Override
    public int hashCode() {

	int result = 2;
	result = 38 * result + (getIdcomune() == null ? 0 : this.getIdcomune().hashCode());
	result = 38 * result + (getCodiceresponsabile() == null ? 0 : this.getCodiceresponsabile().hashCode());
	result = 38 * result + (getTipomovimento() == null ? 0 : this.getTipomovimento().hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {

	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	ResponsabiliTmAvvId castOther = (ResponsabiliTmAvvId) obj;
	return ((this.getIdcomune() == castOther.getIdcomune()) || (this.getIdcomune() != null && castOther.getIdcomune() != null && this
		.getIdcomune().equals(castOther.getIdcomune())))
		&& ((this.getCodiceresponsabile() == castOther.getCodiceresponsabile()) || (this.getCodiceresponsabile() != null
			&& castOther.getCodiceresponsabile() != null && this.getCodiceresponsabile().equals(castOther.getCodiceresponsabile())))
		&& ((this.getTipomovimento() == castOther.getTipomovimento()) || (this.getTipomovimento() != null
			&& castOther.getTipomovimento() != null && this.getTipomovimento().equals(castOther.getTipomovimento())));
    }
}
