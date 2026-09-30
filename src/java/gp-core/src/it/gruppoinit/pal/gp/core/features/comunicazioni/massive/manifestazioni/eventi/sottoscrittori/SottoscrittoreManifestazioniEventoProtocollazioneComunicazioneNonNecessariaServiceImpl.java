package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni.eventi.sottoscrittori;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDettaglioDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.IConfigurazioneComunicazioneService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.EventoProtocollazioneComunicazioneNonNecessaria;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni.ConfigurazioneComunicazioniManifestazioni;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni.StatoComunicazioniManifestazioniEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni.workflow.IWorkFlowComunicazioniManifestazioniService;

@Service
public class SottoscrittoreManifestazioniEventoProtocollazioneComunicazioneNonNecessariaServiceImpl
	extends SottoscrittoreEventoManifestazioniBase<EventoProtocollazioneComunicazioneNonNecessaria> {

    private IComunicazioniMassiveDettaglioDAO massiveDao;
    private IConfigurazioneComunicazioneService configurazioneComunicazioneService;
    private IWorkFlowComunicazioniManifestazioniService workflowService;

    @Autowired
    public SottoscrittoreManifestazioniEventoProtocollazioneComunicazioneNonNecessariaServiceImpl(IComunicazioniMassiveDettaglioDAO massiveDao,
	    IConfigurazioneComunicazioneService configurazioneComunicazioneService, IWorkFlowComunicazioniManifestazioniService workflowService) {

	super();
	this.massiveDao = massiveDao;
	this.configurazioneComunicazioneService = configurazioneComunicazioneService;
	this.workflowService = workflowService;
    }

    @Override
    void onEventInternal(EventoProtocollazioneComunicazioneNonNecessaria e) {

	ConfigurazioneComunicazioniManifestazioni configurazione = new ConfigurazioneComunicazioniManifestazioni();
	this.configurazioneComunicazioneService.getByIdDettaglioComunicazione(e.getIdDettaglioComunicazione(), configurazione);
	// Imposta lo stato a pronta per invio
	this.massiveDao.impostaStatoConCommit(e.getIdDettaglioComunicazione(), StatoComunicazioniManifestazioniEnum.PRONTA_ALL_INVIO.name());
	// chiama il workflow per elaborare la riga
	workflowService.elabora(e.getIdDettaglioComunicazione(), configurazione);
    }
}