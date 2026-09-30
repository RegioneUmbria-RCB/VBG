package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.messaggi;

import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.features.infrastructure.auditing.messages.MessaggioDiSistema;

public class MessaggioBorsellinoCreato extends MessaggioDiSistema {

    private Anagrafe anagrafe;

    public MessaggioBorsellinoCreato(Anagrafe anagrafe) {

	this.anagrafe = anagrafe;
    }

    @Override
    public String getTestoMessaggio() {

	return "Abbonamento di " + descrizioneAnagrafe();
    }

    private String descrizioneAnagrafe() {

	return anagrafe.getDescrizioneRichiedenteBreve();
    }
}
