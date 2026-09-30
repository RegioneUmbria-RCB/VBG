package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.sottoscrittori;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDettaglioDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.ConfigurazioneComunicazioniBollettazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.StatoComunicazioniBollettazioneEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.workflow.IWorkFlowComunicazioniBollettazioneService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.IConfigurazioneComunicazioneService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.EventoFirmaComunicazioneAvviata;

@Service
public class SottoscrittoreEventoFirmaComunicazioneAvviata extends SottoscrittoreEventoBollettazioneBase<EventoFirmaComunicazioneAvviata> {

    private IComunicazioniMassiveDettaglioDAO massiveDao;
    private IConfigurazioneComunicazioneService configurazioneComunicazioneService;
    private IWorkFlowComunicazioniBollettazioneService workflowService;

    @Autowired
    public SottoscrittoreEventoFirmaComunicazioneAvviata(IComunicazioniMassiveDettaglioDAO massiveDao,
	    IConfigurazioneComunicazioneService configurazioneComunicazioneService, IWorkFlowComunicazioniBollettazioneService workflowService) {

	super();
	this.massiveDao = massiveDao;
	this.configurazioneComunicazioneService = configurazioneComunicazioneService;
	this.workflowService = workflowService;
    }

    @Override
    void onEventInternal(EventoFirmaComunicazioneAvviata e) {

	ConfigurazioneComunicazioniBollettazione configurazione = new ConfigurazioneComunicazioniBollettazione();
	configurazioneComunicazioneService.getByIdDettaglioComunicazione(e.getIdDettaglioComunicazione(), configurazione);
	// Imposta lo stato "Firma avviata"
	this.massiveDao.impostaStatoConCommit(e.getIdDettaglioComunicazione(), StatoComunicazioniBollettazioneEnum.FIRMA_IN_CORSO.name());
	// Elabora il workflow (lo step non ha gestori...)
	workflowService.elabora(e.getIdDettaglioComunicazione(), configurazione);
    }
}
