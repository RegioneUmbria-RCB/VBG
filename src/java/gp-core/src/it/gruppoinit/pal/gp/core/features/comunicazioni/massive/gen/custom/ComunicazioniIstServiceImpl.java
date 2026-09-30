package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.custom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.BollMassiveT;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.IEventoMassiva.ContestoComunicazioneEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.ComunicazioniGenServiceImpl;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.ConfigurazioniComunicazioneGen;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.IWorkFlowComunicazioniGenService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.QueriesConstants;

@Service
public class ComunicazioniIstServiceImpl extends ComunicazioniGenServiceImpl{
    
    private IWorkFlowComunicazioniGenService workFlowComunicazioniMercatiService;
    
    @Autowired
    public void setWorkFlowComunicazioniMercatiService(@Qualifier("workFlowComunicazioniIstanzeService") IWorkFlowComunicazioniGenService workFlowComunicazioniMercatiService) {
    
        this.workFlowComunicazioniMercatiService = workFlowComunicazioniMercatiService;
    }


    @Override
    public boolean exists(Integer idTestata) {

	return comunicazioniMassiveGenDAO.existsTestata(QueriesConstants.ISTANZETESTATECHCK, idTestata);
    }


    @Override
    protected IWorkFlowComunicazioniGenService giveWorkFlowComunicazioniGenService() {

	return workFlowComunicazioniMercatiService;
    }


    @Override
    protected ContestoComunicazioneEnum giveContesto() {
	return ContestoComunicazioneEnum.ISTANZE;
    }


    @Override
    protected void collegaRigheAComunicazioni(int idTestata, ConfigurazioniComunicazioneGen configurazione) {

	creazioneMassiveDettaglioService.collegaRigheIstanzeAComunicazioni(idTestata, configurazione);
    }


    @Override
    protected String giveMessaggioCancellazione(int idTestata, Responsabili operatore) {
	return "##eliminaMassiva_istanze## L'operatore " +
		operatore +
		" ha eliminato la massiva con codice " +
		idTestata;
    }
    
    
    
}
