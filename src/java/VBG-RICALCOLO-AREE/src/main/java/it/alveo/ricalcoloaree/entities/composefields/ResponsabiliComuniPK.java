package it.alveo.ricalcoloaree.entities.composefields;

import java.io.Serializable;

public class ResponsabiliComuniPK implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1880860485964928520L;
    private String codiceresponsabile;
    private String codicecomune;
    private String idcomune;

    // Costruttore di default
    public ResponsabiliComuniPK() {

    }

    // Costruttore con parametri
    public ResponsabiliComuniPK(String codiceresponsabile, String codicecomune, String idcomune) {

	this.codiceresponsabile = codiceresponsabile;
	this.codicecomune = codicecomune;
	this.idcomune = idcomune;
    }

    // Getters e setters
    public String getCodiceresponsabile() {

	return codiceresponsabile;
    }

    public void setCodiceresponsabile(String codiceresponsabile) {

	this.codiceresponsabile = codiceresponsabile;
    }

    public String getCodicecomune() {

	return codicecomune;
    }

    public void setCodicecomune(String codicecomune) {

	this.codicecomune = codicecomune;
    }

    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    // Override equals e hashCode (fondamentale per le chiavi primarie composite)
    @Override
    public boolean equals(Object o) {

	if (this == o)
	    return true;
	if (o == null || getClass() != o.getClass())
	    return false;
	ResponsabiliComuniPK that = (ResponsabiliComuniPK) o;
	return codiceresponsabile.equals(that.codiceresponsabile) && codicecomune.equals(that.codicecomune) && idcomune.equals(that.idcomune);
    }

    @Override
    public int hashCode() {

	return 31 * codiceresponsabile.hashCode() + codicecomune.hashCode() + idcomune.hashCode();
    }
}
