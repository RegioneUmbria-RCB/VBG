package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.sottoscrittori;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDettaglioDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.ConfigurazioneComunicazioniCommissioni;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.StatoComunicazioniCommissioniEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.workflow.IWorkFlowComunicazioniCommissioniService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.IConfigurazioneComunicazioneService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.EventoComunicazioneInviata;

@Service
public class SottoscrittoreCommissioniEventoComunicazioneInviataServiceImpl extends SottoscrittoreEventoCommissioniBase<EventoComunicazioneInviata> {

    private IComunicazioniMassiveDettaglioDAO massiveDao;
    private IConfigurazioneComunicazioneService configurazioneComunicazioneService;
    private IWorkFlowComunicazioniCommissioniService workflowService;

    @Autowired
    public SottoscrittoreCommissioniEventoComunicazioneInviataServiceImpl(IComunicazioniMassiveDettaglioDAO massiveDao,
	    IConfigurazioneComunicazioneService configurazioneComunicazioneService, IWorkFlowComunicazioniCommissioniService workflowService) {

	super();
	this.massiveDao = massiveDao;
	this.configurazioneComunicazioneService = configurazioneComunicazioneService;
	this.workflowService = workflowService;
    }

    @Override
    void onEventInternal(EventoComunicazioneInviata e) {

	ConfigurazioneComunicazioniCommissioni configurazione = new ConfigurazioneComunicazioniCommissioni();
	configurazioneComunicazioneService.getByIdDettaglioComunicazione(e.getIdDettaglioComunicazione(), configurazione);
	// Imposta lo stato a "Inviata"
	this.massiveDao.impostaStatoConCommit(e.getIdDettaglioComunicazione(), StatoComunicazioniCommissioniEnum.INVIATA.name());
	// chiama il workflow per elaborare la riga (qui la vita del dettaglio dovrebbe essere finita)
	workflowService.elabora(e.getIdDettaglioComunicazione(), configurazione);
    }
}
