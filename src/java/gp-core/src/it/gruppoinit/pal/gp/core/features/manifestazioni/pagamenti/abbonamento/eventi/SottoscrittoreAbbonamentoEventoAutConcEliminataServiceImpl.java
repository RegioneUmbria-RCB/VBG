package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.eventi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.BorsellinoAutorizzazioni;
import it.gruppoinit.pal.gp.core.domain.BorsellinoConfigurazione;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniDAO;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EsitoCancellazioneAutOConc;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EventoAutorizzazioneConcessioneEliminata;
import it.gruppoinit.pal.gp.core.features.buslightyear.exceptions.EventAbortedException;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.ComportamentoEnum;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.IAbbonamentoService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.IBorsellinoAutorizzazioniDAO;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.auditing.CancellazioneAutConcDaAbbonamentoLogger;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.auditing.CancellazioneAutConcDaAbbonamentoLogger.CancellazioneAutConcDaAbbonamentoLoggerEnum;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione.IBorsellinoConfigurazioneDAO;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.exceptions.BorsellinoException;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;

@Service
public class SottoscrittoreAbbonamentoEventoAutConcEliminataServiceImpl implements IEventSubscriber<EventoAutorizzazioneConcessioneEliminata> {

    @Autowired
    private IBorsellinoConfigurazioneDAO borsellinoConfigurazioneDAO;
    @Autowired
    private AutorizzazioniDAO autorizzazioniDAO;
    @Autowired
    private IAbbonamentoService abbonamentoService;
    @Autowired
    private IBorsellinoAutorizzazioniDAO borsellinoAutorizzazioniDAO;
    @Autowired
    private UserSecurityService userSecurityService;
    @Autowired
    private AnagrafeService anagrafeService;

    @Override
    public void onEvent(EventoAutorizzazioneConcessioneEliminata e) throws EventAbortedException {

	EsitoCancellazioneAutOConc esitoElaborazione = e.getEsito();
	if (esitoElaborazione != null && esitoElaborazione.isErrore()) {
	    String messaggio = "Sono presenti degli errori bloccanti e non è possibile effettuare la cancellazione ";
	    if (e.isConcessione()) {
		messaggio += " della concessione ";
	    } else {
		messaggio += " dell'autorizzazione ";
	    }
	    messaggio += e.getEstremiAtto();
	    throw new EventAbortedException(messaggio, this.getClass().getSimpleName(), e);
	}
	Autorizzazioni aut = autorizzazioniDAO.findById(new PkId(e.getIdAutorizzazioni()));
	if (aut != null) {
	    // SGANCIO L'ATORIZZAZIONE DAL BORSELLINO E LA RIAGGANCIO
	    CancellazioneAutConcDaAbbonamentoLogger auditLogger = new CancellazioneAutConcDaAbbonamentoLogger(aut,
		    userSecurityService.getCurrentlyAuthenticatedUserDetails().toString());
	    auditLogger.log();
	    Integer codiceAnagrafeNuovoOccupante = aut.getOccupante().getId().getCodice();
	    Anagrafe subentrante = anagrafeService.findById(new PkId(codiceAnagrafeNuovoOccupante));
	    List<BorsellinoAutorizzazioni> borselliniCollegatiAllAutorizzazione = borsellinoAutorizzazioniDAO
		    .findByIdAutorizzazione(e.getIdAutorizzazioni());
	    for (BorsellinoAutorizzazioni borsellinoAutorizzazioni : borselliniCollegatiAllAutorizzazione) {
		// In ogni caso la sgancio dal borsellino del vecchio titolare (o cedente in affitto)
		try {
		    String guidBorsellino = borsellinoAutorizzazioni.getBorsellino().getUuid();
		    abbonamentoService.rimuoviAutorizzazione(guidBorsellino, e.getIdAutorizzazioni());
		    auditLogger.addMessaggio(CancellazioneAutConcDaAbbonamentoLoggerEnum.AUTORIZZAZIONE_RIMOSSA_DA_BORSELLINO, guidBorsellino).log();
		} catch (BorsellinoException e1) {
		    throw new EventAbortedException(e1, this.getClass().getSimpleName(), e);
		}
	    }
	    BorsellinoConfigurazione cfg = borsellinoConfigurazioneDAO.findConfigurazione();
	    if (cfg != null && cfg.getTipoInstallazione().equalsIgnoreCase(ComportamentoEnum.OPERATORE.name())) {
		try {
		    abbonamentoService.collegaAlBorsellino(subentrante.getId().getCodice(), e.getIdAutorizzazioni());
		} catch (BorsellinoException e1) {
		    throw new EventAbortedException(e1, this.getClass().getSimpleName(), e);
		}
		auditLogger.addMessaggio(CancellazioneAutConcDaAbbonamentoLoggerEnum.AUTORIZZAZIONE_COLLEGATA_A_BORSELLINO).log();
		// SE NON PRESENTE UN BORSELLINO NON NE POSSO CREARE UNO PER L'ANAGRAFE DEL SUBENTRANTE (POTREBBE ESSERE UNA PERSONA GIURIDICA
		// IL BORSELLINO LO DEVE CREARE ENTRANDO NELL'APP O E' GIA' CREATO
	    }
	    auditLogger.logFineMetodo();
	}
    }
}
