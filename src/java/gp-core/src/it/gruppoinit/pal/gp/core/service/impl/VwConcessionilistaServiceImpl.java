package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.infocamera.schema.sigeproexport.LISTAISTANZE;
import it.gruppoinit.infocamera.schema.sigeproexport.LISTAISTANZE.ISTANZA;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.VwConcessionilistaDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.OrderBySqlFormula.FunctionsEnum;
import it.gruppoinit.pal.gp.core.dao.helper.TipoQueryHelperEnum;
import it.gruppoinit.pal.gp.core.domain.Esportazioni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.VwConcessionilista;
import it.gruppoinit.pal.gp.core.domain.VwConcessionlistaId;
import it.gruppoinit.pal.gp.core.domain.helper.ConcessioniListHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.TmpEsportazioniService;
import it.gruppoinit.pal.gp.core.service.VwConcessionilistaService;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.client.SigeproExportWsClient;
import it.gruppoinit.sigepro.ws.sigeproexport.stub.Parametro;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VwConcessionilistaServiceImpl extends BaseServiceImpl<VwConcessionilista, PkId> implements VwConcessionilistaService {

    private static final Logger log = LoggerFactory.getLogger(VwConcessionilistaServiceImpl.class);
    // Costanti
    private final static double pageSize = 100;
    private VwConcessionilistaDAO vwConcessionilistaDAO;
    private TmpEsportazioniService tmpEsportazioniService;

    @Autowired
    public void setVwConcessionilistaDAO(VwConcessionilistaDAO vwConcessionilistaDAO) {

	this.vwConcessionilistaDAO = vwConcessionilistaDAO;
    }

    @Autowired
    public void setTmpEsportazioniService(TmpEsportazioniService tmpEsportazioniService) {

	this.tmpEsportazioniService = tmpEsportazioniService;
    }

    @Override
    protected Class<VwConcessionilista> getEntityClass() {

	return VwConcessionilista.class;
    }

    @Override
    public void delete(VwConcessionilista entity) {

	throw new NotImplementedException();
    }

    @Override
    public List<VwConcessionilista> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }

    @Override
    public VwConcessionilista findById(PkId id) {

	throw new NotImplementedException();
    }

    @Override
    public void insert(VwConcessionilista entity) {

	throw new NotImplementedException();
    }

    @Override
    public void update(VwConcessionilista entity) {

	throw new NotImplementedException();
    }

    @Override
    public List<VwConcessionilista> findConcessioniCriteria(VwConcessionilista vwConcessionilista) {

	return vwConcessionilistaDAO.findConcessioniCriteria(vwConcessionilista);
    }

    @Override
    public List<VwConcessionilista> findConcessioniCriteria(VwConcessionilista vwConcessionilista, Integer firstResult, Integer maxResults) {

	FilterTable filterTable = concessioniFilterToFilterTable(vwConcessionilista);
	List<VwConcessionilista> concessioniList = vwConcessionilistaDAO.findByFilterTable(filterTable, firstResult, maxResults);
	return concessioniList;
	//return vwConcessionilistaDAO.findConcessioniCriteria(vwConcessionilista, firstResult, maxResults);
    }

    @Override
    public int countByFilter(VwConcessionilista vwConcessionilista) {

	FilterTable filterTable = concessioniFilterToFilterTable(vwConcessionilista);
	int count = vwConcessionilistaDAO.countRecord(filterTable);
	return count;
	//return vwConcessionilistaDAO.countByFilter(vwConcessionilista);
    }

    private FilterTable concessioniFilterToFilterTable(VwConcessionilista vwConcessionilista) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	FilterRestriction fr = new FilterRestriction();
	if (vwConcessionilista.getTransientProgressivoDa() != null) {
	    fr.addFilterField(FilterUtils.greaterEqual("id.progressivo", vwConcessionilista.getTransientProgressivoDa(), Integer.class));
	}
	if (vwConcessionilista.getTransientProgressivoA() != null) {
	    fr.addFilterField(FilterUtils.smallerEqual("id.progressivo", vwConcessionilista.getTransientProgressivoA(), Integer.class));
	}
	if (StringUtils.isNotBlank(vwConcessionilista.getConcNumero())) {
	    fr.addFilterField(FilterUtils.equals("concNumero", vwConcessionilista.getConcNumero(), String.class));
	}
	if (vwConcessionilista.getConcIdmercato() != null) {
	    if (vwConcessionilista.getConcIdmercato().intValue() != 0) {
		fr.addFilterField(FilterUtils.equals("concIdmercato", vwConcessionilista.getConcIdmercato(), Integer.class));
	    }
	}
	if (vwConcessionilista.getConcIdposteggio() != null) {
	    if (vwConcessionilista.getConcIdposteggio() != 0) {
		fr.addFilterField(FilterUtils.equals("concIdposteggio", vwConcessionilista.getConcIdposteggio(), Integer.class));
	    }
	}
	if (vwConcessionilista.getConcIdmercatiuso() != null) {
	    if (vwConcessionilista.getConcIdmercatiuso() != 0) {
		fr.addFilterField(FilterUtils.equals("concIdmercatiuso", vwConcessionilista.getConcIdmercatiuso(), Integer.class));
	    }
	}
	if (vwConcessionilista.getIconcCodicecausale() != null && vwConcessionilista.getIconcCodicecausale() != 0) {
	    fr.addFilterField(FilterUtils.equals("iconcCodicecausale", vwConcessionilista.getIconcCodicecausale(), Short.class));
	}
	if (vwConcessionilista.getDataInizioRilascio() != null) {
	    fr.addFilterField(FilterUtils.greaterEqual("concDatarilascio", vwConcessionilista.getDataInizioRilascio(), Date.class));
	}
	if (vwConcessionilista.getDataFineRilascio() != null) {
	    fr.addFilterField(FilterUtils.smallerEqual("concDatarilascio", vwConcessionilista.getDataFineRilascio(), Date.class));
	}
	if (vwConcessionilista.getDataInizioScadenze() != null) {
	    fr.addFilterField(FilterUtils.greaterEqual("concDatascadenza", vwConcessionilista.getDataInizioScadenze(), Date.class));
	}
	if (vwConcessionilista.getDataFineScadenze() != null) {
	    fr.addFilterField(FilterUtils.smallerEqual("concDatascadenza", vwConcessionilista.getDataFineScadenze(), Date.class));
	}
	if (EntityUtils.getNestedProperty(vwConcessionilista.getTitolareConcessione(), "id.codice") != null) {
	    fr.addFilterField(FilterUtils
		    .equals("concCodicetitolare", vwConcessionilista.getTitolareConcessione().getId().getCodice(), Integer.class));
	}
	if (!(vwConcessionilista.getIstCodicerichiedente() == null || vwConcessionilista.getIstCodicerichiedente().equals(0))) {
	    fr.addFilterField(FilterUtils.equals("istCodicerichiedente", vwConcessionilista.getIstCodicerichiedente(), Integer.class));
	}
	if (vwConcessionilista.isConcAttiva()) {
	    fr.addFilterField(FilterUtils.equals("concAttiva", Boolean.TRUE, Integer.class));
	}
	if (vwConcessionilista.getDataStoricoDaTransient() != null) {
	    fr.addFilterField(FilterUtils.greaterEqual("dataStorico", vwConcessionilista.getDataStoricoDaTransient(), Date.class));
	}
	if (vwConcessionilista.getDataStoricoATransient() != null) {
	    fr.addFilterField(FilterUtils.smallerEqual("dataStorico", vwConcessionilista.getDataStoricoATransient(), Date.class));
	}
	if (EntityUtils.getNestedProperty(vwConcessionilista.getContipologiaregistri(), "id.codice") != null) {
	    fr.addFilterField(FilterUtils.equals("conCodregistro", vwConcessionilista.getContipologiaregistri().getId().getCodice(), Integer.class));
	}
	///////////////////////////////////////////// Filtri per l'istanza /////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	if (StringUtils.isNotBlank(vwConcessionilista.getIstanza().getNumeroistanza())) {
	    //det.add(Restrictions.eq("_istanza.numeroistanza", vwConcessionilista.getIstanza().getNumeroistanza()));
	    fr.addFilterField(FilterUtils.equals("numeroistanza", vwConcessionilista.getIstanza().getNumeroistanza(), "istanza", String.class));
	}
	if (vwConcessionilista.getIstanzadataDa() != null) {
	    //det.add(Restrictions.ge("_istanza.data", vwConcessionilista.getIstanzadataDa()));
	    fr.addFilterField(FilterUtils.greaterEqual("data", vwConcessionilista.getIstanzadataDa(), "istanza", Date.class));
	}
	if (vwConcessionilista.getIstanzadataA() != null) {
	    //det.add(Restrictions.le("_istanza.data", vwConcessionilista.getIstanzadataA()));
	    fr.addFilterField(FilterUtils.smallerEqual("data", vwConcessionilista.getIstanzadataA(), "istanza", Date.class));
	}
	if (EntityUtils.getNestedProperty(vwConcessionilista.getIstanza().getAlberoproc(), "id.codice") != null) {
	    //	    det.createCriteria("_istanza.alberoproc", "_alberoproc");
	    //	    det.add(Restrictions.eq("_alberoproc.id.codice", vwConcessionilista.getIstanza().getAlberoproc().getId().getCodice()));
	    fr.addFilterField(FilterUtils.equals("id.codice", vwConcessionilista.getIstanza().getAlberoproc().getId().getCodice(),
		    "istanza.alberoproc", Integer.class));
	}
	if (EntityUtils.getNestedProperty(vwConcessionilista.getIstanza().getProcedura(), "id.codice") != null) {
	    //	    det.createCriteria("_istanza.procedura", "_procedura");
	    //	    det.add(Restrictions.eq("_procedura.id.codice", vwConcessionilista.getIstanza().getProcedura().getId().getCodice()));
	    fr.addFilterField(FilterUtils.equals("id.codice", vwConcessionilista.getIstanza().getProcedura().getId().getCodice(),
		    "istanza.procedura", Integer.class));
	}
	if (EntityUtils.getNestedProperty(vwConcessionilista.getIstanzestradario().getStradario(), "id.codice") != null) {
	    //	    det.createCriteria("_istanza.istanzestradarios", "istanzestradario");
	    //	    det.createCriteria("istanzestradario.stradario", "_stradario");
	    //	    det.add(Restrictions.eq("_stradario.id.codice", vwConcessionilista.getIstanzestradario().getStradario().getId().getCodice()));
	    //	    isStradarioJoinPresent = true;
	    fr.addFilterField(FilterUtils.equals("id.codice", vwConcessionilista.getIstanzestradario().getStradario().getId().getCodice(),
		    "istanza.istanzestradarios.stradario", Integer.class));
	}
	if (StringUtils.isNotBlank(vwConcessionilista.getIstanzestradario().getCap())) {
	    //	    vwConcessionilista.getIstanzestradario().getStradario().getId().getCodice()
	    //		det.createCriteria("_istanza.istanzestradarios", "istanzestradario");
	    //		isIstanzestradarioJoinPresent = true;
	    //	    }
	    //	    det.add(Restrictions.eq("istanzestradario.cap", vwConcessionilista.getIstanzestradario().getCap()));
	    fr.addFilterField(FilterUtils.equals("cap", vwConcessionilista.getIstanzestradario().getCap(), "istanza.istanzestradarios", String.class));
	}
	if (vwConcessionilista.getIstanza().getComune() != null
		&& StringUtils.isNotBlank(vwConcessionilista.getIstanza().getComune().getCodicecomune())) {
	    //	    det.createCriteria("_istanza.comune", "_comune");
	    //	    det.add(Restrictions.eq("_comune.codicecomune", vwConcessionilista.getIstanza().getComune().getCodicecomune()));
	    fr.addFilterField(FilterUtils.equals("codicecomune", vwConcessionilista.getIstanza().getComune().getCodicecomune(), "istanza.comune",
		    String.class));
	}
	if (EntityUtils.getNestedProperty(vwConcessionilista.getIstanza().getRichiedente(), "id.codice") != null) {
	    //	    det.createAlias("_istanza.richiedente", "_richiedenteistanza", Criteria.LEFT_JOIN);
	    //	    det.add(Restrictions.eq("_richiedenteistanza.id.codice", vwConcessionilista.getIstanza().getRichiedente().getId().getCodice()));	    
	    fr.addFilterField(FilterUtils.equals("id.codice", vwConcessionilista.getIstanza().getRichiedente().getId().getCodice(),
		    "istanza.richiedente", Integer.class));
	}
	ft.addRestriction(fr);
	//	// ///ORDINAMENTI
	// Controllo che siano stati passati ii campi di ordinamento
	if (StringUtils.isNotBlank(vwConcessionilista.getOrderBy())) {
	    // I campi di ordinamento vengono passati dal form nella forma field1,filed2..fieldN e indicano l'ordine 
	    // dei campi per cui si deve ordinare
	    // Faccio lo split per recuperare ogni singolo campo    
	    String[] field = vwConcessionilista.getOrderBy().split(",");
	    String[] padNumeroconcessioni = new String[] { "20", "' '" };
	    String[] padNumeroistanza = new String[] { "20", "' '" };
	    // Ciclo tutti i campi in modo per cui devo ordinare in modo che se verranno aggiunti successivamente dei nuovi
	    // basterà inserire la nuova condizione di ordinamento
	    for (int i = 0; i < field.length; i++) {
		if (field[i].equals("concDatarilascio")) {
		    ft.addOrder(FilterUtils.order(field[i], vwConcessionilista.getOrderAscDesc()));
		}
		if (field[i].equalsIgnoreCase("istanza.data")) {
		    ft.addOrder(FilterUtils.order("data", "istanza", vwConcessionilista.getOrderAscDesc()));
		}
		if (field[i].equalsIgnoreCase("anagrafeconcessione")) {
		    ft.addOrder(FilterUtils.order("nominativo", "titolare", vwConcessionilista.getOrderAscDesc()));
		}
		if (field[i].equals("istanza.numeroistanza")) {
		    switch (vwConcessionilista.getOrderAscDesc()) {
		    case ASC:
			ft.addOrder(FilterUtils.orderAsc("numeroistanza", "istanza", FunctionsEnum.LPAD_FUNCTION, padNumeroistanza));
			break;
		    case DESC:
			ft.addOrder(FilterUtils.orderDesc("numeroistanza", "istanza", FunctionsEnum.LPAD_FUNCTION, padNumeroistanza));
			break;
		    default:
			ft.addOrder(FilterUtils.order("numeroistanza", "istanza", vwConcessionilista.getOrderAscDesc()));
			break;
		    }
		}
		if (field[i].equals("concNumero")) {
		    switch (vwConcessionilista.getOrderAscDesc()) {
		    case ASC:
			ft.addOrder(FilterUtils.orderAsc(field[i], FunctionsEnum.LPAD_FUNCTION, padNumeroconcessioni));
			break;
		    case DESC:
			ft.addOrder(FilterUtils.orderDesc(field[i], FunctionsEnum.LPAD_FUNCTION, padNumeroconcessioni));
			break;
		    default:
			ft.addOrder(FilterUtils.order(field[i], vwConcessionilista.getOrderAscDesc()));
			break;
		    }
		}
		if (field[i].equals("istanza.istanzestradario.stradario.descrizione")) {
		    ft.addOrder(FilterUtils.order("descrizione", "istanza.istanzestradarios.stradario", vwConcessionilista.getOrderAscDesc()));
		}
	    }
	    // BOCCI 2012-10-03 - ORDINAMENTO FISSO OLTRE AI CRITERI PRECEDENTI ANCHE PER DATASTORICO 
	    // (AUTORIZZAZIONI_SUBENTRI.DATA_STORICO DESC, AUTORIZZAZIONI_SUBENTRI.ID DESC)
	    ft.addOrder(FilterUtils.orderDesc("dataStorico"));
	    ft.addOrder(FilterUtils.orderDesc("id.progressivo"));
	}
	return ft;
    }

    @Override
    public byte[] exportConcessioni(VwConcessionilista vwConcessionilista, Integer codicetipoesportazione, String idComuneTipoesportazione,
	    String email, boolean isInviaMail) {

	// §§§BEGIN§§§
	//
	// Definisco la formattazione della data
	long t1 = System.currentTimeMillis();
	log.debug("exportConcessioni# Start processo export concessioni, modalità MS");
	DateFormat myDateFormatOut = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
	// Date date = new Date();
	LISTAISTANZE listaconcessioni = new LISTAISTANZE();
	List<ConcessioniListHelper> listConcessioni = new ArrayList<ConcessioniListHelper>();
	if (BooleanUtils.toBoolean(vwConcessionilista.isConcAttiva()) && vwConcessionilista.getAttiveAllaDataTransient() != null) {
	    log.debug("exportConcessioni# 1. Export concessioni attive fino a {}  ",
		    Utilities.formatDate(vwConcessionilista.getAttiveAllaDataTransient(), WebConstants.DATE_FORMAT_PATTERN));
	    listConcessioni = this.findConcessioniListHelper(vwConcessionilista, null, null, true);
	    ConcessioniListHelper vwConc = null;
	    for (ConcessioniListHelper vwConcessionilistaTemp : listConcessioni) {
		vwConc = new ConcessioniListHelper();
		// Se sono vere queste condizioni allora devo elaborare la risposta e recuperare la riga completa in quanto l'oggetto contiene solo
		// conc_id e idcomune
		VwConcessionilista filterTemp = null;
		VwConcessionlistaId id = null;
		for (ConcessioniListHelper concessioniListHelper : listConcessioni) {
		    filterTemp = new VwConcessionilista();
		    id = new VwConcessionlistaId();
		    id.setIdcomune(concessioniListHelper.getIdcomune());
		    filterTemp.setId(id);
		    filterTemp.setConcId(concessioniListHelper.getConc_id().intValue());
		    //5. Popolare la lista da ritornare
		    List<ConcessioniListHelper> tempList = this.findConcessioniListHelper(filterTemp, null, null,
			    TipoQueryHelperEnum.SELECT_WHERE_CONC_ID);
		    if (!tempList.isEmpty()) {
			//		    list.add(tempList.get(0));
			vwConc = tempList.get(0);
		    }
		}
		ISTANZA concessione = new ISTANZA();
		concessione.setIDCOMUNE(ORMHelper.getIdcomune());
		//String codiceconcessione = vwConcessionilistaTemp.getId().getConcId().toString();
		String codiceconcessione = vwConc.getConc_id().toString();
		concessione.setCODICE(codiceconcessione);
		//String outdate = myDateFormatOut.format(vwConcessionilistaTemp.getConcDatarilascio());
		String outdate = myDateFormatOut.format(vwConc.getConc_datarilascio());
		concessione.setDATA(outdate);
		if (StringUtils.isNotBlank(vwConc.getIst_codicecomune())) {
		    concessione.setCODICECOMUNE(vwConc.getIst_codicecomune());
		}
		listaconcessioni.getISTANZA().add(concessione);
	    }
	} else {
	    log.debug("exportConcessioni# 2. Export concessioni ");
	    listConcessioni = this.findConcessioniListHelper(vwConcessionilista, null, null);
	    for (ConcessioniListHelper vwConc : listConcessioni) {
		ISTANZA concessione = new ISTANZA();
		concessione.setIDCOMUNE(ORMHelper.getIdcomune());
		//String codiceconcessione = vwConcessionilistaTemp.getId().getConcId().toString();
		String codiceconcessione = vwConc.getConc_id().toString();
		concessione.setCODICE(codiceconcessione);
		//String outdate = myDateFormatOut.format(vwConcessionilistaTemp.getConcDatarilascio());
		String outdate = myDateFormatOut.format(vwConc.getConc_datarilascio());
		concessione.setDATA(outdate);
		if (StringUtils.isNotBlank(vwConc.getIst_codicecomune())) {
		    concessione.setCODICECOMUNE(vwConc.getIst_codicecomune());
		}
		listaconcessioni.getISTANZA().add(concessione);
	    }
	}
	//	    }
	//	}
	// WEB SERVICE SIGEPROEXPORT
	/* Vengono utilizzati gli Stub creati per Infocamere quindi la lista istanze in realtà
	 * rappresenta la lista di IAttività  
	 */
	Parametro parametro1 = new Parametro();
	parametro1.setNOME("PROGRESSIVO_INVIO");
	parametro1.setVALORE("001");
	Parametro parametro2 = new Parametro();
	parametro2.setNOME("DATA_INVIO");
	Date _date = new Date();
	String dataInvio = myDateFormatOut.format(_date);
	parametro2.setVALORE(dataInvio);
	Parametro[] listaParametri = { parametro1, parametro2 };
	byte[] responseByte;
	if (isInviaMail) {
	    responseByte = SigeproExportWsClient.exportMail(ORMHelper.getToken(), listaconcessioni, codicetipoesportazione, idComuneTipoesportazione,
		    listaParametri, email, true);
	} else {
	    responseByte = SigeproExportWsClient.export(ORMHelper.getToken(), listaconcessioni, codicetipoesportazione, idComuneTipoesportazione,
		    listaParametri, true);
	}
	long t2 = System.currentTimeMillis();
	String time = String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes(t2 - t1), TimeUnit.MILLISECONDS.toSeconds(t2 - t1)
		- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(t2 - t1)));
	log.debug("exportConcessioni# Processo servito in {}", time);
	return responseByte;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void clear() {

	vwConcessionilistaDAO.clear();
    }

    @Override
    public VwConcessionilista populateFilterCessateUltimoMese() {

	Calendar toDay = GregorianCalendar.getInstance();
	VwConcessionilista vwConcessionilista = new VwConcessionilista();
	vwConcessionilista.setTransientProgressivoDa(99999999);
	vwConcessionilista.setConcAttiva(false);
	vwConcessionilista.setDataStoricoATransient(toDay.getTime());
	toDay.roll(Calendar.MONTH, -1);
	vwConcessionilista.setDataStoricoDaTransient(toDay.getTime());
	return vwConcessionilista;
    }

    @Override
    public VwConcessionilista populateFilterSubentriUltimoMese() {

	Calendar toDay = GregorianCalendar.getInstance();
	VwConcessionilista vwConcessionilista = new VwConcessionilista();
	vwConcessionilista.setTransientProgressivoA(99999998);
	vwConcessionilista.setConcAttiva(false);
	vwConcessionilista.setDataStoricoATransient(toDay.getTime());
	toDay.roll(Calendar.MONTH, -1);
	vwConcessionilista.setDataStoricoDaTransient(toDay.getTime());
	return vwConcessionilista;
    }

    @Override
    public VwConcessionilista populateFilterRilasciUltimoMese() {

	Calendar toDay = GregorianCalendar.getInstance();
	VwConcessionilista vwConcessionilista = new VwConcessionilista();
	vwConcessionilista.setTransientProgressivoDa(99999998);
	vwConcessionilista.setConcAttiva(true);
	vwConcessionilista.setDataFineRilascio(toDay.getTime());
	toDay.roll(Calendar.MONTH, -1);
	vwConcessionilista.setDataInizioRilascio(toDay.getTime());
	return vwConcessionilista;
    }

    @Override
    public boolean isConcessionePresenteByMercato(Integer codiceIstanza, Integer codiceMercato) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceIstanza, "istanza", Integer.class));
	fr.addFilterField(FilterUtils.equals("concIdmercato", codiceMercato, Integer.class));
	fr.addFilterField(FilterUtils.equals("concAttiva", Boolean.TRUE, Boolean.class));
	ft.addRestriction(fr);
	List<VwConcessionilista> list = vwConcessionilistaDAO.findByFilterTable(ft);
	if (!list.isEmpty()) {
	    return true;
	}
	return false;
    }

    @Override
    public boolean isConcessionePresenteByMercatoAndUsoAndPosteggio(Integer codiceMercato, Integer codiceMercatoUso, Integer codicePosteggio) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("concIdmercato", codiceMercato, Integer.class));
	fr.addFilterField(FilterUtils.equals("concIdmercatiuso", codiceMercatoUso, Integer.class));
	fr.addFilterField(FilterUtils.equals("concIdposteggio", codicePosteggio, Integer.class));
	fr.addFilterField(FilterUtils.equals("concAttiva", Boolean.TRUE, Boolean.class));
	ft.addRestriction(fr);
	List<VwConcessionilista> list = vwConcessionilistaDAO.findByFilterTable(ft);
	if (!list.isEmpty()) {
	    return false;
	}
	return true;
    }

    @Override
    public String exportModalitaPentaho(VwConcessionilista vwConcessionilista, Esportazioni esportazioni, Date data, String email,
	    String contestoExport, boolean isInvioMail) {

	long t1 = System.currentTimeMillis();
	log.debug("exportModalitaPentaho# Start export pentaho, contesto : {}", contestoExport);
	String sessionId = ORMHelper.getToken();
	log.debug("exportModalitaPentaho# Cancello i record su tmp_esportazioni con sessionId: {}", sessionId);
	tmpEsportazioniService.deleteBysessionId(sessionId);
	log.debug("exportModalitaPentaho# Inizio esportazione concessioni. Invio email. {}", isInvioMail);
	vwConcessionilistaDAO.exportModalitaPentaho(vwConcessionilista, esportazioni, data, email, contestoExport, isInvioMail);
	long t2 = System.currentTimeMillis();
	String time = String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes(t2 - t1), TimeUnit.MILLISECONDS.toSeconds(t2 - t1)
		- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(t2 - t1)));
	log.debug("exportModalitaPentaho# Processo servito in {}", time);
	return sessionId;
    }

    @Override
    public List<ConcessioniListHelper> findConcessioniListHelper(VwConcessionilista vwConcessionilista, Integer firstResult, Integer maxResults) {

	return vwConcessionilistaDAO.findConcessioniListHelper(vwConcessionilista, firstResult, maxResults);
    }

    @Override
    public List<ConcessioniListHelper> findConcessioniListHelper(VwConcessionilista vwConcessionilista, Integer firstResult, Integer maxResults,
	    boolean isSelectGroupByConcId) {

	return vwConcessionilistaDAO.findConcessioniListHelper(vwConcessionilista, firstResult, maxResults, isSelectGroupByConcId);
    }

    @Override
    public List<ConcessioniListHelper> findConcessioniListHelper(VwConcessionilista vwConcessionilista, Integer firstResult, Integer maxResults,
	    TipoQueryHelperEnum tipoQueryHelperEnum) {

	return vwConcessionilistaDAO.findConcessioniListHelper(vwConcessionilista, firstResult, maxResults, tipoQueryHelperEnum);
    }

    @Override
    public int countConcessioniListHelper(VwConcessionilista vwConcessionilista) {

	return vwConcessionilistaDAO.countConcessioniListHelper(vwConcessionilista);
    }

    @Override
    public int countConcessioniListHelper(VwConcessionilista vwConcessionilista, boolean isCountDistinctConcId) {

	// TODO Auto-generated method stub
	return vwConcessionilistaDAO.countConcessioniListHelper(vwConcessionilista, isCountDistinctConcId);
    }
}
