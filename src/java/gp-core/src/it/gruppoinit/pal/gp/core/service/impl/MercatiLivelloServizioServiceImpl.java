package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.MercatiLivelloServizioDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.LivelloServizio;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiDLivelloServizio;
import it.gruppoinit.pal.gp.core.domain.MercatiLivelloServizio;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.MercatiLivelloServizioHelper;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.filters.OrderTypeEnum;
import it.gruppoinit.pal.gp.core.service.LivelloServizioService;
import it.gruppoinit.pal.gp.core.service.MercatiDLivelloServizioService;
import it.gruppoinit.pal.gp.core.service.MercatiLivelloServizioService;
import it.gruppoinit.pal.gp.core.service.MercatiService;
import it.gruppoinit.pal.gp.core.service.MercatiUsoService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class MercatiLivelloServizioServiceImpl extends BaseServiceImpl<MercatiLivelloServizio, PkId> implements MercatiLivelloServizioService {

    private static final Logger log = LoggerFactory.getLogger(MercatiLivelloServizioServiceImpl.class);
    private MercatiLivelloServizioDAO mercatilivelloservizioDAO;
    private MercatiUsoService mercatiUsoService;
    private MercatiService mercatiService;
    private LivelloServizioService livelloServizioService;
    private MercatiDLivelloServizioService mercatiDLivelloServizioService;

    @Autowired
    public void setMercatiDLivelloServizioService(MercatiDLivelloServizioService mercatiDLivelloServizioService) {

	this.mercatiDLivelloServizioService = mercatiDLivelloServizioService;
    }

    @Autowired
    public void setMercatiLivelloServizioDAO(MercatiLivelloServizioDAO mercatilivelloservizioDAO) {

	this.mercatilivelloservizioDAO = mercatilivelloservizioDAO;
    }

    @Autowired
    public void setMercatiUsoService(MercatiUsoService mercatiUsoService) {

	this.mercatiUsoService = mercatiUsoService;
    }

    @Autowired
    public void setMercatiService(MercatiService mercatiService) {

	this.mercatiService = mercatiService;
    }

    @Autowired
    public void setLivelloServizioService(LivelloServizioService livelloServizioService) {

	this.livelloServizioService = livelloServizioService;
    }

    @Override
    protected Class<MercatiLivelloServizio> getEntityClass() {

	return MercatiLivelloServizio.class;
    }

    @Override
    public List<MercatiLivelloServizio> findAll(Integer firstResult, Integer maxResult) {

	return mercatilivelloservizioDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(MercatiLivelloServizio entity) {

	dataIntegration(entity, false);
	if (validateEntity(entity) && isInserOrUpdateAllowed(entity)) {
	    mercatilivelloservizioDAO.insert(entity);
	}
    }

    @Override
    public MercatiLivelloServizio findById(PkId id) {

	return mercatilivelloservizioDAO.findById(id);
    }

    @Override
    public void update(MercatiLivelloServizio entity) {

	dataIntegration(entity, true);
	if (validateEntity(entity) && isInserOrUpdateAllowed(entity)) {
	    mercatilivelloservizioDAO.update(entity);
	}
    }

    @Override
    public void updateServzioAndServizioPosteggio(MercatiLivelloServizio entity) {

	this.update(entity);
	if (entity.getDataFineValidita() != null) {
	    List<MercatiDLivelloServizio> lisDLivelloServizios = mercatiDLivelloServizioService.findByServizio(entity.getId().getCodice());
	    for (MercatiDLivelloServizio mercatiDLivelloServizio : lisDLivelloServizios) {
		log.debug("updateServzioAndServizioPosteggio# posteggio = {}, servizio = {}", mercatiDLivelloServizio.getMercatiD()
			.getCodiceposteggio(), mercatiDLivelloServizio.getMercatiLivelloServizio().getId().getCodice());
		if (mercatiDLivelloServizio.getDataFine() == null) {
		    mercatiDLivelloServizio.setDataFine(entity.getDataFineValidita());
		    mercatiDLivelloServizioService.update(mercatiDLivelloServizio);
		} else {
		    if (mercatiDLivelloServizio.getDataFine().after(entity.getDataFineValidita())) {
			mercatiDLivelloServizio.setDataFine(entity.getDataFineValidita());
			mercatiDLivelloServizioService.update(mercatiDLivelloServizio);
		    }
		}
	    }
	}
    }

    @Override
    public void delete(MercatiLivelloServizio entity) {

	if (isDeleteAllowed(entity)) {
	    mercatilivelloservizioDAO.delete(entity);
	}
    }

    @Override
    public List<MercatiLivelloServizio> findByUso(Integer codiceuso) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceuso, "mercatiUso", Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.order("dataInizioValidita", OrderTypeEnum.DESC));
	ft.addOrder(FilterUtils.order("dataFineValidita", OrderTypeEnum.DESC));
	ft.addOrder(FilterUtils.order("descrizione", OrderTypeEnum.ASC));
	return mercatilivelloservizioDAO.findByFilterTable(ft);
    }

    @Override
    public List<MercatiLivelloServizioHelper> findByMecato(Integer codicemercato) {

	List<MercatiLivelloServizioHelper> r = new ArrayList<MercatiLivelloServizioHelper>();
	MercatiLivelloServizioHelper helper = null;
	Mercati m = mercatiService.findById(new PkId(codicemercato));
	List<MercatiUso> mercatiUsos = mercatiUsoService.findByMercato(m);
	for (MercatiUso mercatiUso : mercatiUsos) {
	    helper = new MercatiLivelloServizioHelper();
	    List<MercatiLivelloServizio> livelloServizios = this.findByUso(mercatiUso.getId().getCodice());
	    helper.setMercatiUso(mercatiUso);
	    helper.setMercatiLivelloServizios(livelloServizios);
	    r.add(helper);
	}
	return r;
    }

    @Override
    public List<MercatiLivelloServizioHelper> findByMecatoUso(Integer codiceuso) {

	List<MercatiLivelloServizioHelper> r = new ArrayList<MercatiLivelloServizioHelper>();
	MercatiLivelloServizioHelper helper = new MercatiLivelloServizioHelper();
	MercatiUso mercatiUso = mercatiUsoService.findById(new PkId(codiceuso));
	List<MercatiLivelloServizio> livelloServizios = this.findByUso(codiceuso);
	helper.setMercatiUso(mercatiUso);
	helper.setMercatiLivelloServizios(livelloServizios);
	r.add(helper);
	return r;
    }

    @Override
    public void insert(MercatiLivelloServizio entity, List<String> _usi) {

	insert(entity, _usi, null);
    }
    @Override
    public void insert(MercatiLivelloServizio entity, MercatiDLivelloServizio dentity, List<String> _usi, List<Integer> idposteggi) {

	List<Integer> idsLivelliServizio = new ArrayList<Integer>();
	insert(entity, _usi, idsLivelliServizio);
	mercatiDLivelloServizioService.insertMultiPosteggioMultiUso(idposteggi, idsLivelliServizio, 
		dentity.getFattoreMoltiplicativo(), dentity.getDataInizio(), dentity.getDataFine(), dentity.getUsaMqPosteggio());
	
    }
    
    private void insert(MercatiLivelloServizio entity, List<String> _usi, List<Integer> registerIds) {

	MercatiLivelloServizio objectInsert = null;
	for (String cod : _usi) {
	    MercatiUso mercatiUso = mercatiUsoService.findById(new PkId(Integer.parseInt(cod)));
	    objectInsert = new MercatiLivelloServizio();
	    objectInsert.setAttivo(entity.getAttivo());
	    objectInsert.setDescrizione(entity.getDescrizione());
	    LivelloServizio livelloServizio = livelloServizioService.findById(new PkId(entity.getLivelloServizio().getId().getCodice()));
	    objectInsert.setLivelloServizio(livelloServizio);
	    objectInsert.setMercatiUso(mercatiUso);
	    objectInsert.setTariffa(entity.getTariffa());
	    objectInsert.setDataInizioValidita(entity.getDataInizioValidita());
	    objectInsert.setDataFineValidita(entity.getDataFineValidita());
	    this.insert(objectInsert);
	    
	    if( registerIds != null ){
		registerIds.add(objectInsert.getId().getCodice());
	    }
	}
    }

    @Override
    public List<MercatiLivelloServizio> findByUsoAndServizio(Integer codiceuso, Integer codiceservizio) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceuso, "mercatiUso", Integer.class));
	fr.addFilterField(FilterUtils.equals("id.codice", codiceservizio, "livelloServizio", Integer.class));
	ft.addRestriction(fr);
	return mercatilivelloservizioDAO.findByFilterTable(ft);
    }

    @Override
    public List<MercatiLivelloServizio> findByBeforeDataDaAndLivelloServizio(Date date, Integer codiceLivelloServizio) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	Calendar _date = null;
	if (date != null) {
	    _date = Calendar.getInstance();
	    _date.setTime(date);
	    _date.set(Calendar.HOUR, 23);
	    _date.set(Calendar.MINUTE, 59);
	    _date.set(Calendar.SECOND, 59);
	}
	fr.addFilterField(FilterUtils.smallerEqual("dataInizioValidita", _date, Date.class));
	fr.addFilterField(FilterUtils.equals("id.codice", codiceLivelloServizio, "livelloServizio", Integer.class));
	ft.addRestriction(fr);
	return mercatilivelloservizioDAO.findByFilterTable(ft);
    }

    @Override
    public List<MercatiLivelloServizio> findByMecatoUso(Integer codiceUso, boolean attivi, boolean nonscaduti) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	if (nonscaduti) {
	    Calendar _date = Calendar.getInstance();
	    _date.set(Calendar.HOUR, 23);
	    _date.set(Calendar.MINUTE, 59);
	    _date.set(Calendar.SECOND, 59);
	    FilterRestriction or = new FilterRestriction();
	    or.addFilterField(FilterUtils.isNotNull("dataInizioValidita"));
	    or.addFilterField(FilterUtils.smallerEqual("dataInizioValidita", _date, Date.class));
	    ft.addRestriction(or);
	}
	fr.addFilterField(FilterUtils.equals("id.codice", codiceUso, "mercatiUso", Integer.class));
	if (attivi) {
	    fr.addFilterField(FilterUtils.equals("attivo", attivi, Boolean.class));
	}
	ft.addRestriction(fr);
	return mercatilivelloservizioDAO.findByFilterTable(ft);
    }

    @Override
    public List<MercatiLivelloServizio> findByDescrizioneAndUso(String textToSearch, Integer codiceUso) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceUso, "mercatiUso", Integer.class));
	fr.addFilterField(FilterUtils.like("descrizione", textToSearch));
	ft.addRestriction(fr);
	return mercatilivelloservizioDAO.findByFilterTable(ft);
    }
    
    @Override
    public List<MercatiLivelloServizio> findByMultiUso(Integer[] codiciUso) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.in("id.codice", codiciUso, "mercatiUso", Integer.class));
	ft.addRestriction(fr);
	return mercatilivelloservizioDAO.findByFilterTable(ft);
    }

    @Override
    public void aggiornaTariffa(MercatiLivelloServizio daAggiornare, MercatiLivelloServizio entity) {

	if (validateEntity(entity) && isInserOrUpdateAllowed(entity) && isAggiornaTariffaAllowed(daAggiornare, entity)) {
	    Date dataInizioTaraffiAggiornata = entity.getDataInizioValidita();
	    Date datFineTariffaDaAggiornare = Utilities.addAndremoveDays(dataInizioTaraffiAggiornata, 1, false);
	    this.insert(entity);
	    daAggiornare.setDataFineValidita(datFineTariffaDaAggiornare);
	    this.update(daAggiornare);
	    String nota = getMessageFromBundle("label.nota_aggiornamento_tariffa_servizio", null);
	    daAggiornare.setNote(nota);
	    // Aggiornamento livello di servizio sui posteggi in cui il vecchio livello di servizio era attivo
	    List<MercatiDLivelloServizio> livelloServiziosDaChiudere = mercatiDLivelloServizioService
		    .findByServizio(daAggiornare.getId().getCodice());
	    for (MercatiDLivelloServizio mercatiDLivelloServizio : livelloServiziosDaChiudere) {
		// Aggiorna data fine sui livello servizi associati a posteggi
		if (mercatiDLivelloServizio.getDataFine() == null) {
		    log.debug("aggiornaTariffa# posteggio = {}, mercatiDLivelloServizio = {}, nuova data fine = {}", mercatiDLivelloServizio
			    .getMercatiD().getCodiceposteggio(), Utilities.formatDate(daAggiornare.getDataFineValidita(), false));
		    mercatiDLivelloServizio.setDataFine(daAggiornare.getDataFineValidita());
		    mercatiDLivelloServizioService.update(mercatiDLivelloServizio);
		} else if (mercatiDLivelloServizio.getDataFine() != null
			&& mercatiDLivelloServizio.getDataFine().after(daAggiornare.getDataFineValidita())) {
		    log.debug(
			    "aggiornaTariffa# posteggio = {}, mercatiDLivelloServizio = {}, aggiorno data fine da {} a {}",
			    new Object[] { mercatiDLivelloServizio.getMercatiD().getCodiceposteggio(),
				    Utilities.formatDate(mercatiDLivelloServizio.getDataFine(), false),
				    Utilities.formatDate(daAggiornare.getDataFineValidita(), false) });
		    mercatiDLivelloServizio.setDataFine(daAggiornare.getDataFineValidita());
		    mercatiDLivelloServizioService.update(mercatiDLivelloServizio);
		}
		// aggiungo il nuovo livello di servizio inserito in precedenza su tutti i posteggi in cui era attivo il vecchio
		MercatiDLivelloServizio mercatiDLivelloServizioNuovo = cloneMercatiDLivelloServizioWithoutServizio(mercatiDLivelloServizio);
		mercatiDLivelloServizioNuovo.setDataFine(null);
		mercatiDLivelloServizioNuovo.setDataInizio(entity.getDataInizioValidita());
		mercatiDLivelloServizioNuovo.setMercatiLivelloServizio(entity);
		if (entity.getDataFineValidita() != null) {
		    mercatiDLivelloServizioNuovo.setDataFine(entity.getDataFineValidita());
		}
		mercatiDLivelloServizioService.insert(mercatiDLivelloServizioNuovo);
		log.debug("aggiornaTariffa# Nuovo livello servizio mercatiDLivelloServizio = {}, nuova data fine = {}",
			Utilities.formatDate(daAggiornare.getDataFineValidita(), false));
	    }
	}
    }

    private MercatiDLivelloServizio cloneMercatiDLivelloServizioWithoutServizio(MercatiDLivelloServizio mercatiDLivelloServizio) {

	MercatiDLivelloServizio mercatiDLivelloServizioNuovo = new MercatiDLivelloServizio();
	if (mercatiDLivelloServizio.getDataFine() != null) {
	    mercatiDLivelloServizioNuovo.setDataFine(mercatiDLivelloServizio.getDataFine());
	}
	mercatiDLivelloServizioNuovo.setDataInizio(mercatiDLivelloServizio.getDataInizio());
	mercatiDLivelloServizioNuovo.setFattoreMoltiplicativo(mercatiDLivelloServizio.getFattoreMoltiplicativo());
	mercatiDLivelloServizioNuovo.setMercatiD(mercatiDLivelloServizio.getMercatiD());
	//mercatiDLivelloServizioNuovo.setMercatiLivelloServizio(mercatiLivelloServizio);
	mercatiDLivelloServizioNuovo.setUsaMqPosteggio(mercatiDLivelloServizio.getUsaMqPosteggio());
	return mercatiDLivelloServizioNuovo;
    }

    private void dataIntegration(MercatiLivelloServizio entity, boolean isUpdate) {

	if (entity == null) {
	    throw new RuntimeException("Il parametro ComunicazioniT da validare è nullo");
	}
	if (entity.getAttivo() == null) {
	    entity.setAttivo(true);
	}
	fixMergeEntityProperties(entity);
    }

    protected void fixMergeEntityProperties(MercatiLivelloServizio entity) {

    }

    private boolean isInserOrUpdateAllowed(MercatiLivelloServizio entity) {

	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (entity.getDataFineValidita() != null) {
	    if (entity.getDataInizioValidita().after(entity.getDataFineValidita())) {
		String mess = getMessageFromBundle("service_error.data_fine_antecendente", null);
		_ivs.add(new InvalidValue(mess, MercatiLivelloServizio.class, "dataFineValidita", entity, new MercatiLivelloServizio()));
	    }
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return true;
    }

    private boolean isAggiornaTariffaAllowed(MercatiLivelloServizio daAggiornare, MercatiLivelloServizio entityupdate) {

	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (entityupdate.getDataInizioValidita().before(daAggiornare.getDataInizioValidita())) {
	    String mess = getMessageFromBundle("service_error.mercatiLivelloServizio.tariffa.data_non_compatibile", null);
	    _ivs.add(new InvalidValue(mess, null, null, mess, null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return true;
    }

    protected boolean isDeleteAllowed(MercatiLivelloServizio entity) {

	boolean delete = true;
	//		List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	//		TODO_validare_la_delete
	//		// esempio:
	//		// if (entity.getList().size() > 0) {
	//		//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	//		// }
	//		if (!_ivs.isEmpty()) {
	//			this.throwValidationMessages(_ivs);
	//		}
	return delete;
    }
}
