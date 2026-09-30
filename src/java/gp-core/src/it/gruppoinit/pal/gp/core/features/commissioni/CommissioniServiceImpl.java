package it.gruppoinit.pal.gp.core.features.commissioni;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.CommedilizieAllegati;
import it.gruppoinit.pal.gp.core.domain.CommedilizieAppello;
import it.gruppoinit.pal.gp.core.domain.CommedilizieConvocazioni;
import it.gruppoinit.pal.gp.core.domain.CommedilizieTipologie;
import it.gruppoinit.pal.gp.core.domain.CommedilizieTipologiedett;
import it.gruppoinit.pal.gp.core.domain.CommissioniedilizieR;
import it.gruppoinit.pal.gp.core.domain.CommissioniedilizieT;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Tipiprocedure;
import it.gruppoinit.pal.gp.core.features.commissioni.auditing.ICommissioniAuditingService;
import it.gruppoinit.pal.gp.core.features.commissioni.auditing.messaggi.MessaggioCommissioneAggiornata;
import it.gruppoinit.pal.gp.core.features.commissioni.auditing.messaggi.MessaggioCommissioneCancellata;
import it.gruppoinit.pal.gp.core.features.commissioni.auditing.messaggi.MessaggioCommissioneChiusa;
import it.gruppoinit.pal.gp.core.features.commissioni.auditing.messaggi.MessaggioCommissioneCreata;
import it.gruppoinit.pal.gp.core.features.commissioni.auditing.messaggi.MessaggioCommissioneRiaperta;
import it.gruppoinit.pal.gp.core.features.commissioni.comunicazionimassive.ICommissioniComunicazioniMassiveDAO;
import it.gruppoinit.pal.gp.core.features.commissioni.dettaglio.models.DettaglioCommissioneModel;
import it.gruppoinit.pal.gp.core.features.commissioni.dettaglio.models.ElencoSoggettiIstanzaModel;
import it.gruppoinit.pal.gp.core.features.commissioni.dettaglio.models.RigaCommissioneModel;
import it.gruppoinit.pal.gp.core.features.commissioni.dettaglio.pratiche.documenti.ICommissioniDocumentiPraticheService;
import it.gruppoinit.pal.gp.core.features.commissioni.model.CommissioneListModel;
import it.gruppoinit.pal.gp.core.features.commissioni.model.CommissioneModel;
import it.gruppoinit.pal.gp.core.service.CommedilizieAllegatiService;
import it.gruppoinit.pal.gp.core.service.CommedilizieAppelloService;
import it.gruppoinit.pal.gp.core.service.CommedilizieConvocazioniService;
import it.gruppoinit.pal.gp.core.service.CommedilizieTipologiedettService;
import it.gruppoinit.pal.gp.core.service.CommissioniedilizieRService;
import it.gruppoinit.pal.gp.core.service.MovimentiNoSecurityService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;
import it.gruppoinit.pal.gp.core.utils.LoggerCancellazioni;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Service
public class CommissioniServiceImpl implements ICommissioniService {

    @Autowired
    private ICommissioniDAO commissioniDAO;
    @Autowired
    private ICommissioniAuditingService auditingService;
    @Autowired
    private UserSecurityService userSecurityService;
    @Autowired
    private ICommissioniDocumentiPraticheService commissioniDocumentiPraticheService;
    @Autowired
    private CommissioniedilizieRService commissioniedilizieRService;
    @Autowired
    private CommedilizieAllegatiService commedilizieAllegatiService;
    @Autowired
    private CommedilizieAppelloService commedilizieAppelloService;
    @Autowired
    private CommedilizieConvocazioniService commedilizieConvocazioniService;
    @Autowired
    private ICommissioniComunicazioniMassiveDAO commissioniComunicazioniMassiveDAO;
    @Autowired
    private MovimentiNoSecurityService movimentiNoSecurityService;
    @Autowired
    private ApplicationContext context;
    @Autowired
    private CommedilizieTipologiedettService commedilizieTipologiedettService;

    @SuppressWarnings("unchecked")
    @Override
    public CommissioneModel getCommissione(int idCommissione) {

	CommissioniedilizieT commissione = (CommissioniedilizieT) this.commissioniDAO.getById(CommissioniedilizieT.class, idCommissione);
	return CommissioneModel.fromCommissioniedilizieT(commissione);
    }

