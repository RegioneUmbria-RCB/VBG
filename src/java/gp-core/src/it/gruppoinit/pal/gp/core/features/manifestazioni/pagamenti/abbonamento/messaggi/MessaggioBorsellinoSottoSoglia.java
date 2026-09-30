package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.messaggi;

import it.gruppoinit.pal.gp.core.domain.web.servizijson.AutorizzazioneRestBean;
import it.gruppoinit.pal.gp.core.features.infrastructure.auditing.messages.MessaggioDiSistema;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.verticalizzazione.VerticalizzazioneAbbonamentoPosteggiServiceImpl;

public class MessaggioBorsellinoSottoSoglia extends MessaggioDiSistema {

    private AutorizzazioneRestBean aut;
    private VerticalizzazioneAbbonamentoPosteggiServiceImpl verticalizzazioneAbbonamentoPosteggi;
    private String messaggio = null;
    private static final String SOGLIA_AVVISO = "#SOGLIA_AVVISO#";
    private static final String NUMERO_AUTORIZZAZIONE = "#NUMERO_AUTORIZZAZIONE#";
    private static final String DATA_AUTORIZZAZIONE = "#DATA_AUTORIZZAZIONE#";

    /**
     * Il messaggio per indicare l'avviso di credito sotto soglia. E' possibile specificare le seguenti variabili.
     * <ul>
     * <li>#SOGLIA_AVVISO# il valore della soglia configurata nella regola sotto la quale mostrare l'avviso.</li>
     * <li>#NUMERO_AUTORIZZAZIONE# Il numero dell'autorizzazione/concessione per il quale è generato l'avviso</li>
     * <li>#DATA_AUTORIZZAZIONE# La Data dell'autorizzazione/concessione per il quale è generato l'avviso
     * </ul>
     * 
     * @param aut
     * @param verticalizzazioneAbbonamentoPosteggi
     */
    public MessaggioBorsellinoSottoSoglia(AutorizzazioneRestBean aut,
	    VerticalizzazioneAbbonamentoPosteggiServiceImpl verticalizzazioneAbbonamentoPosteggi) {

	this.aut = aut;
	this.verticalizzazioneAbbonamentoPosteggi = verticalizzazioneAbbonamentoPosteggi;
	this.messaggio = verticalizzazioneAbbonamentoPosteggi.messaggioCreditoSottoSoglia();
    }

    @Override
    public String getTestoMessaggio() {

	return messaggio //
		.replace(SOGLIA_AVVISO, String.valueOf(verticalizzazioneAbbonamentoPosteggi.sogliaAvviso())) // 
		.replace(NUMERO_AUTORIZZAZIONE, aut.getNumero()) //
		.replace(DATA_AUTORIZZAZIONE, aut.getData());
    }
}
