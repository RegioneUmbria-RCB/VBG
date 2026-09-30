package it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.rest;

import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang.StringUtils;

public class FirmatarioResponse {

    @XmlElement(name = "codice")
    private String codice;
    @XmlElement(name = "descrizione")
    private String descrizione;

    public String getCodice() {

	return codice;
    }

    public void setCodice(String codice) {

	this.codice = codice;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((StringUtils.isBlank(this.codice)) ? 0 : this.codice.hashCode());
	result = prime * result + ((StringUtils.isBlank(this.descrizione)) ? 0 : this.descrizione.hashCode());
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
	FirmatarioResponse other = (FirmatarioResponse) obj;
	//codice
	if (StringUtils.isBlank(this.codice)) {
	    if (!StringUtils.isBlank(other.codice)) {
		return false;
	    }
	} else if (this.codice.compareToIgnoreCase(other.codice) != 0) {
	    return false;
	}
	//descrizione
	if (StringUtils.isBlank(this.descrizione)) {
	    if (!StringUtils.isBlank(other.descrizione)) {
		return false;
	    }
	} else if (this.descrizione.compareToIgnoreCase(other.descrizione) != 0) {
	    return false;
	}
	return true;
    }
}
