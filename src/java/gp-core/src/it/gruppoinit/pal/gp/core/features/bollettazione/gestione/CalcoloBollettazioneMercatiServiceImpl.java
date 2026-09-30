package it.gruppoinit.pal.gp.core.features.bollettazione.gestione;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.helper.PeriodiEnum;
import it.gruppoinit.pal.gp.core.dao.helper.TitolaritaPagamentiEnum;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.BollCfgConti;
import it.gruppoinit.pal.gp.core.domain.BollCfgMercati;
import it.gruppoinit.pal.gp.core.domain.BollCfgTipo;
import it.gruppoinit.pal.gp.core.domain.BollGestTestata;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiFormuleCalcolo;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.MercatiFormuleCalcoloContestoEnum;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.features.bollettazione.BollettazioneNonConsentitaException;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.dao.BollGestMercatiDettDAO;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.dao.BollettazioneDAO;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.model.RiepilogoGiornateNonInizializzateBean;
import it.gruppoinit.pal.gp.core.features.bollettazione.mercati.RegoleAmmissioneMercati;
import it.gruppoinit.pal.gp.core.features.bollettazione.mercati.RegoleAmmissioneMercatiFactory;
import it.gruppoinit.pal.gp.core.features.bollettazione.mercati.RegoleAmmissioneMercatiParams;
import it.gruppoinit.pal.gp.core.features.bollettazione.mercati.calcolo.elaborazione.ElaborazioneService;
import it.gruppoinit.pal.gp.core.features.contabilita.AliquotaIVA;
import it.gruppoinit.pal.gp.core.features.contabilita.ImportoIvato;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.IRecuperaInformazioniGiornataService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.InfoGiornataPresenzaBean;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.MercatiFormuleResolver;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.segnaposto.SegnapostoFormuleBuilderRequest;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.VerticalizzazioneNodoPagamentiServiceImpl;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.soggettopendenza.SoggettiPendenzaEnum;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.soggettopendenza.SoggettoPendenzaService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.MercatiService;
import it.gruppoinit.pal.gp.core.service.MercatiUsoService;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Service
public class CalcoloBollettazioneMercatiServiceImpl extends CalcoloBollettazioneServiceBase implements CalcoloBollettazioneMercatiService {

    private static final String FLUSH_CLEAR = "flush/clear";
    private BollettazioneDAO bollettazioneDAO;
    private BollGestMercatiDettDAO bollGestMercatiDettDAO;
    private IRecuperaInformazioniGiornataService recuperaInformazioniGiornataService;
    private MercatiService mercatiService;
    private MercatiUsoService mercatiUsoService;
    private SoggettoPendenzaService pendenzaService;
    private ElaborazioneService elaborazioneService;

    @Autowired
    public CalcoloBollettazioneMercatiServiceImpl(BollettazioneDAO bollettazioneDAO,
	    IRecuperaInformazioniGiornataService recuperaInformazioniGiornataService, MercatiService mercatiService,
	    VerticalizzazioniService verticalizzazioniService, BollGestMercatiDettDAO bollGestMercatiDettDAO, MercatiUsoService mercatiUsoService,
	    SoggettoPendenzaService pendenzaService, ElaborazioneService elaborazioneService) {

	super(verticalizzazioniService);
	this.bollettazioneDAO = bollettazioneDAO;
	this.recuperaInformazioniGiornataService = recuperaInformazioniGiornataService;
	this.mercatiService = mercatiService;
	this.bollGestMercatiDettDAO = bollGestMercatiDettDAO;
	this.mercatiUsoService = mercatiUsoService;
	this.pendenzaService = pendenzaService;
	this.elaborazioneService = elaborazioneService;
    }

    public class RigaDettaglioCalcoloKey {

	private Integer idAnagrafe;
	private Integer idConto;
	private Integer idPosteggio;

	public RigaDettaglioCalcoloKey(Integer idAnagrafe, Integer idConto, Integer idPosteggio) {

	    this.idAnagrafe = idAnagrafe;
	    this.idConto = idConto;
	    this.idPosteggio = idPosteggio;
	}

	public Integer getIdAnagrafe() {

	    return idAnagrafe;
	}

	public Integer getIdConto() {

	    return idConto;
	}

	public Integer getIdPosteggio() {

	    return idPosteggio;
	}

	@Override
	public int hashCode() {

	    int result = 17;
	    result = 37 * result + (this.getIdAnagrafe() == null ? 0 : this.getIdAnagrafe());
	    result = 37 * result + (this.getIdConto() == null ? 0 : this.getIdConto());
	    result = 37 * result + (this.getIdPosteggio() == null ? 0 : this.getIdPosteggio());
	    return result;
	}

