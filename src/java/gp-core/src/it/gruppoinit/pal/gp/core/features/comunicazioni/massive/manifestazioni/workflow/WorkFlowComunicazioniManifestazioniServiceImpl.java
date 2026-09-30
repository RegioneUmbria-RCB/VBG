package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni.workflow;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.MassiveDettaglio;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDettaglioDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.IWorkFlowStep;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni.ConfigurazioneComunicazioniManifestazioni;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni.StatoComunicazioniManifestazioniEnum;

@Service
public class WorkFlowComunicazioniManifestazioniServiceImpl implements IWorkFlowComunicazioniManifestazioniService {

    @SuppressWarnings("rawtypes")
    private Map<StatoComunicazioniManifestazioniEnum, IWorkFlowStep> passaggiWorkFlow = new HashMap<StatoComunicazioniManifestazioniEnum, IWorkFlowStep>();
    private IComunicazioniMassiveDettaglioDAO comunicazioniMassiveDettaglioDAO;

    @Autowired
    public void setComunicazioniMassiveDettaglioDAO(IComunicazioniMassiveDettaglioDAO comunicazioniMassiveDettaglioDAO) {

	this.comunicazioniMassiveDettaglioDAO = comunicazioniMassiveDettaglioDAO;
    }

    @Autowired
    public WorkFlowComunicazioniManifestazioniServiceImpl(ManifestazioniProtocollaServiceImpl protocollaServiceImpl,
	    ManifestazioniInviaMailServiceImpl inviaMailServiceImpl) {

	this.passaggiWorkFlow.put(StatoComunicazioniManifestazioniEnum.PRONTA_PER_ELABORAZIONE, protocollaServiceImpl);
	this.passaggiWorkFlow.put(StatoComunicazioniManifestazioniEnum.PRONTA_ALL_INVIO, inviaMailServiceImpl);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void elabora(int idDettaglioComunicazione, ConfigurazioneComunicazioniManifestazioni configurazione) {

	MassiveDettaglio riga = this.comunicazioniMassiveDettaglioDAO.getById(idDettaglioComunicazione);
	this.comunicazioniMassiveDettaglioDAO.salvaErrore(idDettaglioComunicazione, null);
	String ultimoStatoCompletato = riga.getUltimoStatoCompletato();
	IWorkFlowStep step = getCurrent(StatoComunicazioniManifestazioniEnum.valueOf(ultimoStatoCompletato));
	if (step != null) {
	    step.elabora(idDettaglioComunicazione, configurazione);
	}
    }

    @SuppressWarnings("rawtypes")
    private IWorkFlowStep getCurrent(StatoComunicazioniManifestazioniEnum statoEnum) {

	return passaggiWorkFlow.get(statoEnum);
    }

    @Override
    public StatoComunicazioniManifestazioniEnum getStatoConclusivo() {

	return StatoComunicazioniManifestazioniEnum.INVIATA;
    }
}
