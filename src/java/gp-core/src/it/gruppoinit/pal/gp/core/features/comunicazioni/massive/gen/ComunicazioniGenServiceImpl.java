package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.BollMassiveT;
import it.gruppoinit.pal.gp.core.domain.MassiveDettaglio;
import it.gruppoinit.pal.gp.core.domain.MassiveTestata;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDettaglioDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.ICreazioneMassiveTestataService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IWorkflowComunicazioniService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.ConfigurazioneComunicazioniCommissioni;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.IConfigurazioneComunicazioneService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.IEventoMassiva.ContestoComunicazioneEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.ListaComunicazioniResoconti;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.ResocontoOperazioniMassive;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.RigaComunicazioneDettagliata;
import it.gruppoinit.pal.gp.core.features.firmadigitale.DocumentiDaFirmareDAO;
import it.gruppoinit.pal.gp.core.utils.ICurrentDateService;
import it.gruppoinit.pal.gp.core.utils.LoggerCancellazioni;

@Service
public class ComunicazioniGenServiceImpl implements IComunicazioniGenService {

    private ICreazioneMassiveTestataService creazioneMassiveTestataService;
    private IComunicazioniMassiveDAO comunicazioniMassiveDAO;
    protected ICreazioneMassiveGDettaglioService creazioneMassiveDettaglioService;
    protected IComunicazioniMassiveGenDAO comunicazioniMassiveGenDAO;
    private IConfigurazioneComunicazioneService configurazioneComunicazioneService;
    private IComunicazioniMassiveDettaglioDAO comunicazioniMassiveDettaglioDAO;
    private ICurrentDateService currentDateService;
    private DocumentiDaFirmareDAO documentiDaFirmareDAO;
    
    @Autowired
    public void setCreazioneMassiveTestataService(ICreazioneMassiveTestataService creazioneMassiveTestataService) {

	this.creazioneMassiveTestataService = creazioneMassiveTestataService;
    }
    
    @Autowired
    public void setComunicazioniMassiveDAO(IComunicazioniMassiveDAO comunicazioniMassiveDAO) {

	this.comunicazioniMassiveDAO = comunicazioniMassiveDAO;
    }
    
   
    @Autowired
    public void setCreazioneMassiveDettaglioService(ICreazioneMassiveGDettaglioService creazioneMassiveDettaglioService) {
    
        this.creazioneMassiveDettaglioService = creazioneMassiveDettaglioService;
    }
    
    @Autowired
    public void setComunicazioniMassiveGenDAO(IComunicazioniMassiveGenDAO comunicazioniMassiveGenDAO) {
	    
        this.comunicazioniMassiveGenDAO = comunicazioniMassiveGenDAO;
    }
    
    @Autowired
    public void setConfigurazioneComunicazioneService(IConfigurazioneComunicazioneService configurazioneComunicazioneService) {
    
        this.configurazioneComunicazioneService = configurazioneComunicazioneService;
    }
    
    @Autowired
    public void setComunicazioniMassiveDettaglioDAO(IComunicazioniMassiveDettaglioDAO comunicazioniMassiveDettaglioDAO) {
    
        this.comunicazioniMassiveDettaglioDAO = comunicazioniMassiveDettaglioDAO;
    }
    
    @Autowired
    public void setCurrentDateService(ICurrentDateService currentDateService) {
    
        this.currentDateService = currentDateService;
    }

    @Autowired
    public void setDocumentiDaFirmareDAO(DocumentiDaFirmareDAO documentiDaFirmareDAO) {
    
        this.documentiDaFirmareDAO = documentiDaFirmareDAO;
    }

    @Override
    public int creaNuovaComunicazione(ConfigurazioniComunicazioneGen configurazione) {

	int idTestata = creazioneMassiveTestataService.insert(configurazione);
	collegaRigheAComunicazioni(idTestata, configurazione);
	return idTestata;
	
    }
    
    protected void collegaRigheAComunicazioni(int idTestata, ConfigurazioniComunicazioneGen configurazione) {
	throw new RuntimeException("not implemented");
    }

