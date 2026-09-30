package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.workflow;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.gruppoinit.pal.gp.core.domain.MassiveDettaglio;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDettaglioDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.ConfigurazioneComunicazioniBollettazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.StatoComunicazioniBollettazioneEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.IWorkFlowStep;

@Service
public class WorkFlowComunicazioniBollettazioneServiceImpl implements IWorkFlowComunicazioniBollettazioneService {

    private Map<StatoComunicazioniBollettazioneEnum, IWorkFlowStep> passaggiWorkFlow = new HashMap<StatoComunicazioniBollettazioneEnum, IWorkFlowStep>();
    @Autowired
    private IComunicazioniMassiveDettaglioDAO comunicazioniMassiveDettaglioDAO;

    @Autowired
    public WorkFlowComunicazioniBollettazioneServiceImpl(GenerazioneAllegatiStepServiceImpl generazioneAllegatiStepServiceImpl,
	    MettiAllaFirmaServiceImpl mettiAllaFirmaServiceImpl, ProtocollaServiceImpl protocollaServiceImpl,
	    InviaMailServiceImpl inviaMailServiceImpl, IComunicazioniMassiveDettaglioDAO massiveDao,
	    AllegatiFissiStepServiceImpl allegatiFissiStepServiceImpl, AllegatiCompilabiliServiceImpl allegatiCompilabiliServiceImpl,
	    IComunicazioniMassiveDettaglioDAO comunicazioniMassiveDettaglioDAO) {

	passaggiWorkFlow.put(StatoComunicazioniBollettazioneEnum.PRONTA_PER_ELABORAZIONE, generazioneAllegatiStepServiceImpl);
	passaggiWorkFlow.put(StatoComunicazioniBollettazioneEnum.PRONTA_PER_ALLEGATI_FISSI, allegatiFissiStepServiceImpl);
	passaggiWorkFlow.put(StatoComunicazioniBollettazioneEnum.PRONTA_PER_ALLEGATI_COMPILABILI, allegatiCompilabiliServiceImpl);
	passaggiWorkFlow.put(StatoComunicazioniBollettazioneEnum.PRONTA_ALLA_FIRMA, mettiAllaFirmaServiceImpl);
	passaggiWorkFlow.put(StatoComunicazioniBollettazioneEnum.PRONTA_PER_PROTOCOLLAZIONE, protocollaServiceImpl);
	passaggiWorkFlow.put(StatoComunicazioniBollettazioneEnum.PRONTA_ALL_INVIO, inviaMailServiceImpl);
	this.comunicazioniMassiveDettaglioDAO = comunicazioniMassiveDettaglioDAO;
    }

    private IWorkFlowStep getCurrent(StatoComunicazioniBollettazioneEnum statoComunicazioniBollettazioneEnum) {

	return passaggiWorkFlow.get(statoComunicazioniBollettazioneEnum);
    }

    @Override
    @Transactional(noRollbackFor = RuntimeException.class)
    public void elabora(int idDettaglioComunicazione, ConfigurazioneComunicazioniBollettazione configurazione) {

	MassiveDettaglio riga = comunicazioniMassiveDettaglioDAO.getById(idDettaglioComunicazione);
	comunicazioniMassiveDettaglioDAO.salvaErrore(idDettaglioComunicazione, null);
	String ultimoStatoCompletato = riga.getUltimoStatoCompletato();
	IWorkFlowStep step = getCurrent(StatoComunicazioniBollettazioneEnum.valueOf(ultimoStatoCompletato));
	if (step != null) {
	    step.elabora(idDettaglioComunicazione, configurazione);
	}
    }

    @Override
    public StatoComunicazioniBollettazioneEnum getStatoConclusivo() {

	return StatoComunicazioniBollettazioneEnum.INVIATA;
    }
}