	@Override
	public boolean equals(Object obj) {

	    if (this == obj) {
		return true;
	    }
	    if (obj == null) {
		return false;
	    }
	    if (getClass() != obj.getClass()) {
		return false;
	    }
	    RigaDettaglioCalcoloKey castOther = (RigaDettaglioCalcoloKey) obj;
	    return (((this.getIdAnagrafe() == castOther.getIdAnagrafe())
		    || (this.getIdAnagrafe() != null && castOther.getIdAnagrafe() != null && this.getIdAnagrafe().equals(castOther.getIdAnagrafe()))) //
		    && ((this.getIdConto() == castOther.getIdConto())
			    || (this.getIdConto() != null && castOther.getIdConto() != null && this.getIdConto().equals(castOther.getIdConto()))) //
	    //
	    );
	}
    }

    @Override
    public EsitoCalcoloBollettazione calcola(RichiestaCalcoloBollettazioneMercato richiesta, Boolean conguaglio) {

	//estrapolo la lista di tutti i dettagli interessati dalla bollettazione
	validaGiornateMercatoInizializzateAllaDataOdierna(richiesta);
	BollettazioneAuditLogger.logger.debug("calcola prima di findByFiltriBollettazioneMercato");
	//non serve passare il guid in quanto non deve inserire nulla
	List<RigaDettaglioCalcoloMercati> result = bollettazioneDAO.findByFiltriBollettazioneMercato(null, richiesta.getTitolarita(),
		richiesta.getFiltriMercati(), richiesta.getIntervalloDate(), conguaglio);
	BollettazioneAuditLogger.logger.debug("calcola dopo aver cercato findByFiltriBollettazioneMercato");
	//creo la mappa che contiene le righe da passare alla bollettazione in quanto devono essere accorpate per conto
	HashMap<RigaDettaglioCalcoloKey, RigaDettaglioBollettazioneMercatiDett> mapRighe = new HashMap<RigaDettaglioCalcoloKey, RigaDettaglioBollettazioneMercatiDett>(
		0);
	//creo la lista che viene ritornata dalla funzione
	List<RigaDettaglioBollettazioneMercatiDett> righe = new ArrayList<RigaDettaglioBollettazioneMercatiDett>();
	//creo la mappa che contiene i dettagli di ogni riga di bollettazione
	Map<String, List<BollGestMercatiDettHelper>> mapPosteggiUsoPerAut = new HashMap<String, List<BollGestMercatiDettHelper>>();
	//ciclo la query
	if (BollettazioneAuditLogger.logger.isDebugEnabled()) {
	    BollettazioneAuditLogger.logger.debug("righe trovate {}", result.size());
	}
	int righeProcessate = 0;
	int numIterazioni = 1;
	Set<String> giornataPerPosteggioContoConteggiata = new HashSet<String>();
	for (RigaDettaglioCalcoloMercati rigaDettaglioCalcoloMercati : result) {
	    BollettazioneAuditLogger.logger.info("processo nuova riga idPosteggio: {}, dataGiornata: {}",
		    rigaDettaglioCalcoloMercati.getIdPosteggio(), rigaDettaglioCalcoloMercati.getDataGiornata());
	    //recupero il codice dell'anagrafica di riferimento
	    Integer codiceAnagrafe = rigaDettaglioCalcoloMercati.getIdAnagrafe();
	    BollettazioneAuditLogger.logger.debug("recuperate le info della concessione {}",
		    rigaDettaglioCalcoloMercati.getIdAutorizzazioneConcessione());
	    //recupero la giornata di riferimento
	    BollettazioneAuditLogger.logger.debug("recuperata la giornata {}", rigaDettaglioCalcoloMercati.getIdGiornata());
	    InfoGiornataPresenzaBean info = InfoGiornataPresenzaBean.fromRigaDettaglioCalcoloMercati(rigaDettaglioCalcoloMercati);
	    //recupero i costi attivi per concessione e giornata suddivisi per conto
	    Map<Integer, ContoImportoTotaleHelper> costiAttivi = this.recuperaCostiGiornataPerTipoConto(rigaDettaglioCalcoloMercati.getIdUso(),
		    rigaDettaglioCalcoloMercati.getDataGiornata(), rigaDettaglioCalcoloMercati, info, richiesta.getFiltriConti());
	    BollettazioneAuditLogger.logger.debug("recuperati i costi {},{}", rigaDettaglioCalcoloMercati.getIdGiornata(),
		    rigaDettaglioCalcoloMercati.getIdAutorizzazioneConcessione());
	    //creo la lista che contiene i dettagli per posteggio/concessione
	    List<BollGestMercatiDettHelper> listaPosteggiPerAut = mapPosteggiUsoPerAut
		    .get(rigaDettaglioCalcoloMercati.getChiaveRiferimentoAutorizzazione());
	    if (listaPosteggiPerAut == null) {
		listaPosteggiPerAut = new ArrayList<BollGestMercatiDettHelper>();
	    }
	    for (Integer idConto : costiAttivi.keySet()) {
		String chiaveContoPosteggioGiornata = getChiaveContoPosteggioGiornata(rigaDettaglioCalcoloMercati.getIdPosteggio(),
			rigaDettaglioCalcoloMercati.getDataGiornata(), idConto);
		BollettazioneAuditLogger.logger.debug("inizio calcolo del conto {}", idConto);
		ContoImportoTotaleHelper costoConto = costiAttivi.get(idConto);
		//estrapolo il totale per conto, giornata, concessione
		BigDecimal importoTotaleConcessioneGiornata = costoConto.getImportoIvato().getImportoConIVA();
		//aggiungo rigaDettaglioCalcoloMercati a questa mappa
		listaPosteggiPerAut.add(new BollGestMercatiDettHelper(idConto, rigaDettaglioCalcoloMercati.getIdPosteggio(),
			rigaDettaglioCalcoloMercati.getIdUso(), rigaDettaglioCalcoloMercati.getIdGiornata(), importoTotaleConcessioneGiornata));
		mapPosteggiUsoPerAut.put(rigaDettaglioCalcoloMercati.getChiaveRiferimentoAutorizzazione(), listaPosteggiPerAut);
		RigaDettaglioCalcoloKey key = new RigaDettaglioCalcoloKey(codiceAnagrafe, idConto, rigaDettaglioCalcoloMercati.getIdPosteggio());
		RigaDettaglioBollettazioneMercatiDett riga = null;
		BigDecimal importoSenzaIVA = costoConto.getImportoIvato().getImportoSenzaIVA();
		Integer iva = costoConto.getImportoIvato().getIva();
		BigDecimal importoTotale = costoConto.getImportoIvato().getImportoConIVA();
		if (mapRighe.containsKey(key)) {
		    riga = mapRighe.get(key);
		    if (!giornataPerPosteggioContoConteggiata.contains(chiaveContoPosteggioGiornata)) {
			riga.setImportoSenzaIVA(importoSenzaIVA.add(mapRighe.get(key).getImportoSenzaIVA()));
			riga.setImportoTotale(importoTotale.add(mapRighe.get(key).getImportoTotale()));
		    }
		} else {
		    riga = new RigaDettaglioBollettazioneMercatiDett();
		    riga.setIdAnagrafe(codiceAnagrafe);
		    riga.setDescrizione(
			    new DescrizioneRigaBollettazioneMercato(this.bollettazioneDAO, idConto, rigaDettaglioCalcoloMercati.getIdPosteggio()));
		    riga.setIdConto(idConto);
		    riga.setImportoSenzaIVA(importoSenzaIVA);
		    riga.setIva(iva);
		    riga.setImportoTotale(importoTotale);
		    riga.setIdRiferimento(rigaDettaglioCalcoloMercati.getIdRiferimento());
		    riga.setSubentro(rigaDettaglioCalcoloMercati.isSubentro());
		    riga.setIdAutorizzazioneConcessione(rigaDettaglioCalcoloMercati.getIdAutorizzazioneConcessione());
		    riga.setIdUso(rigaDettaglioCalcoloMercati.getIdUso());
		    riga.setIdPosteggio(rigaDettaglioCalcoloMercati.getIdPosteggio());
		}
		giornataPerPosteggioContoConteggiata.add(chiaveContoPosteggioGiornata);
		mapRighe.put(key, riga);
	    }
	    righeProcessate++;
	    BollettazioneAuditLogger.logger.debug("riga {} processata", righeProcessate);
	    if (righeProcessate == 1000) {
		BollettazioneAuditLogger.logger.debug(FLUSH_CLEAR);
		this.bollettazioneDAO.flush();
		this.bollettazioneDAO.clear();
		righeProcessate = 0;
		BollettazioneAuditLogger.logger.debug("iterazioni {} processata", numIterazioni++);
	    }
	}
	BollettazioneAuditLogger.logger.debug(FLUSH_CLEAR);
	this.bollettazioneDAO.flush();
	this.bollettazioneDAO.clear();
	BollettazioneAuditLogger.logger.debug("mapPosteggiUsoPerAut.size() {}", mapPosteggiUsoPerAut.size());
	for (Map.Entry<RigaDettaglioCalcoloKey, RigaDettaglioBollettazioneMercatiDett> entry : mapRighe.entrySet()) {
	    RigaDettaglioBollettazioneMercatiDett r = entry.getValue();
	    List<BollGestMercatiDettHelper> lista = mapPosteggiUsoPerAut.get(r.getChiaveRiferimentoAutorizzazione());
	    BollettazioneAuditLogger.logger.debug("RigaDettaglioBollettazioneMercatiDett.ChiaveRiferimentoAutorizzazione() {},{}-{}",
		    new Object[] { r.getChiaveRiferimentoAutorizzazione(), lista != null, lista.isEmpty() });
	    for (BollGestMercatiDettHelper dettaglio : lista) {
		if (dettaglio.getIdConto().equals(r.getIdConto())) {
		    r.getDettagliMercati().add(dettaglio);
		    BollettazioneAuditLogger.logger.debug("RigaDettaglioBollettazioneMercatiDettaggiunto dettaglio {}",
			    new Object[] { r.getChiaveRiferimentoAutorizzazione() });
		}
	    }
	    if (r.getImportoTotale().compareTo(BigDecimal.ZERO) != 0) {
		righe.add(r);
	    }
	}
	BollettazioneAuditLogger.logger.debug("processamento terminato righe {}", righe.size());
	return new EsitoCalcoloBollettazione(richiesta.getIntervalloDate(), righe);
    }

