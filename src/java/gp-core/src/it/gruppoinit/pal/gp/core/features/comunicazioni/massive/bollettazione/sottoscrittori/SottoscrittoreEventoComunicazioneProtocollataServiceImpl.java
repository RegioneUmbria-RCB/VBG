package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.sottoscrittori;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDettaglioDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.ConfigurazioneComunicazioniBollettazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.StatoComunicazioniBollettazioneEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.workflow.IWorkFlowComunicazioniBollettazioneService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.IConfigurazioneComunicazioneService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.EventoComunicazioneProtocollata;

@Service
public class SottoscrittoreEventoComunicazioneProtocollataServiceImpl extends SottoscrittoreEventoBollettazioneBase<EventoComunicazioneProtocollata> {

    private IComunicazioniMassiveDettaglioDAO massiveDao;
    private IConfigurazioneComunicazioneService configurazioneComunicazioneService;
    private IWorkFlowComunicazioniBollettazioneService workflowService;

    @Autowired
    public SottoscrittoreEventoComunicazioneProtocollataServiceImpl(IComunicazioniMassiveDettaglioDAO massiveDao,
	    IConfigurazioneComunicazioneService configurazioneComunicazioneService, IWorkFlowComunicazioniBollettazioneService workflowService) {

	super();
	this.massiveDao = massiveDao;
	this.configurazioneComunicazioneService = configurazioneComunicazioneService;
	this.workflowService = workflowService;
    }

    @Override
    void onEventInternal(EventoComunicazioneProtocollata e) {

	ConfigurazioneComunicazioniBollettazione configurazione = new ConfigurazioneComunicazioniBollettazione();
	configurazioneComunicazioneService.getByIdDettaglioComunicazione(e.getIdDettaglioComunicazione(), configurazione);
	// Imposta lo stato a pronta per invio
	this.massiveDao.impostaStatoConCommit(e.getIdDettaglioComunicazione(), StatoComunicazioniBollettazioneEnum.PRONTA_ALL_INVIO.name());
	// chiama il workflow per elaborare la riga
	workflowService.elabora(e.getIdDettaglioComunicazione(), configurazione);
    }
}
