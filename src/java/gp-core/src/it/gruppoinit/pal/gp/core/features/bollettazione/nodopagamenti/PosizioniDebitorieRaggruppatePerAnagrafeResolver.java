package it.gruppoinit.pal.gp.core.features.bollettazione.nodopagamenti;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.features.bollettazione.arrotondamento.ArrotondamentoEnum;
import it.gruppoinit.pal.gp.core.features.bollettazione.arrotondamento.ArrotondamentoFactory;
import it.gruppoinit.pal.gp.core.features.bollettazione.arrotondamento.ArrotondamentoService;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.AnagraficaBollettazione;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.DettaglioBollettazione;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.RigaBollettazione;
import it.gruppoinit.pal.gp.core.features.bollettazione.rateizzazioni.BollettazioneRataBean;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.ImportoBean;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.NodoPagamentiService;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.RataBean;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.VerticalizzazioneNodoPagamentiServiceImpl;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.rest.ConnettoreType;
import it.gruppoinit.pal.gp.core.features.oneri.rateizzazione.configurazione.tipiscadenze.DataScadenzaResolver;
import it.gruppoinit.pal.gp.core.features.oneri.rateizzazione.configurazione.tipiscadenze.TipoScadenzaEnum;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.BollGestDettaglioService;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;

public class PosizioniDebitorieRaggruppatePerAnagrafeResolver extends PosizioniDebitorieResolverBase implements IPosizioniDebitorieResolver {

    private Logger logger = LoggerFactory.getLogger(PosizioniDebitorieRaggruppatePerAnagrafeResolver.class);
    private List<AnagraficaBollettazione> anagraficaBollettazioneList;
    private VerticalizzazioniService verticalizzazioniService;
    private AnagrafeService anagrafeService;
    private BollGestDettaglioService bollGestDettaglioService;
    private String descrizioneBollettazione;
    private Date dataScadenzaBollettazione;
    private Set<BollettazioneRataBean> rateizzazione;
    private int idTestataBollettazione;
    private NodoPagamentiService nodoPagamentiService;
    private Map<String, ConnettoreType> mappaConnettoriPerCf = new HashMap<String, ConnettoreType>();
    private ComuniassociatiService comuniassociatiService;

    public PosizioniDebitorieRaggruppatePerAnagrafeResolver(VerticalizzazioniService verticalizzazioniService, AnagrafeService anagrafeService,
	    BollGestDettaglioService bollGestDettaglioService, DettaglioBollettazione boll, NodoPagamentiService nodoPagamentiService,
	    ComuniassociatiService comuniassociatiService) {

	this.verticalizzazioniService = verticalizzazioniService;
	this.anagraficaBollettazioneList = boll.getAnagraficaBollettazioneList();
	this.descrizioneBollettazione = boll.getDescrizione();
	this.dataScadenzaBollettazione = boll.getDataScadenza();
	this.rateizzazione = boll.getRateizzazione();
	this.bollGestDettaglioService = bollGestDettaglioService;
	this.anagrafeService = anagrafeService;
	this.idTestataBollettazione = boll.getId();
	this.nodoPagamentiService = nodoPagamentiService;
	this.comuniassociatiService = comuniassociatiService;
    }

