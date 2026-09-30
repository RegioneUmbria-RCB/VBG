package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.wf;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.MassiveDettaglio;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDettaglioDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.IWorkFlowStep;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.ConfigurazioniComunicazioneGen;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.StatoComunicazioniGenEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.eventi.EventoAppIoStatoCoda;

@Service
public class GenAppIoSchedulataStepServiceImpl implements IWorkFlowStep<ConfigurazioniComunicazioneGen> {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(GenAppIoSchedulataStepServiceImpl.class);
    private IComunicazioniMassiveDettaglioDAO comunicazioniMassiveDettaglioDAO;
    private IEventPublisher publisher;
    private IComunicazioniMassiveDettaglioDAO massiveDao;

    @Autowired
    public GenAppIoSchedulataStepServiceImpl(IComunicazioniMassiveDettaglioDAO comunicazioniMassiveDettaglioDAO, IEventPublisher publisher,
	    IComunicazioniMassiveDettaglioDAO massiveDao) {

	super();
	this.comunicazioniMassiveDettaglioDAO = comunicazioniMassiveDettaglioDAO;
	this.publisher = publisher;
	this.massiveDao = massiveDao;
    }

    @Override
    public void elabora(int idDettaglioComunicazione, ConfigurazioniComunicazioneGen configurazione) {

	MassiveDettaglio dettaglio = comunicazioniMassiveDettaglioDAO.getById(idDettaglioComunicazione);
	this.massiveDao.impostaStatoConCommit(idDettaglioComunicazione, StatoComunicazioniGenEnum.APPIO_STATO_CODA.name());
	EventoAppIoStatoCoda eventoAppIoStatoCoda = new EventoAppIoStatoCoda(configurazione.getContesto(), dettaglio.getId().getCodice());
	eventoAppIoStatoCoda.setWarnings(configurazione.getWarnings());
	publisher.publish(eventoAppIoStatoCoda);
    }
}
