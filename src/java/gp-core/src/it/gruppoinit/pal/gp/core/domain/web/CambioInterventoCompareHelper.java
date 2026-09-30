package it.gruppoinit.pal.gp.core.domain.web;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;

public class CambioInterventoCompareHelper extends CodiceDescrizioneBean {

    private CambioInterventoComparePropertiesHelper attuale = new CambioInterventoComparePropertiesHelper();
    private CambioInterventoComparePropertiesHelper nuovo = new CambioInterventoComparePropertiesHelper();
    private boolean documentoRichiesto;

    public CambioInterventoComparePropertiesHelper getAttuale() {

	return attuale;
    }

    public void setAttuale(CambioInterventoComparePropertiesHelper attuale) {

	this.attuale = attuale;
    }

    public CambioInterventoComparePropertiesHelper getNuovo() {

	return nuovo;
    }

    public void setNuovo(CambioInterventoComparePropertiesHelper nuovo) {

	this.nuovo = nuovo;
    }

    public boolean isDocumentoRichiesto() {

	return documentoRichiesto;
    }

    public void setDocumentoRichiesto(boolean documentoRichiesto) {

	this.documentoRichiesto = documentoRichiesto;
    }

    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
