package it.gruppoinit.pal.gp.core.domain;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProtocolloModalitainvioId implements Serializable {

    private static final long serialVersionUID = 7903787247716018305L;
    private String idcomune;
    private String codice;

    public ProtocolloModalitainvioId() {

	this.idcomune = ORMHelper.getIdcomune();
    }

    public ProtocolloModalitainvioId(String codice) {

	this();
	this.idcomune = ORMHelper.getIdcomune();
	this.codice = codice;
    }

    @Column(name = "IDCOMUNE", nullable = false, length = 6)
    public String getIdcomune() {

	return this.idcomune;
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

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((getCodice() == null) ? 0 : getCodice().hashCode());
	result = prime * result + ((getIdcomune() == null) ? 0 : getIdcomune().hashCode());
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
	ProtocolloModalitainvioId other = (ProtocolloModalitainvioId) obj;
	if (codice == null) {
	    if (other.codice != null)
		return false;
	} else if (!codice.equals(other.codice))
	    return false;
	if (idcomune == null) {
	    if (other.idcomune != null)
		return false;
	} else if (!idcomune.equals(other.idcomune))
	    return false;
	return true;
    }
}
