package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.annotations.lock.Lockable;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.MassiveDettDestinatari;
import it.gruppoinit.pal.gp.core.domain.MassiveDettDocdafirmare;
import it.gruppoinit.pal.gp.core.domain.MassiveDettaglio;
import it.gruppoinit.pal.gp.core.domain.MassiveTestata;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.features.commissioni.ICommissioniService;
import it.gruppoinit.pal.gp.core.features.commissioni.auditing.ICommissioniAuditingService;
import it.gruppoinit.pal.gp.core.features.commissioni.auditing.messaggi.MessaggioComunicazioneCommissioneCreata;
import it.gruppoinit.pal.gp.core.features.commissioni.auditing.messaggi.MessaggioComunicazioneCommissioneEliminata;
import it.gruppoinit.pal.gp.core.features.commissioni.comunicazionimassive.ICommissioniComunicazioniMassiveDAO;
import it.gruppoinit.pal.gp.core.features.commissioni.model.CommissioneModel;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDettaglioDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.ICreazioneMassiveTestataService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IWorkflowComunicazioniService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.ICreazioneMassiveDettaglioService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.upgr.UpgrMassiveDestinatariHelper;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.workflow.IWorkFlowComunicazioniCommissioniService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.IConfigurazioneComunicazioneService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.DettagliMailComunicazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.ListaComunicazioniResoconti;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.ResocontoOperazioniMassive;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.RigaComunicazioneDettagliata;
import it.gruppoinit.pal.gp.core.features.firmadigitale.DocumentiDaFirmareDAO;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.utils.ICurrentDateService;

@Service
public class ComunicazioniCommissioniServiceImpl implements IComunicazioniCommissioniService {

    private ICreazioneMassiveTestataService creazioneMassiveTestataService;
    private ICreazioneMassiveDettaglioService creazioneMassiveDettaglioService;
    private IConfigurazioneComunicazioneService configurazioneComunicazioneService;
    private IComunicazioniMassiveDettaglioDAO comunicazioniMassiveDettaglioDAO;
    private IComunicazioniMassiveDAO comunicazioniMassiveDAO;
    private ICurrentDateService currentDateService;
    private DocumentiDaFirmareDAO documentiDaFirmareDAO;
    private IWorkFlowComunicazioniCommissioniService workFlowComunicazioniCommissioniService;
    private ICommissioniAuditingService commissioniAuditingService;
    private UserSecurityService userSecurityService;
    private ICommissioniService commissioniService;
    private ICommissioniComunicazioniMassiveDAO commissioniComunicazioniMassiveDAO;

    @Autowired
    public void setCommissioniComunicazioniMassiveDAO(ICommissioniComunicazioniMassiveDAO commissioniComunicazioniMassiveDAO) {

	this.commissioniComunicazioniMassiveDAO = commissioniComunicazioniMassiveDAO;
    }

    @Autowired
    public ComunicazioniCommissioniServiceImpl(ICreazioneMassiveTestataService creazioneMassiveTestataService,
	    ICreazioneMassiveDettaglioService creazioneMassiveDettaglioService,
	    IConfigurazioneComunicazioneService configurazioneComunicazioneService,
	    IComunicazioniMassiveDettaglioDAO comunicazioniMassiveDettaglioDAO, IComunicazioniMassiveDAO comunicazioniMassiveDAO,
	    ICurrentDateService currentDateService, DocumentiDaFirmareDAO documentiDaFirmareDAO,
	    IWorkFlowComunicazioniCommissioniService workFlowComunicazioniCommissioniService, ICommissioniAuditingService commissioniAuditingService,
	    UserSecurityService userSecurityService, ICommissioniService commissioniService) {

	this.configurazioneComunicazioneService = configurazioneComunicazioneService;
	this.creazioneMassiveDettaglioService = creazioneMassiveDettaglioService;
	this.creazioneMassiveTestataService = creazioneMassiveTestataService;
	this.comunicazioniMassiveDettaglioDAO = comunicazioniMassiveDettaglioDAO;
	this.comunicazioniMassiveDAO = comunicazioniMassiveDAO;
	this.currentDateService = currentDateService;
	this.documentiDaFirmareDAO = documentiDaFirmareDAO;
	this.workFlowComunicazioniCommissioniService = workFlowComunicazioniCommissioniService;
	this.commissioniAuditingService = commissioniAuditingService;
	this.userSecurityService = userSecurityService;
	this.commissioniService = commissioniService;
    }

    @Override
    public int creaNuovaComunicazione(ConfigurazioneComunicazioniCommissioni configurazione) {

	int idTestata = creazioneMassiveTestataService.insert(configurazione);
	creazioneMassiveDettaglioService.collegaRigheCommissioniAComunicazioni(idTestata, configurazione);
	CommissioneModel commissioneModel = commissioniService.getCommissione(configurazione.getIdCommissione());
	commissioniAuditingService.log(configurazione.getIdCommissione(),
		new MessaggioComunicazioneCommissioneCreata(commissioneModel.getNumeroProtocollo(), this.getResponsabile(), idTestata));
	return idTestata;
    }

