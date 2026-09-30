package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.wf;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.MassiveDettaglio;
import it.gruppoinit.pal.gp.core.features.buslightyear.exceptions.EventAbortedException;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDettaglioDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.ConfigurazioneComunicazioniCommissioni;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.StatoComunicazioniCommissioniEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.workflow.CommissioniGenerazioneAllegatiStepServiceImpl;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.IWorkFlowStep;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.EventoAllegatiDelDettaglioElaborati;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.EventoElaboraAllegatiFissi;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.EventoRielabora;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.IEventoMassiva.ContestoComunicazioneEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.ConfigurazioniComunicazioneGen;

@Service
public class GenRielaboraServiceImpl implements IWorkFlowStep<ConfigurazioniComunicazioneGen> {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(GenRielaboraServiceImpl.class);
    private IEventPublisher publisher;

    @Autowired
    public GenRielaboraServiceImpl(IEventPublisher publisher) {

	this.publisher = publisher;
    }

    @Override
    public void elabora(int idDettaglioComunicazione, ConfigurazioniComunicazioneGen configurazione) {
	
	try {
	    publisher.publishThrowOnFailure(new EventoRielabora(configurazione.getContesto(), idDettaglioComunicazione));
	} catch (EventAbortedException e) {
	    log.error("Errore su rielaborazione",e);
	    throw new RuntimeException(e);
	}
	
    }

    
}
