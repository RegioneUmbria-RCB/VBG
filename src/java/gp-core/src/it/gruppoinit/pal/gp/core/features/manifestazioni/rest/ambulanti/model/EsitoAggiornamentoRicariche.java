package it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EsitoAggiornamentoRicariche extends EsitoOperazioneAggiornamento {

    @XmlElement(name = "comuni_non_aggiornati")
    private List<String> codiciComuniNonAggiornati;

    protected EsitoAggiornamentoRicariche() {

	super();
    }

    public EsitoAggiornamentoRicariche(boolean esito, String messaggio, List<String> codiciComuniNonAggiornati) {

	super(esito, messaggio);
	this.codiciComuniNonAggiornati = codiciComuniNonAggiornati;
    }

    public List<String> getCodiciComuniNonAggiornati() {

	if (this.codiciComuniNonAggiornati == null) {
	    this.codiciComuniNonAggiornati = new ArrayList<String>();
	}
	return codiciComuniNonAggiornati;
    }

    public void setCodiciComuniNonAggiornati(List<String> codiciComuniNonAggiornati) {

	this.codiciComuniNonAggiornati = codiciComuniNonAggiornati;
    }
}
