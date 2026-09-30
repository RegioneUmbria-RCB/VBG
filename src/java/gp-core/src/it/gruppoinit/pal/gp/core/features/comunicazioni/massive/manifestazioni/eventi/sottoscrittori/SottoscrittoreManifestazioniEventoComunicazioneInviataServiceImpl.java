package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni.eventi.sottoscrittori;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDettaglioDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.IConfigurazioneComunicazioneService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.EventoComunicazioneInviata;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni.ConfigurazioneComunicazioniManifestazioni;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni.StatoComunicazioniManifestazioniEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni.workflow.IWorkFlowComunicazioniManifestazioniService;

@Service
public class SottoscrittoreManifestazioniEventoComunicazioneInviataServiceImpl
	extends SottoscrittoreEventoManifestazioniBase<EventoComunicazioneInviata> {

    private IComunicazioniMassiveDettaglioDAO massiveDao;
    private IConfigurazioneComunicazioneService configurazioneComunicazioneService;
    private IWorkFlowComunicazioniManifestazioniService workflowService;

    @Autowired
    public SottoscrittoreManifestazioniEventoComunicazioneInviataServiceImpl(IComunicazioniMassiveDettaglioDAO massiveDao,
	    IConfigurazioneComunicazioneService configurazioneComunicazioneService, IWorkFlowComunicazioniManifestazioniService workflowService) {

	super();
	this.massiveDao = massiveDao;
	this.configurazioneComunicazioneService = configurazioneComunicazioneService;
	this.workflowService = workflowService;
    }

    @Override
    void onEventInternal(EventoComunicazioneInviata e) {

	ConfigurazioneComunicazioniManifestazioni configurazione = new ConfigurazioneComunicazioniManifestazioni();
	configurazioneComunicazioneService.getByIdDettaglioComunicazione(e.getIdDettaglioComunicazione(), configurazione);
	// Imposta lo stato a "Inviata"
	this.massiveDao.impostaStatoConCommit(e.getIdDettaglioComunicazione(), StatoComunicazioniManifestazioniEnum.INVIATA.name());
	// chiama il workflow per elaborare la riga (qui la vita del dettaglio dovrebbe essere finita)
	this.workflowService.elabora(e.getIdDettaglioComunicazione(), configurazione);
    }
}
