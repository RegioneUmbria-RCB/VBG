package it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model;

import javax.xml.bind.annotation.XmlElement;

public class ConfigurazioneAutorizzazioniAppAmbulanti {

    @XmlElement(name = "attiva_stampa_pdf")
    private boolean attivaStampPdf;

    public boolean isAttivaStampPdf() {

	return attivaStampPdf;
    }

    public void setAttivaStampPdf(boolean attivaStampPdf) {

	this.attivaStampPdf = attivaStampPdf;
    }
}