    @Override
    public void elabora(int idTestata) {

	ConfigurazioniComunicazioneGen configurazione = new ConfigurazioniComunicazioneGen(giveContesto());
	configurazioneComunicazioneService.getById(idTestata, configurazione);
	List<MassiveDettaglio> righe = comunicazioniMassiveDettaglioDAO.getRigheByIdTestata(idTestata,
		giveWorkFlowComunicazioniGenService().getStatoConclusivo().name());
	for (MassiveDettaglio riga : righe) {
	    giveWorkFlowComunicazioniGenService().elabora(riga.getId().getCodice(), configurazione);
	}
    }

    @Override
    public void elaboraRiga(int idRiga) {

	MassiveDettaglio riga = this.comunicazioniMassiveDettaglioDAO.getById(idRiga);
	ConfigurazioniComunicazioneGen configurazione = new ConfigurazioniComunicazioneGen(giveContesto());
	configurazioneComunicazioneService.getById(riga.getMassiveTestata().getId().getCodice(), configurazione);
	giveWorkFlowComunicazioniGenService().elabora(riga.getId().getCodice(), configurazione);
    }
    
    protected ContestoComunicazioneEnum giveContesto() {
	throw new RuntimeException("giveContesto: no contesto valid");
    }

    @Override
    public List<ListaComunicazioniResoconti> creaListaTestata(Integer idRiferimento) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<ListaComunicazioniResoconti> creaListaTestataGen(String sql, Object[] params, String fkscalar) {
	
	List<Integer> listIdTestate = this.creazioneMassiveTestataService.findIdTestataByGen(sql, params, fkscalar);
	List<ListaComunicazioniResoconti> comunicazioniResoconti = new ArrayList<ListaComunicazioniResoconti>();
	for (Integer idTestata : listIdTestate) {
	    comunicazioniResoconti.add(this.getResoconto(idTestata));
	}
	return comunicazioniResoconti;
	
    }

    @Override
    public ListaComunicazioniResoconti getResoconto(Integer idTestata) {

	MassiveTestata mt = this.comunicazioniMassiveDAO.getTestataById(idTestata);
	//creo ListaComunicazioniResoconti e setto le varie proprietà
	ListaComunicazioniResoconti listaComunicazioniResoconti = new ListaComunicazioniResoconti(
		this.giveWorkFlowComunicazioniGenService().getStatoConclusivo());
	listaComunicazioniResoconti.setId(idTestata);
	listaComunicazioniResoconti.setDescrizione(mt.getDescrizione());
	listaComunicazioniResoconti.setData(mt.getDataComunicazione());
	// creo ResocontoOperazioniMassive
	List<ResocontoOperazioniMassive> listRom = this.creazioneMassiveTestataService.findResocontoOperazioniMassiveById(idTestata);
	//setto la lista nel resoconto
	listaComunicazioniResoconti.setResocontoOperazioniMassive(listRom);
	return listaComunicazioniResoconti;
    }

    @Override
    public RigaComunicazioneDettagliata getRigaDettagliata(int idRiga) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void eliminaMassiva(int idTestata, Responsabili operatore) {
	LoggerCancellazioni.log(giveMessaggioCancellazione(idTestata, operatore));
	this.comunicazioniMassiveDAO.eliminaMassiva(idTestata, operatore.getId().getCodice(), this.currentDateService.getCurrentDate());
	// ELIMINA massive_dett_docdafirmare
	List<Integer> documentiDaFirmarePerIdTestata = this.comunicazioniMassiveDettaglioDAO.getDocumentiDaFirmarePerIdTestata(idTestata);
	this.comunicazioniMassiveDettaglioDAO.eliminaMassiveDocDaFirmareByIdDocDaFirmare(documentiDaFirmarePerIdTestata);
	this.documentiDaFirmareDAO.eliminaDocDaFirmare(documentiDaFirmarePerIdTestata);
    }
    
    protected String giveMessaggioCancellazione(int idTestata, Responsabili operatore) {
	throw new RuntimeException("Not implemented");
    }

    @Override
    public boolean exists(Integer idTestata) {

	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public IWorkflowComunicazioniService getWorkFlowService() {

	return giveWorkFlowComunicazioniGenService();
    }
    
    protected IWorkFlowComunicazioniGenService giveWorkFlowComunicazioniGenService() {
	return null;
    }
}
