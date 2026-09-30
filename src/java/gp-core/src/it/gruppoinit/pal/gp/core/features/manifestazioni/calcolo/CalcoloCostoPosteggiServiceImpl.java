package it.gruppoinit.pal.gp.core.features.manifestazioni.calcolo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ImportoHelper2Bean;
import it.gruppoinit.pal.gp.core.dao.helper.ImportoHelperBean;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniAttivita;
import it.gruppoinit.pal.gp.core.domain.Concessioniuso;
import it.gruppoinit.pal.gp.core.domain.Istanzeattivita;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiCfgAttivita;
import it.gruppoinit.pal.gp.core.domain.MercatiCfgAttivitaId;
import it.gruppoinit.pal.gp.core.domain.MercatiCfgConti;
import it.gruppoinit.pal.gp.core.domain.MercatiContabilitaTributi;
import it.gruppoinit.pal.gp.core.domain.MercatiConti;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiDConti;
import it.gruppoinit.pal.gp.core.domain.MercatiDattivitaistat;
import it.gruppoinit.pal.gp.core.domain.MercatiFormuleCalcolo;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeD;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeT;
import it.gruppoinit.pal.gp.core.domain.Mercatistradario;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.VwConcessioniattive;
import it.gruppoinit.pal.gp.core.domain.helper.ContiBean;
import it.gruppoinit.pal.gp.core.domain.helper.MercatiFormuleCalcoloContestoEnum;
import it.gruppoinit.pal.gp.core.domain.helper.PosteggioImportoHelper;
import it.gruppoinit.pal.gp.core.domain.helper.RigaImporto;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.ContoImportoTotaleHelper;
import it.gruppoinit.pal.gp.core.features.contabilita.AliquotaIVA;
import it.gruppoinit.pal.gp.core.features.contabilita.ImportoIvato;
import it.gruppoinit.pal.gp.core.features.manifestazioni.configurazione.MercatiDService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.IRecuperaInformazioniGiornataService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.InfoGiornataPresenzaBean;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.MercatiFormuleResolver;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.segnaposto.SegnapostoFormuleBuilderRequest;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.segnaposto.SegnapostoFormuleBuilderRequest.PROVENIENZA;
import it.gruppoinit.pal.gp.core.features.oneri.ContiService;
import it.gruppoinit.pal.gp.core.service.AutorizzazioniAttivitaService;
import it.gruppoinit.pal.gp.core.service.MercatiCfgAttivitaService;
import it.gruppoinit.pal.gp.core.service.MercatiCfgContiService;
import it.gruppoinit.pal.gp.core.service.MercatiDattivitaistatService;
import it.gruppoinit.pal.gp.core.service.MercatiService;
import it.gruppoinit.pal.gp.core.service.MercatiUsoService;

@Service
public class CalcoloCostoPosteggiServiceImpl implements ICalcoloCostoPosteggiService {

    private static final Logger log = LoggerFactory.getLogger(CalcoloCostoPosteggiServiceImpl.class);
    @Autowired
    private MercatiCfgContiService mercatiCfgContiService;
    @Autowired
    private MercatiCfgAttivitaService mercatiCfgAttivitaService;
    @Autowired
    private MercatiDattivitaistatService mercatiDattivitaistatService;
    @Autowired
    private MercatiService mercatiService;
    @Autowired
    private MercatiDService mercatiDService;
    @Autowired
    private AutorizzazioniAttivitaService autorizzazioniAttivitaService;
    @Autowired
    private MercatiUsoService mercatiUsoService;
    @Autowired
    private IRecuperaInformazioniGiornataService recuperaInformazioniGiornataService;
    @Autowired
    private ContiService contiService;

    @Override
    public PosteggioImportoHelper calcolaCostoPosteggio(MercatipresenzeD presenza, MercatiD posteggio, List<MercatiCfgAttivita> listMcfgAttivita,
	    Integer anno, int giorni, VwConcessioniattive concessioneAttiva, String contesto, String codiceIstatCoefficenteMerceologico,
	    Integer mercatiUsoId, Date dataDiRiferimento, MercatiFormuleCalcoloContestoEnum contestoFormula) {

	return calcolaCostoPosteggioInternal(presenza, posteggio, listMcfgAttivita, anno, giorni, concessioneAttiva, contesto, null,
		codiceIstatCoefficenteMerceologico, mercatiUsoId, dataDiRiferimento, contestoFormula);
    }

