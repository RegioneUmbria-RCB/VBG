package it.gruppoinit.pal.gp.core.features.rabbitmq.posizionidebitorie;

import it.gruppoinit.pal.gp.core.features.rabbitmq.model.CodiciFiscaliDestinatariBean;

public class AbbonamentiDestinatariResolver implements IDestinatariDettPosizioneDebitoriaResolver {

    public AbbonamentiDestinatariResolver() {

	//  Auto-generated constructor stub
    }

    @Override
    public CodiciFiscaliDestinatariBean getDestinatariPersoneFisiche() {

	// dalla posizione debitoria risale al borsellino dove è registrata la posizione stessa
	// se legato a persona fisica prende quella
	// se legato a persona giuridica e codice fiscale è da 16 allora passa quella altrimenti torna new CodiciFiscaliDestinatariBean()
	// attualmente ritorno una struttura vuota perché non aggiungerebbe novità rispetto alla posizione aperta
	return new CodiciFiscaliDestinatariBean();
    }
}
