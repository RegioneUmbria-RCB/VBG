package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.sottoscrittori;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDettaglioDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.ConfigurazioneComunicazioniBollettazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.StatoComunicazioniBollettazioneEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.workflow.IWorkFlowComunicazioniBollettazioneService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.IConfigurazioneComunicazioneService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.EventoAllegatiFissiElaborati;

@Service
public class SottoscrittoreEventoAllegatiFissiElaboratiServiceImpl extends SottoscrittoreEventoBollettazioneBase<EventoAllegatiFissiElaborati> {

    private IComunicazioniMassiveDettaglioDAO massiveDao;
    private IConfigurazioneComunicazioneService configurazioneComunicazioneService;
    private IWorkFlowComunicazioniBollettazioneService workflowService;

    @Autowired
    public SottoscrittoreEventoAllegatiFissiElaboratiServiceImpl(IComunicazioniMassiveDettaglioDAO massiveDao,
	    IConfigurazioneComunicazioneService configurazioneComunicazioneService, IWorkFlowComunicazioniBollettazioneService workflowService) {

	this.massiveDao = massiveDao;
	this.configurazioneComunicazioneService = configurazioneComunicazioneService;
	this.workflowService = workflowService;
    }

    @Override
    void onEventInternal(EventoAllegatiFissiElaborati e) {

	ConfigurazioneComunicazioniBollettazione configurazione = new ConfigurazioneComunicazioniBollettazione();
	configurazioneComunicazioneService.getByIdDettaglioComunicazione(e.getIdDettaglioComunicazione(), configurazione);
	this.massiveDao.impostaStatoConCommit(e.getIdDettaglioComunicazione(),
		StatoComunicazioniBollettazioneEnum.PRONTA_PER_ALLEGATI_COMPILABILI.name());
	// chiama il workflow per elaborare la riga (qui la vita del dettaglio dovrebbe essere finita)
	workflowService.elabora(e.getIdDettaglioComunicazione(), configurazione);
    }
}
