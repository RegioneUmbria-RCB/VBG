package it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EsitoAggiornamentoBorsellino extends EsitoOperazioneAggiornamento {

    protected EsitoAggiornamentoBorsellino() {

	super();
    }

    public EsitoAggiornamentoBorsellino(boolean esito, String messaggio) {

	super(esito, messaggio);
    }
}
