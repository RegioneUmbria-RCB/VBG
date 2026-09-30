package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.wf;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.MassiveDettaglio;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDettaglioDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.ConfigurazioneComunicazioniCommissioni;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.StatoComunicazioniCommissioniEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.workflow.CommissioniGenerazioneAllegatiStepServiceImpl;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.IWorkFlowStep;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.EventoAllegatiDelDettaglioElaborati;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.EventoElaboraAllegatiFissi;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.IEventoMassiva.ContestoComunicazioneEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.ConfigurazioniComunicazioneGen;

@Service
public class GenGenerazioneAllegatiStepServiceImpl implements IWorkFlowStep<ConfigurazioniComunicazioneGen> {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(CommissioniGenerazioneAllegatiStepServiceImpl.class);
    private IEventPublisher publisher;
    private IComunicazioniMassiveDettaglioDAO massiveDao;

    @Autowired
    public GenGenerazioneAllegatiStepServiceImpl(IEventPublisher publisher, IComunicazioniMassiveDettaglioDAO massiveDao) {

	this.publisher = publisher;
	this.massiveDao = massiveDao;
    }

    @Override
    public void elabora(int idDettaglioComunicazione, ConfigurazioniComunicazioneGen configurazione) {
	
	MassiveDettaglio dettaglio = massiveDao.getById(idDettaglioComunicazione);
	log.debug("elabora {}", idDettaglioComunicazione);
	log.debug("Gestione allegati stato corrente {}", dettaglio.getUltimoStatoCompletato());
	
	//if (!configurazione.contieneAllegati()) {
	if (!configurazione.contieneAllegati()) {
	    // SE LISTA DI ALLEGATI FISSI E COMPILABILI è VUOTA 
	    // LANCIA EVENTO EventoAllegatiDelDettaglioElaborati ED ESCE?? O serve nuovo evento?
	    log.debug("elabora niente da elaborare rilancio l'evento");
	    publisher.publish(new EventoAllegatiDelDettaglioElaborati(configurazione.getContesto(), idDettaglioComunicazione));
	    return;
	}
	publisher.publish(new EventoElaboraAllegatiFissi(configurazione.getContesto(), idDettaglioComunicazione));
	//	this.massiveDao.impostaStatoConCommit(e.getIdDettaglioComunicazione(), StatoComunicazioniCommissioniEnum.PRONTA_PER_ALLEGATI_FISSI.name());
	//	// chiama il workflow per elaborare la riga (qui la vita del dettaglio dovrebbe essere finita)
	//	workflowService.elabora(e.getIdDettaglioComunicazione(), configurazione);
    }

    
}
