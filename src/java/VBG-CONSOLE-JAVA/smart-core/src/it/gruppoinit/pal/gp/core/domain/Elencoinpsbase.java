package it.gruppoinit.pal.gp.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.NotEmpty;

@Entity
@Table(name = "ELENCOINPSBASE")
public class Elencoinpsbase {

    private String codice;
    private String descrizione;

    public Elencoinpsbase() {

	super();
    }

    @Id
    @Column(name = "CODICE", unique = true, nullable = false, length = 6)
    public String getCodice() {

	return codice;
    }

    public void setCodice(String codice) {

	this.codice = codice;
    }

    @NotEmpty
    @Column(name = "DESCRIZIONE", length = 50)
    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    @Transient
    public String getDescrizioneEstesa() {

	String result = "";
	if (StringUtils.isNotBlank(getDescrizione())) {
	    result += getDescrizione();
	}
	if (StringUtils.isNotBlank(getCodice())) {
	    result += " (" + getCodice() + ")";
	}
	return result;
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((codice == null) ? 0 : codice.hashCode());
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
	Elencoinpsbase other = (Elencoinpsbase) obj;
	if (codice == null) {
	    if (other.codice != null) {
		return false;
	    }
	} else if (!codice.equals(other.codice)) {
	    return false;
	}
	return true;
    }
}
