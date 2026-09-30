package it.gruppoinit.pal.gp.core.domain;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProtocolloSmistamentiId implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3370748509938880972L;
    private String idcomune;
    private String codice;

    public ProtocolloSmistamentiId() {

	this.idcomune = ORMHelper.getIdcomune();
    }

    public ProtocolloSmistamentiId(String codice) {

	this.idcomune = ORMHelper.getIdcomune();
	this.codice = codice;
    }

    @Column(name = "IDCOMUNE", nullable = false, length = 6)
    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    @Column(name = "CODICE", nullable = false, length = 30)
    public String getCodice() {

	return codice;
    }

    public void setCodice(String codice) {

	this.codice = codice;
    }

    public boolean equals(Object other) {

	if ((this == other))
	    return true;
	if ((other == null))
	    return false;
	if (!(other instanceof ProtocolloSmistamentiId))
	    return false;
	ProtocolloSmistamentiId castOther = (ProtocolloSmistamentiId) other;
	return (((this.getIdcomune() == castOther.getIdcomune()) || (this.getIdcomune() != null && castOther.getIdcomune() != null && this
		.getIdcomune().equals(castOther.getIdcomune()))) && ((this.getCodice() == castOther.getCodice()) || (this.getCodice() != null
		&& castOther.getCodice() != null && this.getCodice().equals(castOther.getCodice()))));
    }

    public int hashCode() {

	int result = 17;
	result = 37 * result + (getIdcomune() == null ? 0 : this.getIdcomune().hashCode());
	result = 37 * result + (getCodice() == null ? 0 : this.getCodice().hashCode());
	return result;
    }
}