    private PosteggioImportoHelper calcolaCostoPosteggioInternal(MercatipresenzeD presenza, MercatiD posteggio,
	    List<MercatiCfgAttivita> listMcfgAttivita, Integer anno, int giorni, VwConcessioniattive concessioneAttiva, String contesto,
	    Autorizzazioni autorizzazioneSpuntista, String codiceIstatCoefficenteMerceologico, Integer mercatiUsoId, Date dataDiRiferimento,
	    MercatiFormuleCalcoloContestoEnum contestoFormula) {

	if (mercatiUsoId != null) {
	    MercatiUso uso = mercatiUsoService.findById(new PkId(mercatiUsoId));
	    log.debug("Calcolo il costo per il posteggio {}", posteggio.getId());
	    if (!uso.getFormuleAttiveByContesto(dataDiRiferimento, contestoFormula).isEmpty()) {
		return calcolaConFormule(presenza, uso, dataDiRiferimento);
	    }
	}
	boolean isMerccatiCfgConti = mercatiCfgContiService.existsDati();
	if (contesto == null || contesto.equals("")) {
	    contesto = WebConstants.MERCATO_CONTESTO_TUTTI;
	}
	if (isMerccatiCfgConti) {
	    return calcolaCostoPosteggioDaMercatiCfgConti(presenza, posteggio, listMcfgAttivita, anno, giorni, concessioneAttiva, contesto,
		    autorizzazioneSpuntista, codiceIstatCoefficenteMerceologico);
	} else {
	    return calcolaCostoPosteggioLegacy(presenza, posteggio, listMcfgAttivita, anno, giorni, concessioneAttiva, contesto,
		    autorizzazioneSpuntista, codiceIstatCoefficenteMerceologico);
	}
    }

    private PosteggioImportoHelper calcolaConFormule(MercatipresenzeD presenza, MercatiUso uso, Date dataDiRiferimento) {

	if (presenza == null) {
	    throw new IllegalArgumentException("Non è possibile passare null il parametro presenza al metodo per calcolare il costo del posteggio");
	}
	Set<MercatiFormuleCalcolo> formule = uso.getFormuleAttiveByContesto(dataDiRiferimento, MercatiFormuleCalcoloContestoEnum.PRESENZA);
	Map<Integer, ContoImportoTotaleHelper> map = new HashMap<Integer, ContoImportoTotaleHelper>();
	for (MercatiFormuleCalcolo formula : formule) {
	    MercatipresenzeT giornata = presenza.getMercatiPresenzeT();
	    MercatiContabilitaTributi contoAttivo = formula.getContoAttivo(dataDiRiferimento);
	    if (contoAttivo == null) {
		log.error("Non ci sono conti attivi per la formula {}", formula);
		throw new RuntimeException("Non ci sono conti attivi per la formula " + formula);
	    }
	    SegnapostoFormuleBuilderRequest request = new SegnapostoFormuleBuilderRequest(formula, giornata.getId().getCodice(),
		    presenza.getPosteggio().getId().getCodice(), PROVENIENZA.PRESENZE.name());
	    InfoGiornataPresenzaBean info = InfoGiornataPresenzaBean.fromPresenza(presenza);
	    //estrapolo l'importo per quella giornata e quella formula
	    ContoImportoTotaleHelper costo = new MercatiFormuleResolver(request, recuperaInformazioniGiornataService, giornata.getDataRegistrazione(),
		    info).risolvi();
	    //verifico se ho già in mappa quel conto altrimenti lo creo nuovo
	    if (!map.containsKey(costo.getIdConto())) {
		map.put(costo.getIdConto(), costo);
	    } else {
		//estrapolo dalla mappa il costo attuale per quel conto
		ContoImportoTotaleHelper costoAttuale = map.get(costo.getIdConto());
		//calcolo l'importo da aggiungere ( sia in termini di importoSenzaIva che importoConIva )
		ImportoIvato importoDaAggiungere = new AliquotaIVA(costoAttuale.getImportoIvato().getIva())
			.applica(costo.getImportoIvato().getImportoSenzaIVA());
		//calcolo i nuovi importi sommando gli importi attuali con quelli calcolati
		BigDecimal nuovoImportoSenzaIVA = costoAttuale.getImportoIvato().getImportoSenzaIVA().add(importoDaAggiungere.getImportoSenzaIVA());
		BigDecimal nuovoImportoConIVA = costoAttuale.getImportoIvato().getImportoConIVA().add(importoDaAggiungere.getImportoConIVA());
		//aggiorno gli importi nel costo attuale
		costoAttuale.getImportoIvato().setImportoSenzaIVA(nuovoImportoSenzaIVA);
		costoAttuale.getImportoIvato().setImportoConIVA(nuovoImportoConIVA);
		//aggiorno il riferimento al costo nella mappa
		map.put(costo.getIdConto(), costoAttuale);
	    }
	}
	if (map.isEmpty()) {
	    String messaggioErrore = "Non sono state trovate formule con contesto " +
		    MercatiFormuleCalcoloContestoEnum.PRESENZA +
		    " tra le formule del mercato " +
		    presenza.getMercatiPresenzeT().getMercato().getDescrizione();
	    throw new IllegalArgumentException(messaggioErrore);
	}
	PosteggioImportoHelper h = new PosteggioImportoHelper();
	for (Entry<Integer, ContoImportoTotaleHelper> mcf : map.entrySet()) {
	    ContoImportoTotaleHelper cith = mcf.getValue();
	    if (cith.getImportoIvato().getImportoConIVA().compareTo(BigDecimal.ZERO) > 0) {
		h.getListaImporti().add(new RigaImporto(cith, contiService.findById(new PkId(cith.getIdConto()))));
	    }
	}
	return h;
    }

