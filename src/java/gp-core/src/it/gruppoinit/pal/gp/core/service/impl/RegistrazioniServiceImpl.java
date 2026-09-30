package it.gruppoinit.pal.gp.core.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.RegistrazioniDAO;
import it.gruppoinit.pal.gp.core.dao.RegistrazioniDAO.RegistrazioniMercatoEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.OrderBySqlFormula.FunctionsEnum;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Conti;
import it.gruppoinit.pal.gp.core.domain.ContoInteressiLegali;
import it.gruppoinit.pal.gp.core.domain.ImportiRateizzati;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiCfgAttivita;
import it.gruppoinit.pal.gp.core.domain.MercatiConfigurazione;
import it.gruppoinit.pal.gp.core.domain.MercatiConfigurazioneId;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeD;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeT;
import it.gruppoinit.pal.gp.core.domain.Oneritipirateizzazione;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Posteggio;
import it.gruppoinit.pal.gp.core.domain.RangeRateizzazioni;
import it.gruppoinit.pal.gp.core.domain.RegIoAssegnazioni;
import it.gruppoinit.pal.gp.core.domain.Registrazioni;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniCausali;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniDaMercato;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniFilter;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniImporti;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniInOut;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniStatisticheMercati;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Tipimodalitapagamento;
import it.gruppoinit.pal.gp.core.domain.VwConcessioniattive;
import it.gruppoinit.pal.gp.core.domain.helper.ContiBean;
import it.gruppoinit.pal.gp.core.domain.helper.MercatiFormuleCalcoloContestoEnum;
import it.gruppoinit.pal.gp.core.domain.helper.MercatipresenzeDDTO;
import it.gruppoinit.pal.gp.core.domain.helper.PosteggioImportoHelper;
import it.gruppoinit.pal.gp.core.domain.helper.RateizzazioniHelper;
import it.gruppoinit.pal.gp.core.domain.helper.RigaImporto;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calcolo.ICalcoloCostoPosteggiService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.MercatipresenzeDService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.configurazione.MercatiDService;
import it.gruppoinit.pal.gp.core.features.oneri.ContiService;
import it.gruppoinit.pal.gp.core.features.oneri.rateizzazione.configurazione.TipologiaRateizzazioneEnum;
import it.gruppoinit.pal.gp.core.features.oneri.rateizzazione.configurazione.tipiscadenze.DataScadenzaResolver;
import it.gruppoinit.pal.gp.core.features.oneri.rateizzazione.configurazione.tipiscadenze.TipoScadenzaEnum;
import it.gruppoinit.pal.gp.core.filters.FieldOperationsEnum;
import it.gruppoinit.pal.gp.core.filters.FilterField;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.InteressiLegaliService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.MercatiCfgAttivitaService;
import it.gruppoinit.pal.gp.core.service.MercatiConfigurazioneService;
import it.gruppoinit.pal.gp.core.service.MercatiUsoService;
import it.gruppoinit.pal.gp.core.service.RangeRateizzazioniService;
import it.gruppoinit.pal.gp.core.service.RegIoAssegnazioniService;
import it.gruppoinit.pal.gp.core.service.RegistrazioniCausaliService;
import it.gruppoinit.pal.gp.core.service.RegistrazioniImportiService;
import it.gruppoinit.pal.gp.core.service.RegistrazioniInOutService;
import it.gruppoinit.pal.gp.core.service.RegistrazioniService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.TipimodalitapagamentoService;
import it.gruppoinit.pal.gp.core.service.TracciatiService;
import it.gruppoinit.pal.gp.core.service.VwConcessioniattiveService;
import it.gruppoinit.pal.gp.core.service.helper.PeriodicitaEnum;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Service
public class RegistrazioniServiceImpl extends BaseServiceImpl<Registrazioni, PkId> implements RegistrazioniService {

    private static final Logger log = LoggerFactory.getLogger(RegistrazioniServiceImpl.class);
    private RegistrazioniDAO registrazioniDAO;
    private RegistrazioniCausaliService registrazioniCausaliService;
    private RegistrazioniImportiService registrazioniImportiService;
    private MercatiConfigurazioneService mercatiConfigurazioneService;
    private RegIoAssegnazioniService regIoAssegnazioniService;
    private RegistrazioniInOutService registrazioniInOutService;
    private TipimodalitapagamentoService tipimodalitapagamentoService;
    private MercatiDService mercatiDService;
    private MercatiCfgAttivitaService mercatiCfgAttivitaService;
    private VwConcessioniattiveService vwConcessioniattiveService;
    private AnagrafeService anagrafeService;
    private SoftwareService softwareService;
    private MercatiUsoService mercatiUsoService;
    private ResponsabiliService responsabiliService;
    private IstanzeService istanzeService;
    private InventarioprocedimentiService inventarioprocedimentiService;
    private RangeRateizzazioniService rangeRateizzazioniService;
    private InteressiLegaliService interessiLegaliService;
    private MercatipresenzeDService mercatipresenzeDService;
    private TracciatiService tracciatiService;
    private ContiService contiService;
    @Autowired
    private ICalcoloCostoPosteggiService calcoloCostoPosteggiService;

    @Autowired
    public void setContiService(ContiService contiService) {

	this.contiService = contiService;
    }

    @Autowired
    public void setTracciatiService(TracciatiService tracciatiService) {

	this.tracciatiService = tracciatiService;
    }

    @Autowired
    public void setMercatipresenzeDService(MercatipresenzeDService mercatipresenzeDService) {

	this.mercatipresenzeDService = mercatipresenzeDService;
    }

    @Autowired
    public void setInteressiLegaliService(InteressiLegaliService interessiLegaliService) {

	this.interessiLegaliService = interessiLegaliService;
    }

    @Autowired
    public void setRangeRateizzazioniService(RangeRateizzazioniService rangeRateizzazioniService) {

	this.rangeRateizzazioniService = rangeRateizzazioniService;
    }

    @Autowired
    public void setRegistrazioniDAO(RegistrazioniDAO registrazioniDAO) {

	this.registrazioniDAO = registrazioniDAO;
    }

    @Autowired
    public void setRegistrazioniCausaliService(RegistrazioniCausaliService registrazioniCausaliService) {

	this.registrazioniCausaliService = registrazioniCausaliService;
    }

    @Autowired
    public void setRegistrazioniImportiService(RegistrazioniImportiService registrazioniImportiService) {

	this.registrazioniImportiService = registrazioniImportiService;
    }

    @Autowired
    public void setMercatiConfigurazioneService(MercatiConfigurazioneService mercatiConfigurazioneService) {

	this.mercatiConfigurazioneService = mercatiConfigurazioneService;
    }

    @Autowired
    public void setRegIoAssegnazioniService(RegIoAssegnazioniService regIoAssegnazioniService) {

	this.regIoAssegnazioniService = regIoAssegnazioniService;
    }

    @Autowired
    public void setRegistrazioniInOutService(RegistrazioniInOutService registrazioniInOutService) {

	this.registrazioniInOutService = registrazioniInOutService;
    }

    @Autowired
    public void setTipimodalitapagamentoService(TipimodalitapagamentoService tipimodalitapagamentoService) {

	this.tipimodalitapagamentoService = tipimodalitapagamentoService;
    }

    @Autowired
    public void setMercatiDService(MercatiDService mercatiDService) {

	this.mercatiDService = mercatiDService;
    }

    @Autowired
    public void setMercatiCfgAttivitaService(MercatiCfgAttivitaService mercatiCfgAttivitaService) {

	this.mercatiCfgAttivitaService = mercatiCfgAttivitaService;
    }

    @Autowired
    public void setVwConcessioniattiveService(VwConcessioniattiveService vwConcessioniattiveService) {

	this.vwConcessioniattiveService = vwConcessioniattiveService;
    }

    @Autowired
    public void setSoftwareService(SoftwareService softwareService) {

	this.softwareService = softwareService;
    }

    @Autowired
    public void setMercatiUsoService(MercatiUsoService mercatiUsoService) {

	this.mercatiUsoService = mercatiUsoService;
    }

    @Autowired
    public void setResponsabiliService(ResponsabiliService responsabiliService) {

	this.responsabiliService = responsabiliService;
    }

