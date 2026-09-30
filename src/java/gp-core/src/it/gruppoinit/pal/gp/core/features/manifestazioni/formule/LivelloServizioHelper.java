package it.gruppoinit.pal.gp.core.features.manifestazioni.formule;

import org.apache.commons.lang.StringUtils;

public class LivelloServizioHelper {

    private Integer codice;
    private String segnaposto;
    private String descrizione;

    public LivelloServizioHelper(Integer codice, String segnaposto, String descrizione) {

	super();
	if (StringUtils.isBlank(segnaposto)) {
	    return;
	}
	this.codice = codice;
	this.segnaposto = segnaposto.startsWith("[") ? segnaposto.substring(1) : segnaposto;
	this.segnaposto = this.segnaposto.endsWith("]") ? this.segnaposto.substring(0, this.segnaposto.length() - 1) : this.segnaposto;
	this.descrizione = descrizione;
    }

    public Integer getCodice() {

	return codice;
    }

    public String getSegnaposto() {

	return segnaposto;
    }

    public String getDescrizione() {

	return descrizione;
    }
}
