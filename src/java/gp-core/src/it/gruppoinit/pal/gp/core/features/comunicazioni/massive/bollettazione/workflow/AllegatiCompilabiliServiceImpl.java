package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.workflow;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDettaglioDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.ConfigurazioneComunicazioniBollettazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.IComunicazioniToBollettazioneService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.IWorkFlowStep;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.LetteraComunicazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.EventoAllegatiDelDettaglioElaborati;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.IEventoMassiva.ContestoComunicazioneEnum;

@Service
public class AllegatiCompilabiliServiceImpl implements IWorkFlowStep<ConfigurazioneComunicazioniBollettazione> {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(AllegatiCompilabiliServiceImpl.class);
    private IEventPublisher publisher;
    private IComunicazioniMassiveDettaglioDAO comunicazioniDettaglioDAO;
    private IComunicazioniToBollettazioneService comunicazioniToBollettazioneService;

    @Autowired
    public AllegatiCompilabiliServiceImpl(IEventPublisher publisher, IComunicazioniMassiveDettaglioDAO comunicazioniDettaglioDAO,
	    IComunicazioniToBollettazioneService comunicazioniToBollettazioneService) {

	this.publisher = publisher;
	this.comunicazioniDettaglioDAO = comunicazioniDettaglioDAO;
	this.comunicazioniToBollettazioneService = comunicazioniToBollettazioneService;
    }

    @Override
    public void elabora(int idDettaglioComunicazione, ConfigurazioneComunicazioniBollettazione configurazione) {

	try {
	    for (LetteraComunicazione lettera : configurazione.getLettereComunicazione()) {
		// 2.1 PER OGNI ALLEGATO COMPILABILE VA RICHIAMATO IL SERVIZIO CHE EFFETTUA LE SOSTITUZIONI DEL DOCUMENTo
		// recupera il dettaglio della bollettazione
		// 3 VERIFICA SE TRASFORMARE IN PDF IL DOCUMENTO
		// 3.1 SE TRASFORMA DEVE RICHIAMARE IL SERVIZIO FILECONVERTER PER CONVERTIRE I FILE NON PDF
		log.debug("elabora inserisco lettera {}, {}", lettera.getCodiceLettera(), configurazione.isTrasformaAllegatiCompilabiliInPdf());
		int codiceOggetto = comunicazioniToBollettazioneService.generaOggettoAccompagnamentoBollettazioneDettaglio(lettera.getCodiceLettera(),
			idDettaglioComunicazione, configurazione.isTrasformaAllegatiCompilabiliInPdf());
		comunicazioniDettaglioDAO.insertAllegato(idDettaglioComunicazione, codiceOggetto);
	    }
	    publisher.publish(new EventoAllegatiDelDettaglioElaborati(ContestoComunicazioneEnum.BOLLETTAZIONE, idDettaglioComunicazione));
	} catch (Exception e) {
	    String messaggioErrore = "Si è verificato un errore nell'elaborazione degli allegati " + e.getMessage();
	    log.error(messaggioErrore, e);
	    comunicazioniDettaglioDAO.salvaErrore(idDettaglioComunicazione, messaggioErrore);
	    throw new RuntimeException(messaggioErrore);
	}
    }
}