    private PosteggioImportoHelper calcolaCostoPosteggioDaMercatiCfgConti(MercatipresenzeD presenza, MercatiD posteggio,
	    List<MercatiCfgAttivita> listMcfgAttivita, Integer anno, int giorni, VwConcessioniattive concessioneAttiva, String contesto,
	    Autorizzazioni autorizzazioneSpuntista, String codiceIstatCoefficenteMerceologico) {

	PosteggioImportoHelper result = new PosteggioImportoHelper();
	BigDecimal mqPosteggio = posteggio.getSuperficie(); // su
	BigDecimal importoIndividualePosteggio = new BigDecimal(0);
	if (contesto == null || contesto.equals("")) {
	    contesto = WebConstants.MERCATO_CONTESTO_TUTTI;
	}
	log.debug("Calcolo il costo per il posteggio {} uso la configurazione MERCATI_CFG_CONTI", posteggio.getId());
	Date dataPresenza = Calendar.getInstance().getTime();
	Concessioniuso concessioniuso = null;
	if (presenza != null) {
	    dataPresenza = presenza.getMercatiPresenzeT().getDataRegistrazione();
	    concessioniuso = presenza.getMercatiPresenzeT().getMercatoUso().getConcessioniuso();
	    if (presenza.getMercatiPresenzeT().getConcessioniuso() != null && presenza.getMercatiPresenzeT().getConcessioniuso().getId() != null
		    && presenza.getMercatiPresenzeT().getConcessioniuso().getId().getCodice() != null) {
		concessioniuso = presenza.getMercatiPresenzeT().getConcessioniuso();
	    }
	}
	log.debug("Concessioniuso per il posteggio {}-{}", concessioniuso, posteggio.getId());
	List<MercatiCfgConti> mcs = new ArrayList<MercatiCfgConti>();
	Integer posteggioSettoreId = null;
	if (posteggio.getPosteggiSettori() != null && posteggio.getPosteggiSettori().getId() != null
		&& posteggio.getPosteggiSettori().getId().getCodice() != null) {
	    posteggioSettoreId = posteggio.getPosteggiSettori().getId().getCodice();
	}
	log.debug("Posteggi settori per il posteggio {}-{}", posteggioSettoreId, posteggio.getId());
	Mercati m = mercatiService.findById(new PkId(posteggio.getMercati().getId().getCodice()));
	boolean isRicercaPosteggiSettori = false;
	if (m.getMercatiCategorie() != null && m.getMercatiCategorie().getId() != null && m.getMercatiCategorie().getId().getCodice() != null) {
	    if (posteggioSettoreId != null) {
		mcs = mercatiCfgContiService.findByMercatoCategoriaAndPosteggiSettoriAndDataPresenza(m.getMercatiCategorie().getId().getCodice(),
			posteggioSettoreId, dataPresenza);
		isRicercaPosteggiSettori = true;
	    }
	    if (mcs.isEmpty()) {
		isRicercaPosteggiSettori = false;
		log.debug("Non ho trovato le configurazioni per il posteggiosettore le cerco per mercaticategoria {}-{}", posteggioSettoreId,
			posteggio.getId());
		mcs = mercatiCfgContiService.findByMercatoCategoriaAndDataPresenza(m.getMercatiCategorie().getId().getCodice(), dataPresenza);
	    }
	}
	if (mcs.isEmpty()) {
	    log.debug("Non ho trovato le configurazioni per il posteggiosettore le cerco per data presenza {}", posteggio.getId());
	    mcs = mercatiCfgContiService.findByDataPresenza(dataPresenza);
	}
	if (mcs.isEmpty()) {
	    log.error("Configurazione errata. Non sono presenti coefficienti validi per il posteggio {}", posteggio.getId());
	    throw new RuntimeException("Configurazione errata. Non sono presenti coefficienti validi - rif MERCATI_CONTI_CFG");
	}
	Map<String, ImportoHelper2Bean> righeImporti = new HashMap<String, ImportoHelper2Bean>();
	///////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////
	BigDecimal coefficienteViario = new BigDecimal(0);
	posteggio = mercatiDService.findById(posteggio.getId());
	Integer codiceVia = null;
	if (null != posteggio.getStradario() && null != posteggio.getStradario().getId()) {
	    codiceVia = posteggio.getStradario().getId().getCodice();
	}
	// cerco il coefficiente viario per quel posteggio
	Set<Mercatistradario> mercatistradarios = posteggio.getMercati().getMercatistradarios();
	for (Mercatistradario mercatistradario : mercatistradarios) {
	    if (codiceVia != null && mercatistradario.getStradario().getId().getCodice().equals(codiceVia)) {
		if (mercatistradario.getCoefficienteViario() != null) { // evita NullPointer
		    coefficienteViario = coefficienteViario.add(mercatistradario.getCoefficienteViario()); //
		    //
		}
		break;
	    }
	}
	/// BEGIN CATEGORIE POSTEGGIO
	if (isRicercaPosteggiSettori) {
	    //
	    log.debug("Il posteggio ha configurazioni su posteggisettori {} - {}", posteggio.getId(), posteggioSettoreId);
	    for (MercatiCfgConti mercatiCfgConti : mcs) {
		// IN QUESTO CASO HO TROVATO TUTTI QUELLI DI QUEL POSTEGGIO SETTORE
		ImportoHelper2Bean ihb = new ImportoHelper2Bean(mercatiCfgConti);
		righeImporti.put(String.valueOf(mercatiCfgConti.getId().getCodice()), ihb);
	    }
	} else {
	    if (StringUtils.isNotBlank(codiceIstatCoefficenteMerceologico)) {
		log.debug("È stato specificato il coefficente merceologico per l'attività {} cerco il valore in configurazione",
			codiceIstatCoefficenteMerceologico);
		for (MercatiCfgConti mercatiCfgConti : mcs) {
		    if (mercatiCfgConti.getAttivita() != null && mercatiCfgConti.getAttivita().getId() != null
			    && StringUtils.isNotBlank(mercatiCfgConti.getAttivita().getId().getCodiceistat())) {
			if (codiceIstatCoefficenteMerceologico.equalsIgnoreCase(mercatiCfgConti.getAttivita().getId().getCodiceistat())) {
			    ImportoHelper2Bean ihb = new ImportoHelper2Bean(mercatiCfgConti);
			    righeImporti.put(String.valueOf(mercatiCfgConti.getId().getCodice()), ihb);
			}
		    } else {
			ImportoHelper2Bean ihb = new ImportoHelper2Bean(mercatiCfgConti);
			righeImporti.put(String.valueOf(mercatiCfgConti.getId().getCodice()), ihb);
		    }
		}
	    } else {
		// cerco il coefficiente merceologico
		if (concessioneAttiva != null) {
		    Set<Istanzeattivita> istanzeattivitas = concessioneAttiva.getIstanza().getIstanzeattivitas();
		    for (Istanzeattivita istanzeattivita : istanzeattivitas) {
			for (MercatiCfgConti mercatiCfgConti : mcs) {
			    if (mercatiCfgConti.getAttivita() == null || istanzeattivita.getAttivita().getId().getCodiceistat()
				    .equals(mercatiCfgConti.getAttivita().getId().getCodiceistat())) {
				log.debug("calcolaCostoPosteggio(): attività dell'istanza ({}) è {} ({})",
					new Object[] { istanzeattivita.getIstanza().getNumeroistanza(), istanzeattivita.getAttivita(),
					    istanzeattivita.getAttivita().getId().getCodiceistat() });
				ImportoHelper2Bean ihb = new ImportoHelper2Bean(mercatiCfgConti);
				righeImporti.put(String.valueOf(mercatiCfgConti.getId().getCodice()), ihb);
			    }
			}
		    }
		}
		if (righeImporti.isEmpty()) {
		    log.debug("Recupero coefficiente da categoria merceologica");
		    if (autorizzazioneSpuntista != null) {
			// in primo luogo cerco il coefficente dall'autorizzazione dello spuntista
			List<AutorizzazioniAttivita> atts = autorizzazioniAttivitaService
				.findByAutorizzazione(autorizzazioneSpuntista.getId().getCodice(), 0, 1);
			for (AutorizzazioniAttivita att : atts) {
			    for (MercatiCfgConti mercatiCfgConti : mcs) {
				if (mercatiCfgConti.getAttivita() == null || att.getAttivita().getId().getCodiceistat()
					.equals(mercatiCfgConti.getAttivita().getId().getCodiceistat())) {
				    ImportoHelper2Bean ihb = new ImportoHelper2Bean(mercatiCfgConti);
				    righeImporti.put(String.valueOf(mercatiCfgConti.getId().getCodice()), ihb);
				}
			    }
			}
		    }
		}
	    }
	    if (righeImporti.isEmpty()) {
		List<MercatiDattivitaistat> findAttivitaPosteggio = mercatiDattivitaistatService.findAttivitaPosteggio(posteggio.getId().getCodice());
		// se non trovato lo cerco sulla configurazione del posteggio
		if (findAttivitaPosteggio != null && !findAttivitaPosteggio.isEmpty()) {
		    MercatiDattivitaistat mdai = findAttivitaPosteggio.get(0);
		    log.debug("Attivita= {}, Mercato={} Posteggio ={} ", new Object[] { mdai.getAttivita().getDescrizioneEstesa(),
			mdai.getMercato().getDescrizione(), mdai.getPosteggio().getCodiceposteggio() });
		    MercatiCfgAttivitaId id = new MercatiCfgAttivitaId(mdai.getId().getFkcodiceattivitaistat());
		    MercatiCfgAttivita mercatiCfgAttivita = mercatiCfgAttivitaService.findById(id);
		    if (mercatiCfgAttivita != null && StringUtils.isNotBlank(mercatiCfgAttivita.getId().getFkCodiceattivita())) {
			for (MercatiCfgConti mercatiCfgConti : mcs) {
			    if (mercatiCfgConti.getAttivita() == null || mercatiCfgAttivita.getId().getFkCodiceattivita()
				    .equals(mercatiCfgConti.getAttivita().getId().getCodiceistat())) {
				ImportoHelper2Bean ihb = new ImportoHelper2Bean(mercatiCfgConti);
				righeImporti.put(String.valueOf(mercatiCfgConti.getId().getCodice()), ihb);
			    }
			}
		    }
		}
	    }
	}
	////// END CATEGORIE POSTEGGIO
	if (coefficienteViario == null || coefficienteViario.floatValue() == 0.0) {
	    coefficienteViario = new BigDecimal(1);
	}
	if (mqPosteggio == null || mqPosteggio.floatValue() == 0.0) {
	    mqPosteggio = new BigDecimal(1);
	}
	if (righeImporti.isEmpty() && !mcs.isEmpty()) {
	    for (MercatiCfgConti mcfg : mcs) {
		if (mcfg.getAttivita() == null && mcfg.getMercatiCategorie() == null && mcfg.getPosteggiSettori() == null) {
		    ImportoHelper2Bean ihb = new ImportoHelper2Bean(mcfg);
		    righeImporti.put(String.valueOf(mcfg.getId().getCodice()), ihb);
		}
	    }
	}
	List<RigaImporto> righeImportiPosteggio = new ArrayList<RigaImporto>();
	Collection<ImportoHelper2Bean> importi = righeImporti.values();
	for (ImportoHelper2Bean importoHelper2Bean : importi) {
	    boolean inserisceImporto = true;
	    RigaImporto riga = new RigaImporto();
	    MercatiCfgConti mercatiCfgConti = importoHelper2Bean.getMercatiCfgConti();
	    if (concessioniuso != null) {
		inserisceImporto = false;
		if (mercatiCfgConti.getConcessioniuso() == null
			|| concessioniuso.getId().getCodice().equals(mercatiCfgConti.getConcessioniuso().getId().getCodice())) {
		    inserisceImporto = true;
		}
	    }
	    if (inserisceImporto) {
		if (mercatiCfgConti.getFlagMoltiplicaMq() != null && !mercatiCfgConti.getFlagMoltiplicaMq().booleanValue()) {
		    importoIndividualePosteggio = mercatiCfgConti.getImporto();
		} else {
		    giorni = (giorni == 0) ? 1 : giorni;
		    log.debug("calcolaCostoPosteggio(): T=costo * giorni * coeff merc * coeff viario * mq");
		    log.debug("calcolaCostoPosteggio(): costo={}", importoIndividualePosteggio);
		    importoIndividualePosteggio = mercatiCfgConti.getImporto();
		    importoIndividualePosteggio = importoIndividualePosteggio.multiply(new BigDecimal(giorni));
		    importoIndividualePosteggio = importoIndividualePosteggio.multiply(coefficienteViario);
		    importoIndividualePosteggio = importoIndividualePosteggio.multiply(mqPosteggio);
		    log.debug("calcolaCostoPosteggio(): giorni={}", giorni);
		    log.debug("calcolaCostoPosteggio(): coeff merceologico={}", mercatiCfgConti.getImporto());
		    log.debug("calcolaCostoPosteggio(): coeff viario={}", coefficienteViario);
		    log.debug("calcolaCostoPosteggio(): mq posteggio={}", mqPosteggio);
		}
		if (log.isDebugEnabled()) {
		    log.debug("calcolaCostoPosteggio(): TARIFFA Canone = {}", importoIndividualePosteggio);
		}
		// devo fare tante registrazioni importi per quante rate sono state configurate
		// e con la percentuale e la scadenza da spalmare sulle righe di importo
		riga.setConto(new ContiBean(mercatiCfgConti.getConti()));
		// .. IMPORTANTE SETTO LA SCALA DEL DECIMALE ALTRIMENTI DA ERRORE
		// .. IL VALIDATORE DELL'OGGETTO DI DOMINIO
		importoIndividualePosteggio = importoIndividualePosteggio.setScale(2, BigDecimal.ROUND_HALF_UP);
		riga.setImporto(importoIndividualePosteggio);
		Integer iva = mercatiCfgConti.getConti().getIva();
		if (iva == null) {
		    throw new RuntimeException("Errore nella configurazione dei conti della contabilita'. Non e' stata definita l'iva per il conto " +
			    mercatiCfgConti.getConti().getDescrizione() +
			    "(" +
			    mercatiCfgConti.getConti().getId() +
			    ")");
		}
		riga.setIva(iva);
		righeImportiPosteggio.add(riga);
	    }
	}
	result.setListaImporti(righeImportiPosteggio);
	return result;
	///////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////
    }