    @SuppressWarnings("unchecked")
    @Override
    public DettaglioCommissioneModel getDettaglioCommissione(int idCommissione) {

	CommissioniedilizieT commissione = (CommissioniedilizieT) this.commissioniDAO.getById(CommissioniedilizieT.class, idCommissione);
	DettaglioCommissioneModel dettaglio = new DettaglioCommissioneModel();
	dettaglio.setId(idCommissione);
	dettaglio.setData(commissione.getData());
	dettaglio.setDescrizione(commissione.getDescrizione());
	dettaglio.setNumeroProtocollo(commissione.getNumprotocollo());
	dettaglio.setOraFine(commissione.getOrafine());
	dettaglio.setOraInizio(commissione.getOrainizio());
	dettaglio.setAperta(commissione.getFlagaperta());
	for (CommissioniedilizieR rigaCommissione : commissione.getCommissioniedilizieRs()) {
	    Istanze istanza = rigaCommissione.getMovimento().getIstanza();
	    RigaCommissioneModel riga = new RigaCommissioneModel();
	    riga.setId(rigaCommissione.getId().getCodice());
	    riga.setComune(istanza.getComune().getComune());
	    riga.setCodiceIstanza(istanza.getId().getCodice());
	    riga.setDataPresentazione(istanza.getData());
	    riga.setDataProtocollo(istanza.getDataprotocollo());
	    riga.setDataRichiesta(rigaCommissione.getMovimento().getData());
	    riga.setIntervento(istanza.getAlberoproc().getDescrizioneCompleta());
	    riga.setLavori(istanza.getLavori());
	    riga.setCodiceMovimento(rigaCommissione.getMovimento().getId().getCodice());
	    riga.setMovimento(rigaCommissione.getMovimento().getDescrizioneEstesa());
	    if (rigaCommissione.getMovimentoRientro() != null) {
		riga.setCodiceMovimentoRientro(rigaCommissione.getMovimentoRientro().getId().getCodice());
		riga.setMovimentoRientro(rigaCommissione.getMovimentoRientro().getDescrizioneEstesa());
	    }
	    riga.setNumeroDocumenti(this.commissioniDocumentiPraticheService.countDocumentiDellaCommissione(riga.getId()));
	    riga.setNumeroIstanza(istanza.getNumeroistanza());
	    riga.setNumeroProtocollo(istanza.getNumeroprotocollo());
	    riga.setOrdine(rigaCommissione.getOrdine());
	    riga.setRichiedente(istanza.getRichiedente().getDescrizioneRichiedente());
	    if (rigaCommissione.getCommedilizieTipopareri() != null) {
		riga.setTipologiaParere(rigaCommissione.getCommedilizieTipopareri().getDescrizione());
	    }
	    dettaglio.getRighe().add(riga);
	}
	return dettaglio;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void insert(CommissioneModel commissione) {

	CommissioniedilizieT commissioneEdilizia = new CommissioniedilizieT();
	CommedilizieTipologie tipologia = new CommedilizieTipologie();
	tipologia.setId(new PkId(commissione.getCodiceTipologia()));
	commissioneEdilizia.setCommedilizieTipologie(tipologia);
	commissioneEdilizia.setData(new Date());
	commissioneEdilizia.setDescrizione(commissione.getDescrizione());
	commissioneEdilizia.setNote(commissione.getNote());
	commissioneEdilizia.setNumprotocollo(commissione.getNumeroProtocollo());
	commissioneEdilizia.setFlagaperta(commissione.isAperta());
	commissioneEdilizia.setFlagSincrona(commissione.isSincrona());
	commissioneEdilizia.setIdconvocazione(commissione.getIdConvocazione());
	commissioneEdilizia.setOdg(commissione.getOdg());
	this.commissioniDAO.insert(commissioneEdilizia);
	commissione.setId(commissioneEdilizia.getId().getCodice());
	//Audit alla creazione della commissione
	this.auditingService.log(commissione.getId(), new MessaggioCommissioneCreata(commissione.getNumeroProtocollo(), this.getResponsabile()));
	inserisciDatiDaMovimento(commissione.getCodiceMovimento(), commissioneEdilizia);
    }

    private void inserisciDatiDaMovimento(Integer codiceMovimento, CommissioniedilizieT commissioneEdilizia) {

	if (codiceMovimento != null) {
	    List<String> codiciMovimenti = new ArrayList<String>();
	    codiciMovimenti.add(String.valueOf(codiceMovimento));
	    commissioniedilizieRService.insertMultiploCommissioniedilizieR(codiciMovimenti, commissioneEdilizia);
	    Movimenti mov = movimentiNoSecurityService.findById(new PkId(codiceMovimento));
	    CommedilizieConvocazioni c = new CommedilizieConvocazioni();
	    Tipiprocedure procedura = mov.getIstanza().getProcedura();
	    int numggcds = 0;
	    if (procedura.getNumggcdspiudata() != null) {
		numggcds = procedura.getNumggcdspiudata();
	    }
	    Date dataconvocazione = mov.getData();
	    dataconvocazione = Utilities.addDays(dataconvocazione, numggcds);
	    c.setDataconvocazione(dataconvocazione);
	    c.setOraconvocazione("08:30");
	    c.setCommissioniedilizieT(commissioneEdilizia);
	    commedilizieConvocazioniService.insert(c);
	}
    }

    @SuppressWarnings("unchecked")
    @Override
    public void updateCommissione(CommissioneModel commissione) {

	CommissioniedilizieT commissioneEdilizia = (CommissioniedilizieT) this.commissioniDAO.getById(CommissioniedilizieT.class,
		commissione.getId());
	if (commissioneEdilizia == null) {
	    throw new IllegalArgumentException("Impossibile trovare la commissione con id " + commissione.getId());
	}
	CommedilizieTipologie tipologia = new CommedilizieTipologie();
	tipologia.setId(new PkId(commissione.getCodiceTipologia()));
	commissioneEdilizia.setCommedilizieTipologie(tipologia);
	commissioneEdilizia.setDescrizione(commissione.getDescrizione());
	commissioneEdilizia.setNote(commissione.getNote());
	commissioneEdilizia.setNumprotocollo(commissione.getNumeroProtocollo());
	commissioneEdilizia.setFlagaperta(commissione.isAperta());
	commissioneEdilizia.setFlagSincrona(commissione.isSincrona());
	commissioneEdilizia.setDataFine(commissione.getDataFine());
	commissioneEdilizia.setOdg(commissione.getOdg());
	this.commissioniDAO.update(commissioneEdilizia);
	//Audit alla modifica della commissione
	if (commissione.isAperta()) {
	    this.auditingService.log(commissione.getId(),
		    new MessaggioCommissioneAggiornata(commissione.getId(), commissione.getNumeroProtocollo(), this.getResponsabile()));
	} else {
	    // in caso di chiusura 
	    this.auditingService.log(commissione.getId(),
		    new MessaggioCommissioneChiusa(commissione.getNumeroProtocollo(), this.getResponsabile(), commissione.getDataFine()));
	}
    }

    private String getResponsabile() {

	return ((Responsabili) this.userSecurityService.getCurrentlyAuthenticatedUserDetails()).getResponsabile();
    }

    @Override
    public ElencoSoggettiIstanzaModel getElencoSoggettiIstanza(int idRiga) {

	return this.commissioniDAO.getElencoSoggettiIstanza(idRiga);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void updateCommissioniedilizieTAndChild(DettaglioCommissioneModel model) {

	//1. Aggiorno ora inizio/fine della commissione
	CommissioniedilizieT commissione = (CommissioniedilizieT) this.commissioniDAO.getById(CommissioniedilizieT.class, model.getId());
	commissione.setOrainizio(model.getOraInizio());
	commissione.setOrafine(model.getOraFine());
	this.commissioniDAO.update(commissione);
	//2. Aggiorno ordine delle righe
	for (RigaCommissioneModel riga : model.getRighe()) {
	    CommissioniedilizieR rigaCommissione = (CommissioniedilizieR) this.commissioniDAO.getById(CommissioniedilizieR.class, riga.getId());
	    rigaCommissione.setOrdine(riga.getOrdine());
	    this.commissioniDAO.update(rigaCommissione);
	}
    }

    @SuppressWarnings("unchecked")
    @Override
    public void delete(Integer idCommissione) {

	isDeleteAllowed(idCommissione);
	CommissioniedilizieT commissione = (CommissioniedilizieT) this.commissioniDAO.getById(CommissioniedilizieT.class, idCommissione);
	childDelete(commissione);
	this.commissioniDAO.delete(commissione);
	LoggerCancellazioni
		.log("#COMMISSIONI_CONFERENZE# " +
		     new MessaggioCommissioneCancellata(commissione, ((Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails()))
			     .getTestoMessaggio());
    }

    private void isDeleteAllowed(Integer idCommissione) {

	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (commissioniComunicazioniMassiveDAO.sonoPresentiComunicazioni(idCommissione)) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "COMUNICAZIONI MASSIVE", null));
	}
	if (!_ivs.isEmpty()) {
	    throw new BusinessValidationException(_ivs, Utilities.getMessageFromBundle(context, "error.business_error_message", new Object[] { "" }),
		    null);
	}
    }

