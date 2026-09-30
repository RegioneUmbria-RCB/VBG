package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.subscr;



import org.springframework.stereotype.Service;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.EventoComunicazioneInviata;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.ConfigurazioniComunicazioneGen;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.StatoComunicazioniGenEnum;

@Service
public class SottoscrittoreGenEventoComunicazioneInviataServiceImpl extends SottoscrittoreEventoGenBaseImpl<EventoComunicazioneInviata> {

    private static final String CONCLUSA_CON_ERRORE = "CONCLUSA_CON_ERRORE";
    public static final String ERRORE_PROTOCOLLO = "Errore durante invio protocollo";
    public static final String ERRORE_MAIL = "Errore durante invio mail";
    public static final String ERRORE_APPIO = "Errore durante invio appio";
    
    private static final String TUTTI_ERRORI = "CONCLUSA_CON_ERRORE_PROTOCOLLO_MAIL_APPIO";
    
    @Override
    void onEventInternal(EventoComunicazioneInviata e) {

	ConfigurazioniComunicazioneGen configurazione = new ConfigurazioniComunicazioneGen(e.getContesto());
	configurazioneComunicazioneService.getByIdDettaglioComunicazione(e.getIdDettaglioComunicazione(), configurazione);

	if(e.getWarnings() != null && !e.getWarnings().isEmpty()){
	    
	    String statoCompletatoErrore = SottoscrittoreGenEventoComunicazioneInviataServiceImpl.CONCLUSA_CON_ERRORE;
	    for(String errore : e.getWarnings()){
		
		if(errore.startsWith(ERRORE_PROTOCOLLO)){
		    statoCompletatoErrore += "_PROTOCOLLO";
		}
		if(errore.startsWith(ERRORE_MAIL)){
		    statoCompletatoErrore += "_MAIL";
		}
		if(errore.startsWith(ERRORE_APPIO)){
		    statoCompletatoErrore += "_APPIO";
		}
		
	    }
	    
	    if(TUTTI_ERRORI.equals(statoCompletatoErrore)){
		statoCompletatoErrore = SottoscrittoreGenEventoComunicazioneInviataServiceImpl.CONCLUSA_CON_ERRORE;
		//per ora se tutti e 3 sono errori allora scriviamo così
	    }
	    
	    this.massiveDao.impostaStatoConCommit(e.getIdDettaglioComunicazione(), statoCompletatoErrore);
	    
	    StringBuilder sb = new StringBuilder();
	    for(String s : e.getWarnings()){
		sb.append(s).append("\r\n").append("\r\n");
	    }
	    this.massiveDao.salvaErrore(e.getIdDettaglioComunicazione(), sb.toString());
	}else{
	    this.massiveDao.impostaStatoConCommit(e.getIdDettaglioComunicazione(), StatoComunicazioniGenEnum.INVIATA.name()); 
	}
	
	
    }

}
