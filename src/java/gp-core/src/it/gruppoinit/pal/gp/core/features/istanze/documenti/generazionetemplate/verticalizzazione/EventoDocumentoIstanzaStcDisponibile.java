package it.gruppoinit.pal.gp.core.features.istanze.documenti.generazionetemplate.verticalizzazione;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoDocumentoIstanzaStcDisponibile implements IEvent {

    private Integer codiceIStanza;

    public EventoDocumentoIstanzaStcDisponibile(Integer codiceIstanza) {

	super();
	this.setCodiceIStanza(codiceIstanza);
    }

    public Integer getCodiceIStanza() {

	return codiceIStanza;
    }

    public void setCodiceIStanza(Integer codiceIStanza) {

	this.codiceIStanza = codiceIStanza;
    }
}