    @Lockable
    @Override
    public void elabora(int idTestata) {

	ConfigurazioneComunicazioniCommissioni configurazione = new ConfigurazioneComunicazioniCommissioni();
	configurazioneComunicazioneService.getById(idTestata, configurazione);
	List<MassiveDettaglio> righe = comunicazioniMassiveDettaglioDAO.getRigheByIdTestata(idTestata,
		workFlowComunicazioniCommissioniService.getStatoConclusivo().name());
	for (MassiveDettaglio riga : righe) {
	    workFlowComunicazioniCommissioniService.elabora(riga.getId().getCodice(), configurazione);
	}
    }

    @Override
    public void elaboraRiga(int idRiga) {

	MassiveDettaglio riga = this.comunicazioniMassiveDettaglioDAO.getById(idRiga);
	ConfigurazioneComunicazioniCommissioni configurazione = new ConfigurazioneComunicazioniCommissioni();
	configurazioneComunicazioneService.getById(riga.getMassiveTestata().getId().getCodice(), configurazione);
	workFlowComunicazioniCommissioniService.elabora(riga.getId().getCodice(), configurazione);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List<ListaComunicazioniResoconti> creaListaTestata(Integer id) {

	List<Integer> listIdTestate = creazioneMassiveTestataService.findByIdCommissioni(id);
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
		this.workFlowComunicazioniCommissioniService.getStatoConclusivo());
	listaComunicazioniResoconti.setId(idTestata);
	listaComunicazioniResoconti.setDescrizione(mt.getDescrizione());
	listaComunicazioniResoconti.setData(mt.getDataComunicazione());
	// creo ResocontoOperazioniMassive
	List<ResocontoOperazioniMassive> listRom = creazioneMassiveTestataService.findResocontoOperazioniMassiveById(idTestata);
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

	this.comunicazioniMassiveDAO.eliminaMassiva(idTestata, operatore.getId().getCodice(), this.currentDateService.getCurrentDate());
	// ELIMINA massive_dett_docdafirmare
	List<Integer> documentiDaFirmarePerIdTestata = this.comunicazioniMassiveDettaglioDAO.getDocumentiDaFirmarePerIdTestata(idTestata);
	this.comunicazioniMassiveDettaglioDAO.eliminaMassiveDocDaFirmareByIdDocDaFirmare(documentiDaFirmarePerIdTestata);
	this.documentiDaFirmareDAO.eliminaDocDaFirmare(documentiDaFirmarePerIdTestata);
	ConfigurazioneComunicazioniCommissioni configurazione = new ConfigurazioneComunicazioniCommissioni();
	this.configurazioneComunicazioneService.getById(idTestata, configurazione);
	CommissioneModel commissione = commissioniService.getCommissione(configurazione.getIdCommissione());
	commissioniAuditingService.log(commissione.getId(),
		new MessaggioComunicazioneCommissioneEliminata(commissione.getNumeroProtocollo(), this.getResponsabile(), idTestata));
    }

    private String getResponsabile() {

	return ((Responsabili) this.userSecurityService.getCurrentlyAuthenticatedUserDetails()).getResponsabile();
    }

    @Override
    public void upgrDestinatariComunicazioniCommissioniDettagli() {

	List<UpgrMassiveDestinatariHelper> massiveDettagli = this.comunicazioniMassiveDettaglioDAO.upgrDestinatariComunicazioniCommissioniDettagli();
	String idComune = ORMHelper.getIdcomune(); // idcomune già presente in sessione
	for (UpgrMassiveDestinatariHelper massiveDettaglio : massiveDettagli) {
	    // Ogni riga potrebbe essere di un comune diverso ché la query è fatta per tutti gli idcomune
	    // devo quindi settare ORMHELPER prima di aggiornare/inserire
	    ORMHelper.setIdcomune(massiveDettaglio.getIdComune());
	    MassiveDettDestinatari massiveDettDestinatari = new MassiveDettDestinatari();
	    massiveDettDestinatari.setId(new PkId());
	    Anagrafe anagrafe = new Anagrafe();
	    anagrafe.setId(new PkId(massiveDettaglio.getCodiceAnagrafe()));
	    massiveDettDestinatari.setAnagrafe(anagrafe);
	    massiveDettDestinatari.setMailDestinatario(massiveDettaglio.getMailDestinatario());
	    MassiveDettaglio dettaglio = new MassiveDettaglio();
	    dettaglio.setId(new PkId(massiveDettaglio.getIdMassiveD()));
	    massiveDettDestinatari.setMassiveDettaglio(dettaglio);
	    this.comunicazioniMassiveDAO.saveEntity(massiveDettDestinatari);
	}
	ORMHelper.setIdcomune(idComune);// ripristino idcomune già presente in sessione
    }

    @Override
    public boolean exists(Integer idTestata) {

	return this.commissioniComunicazioniMassiveDAO.exists(idTestata);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public IWorkflowComunicazioniService getWorkFlowService() {

	return this.workFlowComunicazioniCommissioniService;
    }
}