    @Override
    public PosizioneDebitoriaPerNodoPagamentiBean getPosizioni() {

	logger.debug("getPosizioni ({}) entro nel metodo", idTestataBollettazione);
	List<PosizioneDebitoriaBollettazioneBean> posizioniDebitorie = new ArrayList<PosizioneDebitoriaBollettazioneBean>();
	Set<String> codiciComune = new HashSet<String>();
	Map<String, String> mappaCfEntiCreditoriPerComune = new HashMap<String, String>();
	logger.debug("getPosizioni ({}) getComuniPerDettagliBollettazione", idTestataBollettazione);
	Map<Integer, Set<String>> comuniPerDettagliBollettazione = bollGestDettaglioService.getComuniPerDettagliBollettazione(idTestataBollettazione);
	logger.debug("getPosizioni ({}) getComuniPerDettagliBollettazione fatto, verifico la configurazione delle causali", idTestataBollettazione);
	verificaConfigurazioniCausali(anagraficaBollettazioneList, bollGestDettaglioService);
	logger.debug("getPosizioni ({}) getComuniPerDettagliBollettazione fatto, verifico la configurazione delle causali FATTA",
		idTestataBollettazione);
	String defaultCodiceComune = null;
	boolean isComuniassociati = comuniassociatiService.isComuniassociati(ORMHelper.getIdcomune());
	if (!isComuniassociati) {
	    defaultCodiceComune = comuniassociatiService.findByIdcomune(ORMHelper.getIdcomune()).get(0).getId().getCodicecomune();
	    logger.warn("getPosizioni ({}) isComuniassociati defaultCodiceComune {}", idTestataBollettazione, defaultCodiceComune);
	}
	for (AnagraficaBollettazione anagraficaBollettazione : this.anagraficaBollettazioneList) {
	    logger.debug("getPosizioni ({}) anagraficaBollettazione {}", idTestataBollettazione, anagraficaBollettazione.getId());
	    Anagrafe soggettoDebitore = this.anagrafeService.findById(new PkId(anagraficaBollettazione.getId()));
	    List<ImportoBean> importoBeans = null;
	    List<Integer> idRigheBollettazione = null;
	    Map<String, RigheBollettazioneImporti> m = new HashMap<String, RigheBollettazioneImporti>();
	    logger.debug("getPosizioni ({}) RigaBollettazione riga : anagraficaBollettazione.getRigheBollettazioneList() {} - prima del ciclo",
		    idTestataBollettazione, anagraficaBollettazione.getId());
	    if (!anagraficaBollettazione.isDettaglioPosizioneDebitoriaPresente()) {
		for (RigaBollettazione riga : anagraficaBollettazione.getRigheBollettazioneList()) {
		    if (BooleanUtils.isTrue(riga.getValidato()) && riga.getIdDettPosizioniDebitorie().isEmpty()) {
			logger.debug("getPosizioni ({})  getComunePerDettaglio(riga.getId(), comuniPerDettagliBollettazione) - {} - {} - {}",
				new Object[] { idTestataBollettazione, riga.getId(), isComuniassociati, defaultCodiceComune });
			String codiceComune = getComunePerDettaglio(riga.getId(), comuniPerDettagliBollettazione, isComuniassociati,
				defaultCodiceComune);
			logger.debug("getPosizioni ({})  getComunePerDettaglio(riga.getId(), comuniPerDettagliBollettazione) - {}",
				idTestataBollettazione, codiceComune);
			codiciComune.add(codiceComune);
			String cfEnteCreditore = mappaCfEntiCreditoriPerComune.get(codiceComune);
			if (StringUtils.isBlank(cfEnteCreditore)) {
			    VerticalizzazioneNodoPagamentiServiceImpl v = new VerticalizzazioneNodoPagamentiServiceImpl(verticalizzazioniService,
				    codiceComune);
			    cfEnteCreditore = v.arCodFiscEnteCreditore();
			    mappaCfEntiCreditoriPerComune.put(codiceComune, cfEnteCreditore);
			    popolaMappaConnettori(cfEnteCreditore, v.urlWs());
			}
			logger.debug(
				"getPosizioni ({})  {} - prima di this.bollGestDettaglioService.recuperaMappaturaNodoPagDaIdDettaglio(riga.getId())",
				idTestataBollettazione, anagraficaBollettazione.getId());
			String mappaturaClient = this.bollGestDettaglioService.recuperaMappaturaNodoPagDaIdDettaglio(riga.getId());
			logger.debug(
				"getPosizioni ({}) this.bollGestDettaglioService.recuperaMappaturaNodoPagDaIdDettaglio(riga.getId()) - {} -FATTO",
				idTestataBollettazione, mappaturaClient);
			ImportoBean bean = new ImportoBean(riga.getImportoTotale(), mappaturaClient);
			importoBeans = new ArrayList<ImportoBean>();
			idRigheBollettazione = new ArrayList<Integer>();
			importoBeans.add(bean);
			idRigheBollettazione.add(riga.getId());
			RigheBollettazioneImporti imp = m.get(cfEnteCreditore);
			if (imp == null) {
			    imp = new RigheBollettazioneImporti(importoBeans, idRigheBollettazione, codiceComune);
			} else {
			    imp.add(importoBeans, idRigheBollettazione);
			}
			m.put(cfEnteCreditore, imp);
		    }
		}
		logger.debug("getPosizioni ({}) Entry<String, RigheBollettazioneImporti> cfImporti : m.entrySet() {} - ciclo INIZIO ",
			idTestataBollettazione, anagraficaBollettazione.getId());
		for (Entry<String, RigheBollettazioneImporti> cfImporti : m.entrySet()) {
		    String codiceFiscaleEnteCreditore = cfImporti.getKey();
		    RigheBollettazioneImporti rbi = cfImporti.getValue();
		    if (anagraficaBollettazione.isDaRateizzare()) {
			posizioniDebitorie
				.addAll(this.creaPosizioniDebitorieRateizzate(codiceFiscaleEnteCreditore, soggettoDebitore, this.rateizzazione, rbi));
		    } else {
			if (!rbi.getImportoBeans().isEmpty()) {
			    Map<String, RigheBollettazioneImporti> mappaImporti = this.recuperaImportiPerCausale(rbi, codiceFiscaleEnteCreditore);
			    for (Map.Entry<String, RigheBollettazioneImporti> importoPerCausale : mappaImporti.entrySet()) {
				// Gestione arrotondamento. 
				ArrotondamentoFactory arrotondamentoFactory = new ArrotondamentoFactory();
				//Recupero il tipo di arrotondamento dalle configurazioni
				String ta = this.bollGestDettaglioService.findArrotondamentoByBollettazione(idTestataBollettazione);
				ArrotondamentoEnum tipoArrotondamento = ArrotondamentoEnum.daConfigurazione(ta);
				// In base alla configurazione settata applico il tipo di arrotondamento
				ArrotondamentoService service = arrotondamentoFactory.get(tipoArrotondamento);
				// restituisco l'importo arrotondato o meno
				List<ImportoBean> importoBeansTot = service.arrotondamento(importoPerCausale.getValue().getImportoBeans());
				PosizioneDebitoriaBollettazioneBean posDebitoria = new PosizioneDebitoriaBollettazioneBean(codiceFiscaleEnteCreditore,
					soggettoDebitore, importoBeansTot, Calendar.getInstance().getTime(), this.descrizioneBollettazione,
					importoPerCausale.getValue().getIdRigheBollettazione(), null, this.dataScadenzaBollettazione, null, 1,
					importoPerCausale.getValue().getCodiceComune());
				posizioniDebitorie.add(posDebitoria);
			    }
			}
		    }
		}
		logger.debug("getPosizioni ({}) Entry<String, RigheBollettazioneImporti> cfImporti : m.entrySet() {} - ciclo TERMINATO",
			idTestataBollettazione, anagraficaBollettazione.getId());
	    }
	}
	return new PosizioneDebitoriaPerNodoPagamentiBean(posizioniDebitorie, codiciComune);
    }