    private void childDelete(CommissioniedilizieT entity) {

	Set<CommissioniedilizieR> commissioniedilizieRs = entity.getCommissioniedilizieRs();
	for (CommissioniedilizieR commissioniedilizieR : commissioniedilizieRs) {
	    this.commissioniedilizieRService.delete(commissioniedilizieR);
	}
	Set<CommedilizieAllegati> allegatis = entity.getCommedilizieAllegatis();
	for (CommedilizieAllegati commedilizieAllegati : allegatis) {
	    this.commedilizieAllegatiService.delete(commedilizieAllegati);
	}
	Set<CommedilizieAppello> commedilizieAppellos = entity.getCommedilizieAppellos();
	for (CommedilizieAppello commedilizieAppello : commedilizieAppellos) {
	    this.commedilizieAppelloService.delete(commedilizieAppello);
	}
	entity.setIdconvocazione(null);
	this.commissioniDAO.update(entity);
	Set<CommedilizieConvocazioni> convocazionis = entity.getCommedilizieConvocazionis();
	for (CommedilizieConvocazioni commedilizieConvocazioni : convocazionis) {
	    this.commedilizieConvocazioniService.deleteWithoutControl(commedilizieConvocazioni);
	}
	this.auditingService.delete(entity.getId().getCodice());
    }

