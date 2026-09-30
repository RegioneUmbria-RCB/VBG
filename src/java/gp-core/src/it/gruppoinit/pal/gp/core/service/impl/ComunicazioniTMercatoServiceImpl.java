package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.ComunicazioniTMercatoDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniConcessioni;
import it.gruppoinit.pal.gp.core.domain.ComunicazioniD;
import it.gruppoinit.pal.gp.core.domain.ComunicazioniDConcessioni;
import it.gruppoinit.pal.gp.core.domain.ComunicazioniT;
import it.gruppoinit.pal.gp.core.domain.ComunicazioniTMercato;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.features.manifestazioni.concessioni.AutorizzazioniConcessioniService;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.ComunicazioniDConcessioniService;
import it.gruppoinit.pal.gp.core.service.ComunicazioniDService;
import it.gruppoinit.pal.gp.core.service.ComunicazioniManagerService;
import it.gruppoinit.pal.gp.core.service.ComunicazioniTMercatoService;
import it.gruppoinit.pal.gp.core.service.ComunicazioniTService;
import it.gruppoinit.pal.gp.core.service.MercatiService;
import it.gruppoinit.pal.gp.core.service.TmpStatiComunicazioniDService;
import it.gruppoinit.pal.gp.core.service.VwConcessioniattiveService;
import it.gruppoinit.pal.gp.core.service.helper.ComunicazioniConcessioneServiceHelper;
import it.gruppoinit.pal.gp.core.service.helper.ComunicazioniServiceHelper;
import it.gruppoinit.pal.gp.core.service.helper.ComunicazioniTStatoEnum;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
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
public class ComunicazioniTMercatoServiceImpl extends BaseServiceImpl<ComunicazioniTMercato, PkId> implements ComunicazioniTMercatoService {

    private static final Logger log = LoggerFactory.getLogger(ComunicazioniTMercatoServiceImpl.class);
    private ComunicazioniTMercatoDAO comunicazionitmercatoDAO;
    private MercatiService mercatiService;
    private ComunicazioniTService comunicazioniTService;
    private VwConcessioniattiveService vwConcessioniattiveService;
    private ComunicazioniDService comunicazioniDService;
    private ComunicazioniDConcessioniService comunicazioniDConcessioniService;
    private AutorizzazioniConcessioniService autorizzazioniConcessioniService;
    private TmpStatiComunicazioniDService tmpStatiComunicazioniDService;
    private ComunicazioniManagerService comunicazioniManagerService;

    @Autowired
    public void setComunicazioniManagerService(ComunicazioniManagerService comunicazioniManagerService) {

	this.comunicazioniManagerService = comunicazioniManagerService;
    }

    @Autowired
    public void setTmpStatiComunicazioniDService(TmpStatiComunicazioniDService tmpStatiComunicazioniDService) {

	this.tmpStatiComunicazioniDService = tmpStatiComunicazioniDService;
    }

    @Autowired
    public void setAutorizzazioniConcessioniService(AutorizzazioniConcessioniService autorizzazioniConcessioniService) {

	this.autorizzazioniConcessioniService = autorizzazioniConcessioniService;
    }

    @Autowired
    public void setComunicazioniDConcessioniService(ComunicazioniDConcessioniService comunicazioniDConcessioniService) {

	this.comunicazioniDConcessioniService = comunicazioniDConcessioniService;
    }

    @Autowired
    public void setComunicazioniDService(ComunicazioniDService comunicazioniDService) {

	this.comunicazioniDService = comunicazioniDService;
    }

    @Autowired
    public void setVwConcessioniattiveService(VwConcessioniattiveService vwConcessioniattiveService) {

	this.vwConcessioniattiveService = vwConcessioniattiveService;
    }

    @Autowired
    public void setComunicazioniTService(ComunicazioniTService comunicazioniTService) {

	this.comunicazioniTService = comunicazioniTService;
    }

    @Autowired
    public void setMercatiService(MercatiService mercatiService) {

	this.mercatiService = mercatiService;
    }

    @Autowired
    public void setComunicazioniTMercatoDAO(ComunicazioniTMercatoDAO comunicazionitmercatoDAO) {

	this.comunicazionitmercatoDAO = comunicazionitmercatoDAO;
    }

    @Override
    protected Class<ComunicazioniTMercato> getEntityClass() {

	return ComunicazioniTMercato.class;
    }

