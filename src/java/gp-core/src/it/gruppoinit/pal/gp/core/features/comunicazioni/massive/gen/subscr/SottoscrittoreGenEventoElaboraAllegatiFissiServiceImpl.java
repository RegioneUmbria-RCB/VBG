package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.subscr;




import org.springframework.stereotype.Service;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.StatoComunicazioniCommissioniEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.EventoElaboraAllegatiFissi;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.ConfigurazioniComunicazioneGen;

@Service
public class SottoscrittoreGenEventoElaboraAllegatiFissiServiceImpl extends SottoscrittoreEventoGenBaseImpl<EventoElaboraAllegatiFissi> {

    @Override
    void onEventInternal(EventoElaboraAllegatiFissi e) {

	ConfigurazioniComunicazioneGen configurazione = new ConfigurazioniComunicazioneGen(e.getContesto());
	configurazioneComunicazioneService.getByIdDettaglioComunicazione(e.getIdDettaglioComunicazione(), configurazione);
	this.massiveDao.impostaStatoConCommit(e.getIdDettaglioComunicazione(), StatoComunicazioniCommissioniEnum.PRONTA_PER_ALLEGATI_FISSI.name());
	// Invoca l'elaborazione del workflow
	giveWorkflowComunicazioniService(e.getContesto()).elabora(e.getIdDettaglioComunicazione(), configurazione);
	
    }

    
}
