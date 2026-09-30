package it.gruppoinit.pal.gp.core.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.IAttivitaSnapshotDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.OrderBySqlFormula.FunctionsEnum;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellid;
import it.gruppoinit.pal.gp.core.domain.IAttivita;
import it.gruppoinit.pal.gp.core.domain.IAttivitaSnapshot;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2dati;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2datiId;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2datiSnapshot;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2datiSnapshotId;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2modTSnapshot;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2modTSnapshotId;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2modellit;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2dati;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2datiId;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.IASnapshotCampoHelper;
import it.gruppoinit.pal.gp.core.domain.helper.IASnapshotSchedaHelper;
import it.gruppoinit.pal.gp.core.domain.helper.IASnapshotValoriHelper;
import it.gruppoinit.pal.gp.core.domain.helper.IASnapshotViewerHelper;
import it.gruppoinit.pal.gp.core.domain.helper.WsExportBean;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.IAttivitaFilter;
import it.gruppoinit.pal.gp.core.features.attivita.IAttivitaService;
import it.gruppoinit.pal.gp.core.features.attivita.snapshots.logic.ICalcoloSnapshotService;
import it.gruppoinit.pal.gp.core.features.attivita.snapshots.logic.ISnapshotDAO;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellidService;
import it.gruppoinit.pal.gp.core.service.IAttivitaSnapshotService;
import it.gruppoinit.pal.gp.core.service.IAttivitadyn2datiService;
import it.gruppoinit.pal.gp.core.service.IAttivitadyn2datiSnapshotService;
import it.gruppoinit.pal.gp.core.service.IAttivitadyn2modTSnapshotService;
import it.gruppoinit.pal.gp.core.service.IAttivitadyn2modellitService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.Istanzedyn2datiService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;

/**
 * 
 * @author gianpaolot
 */
@Service
public class IAttivitaSnapshotServiceImpl extends BaseServiceImpl<IAttivitaSnapshot, PkId> implements IAttivitaSnapshotService {

    private static final Logger log = LoggerFactory.getLogger(IAttivitaSnapshotServiceImpl.class);
    private IAttivitaSnapshotDAO iattivitasnapshotDAO;
    private IAttivitadyn2modellitService iAttivitadyn2modellitService;
    private IAttivitadyn2datiService iAttivitadyn2datiService;
    private IAttivitadyn2datiSnapshotService iAttivitadyn2datiSnapshotService;
    private IAttivitadyn2modTSnapshotService iAttivitadyn2modTSnapshotService;
    private IstanzeService istanzeService;
    private Istanzedyn2datiService istanzedyn2datiService;
    private Dyn2ModellidService dyn2ModellidService;
    private MovimentiService movimentiService;
    ////////////////////////////////////////////////////////////////////////
    private IAttivitaService iAttivitaService;
    @Autowired
    private ISnapshotDAO snapshotDAO;
    private final static Integer MAX_RESULT = 20;
    @Autowired
    private ICalcoloSnapshotService calcoloSnapshotService;

    @Autowired
    public void setDyn2ModellidService(Dyn2ModellidService dyn2ModellidService) {

	this.dyn2ModellidService = dyn2ModellidService;
    }

    @Autowired
    public void setiAttivitadyn2modellitService(IAttivitadyn2modellitService iAttivitadyn2modellitService) {

	this.iAttivitadyn2modellitService = iAttivitadyn2modellitService;
    }

    @Autowired
    public void setiAttivitadyn2datiService(IAttivitadyn2datiService iAttivitadyn2datiService) {

	this.iAttivitadyn2datiService = iAttivitadyn2datiService;
    }