    private void validaGiornateMercatoInizializzateAllaDataOdierna(RichiestaCalcoloBollettazioneMercato richiesta) {

	IntervalloDate intervalloDate = IntervalloDate.fromIntervalloDate(richiesta.getIntervalloDate()); // lo clono per non cambiare le proprietà di rifeimento dell'oggetto che mi servirà per le query di bollettazione
	Date oggi = Calendar.getInstance().getTime();
	if (Utilities.compareDates(oggi, intervalloDate.getDataFine()) < 0) {
	    // se data inizio è 01/09/2023 e data fine è 31/10/2023 e faccio girare la bollettazione il 10/10/2023
	    // devo verificare che siano inizializzate le giornate dal 01/09/2023 al 10/10/2023 
	    // se faccio girare la bollettazione il 15/11/2023
	    // devo verificare che siano inizializzate le giornate dal 01/09/2023 al 31/10/2023 (il periodo dela bollettazione)
	    intervalloDate.setDataFine(Utilities.dateWithoutTime(oggi));
	}
	List<RiepilogoGiornateNonInizializzateBean> riepilogo = bollGestMercatiDettDAO.trovaGiornateNonInizializzateNelPeriodo(intervalloDate,
		richiesta.getFiltriMercati());
	if (!riepilogo.isEmpty()) {
	    BollettazioneAuditLogger.logger.error("validaGiornateMercatoInizializzateAllaDataOdierna trovate giornate non inizializzate");
	    throw new InvalidConfigurationException(getMessaggioErrore(riepilogo));
	}
    }

