package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.workflow;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDettaglioDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.ConfigurazioneComunicazioniBollettazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.IWorkFlowStep;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.AllegatoComunicazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.EventoAllegatiFissiElaborati;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.IEventoMassiva.ContestoComunicazioneEnum;

@Service
public class AllegatiFissiStepServiceImpl implements IWorkFlowStep<ConfigurazioneComunicazioniBollettazione> {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(AllegatiFissiStepServiceImpl.class);
    private IEventPublisher publisher;
    private IComunicazioniMassiveDettaglioDAO comunicazioniDettaglioDAO;

    @Autowired
    public AllegatiFissiStepServiceImpl(IEventPublisher publisher, IComunicazioniMassiveDettaglioDAO comunicazioniDettaglioDAO) {

	this.publisher = publisher;
	this.comunicazioniDettaglioDAO = comunicazioniDettaglioDAO;
    }

    @Override
    public void elabora(int idDettaglioComunicazione, ConfigurazioneComunicazioniBollettazione configurazione) {

	for (AllegatoComunicazione allegatoComunicazione : configurazione.getAllegatiFissi()) {
	    log.debug("elabora inserisco allegato fisso {}", allegatoComunicazione.getCodiceOggetto());
	    comunicazioniDettaglioDAO.insertAllegato(idDettaglioComunicazione, allegatoComunicazione.getCodiceOggetto());
	}
	publisher.publish(new EventoAllegatiFissiElaborati(ContestoComunicazioneEnum.BOLLETTAZIONE, idDettaglioComunicazione));
    }
}
