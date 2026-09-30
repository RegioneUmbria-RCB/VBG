package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.subscr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.MassiveDettaglio;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDettaglioDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.EventoComunicazioneProntaAllInvioAppIo;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.EventoRielabora;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.ConfigurazioniComunicazioneGen;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.StatoComunicazioniGenEnum;

@Service
public class SottoscrittoreGenEventoRielaboraServiceImpl
	extends SottoscrittoreEventoGenBaseImpl<EventoRielabora> {

    @Autowired
    private IComunicazioniMassiveDettaglioDAO comunicazioniMassiveDettaglioDAO;
    

    
    @Override
    void onEventInternal(EventoRielabora e) {

	ConfigurazioniComunicazioneGen configurazione = new ConfigurazioniComunicazioneGen(e.getContesto());
	configurazioneComunicazioneService.getByIdDettaglioComunicazione(e.getIdDettaglioComunicazione(), configurazione);
	
	/*
	 * Per ora gestiremo solo questi 3 casi, essendo quelli davvero non bloccanti e consecutivi
	 */
	
	
	MassiveDettaglio dettaglio = comunicazioniMassiveDettaglioDAO.getById(e.getIdDettaglioComunicazione());
	StatoComunicazioniGenEnum statoComunicazione = StatoComunicazioniGenEnum.PRONTA_PER_PROTOCOLLAZIONE;
	String errore = dettaglio.getErrore();
	
	if(errore!=null){
	    
	    if(errore.contains(SottoscrittoreGenEventoComunicazioneInviataServiceImpl.ERRORE_APPIO)){
		statoComunicazione = StatoComunicazioniGenEnum.PRONTA_ALL_INVIO_APPIO;
	    }
	    if(errore.contains(SottoscrittoreGenEventoComunicazioneInviataServiceImpl.ERRORE_MAIL)){
		statoComunicazione = StatoComunicazioniGenEnum.PRONTA_ALL_INVIO;
	    }
	    if(errore.contains(SottoscrittoreGenEventoComunicazioneInviataServiceImpl.ERRORE_PROTOCOLLO)){
		statoComunicazione = StatoComunicazioniGenEnum.PRONTA_PER_PROTOCOLLAZIONE;
	    }
	    
	}
	
	
	// Imposta lo stato a "Inviata"
	this.massiveDao.impostaStatoConCommit(e.getIdDettaglioComunicazione(), statoComunicazione.name());
	// chiama il workflow per elaborare la riga (qui la vita del dettaglio dovrebbe essere finita)
	
	giveWorkflowComunicazioniService(e.getContesto()).elabora(e.getIdDettaglioComunicazione(), configurazione);
    }
}
