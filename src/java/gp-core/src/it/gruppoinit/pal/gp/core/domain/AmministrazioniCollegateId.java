package it.gruppoinit.pal.gp.core.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

@Embeddable
public class AmministrazioniCollegateId implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4620855263774695733L;
    private Integer codiceamministrazione;
    private String idcomune;
    private String codicecomune;

    public AmministrazioniCollegateId(Integer codiceamministrazione, String codicecomune) {

	this();
	this.codiceamministrazione = codiceamministrazione;
	this.codicecomune = codicecomune;
    }

    public AmministrazioniCollegateId() {

	this.idcomune = ORMHelper.getIdcomune();
    }

    @Column(name = "CODICEAMMINISTRAZIONE", precision = 10, scale = 0)
    public Integer getCodiceamministrazione() {

	return codiceamministrazione;
    }

    public void setCodiceamministrazione(Integer codiceamministrazione) {

	this.codiceamministrazione = codiceamministrazione;
    }

    @Column(name = "IDCOMUNE", nullable = false, length = 6)
    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    @Column(name = "CODICECOMUNE", nullable = false, length = 5)
    public String getCodicecomune() {

	return codicecomune;
    }

    public void setCodicecomune(String codicecomune) {

	this.codicecomune = codicecomune;
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((codiceamministrazione == null) ? 0 : codiceamministrazione.hashCode());
	result = prime * result + ((codicecomune == null) ? 0 : codicecomune.hashCode());
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
	AmministrazioniCollegateId other = (AmministrazioniCollegateId) obj;
	if (codiceamministrazione == null) {
	    if (other.codiceamministrazione != null)
		return false;
	} else if (!codiceamministrazione.equals(other.codiceamministrazione))
	    return false;
	if (codicecomune == null) {
	    if (other.codicecomune != null)
		return false;
	} else if (!codicecomune.equals(other.codicecomune))
	    return false;
	if (idcomune == null) {
	    if (other.idcomune != null)
		return false;
	} else if (!idcomune.equals(other.idcomune))
	    return false;
	return true;
    }
}
