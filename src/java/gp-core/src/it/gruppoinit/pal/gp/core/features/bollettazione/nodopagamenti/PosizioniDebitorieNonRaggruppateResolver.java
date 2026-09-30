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
import java.util.Set;

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
import it.gruppoinit.pal.gp.core.features.nodopagamenti.RataBean;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.VerticalizzazioneNodoPagamentiServiceImpl;
import it.gruppoinit.pal.gp.core.features.oneri.rateizzazione.configurazione.tipiscadenze.DataScadenzaResolver;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.BollGestDettaglioService;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;

public class PosizioniDebitorieNonRaggruppateResolver extends PosizioniDebitorieResolverBase implements IPosizioniDebitorieResolver {

    private Logger logger = LoggerFactory.getLogger(PosizioniDebitorieNonRaggruppateResolver.class);
    private List<AnagraficaBollettazione> anagraficaBollettazioneList;
    private VerticalizzazioniService verticalizzazioniService;
    private AnagrafeService anagrafeService;
    private BollGestDettaglioService bollGestDettaglioService;
    private String descrizioneBollettazione;
    private Date dataScadenzaBollettazione;
    private Set<BollettazioneRataBean> rateizzazione;
    private int idTestataBollettazione;
    private ComuniassociatiService comuniassociatiService;

    public PosizioniDebitorieNonRaggruppateResolver(VerticalizzazioniService verticalizzazioniService, AnagrafeService anagrafeService,
	    BollGestDettaglioService bollGestDettaglioService, DettaglioBollettazione boll, ComuniassociatiService comuniassociatiService) {

	this.verticalizzazioniService = verticalizzazioniService;
	this.anagraficaBollettazioneList = boll.getAnagraficaBollettazioneList();
	this.descrizioneBollettazione = boll.getDescrizione();
	this.dataScadenzaBollettazione = boll.getDataScadenza();
	this.rateizzazione = boll.getRateizzazione();
	this.bollGestDettaglioService = bollGestDettaglioService;
	this.anagrafeService = anagrafeService;
	this.idTestataBollettazione = boll.getId();
	this.comuniassociatiService = comuniassociatiService;
    }

    @Override
    public PosizioneDebitoriaPerNodoPagamentiBean getPosizioni() {

	List<PosizioneDebitoriaBollettazioneBean> posizioniDebitorie = new ArrayList<PosizioneDebitoriaBollettazioneBean>();
	Set<String> codiciComune = new HashSet<String>();
	Map<String, String> mappaCfEntiCreditoriPerComune = new HashMap<String, String>();
	Map<Integer, Set<String>> comuniPerDettagliBollettazione = bollGestDettaglioService.getComuniPerDettagliBollettazione(idTestataBollettazione);
	verificaConfigurazioniCausali(anagraficaBollettazioneList, bollGestDettaglioService);
	String defaultCodiceComune = null;
	boolean isComuniassociati = comuniassociatiService.isComuniassociati(ORMHelper.getIdcomune());
	if (!isComuniassociati) {
	    defaultCodiceComune = comuniassociatiService.findByIdcomune(ORMHelper.getIdcomune()).get(0).getId().getCodicecomune();
	    logger.warn("getPosizioni ({}) isComuniassociati defaultCodiceComune {}", idTestataBollettazione, defaultCodiceComune);
	}
	for (AnagraficaBollettazione anagraficaBollettazione : anagraficaBollettazioneList) {
	    Anagrafe soggettoDebitore = this.anagrafeService.findById(new PkId(anagraficaBollettazione.getId()));
	    for (RigaBollettazione riga : anagraficaBollettazione.getRigheBollettazioneList()) {
		if (BooleanUtils.isTrue(riga.getValidato()) && riga.getIdDettPosizioniDebitorie().isEmpty()) {
		    List<ImportoBean> importoBeans = new ArrayList<ImportoBean>();
		    List<Integer> idRigheBollettazione = new ArrayList<Integer>();
		    String mappaturaClient = this.bollGestDettaglioService.recuperaMappaturaNodoPagDaIdDettaglio(riga.getId());
		    // arrotondamento
		    ImportoBean importoBean = new ImportoBean(this.setArrotondamento(riga.getImportoTotale()), mappaturaClient);
		    importoBeans.add(importoBean);
		    idRigheBollettazione.add(riga.getId());
		    String codiceComune = getComunePerDettaglio(riga.getId(), comuniPerDettagliBollettazione, isComuniassociati, defaultCodiceComune);
		    codiciComune.add(codiceComune);
		    String cfEnteCreditore = mappaCfEntiCreditoriPerComune.get(codiceComune);
		    if (StringUtils.isBlank(cfEnteCreditore)) {
			cfEnteCreditore = new VerticalizzazioneNodoPagamentiServiceImpl(verticalizzazioniService, codiceComune)
				.arCodFiscEnteCreditore();
			mappaCfEntiCreditoriPerComune.put(codiceComune, cfEnteCreditore);
		    }
		    if (riga.isDaRateizzare()) {
			posizioniDebitorie.addAll(this.creaPosizioneDebitoriaRateizzata(cfEnteCreditore, soggettoDebitore, this.rateizzazione, riga,
				importoBean, codiceComune, idRigheBollettazione));
		    } else {
			PosizioneDebitoriaBollettazioneBean posDebitoria = new PosizioneDebitoriaBollettazioneBean(cfEnteCreditore, soggettoDebitore,
				importoBeans, Calendar.getInstance().getTime(), this.descrizioneBollettazione, idRigheBollettazione, null,
				this.dataScadenzaBollettazione, null, 1, codiceComune);
			posizioniDebitorie.add(posDebitoria);
		    }
		}
	    }
	}
	return new PosizioneDebitoriaPerNodoPagamentiBean(posizioniDebitorie, codiciComune);
    }