    private List<PosizioneDebitoriaBollettazioneBean> creaPosizioniDebitorieRateizzate(String codiceFiscaleEnteCreditore, Anagrafe soggettoDebitore,
	    Set<BollettazioneRataBean> pianoRateale, RigheBollettazioneImporti righe) {

	if (pianoRateale == null || pianoRateale.isEmpty()) {
	    throw new InvalidConfigurationException("E' stata richiesta una rateizzazione senza passare un piano di rateizzazione da applicare");
	}
	if (righe.getImportoBeans() == null || righe.getImportoBeans().isEmpty()) {
	    throw new InvalidConfigurationException("E' stata richiesta una rateizzazione senza passare gli importi da rateizzare");
	}
	List<PosizioneDebitoriaBollettazioneBean> rate = new ArrayList<PosizioneDebitoriaBollettazioneBean>();
	Map<String, RigheBollettazioneImporti> mappaImporti = this.recuperaImportiPerCausale(righe, codiceFiscaleEnteCreditore);
	if (mappaImporti.entrySet().size() != 1) {
	    throw new InvalidConfigurationException(
		    "Non è possibile chiedere la rateizzazione in fase di bollettazione se le causali coninvolte sono diverse tra di loro");
	}
	for (Map.Entry<String, RigheBollettazioneImporti> importoPerCausale : mappaImporti.entrySet()) {
	    BigDecimal importoTotale = BigDecimal.ZERO;
	    Map<String, List<ImportoBean>> importiPerMappatura = new HashMap<String, List<ImportoBean>>();
	    for (ImportoBean importo : importoPerCausale.getValue().getImportoBeans()) {
		List<ImportoBean> importi = importiPerMappatura.get(importo.getMappaturaNodoPag());
		if (importi == null) {
		    importi = new ArrayList<ImportoBean>();
		}
		importi.add(importo);
		importiPerMappatura.put(importo.getMappaturaNodoPag(), importi);
		importoTotale = importoTotale.add(importo.getImporto());
	    }
	    Date dataScadenza = this.dataScadenzaBollettazione;
	    BigDecimal importoRateizzato = BigDecimal.ZERO;
	    for (BollettazioneRataBean rata : pianoRateale) {
		if (importoTotale.compareTo(BigDecimal.valueOf(rata.getImportoMinimo())) >= 0
			&& (rata.getImportoMassimo() == null || importoTotale.compareTo(BigDecimal.valueOf(rata.getImportoMassimo())) <= 0)) {
		    //trovata la rataeizzazione da applicare
		    List<RataBean> rateizzazionelocal = new ArrayList<RataBean>();
		    for (int i = 1; i <= rata.getNumeroRate(); i++) {
			List<ImportoBean> importiRateizzati = new ArrayList<ImportoBean>();
			for (Entry<String, List<ImportoBean>> importiSet : importiPerMappatura.entrySet()) {
			    String mappaturaClient = importiSet.getKey();
			    BigDecimal importoTotaleconto = BigDecimal.ZERO;
			    for (ImportoBean importo : importiSet.getValue()) {
				importoTotaleconto = importoTotaleconto.add(importo.getImporto());
			    }
			    BigDecimal importoRata = importoTotaleconto.multiply(rata.getRipartizione()[i - 1]);
			    importoRata = importoRata.divide(BigDecimal.valueOf(100));
			    // Gestione arrotondamento. 
			    ArrotondamentoFactory arrotondamentoFactory = new ArrotondamentoFactory();
			    //Recupero il tipo di arrotondamento dalle configurazioni
			    String ta = this.bollGestDettaglioService.findArrotondamentoByBollettazione(idTestataBollettazione);
			    ArrotondamentoEnum tipoArrotondamento = ArrotondamentoEnum.daConfigurazione(ta);
			    // In base alla configurazione settata applico il tipo di arrotondamento
			    ArrotondamentoService service = arrotondamentoFactory.get(tipoArrotondamento);
			    // restituisco l'importo arrotondato o meno
			    importoRata = service.arrotondamento(importoRata);
			    if (importoRata.scale() > 2) {
				importoRata = importoRata.setScale(2, RoundingMode.HALF_UP); //arrotondo la cifra restituita a due decimali
			    }
			    importoRateizzato = importoRateizzato.add(importoRata);
			    importiRateizzati.add(new ImportoBean(importoRata, mappaturaClient));
			}
			dataScadenza = new DataScadenzaResolver(rata.getTipoScadenza(), dataScadenzaBollettazione, i - 1, rata.getScadenzePeriodo())
				.calcolaScadenza();
			rateizzazionelocal.add(new RataBean(null, importiRateizzati, this.descrizioneBollettazione, i, dataScadenza));
		    }
		    verificaImportiRate(importoTotale, importoRateizzato, rateizzazionelocal);
		    rate.add(PosizioneDebitoriaBollettazioneBean.conRate(codiceFiscaleEnteCreditore, rateizzazionelocal, soggettoDebitore,
			    Calendar.getInstance().getTime(), null, null, importoPerCausale.getValue().getCodiceComune(),
			    this.descrizioneBollettazione, importoPerCausale.getValue().getIdRigheBollettazione()));
		}
	    }
	}
	return rate;
    }

