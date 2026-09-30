package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.wf;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.AppIoCoda;
import it.gruppoinit.pal.gp.core.domain.AppIoCodaId;
import it.gruppoinit.pal.gp.core.domain.AppIoCodaMassiveD;
import it.gruppoinit.pal.gp.core.domain.MassiveDettaglio;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.comunicazioni.appio.IAppIoCodaMassiveDDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.appio.IAppIoCodaService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.appio.StatiCodaEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDettaglioDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.IWorkFlowStep;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.EventoComunicazioneInviata;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.ConfigurazioniComunicazioneGen;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.StatoComunicazioniGenEnum;

@Service
public class GenAppIoStatoCodaStepServiceImpl implements IWorkFlowStep<ConfigurazioniComunicazioneGen> {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(GenAppIoStatoCodaStepServiceImpl.class);
    private IComunicazioniMassiveDettaglioDAO comunicazioniMassiveDettaglioDAO;
    private IComunicazioniMassiveDettaglioDAO massiveDao;
    private IAppIoCodaMassiveDDAO appIoCodaMassiveDDAO;
    private IAppIoCodaService appIoCodaService;
    private IEventPublisher publisher;

    private static final String APPIO_SCHEDULATA_ERRORE = "APPIO_SCHEDULATA_ERRORE";
    private static final String ERRORE_PROTOCOLLO = "Errore durante invio protocollo";
    private static final String ERRORE_MAIL = "Errore durante invio mail";
    private static final String ERRORE_APPIO = "Errore durante invio appio";
    private static final String CONCLUSA_CON_APPIO_ERRORE = "CONCLUSA_CON_APPIO_ERRORE"; //Questa mi serve per renderlo non rilanciabile
    
    @Autowired
    public GenAppIoStatoCodaStepServiceImpl(IComunicazioniMassiveDettaglioDAO comunicazioniMassiveDettaglioDAO,
	    IComunicazioniMassiveDettaglioDAO massiveDao, IAppIoCodaMassiveDDAO appIoCodaMassiveDDAO, IAppIoCodaService appIoCodaService,
	    IEventPublisher publisher) {

	super();
	this.comunicazioniMassiveDettaglioDAO = comunicazioniMassiveDettaglioDAO;
	this.massiveDao = massiveDao;
	this.appIoCodaMassiveDDAO = appIoCodaMassiveDDAO;
	this.appIoCodaService = appIoCodaService;
	this.publisher = publisher;
    }

    @Override
    public void elabora(int idDettaglioComunicazione, ConfigurazioniComunicazioneGen configurazione) {

	MassiveDettaglio dettaglio = comunicazioniMassiveDettaglioDAO.getById(idDettaglioComunicazione);
	List<AppIoCodaMassiveD> appIoCodaMassiveD = this.appIoCodaMassiveDDAO.findByIdDettaglioMassiveD(idDettaglioComunicazione);
	AppIoCodaId appIoCodaId = new AppIoCodaId();
	appIoCodaId.setGuid(appIoCodaMassiveD.get(0).getId().getGuidCoda());
	AppIoCoda appIoCoda = this.appIoCodaService.findById(appIoCodaId);
	String stato = appIoCoda.getStato();
	StatiCodaEnum statiCodaEnum = StatiCodaEnum.valueOf(stato);
	
	String statoAppio = StatoComunicazioniGenEnum.APPIO_SCHEDULATA.name();
	
	
	
	switch (statiCodaEnum) {
	case DA_PROCESSARE:
	    statoAppio = StatoComunicazioniGenEnum.APPIO_SCHEDULATA.name();
	    break;
	case IN_LAVORAZIONE:
	    statoAppio = StatoComunicazioniGenEnum.APPIO_SCHEDULATA.name();
	    break;
	case INVIATA_A_GATEWAY:
	    statoAppio = StatoComunicazioniGenEnum.APPIO_SCHEDULATA.name();
	    break;
	case NOTIFICATA_APPIO:
	    statoAppio = StatoComunicazioniGenEnum.APPIO_SCHEDULATA.name();
	    break;
	case NOTIFICATA_UTENTE:
	    statoAppio = StatiCodaEnum.NOTIFICATA_UTENTE.name();
	    break;
	case ERRORE:
	    statoAppio = StatoComunicazioniGenEnum.APPIO_ERRORE.name();
	    break;
	default:
	    break;
	}
	
	
	if(StatoComunicazioniGenEnum.APPIO_SCHEDULATA.name().equals(statoAppio)){
	    
	    if(configurazione.getWarnings() != null && !configurazione.getWarnings().isEmpty()){
		String statoCompletatoErrore = APPIO_SCHEDULATA_ERRORE;
		    for(String errore : configurazione.getWarnings()){
			
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
		this.massiveDao.impostaStatoConCommit(idDettaglioComunicazione, statoCompletatoErrore);
		StringBuilder sb = new StringBuilder();
		for (String s : configurazione.getWarnings()) {
		    sb.append(s).append("\r\n").append("\r\n");
		}
		this.massiveDao.salvaErrore(idDettaglioComunicazione, sb.toString());
	    }else{
		this.massiveDao.impostaStatoConCommit(idDettaglioComunicazione, StatoComunicazioniGenEnum.APPIO_SCHEDULATA.name());  
	    }
	    
	}else{//Gli altri casi saranno tutti gestiti tramite evento Inviata
	    List<String> warnings = configurazione.getWarnings() != null ? configurazione.getWarnings() : new ArrayList<String>();
	    if(StatoComunicazioniGenEnum.APPIO_ERRORE.name().equals(statoAppio)){
		
		String messaggio = "Errore durante invio appio : " + appIoCoda.getStatoMessaggio();
		
		if(warnings.isEmpty()){
		    this.massiveDao.impostaStatoConCommit(idDettaglioComunicazione, CONCLUSA_CON_APPIO_ERRORE);
		    this.massiveDao.salvaErrore(idDettaglioComunicazione, messaggio);
		    return;//Se non ci sono errori precedenti, allora si mette direttamente CONCLUSA_CON_APPIO_ERRORE, il quale è non rilanciabile
		}
		
		warnings.add("Errore durante invio appio : " + messaggio);
	    }
	    EventoComunicazioneInviata eventoComunicazioneInviata = new EventoComunicazioneInviata(configurazione.getContesto(), dettaglio.getId().getCodice());
	    eventoComunicazioneInviata.setWarnings(warnings);
	    publisher.publish(eventoComunicazioneInviata);
	}
		
    }
}