    private String getMessaggioErrore(List<RiepilogoGiornateNonInizializzateBean> riepilogo) {

	Map<String, List<Date>> giornatePerMercato = new HashMap<String, List<Date>>();
	for (RiepilogoGiornateNonInizializzateBean r : riepilogo) {
	    String mercato = r.getMercato();
	    List<Date> list = giornatePerMercato.get(mercato);
	    if (list == null) {
		list = new ArrayList<Date>();
	    }
	    list.add(r.getDataregistrazione());
	    giornatePerMercato.put(mercato, list);
	}
	StringBuilder errore = new StringBuilder();
	errore.append("Non e' possibile procedere in quanto i seguenti mercati non hanno le giornate inizializzate: ");
	for (Entry<String, List<Date>> rg : giornatePerMercato.entrySet()) {
	    errore.append("<br />");
	    errore.append(rg.getKey());
	    errore.append("<ul>");
	    for (Date d : rg.getValue()) {
		errore.append("<li>").append(Utilities.formatDate(d, false));
		errore.append("</li>");
	    }
	    errore.append("</ul>");
	}
	return errore.toString();
    }

    private String getChiaveContoPosteggioGiornata(Integer idPosteggio, Date dataGiornata, Integer idConto) {

	return idPosteggio.intValue() + "-" + idConto.intValue() + "-" + Utilities.formatDate(dataGiornata, "yyyyMMdd");
    }

