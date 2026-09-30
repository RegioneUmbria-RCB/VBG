package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.workflow;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.MassiveDettaglio;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDettaglioDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.ConfigurazioneComunicazioniCommissioni;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.StatoComunicazioniCommissioniEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.IWorkFlowStep;

@Service
public class WorkFlowComunicazioniCommissioniServiceImpl implements IWorkFlowComunicazioniCommissioniService {

    private Map<StatoComunicazioniCommissioniEnum, IWorkFlowStep<ConfigurazioneComunicazioniCommissioni>> passaggiWorkFlow = new HashMap<StatoComunicazioniCommissioniEnum, IWorkFlowStep<ConfigurazioneComunicazioniCommissioni>>();
    private IComunicazioniMassiveDettaglioDAO comunicazioniMassiveDettaglioDAO;

    @Autowired
    public WorkFlowComunicazioniCommissioniServiceImpl(IComunicazioniMassiveDettaglioDAO comunicazioniMassiveDettaglioDAO,
	    CommissioniGenerazioneAllegatiStepServiceImpl generazioneAllegatiStepServiceImpl,
	    CommissioniAllegatiFissiStepServiceImpl allegatiFissiStepServiceImpl,
	    CommissioniAllegatiCompilabiliServiceImpl allegatiCompilabiliServiceImpl, CommissioniMettiAllaFirmaServiceImpl mettiAllaFirmaServiceImpl,
	    CommissioniProtocollaServiceImpl protocollaServiceImpl, CommissioniInviaMailServiceImpl inviaMailServiceImpl) {

	passaggiWorkFlow.put(StatoComunicazioniCommissioniEnum.PRONTA_PER_ELABORAZIONE, generazioneAllegatiStepServiceImpl);
	passaggiWorkFlow.put(StatoComunicazioniCommissioniEnum.PRONTA_PER_ALLEGATI_FISSI, allegatiFissiStepServiceImpl);
	passaggiWorkFlow.put(StatoComunicazioniCommissioniEnum.PRONTA_PER_ALLEGATI_COMPILABILI, allegatiCompilabiliServiceImpl);
	passaggiWorkFlow.put(StatoComunicazioniCommissioniEnum.PRONTA_ALLA_FIRMA, mettiAllaFirmaServiceImpl);
	passaggiWorkFlow.put(StatoComunicazioniCommissioniEnum.PRONTA_PER_PROTOCOLLAZIONE, protocollaServiceImpl);
	passaggiWorkFlow.put(StatoComunicazioniCommissioniEnum.PRONTA_ALL_INVIO, inviaMailServiceImpl);
	this.comunicazioniMassiveDettaglioDAO = comunicazioniMassiveDettaglioDAO;
    }

    @Override
    public void elabora(int idDettaglioComunicazione, ConfigurazioneComunicazioniCommissioni configurazione) {

	MassiveDettaglio riga = comunicazioniMassiveDettaglioDAO.getById(idDettaglioComunicazione);
	comunicazioniMassiveDettaglioDAO.salvaErrore(idDettaglioComunicazione, null);
	String ultimoStatoCompletato = riga.getUltimoStatoCompletato();
	IWorkFlowStep<ConfigurazioneComunicazioniCommissioni> step = getCurrent(StatoComunicazioniCommissioniEnum.valueOf(ultimoStatoCompletato));
	if (step != null) {
	    step.elabora(idDettaglioComunicazione, configurazione);
	}
    }

    private IWorkFlowStep<ConfigurazioneComunicazioniCommissioni> getCurrent(StatoComunicazioniCommissioniEnum statoComunicazioniCommissioniEnum) {

	return passaggiWorkFlow.get(statoComunicazioniCommissioniEnum);
    }

    @Override
    public StatoComunicazioniCommissioniEnum getStatoConclusivo() {

	return StatoComunicazioniCommissioniEnum.INVIATA;
    }
}
