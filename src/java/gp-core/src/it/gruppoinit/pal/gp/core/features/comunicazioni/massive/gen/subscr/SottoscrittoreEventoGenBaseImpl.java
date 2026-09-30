package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.subscr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDettaglioDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.IConfigurazioneComunicazioneService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.IEventoMassiva;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.IEventoMassiva.ContestoComunicazioneEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.IComunicazioniGenService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.IWorkFlowComunicazioniGenService;

public class SottoscrittoreEventoGenBaseImpl<T extends IEventoMassiva> extends SottoscrittoreEventoGenBase<T>{

    private IComunicazioniGenService comunicazioniMercService;
    private IWorkFlowComunicazioniGenService workFlowComunicazioniMercatiService;
    private IComunicazioniGenService comunicazioniIstService;
    private IWorkFlowComunicazioniGenService workFlowComunicazioniIstanzeService;
    
    protected IComunicazioniMassiveDettaglioDAO massiveDao;
    protected IConfigurazioneComunicazioneService configurazioneComunicazioneService;
    protected IEventPublisher eventPublisher;
    
    
    @Autowired
    public void setComunicazioniMercService(@Qualifier("comunicazioniMercServiceImpl") IComunicazioniGenService comunicazioniMercService) {
    
        this.comunicazioniMercService = comunicazioniMercService;
    }

    @Autowired
    public void setWorkFlowComunicazioniMercatiService(@Qualifier("workFlowComunicazioniMercatiService") IWorkFlowComunicazioniGenService workFlowComunicazioniMercatiService) {
    
        this.workFlowComunicazioniMercatiService = workFlowComunicazioniMercatiService;
    }    
    
    @Autowired
    public void setComunicazioniIstService(@Qualifier("comunicazioniIstServiceImpl") IComunicazioniGenService comunicazioniIstService) {
    
        this.comunicazioniIstService = comunicazioniIstService;
    }

    @Autowired
    public void setWorkFlowComunicazioniIstanzeService(@Qualifier("workFlowComunicazioniIstanzeService") IWorkFlowComunicazioniGenService workFlowComunicazioniIstanzeService) {
    
        this.workFlowComunicazioniIstanzeService = workFlowComunicazioniIstanzeService;
    }

    @Autowired
    public void setMassiveDao(IComunicazioniMassiveDettaglioDAO massiveDao) {
    
        this.massiveDao = massiveDao;
    }

    @Autowired
    public void setConfigurazioneComunicazioneService(IConfigurazioneComunicazioneService configurazioneComunicazioneService) {
    
        this.configurazioneComunicazioneService = configurazioneComunicazioneService;
    }

    @Autowired
    public void setEventPublisher(IEventPublisher eventPublisher) {
    
        this.eventPublisher = eventPublisher;
    }

    //In generale aggiungere i service che servono, e poi restituirli con un if,else a seconda del contesto
    protected IWorkFlowComunicazioniGenService giveWorkflowComunicazioniService(ContestoComunicazioneEnum contesto) {
	if(contesto == ContestoComunicazioneEnum.MERCATI) {
	    return workFlowComunicazioniMercatiService;
	}else if(contesto == ContestoComunicazioneEnum.ISTANZE) {
	    return workFlowComunicazioniIstanzeService;
	}
	
	throw new RuntimeException("giveWorkflowComunicazioniService: No valid comunicazione setted ");
    }
    
    protected IComunicazioniGenService giveComunicazioniService(ContestoComunicazioneEnum contesto) {
	if(contesto == ContestoComunicazioneEnum.MERCATI) {
	    return comunicazioniMercService;
	}else if(contesto == ContestoComunicazioneEnum.ISTANZE) {
	    return comunicazioniIstService;
	}
	
	throw new RuntimeException("giveComunicazioniService: No valid comunicazione setted ");
    }


    @Override
    void onEventInternal(T e) {

	// TODO Auto-generated method stub
	
    }
}
