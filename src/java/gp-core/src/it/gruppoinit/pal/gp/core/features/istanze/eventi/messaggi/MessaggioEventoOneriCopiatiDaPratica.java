package it.gruppoinit.pal.gp.core.features.istanze.eventi.messaggi;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.opensaml.artifact.InvalidArgumentException;

import it.gruppoinit.pal.gp.core.features.infrastructure.auditing.messages.MessaggioDiSistema;

public class MessaggioEventoOneriCopiatiDaPratica extends MessaggioDiSistema {

    public class Inner {

	public String test;
    }

    private String numeroIstanzaOrigine;
    private String numeroIstanzaDestinazione;
    private List<CausaleImportoOnerePerMessaggio> oneriCopiati;

    public MessaggioEventoOneriCopiatiDaPratica(String numeroIstanzaOrigine, String numeroIstanzaDestinazione,
	    List<CausaleImportoOnerePerMessaggio> oneriCopiati) {

	if (StringUtils.isEmpty(numeroIstanzaOrigine)) {
	    throw new InvalidArgumentException("Numero di istanza origine non impostato");
	}
	if (StringUtils.isEmpty(numeroIstanzaDestinazione)) {
	    throw new InvalidArgumentException("Numero di istanza destinazione non impostato");
	}
	if (oneriCopiati == null || oneriCopiati.isEmpty()) {
	    throw new InvalidArgumentException("Oneri copiati da un'istanza all'altra non impostati");
	}
	this.numeroIstanzaOrigine = numeroIstanzaOrigine;
	this.numeroIstanzaDestinazione = numeroIstanzaDestinazione;
	this.oneriCopiati = oneriCopiati;
    }

    @Override
    public String getTestoMessaggio() {

	StringBuilder messaggio = new StringBuilder();
	messaggio.append("Oneri non pagati copiati dalla pratica ").append(numeroIstanzaOrigine).append(" alla pratica ")
		.append(numeroIstanzaDestinazione).append(":");
	for (CausaleImportoOnerePerMessaggio onere : oneriCopiati) {
	    messaggio.append("\r\n").append(onere);
	}
	return messaggio.toString();
    }
}
