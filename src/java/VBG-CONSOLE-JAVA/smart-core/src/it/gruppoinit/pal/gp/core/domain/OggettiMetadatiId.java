package it.gruppoinit.pal.gp.core.domain;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class OggettiMetadatiId implements java.io.Serializable {

    private static final long serialVersionUID = 6801831908542818620L;
    private String idcomune;
    private Integer codiceoggetto;
    private String chiave;

    public OggettiMetadatiId() {

	this.idcomune = ORMHelper.getIdcomune();
    }

    public OggettiMetadatiId(String idcomune, Integer codiceoggetto, String chiave) {

	super();
	this.idcomune = idcomune;
	this.codiceoggetto = codiceoggetto;
	this.chiave = chiave;
    }

    public OggettiMetadatiId(Integer codiceoggetto, String chiave) {

	super();
	this.idcomune = ORMHelper.getIdcomune();
	this.codiceoggetto = codiceoggetto;
	this.chiave = chiave;
    }

    @Column(name = "IDCOMUNE", nullable = false, length = 6)
    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    @Column(name = "CODICEOGGETTO", nullable = false, precision = 10, scale = 0)
    public Integer getCodiceoggetto() {

	return codiceoggetto;
    }

    public void setCodiceoggetto(Integer codiceoggetto) {

	this.codiceoggetto = codiceoggetto;
    }

    @Column(name = "CHIAVE", nullable = false, length = 100)
    public String getChiave() {

	return chiave;
    }

    public void setChiave(String chiave) {

	this.chiave = chiave;
    }

    @Override
    public int hashCode() {

	final int prime = 37;
	int result = 1;
	result = prime * result + (getIdcomune() == null ? 0 : this.getIdcomune().hashCode());
	result = prime * result + (getChiave() == null ? 0 : this.getChiave().hashCode());
	result = prime * result + (getCodiceoggetto() == null ? 0 : this.getCodiceoggetto().hashCode());
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
	OggettiMetadatiId castOther = (OggettiMetadatiId) obj;
	return ((this.getIdcomune() == castOther.getIdcomune()) || (this.getIdcomune() != null && castOther.getIdcomune() != null && this
		.getIdcomune().equals(castOther.getIdcomune())))
		&& ((this.getChiave() == castOther.getChiave()) || (this.getChiave() != null && castOther.getChiave() != null && this.getChiave()
			.equals(castOther.getChiave())))
		&& ((this.getCodiceoggetto() == castOther.getCodiceoggetto()) || (this.getCodiceoggetto() != null
			&& castOther.getCodiceoggetto() != null && this.getCodiceoggetto().equals(castOther.getCodiceoggetto())));
    }
}