    private Map<Integer, ContoImportoTotaleHelper> recuperaCostiGiornataPerTipoConto(Integer idUso, Date dataGiornata,
	    RigaDettaglioCalcoloMercati rigaDettaglioCalcoloMercati, InfoGiornataPresenzaBean infoPresenza, List<Integer> filtriConti) {

	BollettazioneAuditLogger.logger.debug("recuperaCostiGiornataPerTipoConto entro nel metodo");
	Map<Integer, ContoImportoTotaleHelper> map = new HashMap<Integer, ContoImportoTotaleHelper>();
	MercatiUso uso = mercatiUsoService.findById(new PkId(idUso));
	//recupero le formule attive alla giornata da calcolare
	Set<MercatiFormuleCalcolo> formule = uso.getFormuleAttiveByContesto(dataGiornata, MercatiFormuleCalcoloContestoEnum.BOLLETTAZIONE);
	//ciclo le formule attive
	BollettazioneAuditLogger.logger.debug("Formuleattive Recuperate");
	for (MercatiFormuleCalcolo formula : formule) {
	    //estrapolo l'importo per quella giornata e quella formula
	    SegnapostoFormuleBuilderRequest request = new SegnapostoFormuleBuilderRequest(formula, rigaDettaglioCalcoloMercati);
	    ContoImportoTotaleHelper costo = new MercatiFormuleResolver(request, recuperaInformazioniGiornataService, dataGiornata, infoPresenza)
		    .risolvi();
	    BollettazioneAuditLogger.logger.debug("costo recuperato");
	    boolean aggiungiConto = verificaAggiungiConto(costo.getIdConto(), filtriConti, formula);
	    //verifico se ho già in mappa quel conto altrimenti lo creo nuovo
	    if (aggiungiConto) {
		if (!map.containsKey(costo.getIdConto())) {
		    BollettazioneAuditLogger.logger.debug("non trovato nella mappa");
		    map.put(costo.getIdConto(), costo);
		} else {
		    BollettazioneAuditLogger.logger.debug("trovato nella mappa");
		    //estrapolo dalla mappa il costo attuale per quel conto
		    ContoImportoTotaleHelper costoAttuale = map.get(costo.getIdConto());
		    //calcolo l'importo da aggiungere ( sia in termini di importoSenzaIva che importoConIva )
		    ImportoIvato importoDaAggiungere = new AliquotaIVA(costoAttuale.getImportoIvato().getIva())
			    .applica(costo.getImportoIvato().getImportoSenzaIVA());
		    //calcolo i nuovi importi sommando gli importi attuali con quelli calcolati
		    BigDecimal nuovoImportoSenzaIVA = costoAttuale.getImportoIvato().getImportoSenzaIVA()
			    .add(importoDaAggiungere.getImportoSenzaIVA());
		    BigDecimal nuovoImportoConIVA = costoAttuale.getImportoIvato().getImportoConIVA().add(importoDaAggiungere.getImportoConIVA());
		    //aggiorno gli importi nel costo attuale
		    costoAttuale.getImportoIvato().setImportoSenzaIVA(nuovoImportoSenzaIVA);
		    costoAttuale.getImportoIvato().setImportoConIVA(nuovoImportoConIVA);
		    //aggiorno il riferimento al costo nella mappa
		    map.put(costo.getIdConto(), costoAttuale);
		    BollettazioneAuditLogger.logger.debug("aggiunto alla mappa");
		}
	    }
	}
	if (map.isEmpty()) {
	    BollettazioneAuditLogger.logger.error(
		    "recuperaCostiGiornataPerTipoConto Non sono stati trovati conti per la RigaDettaglioCalcoloMercati {}, InfoGiornataPresenzaBean {}",
		    rigaDettaglioCalcoloMercati, infoPresenza);
	    throw new InvalidConfigurationException(
		    "recuperaCostiGiornataPerTipoConto Non sono stati trovati conti per la RigaDettaglioCalcoloMercati " +
			    rigaDettaglioCalcoloMercati +
			    ", InfoGiornataPresenzaBean " +
			    infoPresenza);
	}
	BollettazioneAuditLogger.logger.debug("recuperaCostiGiornataPerTipoConto esco dal metodo");
	return map;
    }

    public boolean verificaAggiungiConto(Integer idConto, List<Integer> filtriConti, MercatiFormuleCalcolo formula) {

	if (formula.getContesto() == null) {
	    throw new IllegalArgumentException("La formula non ha definito un contesto");
	}
	if (filtriConti.isEmpty()) {
	    return formula.getContesto().equals(MercatiFormuleCalcoloContestoEnum.BOLLETTAZIONE);
	}
	return filtriConti.contains(idConto);
    }

    private RegoleAmmissioneMercatiParams getRegoleParams(IntervalloDate intervallo) {

	Date dataInizio = intervallo.getDataInizio();
	Date dataFine = intervallo.getDataFine();
	List<MercatiFormuleCalcolo> formule = bollettazioneDAO.findFormuleByIntervallo(dataInizio, dataFine);
	return new RegoleAmmissioneMercatiParams(dataInizio, dataFine, formule);
    }

