package it.gruppoinit.pal.gp.core.features.rabbitmq.posizionidebitorie;

import it.gruppoinit.pal.gp.core.features.rabbitmq.model.CodiciFiscaliDestinatariBean;

public class MercatipresenzeDDestinatariResolver implements IDestinatariDettPosizioneDebitoriaResolver {

    public MercatipresenzeDDestinatariResolver() {

	//  Auto-generated constructor stub
    }

    @Override
    public CodiciFiscaliDestinatariBean getDestinatariPersoneFisiche() {

	// dalla posizione debitoria risale alla presenza dove è registrata la posizione stessa
	// se CODICEANAGRAFE è persona fisica prende quella
	// se CODICEANAGRAFE è persona giuridica e codice fiscale è da 16 allora passa quella altrimenti torna new CodiciFiscaliDestinatariBean()
	// attualmente ritorno una struttura vuota perché non aggiungerebbe novità rispetto alla posizione aperta
	return new CodiciFiscaliDestinatariBean();
    }
}
