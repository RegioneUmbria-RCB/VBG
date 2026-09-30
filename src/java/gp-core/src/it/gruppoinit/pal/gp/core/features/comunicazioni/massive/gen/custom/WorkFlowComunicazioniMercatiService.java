package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.custom;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDettaglioDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.IWorkFlowStep;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.ConfigurazioniComunicazioneGen;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.StatoComunicazioniGenEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.WorkFlowComunicazioniGenService;
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
public class WorkFlowComunicazioniMercatiService extends WorkFlowComunicazioniGenService {

    private Map<StatoComunicazioniGenEnum, IWorkFlowStep<ConfigurazioniComunicazioneGen>> passaggiWorkFlow = new HashMap<StatoComunicazioniGenEnum, IWorkFlowStep<ConfigurazioniComunicazioneGen>>();

    @Autowired
    public WorkFlowComunicazioniMercatiService(IComunicazioniMassiveDettaglioDAO comunicazioniMassiveDettaglioDAO,
	    GenGenerazioneAllegatiStepServiceImpl generazioneAllegatiStepServiceImpl, GenAllegatiFissiStepServiceImpl allegatiFissiStepServiceImpl,
	    GenAllegatiCompilabiliServiceImpl allegatiCompilabiliServiceImpl, GenMettiAllaFirmaServiceImpl mettiAllaFirmaServiceImpl,
	    GenProtocollaServiceImpl protocollaServiceImpl, GenInviaMailServiceImpl inviaMailServiceImpl,
	    GenInviaAppioServiceImpl inviaAppioServiceImpl, GenAppIoSchedulataStepServiceImpl genAppIoSchedulataStepServiceImpl,
	    GenAppIoStatoCodaStepServiceImpl genAppIoStatoCodaStepServiceImpl, GenRielaboraServiceImpl genRielaboraServiceImpl) {

	super(comunicazioniMassiveDettaglioDAO, generazioneAllegatiStepServiceImpl, allegatiFissiStepServiceImpl, allegatiCompilabiliServiceImpl,
		mettiAllaFirmaServiceImpl, protocollaServiceImpl, inviaMailServiceImpl, inviaAppioServiceImpl, genAppIoSchedulataStepServiceImpl,
		genAppIoStatoCodaStepServiceImpl, genRielaboraServiceImpl);

	// TODO Auto-generated constructor stub
    }

    @Override
    protected IWorkFlowStep<ConfigurazioniComunicazioneGen> getCurrent(StatoComunicazioniGenEnum statoComunicazioniGenEnum) {

	if (this.passaggiWorkFlow.containsKey(statoComunicazioniGenEnum)) {
	    return passaggiWorkFlow.get(statoComunicazioniGenEnum);
	}
	return super.getCurrent(statoComunicazioniGenEnum);
    }
}