    @Autowired
    public void setIAttivitaSnapshotDAO(IAttivitaSnapshotDAO iattivitasnapshotDAO) {

	this.iattivitasnapshotDAO = iattivitasnapshotDAO;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setIstanzedyn2datiService(Istanzedyn2datiService istanzedyn2datiService) {

	this.istanzedyn2datiService = istanzedyn2datiService;
    }

    @Autowired
    public void setiAttivitadyn2datiSnapshotService(IAttivitadyn2datiSnapshotService iAttivitadyn2datiSnapshotService) {

	this.iAttivitadyn2datiSnapshotService = iAttivitadyn2datiSnapshotService;
    }

    @Autowired
    public void setiAttivitadyn2modTSnapshotService(IAttivitadyn2modTSnapshotService iAttivitadyn2modTSnapshotService) {

	this.iAttivitadyn2modTSnapshotService = iAttivitadyn2modTSnapshotService;
    }

    @Autowired
    public void setMovimentiService(MovimentiService movimentiService) {

	this.movimentiService = movimentiService;
    }

    /////////////////////////////////////////////
    @Autowired
    public void setiAttivitaService(IAttivitaService iAttivitaService) {

	this.iAttivitaService = iAttivitaService;
    }

    @Override
    protected Class<IAttivitaSnapshot> getEntityClass() {

	return IAttivitaSnapshot.class;
    }

    @Override
    public List<IAttivitaSnapshot> findAll(Integer firstResult, Integer maxResult) {

	return iattivitasnapshotDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(IAttivitaSnapshot entity) {

	if (validateEntity(entity)) {
	    iattivitasnapshotDAO.insert(entity);
	}
    }

    @Override
    public IAttivitaSnapshot findById(PkId id) {

	return iattivitasnapshotDAO.findById(id);
    }

    @Override
    public void update(IAttivitaSnapshot entity) {

	if (validateEntity(entity)) {
	    iattivitasnapshotDAO.update(entity);
	}
    }

    @Override
    public void delete(IAttivitaSnapshot entity) {

	if (isDeleteAllowed(entity)) {
	    childDelete(entity);
	    iattivitasnapshotDAO.delete(entity);
	}
    }

    @Override
    public List<IAttivitaSnapshot> findByAttivitaAndData(Integer codiceAttivita, Date dataValidita) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceAttivita, "attivita", Integer.class));
	fr.addFilterField(FilterUtils.equals("data", dataValidita, Date.class));
	ft.addRestriction(fr);
	return iattivitasnapshotDAO.findByFilterTable(ft, null, null);
    }

    @Override
    public List<IAttivitaSnapshot> findByAttivitaAndIstanza(Integer codice, Integer codiceIstanza) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codice, "attivita", Integer.class));
	fr.addFilterField(FilterUtils.equals("id.codice", codiceIstanza, "istanza", Integer.class));
	ft.addRestriction(fr);
	return iattivitasnapshotDAO.findByFilterTable(ft, null, null);
    }

    @Override
    public IAttivitaSnapshot findBeforeData(Integer codiceAttivita, Date dataValidita) {

	return iattivitasnapshotDAO.findBeforeData(codiceAttivita, dataValidita);
    }

    @Override
    public List<IAttivitaSnapshot> findAfterData(Integer codiceAttivita, Date dataValidita) {

	return iattivitasnapshotDAO.findAfterData(codiceAttivita, dataValidita);
    }

    @Override
    public IAttivitaSnapshot findBeforeDataAndOrdine(Integer codiceAttivita, Date dataValidita, Integer ordine) {

	return iattivitasnapshotDAO.findBeforeDataAndOrdine(codiceAttivita, dataValidita, ordine);
    }

    @Override
    public void updateSettaAttiva(List<Istanze> istanzes, IAttivitaSnapshot attivitaSnapshot) {

	log.debug("Inizio aggiornamento campo attiva del record IAttivitaSnapshot con codice {}..... ", attivitaSnapshot.getId().getCodice());
	boolean isAttiva = istanzeService.findIfIsAttivitaAttivaFromIstanze(istanzes);
	log.debug("Aggiorno il valore attiva con {}", isAttiva);
	attivitaSnapshot.setAttiva(isAttiva);
	this.update(attivitaSnapshot);
	log.debug("Fine aggiornamento campo attiva del record IAttivitaSnapshot con codice {}.... ", attivitaSnapshot.getId().getCodice());
    }

    @Override
    public List<IAttivitaSnapshot> findByAttivita(Integer codiceAttivita) {

	return iattivitasnapshotDAO.findByAttivita(codiceAttivita);
    }

    @Override
    protected void childDelete(IAttivitaSnapshot entity) {

	//Set<IAttivitadyn2datiSnapshot> setDyn2Dati = entity.getAttivitadyn2datiSnapshots();
	List<IAttivitadyn2datiSnapshot> listDyn2Dati = iAttivitadyn2datiSnapshotService.findByIAttivitaSnapshot(entity.getId().getCodice());
	for (IAttivitadyn2datiSnapshot iAttivitadyn2datiSnapshot : listDyn2Dati) {
	    iAttivitadyn2datiSnapshotService.delete(iAttivitadyn2datiSnapshot);
	}
	//Set<IAttivitadyn2modTSnapshot> attivitadyn2modTSnapshots = entity.getAttivitadyn2modTSnapshots();
	List<IAttivitadyn2modTSnapshot> attivitadyn2modTSnapshots = iAttivitadyn2modTSnapshotService
		.findByfindByIAttivitaSnapshot(entity.getId().getCodice());
	for (IAttivitadyn2modTSnapshot iAttivitadyn2modTSnapshot : attivitadyn2modTSnapshots) {
	    iAttivitadyn2modTSnapshotService.delete(iAttivitadyn2modTSnapshot);
	}
	iattivitasnapshotDAO.flush();
	iattivitasnapshotDAO.clear();
    }

    @Override
    public void deleteByIstanza(Integer codiceIstanzaScollegata) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("istanzaId", codiceIstanzaScollegata, Integer.class));
	ft.addRestriction(fr);
	List<IAttivitaSnapshot> list = iattivitasnapshotDAO.findByFilterTable(ft, null, null);
	if (!list.isEmpty()) {
	    this.delete(list.get(0));
	}
    }

    @Override
    public void updateRutineSnapShot(boolean isScollega, Integer codiceIstanza) {

	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	IAttivita attivita = istanza.getAttivita();
	//iAttivitaService.updateAndAggiornaSnapshot(attivita, istanza.getDatavalidita(), codiceIstanza);
	this.updateDatiDinamiciSnapshots(attivita, istanza.getDatavalidita());
    }

    @Override
    public void updateElaboraSnapshotAttivita(Integer codiceIattivita) {

	if (codiceIattivita == null) {
	    throw new IllegalArgumentException("Il codice attività non può essere nullo");
	}
	IAttivita iAttivita = iAttivitaService.findById(new PkId(codiceIattivita));
	if (iAttivita == null) {
	    throw new IllegalArgumentException("Attività con codice " + codiceIattivita + " nulla");
	}
	List<Istanze> listIstanzeAttivita = istanzeService.findByAttivita(codiceIattivita);
	// Passo tutte  li istanze dell'attivta ed elaboro gli snapshot
	for (Istanze istanze : listIstanzeAttivita) {
	    iAttivitaService.updateSnapShot(istanze.getId().getCodice());
	}
	// Riporta sull'ultimo snapshot i dati gestite manulamente sulla scheda dell'attività.
	if (iAttivita.getIstanza().getDatavalidita() != null) {
	    iAttivitaService.updateSnapShotCopiaDatiDinamici(iAttivita);
	}
    }

    @Override
    public String updateElaboraSnapshotAttivita() {

	StringBuilder risultato = new StringBuilder("RISULTATO ELABORAZIONE SNAP SHOT ATTIVITA':<br /><br />");
	StringBuilder errori = new StringBuilder();
	int countAttivita = 0;
	int coutErrori = 0;
	Integer firstResult = 0;
	// Recupero tutte le attività che non hanno uno snapshot
	List<Integer> list = iAttivitaService.findIdAttivitaWithoutSnapshot(0, MAX_RESULT);
	while (!list.isEmpty()) {
	    countAttivita += list.size();
	    for (Integer codicettivita : list) {
		try {
		    this.calcoloSnapshotService.ricalcola(codicettivita);
		    this.iattivitasnapshotDAO.flush();
		    this.iattivitasnapshotDAO.commit();
		} catch (Exception e) {
		    IAttivita iAttivita = iAttivitaService.findById(new PkId(codicettivita));
		    String descrizioneAttivita = iAttivita.getDenominazione();
		    Integer codice = iAttivita.getId().getCodice();
		    log.error("Errore durante l'elaborazione dello snap shot dell'attività {} (cod: {} ): {} ",
			    new Object[] { descrizioneAttivita, codice, e });
		    errori.append("Errore durante l'elaborazione dello snap shot dell'attività:" +
			    iAttivita.getDenominazione() +
			    "(" +
			    iAttivita.getId() +
			    ")<br />");
		    coutErrori++;
		}
	    }
	    firstResult = firstResult + MAX_RESULT;
	    list = iAttivitaService.findIdAttivitaWithoutSnapshot(0, MAX_RESULT);
	}
	int attivitaElaborate = countAttivita - coutErrori;
	risultato.append("Attenzione, le  Attività composte esclusivamente da istanze senza data di validità continueranno ad essere elaborate, " +
		"in quanto tali istanze non permettono la creazione di snapshot<br />(La funzionalità elabora tutte le attività senza snapshot). <br />");
	risultato.append(" <br />");
	risultato.append("Num. attività da elaborare: " + countAttivita + "<br />");
	risultato.append("Num. attività  elaborate: " + attivitaElaborate + "<br />");
	if (coutErrori > 0) {
	    risultato.append("Per verificare gli errori controllare il file sigepro2.log<br />");
	    risultato.append("Errori:<br />");
	    risultato.append(errori + "<br />");
	}
	return risultato.toString();
    }

    //    protected boolean isDeleteAllowed(IAttivitaSnapshot entity) {
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
    @Override
    public IASnapshotViewerHelper populateSnapshotViewerHelper(Integer codiceAttivita) {

	IASnapshotViewerHelper helper = new IASnapshotViewerHelper();
	List<IASnapshotSchedaHelper> listaSchedes = new ArrayList<IASnapshotSchedaHelper>();
	List listaSchede = iattivitasnapshotDAO.findListaSchede(codiceAttivita);
	for (Object arrayValori : listaSchede) {
	    if (arrayValori instanceof Object[]) {
		Object[] valori = (Object[]) arrayValori;
		// distinct i_attivitadyn2mod_t_snapshot.idcomune, dyn2_modellit.id as idModello, dyn2_modellit.codice_scheda, dyn2_modellit.descrizione
		String idcomune = (String) valori[0];
		Integer idmodello = (Integer) valori[1];
		String codiceScheda = (String) valori[2];
		String descrizioneScheda = (String) valori[3];
		IASnapshotSchedaHelper scheda = new IASnapshotSchedaHelper();
		scheda.setDescrizione(descrizioneScheda);
		scheda.setToolTip(codiceScheda);
		int maxIndice = iAttivitadyn2datiSnapshotService.getMaxIndice(codiceAttivita, idmodello);
		scheda.setMaxIndice(maxIndice);
		int maxIndiceMolteplicita = iAttivitadyn2datiSnapshotService.getMaxIndiceMolteplicita(codiceAttivita, idmodello);
		scheda.setMaxIndiceMolteplicita(maxIndiceMolteplicita);
		List<Dyn2Modellid> listaCampischeda = dyn2ModellidService.findRigheModello(idmodello);
		if (listaCampischeda.size() == 0) {
		    continue;
		}
		List<IASnapshotCampoHelper> listaCampi = new ArrayList<IASnapshotCampoHelper>();
		for (Dyn2Modellid d2md : listaCampischeda) {
		    if (d2md.getDyn2Campi() != null) {
			IASnapshotCampoHelper ch = new IASnapshotCampoHelper();
			ch.setCodice(d2md.getDyn2Campi().getId().getCodice());
			ch.setNomeCampo(d2md.getDyn2Campi().getNomecampo());
			ch.setDescrizione(d2md.getDyn2Campi().getDescrizione());
			ch.setToolTip(d2md.getDyn2Campi().getEtichetta());
			listaCampi.add(ch);
		    }
		}
		scheda.setListaCampi(listaCampi);
		listaSchedes.add(scheda);
	    }
	}
	helper.setListaSchede(listaSchedes);
	List<IASnapshotValoriHelper> listaSnapshots = new ArrayList<IASnapshotValoriHelper>();
	List<IAttivitaSnapshot> ls = this.findByAttivita(codiceAttivita);
	for (IAttivitaSnapshot ias : ls) {
	    IASnapshotValoriHelper sh = new IASnapshotValoriHelper();
	    sh.setId(ias.getId().getCodice());
	    sh.setAttiva(ias.getAttiva());
	    sh.setOperante(ias.getOperante());
	    sh.setData(ias.getData());
	    sh.setDenominazione(ias.getDenominazione());
	    sh.setCodiceIstanzaUltima(ias.getIstanza().getId().getCodice());
	    sh.setNumeroIstanzaUltima(ias.getIstanza().getNumeroistanza());
	    sh.setSoftwareIstanzaUltima(ias.getIstanza().getSoftware().getCodice());
	    sh.setCodiceOsservatorio(ias.getCodiceOsservatorio());
	    if (ias.getIstanza().getAlberoproc() != null) {
		if (ias.getIstanza().getAlberoproc().getVwAlberoproc() != null) {
		    sh.setDescrizioneIntervento(ias.getIstanza().getAlberoproc().getVwAlberoproc().getScDescrizione());
		}
	    }
	    if (ias.getTipologiaAttivita() != null) {
		if (ias.getTipologiaAttivita().getId() != null) {
		    if (ias.getTipologiaAttivita().getId().getCodice() != null) {
			sh.setTipologiaAttivita(ias.getTipologiaAttivita().getDescrizione());
		    }
		}
	    }
	    List<IAttivitadyn2datiSnapshot> valori = iAttivitadyn2datiSnapshotService.findByIAttivitaSnapshot(ias.getId().getCodice());
	    Map<String, String> mapValori = new HashMap<String, String>();
	    for (IAttivitadyn2datiSnapshot iads : valori) {
		String codice = IASnapshotValoriHelper.builCodiceOggettoMappa(ias.getId().getCodice().intValue(),
			iads.getDyn2Campi().getId().getCodice(), iads.getId().getIndice().intValue(),
			iads.getId().getIndiceMolteplicita().intValue());
		String valore = StringUtils.defaultString(iads.getValoredecodificato()).trim().equals("") ? iads.getValore()
			: iads.getValoredecodificato();
		mapValori.put(codice, valore);
	    }
	    sh.setMapValori(mapValori);
	    listaSnapshots.add(sh);
	}
	helper.setListaSnapshot(listaSnapshots);
	return helper;
    }

    @Override
    public List<IAttivitaSnapshot> findByIattivitaTipologie(Integer codiceIattivitaTipologia, Integer firstResult, Integer maxResult) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("tipologiaAttivitaId", codiceIattivitaTipologia, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("denominazione"));
	return iattivitasnapshotDAO.findByFilterTable(ft, firstResult, maxResult);
    }

    @Override
    public IAttivitaSnapshot findByData(IAttivita attivita, Date date) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("attivitaId", attivita.getId().getCodice(), Integer.class));
	Calendar c = Calendar.getInstance();
	c.setTime(date);
	c.set(Calendar.HOUR_OF_DAY, 0);
	c.set(Calendar.MINUTE, 0);
	c.set(Calendar.SECOND, 0);
	Date low = new Date(c.getTimeInMillis());
	c.set(Calendar.HOUR_OF_DAY, 23);
	c.set(Calendar.MINUTE, 59);
	c.set(Calendar.SECOND, 59);
	Date upper = new Date(c.getTimeInMillis());
	fr.addFilterField(FilterUtils.greaterEqual("data", low, Date.class));
	fr.addFilterField(FilterUtils.smallerEqual("data", upper, Date.class));
	//	fr.addFilterField(FilterUtils.equals("data", date, Date.class));
	ft.addRestriction(fr);
	List<IAttivitaSnapshot> list = iattivitasnapshotDAO.findByFilterTable(ft, 0, 1);
	if (!list.isEmpty()) {
	    return list.get(0);
	}
	return null;
    }

    @Override
    public boolean isSnapshotExsistByAttivita(Integer codiceattivita) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("attivitaId", codiceattivita, Integer.class));
	ft.addRestriction(fr);
	boolean risultato = iattivitasnapshotDAO.existsRecords(ft);
	return risultato;
    }

    @Override
    public void updateDatiDinamiciSnapshots(IAttivita iAttivita, Date data) {

	try {
	    boolean isLastSnashotChanged = false;
	    List<IAttivitaSnapshot> list = this.findFromData(iAttivita.getId().getCodice(), data);
	    IAttivitaSnapshot lastIAttivitaSnapshot = null;
	    for (IAttivitaSnapshot iAttivitaSnapshot : list) {
		isLastSnashotChanged = this.updateDatiDinamiciSingoloSnapshot(iAttivitaSnapshot);
		lastIAttivitaSnapshot = iAttivitaSnapshot;
	    }
	    if (isLastSnashotChanged) {
		log.debug("L'ultimo snapshot è cambiato, aggiorno lo stato attuale delle schede e/o dati dinamici");
		changeDatiDinamiciAttivita(lastIAttivitaSnapshot);
	    }
	} catch (Exception e) {
	    // NOn blocca l'esecuzione in quanto in un primo momento la generazione degli snapshot era in backgroud. 
	    log.error(
		    "Errore nell' esecuzione del metodo updateDatiDinamiciSnapshots(IAttivita iAttivita, Date data) per l'attività id: {} e data {} : {}",
		    new Object[] { iAttivita.getId().getCodice(), data, e });
	}
    }

    private void changeDatiDinamiciAttivita(IAttivitaSnapshot lastIAttivitaSnapshot) {

	Integer codiceattivita = lastIAttivitaSnapshot.getAttivita().getId().getCodice();
	List<IAttivitadyn2modellit> iAttivitadyn2modellits = iAttivitadyn2modellitService.findByAttivita(codiceattivita, null, null);
	for (IAttivitadyn2modellit iAttivitadyn2modellit : iAttivitadyn2modellits) {
	    // Set<Dyn2Modellid> dyn2Modellids = iAttivitadyn2modellit.getDyn2Modellit().getDyn2Modellids();
	    List<Dyn2Modellid> dyn2Modellids = dyn2ModellidService.findByModelloT(iAttivitadyn2modellit.getDyn2Modellit());
	    for (Dyn2Modellid dyn2Modellid : dyn2Modellids) {
		if (EntityUtils.getNestedProperty(dyn2Modellid.getDyn2Campi(), "id.codice") != null) {
		    Integer codiceCampo = dyn2Modellid.getDyn2Campi().getId().getCodice();
		    if (BooleanUtils.toBoolean(dyn2Modellid.getFlgMultiplo())) {
			log.debug("changeDatiDinamiciAttivita# Campo di tipo multiplo: {}", BooleanUtils.toBoolean(dyn2Modellid.getFlgMultiplo()));
			List<IAttivitadyn2dati> iAttivitadyn2datis = iAttivitadyn2datiService.findByAttivitaAndDyn2Campi(codiceattivita, codiceCampo,
				null);
			// se campo multiplo li cancello tutti e li inserisco in base all'ultimo snapshot
			for (IAttivitadyn2dati iAttivitadyn2dati : iAttivitadyn2datis) {
			    iAttivitadyn2datiService.delete(iAttivitadyn2dati);
			}
		    }
		    log.debug("changeDatiDinamiciAttivita# Campo di tipo multiplo: {}", BooleanUtils.toBoolean(dyn2Modellid.getFlgMultiplo()));
		    List<IAttivitadyn2datiSnapshot> attivitadyn2datiSnapshots = iAttivitadyn2datiSnapshotService
			    .findByAttivitaAndAttivitaSnapshotAndCampo(codiceattivita, lastIAttivitaSnapshot.getId().getCodice(), codiceCampo);
		    /////Recupero IAttivitadyn2datiSnapshot per il campo dinamico ciclato.
		    //for (IAttivitadyn2dati attyn2dati : attivitadyn2datis) {
		    for (IAttivitadyn2datiSnapshot attyn2datiSnapshot : attivitadyn2datiSnapshots) {
			// Rcupero IAttivitadyn2dati per il campo clicato
			IAttivitadyn2datiId idAttivitadyn2dati = new IAttivitadyn2datiId();
			idAttivitadyn2dati.setFkD2cId(codiceCampo);
			idAttivitadyn2dati.setFkIaId(codiceattivita);
			idAttivitadyn2dati.setIdcomune(ORMHelper.getIdcomune());
			idAttivitadyn2dati.setIndice(attyn2datiSnapshot.getId().getIndice());
			idAttivitadyn2dati.setIndiceMolteplicita(attyn2datiSnapshot.getId().getIndiceMolteplicita());
			IAttivitadyn2dati attivitadyn2dati = iAttivitadyn2datiService.findById(idAttivitadyn2dati);
			/////Recupero IAttivitadyn2datiSnapshot per il campo dinamico ciclato.
			IAttivitadyn2datiSnapshotId idAttivitadyn2datiSnapshot = new IAttivitadyn2datiSnapshotId();
			idAttivitadyn2datiSnapshot.setFkD2cId(codiceCampo);
			idAttivitadyn2datiSnapshot.setFkIaId(codiceattivita);
			idAttivitadyn2datiSnapshot.setFkIasId(lastIAttivitaSnapshot.getId().getCodice());
			idAttivitadyn2datiSnapshot.setIdcomune(ORMHelper.getIdcomune());
			idAttivitadyn2datiSnapshot.setIndice(attyn2datiSnapshot.getId().getIndice());
			idAttivitadyn2datiSnapshot.setIndiceMolteplicita(attyn2datiSnapshot.getId().getIndiceMolteplicita());
			IAttivitadyn2datiSnapshot iAttivitadyn2datiSnapshot = iAttivitadyn2datiSnapshotService.findById(idAttivitadyn2datiSnapshot);
			if (iAttivitadyn2datiSnapshot != null) {
			    if (attivitadyn2dati != null) {
				// Il campo è presente nello snaposhot, aggiorno il corrispettivo campo della scheda dell'attività 
				attivitadyn2dati.setValore(iAttivitadyn2datiSnapshot.getValore());
				attivitadyn2dati.setValoredecodificato(iAttivitadyn2datiSnapshot.getValoredecodificato());
				attivitadyn2dati.setAutoins(true);
				iAttivitadyn2datiService.update(attivitadyn2dati);
			    } else {
				// Il campo è presente nello snaposhot, aggiorno il corrispettivo campo della scheda dell'attività 
				attivitadyn2dati = new IAttivitadyn2dati();
				attivitadyn2dati.setId(idAttivitadyn2dati);
				attivitadyn2dati.setDyn2Campi(iAttivitadyn2datiSnapshot.getDyn2Campi());
				attivitadyn2dati.setValore(iAttivitadyn2datiSnapshot.getValore());
				attivitadyn2dati.setValoredecodificato(iAttivitadyn2datiSnapshot.getValoredecodificato());
				iAttivitadyn2datiService.insert(attivitadyn2dati);
			    }
			} else {
			    // Il campo dinamico non esiste nello snapshot, verifico se eiste su iattivitadyn2dati, in caso contrario
			    // non deve essere fatta alcuna operazione
			    if (attivitadyn2dati != null) {
				//Non esiste lo snapshot  per il campo dinamico, i casi potrebbero essere che:
				//	- il dato dinamico viene gestito dalle istanze ma in questo snapshot  non è valorizzato
				//	- il dato dinamico non viene gestito dalle istanze ma manualmente dall'attività
				//Nel primo caso devo cancellare il dato dinamico perchè verrà ricalcolato, nel secondo caso (inserimento manuale) no
				boolean isCampoInDyn2ModelliD = dyn2ModellidService.isExistDyn2CampiInDyn2ModelliDIstanzeAndAttivita(
					lastIAttivitaSnapshot.getAttivita().getId().getCodice(), lastIAttivitaSnapshot.getData(),
					dyn2Modellid.getDyn2Campi().getId().getCodice());
				//se true :il dato dinamico viene gestito dalle istanze ma in questo snapshot  non è valorizzato
				if (isCampoInDyn2ModelliD) {
				    iAttivitadyn2datiService.delete(attivitadyn2dati);
				} else //false: il dato dinamico non viene gestito dalle istanze ma manualmente dall'attività
				{
				    // Il campo dinamico è presente nella scheda dell'attivita, ma non è getsibile tramite le istanze
				    log.debug("Il campo dinamico {} è presente nella scheda dell'attivita, ma non è getsibile tramite le istanze",
					    new Object[] { dyn2Modellid.getDyn2Campi().getId().getCodice() });
				}
			    }
			}
		    }
		}
	    }
	}
    }

    @Override
    public boolean updateDatiDinamiciSingoloSnapshot(IAttivitaSnapshot iAttivitaSnapshot) {

	// Imposto la variabile di ritorno del metodo a false
	boolean isDatiAndModelliDinaminciChanged = false;
	try {
	    IAttivita iAttivita = iAttivitaSnapshot.getAttivita();
	    Set<IAttivitadyn2modTSnapshot> iAttivitadyn2modTSnapshots = null;
	    Set<IAttivitadyn2datiSnapshot> iAttivitadyn2datiSnapshots = null;
	    Set<IAttivitadyn2datiSnapshot> iAttivitadyn2datiSnapshotsTemp = new HashSet<IAttivitadyn2datiSnapshot>();
	    IAttivitaSnapshot attivitaSnapshotPrecedente = this.findSnapshotPrecedente(iAttivitaSnapshot);
	    Date dataAttivitaSnapshotPrecedente = null;
	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    // Creo la struttura delle schede dinamiche dell'attivita
	    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    // trovo tutte le schede associate all'attivita
	    List<IAttivitadyn2modellit> iAttivitadyn2modellits = iAttivitadyn2modellitService.findByAttivita(iAttivita.getId().getCodice(), null,
		    null);
	    if (!iAttivitadyn2modellits.isEmpty()) {
		iAttivitadyn2modTSnapshots = new HashSet<IAttivitadyn2modTSnapshot>();
		iAttivitadyn2datiSnapshots = new HashSet<IAttivitadyn2datiSnapshot>();
		IAttivitadyn2modTSnapshot attivitadyn2modTSnapshot = null;
		for (IAttivitadyn2modellit iAttivitadyn2modellit : iAttivitadyn2modellits) {
		    attivitadyn2modTSnapshot = new IAttivitadyn2modTSnapshot();
		    attivitadyn2modTSnapshot.setAttivita(iAttivita);
		    attivitadyn2modTSnapshot.setAttivitaSnapshot(iAttivitaSnapshot);
		    attivitadyn2modTSnapshot.setDyn2Modellit(iAttivitadyn2modellit.getDyn2Modellit());
		    IAttivitadyn2modTSnapshotId idModTSnapshot = new IAttivitadyn2modTSnapshotId();
		    idModTSnapshot.setFkD2mtId(iAttivitadyn2modellit.getDyn2Modellit().getId().getCodice());
		    idModTSnapshot.setFkIaId(iAttivita.getId().getCodice());
		    idModTSnapshot.setFkIasId(iAttivitaSnapshot.getId().getCodice());
		    idModTSnapshot.setIdcomune(ORMHelper.getIdcomune());
		    attivitadyn2modTSnapshot.setId(idModTSnapshot);
		    iAttivitadyn2modTSnapshots.add(attivitadyn2modTSnapshot);
		    // Trovo tutti i campoi associati alla scheda
		    Set<Dyn2Modellid> dyn2Modellids = iAttivitadyn2modellit.getDyn2Modellit().getDyn2Modellids();
		    for (Dyn2Modellid dyn2Modellid : dyn2Modellids) {
			if (EntityUtils.getNestedProperty(dyn2Modellid.getDyn2Campi(), "id.codice") != null) {
			    // Controlle se esiste lo snapshot precedente
			    Dyn2Campi campi = dyn2Modellid.getDyn2Campi();
			    // Campo di tipo non multiplo (segue la logica  he riporta il valore dello snapshot precedente se per l'attuale istanza
			    // il campo non è popolato o non presente)
			    if (!BooleanUtils.toBoolean(dyn2Modellid.getFlgMultiplo())) {
				//valorizzo una variabile interna per controllare se la base modifica un dato dinamico
				if (attivitaSnapshotPrecedente != null) {
				    // Verifico se nello snashot precedente è presente un valore per questo campo
				    log.debug("Campo dinamico {} [{}]", new Object[] { campi.getNomecampo(), campi.getId().getCodice() });
				    List<IAttivitadyn2datiSnapshot> iAttivitadyn2datiSnapshotsDB = iAttivitadyn2datiSnapshotService
					    .findByAttivitaAndAttivitaSnapshotAndCampo(iAttivita.getId().getCodice(),
						    attivitaSnapshotPrecedente.getId().getCodice(), campi.getId().getCodice());
				    if (!iAttivitadyn2datiSnapshotsDB.isEmpty()) {
					iAttivitadyn2datiSnapshotsTemp = new HashSet<IAttivitadyn2datiSnapshot>();
					for (IAttivitadyn2datiSnapshot iAttivitadyn2datiSnapshotDB : iAttivitadyn2datiSnapshotsDB) {
					    // popolare le proprietà leggendole dalla  lista
					    IAttivitadyn2datiSnapshot iAttivitadyn2datiSnapshot = new IAttivitadyn2datiSnapshot();
					    iAttivitadyn2datiSnapshot.setAttivita(iAttivita);
					    iAttivitadyn2datiSnapshot.setAttivitaSnapshot(iAttivitaSnapshot);
					    iAttivitadyn2datiSnapshot.setDyn2Campi(campi);
					    IAttivitadyn2datiSnapshotId idDynDatiSnapshot = new IAttivitadyn2datiSnapshotId();
					    idDynDatiSnapshot.setFkD2cId(campi.getId().getCodice());
					    idDynDatiSnapshot.setFkIaId(iAttivita.getId().getCodice());
					    idDynDatiSnapshot.setFkIasId(iAttivitaSnapshot.getId().getCodice());
					    idDynDatiSnapshot.setIdcomune(ORMHelper.getIdcomune());
					    idDynDatiSnapshot.setIndice(iAttivitadyn2datiSnapshotDB.getId().getIndice());
					    idDynDatiSnapshot.setIndiceMolteplicita(iAttivitadyn2datiSnapshotDB.getId().getIndiceMolteplicita());
					    iAttivitadyn2datiSnapshot.setValore(iAttivitadyn2datiSnapshotDB.getValore());
					    iAttivitadyn2datiSnapshot.setValoredecodificato(iAttivitadyn2datiSnapshotDB.getValoredecodificato());
					    iAttivitadyn2datiSnapshot.setAutoins(iAttivitadyn2datiSnapshotDB.getAutoins());
					    iAttivitadyn2datiSnapshot.setId(idDynDatiSnapshot);
					    iAttivitadyn2datiSnapshotsTemp.add(iAttivitadyn2datiSnapshot);
					}
					// agganciare a iAttivitadyn2datiSnapshots
				    } else {
					///
					///
					//Non esiste un il campo dinamico per lo snapshot precedente, i casi potrebbero essere che:
					//	1. il dato dinamico viene gestito dalle istanze ma nello snapshot precedente non era valorizzato
					//	2. il dato dinamico non viene gestito dalle istanze ma manualmente dall'attività
					//Nel primo caso devo cancellare il dato dinamico perchè verrà ricalcolato, nel secondo caso (inserimento manuale) no
					boolean isCampoInDyn2ModelliD = dyn2ModellidService.isExistDyn2CampiInDyn2ModelliDIstanzeAndAttivita(
						iAttivita.getId().getCodice(), iAttivitaSnapshot.getData(), campi.getId().getCodice());
					if (isCampoInDyn2ModelliD) {
					    // Utilizzo la iAttivitadyn2datiService.findByAttivitaAndDyn2Campi per capire la molteplicità del campo dinamico 
					    //Es. se il campo dinamico in esame ha una molteplicità 3 la query mi ritorna 3 valori, significa che dovrò inserire
					    // 3 record con valore e valore decofificato null,e all'oggetto IAttivitadyn2dati recupero solo i valori
					    // di indice e indicemolteplicità
					    List<Istanzedyn2dati> istanzedyn2datis = istanzedyn2datiService.findByIstanzaAndDyn2Campi(
						    iAttivitaSnapshot.getIstanza().getId().getCodice(), campi.getId().getCodice(), null);
					    for (Istanzedyn2dati istanzedyn2dati : istanzedyn2datis) {
						IAttivitadyn2datiSnapshot iAttivitadyn2datiSnapshot = new IAttivitadyn2datiSnapshot();
						iAttivitadyn2datiSnapshot.setValore(istanzedyn2dati.getValore());
						iAttivitadyn2datiSnapshot.setValoredecodificato(istanzedyn2dati.getValoredecodificato());
						iAttivitadyn2datiSnapshot.setAutoins(true);
						iAttivitadyn2datiSnapshot.setDyn2Campi(campi);
						iAttivitadyn2datiSnapshot.setAttivitaSnapshot(iAttivitaSnapshot);
						iAttivitadyn2datiSnapshot.setAttivita(iAttivita);
						IAttivitadyn2datiSnapshotId idDynDatiSnapshot = new IAttivitadyn2datiSnapshotId();
						idDynDatiSnapshot.setFkD2cId(campi.getId().getCodice());
						idDynDatiSnapshot.setFkIaId(iAttivita.getId().getCodice());
						idDynDatiSnapshot.setFkIasId(iAttivitaSnapshot.getId().getCodice());
						idDynDatiSnapshot.setIndice(istanzedyn2dati.getId().getIndice());
						idDynDatiSnapshot.setIndiceMolteplicita(istanzedyn2dati.getId().getIndiceMolteplicita());
						iAttivitadyn2datiSnapshot.setId(idDynDatiSnapshot);
						iAttivitadyn2datiSnapshotsTemp.add(iAttivitadyn2datiSnapshot);
					    }
					} else {
					    IAttivitadyn2datiSnapshot iAttivitadyn2datiSnapshot = new IAttivitadyn2datiSnapshot();
					    // Nessun modello prevede questo campo dinamico , quindi potrà essere gestito solo
					    // manualmente pertanto devo recuperare e mantenere il valore corrente
					    iAttivitadyn2datiSnapshot.setAutoins(false);
					    //Recupero iAttivitadyn2datiSnapshot attuali perchè devo mantenerli 
					    List<IAttivitadyn2datiSnapshot> iAttivitadyn2datiSnapshotsAttuale = iAttivitadyn2datiSnapshotService
						    .findByAttivitaAndAttivitaSnapshotAndCampo(iAttivita.getId().getCodice(),
							    iAttivitaSnapshot.getId().getCodice(), campi.getId().getCodice());
					    //Verifico se il campo, anche se gestito manualmente, era stato valorizzato ( potrebbe essere previsto ma non
					    //utilizzato)
					    if (!iAttivitadyn2datiSnapshotsAttuale.isEmpty()) {
						IAttivitadyn2datiSnapshotId idDynDatiSnapshot = new IAttivitadyn2datiSnapshotId();
						if (!iAttivitadyn2datiSnapshotsAttuale.isEmpty()) {
						    //Ho trovato valorizzato il dato dinamico 
						    IAttivitadyn2datiSnapshot iAttivitadyn2datiSnapshotAttuale = iAttivitadyn2datiSnapshotsAttuale
							    .get(0);
						    // popolo le proprietà con i valori attuali per mantenerli
						    idDynDatiSnapshot.setIndice(iAttivitadyn2datiSnapshotAttuale.getId().getIndice());
						    idDynDatiSnapshot
							    .setIndiceMolteplicita(iAttivitadyn2datiSnapshotAttuale.getId().getIndiceMolteplicita());
						    iAttivitadyn2datiSnapshot.setValore(iAttivitadyn2datiSnapshotAttuale.getValore());
						    iAttivitadyn2datiSnapshot
							    .setValoredecodificato(iAttivitadyn2datiSnapshotAttuale.getValoredecodificato());
						    iAttivitadyn2datiSnapshot.setAutoins(iAttivitadyn2datiSnapshotAttuale.getAutoins());
						} else {
						    //il campo era previsto nella gestione "manuale" ma non utilizzato pertanto non deve essere fatto niente
						}
						iAttivitadyn2datiSnapshot.setId(idDynDatiSnapshot);
						// Lo setto alla lista per
						iAttivitadyn2datiSnapshotsTemp.add(iAttivitadyn2datiSnapshot);
					    }
					}
				    }
				} else {
				    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				    // Ricerco tutti i campi dell' istanza legata allo snapshot per recuperare tutti gli indici, verranno utilizzati dalla 
				    // funzione checkUpdateDatidinamici per popolare i dyn2campi dello snapshot (questo il caso del primo snapshot in cui 
				    // non esiste un precendete da cui riprendere il modello)
				    List<Istanzedyn2dati> id2ds = istanzedyn2datiService
					    .findByIstanzaAndDyn2Campi(iAttivitaSnapshot.getIstanza().getId().getCodice(), campi.getId().getCodice());
				    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				    //Lo snapshot precedente non esiste:
				    //Devo controllare se il dato dimanico poteva comunque essere impostato nelle istanze dell'
				    //attività con datavalidità = alla data dello snapshot; controllando se è un dato dinamico 
				    //associato ai modelli presenti nelle istanze ( il controllo avviene nella configurazione delle schede dinamiche
				    //perché ovviamente in istanzedyn2dati non è presente in quanto non valorizzato )
				    for (Istanzedyn2dati istanzedyn2dati : id2ds) {
					boolean isCampoInDyn2ModelliD = dyn2ModellidService.isExistDyn2CampiInDyn2ModelliDIstanzeAndAttivita(
						iAttivita.getId().getCodice(), iAttivitaSnapshot.getData(), campi.getId().getCodice());
					IAttivitadyn2datiSnapshot iAttivitadyn2datiSnapshot = new IAttivitadyn2datiSnapshot();
					IAttivitadyn2datiSnapshotId idDynDatiSnapshot = new IAttivitadyn2datiSnapshotId();
					idDynDatiSnapshot = new IAttivitadyn2datiSnapshotId();
					idDynDatiSnapshot.setFkD2cId(campi.getId().getCodice());
					idDynDatiSnapshot.setFkIaId(iAttivita.getId().getCodice());
					idDynDatiSnapshot.setFkIasId(iAttivitaSnapshot.getId().getCodice());
					idDynDatiSnapshot.setIdcomune(ORMHelper.getIdcomune());
					idDynDatiSnapshot.setIndice(istanzedyn2dati.getId().getIndice());
					idDynDatiSnapshot.setIndiceMolteplicita(istanzedyn2dati.getId().getIndiceMolteplicita());
					if (isCampoInDyn2ModelliD) {
					    //Se la configurazione di almeno uno dei modelli prevede il campo in oggetto, imposto il valore a null 
					    iAttivitadyn2datiSnapshot.setValore(null);
					    iAttivitadyn2datiSnapshot.setValoredecodificato(null);
					    iAttivitadyn2datiSnapshot.setAutoins(true);
					} else {
					    // Nessun modello prevede questo campo dinamico , quindi potrà essere gestito solo
					    // manualmente
					    iAttivitadyn2datiSnapshot.setAutoins(false);
					}
					//Se invence non era previsto nella configurazione di nessun modello, mantengo il valore in quanto potrebbe 
					//essere stato inserito manualmente dall'operatore
					iAttivitadyn2datiSnapshot.setDyn2Campi(campi);
					iAttivitadyn2datiSnapshot.setAttivitaSnapshot(iAttivitaSnapshot);
					iAttivitadyn2datiSnapshot.setId(idDynDatiSnapshot);
					iAttivitadyn2datiSnapshotsTemp.add(iAttivitadyn2datiSnapshot);
				    }
				}
				// non sarà più bisogno controllare se la iAttivitadyn2datiSnapshotsTemp è piena o no, perchè
				// in qualsiasi dei casi precendeti andremo sempre a popolare la lista anche se conterrà un solo elemento
				iAttivitadyn2datiSnapshots.addAll(iAttivitadyn2datiSnapshotsTemp);
				//}
			    } else
			    // il campo è di tipo multiplo, in questo caso sugli snapshor riporto l'attuale situazione dell'istanza che sto collegando all'attività,
			    // quindi se un blocco multiplo è stato eliminato non riporto i dati del vecchio snapshot
			    {
				List<IAttivitadyn2datiSnapshot> listDyn2datidaDcancellare = iAttivitadyn2datiSnapshotService
					.findByAttivitaAndAttivitaSnapshotAndCampo(iAttivita.getId().getCodice(),
						iAttivitaSnapshot.getId().getCodice(), campi.getId().getCodice());
				for (IAttivitadyn2datiSnapshot iAttivitadyn2datiSnapshot : listDyn2datidaDcancellare) {
				    iAttivitadyn2datiSnapshotService.delete(iAttivitadyn2datiSnapshot);
				}
				List<Istanzedyn2dati> id2ds = istanzedyn2datiService
					.findByIstanzaAndDyn2Campi(iAttivitaSnapshot.getIstanza().getId().getCodice(), campi.getId().getCodice());
				for (Istanzedyn2dati istanzedyn2dati : id2ds) {
				    IAttivitadyn2datiSnapshot iAttivitadyn2datiSnapshot = new IAttivitadyn2datiSnapshot();
				    IAttivitadyn2datiSnapshotId idDynDatiSnapshot = new IAttivitadyn2datiSnapshotId();
				    idDynDatiSnapshot = new IAttivitadyn2datiSnapshotId();
				    idDynDatiSnapshot.setFkD2cId(campi.getId().getCodice());
				    idDynDatiSnapshot.setFkIaId(iAttivita.getId().getCodice());
				    idDynDatiSnapshot.setFkIasId(iAttivitaSnapshot.getId().getCodice());
				    idDynDatiSnapshot.setIdcomune(ORMHelper.getIdcomune());
				    idDynDatiSnapshot.setIndice(istanzedyn2dati.getId().getIndice());
				    idDynDatiSnapshot.setIndiceMolteplicita(istanzedyn2dati.getId().getIndiceMolteplicita());
				    iAttivitadyn2datiSnapshot.setValore(istanzedyn2dati.getValore());
				    iAttivitadyn2datiSnapshot.setValoredecodificato(istanzedyn2dati.getValoredecodificato());
				    iAttivitadyn2datiSnapshot.setAutoins(true);
				    iAttivitadyn2datiSnapshot.setDyn2Campi(campi);
				    iAttivitadyn2datiSnapshot.setAttivitaSnapshot(iAttivitaSnapshot);
				    iAttivitadyn2datiSnapshot.setId(idDynDatiSnapshot);
				    iAttivitadyn2datiSnapshotsTemp.add(iAttivitadyn2datiSnapshot);
				}
				iAttivitadyn2datiSnapshots.addAll(iAttivitadyn2datiSnapshotsTemp);
			    }
			}
		    }
		}
		// Controllo eventuali nuovi modelli da inserire
		boolean isModelliChanged = checkInsertModellidinamici(iAttivitadyn2modTSnapshots);
		// Controllo eventuali dati dinamici da aggiornare
		List<Istanze> istanzes = istanzeService.findByAttivitaAndIntervalloDataValidita(iAttivita.getId().getCodice(),
			dataAttivitaSnapshotPrecedente, iAttivitaSnapshot.getData());
		for (Istanze istanza : istanzes) {
		    // FIXME verdificare che effettivamente vengano tolti elementi dalla lista passata
		    boolean isDatiChanged = checkUpdateDatidinamici(istanza, iAttivitadyn2datiSnapshots);
		    if (!isDatiAndModelliDinaminciChanged && (isModelliChanged || isDatiChanged)) {
			isDatiAndModelliDinaminciChanged = true;
		    }
		}
		// Inserisco, se ci sono i nuovi modelli dinamici
		if (!iAttivitadyn2modTSnapshots.isEmpty()) {
		    for (IAttivitadyn2modTSnapshot iAttivitadyn2modTSnapshot : iAttivitadyn2modTSnapshots) {
			// Insert usato anche come update!!!!!!!!!!!!!!!!!!!!
			iAttivitadyn2modTSnapshotService.insert(iAttivitadyn2modTSnapshot);
			iattivitasnapshotDAO.commit();
			iattivitasnapshotDAO.flush();
		    }
		}
		// Cancello il dato dinamico con il vecchio valore
		// Inserisco il dato dinamico con il nuovo valore
		for (IAttivitadyn2datiSnapshot iAttivitadyn2datiSnapshot : iAttivitadyn2datiSnapshots) {
		    // Evito di inserire campi dinamici che hanno il campo valore vuoto, nel caso di campo vuoto
		    // andremo a cancellare il dato dinamico dalla "iAttivitadyn2datiSnapshot"
		    if (StringUtils.isNotBlank(iAttivitadyn2datiSnapshot.getValore())) {
			iAttivitadyn2datiSnapshotService.update(iAttivitadyn2datiSnapshot);
		    } else {
			iAttivitadyn2datiSnapshotService.delete(iAttivitadyn2datiSnapshot);
		    }
		    iattivitasnapshotDAO.flush();
		    iattivitasnapshotDAO.commit();
		    iattivitasnapshotDAO.flush();
		    iattivitasnapshotDAO.clear();
		}
	    }
	} catch (Exception e) {
	    // Non blocca l'esecuzione in quanto in un primo momento la generazione degli snapshot era in backgroud. 
	    log.error(
		    "Errore nell' esecuzione del metodo updateDatiDinamiciSnapshots(IAttivita iAttivita, Date data) per l'attività snapshot id: {}  : {}",
		    new Object[] { iAttivitaSnapshot.getId().getCodice(), e });
	}
	//	return isDatiAndModelliDinaminciChanged;
	return true;
    }

    /**
     * Controlla tutti gli oggetti passati dalla lista,andando ad eliminare (dalla lista e non dal db) quelli già
     * presneti su db. Se almeno un elemento non è presente su db il metodo ritorna true, altrimenti false.
     * 
     * @param iAttivitadyn2modTSnapshots
     * @return
     */
    private boolean checkInsertModellidinamici(Set<IAttivitadyn2modTSnapshot> iAttivitadyn2modTSnapshots) {

	Set<IAttivitadyn2modTSnapshot> temp = new HashSet<IAttivitadyn2modTSnapshot>();
	boolean risultato = false;
	for (IAttivitadyn2modTSnapshot iAttivitadyn2modTSnapshot : iAttivitadyn2modTSnapshots) {
	    IAttivitadyn2modTSnapshot iAttivitadyn2modTSnapshotTemp = iAttivitadyn2modTSnapshotService.findById(iAttivitadyn2modTSnapshot.getId());
	    if (iAttivitadyn2modTSnapshotTemp == null) {
		temp.add(iAttivitadyn2modTSnapshot);
		risultato = true;
	    }
	}
	iAttivitadyn2modTSnapshots = temp;
	return risultato;
    }

    /**
     * <pre>
     * Cicla lista passata e per ogni elemento verifica se è presente nei dati dinamici dell'istanza:
     *   1. si : verifica se il valore è diverso:
     *         1.1 si : aggiorna il valore e imposta la variabile di ritorno a true
     *         1.2 no : lascia il vecchio valore e non altera il valore di ritorno
     *   2. no : non altera il valore di ritorno
     * &#64;param istanza
     * &#64;param iAttivitadyn2datiSnapshots
     * &#64;return
     * </pre>
     */
    private boolean checkUpdateDatidinamici(Istanze istanza, Set<IAttivitadyn2datiSnapshot> iAttivitadyn2datiSnapshots) {

	boolean risultato = false;
	Map<Integer, Integer> mappaCampiIndice = new HashMap<Integer, Integer>();
	IAttivita iAttivita = null;
	IAttivitaSnapshot iAttivitaSnapshot = null;
	for (IAttivitadyn2datiSnapshot iAttivitadyn2datiSnapshot : iAttivitadyn2datiSnapshots) {
	    iAttivita = iAttivitadyn2datiSnapshot.getAttivita();
	    iAttivitaSnapshot = iAttivitadyn2datiSnapshot.getAttivitaSnapshot();
	    Istanzedyn2datiId id = new Istanzedyn2datiId();
	    id.setCodiceistanza(istanza.getId().getCodice());
	    id.setFkD2cId(iAttivitadyn2datiSnapshot.getDyn2Campi().getId().getCodice());
	    id.setIdcomune(ORMHelper.getIdcomune());
	    id.setIndice(iAttivitadyn2datiSnapshot.getId().getIndice());
	    id.setIndiceMolteplicita(iAttivitadyn2datiSnapshot.getId().getIndiceMolteplicita());
	    Istanzedyn2dati istanzedyn2dati = istanzedyn2datiService.findById(id);
	    if (istanzedyn2dati != null) {
		if (!istanzedyn2dati.getValore().equals(iAttivitadyn2datiSnapshot.getValore())) {
		    iAttivitadyn2datiSnapshot.setValore(istanzedyn2dati.getValore());
		    iAttivitadyn2datiSnapshot.setValoredecodificato(istanzedyn2dati.getValoredecodificato());
		    risultato = true;
		    if (mappaCampiIndice.get(iAttivitadyn2datiSnapshot.getDyn2Campi().getId().getCodice()) == null) {
			mappaCampiIndice.put(iAttivitadyn2datiSnapshot.getDyn2Campi().getId().getCodice(),
				iAttivitadyn2datiSnapshot.getId().getIndiceMolteplicita());
		    } else {
			if ((mappaCampiIndice.get(iAttivitadyn2datiSnapshot.getDyn2Campi().getId().getCodice()) < iAttivitadyn2datiSnapshot.getId()
				.getIndiceMolteplicita())) {
			    mappaCampiIndice.put(iAttivitadyn2datiSnapshot.getDyn2Campi().getId().getCodice(),
				    iAttivitadyn2datiSnapshot.getId().getIndiceMolteplicita());
			}
		    }
		}
	    }
	}
	//	for (Map.Entry<Integer, Integer> campi : mappaCampiIndice.entrySet()) {
	//	    Integer indiceMax = campi.getValue();
	//	    List<Istanzedyn2dati> istanzedyn2datis = istanzedyn2datiService.findByIstanzaAndDyn2Campi(istanza.getId().getCodice(), campi.getKey());
	//	    for (Istanzedyn2dati istanzedyn2dati : istanzedyn2datis) {
	//		IAttivitadyn2datiSnapshot attivitadyn2datiSnapshot = null;
	//		if (istanzedyn2dati.getId().getIndiceMolteplicita() > indiceMax) {
	//		    attivitadyn2datiSnapshot = new IAttivitadyn2datiSnapshot();
	//		    //
	//		    IAttivitadyn2datiSnapshotId id = new IAttivitadyn2datiSnapshotId();
	//		    id.setFkD2cId(istanzedyn2dati.getDyn2Campi().getId().getCodice());
	//		    id.setFkIaId(iAttivita.getId().getCodice());
	//		    id.setFkIasId(iAttivitaSnapshot.getId().getCodice());
	//		    id.setIdcomune(ORMHelper.getIdcomune());
	//		    id.setIndice(istanzedyn2dati.getId().getIndice());
	//		    id.setIndiceMolteplicita(istanzedyn2dati.getId().getIndiceMolteplicita());
	//		    attivitadyn2datiSnapshot.setId(id);
	//		    attivitadyn2datiSnapshot.setValore(istanzedyn2dati.getValore());
	//		    attivitadyn2datiSnapshot.setValoredecodificato(istanzedyn2dati.getValoredecodificato());
	//		    attivitadyn2datiSnapshot.setAttivita(iAttivita);
	//		    attivitadyn2datiSnapshot.setAttivitaSnapshot(iAttivitaSnapshot);
	//		    attivitadyn2datiSnapshot.setAutoins(true);
	//		    attivitadyn2datiSnapshot.setDyn2Campi(istanzedyn2dati.getDyn2Campi());
	//		    iAttivitadyn2datiSnapshots.add(attivitadyn2datiSnapshot);
	//		}
	//	    }
	//	}
	return risultato;
    }

    //    private Set<IAttivitadyn2modTSnapshot> createListModelliSnapshotFromModelliAttivita(List<IAttivitadyn2modellit> iAttivitadyn2modellits,
    //	    IAttivitaSnapshot iAttivitaSnapshot) {
    //
    //	Set<IAttivitadyn2modTSnapshot> iAttivitadyn2modTSnapshots = new HashSet<IAttivitadyn2modTSnapshot>();
    //	IAttivitadyn2modTSnapshot iAttivitadyn2modTSnapshot = null;
    //	if (iAttivitadyn2modellits != null) {
    //	    for (IAttivitadyn2modellit iAttivitadyn2modellit : iAttivitadyn2modellits) {
    //		iAttivitadyn2modTSnapshot = new IAttivitadyn2modTSnapshot();
    //		// Setto l'attivita
    //		IAttivita iAttivita = iAttivitaService.findById(new PkId(iAttivitadyn2modellit.getId().getFkIaId()));
    //		iAttivitadyn2modTSnapshot.setAttivita(iAttivita);
    //		// Setto lo snapshot
    //		iAttivitadyn2modTSnapshot.setAttivitaSnapshot(iAttivitaSnapshot);
    //		// setto il modello dinamico
    //		iAttivitadyn2modTSnapshot.setDyn2Modellit(iAttivitadyn2modellit.getDyn2Modellit());
    //		// setto l'id
    //		IAttivitadyn2modTSnapshotId id = new IAttivitadyn2modTSnapshotId();
    //		id.setFkD2mtId(iAttivitadyn2modellit.getDyn2Modellit().getId().getCodice());
    //		id.setFkIaId(iAttivita.getId().getCodice());
    //		id.setFkIasId(iAttivitaSnapshot.getId().getCodice());
    //		id.setIdcomune(ORMHelper.getIdcomune());
    //		iAttivitadyn2modTSnapshot.setId(id);
    //		iAttivitadyn2modTSnapshots.add(iAttivitadyn2modTSnapshot);
    //	    }
    //	    return iAttivitadyn2modTSnapshots;
    //	}
    //	return null;
    //    }
    //    private Set<IAttivitadyn2datiSnapshot> createListDatiSnapshotFromDatiAttivita(List<IAttivitadyn2dati> iAttivitadyn2datis,
    //	    IAttivitaSnapshot iAttivitaSnapshot) {
    //
    //	Set<IAttivitadyn2datiSnapshot> iAttivitadyn2datiSnapshots = new HashSet<IAttivitadyn2datiSnapshot>();
    //	IAttivitadyn2datiSnapshot attivitadyn2datiSnapshot = null;
    //	if (iAttivitadyn2datis != null) {
    //	    for (IAttivitadyn2dati iAttivitadyn2dati : iAttivitadyn2datis) {
    //		attivitadyn2datiSnapshot = new IAttivitadyn2datiSnapshot();
    //		//setto  attività
    //		IAttivita iAttivita = iAttivitaService.findById(new PkId(iAttivitadyn2dati.getId().getFkIaId()));
    //		attivitadyn2datiSnapshot.setAttivita(iAttivita);
    //		//setto attivita snapshot
    //		attivitadyn2datiSnapshot.setAttivitaSnapshot(iAttivitaSnapshot);
    //		// setto Dyn2Campi
    //		attivitadyn2datiSnapshot.setDyn2Campi(iAttivitadyn2dati.getDyn2Campi());
    //		// setto valore e valore decodificato a null
    //		attivitadyn2datiSnapshot.setValore(null);
    //		attivitadyn2datiSnapshot.setValoredecodificato(null);
    //		//
    //		IAttivitadyn2datiSnapshotId id = new IAttivitadyn2datiSnapshotId();
    //		id.setFkD2cId(iAttivitadyn2dati.getDyn2Campi().getId().getCodice());
    //		id.setFkIaId(iAttivita.getId().getCodice());
    //		id.setFkIasId(iAttivitaSnapshot.getId().getCodice());
    //		id.setIdcomune(ORMHelper.getIdcomune());
    //		id.setIndice(iAttivitadyn2dati.getId().getIndice());
    //		id.setIndiceMolteplicita(iAttivitadyn2dati.getId().getIndiceMolteplicita());
    //		attivitadyn2datiSnapshot.setId(id);
    //		iAttivitadyn2datiSnapshots.add(attivitadyn2datiSnapshot);
    //	    }
    //	    return iAttivitadyn2datiSnapshots;
    //	}
    //	return null;
    //    }
    @Override
    public IAttivitaSnapshot findSnapshotPrecedente(IAttivitaSnapshot iAttivitaSnapshot) {

	return iattivitasnapshotDAO.findSnapshotPrecedente(iAttivitaSnapshot);
    }

    @Override
    public List<IAttivitaSnapshot> findFromData(Integer codiceAttivita, Date dataValidita) {

	return iattivitasnapshotDAO.findFromData(codiceAttivita, dataValidita);
    }

    @Override
    public void updateRicalcolaSnapshot(IAttivita iAttivita, Date dataPartenza) {

	// Il metodo non controlla gli snapshot successivi in quanto i dati non possono cambiare
	this.updateDatiTestaSingoloSnapshot(iAttivita, dataPartenza);
	// Il metodo verifica ed eventualmente cambia i dati dinamici degli snapshot a partire dalla data passata
	this.updateDatiDinamiciSnapshots(iAttivita, dataPartenza);
    }

    @Override
    public boolean updateDatiTestaSingoloSnapshot(IAttivita iAttivita, Date data) {

	IAttivitaSnapshot iAttivitaSnapshot = this.findByData(iAttivita, data);
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("attivitaId", iAttivita.getId().getCodice(), Integer.class));
	//fr.addFilterField(FilterUtils.equals("datavalidita", data, Date.class));
	Calendar c = Calendar.getInstance();
	c.setTime(data);
	c.set(Calendar.HOUR_OF_DAY, 0);
	c.set(Calendar.MINUTE, 0);
	c.set(Calendar.SECOND, 0);
	Date low = new Date(c.getTimeInMillis());
	c.set(Calendar.HOUR_OF_DAY, 23);
	c.set(Calendar.MINUTE, 59);
	c.set(Calendar.SECOND, 59);
	Date upper = new Date(c.getTimeInMillis());
	fr.addFilterField(FilterUtils.greaterEqual("datavalidita", low, Date.class));
	fr.addFilterField(FilterUtils.smallerEqual("datavalidita", upper, Date.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("attivitaOrdine", FunctionsEnum.NVL_FUNCTION, "0"));
	ft.addOrder(FilterUtils.orderDesc("id.codice"));
	List<Istanze> list = istanzeService.findByFilterTable(ft, 0, 1);
	if (!list.isEmpty()) {
	    // Controllo se l'istanza rappresentativa dello snapshot è cambiata
	    Istanze istanza = list.get(0);
	    if (!istanza.getId().getCodice().equals(iAttivitaSnapshot.getIstanza().getId().getCodice())) {
		iAttivitaSnapshot.setIstanza(istanza);
		this.update(iAttivitaSnapshot);
		iattivitasnapshotDAO.commit();
		iattivitasnapshotDAO.flush();
		return true;
	    }
	}
	return false;
    }

    @Override
    public List<WsExportBean> findIAttivitaSnapshotByFilter(IAttivitaFilter filter, Date dataEsportazione) {

	return iattivitasnapshotDAO.findIAttivitaSnapshotByFilter(filter, dataEsportazione);
    }

    @Override
    public boolean existAfterData(Integer codiceAttivita, Date datavalidita) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("attivitaId", codiceAttivita, Integer.class));
	fr.addFilterField(FilterUtils.greater("data", datavalidita, Date.class));
	ft.addRestriction(fr);
	return iattivitasnapshotDAO.existsRecords(ft);
    }

    @Override
    public List<Integer> findTuttiICodici(Integer codiceAttivitaDaCuiPartire) {

	return iattivitasnapshotDAO.findTuttiICodici(codiceAttivitaDaCuiPartire);
    }

    @Override
    public Map<Date, Integer> findSnapshots(Integer idAttivita, Date dataRicalcolo) {

	return this.iattivitasnapshotDAO.findSnapshots(idAttivita, dataRicalcolo);
    }
}
