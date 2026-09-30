package it.gruppoinit.pal.gp.core.features.infrastructure.layout.testi;

import org.apache.commons.lang.StringUtils;

public class LayoutTestiDTO {

    private String codiceTesto;
    private String software;
    private String testoBase;
    private String nuovoTesto;

    public LayoutTestiDTO() {

    }

    public String getCodiceTesto() {

	return codiceTesto;
    }

    public void setCodiceTesto(String codiceTesto) {

	this.codiceTesto = codiceTesto;
    }

    public String getSoftware() {

	return software;
    }

    public void setSoftware(String software) {

	this.software = software;
    }

    public String getTestoBase() {

	return testoBase;
    }

    public void setTestoBase(String testoBase) {

	this.testoBase = testoBase;
    }

    public String getNuovoTesto() {

	return nuovoTesto;
    }

    public void setNuovoTesto(String nuovoTesto) {

	this.nuovoTesto = nuovoTesto;
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + (StringUtils.isBlank(this.codiceTesto) ? 0 : this.codiceTesto.hashCode());
	result = prime * result + (StringUtils.isBlank(this.software) ? 0 : software.hashCode());
	result = prime * result + (StringUtils.isBlank(this.testoBase) ? 0 : testoBase.hashCode());
	result = prime * result + (StringUtils.isBlank(this.nuovoTesto) ? 0 : nuovoTesto.hashCode());
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
	LayoutTestiDTO other = (LayoutTestiDTO) obj;
	// codiceTesto
	if (StringUtils.isBlank(this.codiceTesto)) {
	    if (!StringUtils.isBlank(other.codiceTesto))
		return false;
	} else if (this.codiceTesto.compareTo(other.codiceTesto) != 0) {
	    return false;
	}
	// software
	if (StringUtils.isBlank(this.software)) {
	    if (!StringUtils.isBlank(other.software))
		return false;
	} else if (this.software.compareTo(other.software) != 0) {
	    return false;
	}
	// testoBase
	if (StringUtils.isBlank(this.testoBase)) {
	    if (!StringUtils.isBlank(other.testoBase))
		return false;
	} else if (this.testoBase.compareTo(other.testoBase) != 0) {
	    return false;
	}
	// nuovoTesto
	if (StringUtils.isBlank(this.nuovoTesto)) {
	    if (!StringUtils.isBlank(other.nuovoTesto))
		return false;
	} else if (this.nuovoTesto.compareTo(other.nuovoTesto) != 0) {
	    return false;
	}
	return true;
    }
}