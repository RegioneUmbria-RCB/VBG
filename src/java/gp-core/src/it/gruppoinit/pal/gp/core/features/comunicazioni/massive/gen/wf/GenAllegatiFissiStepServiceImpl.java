package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.wf;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDettaglioDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.ConfigurazioneComunicazioniCommissioni;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.workflow.CommissioniAllegatiFissiStepServiceImpl;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.IWorkFlowStep;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.AllegatoComunicazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.EventoAllegatiFissiElaborati;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.IEventoMassiva.ContestoComunicazioneEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.ConfigurazioniComunicazioneGen;

@Service
public class GenAllegatiFissiStepServiceImpl implements IWorkFlowStep<ConfigurazioniComunicazioneGen> {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(GenAllegatiFissiStepServiceImpl.class);
    private IEventPublisher publisher;
    private IComunicazioniMassiveDettaglioDAO comunicazioniDettaglioDAO;

    @Autowired
    public GenAllegatiFissiStepServiceImpl(IEventPublisher publisher, IComunicazioniMassiveDettaglioDAO comunicazioniDettaglioDAO) {

	this.publisher = publisher;
	this.comunicazioniDettaglioDAO = comunicazioniDettaglioDAO;
    }
    
    @Override
    public void elabora(int idDettaglioComunicazione, ConfigurazioniComunicazioneGen configurazione) {

	for (AllegatoComunicazione allegatoComunicazione : configurazione.getAllegatiFissi()) {
	    log.debug("elabora inserisco allegato fisso {}", allegatoComunicazione.getCodiceOggetto());
	    comunicazioniDettaglioDAO.insertAllegato(idDettaglioComunicazione, allegatoComunicazione.getCodiceOggetto());
	}
	publisher.publish(new EventoAllegatiFissiElaborati(configurazione.getContesto(), idDettaglioComunicazione));
	
    }

    
    
}