    @Override
    public Integer creaBollettazione(CreazioneBollTestata creazioneBollTestata, Integer codiceResponsabile) {

	//1. Non far avviare la bollettazione dei mercati se i mercati non sono configurati
	BollCfgTipo bollCfgTipo = bollettazioneDAO.getByIdForBollettazione(BollCfgTipo.class, creazioneBollTestata.getBollCfgTipoId());
	List<Integer> filtriMercati = getFiltriMercati(bollCfgTipo);
	if (filtriMercati.isEmpty()) {
	    throw new InvalidConfigurationException(
		    "La bollettazione non è stata configurata correttamente. Non sono stati specificati mercati di riferimento");
	}
	//2. Controllo se le date si sovrappongono per diverse bollettazioni
	if (bollettazioneDAO.esisteBollettazioneStessoPeriodo(creazioneBollTestata)) {
	    throw new InvalidConfigurationException("E' già presente una bollettazione di questa tipologia nello stesso periodo!");
	}
	//3. Verifica le regole di ammissione: formule attive e conti attivi
	RegoleAmmissioneMercati regole = new RegoleAmmissioneMercatiFactory(this.getRegoleParams(creazioneBollTestata.getIntervalloDate()))
		.getRegoleAmmissione();
	try {
	    regole.valida();
	} catch (BollettazioneNonConsentitaException e) {
	    throw new RuntimeException(e);
	}
	//4. Verifica la configurazione per il nodo dei pagamenti
	this.validaConfigurazioneBollettazione(getListaComuni(filtriMercati));
	BollettazioneAuditLogger.logger.debug("creaBollettazione valida Configurazione OK");
	//5. Estrapola la titolarità dei pagamenti
	PeriodiEnum tipologiaPeriodo = PeriodiEnum.valueOf(bollCfgTipo.getPeriodo().toUpperCase());
	TitolaritaPagamentiEnum titolarita = TitolaritaPagamentiEnum.PRESENZE_EFFETTIVE;
	if (StringUtils.isNotEmpty(bollCfgTipo.getTitolaritaPagamenti())) {
	    titolarita = TitolaritaPagamentiEnum.valueOf(bollCfgTipo.getTitolaritaPagamenti());
	}
	//6. Preparazione della request per avviare il calcolo
	RichiestaCalcoloBollettazioneMercato richiesta = new RichiestaCalcoloBollettazioneMercato(tipologiaPeriodo, titolarita, codiceResponsabile,
		creazioneBollTestata.getDescrizione(), creazioneBollTestata.getIntervalloDate(), getFiltriConti(bollCfgTipo));
	richiesta.getFiltriMercati().addAll(filtriMercati);
	//7. Invoco il calcolo
	boolean isAzienda = isAzienda(richiesta);
	boolean conguaglio = false;
	return this.elaborazioneService.elabora(richiesta, titolarita, isAzienda, conguaglio, creazioneBollTestata, bollCfgTipo);
	/*
	BollettazioneAuditLogger.logger.error("creaBollettazione end null?????");
	return null;
	
	List<RigaDettaglioBollettazioneMercatiDett> righe = new ArrayList<RigaDettaglioBollettazioneMercatiDett>(0);
	EsitoCalcoloBollettazione esito = this.calcola(richiesta, false);
	if (esito != null && esito.getRighe() != null && esito.getRighe().size() > 0) {
	    righe.addAll((List<RigaDettaglioBollettazioneMercatiDett>) esito.getRighe());
	}
	BollettazioneAuditLogger.logger.debug("creaBollettazione righe trovate {}", righe.size());
	//8. Eventuale conguaglio
	if (bollCfgTipo.getFlagConguaglio()) {
	    EsitoCalcoloBollettazione conguaglio = this.calcolaConguaglio(creazioneBollTestata.getBollCfgTipoId(),
		    richiesta.getIntervalloDate().getDataInizio(), codiceResponsabile);
	    if (conguaglio != null && conguaglio.getRighe() != null && conguaglio.getRighe().size() > 0) {
		righe.addAll((List<RigaDettaglioBollettazioneMercatiDett>) conguaglio.getRighe());
	    }
	}
	//9. Verifica esito
	BollettazioneAuditLogger.logger.debug("creaBollettazione conguaglio trovate {}", righe.size());
	if (esito == null || righe.size() == 0) {
	    BollettazioneAuditLogger.logger.error("creaBollettazione end null?????");
	    return null;
	}
	//10. Salvataggio dati
	//10.1. Invoco il salvataggio della testata
	BollGestTestata testata = new BollGestTestata(creazioneBollTestata, bollCfgTipo);
	this.bollettazioneDAO.save(testata);
	BollettazioneAuditLogger.logger.debug("creaBollettazione testata salvata ");
	//10.2. invoco il salvataggio dei filtri
	List<BollGestFiltri> filtri = this.getFiltri(testata);
	this.bollettazioneDAO.save(filtri);
	BollettazioneAuditLogger.logger.debug("creaBollettazione filtri salvati");
	//10.3. invoco il salvataggio del dettaglio
	int righeProcessate = 0;
	int numIterazioni = 1;
	for (RigaDettaglioBollettazioneMercatiDett riga : righe) {
	    //10.3.1 BollGestDettaglio
	    if (riga.getIdAnagrafe() == null) {
		String messaggio = "Non è stato possibile trovare l'anagrafe per il riferimento " +
			riga.getIdRiferimento() +
			", descrizione " +
			riga.getDescrizione();
		BollettazioneAuditLogger.logger.error(messaggio);
		throw new InvalidConfigurationException(messaggio);
	    }
	    BollGestDettaglio dettaglio = new BollGestDettaglio();
	    Anagrafe anagrafe = new Anagrafe();
	    anagrafe.setId(new PkId(riga.getIdAnagrafe()));
	    dettaglio.setAnagrafe(anagrafe);
	    dettaglio.setBollGestTestata(testata);
	    Conti conto = new Conti();
	    conto.setId(new PkId(riga.getIdConto()));
	    dettaglio.setConti(conto);
	    dettaglio.setDescrizione(riga.getDescrizione());
	    dettaglio.setFlagEliminata(Boolean.FALSE);
	    dettaglio.setFlagInsAuto(Boolean.TRUE);
	    dettaglio.setFlagConguaglio(riga.getConguaglio());
	    dettaglio.setFlagRettificata(Boolean.FALSE);
	    dettaglio.setFlagValidata(Boolean.FALSE);
	    dettaglio.setImportoTotale(riga.getImportoTotale());
	    dettaglio.setImportoSenzaIva(riga.getImportoSenzaIVA());
	    dettaglio.setIva(riga.getIva());
	    dettaglio.setDataScadenza(testata.getDataScadenza());
	    this.bollettazioneDAO.save(dettaglio);
	    //10.3.2 BollGestDettAutorizz
	    BollGestDettAutorizz bollAut = new BollGestDettAutorizz();
	    bollAut.setBollGestDettaglio(dettaglio);
	    Autorizzazioni aut = bollettazioneDAO.getById(Autorizzazioni.class, riga.getIdRiferimento());
	    bollAut.setAutorizzazione(aut);
	    if (riga.getIdAutorizzazioniSubentri() != null) {
		AutorizzazioniSubentri sub = autorizzazioniSubentriService.findById(new PkId(riga.getIdAutorizzazioniSubentri()));
		bollAut.setSubentro(sub);
	    }
	    bollAut.setBollGestTestata(testata);
	    this.bollettazioneDAO.save(bollAut);
	    //10.3.3 BollGestMercatiDett
	    List<BollGestMercatiDettHelper> dettagliMercati = riga.getDettagliMercati();
	    for (BollGestMercatiDettHelper dm : dettagliMercati) {
		BollGestMercatiDett e = new BollGestMercatiDett();
		e.setBollGestDettAutorizz(bollAut);
		MercatiUso uso = this.bollettazioneDAO.getById(MercatiUso.class, dm.getIdMercatoUso());
		e.setMercatoUso(uso);
		MercatiD posteggio = this.bollettazioneDAO.getById(MercatiD.class, dm.getIdPosteggio());
		e.setPosteggio(posteggio);
		MercatipresenzeT giornata = this.bollettazioneDAO.getById(MercatipresenzeT.class, dm.getIdGiornata());
		e.setMercatiPresenzeT(giornata);
		e.setImportoTotale(dm.getImportoTotale());
		e.setBollGestTestata(testata);
		this.bollettazioneDAO.save(e);
	    }
	    BollettazioneAuditLogger.logger.debug("creaBollettazione riga salvata");
	    righeProcessate++;
	    if (righeProcessate == 1000) {
		BollettazioneAuditLogger.logger.debug(FLUSH_CLEAR);
		this.bollettazioneDAO.flush();
		this.bollettazioneDAO.clear();
		righeProcessate = 0;
		BollettazioneAuditLogger.logger.debug("creaBollettazione numIterazioni {}", numIterazioni++);
	    }
	}
	BollettazioneAuditLogger.logger.debug(FLUSH_CLEAR);
	this.bollettazioneDAO.flush();
	this.bollettazioneDAO.clear();
	BollettazioneAuditLogger.logger.debug("creaBollettazione end");
	return testata.getId().getCodice();
	*/
    }

