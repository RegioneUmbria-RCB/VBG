package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.sottoscrittori;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDettaglioDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.ConfigurazioneComunicazioniCommissioni;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.StatoComunicazioniCommissioniEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.workflow.IWorkFlowComunicazioniCommissioniService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.IConfigurazioneComunicazioneService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.EventoElaboraAllegatiFissi;

@Service
public class SottoscrittoreCommissioniEventoElaboraAllegatiFissiServiceImpl extends SottoscrittoreEventoCommissioniBase<EventoElaboraAllegatiFissi> {

    private IComunicazioniMassiveDettaglioDAO massiveDao;
    private IConfigurazioneComunicazioneService configurazioneComunicazioneService;
    private IWorkFlowComunicazioniCommissioniService workflowService;

    @Autowired
    public SottoscrittoreCommissioniEventoElaboraAllegatiFissiServiceImpl(IComunicazioniMassiveDettaglioDAO massiveDao,
	    IConfigurazioneComunicazioneService configurazioneComunicazioneService, IWorkFlowComunicazioniCommissioniService workflowService) {

	super();
	this.massiveDao = massiveDao;
	this.configurazioneComunicazioneService = configurazioneComunicazioneService;
	this.workflowService = workflowService;
    }

    @Override
    void onEventInternal(EventoElaboraAllegatiFissi e) {

	ConfigurazioneComunicazioniCommissioni configurazione = new ConfigurazioneComunicazioniCommissioni();
	configurazioneComunicazioneService.getByIdDettaglioComunicazione(e.getIdDettaglioComunicazione(), configurazione);
	this.massiveDao.impostaStatoConCommit(e.getIdDettaglioComunicazione(), StatoComunicazioniCommissioniEnum.PRONTA_PER_ALLEGATI_FISSI.name());
	// Invoca l'elaborazione del workflow
	workflowService.elabora(e.getIdDettaglioComunicazione(), configurazione);
    }
}
