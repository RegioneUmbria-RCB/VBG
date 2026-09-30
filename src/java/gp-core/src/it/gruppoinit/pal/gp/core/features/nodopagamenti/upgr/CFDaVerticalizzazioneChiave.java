package it.gruppoinit.pal.gp.core.features.nodopagamenti.upgr;

public class CFDaVerticalizzazioneChiave {

    String idcomune;
    String software;
    String codiceComune;

    public CFDaVerticalizzazioneChiave() {

	super();
    }

    public CFDaVerticalizzazioneChiave(String idcomune, String software, String codiceComune) {

	super();
	this.idcomune = idcomune;
	this.software = software;
	this.codiceComune = codiceComune;
    }

    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    public String getSoftware() {

	return software;
    }

    public void setSoftware(String software) {

	this.software = software;
    }

    public String getCodiceComune() {

	return codiceComune;
    }

    public void setCodiceComune(String codiceComune) {

	this.codiceComune = codiceComune;
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((codiceComune == null) ? 0 : codiceComune.hashCode());
	result = prime * result + ((idcomune == null) ? 0 : idcomune.hashCode());
	result = prime * result + ((software == null) ? 0 : software.hashCode());
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
	CFDaVerticalizzazioneChiave other = (CFDaVerticalizzazioneChiave) obj;
	if (codiceComune == null) {
	    if (other.codiceComune != null)
		return false;
	} else if (!codiceComune.equals(other.codiceComune))
	    return false;
	if (idcomune == null) {
	    if (other.idcomune != null)
		return false;
	} else if (!idcomune.equals(other.idcomune))
	    return false;
	if (software == null) {
	    if (other.software != null)
		return false;
	} else if (!software.equals(other.software))
	    return false;
	return true;
    }
}