    private Set<String> getListaComuni(List<Integer> filtriMercati) {

	Set<String> ret = new HashSet<String>();
	if (filtriMercati == null || filtriMercati.isEmpty()) {
	    filtriMercati = new ArrayList<Integer>();
	    List<Mercati> mercati = mercatiService.findAllMercatiAttivi(null, null);
	    for (Mercati m : mercati) {
		filtriMercati.add(m.getId().getCodice());
	    }
	}
	String mercatiSenzaComune = new String();
	for (Integer integer : filtriMercati) {
	    Mercati m = mercatiService.findById(new PkId(integer));
	    if (m.getComune() == null) {
		mercatiSenzaComune += "\n" + m.getDescrizione() + " (" + m.getId() + ")";
	    } else {
		ret.add(m.getComune().getCodicecomune());
	    }
	}
	if (StringUtils.isNotBlank(mercatiSenzaComune)) {
	    throw new BusinessValidationException(
		    "Attenzione non è stata configurata la proprietà comune dei seguenti mercati: " + mercatiSenzaComune);
	}
	return ret;
    }

    private List<Integer> getFiltriConti(BollCfgTipo bollCfgTipo) {

	Set<BollCfgConti> cfgcontis = bollCfgTipo.getBollCfgContis();
	if (cfgcontis != null) {
	    List<Integer> filtriMercati = new ArrayList<Integer>();
	    for (BollCfgConti conto : cfgcontis) {
		filtriMercati.add(conto.getId().getFkContoId());
	    }
	    return filtriMercati;
	}
	return new ArrayList<Integer>(0);
    }

    private List<Integer> getFiltriMercati(BollCfgTipo bollCfgTipo) {

	Set<BollCfgMercati> cfgMercati = bollCfgTipo.getBollCfgMercatis();
	if (cfgMercati != null) {
	    List<Integer> filtriMercati = new ArrayList<Integer>();
	    for (BollCfgMercati cfgMercato : cfgMercati) {
		filtriMercati.add(cfgMercato.getMercati().getId().getCodice());
	    }
	    return filtriMercati;
	}
	return new ArrayList<Integer>();
    }