    @Autowired
    public void setAnagrafeService(AnagrafeService anagrafeService) {

	this.anagrafeService = anagrafeService;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setInventarioprocedimentiService(InventarioprocedimentiService inventarioprocedimentiService) {

	this.inventarioprocedimentiService = inventarioprocedimentiService;
    }

    @Override
    protected Class<Registrazioni> getEntityClass() {

	return Registrazioni.class;
    }

    @Override
    public boolean isDeleteAllowed(Registrazioni entity) {

	boolean delete = true;
	// §§§BEGIN§§§
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	List<RegistrazioniImporti> registrazioniImportiSet = registrazioniImportiService.findByRegistrazione(entity);
	Set<RegIoAssegnazioni> regIoAssegnazioniSet = null;
	for (RegistrazioniImporti registrazioniImporti : registrazioniImportiSet) {
	    regIoAssegnazioniSet = registrazioniImporti.getRegIoAssegnazionis();
	    if (!regIoAssegnazioniSet.isEmpty()) {
		_ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "REG_IO_ASSEGNAZIONI", null));
		delete = false;
		break;
	    }
	}
	if (entity.getPresenzeMercatoConcessionari() != null && !entity.getPresenzeMercatoConcessionari().isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "MERCATIPRESENZE_D", null));
	    delete = false;
	}
	if (entity.getPresenzeMercatoOccupanti() != null && !entity.getPresenzeMercatoOccupanti().isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "MERCATIPRESENZE_D", null));
	    delete = false;
	}
	if (!delete) {
	    this.throwValidationMessages(_ivs);
	}
	// §§§END§§§
	return delete;
    }

    @Override
    public void delete(Registrazioni entity) {

	// §§§BEGIN§§§
	if (isDeleteAllowed(entity)) {
	    List<RegistrazioniImporti> registrazioniImportiSet = registrazioniImportiService.findByRegistrazione(entity);
	    for (RegistrazioniImporti registrazioniImporti : registrazioniImportiSet) {
		registrazioniImportiService.delete(registrazioniImporti);
	    }
	    Registrazioni registrazioni = this.findById(entity.getId());
	    registrazioniDAO.delete(registrazioni);
	}
	// §§§END§§§
    }

    public void deleteFromDeleteCalendario(Registrazioni entity) {

	// §§§BEGIN§§§
	this.delete(entity);
	// §§§END§§§
    }

    @Override
    public List<Registrazioni> findAll(Integer firstResult, Integer maxResult) {

	// §§§BEGIN§§§
	return registrazioniDAO.findAll(firstResult, maxResult);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return new java.util.ArrayList();@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public Registrazioni findById(PkId id) {

	// §§§BEGIN§§§
	return registrazioniDAO.findById(id);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void insert(Registrazioni entity) {

	// §§§BEGIN§§§
	if (entity.getDataRegistrazione() != null) {
	    String progressivo = getProgressivoSuccessivo(entity.getDataRegistrazione());
	    entity.setProgressivo(progressivo);
	}
	if (validateEntity(entity)) {
	    if (validateInsertOrUpdate(entity)) {
		// se non inserisco una descrizione allora prendo quella della causale
		if (entity.getDescrizione() == null || entity.getDescrizione().equals("")) {
		    String descrizioneDefault = getDescrizioneDefault(entity);
		    entity.setDescrizione(StringUtils.abbreviate(descrizioneDefault, 255));
		}
		assegnaAnnoDaDataRegistrazione(entity);
		registrazioniDAO.insert(entity);
	    }
	}
	// §§§END§§§
    }

    @Override
    public void update(Registrazioni entity) {

	// §§§BEGIN§§§
	if (validateEntity(entity)) {
	    if (validateInsertOrUpdate(entity)) {
		// se non inserisco una descrizione allora prendo quella della causale
		if (entity.getDescrizione() == null || entity.getDescrizione().equals("")) {
		    String descrizioneDefault = getDescrizioneDefault(entity);
		    entity.setDescrizione(StringUtils.abbreviate(descrizioneDefault, 255));
		}
		assegnaAnnoDaDataRegistrazione(entity);
		registrazioniDAO.update(entity);
		updateImportoRegistrazione(entity);
	    }
	}
	// §§§END§§§
    }

    /**
     * metodo per calcolare il codice progressivo di una registrazioni
     * 
     * @param data
     *            : data di registrazioni
     * @return il codice progressivo successivo valido
     */
    private String getProgressivoSuccessivo(Date data) {

	// §§§BEGIN§§§
	if (data == null) {
	    data = new Date();
	}
	Calendar cal = GregorianCalendar.getInstance();
	cal.setTime(data);
	int year = cal.get(Calendar.YEAR);
	return this.findProgressivo(year);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    private String findProgressivo(int year) {

	// §§§BEGIN§§§
	StringBuffer progressivo = new StringBuffer("");
	Integer prog = registrazioniDAO.findMaxProgressivo(String.valueOf(year));
	if (prog != 0) {
	    prog += 1;
	    String valueProg = prog.toString();
	    int valProgLength = valueProg.length();
	    for (int j = 0; j < (7 - valProgLength); j++) {
		progressivo.append("0");
	    }
	    progressivo.append(valueProg).append("/").append(year);
	} else {
	    progressivo.append("0000001/").append(year);
	}
	return progressivo.toString();
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public List<Registrazioni> findByAnagrafe(Anagrafe anagrafe) {

	// §§§BEGIN§§§
	return registrazioniDAO.findByAnagrafe(anagrafe);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return new java.util.ArrayList();@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public List<RegistrazioniFilter> searchRegistrazioni(RegistrazioniFilter registrazioniFilter) {

	// §§§BEGIN§§§
	return registrazioniDAO.searchRegistrazioni(registrazioniFilter);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return new java.util.ArrayList();@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public List<RegistrazioniStatisticheMercati> findRegByMercato(Integer idMercato) {

	// §§§BEGIN§§§
	return registrazioniDAO.findRegByMercato(idMercato);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return new java.util.ArrayList();@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public List<RegistrazioniDaMercato> findRegByMercatoForCausale(short anno, Integer idMercato, MercatiUso uso) {

	// §§§BEGIN§§§
	return registrazioniDAO.findRegByMercatoForCausale(anno, idMercato, uso);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return new java.util.ArrayList();@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public List<Registrazioni> sistemaContabilitaGiornoMercato(MercatipresenzeT mercatipresenzeT, List<MercatipresenzeDDTO> spuntistiConPosteggio,
	    Responsabili operatore) {

	// §§§BEGIN§§§
	Boolean registrazioniFatte = mercatipresenzeT.getFlagRegfatte();
	List<Registrazioni> answer = new ArrayList<Registrazioni>();
	if (null == registrazioniFatte) {
	    registrazioniFatte = false;
	}
	if (registrazioniFatte.booleanValue() == true) {
	    return answer;
	}
	MercatiConfigurazioneId id = new MercatiConfigurazioneId(mercatipresenzeT.getSoftware().getCodice());
	MercatiConfigurazione mcfg = mercatiConfigurazioneService.findById(id);
	Integer codiceMercato = mercatipresenzeT.getMercato().getId().getCodice();
	List<MercatiCfgAttivita> listMcfgAttivita = mercatiCfgAttivitaService.findAll(null, null);
	// BEGIN registrazioni aumento accertamento spuntisti
	for (MercatipresenzeDDTO mercatipresenzeD : spuntistiConPosteggio) {
	    Registrazioni registrazione = new Registrazioni();
	    registrazione.setRegistrazioniCausali(mcfg.getCausaleAumento());
	    registrazione.setAnno(mercatipresenzeT.getAnno().shortValue());
	    // String progressivo = findProgressivo(mercatipresenzeT.getAnno());
	    //
	    // if (log.isDebugEnabled()) {
	    // log.debug("sistemaContabilitaGiornoMercato(): progressivo=" + progressivo);
	    // }
	    // registrazione.setProgressivo(progressivo);
	    registrazione.setMercatiUso(mercatipresenzeT.getMercatoUso());
	    registrazione.setDataRegistrazione(mercatipresenzeT.getDataRegistrazione());
	    registrazione.setDataSistema(new Date());
	    registrazione.setResponsabili(operatore);
	    // TODO lo vogliamo far inputare all'utente?
	    registrazione.setResponsabiliSistema(operatore);
	    MercatipresenzeD presenza = mercatipresenzeDService.findById(new PkId(mercatipresenzeD.getId().getCodice()));
	    registrazione.setMercatiD(presenza.getPosteggio());
	    registrazione.setAnagrafe(presenza.getOccupante());
	    registrazione.setSoftware(mercatipresenzeT.getSoftware());
	    // inserire la registrazioni
	    // e calcolare le righe di importo
	    insert(registrazione);
	    VwConcessioniattive concessioneAttiva = vwConcessioniattiveService.findByMercatoUsoPosteggio(codiceMercato,
		    mercatipresenzeT.getMercatoUso().getId().getCodice(), mercatipresenzeD.getPosteggio().getId().getCodice());
	    PosteggioImportoHelper costoPosteggioSpuntista = calcoloCostoPosteggiService.calcolaCostoPosteggio(presenza, presenza.getPosteggio(),
		    listMcfgAttivita, mercatipresenzeT.getAnno().intValue(), 1, concessioneAttiva, WebConstants.MERCATO_CONTESTO_SPUNTISTI,
		    null/*
			FIXME DALLA PRESENZA ricavare il codice attivita se presente*/, mercatipresenzeT.getMercatoUso().getId().getCodice(),
		    mercatipresenzeT.getDataRegistrazione(), MercatiFormuleCalcoloContestoEnum.PRESENZA);
	    List<RigaImporto> righePosteggio = costoPosteggioSpuntista.getListaImporti();
	    Set<RegistrazioniImporti> importiRegistrazione = registrazione.getRegistrazioniImportis();
	    for (RigaImporto rigaImportoPosteggio : righePosteggio) {
		RegistrazioniImporti canone = new RegistrazioniImporti();
		canone.setRegistrazioni(registrazione);
		canone.setConti(getConti(rigaImportoPosteggio.getConto()));
		Integer iva = rigaImportoPosteggio.getConto().getIva();
		if (iva == null) {
		    // iva = WebConstants.CONST_IVA;
		    throw new RuntimeException("Errore nella configurazione dei conti della contabilita'. Non e' stata definita l'iva per il conto " +
			    rigaImportoPosteggio.getConto().getDescrizione() +
			    "(" +
			    rigaImportoPosteggio.getConto().getId() +
			    ")");
		}
		canone.setIva(iva);
		// .. IMPORTANTE SETTO LA SCALA DEL DECIMALE ALTRIMENTI DA
		// ERRORE
		// .. IL VALIDATORE DELL'OGGETTO DI DOMINIO
		BigDecimal importoIndividualePosteggio = rigaImportoPosteggio.getImporto().setScale(2, BigDecimal.ROUND_HALF_UP);
		canone.setImporto(importoIndividualePosteggio);
		canone.setScadenza(new Date());
		canone.setNrRata(1);
		// registrazioniImportiService.insertRegImpConto(canone, mercatipresenzeT.getAnno().intValue());
		registrazioniImportiService.insert(canone);
		importiRegistrazione.add(canone);
	    }
	    // PkId presenzaId = new PkId();
	    // presenzaId.setCodice(mercatipresenzeD.getId().getCodice());
	    // MercatipresenzeD presenza = mercatipresenzeDService.findById(presenzaId);
	    // presenza.setRegistrazioneOccupante(registrazione);
	    // mercatipresenzeDService.update(presenza);
	    // INSERISCO L'INCASSO SOLO SE E' STATA SCELTA LA MODALITA'
	    // PAGAMENTO
	    if (presenza.getIncasso().getTipimodalitapagamento() != null) {
		if (presenza.getIncasso().getTipimodalitapagamento().getId() != null) {
		    if (presenza.getIncasso().getTipimodalitapagamento().getId().getCodice() != null) {
			BigDecimal importoIndividualePosteggio = costoPosteggioSpuntista.getImporto();
			RegistrazioniInOut incasso = new RegistrazioniInOut();
			PkId idTmp = new PkId();
			idTmp.setCodice(presenza.getIncasso().getTipimodalitapagamento().getId().getCodice());
			Tipimodalitapagamento tmp = tipimodalitapagamentoService.findById(idTmp);
			incasso.setTipimodalitapagamento(tmp);
			incasso.setRiferimentiPagamento(presenza.getIncasso().getRiferimentiPagamento());
			incasso.setAnagrafe(presenza.getOccupante());
			incasso.setDataIncasso(mercatipresenzeT.getDataRegistrazione());
			importoIndividualePosteggio = importoIndividualePosteggio.setScale(2, BigDecimal.ROUND_HALF_UP);
			incasso.setImporto(importoIndividualePosteggio);
			incasso.setNote(registrazione.getDescrizione());
			incasso.setTipo(WebConstants.REGISTRAZIONIINOUT_TIPO_E);
			incasso.setSoftware(mercatipresenzeT.getSoftware());
			registrazioniInOutService.insert(incasso);
			for (RegistrazioniImporti registrazioniImporti : importiRegistrazione) {
			    RegIoAssegnazioni assegnazione = new RegIoAssegnazioni();
			    assegnazione.setRegistrazioniInOut(incasso);
			    assegnazione.setRegistrazioniImporti(registrazioniImporti);
			    BigDecimal importoConto = registrazioniImporti.getImporto().setScale(2, BigDecimal.ROUND_HALF_UP);
			    assegnazione.setImporto(importoConto);
			}
			regIoAssegnazioniService.assegna(incasso, importiRegistrazione, importoIndividualePosteggio);
		    }
		}
	    }
	    answer.add(registrazione);
	}
	// END registrazioni aumento accertamento spuntisti
	return answer;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return new java.util.ArrayList();@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public List<Registrazioni> findRegistrazioniSpuntistiByMercatoUsoDataregistrazione(Mercati mercato, MercatiUso mercatoUso,
	    Date dataRegistrazione) {

	// §§§BEGIN§§§
	return registrazioniDAO.findByMercatoUsoData(mercato, mercatoUso, dataRegistrazione, RegistrazioniMercatoEnum.SPUNTISTI);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return new java.util.ArrayList();@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public List<Registrazioni> findRegistrazioniConcessionariByMercatoUsoDataregistrazione(Mercati mercato, MercatiUso mercatoUso,
	    Date dataRegistrazione) {

	// §§§BEGIN§§§
	return registrazioniDAO.findByMercatoUsoData(mercato, mercatoUso, dataRegistrazione, RegistrazioniMercatoEnum.CONCESSIONARI);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return new java.util.ArrayList();@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public List<Registrazioni> findByRegistrazioniFilter(RegistrazioniFilter registrazioniFilter, Integer firstResult, Integer maxResult) {

	// §§§BEGIN§§§
	FilterTable filterTable = getFilterTableForCriteria(registrazioniFilter, false);
	return registrazioniDAO.findByFilterTable(filterTable, firstResult, maxResult);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return new java.util.ArrayList();@@@ENDALTERNATIVEEXIT@@@
    }

    private FilterTable getFilterTableForCriteria(RegistrazioniFilter registrazioniFilter, boolean isCount) {

	// §§§BEGIN§§§
	FilterTable criteria = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	// Filtra per progressivo ( settato il match ANYWHERE)
	FilterRestriction fr = new FilterRestriction();
	if (registrazioniFilter.getProgressivo() != null && !registrazioniFilter.getProgressivo().equals("")
		&& !registrazioniFilter.getProgressivo().equals("%")) {
	    fr.addFilterField(FilterUtils.like("progressivo", registrazioniFilter.getProgressivo()));
	    // criteria.add(Restrictions.like("progressivo", registrazioniFilter.getProgressivo(), MatchMode.ANYWHERE));
	}
	// Filtra per descrizione ( settato il match ANYWHERE)
	if (registrazioniFilter.getDescrizione() != null && !registrazioniFilter.getDescrizione().equals("")
		&& !registrazioniFilter.getDescrizione().equals("%")) {
	    fr.addFilterField(FilterUtils.like("progressivo", registrazioniFilter.getDescrizione()));
	    // criteria.add(Restrictions.like("descrizione", registrazioniFilter.getDescrizione(), MatchMode.ANYWHERE));
	}
	// Filtra per data
	if (registrazioniFilter.getDataInizio() != null || registrazioniFilter.getDataFine() != null) {
	    // Filtra per da data di inizio in poi
	    if ((registrazioniFilter.getDataInizio() != null) && (registrazioniFilter.getDataFine() == null)) {
		fr.addFilterField(FilterUtils.greaterEqual("dataRegistrazione", registrazioniFilter.getDataInizio(), Date.class));
		// criteria.add(Restrictions.ge("dataRegistrazione", registrazioniFilter.getDataInizio()));
	    }
	    // Filtra data minore uguale di quella inserita
	    if ((registrazioniFilter.getDataInizio() == null) && (registrazioniFilter.getDataFine() != null)) {
		fr.addFilterField(FilterUtils.smallerEqual("dataRegistrazione", registrazioniFilter.getDataInizio(), Date.class));
		// criteria.add(Restrictions.le("dataRegistrazione", registrazioniFilter.getDataFine()));
	    }
	    // Filtra per data inizio e fine inserite
	    if ((registrazioniFilter.getDataInizio() != null) && (registrazioniFilter.getDataFine() != null)) {
		fr.addFilterField(
			FilterUtils.between("dataRegistrazione", registrazioniFilter.getDataInizio(), registrazioniFilter.getDataFine(), Date.class));
		//criteria.add(Restrictions.between("dataRegistrazione", registrazioniFilter.getDataInizio(), registrazioniFilter.getDataFine()));
	    }
	}
	// Filtra per anagrafe
	if (registrazioniFilter.getAnagrafe() != null && registrazioniFilter.getAnagrafe().getId().getCodice() != null) {
	    fr.addFilterField(FilterUtils.equals("anagrafeId", registrazioniFilter.getAnagrafe().getId().getCodice(), Integer.class));
	    //	    Anagrafe anagrafe = anagrafeDAO.findById(new PkId(registrazioniFilter.getAnagrafe().getId().getCodice()));
	    //	    registrazioniFilter.setAnagrafe(anagrafe);
	    //	    criteria.add(Restrictions.eq("anagrafe", anagrafe));
	}
	// Filtra per registrazioni causali
	if (registrazioniFilter.getRegistrazioniCausali() != null && registrazioniFilter.getRegistrazioniCausali().getId().getCodice() != null) {
	    fr.addFilterField(
		    FilterUtils.equals("registrazioniCausaliId", registrazioniFilter.getRegistrazioniCausali().getId().getCodice(), Integer.class));
	    //	    RegistrazioniCausali registrazioniCausali = registrazioniCausaliDAO.findById(new PkId(registrazioniFilter.getRegistrazioniCausali()
	    //		    .getId().getCodice()));
	    //	    registrazioniFilter.setRegistrazioniCausali(registrazioniCausali);
	    //	    criteria.add(Restrictions.eq("registrazioniCausali", registrazioniCausali));
	}
	// Filtra per mercato uso
	if (registrazioniFilter.getMercatiUso() != null && registrazioniFilter.getMercatiUso().getId().getCodice() != null) {
	    fr.addFilterField(FilterUtils.equals("mercatiUsoId", registrazioniFilter.getMercatiUso().getId().getCodice(), Integer.class));
	    //	    MercatiUso mercatiUso = mercatiUsoDAO.findById(new PkId(registrazioniFilter.getMercatiUso().getId().getCodice()));
	    //	    registrazioniFilter.setMercatiUso(mercatiUso);
	    //	    criteria.add(Restrictions.eq("mercatiUso", mercatiUso));
	}
	// Filtra per mercato
	if (registrazioniFilter.getMercati() != null && registrazioniFilter.getMercati().getId().getCodice() != null) {
	    fr.addFilterField(
		    FilterUtils.equals("id.codice", registrazioniFilter.getMercati().getId().getCodice(), "mercatiD.mercati", Integer.class));
	    //	    Mercati mercati = mercatiDAO.findById(new PkId(registrazioniFilter.getMercati().getId().getCodice()));
	    //	    criteria.createAlias("mercatiD", "_mercatiD", criteria.INNER_JOIN);
	    //	    criteria.add(Restrictions.eq("software", mercati.getSoftware()));
	    //	    criteria.add(Restrictions.eq("_mercatiD.mercati.id.codice", mercati.getId().getCodice()));
	    if (registrazioniFilter.getPosteggio() != null && registrazioniFilter.getPosteggio().getId() != null
		    && registrazioniFilter.getPosteggio().getId().getCodice() != null) {
		MercatiD mercatiD = mercatiDService.findById(new PkId(registrazioniFilter.getPosteggio().getId().getCodice()));
		if (mercatiD != null && mercatiD.getId().getCodice() != null) {
		    fr.addFilterField(FilterUtils.equals("mercatiDId", registrazioniFilter.getPosteggio().getId().getCodice(), Integer.class));
		    // criteria.add(Restrictions.eq("_mercatiD.id.codice", mercatiD.getId().getCodice()));
		}
	    }
	}
	// Filtra per Tipologia di intervento
	if (registrazioniFilter.getAlberoproc() != null && registrazioniFilter.getAlberoproc().getId().getCodice() != null) {
	    fr.addFilterField(
		    FilterUtils.equals("id.codice", registrazioniFilter.getAlberoproc().getId().getCodice(), "istanze.alberoproc", Integer.class));
	    //	    Alberoproc alberoproc = alberoprocDAO.findById(new PkId(registrazioniFilter.getAlberoproc().getId().getCodice()));
	    //	    criteria.createAlias("istanze", "_istanze", criteria.INNER_JOIN);
	    //	    criteria.add(Restrictions.eq("software", alberoproc.getSoftware()));
	    //	    criteria.add(Restrictions.eq("_istanze.alberoproc.id.codice", alberoproc.getId().getCodice()));
	}
	// Filtra per conti
	if (registrazioniFilter.getConti() != null && registrazioniFilter.getConti().getId().getCodice() != null) {
	    //	    criteria.createCriteria("registrazioniImportis", "registrazioniImporti");
	    //	    criteria.add(Restrictions.eq("registrazioniImporti.conti.id.codice", registrazioniFilter.getConti().getId().getCodice()));
	    //	    ProjectionList projectionList = Projections.projectionList();
	    //	    projectionList.add(Projections.groupProperty("id.codice"));
	    //	    criteria.setProjection(projectionList);
	    FilterField<Integer> existsContoinImporti = new FilterField<Integer>("contiId", "registrazioniImportis", FieldOperationsEnum.EXISTS,
		    new Integer[] { registrazioniFilter.getConti().getId().getCodice() }, RegistrazioniImporti.class);
	    existsContoinImporti.setExistsChildEntityId("registrazioni.id");
	    existsContoinImporti.setExistsParentEntityId("id");
	    fr.addFilterField(existsContoinImporti);
	}
	// TODO non implementato
	//	if (registrazioniFilter.getAmministrazioni() != null && registrazioniFilter.getAmministrazioni().getId().getCodice() != null) {
	//	    criteria.createCriteria("registrazioniImportis", "registrazioniImporti");
	//	    criteria.createCriteria("registrazioniImporti.regIoAssegnazionis", "regIoAssegnazioni");
	//	    criteria.createAlias("regIoAssegnazioni.registrazioniInOut", "_registrazioniInOut", Criteria.INNER_JOIN);
	//	    criteria.add(Restrictions.eq("_registrazioniInOut.amministrazioni",
	//		    amministrazioniDAO.findById(new PkId(registrazioniFilter.getAmministrazioni().getId().getCodice()))));
	//	}
	// Filtra per importo
	if (registrazioniFilter.getImporto() != null) {
	    fr.addFilterField(FilterUtils.equals("importo", registrazioniFilter.getImporto(), BigDecimal.class));
	    // criteria.add(Restrictions.eq("importo", registrazioniFilter.getImporto()));
	}
	if (registrazioniFilter.getSaldo() != null) {
	    fr.addFilterField(FilterUtils.greaterEqual("saldo", registrazioniFilter.getSaldo(), "vwRegistrazionisaldo", BigDecimal.class));
	    //	    criteria.createAlias("vwRegistrazionisaldo", "_vwRegistrazionisaldo");
	    //	    criteria.add(Restrictions.ge("_vwRegistrazionisaldo.saldo", registrazioniFilter.getSaldo()));
	}
	criteria.addRestriction(fr);
	if (!isCount) {
	    criteria.addOrder(FilterUtils.orderAsc("dataRegistrazione"));
	    criteria.addOrder(FilterUtils.orderAsc("descrizione", "mercatiD.mercati", FunctionsEnum.NVL_FUNCTION, "'ZZZZZZZZZZZZZZZZZZZZ'"));
	    criteria.addOrder(FilterUtils.orderAsc("descrizione", "mercatiUso", FunctionsEnum.NVL_FUNCTION, "'ZZZZZZZZZZZZZZZZZZZZ'"));
	    criteria.addOrder(FilterUtils.orderAsc("codiceposteggio", "mercatiD", FunctionsEnum.NVL_FUNCTION, "'ZZZZZZZZZZZZZZZZZZZZ'"));
	}
	return criteria;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public int countByRegistrazioniFilter(RegistrazioniFilter registrazioniFilter) {

	// §§§BEGIN§§§
	FilterTable filterTable = getFilterTableForCriteria(registrazioniFilter, true);
	return registrazioniDAO.countRecord(filterTable);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return 0;@@@ENDALTERNATIVEEXIT@@@
    }

    private boolean validateInsertOrUpdate(Registrazioni entity) {

	boolean hasErrors = false;
	// §§§BEGIN§§§
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	PkId id = entity.getRegistrazioniCausali().getId();
	if (id.getCodice() != null) {
	    RegistrazioniCausali registrazioniCausali = registrazioniCausaliService.findById(id);
	    if (registrazioniCausali.getAbilitato()) {
		if (registrazioniCausali.getRichiedeEndo()) {
		    entity.setMercatiD(null);
		    entity.setMercatiUso(null);
		    if (entity.getIstanze() == null || entity.getIstanze().getId() == null || entity.getIstanze().getId().getCodice() == null) {
			InvalidValue iv = new InvalidValue("field.required", entity.getClass(), "istanze", null, entity);
			_ivs.add(iv);
			hasErrors = true;
		    }
		    if (entity.getInventarioprocedimenti() == null || entity.getInventarioprocedimenti().getId() == null
			    || entity.getInventarioprocedimenti().getId().getCodice() == null) {
			InvalidValue iv = new InvalidValue("field.required", entity.getClass(), "inventarioprocedimenti", null, entity);
			_ivs.add(iv);
			hasErrors = true;
		    }
		}
		if (registrazioniCausali.getRichiedePosteggio()) {
		    entity.setInventarioprocedimenti(null);
		    if (entity.getMercatiD() == null || entity.getMercatiD().getId() == null || entity.getMercatiD().getId().getCodice() == null) {
			InvalidValue iv = new InvalidValue("field.required", entity.getClass(), "mercatiD", null, entity);
			_ivs.add(iv);
			hasErrors = true;
		    }
		    if (entity.getMercatiUso() == null || entity.getMercatiUso().getId() == null
			    || entity.getMercatiUso().getId().getCodice() == null) {
			InvalidValue iv = new InvalidValue("field.required", entity.getClass(), "mercatiUso", null, entity);
			_ivs.add(iv);
			hasErrors = true;
		    }
		}
		if (!(registrazioniCausali.getRichiedeEndo() || registrazioniCausali.getRichiedePosteggio())) {
		    // se la causale non richiede endo e non richiede posteggio allora setto a null gli oggetti
		    entity.setMercatiD(null);
		    entity.setMercatiUso(null);
		    entity.setIstanze(null);
		    entity.setInventarioprocedimenti(null);
		}
	    } else {
		InvalidValue iv = new InvalidValue("errors.registrazioni.causale.non.abilitata", entity.getClass(), "registrazioniCausali",
			registrazioniCausali.getDescrizione(), entity);
		_ivs.add(iv);
		hasErrors = true;
	    }
	} else {
	    InvalidValue iv = new InvalidValue("field.required", entity.getClass(), "registrazioniCausali", null, entity);
	    _ivs.add(iv);
	    hasErrors = true;
	}
	if (hasErrors) {
	    this.throwValidationMessages(_ivs);
	}
	// §§§END§§§
	return true;
    }

    @Override
    public List<Posteggio> findSituazioneContabileByMercatoAndPosteggio(Mercati mercati, MercatiUso mercatiUso) {

	// §§§BEGIN§§§
	return registrazioniDAO.findSituazioneContabileByMercatoAndPosteggio(mercati, mercatiUso);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return new java.util.ArrayList();@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public Vector<List<Posteggio>> findSituazioneContabileByMercatoAndPosteggioAndMercatoUso(Mercati mercati, MercatiUso mercatiUso) {

	// §§§BEGIN§§§
	return registrazioniDAO.findSituazioneContabileByMercatoAndPosteggioAndMercatoUso(mercati, mercatiUso);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public List<Anagrafe> findAnagrafeByRegistrazioni(Anagrafe entity) {

	// §§§BEGIN§§§
	return registrazioniDAO.findAnagrafeByRegistrazioni(entity);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return new java.util.ArrayList();@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public Integer insertRiduzioniAccertamento(Registrazioni entity, Responsabili respSistema, String regImpSel) {

	// §§§BEGIN§§§
	String[] regImpSelezionati;
	if (regImpSel == null || regImpSel.equals("")) {
	    regImpSelezionati = new String[0];
	} else {
	    regImpSelezionati = regImpSel.split(",");
	}
	// Creo un nuovo oggetto registrazione e popolo le
	// proprietà a partire dai codici selezionati nell'oggetto
	// RegRiduzioneAccertamento del command
	Registrazioni regRiduzione;
	if (entity.getId().getCodice() != null) {
	    this.update(entity);
	    regRiduzione = entity;
	} else {
	    regRiduzione = new Registrazioni();
	    // regRiduzione.setProgressivo(findProgressivo(entity.getAnno()));
	    regRiduzione.setAnagrafe(anagrafeService.findById(new PkId(entity.getAnagrafe().getId().getCodice())));
	    regRiduzione.setDescrizione(entity.getDescrizione());
	    regRiduzione.setNote(entity.getNote());
	    regRiduzione.setDataRegistrazione(entity.getDataRegistrazione());
	    regRiduzione.setSoftware(softwareService.findById(entity.getSoftware().getCodice()));
	    regRiduzione.setDataSistema(Calendar.getInstance().getTime());
	    if (entity.getMercatiD() != null) {
		if (entity.getMercatiD().getId() != null) {
		    regRiduzione.setMercatiD(mercatiDService.findById(new PkId(entity.getMercatiD().getId().getCodice())));
		    regRiduzione.setMercatiUso(mercatiUsoService.findById(new PkId(entity.getMercatiUso().getId().getCodice())));
		}
	    }
	    regRiduzione
		    .setRegistrazioniCausali(registrazioniCausaliService.findById(new PkId(entity.getRegistrazioniCausali().getId().getCodice())));
	    regRiduzione.setResponsabili(responsabiliService.findById(new PkId(entity.getResponsabili().getId().getCodice())));
	    regRiduzione.setResponsabiliSistema(respSistema);
	    this.insert(regRiduzione);
	}
	// itero la stringa contenente i checkbox selezionati e creo un array di RegistrazioniImporti con proprietà
	// identiche a quelle delle RegistrazioniImporti
	// selezionate ma con importo negativo
	RegistrazioniImporti regImportoRiduzione;
	RegistrazioniImporti regImportoDaRidurre;
	Set<RegistrazioniImporti> regImportiList = new HashSet<RegistrazioniImporti>();
	int i = 0;
	for (String string : regImpSelezionati) {
	    Integer codRegImpSel = new Integer(string);
	    regImportoDaRidurre = registrazioniImportiService.findById(new PkId(codRegImpSel));
	    regImportoRiduzione = new RegistrazioniImporti();
	    regImportoRiduzione.setConti(regImportoDaRidurre.getConti());
	    regImportoRiduzione.setRegistrazioni(regRiduzione);
	    // FIXME se la rimanenza è nulla e la causale della reg prevede solo importi negativi torna errore la
	    // insertRegImporto
	    regImportoRiduzione.setImporto(regImportoDaRidurre.getRimanenza().negate());
	    regImportoRiduzione.setIva(regImportoDaRidurre.getIva());
	    regImportoRiduzione.setNonPrevedeIncassi(true);
	    regImportoRiduzione.setNrRata(++i);
	    regImportoRiduzione.setScadenza(regImportoDaRidurre.getScadenza());
	    regImportiList.add(regImportoRiduzione);
	    registrazioniImportiService.insert(regImportoRiduzione);
	    // aggiorno la registrazioneImporti da ridurre con flag nonPrevedeIncassi=true
	    regImportoDaRidurre.setNonPrevedeIncassi(true);
	    registrazioniImportiService.update(regImportoDaRidurre);
	}
	return regRiduzione.getId().getCodice();
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    /**
     * Metodo privato per costruire una descrizione della registrazione di default
     * 
     * @param entity
     * @return
     */
    private String getDescrizioneDefault(Registrazioni entity) {

	// §§§BEGIN§§§
	String descrizioneRegistrazione = "";
	RegistrazioniCausali causale = registrazioniCausaliService.findById(entity.getRegistrazioniCausali().getId());
	descrizioneRegistrazione = causale.getDescrizione();
	if (entity.getDataRegistrazione() != null) {
	    Date dataRegistrazione = entity.getDataRegistrazione();
	    Calendar cal = GregorianCalendar.getInstance();
	    cal.setTime(dataRegistrazione);
	    entity.setAnno(new Short(String.valueOf(cal.get(Calendar.YEAR))));
	    String anno = String.valueOf(entity.getAnno());
	    descrizioneRegistrazione += " (" + anno + ")";
	}
	if (entity.getMercatiD() != null) {
	    if (entity.getMercatiD().getId().getCodice() != null) {
		MercatiD posteggio = mercatiDService.findById(entity.getMercatiD().getId());
		MercatiUso mercatiUso = mercatiUsoService.findById(entity.getMercatiUso().getId());
		descrizioneRegistrazione += ", manifestazione: " +
			posteggio.getMercati().getDescrizione() +
			", posteggio: " +
			posteggio.getCodiceposteggio() +
			", " +
			mercatiUso.getDescrizione();
	    }
	    if (entity.getIstanze() != null) {
		if (entity.getIstanze().getId().getCodice() != null) {
		    Istanze istanza = istanzeService.findById(entity.getIstanze().getId());
		    descrizioneRegistrazione += ", istanza: " + istanza.getDescrizioneIstanza();
		}
	    }
	    if (entity.getInventarioprocedimenti() != null) {
		if (entity.getInventarioprocedimenti().getId().getCodice() != null) {
		    Inventarioprocedimenti endo = inventarioprocedimentiService.findById(entity.getInventarioprocedimenti().getId());
		    descrizioneRegistrazione += ", endoprocedimento: " + endo.getProcedimento();
		}
	    }
	}
	return descrizioneRegistrazione;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void insertPagamentiUtente(List<Posteggio> listPosteggio, Date dataRegistrazione, MercatiUso mercatiuso, Boolean rate,
	    RegistrazioniCausali registrazioniCausali) {

	// §§§BEGIN§§§
	// int anno = 0;
	// Calendar theDate = GregorianCalendar.getInstance();
	// theDate.setTime(dataRegistrazione);
	// anno = theDate.get(Calendar.YEAR);
	// dal bean pagamento utenze estraggo la lista di posteggi
	for (Posteggio posteggio : listPosteggio) {
	    // verifica che l'inserimento della registrazione venga effettuato solo se il flag visualizza è a false
	    if (posteggio.getVisualizza() == false) {
		// Costruisco l'oggetto registrazione ricavando tutti i parametri dal bean pagamentoUtenze
		Registrazioni registrazioni = new Registrazioni();
		registrazioni.setMercatiD(posteggio.getPosteggio());
		registrazioni.setAnagrafe(posteggio.getAnagrafe());
		registrazioni.setDataRegistrazione(dataRegistrazione);
		registrazioni.setMercatiUso(mercatiUsoService.findById(new PkId(mercatiuso.getId().getCodice())));
		registrazioni.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
		registrazioni.setRegistrazioniCausali(registrazioniCausaliService.findById(new PkId(registrazioniCausali.getId().getCodice())));
		// registrazioni.setProgressivo(findProgressivo(anno));
		// inserisco l'elemto registrazione creato
		insert(registrazioni);
		// Considero le rateizzazioni (ogni singolo registrazione importo viene suddiviso in tante registrazioni
		// importi
		// (pari al numero di rate)
		if (rate == true) {
		    // Calcolo Importo di una registrazione di un posteggio
		    // ( necessario per vedere a che fascia di rateizzazione appartiene)
		    BigDecimal importoRegistrazione = new BigDecimal(0.00);
		    List<RegistrazioniImporti> registrazioniImportiList = posteggio.getRegistrazioniimportiposteggioList();
		    for (RegistrazioniImporti registrazioniImporti : registrazioniImportiList) {
			importoRegistrazione = importoRegistrazione.add(registrazioniImporti.getImporto());
		    }
		    // Ricavo per il posteggio di interesse tutte le righe di importo
		    List<RegistrazioniImporti> temp = posteggio.getRegistrazioniimportiposteggioList();
		    // Associo i parametri richiesti da "RigaImportoPosteggio" utilizzato per fare i calcoli
		    List<RigaImporto> righeCostoPosteggio = new ArrayList<RigaImporto>();
		    for (RegistrazioniImporti registrazioniImporti : temp) {
			RigaImporto rigaImportoPosteggio = new RigaImporto();
			rigaImportoPosteggio.setConto(new ContiBean(registrazioniImporti.getConti()));
			rigaImportoPosteggio.setImporto(registrazioniImporti.getImporto());
			Integer iva = registrazioniImporti.getConti().getIva();
			if (iva == null) {
			    // iva = WebConstants.CONST_IVA;
			    throw new RuntimeException(
				    "Errore nella configurazione dei conti della contabilita'. Non e' stata definita l'iva per il conto " +
					    rigaImportoPosteggio.getConto().getDescrizione() +
					    "(" +
					    rigaImportoPosteggio.getConto().getId() +
					    ")");
			}
			rigaImportoPosteggio.setIva(iva);
			righeCostoPosteggio.add(rigaImportoPosteggio);
		    }
		    List<RegistrazioniImporti> registrazioneimportoRateizzata = getRegistrazioniImportiRateizzati(registrazioni, importoRegistrazione,
			    righeCostoPosteggio);
		    for (RegistrazioniImporti registrazioniImportiRateizzati : registrazioneimportoRateizzata) {
			registrazioniImportiService.insert(registrazioniImportiRateizzati);
		    }
		    // Non considero le rateizzazioni; unica rata per ogni registrazione importo
		} else {
		    List<RegistrazioniImporti> temp = new ArrayList<RegistrazioniImporti>();
		    temp = posteggio.getRegistrazioniimportiposteggioList();
		    for (RegistrazioniImporti registrazioniImporti : temp) {
			registrazioniImporti.setRegistrazioni(registrazioni);
			// registrazioniImporti.setIva(WebConstants.CONST_IVA);
			registrazioniImporti.setNrRata(1);
			registrazioniImportiService.insert(registrazioniImporti);
		    }
		}
	    }
	}
	// §§§END§§§
    }

    @Override
    public List<RegistrazioniImporti> getRegistrazioniImportiRateizzati(Registrazioni registrazioni, BigDecimal importoTotale,
	    List<RigaImporto> rigaImportoList) {

	// §§§BEGIN§§§
	// Ricaviamo le fasce di rateizzazioni possibili per il software impostato
	List<RangeRateizzazioni> rangeRateizzazionilist = rangeRateizzazioniService.findAll(null, null);
	Oneritipirateizzazione oneritipirateizzazione = new Oneritipirateizzazione();
	// Recupero il Tipo di rateizzazione della fascia d'importo corrispondente
	for (RangeRateizzazioni rangeRateizzazioni : rangeRateizzazionilist) {
	    if (rangeRateizzazioni.getRangeBasso() < importoTotale.intValue()) {
		if (rangeRateizzazioni.getRangeAlto() != null && rangeRateizzazioni.getRangeAlto() >= importoTotale.intValue()) {
		    oneritipirateizzazione = rangeRateizzazioni.getTiporateizzazione();
		    break;
		}
		if (rangeRateizzazioni.getRangeAlto() == null) {
		    oneritipirateizzazione = rangeRateizzazioni.getTiporateizzazione();
		    break;
		}
	    }
	}
	// Controllo che il tipo di rateizzazione non ha il flag interessi legali settato.
	// In caso di registrazioni per le Manifestazioni non vanno considerati gli interessi legali.
	if (oneritipirateizzazione.getFlagInteressiLegali()) {
	    throw new RuntimeException("Il piano di rateizzazione non deve prevedere interessi legali.");
	}
	// Richiamo il metodo che mi restituisce una lista di registrazioni importi rateizzati
	// Se sono presenti gli interessi verrà aggiunto per ogni rata una registrazione importi che ha come conto
	// quello specifico per gli interessi.
	List<RegistrazioniImporti> result = this.getImportiRateizzati(registrazioni, registrazioni.getDataRegistrazione(), rigaImportoList,
		oneritipirateizzazione, null, null, null);
	return result;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return new java.util.ArrayList();@@@ENDALTERNATIVEEXIT@@@
    }

    /**
     * Metodo utilizzato per il calcolo delle registrazioni importi.Questo è un metodo accessorio del generico metodo di
     * rateizzazioni. Questo metodo viene utilizzato dal metodo che simula le rateizzazioni, poichè viene scelto in view
     * quale tipo di reteizzazione utilizzare.
     * 
     * @param registrazioni
     * @param dataregistrazione
     *            In caso di interessi legali la data di ragistrazione rappresenta la data finale
     * @param rigaImportoList
     * @param oneritipirateizzazione
     * @return
     */
    @Override
    public List<RegistrazioniImporti> getImportiRateizzati(Registrazioni registrazioni, Date dataregistrazione, List<RigaImporto> rigaImportoList,
	    Oneritipirateizzazione oneritipirateizzazione, Integer tipologiaRateizzazioneRate, Conti contoInteressirate,
	    List<ChiaveValoreBean<Conti, Integer>> ordinamentoContiRat) {

	// §§§BEGIN§§§
	List<RegistrazioniImporti> result = new ArrayList<RegistrazioniImporti>();
	// Se il flag interessi legali è true allora la data di registrazione è la data finale per il calcolo degli
	// interessi legali
	Date dataTemp = dataregistrazione;
	// Array di BigDecimal in cui memorizzo il valore d'interesse per ogni rata.
	BigDecimal[] interessiArray = null;
	if (oneritipirateizzazione.getNumerorate() != 0) {
	    interessiArray = new BigDecimal[oneritipirateizzazione.getNumerorate()];
	} else {
	    // Se non è presente il numero delle rate,allora viene settato a 1 di default.
	    interessiArray = new BigDecimal[1];
	}
	// Inizializzo a zero tutti i valori dell'array.
	for (int i = 0; i < interessiArray.length; i++) {
	    interessiArray[i] = new BigDecimal(0);
	}
	//	if (tipologiaRateizzazioneRate != null && tipologiaRateizzazioneRate.equals(Integer.valueOf(1))) {
	//	    List<RegistrazioniImporti> importiRateizzatiList = getRateConRipartizioneOrdinata(registrazioni, dataregistrazione, rigaImportoList,
	//		    oneritipirateizzazione, accontoPrimaRata, contoInteressirate, ordinamentoContiRat);
	//	    result.addAll(importiRateizzatiList);
	//	    //	    interessiArray = null;
	//	    //	    if (oneritipirateizzazione.getNumerorate() != 0) {
	//	    //		interessiArray = new BigDecimal[oneritipirateizzazione.getNumerorate()];
	//	    //	    } else {
	//	    //		// Se non è presente il numero delle rate,allora viene settato a 1 di default.
	//	    //		interessiArray = new BigDecimal[1];
	//	    //	    }
	//	    //	    // Inizializzo a zero tutti i valori dell'array.
	//	    //	    for (int i = 0; i < interessiArray.length; i++) {
	//	    //		interessiArray[i] = new BigDecimal(0);
	//	    //	    }
	//	    //	    for (RegistrazioniImporti ir : importiRateizzatiList) {
	//	    //		if (ir.getInteressi() != null) {
	//	    //		    BigDecimal interesseSomma = interessiArray[ir.getNrRata() - 1].add(ir.getInteressi());
	//	    //		    interessiArray[ir.getNrRata() - 1] = interesseSomma;
	//	    //		    ir.setInteressi(null);
	//	    //		}
	//	    //	    }
	//	} else {
	// Creo l'oggetto RateizzazioniHelper a cui gli passo oneritipirateizzazione per poter settare le varie
	// proprietà che verranno utilizzate dal metodo rateizzaImporto(...).
	RateizzazioniHelper rateizzazioniHelper = new RateizzazioniHelper(oneritipirateizzazione, interessiLegaliService);
	for (RigaImporto rigaImporto : rigaImportoList) {
	    // Determino le rate degli importi
	    // LOGGARE 
	    PeriodicitaEnum periodicitaEnum = rateizzazioniHelper.getPeriodicitaEnum();
	    TipologiaRateizzazioneEnum tipologiaRateizzazioneEnum = rateizzazioniHelper.getTipologiaRateizzazioneEnum();
	    // LOGGARE
	    List<ImportiRateizzati> importiRateizzatiList = rateizzazioniHelper.rateizzaImporto(rigaImporto.getImporto(), dataTemp,
		    rigaImporto.getDataInizio(), rigaImporto.getIva(), periodicitaEnum, tipologiaRateizzazioneEnum);
	    List<RegistrazioniImporti> tempRegImporti = new ArrayList<RegistrazioniImporti>();
	    for (ImportiRateizzati importiRateizzati : importiRateizzatiList) {
		RegistrazioniImporti canone = new RegistrazioniImporti();
		int numeroRata = importiRateizzati.getNumerorata();
		canone.setRegistrazioni(registrazioni);
		canone.setConti(getConti(rigaImporto.getConto()));
		canone.setIva(rigaImporto.getIva());
		canone.setImporto(importiRateizzati.getImportoRateizzatoSenzaInteresse());
		canone.setNrRata(numeroRata);
		canone.setInteressi(importiRateizzati.getImportoInteresse());
		canone.setScadenza(importiRateizzati.getScadenza());
		// Incremento il valore dell'interesse della specifica rata.
		if (importiRateizzati.getImportoInteresse() != null) {
		    BigDecimal interesseSomma = interessiArray[numeroRata - 1].add(importiRateizzati.getImportoInteresse());
		    interessiArray[numeroRata - 1] = interesseSomma;
		}
		tempRegImporti.add(canone);
	    }
	    result.addAll(tempRegImporti);
	}
	// Se sono presenti interessi allora inserisco una riga d'importo con conto specifico per gli interessi.
	// Come conto viene settato il conto che è stato configurato in Mercati Configurazione, o quello passato al metodo se non nullo
	Conti contoInteresse = null;
	if ((oneritipirateizzazione.getInteressirate() != null && !oneritipirateizzazione.getInteressirate().equals(""))
		|| oneritipirateizzazione.getFlagInteressiLegali()) {
	    List<RegistrazioniImporti> interessi = new ArrayList<RegistrazioniImporti>();
	    if (contoInteressirate != null) {
		if (contoInteressirate.getId() != null) {
		    if (contoInteressirate.getId().getCodice() != null) {
			contoInteresse = contoInteressirate;
		    }
		}
	    }
	    if (contoInteresse == null) {
		// Per Software sarà sempre presente un unico oggetto di MercatiConfigurazione	    
		List<MercatiConfigurazione> mercatiConfiguraziones = mercatiConfigurazioneService.findAll(null, null);
		for (MercatiConfigurazione mercatiConfigurazione : mercatiConfiguraziones) {
		    contoInteresse = mercatiConfigurazione.getContoInteressi();
		    break;
		}
	    }
	    int numeroRate = oneritipirateizzazione.getNumerorate();
	    for (int i = 0; i < numeroRate; i++) {
		// Il valore d'interesse sarà il valore dell'array interessiArray[] in posizione numerorata-1
		BigDecimal importoInteressi = interessiArray[i];
		if (importoInteressi != null && importoInteressi.compareTo(BigDecimal.ZERO) > 0) {
		    RegistrazioniImporti canone = new RegistrazioniImporti();
		    canone.setRegistrazioni(registrazioni);
		    canone.setIva(contoInteresse.getIva());
		    canone.setImporto(importoInteressi);
		    canone.setNrRata(i + 1);
		    // Setto la scadenza degli altri conti con stessa rata.
		    canone.setScadenza(result.get(i).getScadenza());
		    canone.setConti(contoInteresse);
		    interessi.add(canone);
		}
	    }
	    result.addAll(interessi);
	}
	if (tipologiaRateizzazioneRate != null && tipologiaRateizzazioneRate.equals(Integer.valueOf(1))) {
	    List<RegistrazioniImporti> output = ripartisciRate(result, ordinamentoContiRat, oneritipirateizzazione.getNumerorate(), contoInteresse);
	    return output;
	}
	// }
	return result;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return new java.util.ArrayList();@@@ENDALTERNATIVEEXIT@@@
    }

    private List<RegistrazioniImporti> ripartisciRate(List<RegistrazioniImporti> result, List<ChiaveValoreBean<Conti, Integer>> ordinamentoContiRat,
	    int numeroRate, Conti contoInteresse) {

	List<RegistrazioniImporti> output = new ArrayList<RegistrazioniImporti>();
	Map<Integer, BigDecimal> importisPerConto = new HashMap<Integer, BigDecimal>();
	// raggruppo nella mappa il totale degli importi
	Map<Integer, ImportiRateizzati> irs = new HashMap<Integer, ImportiRateizzati>();
	for (RegistrazioniImporti ri : result) {
	    Integer cc = ri.getConti().getId().getCodice();
	    BigDecimal importoConto = importisPerConto.get(cc);
	    if (importoConto == null) {
		importisPerConto.put(cc, ri.getImporto());
	    } else {
		importoConto = importoConto.add(ri.getImporto());
		if (log.isDebugEnabled()) {
		    log.debug("ripartisciRate# conto {}\t{}", cc.intValue(), importoConto);
		}
		importisPerConto.put(cc, importoConto);
	    }
	    Integer nrRata = ri.getNrRata();
	    ImportiRateizzati ir = irs.get(nrRata);
	    if (ir == null) {
		ir = new ImportiRateizzati();
		ir.setScadenza(ri.getScadenza());
		ir.setNumerorata(nrRata);
		ir.setImportoRateizzato(ri.getImporto());
		irs.put(nrRata, ir);
	    } else {
		BigDecimal importoRata = ir.getImportoRateizzato().add(ri.getImporto());
		ir.setImportoRateizzato(importoRata);
		irs.put(nrRata, ir);
		if (log.isDebugEnabled()) {
		    log.debug("ripartisciRate# rata {}\t{}", nrRata, importoRata);
		}
	    }
	}
	// ordino i conti
	List<ChiaveValoreBean<Conti, BigDecimal>> listaConti = new ArrayList<ChiaveValoreBean<Conti, BigDecimal>>(20);
	for (int i = 0; i < 20; i++) {
	    listaConti.add(new ChiaveValoreBean<Conti, BigDecimal>());
	}
	for (ChiaveValoreBean<Conti, Integer> cvb : ordinamentoContiRat) {
	    ChiaveValoreBean<Conti, BigDecimal> con = new ChiaveValoreBean<Conti, BigDecimal>();
	    con.setChiave(cvb.getChiave());
	    if (null != contoInteresse && cvb.getChiave().getId().getCodice().equals(CODICE_CONTO_INTERESSI_TEMPORANEO)) {
		con.setValore(importisPerConto.get(contoInteresse.getId().getCodice()));
	    } else {
		con.setValore(importisPerConto.get(cvb.getChiave().getId().getCodice()));
	    }
	    Integer pos = Integer.valueOf(0);
	    if ((Object) cvb.getValore() instanceof String) {
		try {
		    pos = Integer.parseInt(String.valueOf(cvb.getValore()));
		} catch (Exception e) {
		}
	    }
	    listaConti.add(pos, con);
	}
	List<ChiaveValoreBean<Conti, BigDecimal>> darimuovere = new ArrayList<ChiaveValoreBean<Conti, BigDecimal>>(20);
	for (ChiaveValoreBean<Conti, BigDecimal> cvb : listaConti) {
	    if (cvb.getChiave() == null) {
		darimuovere.add(cvb);
	    }
	}
	listaConti.removeAll(darimuovere);
	for (int i = 0; i < numeroRate; i++) {
	    ImportiRateizzati ir = irs.get((i + 1));
	    if (log.isDebugEnabled()) {
		log.debug("rata: ");
		log.debug("\tgetRateConRipartizioneOrdinata# numeroRata: {}", ir.getNumerorata());
		log.debug("\tgetRateConRipartizioneOrdinata# importorateizzato: {}", ir.getImportoRateizzato());
		log.debug("\tgetRateConRipartizioneOrdinata# scadenza: {}", ir.getScadenza());
	    }
	    BigDecimal importoRata = ir.getImportoRateizzato();
	    for (ChiaveValoreBean<Conti, BigDecimal> cvb : listaConti) {
		BigDecimal importoConto = cvb.getValore();
		if (null != importoConto) {
		    if (importoConto.compareTo(importoRata) >= 0) {
			RegistrazioniImporti r = new RegistrazioniImporti();
			r.setImporto(importoRata);
			r.setInteressi(ir.getImportoInteresse());
			if (cvb.getChiave().getId().getCodice().equals(CODICE_CONTO_INTERESSI_TEMPORANEO)) {
			    r.setConti(contoInteresse);
			    r.setIva(contoInteresse.getIva());
			} else {
			    r.setConti(cvb.getChiave());
			    r.setIva(cvb.getChiave().getIva());
			}
			r.setNrRata(ir.getNumerorata());
			r.setScadenza(ir.getScadenza());
			output.add(r);
			cvb.setValore(importoConto.subtract(importoRata));
			importoRata = importoRata.subtract(cvb.getValore());
			break;
		    } else {
			if (cvb.getValore().compareTo(BigDecimal.ZERO) != 0) {
			    RegistrazioniImporti r = new RegistrazioniImporti();
			    r.setImporto(cvb.getValore());
			    r.setInteressi(ir.getImportoInteresse());
			    if (cvb.getChiave().getId().getCodice().equals(CODICE_CONTO_INTERESSI_TEMPORANEO)) {
				r.setConti(contoInteresse);
				r.setIva(contoInteresse.getIva());
			    } else {
				r.setConti(cvb.getChiave());
				r.setIva(cvb.getChiave().getIva());
			    }
			    r.setNrRata(ir.getNumerorata());
			    r.setScadenza(ir.getScadenza());
			    output.add(r);
			    importoRata = importoRata.subtract(cvb.getValore());
			    cvb.setValore(BigDecimal.ZERO);
			}
		    }
		}
	    }
	}
	return output;
	// ordinati;
    }

    /**
     * la funzione modifica l'anno a partire dalla dataregistrazione
     * 
     * @param entity
     */
    private void assegnaAnnoDaDataRegistrazione(Registrazioni entity) {

	Calendar cal = GregorianCalendar.getInstance();
	cal.setTime(entity.getDataRegistrazione());
	Integer year = cal.get(Calendar.YEAR);
	entity.setAnno(year.shortValue());
    }

    /**
     * il metodo riesegue il conteggio delle righe di importo per calcolare l'importo della registrazione
     * 
     * @param registrazioneId
     */
    public void updateImportoRegistrazione(Registrazioni registrazione) {

	// §§§BEGIN§§§
	List<RegistrazioniImporti> registrazioniImportiList = registrazioniImportiService.findByRegistrazione(registrazione);
	BigDecimal importoRegistrazione = new BigDecimal(0.0);
	for (RegistrazioniImporti registrazioniImporti : registrazioniImportiList) {
	    importoRegistrazione = importoRegistrazione.add(registrazioniImporti.getImporto());
	}
	registrazione.setImporto(importoRegistrazione);
	assegnaAnnoDaDataRegistrazione(registrazione);
	this.validateEntity(registrazione);
	registrazioniDAO.update(registrazione);
	// §§§END§§§
    }

    @Override
    public List<Registrazioni> findMercatiByRegistrazioniAndAnagrafe(Anagrafe anagrafe) {

	// §§§BEGIN§§§
	return registrazioniDAO.findMercatiByRegistrazioniAndAnagrafe(anagrafe);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return new java.util.ArrayList();@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public List<Registrazioni> findAnagrafeByRegistrazioniAndMercati(Mercati mercati) {

	// §§§BEGIN§§§
	return registrazioniDAO.findAnagrafeByRegistrazioniAndMercati(mercati);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return new java.util.ArrayList();@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void adeguaImportiRegistrazioni(RegistrazioniFilter registrazioniFilter, BigDecimal percentualeAdeguamento) {

	// §§§BEGIN§§§
	if (percentualeAdeguamento == null) {
	    throw new RuntimeException("Percentuale adeguamento è nulla");
	}
	if (percentualeAdeguamento.compareTo(new BigDecimal(0)) == 0) {
	    return;
	}
	// FIXME paginare i risultati
	List<Registrazioni> registrazioniList = this.findByRegistrazioniFilter(registrazioniFilter, null, null);
	if (log.isDebugEnabled()) {
	    log.debug("adeguamento percentuale: " + percentualeAdeguamento);
	}
	for (Registrazioni registrazione : registrazioniList) {
	    BigDecimal importoTotaleRegistrazione = registrazione.getImporto();
	    BigDecimal importoAdeguato = adeguaImporto(importoTotaleRegistrazione, percentualeAdeguamento);
	    BigDecimal sommaDegliImporti = new BigDecimal(0.00);
	    List<RegistrazioniImporti> registrazioniImportiList = registrazioniImportiService.findByRegistrazione(registrazione);
	    for (RegistrazioniImporti registrazioniImporti : registrazioniImportiList) {
		BigDecimal importo = registrazioniImporti.getImporto();
		if (importo.floatValue() != new BigDecimal(0).floatValue()) {
		    if (log.isDebugEnabled()) {
			log.debug("importo: " + importo);
		    }
		    importo = adeguaImporto(importo, percentualeAdeguamento);
		    if (log.isDebugEnabled()) {
			log.debug("importo + percentuale: " + importo);
		    }
		    importo = importo.setScale(2, BigDecimal.ROUND_HALF_UP);
		    sommaDegliImporti = sommaDegliImporti.add(importo);
		    registrazioniImporti.setImporto(importo);
		    registrazioniImportiService.update(registrazioniImporti);
		}
	    }
	    importoAdeguato = importoAdeguato.setScale(2, BigDecimal.ROUND_HALF_UP);
	    if (sommaDegliImporti.compareTo(importoAdeguato) == 1) {
		// in questo caso la somma delle rate adeguate è maggiore
		// dell'adeguamento dell'importo della registrazione
		// e da una rata sottraggo il resto
		BigDecimal resto = sommaDegliImporti.subtract(importoAdeguato);
		List<RegistrazioniImporti> registrazioniImportiList2 = registrazioniImportiService.findByRegistrazione(registrazione);
		for (RegistrazioniImporti registrazioniImporti2 : registrazioniImportiList2) {
		    BigDecimal importo2 = registrazioniImporti2.getImporto();
		    importo2 = importo2.subtract(resto);
		    registrazioniImporti2.setImporto(importo2);
		    importo2 = importo2.setScale(2, BigDecimal.ROUND_HALF_UP);
		    registrazioniImporti2.setImporto(importo2);
		    registrazioniImportiService.update(registrazioniImporti2);
		    break;
		}
	    } else if (sommaDegliImporti.compareTo(importoAdeguato) == -1) {
		BigDecimal resto = sommaDegliImporti.subtract(importoAdeguato);
		// in questo caso la somma delle rate adeguate è maggiore
		// dell'adeguamento dell'importo della registrazione
		// e da una rata aggiungo il resto
		List<RegistrazioniImporti> registrazioniImportiList2 = registrazioniImportiService.findByRegistrazione(registrazione);
		for (RegistrazioniImporti registrazioniImporti2 : registrazioniImportiList2) {
		    BigDecimal importo2 = registrazioniImporti2.getImporto();
		    importo2 = importo2.add(resto);
		    registrazioniImporti2.setImporto(importo2);
		    importo2 = importo2.setScale(2, BigDecimal.ROUND_HALF_UP);
		    registrazioniImporti2.setImporto(importo2);
		    registrazioniImportiService.update(registrazioniImporti2);
		    break;
		}
	    }
	}
	// §§§END§§§
    }

    private BigDecimal adeguaImporto(BigDecimal importo, BigDecimal percentualeAdeguamento) {

	// §§§BEGIN§§§
	importo = importo.add(((importo.multiply(percentualeAdeguamento).divide(new BigDecimal(100.00)))));
	return importo;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public Set<RegistrazioniImporti> rateizzaRegistrazioni(Date dataregistrazione, List<RegistrazioniImporti> regList,
	    Oneritipirateizzazione oneritipirateizzazione, List<ContoInteressiLegali> contoInteressiLegaliList, Integer tipologiaRateizzazioneRate,
	    Conti contoInteressirate, List<ChiaveValoreBean<Conti, Integer>> ordinamentoContiRat) {

	// §§§BEGIN§§§
	Set<RegistrazioniImporti> resultSet = new HashSet<RegistrazioniImporti>();
	List<RigaImporto> righeImportoList = new ArrayList<RigaImporto>();
	for (RegistrazioniImporti registrazioniImporti : regList) {
	    RigaImporto rigaImportoPosteggio = new RigaImporto();
	    rigaImportoPosteggio.setConto(new ContiBean(registrazioniImporti.getConti()));
	    rigaImportoPosteggio.setImporto(registrazioniImporti.getImporto());
	    rigaImportoPosteggio.setIva(registrazioniImporti.getIva());
	    for (ContoInteressiLegali contoInteressiLegali : contoInteressiLegaliList) {
		if (contoInteressiLegali.getConti().getId().getCodice().compareTo(registrazioniImporti.getConti().getId().getCodice()) == 0) {
		    rigaImportoPosteggio.setDataInizio(contoInteressiLegali.getDataInizio());
		    break;
		}
	    }
	    righeImportoList.add(rigaImportoPosteggio);
	}
	Registrazioni registrazioni = new Registrazioni();
	// Metodo che restituisce una lista di registrazioni importi rateizzati.
	List<RegistrazioniImporti> result = this.getImportiRateizzati(registrazioni, dataregistrazione, righeImportoList, oneritipirateizzazione,
		tipologiaRateizzazioneRate, contoInteressirate, ordinamentoContiRat);
	// Setto la lista ottenuta nel set, poichè il metodo restituisce un set.
	resultSet.addAll(result);
	return resultSet;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void validateRateizzazione(RegistrazioniFilter registrazioniFilter) {

	// §§§BEGIN§§§
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (registrazioniFilter.getOneritipirateizzazione().getId().getCodice() == 0) {
	    InvalidValue iv = new InvalidValue("validator.nonvuoto", registrazioniFilter.getClass(), "oneritipirateizzazione.id.codice", "",
		    registrazioniFilter);
	    _ivs.add(iv);
	}
	if (registrazioniFilter.getOneritipirateizzazione().getFlagInteressiLegali()) {
	    int i = 0;
	    for (ContoInteressiLegali contoInteressiLegali : registrazioniFilter.getContoInteressiLegaliList()) {
		if (contoInteressiLegali.getDataInizio() == null) {
		    InvalidValue iv = new InvalidValue("validator.nonvuoto", registrazioniFilter.getClass(),
			    "contoInteressiLegaliList[" + i + "].dataInizio", "", registrazioniFilter);
		    _ivs.add(iv);
		} else if (contoInteressiLegali.getDataInizio().compareTo(registrazioniFilter.getDataInizio()) > 0) {
		    InvalidValue iv = new InvalidValue("errors.date.sequenza", registrazioniFilter.getClass(),
			    "contoInteressiLegaliList[" + i + "].dataInizio", "", registrazioniFilter);
		    _ivs.add(iv);
		}
		i++;
	    }
	}
	if (registrazioniFilter.getDataInizio() == null) {
	    InvalidValue iv = new InvalidValue("validator.nonvuoto", registrazioniFilter.getClass(), "dataInizio", "", registrazioniFilter);
	    _ivs.add(iv);
	}
	if (_ivs.size() > 0) {
	    this.throwValidationMessages(_ivs);
	}
	// §§§END§§§
    }

    @Override
    public void deleteAllRegistrazioniImporti(Registrazioni registrazioni) {

	// §§§BEGIN§§§
	Set<RegistrazioniImporti> set = registrazioni.getRegistrazioniImportis();
	registrazioni.setRegistrazioniImportis(null);
	for (RegistrazioniImporti registrazioniImporti : set) {
	    registrazioniImportiService.delete(registrazioniImporti);
	}
	// §§§END§§§
    }

    @Override
    public void updateRegistrazioniImportiRateizzati(Oneritipirateizzazione oneritipirateizzazione, Date dataregistrazione,
	    Registrazioni registrazioni, List<ContoInteressiLegali> contoInteressiLegaliList, Integer tipologiaRateizzazioneRate,
	    Conti contoInteressirate, List<ChiaveValoreBean<Conti, Integer>> ordinamentoContiRat) {

	// §§§BEGIN§§§
	List<RegistrazioniImporti> regImportiList = registrazioniImportiService.findByRegistrazioneGroupByConto(registrazioni);
	Set<RegistrazioniImporti> simulaRegImportiList = this.rateizzaRegistrazioni(dataregistrazione, regImportiList, oneritipirateizzazione,
		contoInteressiLegaliList, tipologiaRateizzazioneRate, contoInteressirate, ordinamentoContiRat);
	this.deleteAllRegistrazioniImporti(registrazioni);
	for (RegistrazioniImporti registrazioniImporti : simulaRegImportiList) {
	    registrazioniImporti.setRegistrazioni(registrazioni);
	    registrazioniImportiService.insert(registrazioniImporti);
	}
	// §§§END§§§
    }

    @Override
    public Integer insertTransazione(Registrazioni entity, List<Registrazioni> registrazioniDaRidurre, List<RegistrazioniImporti> importidaRidurre) {

	// §§§BEGIN§§§
	// devo inserire le riduzioni di accertamento per registrazioniDaRidurre e importidaRidurre
	// ed inserire entity
	Integer answer = null;
	// per le riduzioni di accertamento devo trovare la causale specificata in configurazione
	MercatiConfigurazione mercatiConfigurazione = mercatiConfigurazioneService.findById(new MercatiConfigurazioneId());
	RegistrazioniCausali causaleDiminuzione = mercatiConfigurazione.getCausaleDiminuzione();
	for (Registrazioni registrazioni : registrazioniDaRidurre) {
	    Registrazioni registrazioneDaRidurre = new Registrazioni();
	    registrazioneDaRidurre.setAnagrafe(registrazioni.getAnagrafe());
	    registrazioneDaRidurre.setResponsabili(entity.getResponsabiliSistema());
	    registrazioneDaRidurre.setDataRegistrazione(registrazioni.getDataRegistrazione());
	    registrazioneDaRidurre.setMercatiD(registrazioni.getMercatiD());
	    registrazioneDaRidurre.setMercatiUso(registrazioni.getMercatiUso());
	    registrazioneDaRidurre.setRegistrazioniCausali(causaleDiminuzione);
	    registrazioneDaRidurre.setAnno(registrazioni.getAnno());
	    registrazioneDaRidurre.setDataSistema(Calendar.getInstance().getTime());
	    registrazioneDaRidurre.setSoftware(registrazioni.getSoftware());
	    registrazioneDaRidurre.setIstanze(registrazioni.getIstanze());
	    registrazioneDaRidurre.setInventarioprocedimenti(registrazioni.getInventarioprocedimenti());
	    StringBuffer regImpSelBuffer = new StringBuffer("");
	    for (RegistrazioniImporti registrazioniImporti : importidaRidurre) {
		Integer codiceRegistrazione = registrazioniImporti.getRegistrazioni().getId().getCodice();
		if (codiceRegistrazione.equals(registrazioni.getId().getCodice())) {
		    regImpSelBuffer.append(",").append(registrazioniImporti.getId().getCodice());
		}
	    }
	    String regImpSel = regImpSelBuffer.toString();
	    // elimino la prima virgola
	    regImpSel = regImpSel.substring(1);
	    this.insertRiduzioniAccertamento(registrazioneDaRidurre, entity.getResponsabiliSistema(), regImpSel);
	}
	Registrazioni transazione = new Registrazioni();
	transazione.setAnagrafe(entity.getAnagrafe());
	// transazione.setResponsabili(entity.getResponsabiliSistema());
	transazione.setResponsabili(entity.getResponsabili());
	transazione.setResponsabiliSistema(entity.getResponsabiliSistema());
	transazione.setDataRegistrazione(entity.getDataRegistrazione());
	transazione.setMercatiD(entity.getMercatiD());
	transazione.setMercatiUso(entity.getMercatiUso());
	transazione.setRegistrazioniCausali(entity.getRegistrazioniCausali());
	transazione.setAnno(entity.getAnno());
	transazione.setDataSistema(Calendar.getInstance().getTime());
	transazione.setSoftware(entity.getSoftware());
	transazione.setIstanze(entity.getIstanze());
	transazione.setInventarioprocedimenti(entity.getInventarioprocedimenti());
	this.insert(transazione);
	Set<RegistrazioniImporti> importisIn = entity.getRegistrazioniImportis();
	for (RegistrazioniImporti in : importisIn) {
	    RegistrazioniImporti out = new RegistrazioniImporti();
	    out.setConti(in.getConti());
	    out.setImporto(in.getImporto());
	    out.setInteressi(in.getInteressi());
	    out.setIva(in.getIva());
	    out.setNonPrevedeIncassi(false);
	    out.setNrRata(in.getNrRata());
	    out.setRegistrazioni(transazione);
	    out.setScadenza(in.getScadenza());
	    registrazioniImportiService.insert(out);
	}
	answer = transazione.getId().getCodice();
	return answer;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void insertRateMancanti(short anno, int numerorate) {

	// §§§BEGIN§§§
	List<Registrazioni> regList = this.findRegistrazioniByAnno(anno);
	List<Registrazioni> regListUpdateRate = new ArrayList<Registrazioni>();
	List<Integer> rateMancanti = new ArrayList<Integer>();
	// Determino le registrazioni a cui mancano le rate
	for (Registrazioni registrazioni : regList) {
	    if (registrazioni.getRegistrazioniImportis().size() < numerorate) {
		// inserisco la registrazione
		regListUpdateRate.add(registrazioni);
		// inserisco il numero di rate che mancano alla registrazione
		rateMancanti.add((numerorate - registrazioni.getRegistrazioniImportis().size()));
	    }
	}
	int i = 0;
	// Scorro le registrazioni con rate mancanti
	for (Registrazioni registrazioni : regListUpdateRate) {
	    Integer numeroRateMancanti = rateMancanti.get(i);
	    Set<RegistrazioniImporti> set = registrazioni.getRegistrazioniImportis();
	    // se la registrazione non ha registrazioni importi allora la salto
	    if (!set.isEmpty()) {
		Iterator<RegistrazioniImporti> iterator = set.iterator();
		RegistrazioniImporti registrazioniImporti = (RegistrazioniImporti) iterator.next();
		// Conto della prima registrazione importi
		// Tutte le registrazioni importi avranno lo stesso conto
		Conti conti = registrazioniImporti.getConti();
		// Importo della prima registrazioni importi
		// Tutte le registrazioni importi avranno lo stesso importo
		BigDecimal importo = registrazioniImporti.getImporto();
		int numeroRata = numerorate - numeroRateMancanti + 1;
		if (log.isDebugEnabled()) {
		    log.debug("Aggiornamento rate..START");
		}
		for (int j = 0; j < numeroRateMancanti; j++) {
		    RegistrazioniImporti importi = new RegistrazioniImporti();
		    importi.setConti(conti);
		    importi.setImporto(importo);
		    Integer iva = conti.getIva();
		    if (iva == null) {
			// iva = WebConstants.CONST_IVA;
			throw new RuntimeException(
				"Errore nella configurazione dei conti della contabilita'. Non e' stata definita l'iva per il conto " +
					conti.getDescrizione() +
					"(" +
					conti.getId() +
					")");
		    }
		    importi.setIva(iva);
		    importi.setRegistrazioni(registrazioni);
		    importi.setNrRata(numeroRata);
		    Calendar calscadenza = GregorianCalendar.getInstance();
		    // Il numero del mese corrisponde alla rata.
		    // es: rate=5 mese=5=Maggio
		    // Per i Calendar i mesi partono da 0, allora bisogna fare numeroRata - 1.
		    int mese = numeroRata - 1;
		    calscadenza.set(anno, mese, 1);
		    int giorniMese = calscadenza.getActualMaximum(Calendar.DAY_OF_MONTH);
		    // La data di scadenza è a FINE_MESE
		    calscadenza.set(Calendar.DATE, giorniMese);
		    importi.setScadenza(calscadenza.getTime());
		    if (log.isDebugEnabled()) {
			DateFormat myDateFormatOut = new SimpleDateFormat("dd/MM/yyyy");
			String outdate = myDateFormatOut.format(calscadenza.getTime());
			log.debug("NUMERO RATA: " + numeroRata);
			log.debug("CONTO: " + conti.getDescrizione());
			log.debug("IMPORTO: " + importo);
			log.debug("IVA: " + importi.getIva());
			log.debug("DATA SCADENZA: " + outdate);
			log.debug("REGISTRAZIONE: " + registrazioni.getProgressivo());
		    }
		    registrazioniImportiService.insert(importi);
		    numeroRata++;
		}
	    }
	    i++;
	}
	if (log.isDebugEnabled()) {
	    log.debug("Aggiornamento rate..COMPLETED!!");
	}
	// §§§END§§§
    }

    @Override
    public List<Registrazioni> findRegistrazioniByAnno(short anno) {

	// §§§BEGIN§§§
	return registrazioniDAO.findRegistrazioniByAnno(anno);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void insertRegistrazioniLettureContatori(Registrazioni registrazioni, Set<RegistrazioniImporti> set) {

	// §§§BEGIN§§§
	this.insert(registrazioni);
	for (RegistrazioniImporti registrazioniImporti : set) {
	    registrazioniImporti.setRegistrazioni(registrazioni);
	    registrazioniImportiService.insert(registrazioniImporti);
	}
	// §§§END§§§
    }

    @Override
    public List<Registrazioni> findByAnagrafe(Integer codiceAnagrafe, Integer firstResult, Integer maxResult) {

	// §§§BEGIN§§§
	if (codiceAnagrafe == null) {
	    throw new IllegalArgumentException("findByAnagrafe: il parametro codiceAnagrafe e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceAnagrafe, "anagrafe", Integer.class));
	filterTable.addRestriction(fr);
	return registrazioniDAO.findByFilterTable(filterTable, firstResult, maxResult);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return new java.util.ArrayList();@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void clear() {

	registrazioniDAO.clear();
    }

    @Override
    public List<RegistrazioniImporti> getRegistrazioniAnnuali(Registrazioni registrazione, List<RigaImporto> rigaImportoList,
	    Integer tipoScadenzaRata) {

	// §§§BEGIN§§§	
	// Richiamo il metodo che mi restituisce una lista di registrazioni importi rateizzati
	List<RegistrazioniImporti> result = new ArrayList<RegistrazioniImporti>();
	// Array di BigDecimal in cui memorizzo il valore d'interesse per ogni rata.
	for (RigaImporto rigaImporto : rigaImportoList) {
	    // Determino le rate degli importi
	    //	    List<ImportiRateizzati> importiRateizzatiList = rateizzazioniHelper.rateizzaImporto(rigaImporto.getImporto(), dataTemp,
	    //		    rigaImporto.getDataInizio(), rigaImporto.getIva());
	    List<RegistrazioniImporti> tempRegImporti = new ArrayList<RegistrazioniImporti>();
	    for (int i = 1; i < 13; i++) {
		RegistrazioniImporti canone = new RegistrazioniImporti();
		canone.setRegistrazioni(registrazione);
		canone.setConti(getConti(rigaImporto.getConto()));
		canone.setIva(rigaImporto.getIva());
		if (rigaImporto.isValoreMensile()) { // è mensile
		    canone.setImporto(rigaImporto.getImporto());
		} else {
		    canone.setImporto(rigaImporto.getImporto().divide(BigDecimal.valueOf(12))); // è annuale
		}
		canone.setNrRata(i);
		canone.setInteressi(BigDecimal.ZERO);
		Calendar c = GregorianCalendar.getInstance();
		c.set(Calendar.YEAR, registrazione.getAnno());
		c.set(Calendar.MONTH, i - 1); // GENNAIO == 0
		Date scadenza = new DataScadenzaResolver(TipoScadenzaEnum.fromValue(tipoScadenzaRata), c.getTime(), i, "").calcolaScadenza();
		canone.setScadenza(scadenza);
		tempRegImporti.add(canone);
	    }
	    result.addAll(tempRegImporti);
	}
	// }
	return result;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return new java.util.ArrayList();@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public List<Integer> findCodiciByRegistrazioniFilter(RegistrazioniFilter registrazioniFilter) {

	return registrazioniDAO.findCodiciByRegistrazioniFilter(registrazioniFilter);
    }

    @Override
    public void updateAggiornaIVA(BigDecimal valoreIva, Date data, Software software) {

	int c = registrazioniImportiService.countPerAggiornamentoIVA(valoreIva, data, software);
	if (c > 0) {
	    List<Integer> codiceRegistrazioneImporti = registrazioniImportiService.findPerAggiornamentoIVA(valoreIva, data, software);
	    for (Integer codiceRI : codiceRegistrazioneImporti) {
		RegistrazioniImporti ri = registrazioniImportiService.findById(new PkId(codiceRI));
		if (ri.getRimanenza() != null) {
		    if (ri.getRimanenza().compareTo(BigDecimal.ZERO) > 0) {
			aggiornaIva(ri, valoreIva);
			registrazioniDAO.flush();
			registrazioniDAO.clear();
		    }
		}
	    }
	}
    }

    private void aggiornaIva(RegistrazioniImporti ri, BigDecimal valoreIva) {

	// aggiorna il record
	BigDecimal imponibile = ri.getImponibile();
	BigDecimal dovutoIva = imponibile.multiply(valoreIva).divide(BigDecimal.valueOf(100)).setScale(WebConstants.NUMERO_CIFRE_DECIMALI,
		BigDecimal.ROUND_HALF_UP);
	if (log.isDebugEnabled()) {
	    log.debug("aggiornaIva# imponibile: {}" + imponibile);
	    log.debug("aggiornaIva# dovutoIVA: {}" + dovutoIva);
	}
	ri.setImporto(imponibile.add(dovutoIva));
	ri.setIva(valoreIva.intValue());
	registrazioniImportiService.update(ri);
    }

    @Override
    public byte[] createTracciatoByRegistrazioniFilter(RegistrazioniFilter registrazioniFilter) {

	List<Integer> codici = this.findCodiciByRegistrazioniFilter(registrazioniFilter);
	String directoryTemporanea = Utilities.getSystemTempDir() +
		File.separator +
		"TRACCIATI" +
		File.separator +
		ORMHelper.getIdcomuneAlias() +
		File.separator +
		System.currentTimeMillis();
	String directoryTemporaneaFile = directoryTemporanea + File.separator + "files";
	File tmpFolder = new File(directoryTemporanea);
	File tmpFolderFile = new File(directoryTemporaneaFile);
	if (!tmpFolderFile.exists()) {
	    tmpFolderFile.mkdirs();
	}
	List<File> fileCreati = tracciatiService.creaTracciatiRegistrazioni(codici, tmpFolderFile);
	File zipFile = new File(directoryTemporanea, "Tracciato.zip");
	OutputStream writeTo = null;
	try {
	    writeTo = new FileOutputStream(zipFile);
	    Utilities.zipTo(tmpFolderFile, writeTo);
	    InputStream is = new FileInputStream(zipFile);
	    return IOUtils.toByteArray(is);
	} catch (FileNotFoundException e) {
	    throw new RuntimeException(e);
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    private Conti getConti(ContiBean conto) {

	if (conto == null || conto.getId() == null || conto.getId().getCodice() == null) {
	    return null;
	}
	return contiService.findById(new PkId(conto.getId().getCodice()));
    }
}