    @Override
    public List<ComunicazioniTMercato> findAll(Integer firstResult, Integer maxResult) {

	return comunicazionitmercatoDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(ComunicazioniTMercato entity) {

	dataIntegration(entity, false);
	if (validateEntity(entity)) {
	    comunicazionitmercatoDAO.insert(entity);
	}
    }

    @Override
    public ComunicazioniTMercato findById(PkId id) {

	return comunicazionitmercatoDAO.findById(id);
    }

    @Override
    public void update(ComunicazioniTMercato entity) {

	dataIntegration(entity, true);
	if (validateEntity(entity)) {
	    comunicazionitmercatoDAO.update(entity);
	}
    }

    @Override
    public void delete(ComunicazioniTMercato entity) {

	if (isDeleteAllowed(entity)) {
	    comunicazionitmercatoDAO.delete(entity);
	}
    }

    @Override
    public List<ComunicazioniTMercato> findByCodiceMercato(Integer codicemercato) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codicemercato, "mercati", Integer.class));
	ft.addRestriction(fr);
	return comunicazionitmercatoDAO.findByFilterTable(ft);
    }

    @Override
    public boolean isExistByCodiceMercato(Integer codicemercato) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codicemercato, "mercati", Integer.class));
	ft.addRestriction(fr);
	return comunicazionitmercatoDAO.existsRecords(ft);
    }

    @Override
    public ComunicazioniTMercato insertCreaComunicazione(Integer codiceMercato, String[] listacodiciPosteggi, List<Integer> codiciUsoPercomunicazione) {

	ComunicazioniTMercato comunicazioniTMercato = null;
	if (codiciUsoPercomunicazione != null && !codiciUsoPercomunicazione.isEmpty()) {
	    log.debug("insertCreaComunicazione# Passati codice uso su cui fare le comunicazioni");
	} else {
	    log.debug("insertCreaComunicazione# Controllo se per la lista di posteggi passati ci sono concessione per più usi..");
	    List<Integer> r = vwConcessioniattiveService.findMercatoUsoConConcessioniAttiveByPosteggi(codiceMercato, listacodiciPosteggi);
	    if (!r.isEmpty() && r.size() > 1) {
		log.debug("insertCreaComunicazione# Per i posteggi scelti ci sono concessioni attive per più giorni (Usi)");
		throw new RuntimeException(getMessageFromBundle("comunicazionitmercato.service_error.concessioni_posteggio_piu_usi", null));
	    } else {
		log.debug("insertCreaComunicazione# Per i posteggi scelti ci sono concessioni attive per un solo giorno (Uso) = {}", r.get(0));
		codiciUsoPercomunicazione = new ArrayList<Integer>();
		codiciUsoPercomunicazione.addAll(r);
	    }
	}
	List<AutorizzazioniConcessioni> listAutorizzazioniConcessioni = new ArrayList<AutorizzazioniConcessioni>();
	for (int i = 0; i < listacodiciPosteggi.length; i++) {
	    for (Integer codiceUso : codiciUsoPercomunicazione) {
		AutorizzazioniConcessioni autorizzazioniConcessioni = autorizzazioniConcessioniService
			.findConcessioneAttualeByMercatoAndUsoAndPosteggio(codiceMercato, codiceUso, Integer.parseInt(listacodiciPosteggi[i]));
		if (EntityUtils.getNestedProperty(autorizzazioniConcessioni, "id.codice") != null) {
		    listAutorizzazioniConcessioni.add(autorizzazioniConcessioni);
		}
	    }
	}
	ComunicazioniServiceHelper<AutorizzazioniConcessioni> comunicazioniServiceHelper = new ComunicazioniConcessioneServiceHelper(
		comunicazioniManagerService, comunicazioniDConcessioniService);
	ComunicazioniT comunicazioniT = comunicazioniServiceHelper.inizializzaComunicazioni(listAutorizzazioniConcessioni);
	comunicazioniTService.update(comunicazioniT);
	comunicazioniTMercato = new ComunicazioniTMercato();
	comunicazioniTMercato.setComunicazioniT(comunicazioniT);
	Mercati m = mercatiService.findById(new PkId(codiceMercato));
	comunicazioniTMercato.setMercati(m);
	this.insert(comunicazioniTMercato);
	log.debug("insertCreaComunicazione# Popolata struttura base per le comunicazioni ....");
	return comunicazioniTMercato;
    }

    @Override
    public void deleteComunicazione(ComunicazioniTMercato comunicazioniTMercato) {

	log.debug("deleteComunicazione# start ... ");
	childDelete(comunicazioniTMercato);
	this.delete(comunicazioniTMercato);
	log.debug("deleteComunicazione# delete comunicazioniT .... ");
	comunicazioniTService.delete(comunicazioniTMercato.getComunicazioniT());
	log.debug("deleteComunicazione# end ... ");
	//
    }

    protected void childDelete(ComunicazioniTMercato comunicazioniTMercato) {

	log.debug("childDelete# start ...");
	List<ComunicazioniD> listComD = comunicazioniDService.findByComunicazioniT(comunicazioniTMercato.getComunicazioniT().getId().getCodice());
	for (ComunicazioniD comunicazioniD : listComD) {
	    List<ComunicazioniDConcessioni> listComConcD = comunicazioniDConcessioniService.findByComunicazioniD(comunicazioniD.getId().getCodice());
	    for (ComunicazioniDConcessioni comunicazioniDConcessioni : listComConcD) {
		log.debug("childDelete# comunicazioniDConcessioni = {}, comunicazioniD = {}", comunicazioniDConcessioni.getId().getCodice(),
			comunicazioniD.getId().getCodice());
		comunicazioniDConcessioniService.delete(comunicazioniDConcessioni);
		tmpStatiComunicazioniDService.delete(comunicazioniD.getId().getCodice());
		log.debug("childDelete# Eliminati i record sulla tabelle tmpcomunicazione per codice comunicazione d = {}", comunicazioniD.getId()
			.getCodice());
	    }
	    log.debug("childDelete# comunicazioniD = {}", comunicazioniD.getId().getCodice());
	    comunicazioniDService.delete(comunicazioniD);
	}
	log.debug("childDelete# end ...");
    }

    private void dataIntegration(ComunicazioniTMercato entity, boolean isUpdate) {

	if (entity == null) {
	    throw new RuntimeException("Il parametro ComunicazioniTMercato da validare è nullo");
	}
	fixMergeEntityProperties(entity);
    }

    protected void fixMergeEntityProperties(ComunicazioniTMercato entity) {

	//	ComunicazioniT comunicazioniT = comunicazioniTService.bindDomainObject(entity.getComunicazioniT(), Integer.class, "id.codice");
	//	entity.setComunicazioniT(comunicazioniT);
	//	Mercati mercati = mercatiService.bindDomainObject(entity.getMercati(), Integer.class, "id.codice");
	//	entity.setMercati(mercati);
    }

    protected boolean isDeleteAllowed(ComunicazioniTMercato entity) {

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

    @Override
    public void updateComunicazione(ComunicazioniTMercato comunicazioniTMercato, ComunicazioniT comunicazioniT, Boolean flagbloccaconfigurazione) {

	if (validateBloccoConfigurazione(comunicazioniT, flagbloccaconfigurazione)) {
	    this.update(comunicazioniTMercato);
	    if (BooleanUtils.toBoolean(flagbloccaconfigurazione)) {
		comunicazioniT.setStatoElaborazione(ComunicazioniTStatoEnum.DA_ELABORARE.value());
	    }
	    comunicazioniTService.update(comunicazioniT);
	}
    }

    private boolean validateBloccoConfigurazione(ComunicazioniT comunicazioniT, Boolean flagbloccaconfigurazione) {

	if (BooleanUtils.toBoolean(flagbloccaconfigurazione)) {
	    List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	    if (EntityUtils.getNestedProperty(comunicazioniT.getTipimovimento(), "id.tipomovimento") == null) {
		_ivs.add(new InvalidValue("comunicazionit.service_error.movimento_obbligatorio", null, null, null, null));
	    }
	    if (EntityUtils.getNestedProperty(comunicazioniT.getLetteretipo(), "id.codice") == null) {
		if (BooleanUtils.toBoolean(comunicazioniT.getFlgTrasformaPdf())) {
		    _ivs.add(new InvalidValue("comunicazionit.service_error.convert_pdf_no_allegato", null, null, null, null));
		}
		if (StringUtils.isNotBlank(comunicazioniT.getListaFirmatari())) {
		    _ivs.add(new InvalidValue("comunicazionit.service_error.firma_pdf_no_allegato", null, null, null, null));
		}
	    }
	    //	    if (EntityUtils.getNestedProperty(comunicazioniT.getMailtipo(), "id.codice") == null) {
	    //		_ivs.add(new InvalidValue("comunicazionit.service_error.movimento_mail_tipo_obbligatoria", null, null, null, null));
	    //	    }
	    if (!_ivs.isEmpty()) {
		this.throwValidationMessages(_ivs);
	    }
	}
	return true;
    }
}
