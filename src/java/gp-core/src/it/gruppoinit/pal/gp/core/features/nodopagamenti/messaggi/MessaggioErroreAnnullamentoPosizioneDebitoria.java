package it.gruppoinit.pal.gp.core.features.nodopagamenti.messaggi;

import it.gruppoinit.pal.gp.core.domain.DettPosizioneDebitoria;
import it.gruppoinit.pal.gp.core.domain.Istanzeoneri;
import it.gruppoinit.pal.gp.core.features.infrastructure.auditing.messages.MessaggioDiSistema;

public class MessaggioErroreAnnullamentoPosizioneDebitoria extends MessaggioDiSistema {

    private Istanzeoneri ionere;
    private DettPosizioneDebitoria posizioneDebitoria;
    private String messaggio;

    public MessaggioErroreAnnullamentoPosizioneDebitoria(Istanzeoneri ionere, DettPosizioneDebitoria posizioneDebitoria, String messaggio) {

	this.ionere = ionere;
	this.posizioneDebitoria = posizioneDebitoria;
	this.messaggio = messaggio;
    }

    @Override
    public String getTestoMessaggio() {

	StringBuilder ret = new StringBuilder();
	ret = ret.append("Si sono verificati problemi nell'annullamento della posizione debitoria con id ").append(posizioneDebitoria.toString())
		.append(" legata all'onere con id ") //
		.append(ionere.toString()) //
		.append(" a causa di ").append(messaggio);
	return ret.toString();
    }
}
