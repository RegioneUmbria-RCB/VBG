package it.gruppoinit.pal.gp.core.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

@Embeddable
public class MovimentiMetadatiId implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8609454484921272578L;
    private String idcomune;
    private Integer codicemovimento;
    private String chiave;

    public MovimentiMetadatiId() {

	this.idcomune = ORMHelper.getIdcomune();
    }

    public MovimentiMetadatiId(Integer codicemovimento, String chiave) {

	this.idcomune = ORMHelper.getIdcomune();
	this.codicemovimento = codicemovimento;
	this.chiave = chiave;
    }

    public MovimentiMetadatiId(String idcomune, Integer codicemovimento, String chiave) {

	this.idcomune = idcomune;
	this.codicemovimento = codicemovimento;
	this.chiave = chiave;
    }

    @Column(name = "IDCOMUNE", nullable = false, length = 6)
    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    @Column(name = "CODICEMOVIMENTO", nullable = false, precision = 8, scale = 0)
    public Integer getCodicemovimento() {

	return codicemovimento;
    }

    public void setCodicemovimento(Integer codicemovimento) {

	this.codicemovimento = codicemovimento;
    }

    @Column(name = "CHIAVE", nullable = false, length = 60)
    public String getChiave() {

	return chiave;
    }

    public void setChiave(String chiave) {

	this.chiave = chiave;
    }

    public boolean equals(Object other) {

	if ((this == other))
	    return true;
	if ((other == null))
	    return false;
	if (!(other instanceof IstanzeMetadatiId))
	    return false;
	MovimentiMetadatiId castOther = (MovimentiMetadatiId) other;
	return ((this.getIdcomune() == castOther.getIdcomune())
		|| (this.getIdcomune() != null && castOther.getIdcomune() != null && this.getIdcomune().equals(castOther.getIdcomune())))
		&& ((this.getCodicemovimento() == castOther.getCodicemovimento()) || (this.getCodicemovimento() != null
			&& castOther.getCodicemovimento() != null && this.getCodicemovimento().equals(castOther.getCodicemovimento())))
		&& ((this.getChiave() == castOther.getChiave())
			|| (this.getChiave() != null && castOther.getChiave() != null && this.getChiave().equals(castOther.getChiave())));
    }

    public int hashCode() {

	int result = 17;
	result = 37 * result + (getIdcomune() == null ? 0 : this.getIdcomune().hashCode());
	result = 37 * result + (getCodicemovimento() == null ? 0 : this.getCodicemovimento().hashCode());
	result = 37 * result + (getChiave() == null ? 0 : this.getChiave().hashCode());
	return result;
    }
}
