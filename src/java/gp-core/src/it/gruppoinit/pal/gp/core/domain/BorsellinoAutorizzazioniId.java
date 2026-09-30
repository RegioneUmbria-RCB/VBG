package it.gruppoinit.pal.gp.core.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

@Embeddable
public class BorsellinoAutorizzazioniId implements java.io.Serializable {

    private static final long serialVersionUID = -7009341323565806354L;
    private String idcomune;
    private Integer fkIdBorsellino;
    private Integer fkIdAutorizzazioni;

    public BorsellinoAutorizzazioniId() {

	this.idcomune = ORMHelper.getIdcomune();
    }

    public BorsellinoAutorizzazioniId(String idcomune, Integer fkIdBorsellino) {

	this.idcomune = idcomune;
	this.fkIdBorsellino = fkIdBorsellino;
    }

    public BorsellinoAutorizzazioniId(String idcomune, Integer fkIdBorsellino, Integer fkIdAutorizzazioni) {

	this.idcomune = idcomune;
	this.fkIdBorsellino = fkIdBorsellino;
	this.fkIdAutorizzazioni = fkIdAutorizzazioni;
    }

    @Column(name = "IDCOMUNE", nullable = false, length = 6)
    public String getIdcomune() {

	return this.idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    @Column(name = "FKID_BORSELLINO", nullable = false)
    public Integer getFkIdBorsellino() {

	return fkIdBorsellino;
    }

    public void setFkIdBorsellino(Integer fkIdBorsellino) {

	this.fkIdBorsellino = fkIdBorsellino;
    }

    @Column(name = "FKID_AUTORIZZAZIONI", nullable = false)
    public Integer getFkIdAutorizzazioni() {

	return fkIdAutorizzazioni;
    }

    public void setFkIdAutorizzazioni(Integer fkIdAutorizzazioni) {

	this.fkIdAutorizzazioni = fkIdAutorizzazioni;
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((fkIdAutorizzazioni == null) ? 0 : fkIdAutorizzazioni.hashCode());
	result = prime * result + ((fkIdBorsellino == null) ? 0 : fkIdBorsellino.hashCode());
	result = prime * result + ((idcomune == null) ? 0 : idcomune.hashCode());
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
	BorsellinoAutorizzazioniId other = (BorsellinoAutorizzazioniId) obj;
	if (fkIdAutorizzazioni == null) {
	    if (other.fkIdAutorizzazioni != null)
		return false;
	} else if (!fkIdAutorizzazioni.equals(other.fkIdAutorizzazioni))
	    return false;
	if (fkIdBorsellino == null) {
	    if (other.fkIdBorsellino != null)
		return false;
	} else if (!fkIdBorsellino.equals(other.fkIdBorsellino))
	    return false;
	if (idcomune == null) {
	    if (other.idcomune != null)
		return false;
	} else if (!idcomune.equals(other.idcomune))
	    return false;
	return true;
    }

    @Override
    public String toString() {

	ToStringBuilder toStringBuilder = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
	toStringBuilder.append("idcomune", this.idcomune);
	toStringBuilder.append("fkIdBorsellino", this.fkIdBorsellino);
	toStringBuilder.append("fkIdAutorizzazioni", this.fkIdAutorizzazioni);
	return toStringBuilder.toString();
    }
}
