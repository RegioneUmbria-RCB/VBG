package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.subscr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDettaglioDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.ConfigurazioneComunicazioniBollettazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.StatoComunicazioniBollettazioneEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.ConfigurazioneComunicazioniCommissioni;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.StatoComunicazioniCommissioniEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.workflow.IWorkFlowComunicazioniCommissioniService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.IConfigurazioneComunicazioneService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.EventoFirmaComunicazioneAvviata;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.ConfigurazioniComunicazioneGen;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.StatoComunicazioniGenEnum;

@Service
public class SottoscrittoreGenEventoFirmaComunicazioneAvviata extends SottoscrittoreEventoGenBaseImpl<EventoFirmaComunicazioneAvviata> {

    @Override
    void onEventInternal(EventoFirmaComunicazioneAvviata e) {

	ConfigurazioniComunicazioneGen configurazione = new ConfigurazioniComunicazioneGen(e.getContesto());
	configurazioneComunicazioneService.getByIdDettaglioComunicazione(e.getIdDettaglioComunicazione(), configurazione);
	// Imposta lo stato "Firma avviata"
	this.massiveDao.impostaStatoConCommit(e.getIdDettaglioComunicazione(), StatoComunicazioniGenEnum.FIRMA_IN_CORSO.name());
	// Elabora il workflow (lo step non ha gestori...)
	giveWorkflowComunicazioniService(e.getContesto()).elabora(e.getIdDettaglioComunicazione(), configurazione);
	
    }

}
