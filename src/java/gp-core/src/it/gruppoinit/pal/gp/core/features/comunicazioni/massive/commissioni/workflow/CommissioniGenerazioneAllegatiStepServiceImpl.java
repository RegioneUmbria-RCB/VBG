package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.workflow;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.MassiveDettaglio;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDettaglioDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.ConfigurazioneComunicazioniCommissioni;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.StatoComunicazioniCommissioniEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.IWorkFlowStep;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.EventoAllegatiDelDettaglioElaborati;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.EventoElaboraAllegatiFissi;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.IEventoMassiva.ContestoComunicazioneEnum;

@Service
public class CommissioniGenerazioneAllegatiStepServiceImpl implements IWorkFlowStep<ConfigurazioneComunicazioniCommissioni> {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(CommissioniGenerazioneAllegatiStepServiceImpl.class);
    private IEventPublisher publisher;
    private IComunicazioniMassiveDettaglioDAO massiveDao;

    @Autowired
    public CommissioniGenerazioneAllegatiStepServiceImpl(IEventPublisher publisher, IComunicazioniMassiveDettaglioDAO massiveDao) {

	this.publisher = publisher;
	this.massiveDao = massiveDao;
    }

    @Override
    public void elabora(int idDettaglioComunicazione, ConfigurazioneComunicazioniCommissioni configurazione) {

	MassiveDettaglio dettaglio = massiveDao.getById(idDettaglioComunicazione);
	log.debug("elabora {}", idDettaglioComunicazione);
	StatoComunicazioniCommissioniEnum currStatus = StatoComunicazioniCommissioniEnum.valueOf(dettaglio.getUltimoStatoCompletato());
	log.debug("Gestione allegati stato corrente {}", currStatus);
	if (!configurazione.contieneAllegati()) {
	    // SE LISTA DI ALLEGATI FISSI E COMPILABILI è VUOTA 
	    // LANCIA EVENTO EventoAllegatiDelDettaglioElaborati ED ESCE?? O serve nuovo evento?
	    log.debug("elabora niente da elaborare rilancio l'evento");
	    publisher.publish(new EventoAllegatiDelDettaglioElaborati(ContestoComunicazioneEnum.COMMISSIONI, idDettaglioComunicazione));
	    return;
	}
	publisher.publish(new EventoElaboraAllegatiFissi(ContestoComunicazioneEnum.COMMISSIONI, idDettaglioComunicazione));
	//	this.massiveDao.impostaStatoConCommit(e.getIdDettaglioComunicazione(), StatoComunicazioniCommissioniEnum.PRONTA_PER_ALLEGATI_FISSI.name());
	//	// chiama il workflow per elaborare la riga (qui la vita del dettaglio dovrebbe essere finita)
	//	workflowService.elabora(e.getIdDettaglioComunicazione(), configurazione);
    }
}
