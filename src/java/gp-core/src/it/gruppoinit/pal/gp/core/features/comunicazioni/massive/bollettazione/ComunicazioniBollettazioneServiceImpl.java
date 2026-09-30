package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.annotations.lock.Lockable;
import it.gruppoinit.pal.gp.core.domain.BollMassiveT;
import it.gruppoinit.pal.gp.core.domain.MassiveDettDocdafirmare;
import it.gruppoinit.pal.gp.core.domain.MassiveDettaglio;
import it.gruppoinit.pal.gp.core.domain.MassiveTestata;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.features.bollettazione.comunicazionimassive.IBollettazioneComunicazioniMassiveDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDettaglioDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.ICreazioneMassiveTestataService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IWorkflowComunicazioniService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.workflow.IWorkFlowComunicazioniBollettazioneService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.IConfigurazioneComunicazioneService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.DettagliMailComunicazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.ListaComunicazioniResoconti;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.ResocontoOperazioniMassive;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.RigaComunicazioneDettagliata;
import it.gruppoinit.pal.gp.core.features.firmadigitale.DocumentiDaFirmareDAO;
import it.gruppoinit.pal.gp.core.utils.ICurrentDateService;
import it.gruppoinit.pal.gp.core.utils.LoggerCancellazioni;

@Service
public class ComunicazioniBollettazioneServiceImpl implements IComunicazioniBollettazioneService {

    private ICreazioneMassiveTestataService creazioneMassiveTestataService;
    private ICreazioneMassiveDettaglioService creazioneMassiveDettaglioService;
    private IConfigurazioneComunicazioneService configurazioneComunicazioneService;
    private IComunicazioniMassiveDettaglioDAO comunicazioniMassiveDettaglioDAO;
    private IComunicazioniMassiveDAO comunicazioniMassiveDAO;
    private IWorkFlowComunicazioniBollettazioneService workFlowComunicazioniBollettazioneService;
    private ICurrentDateService currentDateService;
    private DocumentiDaFirmareDAO documentiDaFirmareDAO;
    private IBollettazioneComunicazioniMassiveDAO bollettazioneComunicazioniMassiveDAO;

    @Autowired
    public void setBollettazioneComunicazioniMassiveDAO(IBollettazioneComunicazioniMassiveDAO bollettazioneComunicazioniMassiveDAO) {

	this.bollettazioneComunicazioniMassiveDAO = bollettazioneComunicazioniMassiveDAO;
    }

    @Autowired
    public ComunicazioniBollettazioneServiceImpl(ICreazioneMassiveTestataService creazioneMassiveTestataService,
	    ICreazioneMassiveDettaglioService creazioneMassiveDettaglioService,
	    IConfigurazioneComunicazioneService configurazioneComunicazioneService,
	    IComunicazioniMassiveDettaglioDAO comunicazioniMassiveDettaglioDAO, IComunicazioniMassiveDAO comunicazioniMassiveDAO,
	    IWorkFlowComunicazioniBollettazioneService workFlowComunicazioniBollettazioneService, ICurrentDateService currentDateService,
	    DocumentiDaFirmareDAO documentiDaFirmareDAO) {

	super();
	this.creazioneMassiveTestataService = creazioneMassiveTestataService;
	this.creazioneMassiveDettaglioService = creazioneMassiveDettaglioService;
	this.configurazioneComunicazioneService = configurazioneComunicazioneService;
	this.comunicazioniMassiveDettaglioDAO = comunicazioniMassiveDettaglioDAO;
	this.comunicazioniMassiveDAO = comunicazioniMassiveDAO;
	this.workFlowComunicazioniBollettazioneService = workFlowComunicazioniBollettazioneService;
	this.currentDateService = currentDateService;
	this.documentiDaFirmareDAO = documentiDaFirmareDAO;
    }

    @Override
    public int creaNuovaComunicazione(ConfigurazioneComunicazioniBollettazione configurazione) {

	int idTestata = creazioneMassiveTestataService.insert(configurazione);
	creazioneMassiveDettaglioService.collegaRigheBollettazioneAComunicazioni(idTestata, configurazione);
	return idTestata;
    }

