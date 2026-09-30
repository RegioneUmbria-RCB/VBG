package it.alveo.ricalcoloaree.entities.composefields;

import java.io.Serializable;

public class ResponsabiliPK implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String idcomune;
    private String codiceresponsabile;

    // Costruttore vuoto
    public ResponsabiliPK() {

    }

    // Costruttore con parametri
    public ResponsabiliPK(String idcomune, String codiceresponsabile) {

	this.idcomune = idcomune;
	this.codiceresponsabile = codiceresponsabile;
    }

    // Getters e Setters
    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    public String getCodiceresponsabile() {

	return codiceresponsabile;
    }

    public void setCodiceresponsabile(String codiceresponsabile) {

	this.codiceresponsabile = codiceresponsabile;
    }

    // Override equals() e hashCode() per chiave primaria composta
    @Override
    public boolean equals(Object o) {

	if (this == o)
	    return true;
	if (o == null || getClass() != o.getClass())
	    return false;
	ResponsabiliPK that = (ResponsabiliPK) o;
	return idcomune.equals(that.idcomune) && codiceresponsabile.equals(that.codiceresponsabile);
    }

    @Override
    public int hashCode() {

	return 31 * idcomune.hashCode() + codiceresponsabile.hashCode();
    }
}