    private List<PosizioneDebitoriaBollettazioneBean> creaPosizioneDebitoriaRateizzata(String codiceFiscaleEnteCreditore, Anagrafe soggettoDebitore,
	    Set<BollettazioneRataBean> pianoRateale, RigaBollettazione riga, ImportoBean importoBean, String codiceComune,
	    List<Integer> idRigheBollettazione) {

	if (pianoRateale == null || pianoRateale.isEmpty()) {
	    throw new InvalidConfigurationException("E' stata richiesta una rateizzazione senza passare un piano di rateizzazione da applicare");
	}
	if (riga == null) {
	    throw new InvalidConfigurationException("E' stata richiesta una rateizzazione senza passare gli importi da rateizzare");
	}
	List<PosizioneDebitoriaBollettazioneBean> rate = new ArrayList<PosizioneDebitoriaBollettazioneBean>();
	BigDecimal importoTotale = importoBean.getImporto();
	BigDecimal importoRateizzato = BigDecimal.ZERO;
	for (BollettazioneRataBean rata : pianoRateale) {
	    if (importoTotale.compareTo(BigDecimal.valueOf(rata.getImportoMinimo())) >= 0
		    && (rata.getImportoMassimo() == null || importoTotale.compareTo(BigDecimal.valueOf(rata.getImportoMassimo())) <= 0)) {
		//trovata la rataeizzazione da applicare
		List<RataBean> rateizzazioneLocal = new ArrayList<RataBean>();
		for (int i = 1; i <= rata.getNumeroRate(); i++) {
		    BigDecimal importoRata = importoTotale.multiply(rata.getRipartizione()[i - 1]);
		    importoRata = importoRata.divide(BigDecimal.valueOf(100));
		    // arrotondamento
		    importoRata = setArrotondamento(importoRata);
		    importoRateizzato = importoRateizzato.add(importoRata);
		    ImportoBean importoR = new ImportoBean(importoRata, importoBean.getMappaturaNodoPag());
		    Date dataScadenza = new DataScadenzaResolver(rata.getTipoScadenza(), dataScadenzaBollettazione, i - 1, rata.getScadenzePeriodo())
			    .calcolaScadenza();
		    rateizzazioneLocal.add(RataBean.fromImportoSingolo(null, importoR, this.descrizioneBollettazione, i, dataScadenza));
		}
		verificaImportiRate(importoTotale, importoRateizzato, rateizzazioneLocal);
		rate.add(PosizioneDebitoriaBollettazioneBean.conRate(codiceFiscaleEnteCreditore, rateizzazioneLocal, soggettoDebitore,
			Calendar.getInstance().getTime(), null, null, codiceComune, this.descrizioneBollettazione, idRigheBollettazione));
	    }
	}
	return rate;
    }

    private BigDecimal setArrotondamento(BigDecimal importoRata) {

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
	return importoRata;
    }
}
