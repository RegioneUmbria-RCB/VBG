package it.gruppoinit.pal.gp.core.features.segnaposto.legacy.shared;

import org.apache.commons.lang.StringUtils;

public class IndirizzoDestinatario {

    private String nominativo;
    private String ufficio;
    private String indirizzo;
    private String cap;
    private String citta;
    private String provincia;

    public String getNominativo() {

	return nominativo;
    }

    public void setNominativo(String nominativo) {

	this.nominativo = nominativo;
    }

    public String getIndirizzo() {

	return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {

	this.indirizzo = indirizzo;
    }

    public String getCap() {

	return cap;
    }

    public void setCap(String cap) {

	this.cap = cap;
    }

    public String getCitta() {

	return citta;
    }

    public void setCitta(String citta) {

	this.citta = citta;
    }

    public String getProvincia() {

	return provincia;
    }

    public void setProvincia(String provincia) {

	this.provincia = provincia;
    }

    String getUfficio() {

	return ufficio;
    }

    public void setUfficio(String ufficio) {

	this.ufficio = ufficio;
    }

    public StringBuilder buildIndirizzo(String separator) {

	StringBuilder sb = new StringBuilder();
	boolean lastRow = false;
	if (StringUtils.isNotBlank(this.getNominativo())) {
	    sb.append(this.getNominativo()).append(" ").append(separator);
	}
	if (StringUtils.isNotBlank(this.getIndirizzo())) {
	    sb.append(this.getIndirizzo().trim()).append(" ").append(separator);
	}
	//	boolean lastRow = false;
	if (StringUtils.isNotBlank(this.getCap())) {
	    sb.append(this.getCap().trim()).append(" ");
	    lastRow = true;
	}
	if (StringUtils.isNotBlank(this.getCitta())) {
	    sb.append(this.getCitta().trim()).append(" ");
	    lastRow = true;
	}
	if (StringUtils.isNotBlank(this.getProvincia())) {
	    sb.append("(").append(this.getProvincia().trim()).append(") ");
	    lastRow = true;
	}
	if (lastRow) {
	    sb.append(separator);
	}
	return sb;
    }
}
