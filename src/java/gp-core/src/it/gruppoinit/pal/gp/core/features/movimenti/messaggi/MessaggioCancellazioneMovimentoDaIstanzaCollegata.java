package it.gruppoinit.pal.gp.core.features.movimenti.messaggi;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.features.infrastructure.auditing.messages.MessaggioDiSistema;

public class MessaggioCancellazioneMovimentoDaIstanzaCollegata extends MessaggioDiSistema {

    private Istanze istanzaOrigine;
    private Istanze istanzaDestinazione;
    private Movimenti movimento;
    private String messaggio;

    public MessaggioCancellazioneMovimentoDaIstanzaCollegata(Istanze istanzaOrigine, Istanze istanzaDestinazione, Movimenti movimento,
	    String messaggio) {

	super();
	this.istanzaOrigine = istanzaOrigine;
	this.istanzaDestinazione = istanzaDestinazione;
	this.movimento = movimento;
	this.messaggio = messaggio;
    }

    @Override
    public String getTestoMessaggio() {

	StringBuilder ret = new StringBuilder();
	ret = ret.append("Non è stato possibile cancellare il movimento ") //
		.append(movimento.toString()) //
		.append(" dalla pratica ") //
		.append(istanzaDestinazione.toString()) // 
		.append(". \nLa richiesta è stata fatta dallo scollegamento dell'istanza ") //
		.append(istanzaOrigine.toString())//
		.append(" a causa di ").append(messaggio);
	return ret.toString();
    }
}