    @Override
    public void updateConvocazione(Integer codiceCommissione, Integer codiceConvocazione) {

	this.commissioniDAO.updateConvocazione(codiceCommissione, codiceConvocazione);
	this.commissioniDAO.updateDataOraByConvocazione(codiceCommissione, codiceConvocazione);
    }

    @Override
    public void riapriCommissioneChiusa(Integer codiceCommissione) {

	CommissioniedilizieT commissioneEdilizia = (CommissioniedilizieT) this.commissioniDAO.getById(CommissioniedilizieT.class, codiceCommissione);
	commissioneEdilizia.setFlagaperta(Boolean.TRUE);
	this.commissioniDAO.update(commissioneEdilizia);
	this.auditingService.log(codiceCommissione,
		new MessaggioCommissioneRiaperta(codiceCommissione, commissioneEdilizia.getNumprotocollo(), this.getResponsabile()));
    }

    @SuppressWarnings("unchecked")
    @Override
    public void updateOrario(Integer idCommissione, String oraInizio, String oraFine) {

	CommissioniedilizieT comT = (CommissioniedilizieT) this.commissioniDAO.getById(CommissioniedilizieT.class, idCommissione);
	comT.setOrainizio(oraInizio);
	comT.setOrafine(oraFine);
	this.commissioniDAO.update(comT);
	// audit modifica orario convocazione
	this.auditingService.log(idCommissione, new MessaggioCommissioneAggiornata(idCommissione, comT.getNumprotocollo(), this.getResponsabile()));
    }

    @Override
    public CommissioneModel populateModelFromMovimento(Integer codiceMovimento) {

	Movimenti mov = movimentiNoSecurityService.findById(new PkId(codiceMovimento));
	List<CommedilizieTipologiedett> tipologieDett = commedilizieTipologiedettService
		.findTipimovimento(mov.getTipomovimento().getId().getTipomovimento(), 0, 3);
	CommedilizieTipologie t = null;
	if (tipologieDett.size() == 1) {
	    t = tipologieDett.get(0).getCommedilizieTipologie();
	}
	return CommissioneModel.FromMovimento(mov, t);
    }

    @Override
    public List<CommissioneListModel> listaCommissioniPerOperatore(Integer codiceOperatore, Integer firstResult, Integer maxResults) {

	return commissioniDAO.listaCommissioniPerOperatore(codiceOperatore, firstResult, maxResults);
    }
}