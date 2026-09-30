package it.gruppoinit.pal.gp.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.CommedilizieVotazioniDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.CommedilizieAppello;
import it.gruppoinit.pal.gp.core.domain.CommedilizieVotazioni;
import it.gruppoinit.pal.gp.core.domain.CommedilizieVotiBase;
import it.gruppoinit.pal.gp.core.domain.CommissioniedilizieR;
import it.gruppoinit.pal.gp.core.domain.CommissioniedilizieT;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.features.commissioni.auditing.ICommissioniAuditingService;
import it.gruppoinit.pal.gp.core.features.commissioni.auditing.messaggi.MessaggioVotazioneCommissioneAggiornata;
import it.gruppoinit.pal.gp.core.features.commissioni.auditing.messaggi.MessaggioVotazioneCommissioneCreata;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.CommedilizieAppelloService;
import it.gruppoinit.pal.gp.core.service.CommedilizieVotazioniService;
import it.gruppoinit.pal.gp.core.service.CommedilizieVotiBaseService;
import it.gruppoinit.pal.gp.core.service.CommissioniedilizieRService;
import it.gruppoinit.pal.gp.core.service.CommissioniedilizieTService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;

/**
 * 
 * @author gianpaolot
 */
@Service
public class CommedilizieVotazioniServiceImpl extends BaseServiceImpl<CommedilizieVotazioni, PkId> implements CommedilizieVotazioniService {

    private CommedilizieVotazioniDAO commedilizievotazioniDAO;
    private CommissioniedilizieTService commissioniedilizieTService;
    private CommissioniedilizieRService commissioniedilizieRService;
    private CommedilizieAppelloService commedilizieAppelloService;
    private CommedilizieVotiBaseService commedilizieVotiBaseService;
    private ICommissioniAuditingService auditingService;
    private UserSecurityService userSecurityService;

    @Autowired
    public void setAuditingService(ICommissioniAuditingService auditingService) {

	this.auditingService = auditingService;
    }

    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {

	this.userSecurityService = userSecurityService;
    }

    @Autowired
    public void setCommedilizieVotazioniDAO(CommedilizieVotazioniDAO commedilizievotazioniDAO) {

	this.commedilizievotazioniDAO = commedilizievotazioniDAO;
    }

    @Autowired
    public void setCommissioniedilizieTService(CommissioniedilizieTService commissioniedilizieTService) {

	this.commissioniedilizieTService = commissioniedilizieTService;
    }

    @Autowired
    public void setCommissioniedilizieRService(CommissioniedilizieRService commissioniedilizieRService) {

	this.commissioniedilizieRService = commissioniedilizieRService;
    }

    @Autowired
    public void setCommedilizieAppelloService(CommedilizieAppelloService commedilizieAppelloService) {

	this.commedilizieAppelloService = commedilizieAppelloService;
    }

    @Autowired
    public void setCommedilizieVotiBaseService(CommedilizieVotiBaseService commedilizieVotiBaseService) {

	this.commedilizieVotiBaseService = commedilizieVotiBaseService;
    }

    @Override
    protected Class<CommedilizieVotazioni> getEntityClass() {

	return CommedilizieVotazioni.class;
    }