    private Map<String, RigheBollettazioneImporti> recuperaImportiPerCausale(RigheBollettazioneImporti righe, String codiceFiscaleEnteCreditore) {

	Map<String, RigheBollettazioneImporti> mappa = new TreeMap<String, RigheBollettazioneImporti>();
	for (int i = 0; i < righe.getIdRigheBollettazione().size(); i++) {
	    String mappaturaClient = this.bollGestDettaglioService.recuperaMappaturaNodoPagDaIdDettaglio(righe.getIdRigheBollettazione().get(i));
	    String codiceVersamento = getCodiceVersamentoFromMappatura(codiceFiscaleEnteCreditore, mappaturaClient);
	    if (!mappa.containsKey(codiceVersamento)) {
		List<ImportoBean> importoBeans = new ArrayList<ImportoBean>();
		importoBeans.add(righe.getImportoBeans().get(i));
		List<Integer> idRigheBollettazione = new ArrayList<Integer>();
		idRigheBollettazione.add(righe.getIdRigheBollettazione().get(i));
		mappa.put(codiceVersamento, new RigheBollettazioneImporti(importoBeans, idRigheBollettazione, righe.getCodiceComune()));
	    } else {
		RigheBollettazioneImporti riga = mappa.get(codiceVersamento);
		riga.getIdRigheBollettazione().add(righe.getIdRigheBollettazione().get(i));
		riga.getImportoBeans().add(righe.getImportoBeans().get(i));
	    }
	}
	return mappa;
    }

