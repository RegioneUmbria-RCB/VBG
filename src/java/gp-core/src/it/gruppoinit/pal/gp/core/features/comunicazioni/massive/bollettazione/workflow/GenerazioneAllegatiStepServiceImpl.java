package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.workflow;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.MassiveDettaglio;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDettaglioDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.ConfigurazioneComunicazioniBollettazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.IComunicazioniToBollettazioneService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.StatoComunicazioniBollettazioneEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.IWorkFlowStep;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.EventoAllegatiDelDettaglioElaborati;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.EventoAvvisiPagamentoElaborati;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.IEventoMassiva.ContestoComunicazioneEnum;

@Service
public class GenerazioneAllegatiStepServiceImpl implements IWorkFlowStep<ConfigurazioneComunicazioniBollettazione> {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(GenerazioneAllegatiStepServiceImpl.class);
    private IEventPublisher publisher;
    private IComunicazioniMassiveDettaglioDAO comunicazioniDettaglioDAO;
    private IComunicazioniToBollettazioneService comunicazioniToBollettazioneService;
    private IComunicazioniMassiveDettaglioDAO massiveDao;

    @Autowired
    public GenerazioneAllegatiStepServiceImpl(IEventPublisher publisher, IComunicazioniMassiveDettaglioDAO comunicazioniDettaglioDAO,
	    IComunicazioniToBollettazioneService comunicazioniToBollettazioneService, IComunicazioniMassiveDettaglioDAO massiveDao) {

	this.publisher = publisher;
	this.comunicazioniDettaglioDAO = comunicazioniDettaglioDAO;
	this.comunicazioniToBollettazioneService = comunicazioniToBollettazioneService;
	this.massiveDao = massiveDao;
    }

    @Override
    public void elabora(int idDettaglioComunicazione, ConfigurazioneComunicazioniBollettazione configurazione) {

	MassiveDettaglio dettaglio = massiveDao.getById(idDettaglioComunicazione);
	log.debug("elabora {}", idDettaglioComunicazione);
	StatoComunicazioniBollettazioneEnum currStatus = StatoComunicazioniBollettazioneEnum.valueOf(dettaglio.getUltimoStatoCompletato());
	log.debug("Gestione allegati stato corrente {}", currStatus);
	if (!configurazione.contieneAllegati()) {
	    // SE LISTA DI ALLEGATI FISSI E COMPILABILI è VUOTA o non devo allegare l'avviso
	    // LANCIA EVENTO EventoAllegatiDelDettaglioElaborati ED ESCE?? O serve nuovo evento?
	    log.debug("elabora niente da elaborare rilancio l'evento");
	    publisher.publish(new EventoAllegatiDelDettaglioElaborati(ContestoComunicazioneEnum.BOLLETTAZIONE, idDettaglioComunicazione));
	    return;
	}
	try {
	    log.debug("configurazione.isAllegaAvvisiPagamento() {}", configurazione.isAllegaAvvisiPagamento());
	    if (configurazione.isAllegaAvvisiPagamento()) {
		List<Integer> codiceOggetti = comunicazioniToBollettazioneService.recuperaAvvisoDiPagamento(idDettaglioComunicazione);
		if (codiceOggetti == null || codiceOggetti.isEmpty()) {
		    throw new RuntimeException("Non è stato possibile recuperare l'avviso di pagamento per la riga " + idDettaglioComunicazione);
		}
		for (Integer codiceOggetto : codiceOggetti) {
		    if (codiceOggetto != null) {
			log.debug("codiceOggetto {}", codiceOggetto);
			comunicazioniDettaglioDAO.insertAllegato(idDettaglioComunicazione, codiceOggetto);
		    } else {
			throw new RuntimeException("Non è stato possibile recuperare l'avviso di pagamento " + idDettaglioComunicazione);
		    }
		}
	    }
	    publisher.publish(new EventoAvvisiPagamentoElaborati(ContestoComunicazioneEnum.BOLLETTAZIONE, idDettaglioComunicazione));
	} catch (Exception e) {
	    String messaggioErrore = "Si è verificato un errore nell'elaborazione degli allegati " + e.getMessage() + " per la riga " +
				     idDettaglioComunicazione;
	    log.error(messaggioErrore, e);
	    comunicazioniDettaglioDAO.salvaErrore(idDettaglioComunicazione, messaggioErrore);
	}
    }
}