    private PosteggioImportoHelper calcolaCostoPosteggioLegacy(MercatipresenzeD presenza, MercatiD posteggio,
	    List<MercatiCfgAttivita> listMcfgAttivita, Integer anno, int giorni, VwConcessioniattive concessioneAttiva, String contesto,
	    Autorizzazioni autorizzazioneSpuntista, String codiceIstatCoefficenteMerceologico) {

	PosteggioImportoHelper result = new PosteggioImportoHelper();
	BigDecimal mqPosteggio = posteggio.getSuperficie(); // su
	BigDecimal importoIndividualePosteggio = BigDecimal.ZERO;
	boolean condizioneContesto = true;
	log.debug("Calcolo il costo per il posteggio {} uso la configurazione classica ", posteggio.getId());
	//
	// l'importo del canone differisce solamente x riga conti di mercato e conti posteggio
	// se le righe di mercati_conti o mercati_d_conti con flag_valore = false il campo valore è un coefficiente
	// e la tariffa viene calcolata secondo la formula T = gg (mercato) x valore x coeff calcolato x coeff viario x
	// mq
	// posteggio
	// se le righe di mercati_conti o mercati_d_conti con flag_valore = true allora la tariffa è un valore e la
	// formula è T = valore
	// ossia indipendente dagli altri coefficienti
	BigDecimal importo = BigDecimal.ZERO;
	Map<String, ImportoHelperBean> righeImporti = new HashMap<String, ImportoHelperBean>();
	Set<MercatiConti> mercatoConti = posteggio.getMercati().getMercatiContis();
	String contestoConto = "";
	for (MercatiConti contoMercato : mercatoConti) {
	    contestoConto = contoMercato.getContesto();
	    if (null == contestoConto || contestoConto.equals("")) { // se nel conto contesto non è specificato allora
		// lo associo a TUTTI
		contestoConto = WebConstants.MERCATO_CONTESTO_TUTTI;
	    }
	    // condizione contesto =
	    // se contesto = "Concessionari" e contestoConto = "Tutti" allora true e viceversa
	    // se contesto = "Spuntisti" e contestoConto = "Tutti" allora true e viceversa
	    // se contesto = "Spuntisti" e contestoConto = "Concessionari" allora false e viceversa
	    // se contesto = "Tutti" e contestoConto = "Tutti" allora true
	    condizioneContesto = (contestoConto.equalsIgnoreCase(contesto) || contestoConto.equalsIgnoreCase(WebConstants.MERCATO_CONTESTO_TUTTI)
		    || contesto.equalsIgnoreCase(WebConstants.MERCATO_CONTESTO_TUTTI));
	    if (contoMercato.getAnno().compareTo(anno) == 0 && condizioneContesto) {
		importo = recuperaImportoRidettato(contoMercato.getValore(), contoMercato.getPercentualeConsorzio());
		ImportoHelperBean ihb = new ImportoHelperBean(contoMercato.getConti(), importo, contoMercato.getFlagValore(),
			contoMercato.getContesto(), BooleanUtils.isTrue(contoMercato.getFlagImportomensile()));
		righeImporti.put(String.valueOf(contoMercato.getConti().getId().getCodice()) + "-" + contoMercato.getContesto(), ihb);
	    }
	}
	Set<MercatiDConti> posteggioConti = posteggio.getListaContiPosteggio();
	for (MercatiDConti contoPosteggio : posteggioConti) {
	    contestoConto = contoPosteggio.getContesto();
	    if (null == contestoConto || contestoConto.equals("")) { // se nel conto contesto non è specificato allora
		// lo associo a TUTTI
		contestoConto = WebConstants.MERCATO_CONTESTO_TUTTI;
	    }
	    // condizione contesto
	    // se contesto = "Concessionari" e contestoConto = "Tutti" allora true e viceversa
	    // se contesto = "Spuntisti" e contestoConto = "Tutti" allora true e viceversa
	    // se contesto = "Spuntisti" e contestoConto = "Concessionari" allora false e viceversa
	    // se contesto = "Tutti" e contestoConto = "Tutti" allora true
	    condizioneContesto = (contestoConto.equalsIgnoreCase(contesto) || contestoConto.equalsIgnoreCase(WebConstants.MERCATO_CONTESTO_TUTTI)
		    || contesto.equalsIgnoreCase(WebConstants.MERCATO_CONTESTO_TUTTI));
	    if (contoPosteggio.getAnno().shortValue() == anno.shortValue() && condizioneContesto) {
		importo = recuperaImportoRidettato(contoPosteggio.getValore(), contoPosteggio.getPercentualeConsorzio());
		ImportoHelperBean ihb = new ImportoHelperBean(contoPosteggio.getConto(), importo, contoPosteggio.getFlagValore(),
			contoPosteggio.getContesto(), BooleanUtils.isTrue(contoPosteggio.getFlagImportomensile()));
		righeImporti.put(String.valueOf(contoPosteggio.getConto().getId().getCodice()) + "-" + contoPosteggio.getContesto(), ihb);
	    }
	}
	// elimino i duplicati usando un criterio di preferenza.
	// la riga con il contesto specifico prevale sulla riga con il contesto tutti
	for (MercatiConti contoMercato : mercatoConti) {
	    if (contoMercato.getAnno().compareTo(anno) == 0) {
		String chiaveIhbTutti = contoMercato.getConti().getId().getCodice() + "-" + WebConstants.MERCATO_CONTESTO_TUTTI;
		String chiaveIhbContestoSpecifico = contoMercato.getConti().getId().getCodice() + "-" + contesto;
		ImportoHelperBean tuttiIhb = righeImporti.get(chiaveIhbTutti);
		ImportoHelperBean contestoIhb = righeImporti.get(chiaveIhbContestoSpecifico);
		// rimuovo la chiave solo se la chiave del contesto specifico è <> da
		// WebConstants.MERCATO_CONTESTO_TUTTI
		if (!chiaveIhbTutti.equalsIgnoreCase(chiaveIhbContestoSpecifico) && tuttiIhb != null && contestoIhb != null) {
		    righeImporti.remove(chiaveIhbTutti);
		}
	    }
	}
	if (righeImporti.size() == 0) {
	    // FIXME non c'è nessuna riga di importo o lancio errore?
	    // non registro niente
	    return null;
	}
	BigDecimal coefficienteViario = new BigDecimal(0);
	BigDecimal coefficienteMerceologico = new BigDecimal(0);
	posteggio = mercatiDService.findById(posteggio.getId());
	Integer codiceVia = null;
	if (null != posteggio.getStradario() && null != posteggio.getStradario().getId()) {
	    codiceVia = posteggio.getStradario().getId().getCodice();
	}
	// cerco il coefficiente viario per quel posteggio
	Set<Mercatistradario> mercatistradarios = posteggio.getMercati().getMercatistradarios();
	for (Mercatistradario mercatistradario : mercatistradarios) {
	    if (codiceVia != null && mercatistradario.getStradario().getId().getCodice().equals(codiceVia)) {
		if (mercatistradario.getCoefficienteViario() != null) { // evita NullPointer
		    coefficienteViario = coefficienteViario.add(mercatistradario.getCoefficienteViario()); //
		    //
		}
		break;
	    }
	}
	if (StringUtils.isNotBlank(codiceIstatCoefficenteMerceologico)) {
	    log.debug("È stato specificato il coefficente merceologico per l'attività {} cerco il valore in configurazione",
		    codiceIstatCoefficenteMerceologico);
	    MercatiCfgAttivitaId id = new MercatiCfgAttivitaId(codiceIstatCoefficenteMerceologico);
	    MercatiCfgAttivita mercatiCfgAttivita = mercatiCfgAttivitaService.findById(id);
	    if (mercatiCfgAttivita != null && StringUtils.isNotBlank(mercatiCfgAttivita.getId().getFkCodiceattivita())) {
		coefficienteMerceologico = mercatiCfgAttivita.getCoefficiente();
		log.debug("È stato trovato il coefficente merceologico per l'attività {} ed il valore è {}", codiceIstatCoefficenteMerceologico,
			coefficienteMerceologico);
	    }
	} else {
	    // cerco il coefficiente merceologico
	    if (concessioneAttiva != null) {
		Set<Istanzeattivita> istanzeattivitas = concessioneAttiva.getIstanza().getIstanzeattivitas();
		for (Istanzeattivita istanzeattivita : istanzeattivitas) {
		    for (MercatiCfgAttivita mercatiCfgAttivita : listMcfgAttivita) {
			if (istanzeattivita.getAttivita().getId().getCodiceistat()
				.equals(mercatiCfgAttivita.getAttivita().getId().getCodiceistat())) {
			    log.debug("calcolaCostoPosteggio(): attività dell'istanza ({}) è {} ({})",
				    new Object[] { istanzeattivita.getIstanza().getNumeroistanza(), istanzeattivita.getAttivita(),
					istanzeattivita.getAttivita().getId().getCodiceistat() });
			    log.debug("coefficiente merceologico = {}", mercatiCfgAttivita.getCoefficiente());
			    if (mercatiCfgAttivita.getCoefficiente() != null) { // evita NullPointer
				coefficienteMerceologico = coefficienteMerceologico.add(mercatiCfgAttivita.getCoefficiente());
			    }
			    break;
			}
			if (coefficienteMerceologico.floatValue() != 0) { // appena lo trovo esco dal ciclo
			    break;
			}
		    }
		}
	    }
	    if (coefficienteMerceologico == null || coefficienteMerceologico.floatValue() == 0.0) {
		log.debug("Recupero coefficiente da categoria merceologica");
		coefficienteMerceologico = new BigDecimal(0);
		if (autorizzazioneSpuntista != null) {
		    // in primo luogo cerco il coefficente dall'autorizzazione dello spuntista
		    List<AutorizzazioniAttivita> atts = autorizzazioniAttivitaService
			    .findByAutorizzazione(autorizzazioneSpuntista.getId().getCodice(), 0, 1);
		    for (AutorizzazioniAttivita att : atts) {
			MercatiCfgAttivitaId id = new MercatiCfgAttivitaId(att.getAttivita().getId().getCodiceistat());
			MercatiCfgAttivita mercatiCfgAttivita = mercatiCfgAttivitaService.findById(id);
			if (mercatiCfgAttivita != null && StringUtils.isNotBlank(mercatiCfgAttivita.getId().getFkCodiceattivita())) {
			    coefficienteMerceologico = mercatiCfgAttivita.getCoefficiente();
			    break;
			}
		    }
		}
	    }
	}
	if (coefficienteMerceologico == null || coefficienteMerceologico.floatValue() == 0.0) {
	    List<MercatiDattivitaistat> findAttivitaPosteggio = mercatiDattivitaistatService.findAttivitaPosteggio(posteggio.getId().getCodice());
	    // se non trovato lo cerco sulla configurazione del posteggio
	    if (findAttivitaPosteggio != null && !findAttivitaPosteggio.isEmpty()) {
		MercatiDattivitaistat mdai = findAttivitaPosteggio.get(0);
		log.debug("Attivita= {}, Mercato={} Posteggio ={} ", new Object[] { mdai.getAttivita().getDescrizioneEstesa(),
		    mdai.getMercato().getDescrizione(), mdai.getPosteggio().getCodiceposteggio() });
		MercatiCfgAttivitaId id = new MercatiCfgAttivitaId(mdai.getId().getFkcodiceattivitaistat());
		MercatiCfgAttivita mercatiCfgAttivita = mercatiCfgAttivitaService.findById(id);
		if (mercatiCfgAttivita != null && StringUtils.isNotBlank(mercatiCfgAttivita.getId().getFkCodiceattivita())) {
		    log.debug("Attivita= {} Mercato={} Posteggio ={} Valore ={}", new Object[] { mdai.getAttivita().getDescrizioneEstesa(),
			mdai.getMercato().getDescrizione(), mdai.getPosteggio().getCodiceposteggio(), mercatiCfgAttivita.getCoefficiente() });
		    coefficienteMerceologico = mercatiCfgAttivita.getCoefficiente();
		}
	    }
	}
	if (coefficienteMerceologico == null || coefficienteMerceologico.floatValue() == 0.0) {
	    coefficienteMerceologico = new BigDecimal(1);
	}
	if (coefficienteViario == null || coefficienteViario.floatValue() == 0.0) {
	    coefficienteViario = new BigDecimal(1);
	}
	if (mqPosteggio == null || mqPosteggio.floatValue() == 0.0) {
	    mqPosteggio = new BigDecimal(1);
	}
	List<RigaImporto> righeImportiPosteggio = new ArrayList<RigaImporto>();
	Collection<ImportoHelperBean> importi = righeImporti.values();
	for (ImportoHelperBean importoHelperBean : importi) {
	    RigaImporto riga = new RigaImporto();
	    importoIndividualePosteggio = importoHelperBean.getImporto();
	    if (importoHelperBean.isValoreMensile()) {
		importoIndividualePosteggio = importoIndividualePosteggio.multiply(BigDecimal.valueOf(12));
	    }
	    if (!importoHelperBean.isValore()) {
		giorni = (giorni == 0) ? 1 : giorni;
		if (log.isDebugEnabled()) {
		    log.debug("calcolaCostoPosteggio(): T=costo * giorni * coeff merc * coeff viario * mq");
		    log.debug("calcolaCostoPosteggio(): costo={}", importoIndividualePosteggio);
		}
		importoIndividualePosteggio = importoIndividualePosteggio.multiply(new BigDecimal(giorni));
		importoIndividualePosteggio = importoIndividualePosteggio.multiply(coefficienteMerceologico);
		importoIndividualePosteggio = importoIndividualePosteggio.multiply(coefficienteViario);
		importoIndividualePosteggio = importoIndividualePosteggio.multiply(mqPosteggio);
		if (log.isDebugEnabled()) {
		    log.debug("calcolaCostoPosteggio(): giorni={}", giorni);
		    log.debug("calcolaCostoPosteggio(): coeff merceologico={}", coefficienteMerceologico);
		    log.debug("calcolaCostoPosteggio(): coeff viario={}", coefficienteViario);
		    log.debug("calcolaCostoPosteggio(): mq posteggio={}", mqPosteggio);
		}
	    }
	    if (log.isDebugEnabled()) {
		log.debug("calcolaCostoPosteggio(): TARIFFA Canone = {}", importoIndividualePosteggio);
	    }
	    // devo fare tante registrazioni importi per quante rate sono state configurate
	    // e con la percentuale e la scadenza da spalmare sulle righe di importo
	    riga.setConto(new ContiBean(importoHelperBean.getConto()));
	    // .. IMPORTANTE SETTO LA SCALA DEL DECIMALE ALTRIMENTI DA ERRORE
	    // .. IL VALIDATORE DELL'OGGETTO DI DOMINIO
	    importoIndividualePosteggio = importoIndividualePosteggio.setScale(2, BigDecimal.ROUND_HALF_UP);
	    riga.setImporto(importoIndividualePosteggio);
	    Integer iva = importoHelperBean.getConto().getIva();
	    if (iva == null) {
		throw new RuntimeException("Errore nella configurazione dei conti della contabilita'. Non e' stata definita l'iva per il conto " +
			importoHelperBean.getConto().getDescrizione() +
			"(" +
			importoHelperBean.getConto().getId() +
			")");
	    }
	    riga.setIva(iva);
	    righeImportiPosteggio.add(riga);
	}
	result.setListaImporti(righeImportiPosteggio);
	return result;
    }

    private BigDecimal recuperaImportoRidettato(BigDecimal importo, BigDecimal percConvenzione) {

	if (percConvenzione != null && percConvenzione.compareTo(BigDecimal.ZERO) != 0 && importo != null
		&& importo.compareTo(BigDecimal.ZERO) != 0) {
	    return importo.multiply(percConvenzione).divide(BigDecimal.valueOf(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	return importo;
    }
}