    @Override
    public EsitoCalcoloBollettazione calcolaConguaglio(Integer codiceTipologiaBollettazione, Date dataPartenzaBollettazioneAttuale,
	    Integer codiceResponsabile) {

	List<RigaDettaglioBollettazioneMercatiDett> righeConguaglio = new ArrayList<RigaDettaglioBollettazioneMercatiDett>();
	//riprendo la testata della bollettazione precedente
	BollGestTestata testata = this.bollettazioneDAO.findBollettazionePrecedenteByDataAndTipologia(codiceTipologiaBollettazione,
		dataPartenzaBollettazioneAttuale);
	if (testata != null) {
	    BollettazioneAuditLogger.logger.debug("creaBollettazione calcolaConguaglio trovata testata {}", testata.getId());
	    IntervalloDate intervalloDate = new IntervalloDate(testata.getDallaData(), testata.getAllaData());
	    //riprendo la configurazione
	    BollCfgTipo bollCfgTipo = testata.getBollCfgTipo();
	    // ricreo la richiesta della bollettazione precedente
	    PeriodiEnum tipologiaPeriodo = PeriodiEnum.valueOf(bollCfgTipo.getPeriodo().toUpperCase());
	    TitolaritaPagamentiEnum titolarita = TitolaritaPagamentiEnum.PRESENZE_EFFETTIVE;
	    if (StringUtils.isNotEmpty(bollCfgTipo.getTitolaritaPagamenti())) {
		titolarita = TitolaritaPagamentiEnum.valueOf(bollCfgTipo.getTitolaritaPagamenti());
	    }
	    RichiestaCalcoloBollettazioneMercato richiestaPrecedente = new RichiestaCalcoloBollettazioneMercato(tipologiaPeriodo, titolarita,
		    codiceResponsabile, testata.getDescrizione(), intervalloDate, getFiltriConti(bollCfgTipo));
	    List<Integer> filtriMercati = getFiltriMercati(bollCfgTipo);
	    if (!filtriMercati.isEmpty()) {
		richiestaPrecedente.getFiltriMercati().addAll(filtriMercati);
	    }
	    EsitoCalcoloBollettazione calcoloPrecedente = this.calcola(richiestaPrecedente, true);
	    for (IRigaDettaglioCalcolo rigaDettaglio : calcoloPrecedente.getRighe()) {
		RigaDettaglioBollettazioneMercatiDett riga = (RigaDettaglioBollettazioneMercatiDett) rigaDettaglio;
		Autorizzazioni aut = bollettazioneDAO.getInfoConcessione(riga.getIdAutorizzazioneConcessione(), riga.isSubentro())
			.getAutorizzazione();
		ImportoIvato importoPrecedente = this.bollettazioneDAO.getImportoRigaValidaByTestataAnagrafeEContoEAutorizzazione(
			testata.getId().getCodice(), rigaDettaglio.getIdAnagrafe(), rigaDettaglio.getIdConto(), aut.getId().getCodice());
		// verificare le differenze reali con la bollettazione precedente
		if (importoPrecedente != null) {
		    BigDecimal differenza = riga.getImportoTotale().subtract(importoPrecedente.getImportoConIVA());
		    if (differenza.compareTo(BigDecimal.ZERO) != 0) {
			riga.setImportoTotale(differenza);
			riga.setIva(0);
			riga.setImportoSenzaIVA(differenza);
			riga.setConguaglio(Boolean.TRUE);
			riga.setDescrizione("Conguaglio " + riga.getDescrizione());
			righeConguaglio.add(riga);
		    }
		}
	    }
	    // tornare le sole righe di conguaglio
	    return new EsitoCalcoloBollettazione(intervalloDate, righeConguaglio);
	}
	return null;
    }

    private boolean isAzienda(RichiestaCalcoloBollettazioneMercato richiesta) {

	boolean isAzienda = false;
	boolean valDefault = true;
	for (String codiceComune : getListaComuni(richiesta.getFiltriMercati())) {
	    VerticalizzazioneNodoPagamentiServiceImpl v = new VerticalizzazioneNodoPagamentiServiceImpl(verticalizzazioniService, codiceComune);
	    if (v.isAttiva()) {
		SoggettiPendenzaEnum soggettoPendenza = v.soggettoPendenza();
		boolean nuovoValore = pendenzaService.isAzienda(soggettoPendenza);
		if (nuovoValore != isAzienda) {
		    if (!valDefault) {
			throw new RuntimeException(
				"Sono presenti configurazioni per il nodo pagamenti che prevedono il raggruppamento per azienda e altre che non lo prevedono");
		    }
		    isAzienda = nuovoValore;
		    valDefault = false;
		}
	    }
	}
	return isAzienda;
    }
}