    @Lockable
    @Override
    public void elabora(int idTestata) {

	ConfigurazioneComunicazioniBollettazione configurazione = new ConfigurazioneComunicazioniBollettazione();
	configurazioneComunicazioneService.getById(idTestata, configurazione);
	List<MassiveDettaglio> righe = comunicazioniMassiveDettaglioDAO.getRigheByIdTestata(idTestata,
		workFlowComunicazioniBollettazioneService.getStatoConclusivo().name());
	for (MassiveDettaglio riga : righe) {
	    workFlowComunicazioniBollettazioneService.elabora(riga.getId().getCodice(), configurazione);
	}
    }

    @Lockable
    @Override
    public void elaboraRiga(int idRiga) {

	MassiveDettaglio riga = this.comunicazioniMassiveDettaglioDAO.getById(idRiga);
	ConfigurazioneComunicazioniBollettazione configurazione = new ConfigurazioneComunicazioniBollettazione();
	configurazioneComunicazioneService.getById(riga.getMassiveTestata().getId().getCodice(), configurazione);
	workFlowComunicazioniBollettazioneService.elabora(riga.getId().getCodice(), configurazione);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List<ListaComunicazioniResoconti> creaListaTestata(Integer idBollettazione) {

	// recupero tutte le testate relative all'idbollettazione	
	List<Integer> listIdTestate = creazioneMassiveTestataService.findByIdBollettazione(idBollettazione);
	List<ListaComunicazioniResoconti> comunicazioniResoconti = new ArrayList<ListaComunicazioniResoconti>();
	for (Integer idTestata : listIdTestate) {
	    comunicazioniResoconti.add(this.getResoconto(idTestata));
	}
	return comunicazioniResoconti;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public ListaComunicazioniResoconti getResoconto(Integer idTestata) {

	MassiveTestata mt = this.comunicazioniMassiveDAO.getTestataById(idTestata);
	//creo ListaComunicazioniResoconti e setto le varie proprietà
	ListaComunicazioniResoconti listaComunicazioniResoconti = new ListaComunicazioniResoconti(
		this.workFlowComunicazioniBollettazioneService.getStatoConclusivo());
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

	List<Integer> codiceOggettoAllegati = this.comunicazioniMassiveDettaglioDAO.getCodiciOggettoMassiveDAllegatiByIdDettaglio(idRiga);
	List<MassiveDettDocdafirmare> docDaFirmare = this.comunicazioniMassiveDettaglioDAO.findDocDaFirmarePerDettaglio(idRiga);
	List<DettagliMailComunicazione> dettagliMailRiga = this.comunicazioniMassiveDettaglioDAO.findDettagliMailInviate(idRiga);
	return RigaComunicazioneDettagliata.FromDatiDB(this.comunicazioniMassiveDettaglioDAO.getById(idRiga), codiceOggettoAllegati, docDaFirmare,
		dettagliMailRiga);
    }

    @Override
    public void eliminaMassiva(int idTestata, Responsabili operatore) {

	BollMassiveT bollMassiva = this.bollettazioneComunicazioniMassiveDAO.findByIdTestata(idTestata);
	String msgCancellazione = "##eliminaMassiva_bollettazione## L'operatore " +
		operatore +
		" ha eliminato la massiva con codice " +
		idTestata +
		" della bollettazione " +
		bollMassiva.getBollGestTestata().getId().getCodice();
	LoggerCancellazioni.log(msgCancellazione);
	this.comunicazioniMassiveDAO.eliminaMassiva(idTestata, operatore.getId().getCodice(), this.currentDateService.getCurrentDate());
	// ELIMINA massive_dett_docdafirmare
	List<Integer> documentiDaFirmarePerIdTestata = this.comunicazioniMassiveDettaglioDAO.getDocumentiDaFirmarePerIdTestata(idTestata);
	this.comunicazioniMassiveDettaglioDAO.eliminaMassiveDocDaFirmareByIdDocDaFirmare(documentiDaFirmarePerIdTestata);
	this.documentiDaFirmareDAO.eliminaDocDaFirmare(documentiDaFirmarePerIdTestata);
    }

    @Override
    public boolean exists(Integer idTestata) {

	return this.bollettazioneComunicazioniMassiveDAO.exists(idTestata);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public IWorkflowComunicazioniService getWorkFlowService() {

	return workFlowComunicazioniBollettazioneService;
    }
}