    private String getCodiceVersamentoFromMappatura(String codiceFiscaleEnteCreditore, String mappaturaClient) {

	ConnettoreType connettoreType = mappaConnettoriPerCf.get(codiceFiscaleEnteCreditore);
	return nodoPagamentiService.getCodiceVersamentoOrDefaultFromMappatura(connettoreType, mappaturaClient, "NON_VALIDO" + mappaturaClient);
    }

    private void popolaMappaConnettori(String cfEnteCreditore, String urlWs) {

	logger.debug("popolaMappaConnettori popolo la mappa dei connettori per cf ente creditore {} e url {}", cfEnteCreditore, urlWs);
	if (mappaConnettoriPerCf.get(cfEnteCreditore) == null) {
	    logger.debug("popolaMappaConnettori mappaConnettoriPerCf.get(cfEnteCreditore) == null cf ente creditore {} e url {} invoco il ws",
		    cfEnteCreditore, urlWs);
	    ConnettoreType mappaturaConnettore = nodoPagamentiService.getMappaturaConnettore(cfEnteCreditore, urlWs);
	    if (mappaturaConnettore == null) {
		logger.error("Mappatura connettore non trovata per il cf ente creditore {} e url {}", cfEnteCreditore, urlWs);
		throw new InvalidConfigurationException("Mappatura connettore non trovata per il cf ente creditore " + cfEnteCreditore);
	    }
	    mappaConnettoriPerCf.put(cfEnteCreditore, mappaturaConnettore);
	}
    }

    public static void main(String[] args) {

	Calendar c = Calendar.getInstance();
	c.set(Calendar.DATE, 31);
	c.set(Calendar.MONTH, 2);
	c.set(Calendar.YEAR, 2024);
	Date dataScadenzaBollettazione = c.getTime();
	for (int i = 1; i <= 4; i++) {
	    //	    Date dataScadenza = new DataScadenzaResolver(TipoScadenzaEnum.SCADENZA_PERIODICA_FISSA, dataScadenzaBollettazione, i - 1,
	    //		    "31/03;30/09;31/03;30/09").calcolaScadenza();
	    Date dataScadenza = new DataScadenzaResolver(TipoScadenzaEnum.SCADENZA_PERIODICA_FISSA, dataScadenzaBollettazione, i - 1,
		    "31/03;30/06;30/09;30/11").calcolaScadenza();
	    System.out.println(i + "=" + dataScadenza);
	}
	Set<String> codiciComune = new HashSet<String>();
	codiciComune.add(null);
	System.out.println(codiciComune);
	Map<String, String> m = new HashMap<String, String>();
	System.out.println(m.get(null));
    }
}
