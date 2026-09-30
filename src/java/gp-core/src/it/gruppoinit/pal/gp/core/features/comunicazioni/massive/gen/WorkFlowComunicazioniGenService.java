package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.MassiveDettaglio;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDettaglioDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.IWorkFlowStep;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.wf.GenAllegatiCompilabiliServiceImpl;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.wf.GenAllegatiFissiStepServiceImpl;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.wf.GenAppIoSchedulataStepServiceImpl;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.wf.GenAppIoStatoCodaStepServiceImpl;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.wf.GenGenerazioneAllegatiStepServiceImpl;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.wf.GenInviaAppioServiceImpl;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.wf.GenInviaMailServiceImpl;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.wf.GenMettiAllaFirmaServiceImpl;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.wf.GenProtocollaServiceImpl;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.wf.GenRielaboraServiceImpl;

@Service
public class WorkFlowComunicazioniGenService implements IWorkFlowComunicazioniGenService {

    private static final String CONCLUSA_CON_ERRORE = "CONCLUSA_CON_ERRORE";
    private static final String APPIO_SCHEDULATA = "APPIO_SCHEDULATA";
    
    private Map<StatoComunicazioniGenEnum, IWorkFlowStep<ConfigurazioniComunicazioneGen>> passaggiWorkFlow = new HashMap<StatoComunicazioniGenEnum, IWorkFlowStep<ConfigurazioniComunicazioneGen>>();
    private IComunicazioniMassiveDettaglioDAO comunicazioniMassiveDettaglioDAO;

    @Autowired
    public WorkFlowComunicazioniGenService(IComunicazioniMassiveDettaglioDAO comunicazioniMassiveDettaglioDAO,
	    GenGenerazioneAllegatiStepServiceImpl generazioneAllegatiStepServiceImpl, GenAllegatiFissiStepServiceImpl allegatiFissiStepServiceImpl,
	    GenAllegatiCompilabiliServiceImpl allegatiCompilabiliServiceImpl, GenMettiAllaFirmaServiceImpl mettiAllaFirmaServiceImpl,
	    GenProtocollaServiceImpl protocollaServiceImpl, GenInviaMailServiceImpl inviaMailServiceImpl,
	    GenInviaAppioServiceImpl inviaAppioServiceImpl, GenAppIoSchedulataStepServiceImpl genAppIoSchedulataStepServiceImpl,
	    GenAppIoStatoCodaStepServiceImpl genAppIoStatoCodaStepServiceImpl,GenRielaboraServiceImpl genRielaboraServiceImpl) {

	passaggiWorkFlow.put(StatoComunicazioniGenEnum.PRONTA_PER_ELABORAZIONE, generazioneAllegatiStepServiceImpl);
	passaggiWorkFlow.put(StatoComunicazioniGenEnum.PRONTA_PER_ALLEGATI_FISSI, allegatiFissiStepServiceImpl);
	passaggiWorkFlow.put(StatoComunicazioniGenEnum.PRONTA_PER_ALLEGATI_COMPILABILI, allegatiCompilabiliServiceImpl);
	passaggiWorkFlow.put(StatoComunicazioniGenEnum.PRONTA_ALLA_FIRMA, mettiAllaFirmaServiceImpl);
	passaggiWorkFlow.put(StatoComunicazioniGenEnum.PRONTA_PER_PROTOCOLLAZIONE, protocollaServiceImpl);
	passaggiWorkFlow.put(StatoComunicazioniGenEnum.PRONTA_ALL_INVIO, inviaMailServiceImpl);
	passaggiWorkFlow.put(StatoComunicazioniGenEnum.PRONTA_ALL_INVIO_APPIO, inviaAppioServiceImpl);
	passaggiWorkFlow.put(StatoComunicazioniGenEnum.APPIO_SCHEDULATA, genAppIoSchedulataStepServiceImpl);
	passaggiWorkFlow.put(StatoComunicazioniGenEnum.APPIO_STATO_CODA, genAppIoStatoCodaStepServiceImpl);
	passaggiWorkFlow.put(StatoComunicazioniGenEnum.CONCLUSA_CON_WARNING, genRielaboraServiceImpl);

	this.comunicazioniMassiveDettaglioDAO = comunicazioniMassiveDettaglioDAO;
    }

    @Override
    public void elabora(int idDettaglioComunicazione, ConfigurazioniComunicazioneGen configurazione) {

	MassiveDettaglio riga = comunicazioniMassiveDettaglioDAO.getById(idDettaglioComunicazione);
	String ultimoStatoCompletato = riga.getUltimoStatoCompletato();
	StatoComunicazioniGenEnum ultimoStatoCompletatoEnum = retrieveMappedUltimoStato(ultimoStatoCompletato);
	if(StatoComunicazioniGenEnum.CONCLUSA_CON_WARNING != ultimoStatoCompletatoEnum){
	    comunicazioniMassiveDettaglioDAO.salvaErrore(idDettaglioComunicazione, null); //Non lo eliminiamo l'errore in questo caso  
	}
	IWorkFlowStep<ConfigurazioniComunicazioneGen> step = getCurrent(ultimoStatoCompletatoEnum);
	if (step != null) {
	    step.elabora(idDettaglioComunicazione, configurazione);
	}
    }

    protected IWorkFlowStep<ConfigurazioniComunicazioneGen> getCurrent(StatoComunicazioniGenEnum statoComunicazioniGenEnum) {

	return passaggiWorkFlow.get(statoComunicazioniGenEnum);
    }

    @Override
    public StatoComunicazioniGenEnum getStatoConclusivo() {

	return StatoComunicazioniGenEnum.INVIATA;
    }
    
    public static StatoComunicazioniGenEnum retrieveMappedUltimoStato(String ultimoStatoCompletato){
	if(ultimoStatoCompletato.startsWith(WorkFlowComunicazioniGenService.CONCLUSA_CON_ERRORE)){
	    return StatoComunicazioniGenEnum.CONCLUSA_CON_WARNING;
	}else if(ultimoStatoCompletato.equals(WorkFlowComunicazioniGenService.APPIO_SCHEDULATA)){
	    return StatoComunicazioniGenEnum.APPIO_SCHEDULATA; //Se è appioschedulata si va su appioschedulata, altrimenti si va su warning
	}else if(ultimoStatoCompletato.startsWith(WorkFlowComunicazioniGenService.APPIO_SCHEDULATA)){
	    return StatoComunicazioniGenEnum.CONCLUSA_CON_WARNING;
	}else{
	    return StatoComunicazioniGenEnum.valueOf(ultimoStatoCompletato);
	}
    }
}