    @Override
    public List<CommedilizieVotazioni> findAll(Integer firstResult, Integer maxResult) {

	return commedilizievotazioniDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(CommedilizieVotazioni entity) {

	// §§§BEGIN§§§
	dataIntegration(entity);
	if (validateEntity(entity)) {
	    commedilizievotazioniDAO.insert(entity);
	    childDataInsert(entity);
	}
	// §§§END§§§
    }

    @Override
    public CommedilizieVotazioni findById(PkId id) {

	// §§§BEGIN§§§
	return commedilizievotazioniDAO.findById(id);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void update(CommedilizieVotazioni entity) {

	// §§§BEGIN§§§
	dataIntegration(entity);
	if (validateEntity(entity)) {
	    commedilizievotazioniDAO.update(entity);
	    childDataUpdate(entity);
	}
	// §§§END§§§
    }

    @Override
    public void delete(CommedilizieVotazioni entity) {

	// §§§BEGIN§§§
	if (isDeleteAllowed(entity)) {
	    childDelete(entity);
	    commedilizievotazioniDAO.delete(entity);
	}
	// §§§END§§§
    }

    @Override
    public List<CommedilizieVotazioni> findAppelloByCommissioniedilizieR(CommissioniedilizieR commissioniedilizieR) {

	// §§§BEGIN§§§
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction filterRestriction = new FilterRestriction();
	filterRestriction.addFilterField(FilterUtils.equals("commissioniedilizieR", commissioniedilizieR, CommissioniedilizieR.class));
	filterRestriction.addFilterField(
		FilterUtils.equals("commissioniedilizieT", commissioniedilizieR.getCommissioniedilizieT(), CommissioniedilizieT.class));
	filterTable.addRestriction(filterRestriction);
	filterTable.addOrder(FilterUtils.orderDesc("ordinamento", "commedilizieAppello.commedilizieCarica"));
	List<CommedilizieVotazioni> list = this.findByFilterTable(filterTable);
	List<CommedilizieAppello> listaAppello = commedilizieAppelloService.findByCommissioniEdilizieR(commissioniedilizieR.getId().getCodice());
	if (list.size() < listaAppello.size()) {
	    for (CommedilizieAppello commedilizieAppello : listaAppello) {
		boolean isPresent = isCommedilizaAppelloPresent(list, commedilizieAppello);
		if (!isPresent) {
		    CommedilizieVotazioni commedilizieVotazioni = new CommedilizieVotazioni();
		    commedilizieVotazioni.setCommedilizieAppello(commedilizieAppello);
		    commedilizieVotazioni.setCommissioniedilizieR(commissioniedilizieR);
		    commedilizieVotazioni.setCommissioniedilizieT(commissioniedilizieR.getCommissioniedilizieT());
		    commedilizieVotazioni.setPresente(commedilizieAppello.getPresente());
		    list.add(commedilizieVotazioni);
		}
	    }
	}
	return list;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public List<CommedilizieVotazioni> findByFilterTable(FilterTable filterTable) {

	// §§§BEGIN§§§
	return commedilizievotazioniDAO.findByFilterTable(filterTable);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void insertOrUpdate(CommedilizieVotazioni commedilizieVotazioni) {

	// §§§BEGIN§§§
	//TODO verificare eventuali NP
	String numeroCommissione = commedilizieVotazioni.getCommissioniedilizieT().getNumprotocollo();
	String votazione = commedilizieVotazioni.getCommedilizieVotiBase().getDescrizione();
	String soggetto = commedilizieVotazioni.getCommedilizieAppello().getComponente();
	if (commedilizieVotazioni.getId().getCodice() != null) {
	    this.update(commedilizieVotazioni);
	    this.auditingService.log(commedilizieVotazioni.getCommissioniedilizieT().getId().getCodice(),
		    new MessaggioVotazioneCommissioneAggiornata(getResponsabile(), numeroCommissione, votazione, soggetto));
	} else {
	    this.insert(commedilizieVotazioni);
	    this.auditingService.log(commedilizieVotazioni.getCommissioniedilizieT().getId().getCodice(),
		    new MessaggioVotazioneCommissioneCreata(getResponsabile(), numeroCommissione, votazione, soggetto));
	}
	// §§§END§§§
    }

    @Override
    public List<CommedilizieVotazioni> findByCommissioneOrdinePrecedente(CommissioniedilizieR commissioniedilizieR, Integer codiceCommissioneT,
	    Integer ordine) {

	List<CommedilizieVotazioni> risultato = new ArrayList<CommedilizieVotazioni>();
	if (ordine > 0) {
	    FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	    FilterRestriction restriction = new FilterRestriction();
	    restriction.addFilterField(FilterUtils.equals("id.codice", codiceCommissioneT, "commissioniedilizieT", Integer.class));
	    restriction.addFilterField(FilterUtils.equals("ordine", ordine, "commissioniedilizieR", Integer.class));
	    // Condizione che ci dice che la commissione è stata discussa
	    restriction.addFilterField(FilterUtils.isNotNull("movimentoRientro", "commissioniedilizieR"));
	    filterTable.addRestriction(restriction);
	    risultato = commedilizievotazioniDAO.findByFilterTable(filterTable);
	}
	return risultato;
    }

    @Override
    public List<CommedilizieVotazioni> findByCommissioneOrdinePrecedenteDiscussa(CommissioniedilizieR commissioniedilizieR,
	    Integer codiceCommissioneT, Integer ordineDiPartenza) {

	List<CommedilizieVotazioni> risultatoTemp = new ArrayList<CommedilizieVotazioni>();
	List<CommedilizieVotazioni> risultato = new ArrayList<CommedilizieVotazioni>();
	Integer ordineCommDaRecuperare = ordineDiPartenza - 1;
	while (ordineCommDaRecuperare > 0 && risultatoTemp.isEmpty()) {
	    risultatoTemp = findByCommissioneOrdinePrecedente(commissioniedilizieR, codiceCommissioneT, ordineCommDaRecuperare);
	    ordineCommDaRecuperare--;
	}
	// Annullo il voto dato che la commissione deve ancora essere discussa
	// Devo creare una nuova lista con nuovi oggetti, altrimenti HIBERNATE
	// aggiorna in automatico quelli presenti sul db che ho recuperato
	CommedilizieVotazioni commedilizieVotazioni = null;
	for (CommedilizieVotazioni commedilizieVotazioniTemp : risultatoTemp) {
	    commedilizieVotazioni = new CommedilizieVotazioni();
	    commedilizieVotazioni.setCommissioniedilizieT(commedilizieVotazioniTemp.getCommissioniedilizieT());
	    commedilizieVotazioni.setPresente(commedilizieVotazioniTemp.getPresente());
	    commedilizieVotazioni.setCommedilizieAppello(commedilizieVotazioniTemp.getCommedilizieAppello());
	    commedilizieVotazioni.setCommissioniedilizieR(commissioniedilizieR);
	    risultato.add(commedilizieVotazioni);
	}
	return risultato;
    }

    //    protected boolean isDeleteAllowed(CommedilizieVotazioni entity) {
    //
    //		boolean delete = true;
    //		List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
    //		TODO_validare_la_delete
    //		// esempio:
    //		// if (entity.getList().size() > 0) {
    //		//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
    //		// }
    //		if (!_ivs.isEmpty()) {
    //			this.throwValidationMessages(_ivs);
    //		}
    //		return delete;
    //    }
    // metodo che verifica che all'interno della lista passata di commissioni edilizie votazioni è già presente un record commissioni
    // edilizie appello
    private boolean isCommedilizaAppelloPresent(List<CommedilizieVotazioni> commedilizieVotazionis, CommedilizieAppello commedilizieAppello) {

	boolean risultato = false;
	for (CommedilizieVotazioni commedilizieVotazioni : commedilizieVotazionis) {
	    if (EntityUtils.equals(commedilizieVotazioni.getCommedilizieAppello(), commedilizieAppello)) {
		return true;
	    }
	}
	return risultato;
    }

    private void childDataInsert(CommedilizieVotazioni entity) {

    }

    private void childDataUpdate(CommedilizieVotazioni entity) {

    }

    @Override
    protected void childDelete(CommedilizieVotazioni entity) {

	super.childDelete(entity);
    }

    private void dataIntegration(CommedilizieVotazioni entity) {

	// §§§BEGIN§§§
	if (entity == null) {
	    throw new IllegalArgumentException("L'oggetto Commediliziovotazioni passato è nullo");
	}
	if (!BooleanUtils.isTrue(entity.getPresente())) {
	    entity.setParere(null);
	    entity.setCommedilizieVotiBase(null);
	}
	fixMergeEntityProperties(entity);
	// §§§END§§§
    }

    protected void fixMergeEntityProperties(CommedilizieVotazioni entity) {

	// §§§BEGIN§§§
	CommissioniedilizieT commissioniedilizieT = commissioniedilizieTService.bindDomainObject(entity.getCommissioniedilizieT(), PkId.class,
		"id.codice");
	entity.setCommissioniedilizieT(commissioniedilizieT);
	CommissioniedilizieR commissioniedilizieR = commissioniedilizieRService.bindDomainObject(entity.getCommissioniedilizieR(), PkId.class,
		"id.codice");
	entity.setCommissioniedilizieR(commissioniedilizieR);
	CommedilizieAppello commedilizieAppello = commedilizieAppelloService.bindDomainObject(entity.getCommedilizieAppello(), PkId.class,
		"id.codice");
	entity.setCommedilizieAppello(commedilizieAppello);
	CommedilizieVotiBase commedilizieVotiBase = commedilizieVotiBaseService.bindDomainObject(entity.getCommedilizieVotiBase(), Integer.class,
		"id");
	entity.setCommedilizieVotiBase(commedilizieVotiBase);
	// §§§END§§§
    }

    @Override
    public void salvaVotazioni(List<CommedilizieVotazioni> commedilizieVotazionis) {

	for (CommedilizieVotazioni commedilizieVotazioni : commedilizieVotazionis) {
	    this.insertOrUpdate(commedilizieVotazioni);
	}
    }

    private String getResponsabile() {

	return ((Responsabili) this.userSecurityService.getCurrentlyAuthenticatedUserDetails()).getResponsabile();
    }
}
