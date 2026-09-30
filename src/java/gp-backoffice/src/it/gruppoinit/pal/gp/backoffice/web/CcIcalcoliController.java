package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.CcCausaliriduzionir;
import it.gruppoinit.pal.gp.core.domain.CcCausaliriduzionit;
import it.gruppoinit.pal.gp.core.domain.CcClassisuperfici;
import it.gruppoinit.pal.gp.core.domain.CcCoeffcontributo;
import it.gruppoinit.pal.gp.core.domain.CcDestinazioni;
import it.gruppoinit.pal.gp.core.domain.CcDettaglisuperficie;
import it.gruppoinit.pal.gp.core.domain.CcIcalcoli;
import it.gruppoinit.pal.gp.core.domain.CcIcalcoloDcontributo;
import it.gruppoinit.pal.gp.core.domain.CcIcalcoloTcontributo;
import it.gruppoinit.pal.gp.core.domain.CcIcalcolotcontributoRiduz;
import it.gruppoinit.pal.gp.core.domain.CcIcalcolotot;
import it.gruppoinit.pal.gp.core.domain.CcItabella1;
import it.gruppoinit.pal.gp.core.domain.CcItabella2;
import it.gruppoinit.pal.gp.core.domain.CcItabella3;
import it.gruppoinit.pal.gp.core.domain.CcItabella4;
import it.gruppoinit.pal.gp.core.domain.CcTabella3;
import it.gruppoinit.pal.gp.core.domain.CcTabellaCaratterist;
import it.gruppoinit.pal.gp.core.domain.CcTabellaClassiedificio;
import it.gruppoinit.pal.gp.core.domain.CcTipointervento;
import it.gruppoinit.pal.gp.core.domain.CcValiditacoefficienti;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.web.CalcoloCoefficientiCommand;
import it.gruppoinit.pal.gp.core.domain.web.CcIcalcoliHelper;
import it.gruppoinit.pal.gp.core.service.CcCausaliriduzionirService;
import it.gruppoinit.pal.gp.core.service.CcCausaliriduzionitService;
import it.gruppoinit.pal.gp.core.service.CcCoeffcontributoService;
import it.gruppoinit.pal.gp.core.service.CcDestinazioniService;
import it.gruppoinit.pal.gp.core.service.CcIcalcoliService;
import it.gruppoinit.pal.gp.core.service.CcIcalcoloTcontributoService;
import it.gruppoinit.pal.gp.core.service.CcIcalcolototService;
import it.gruppoinit.pal.gp.core.service.CcTabellaClassiedificioService;
import it.gruppoinit.pal.gp.core.service.CcTipointerventoService;
import it.gruppoinit.pal.gp.core.service.CcValiditacoefficientiService;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author
 */
@Controller
public class CcIcalcoliController extends BaseController<CcIcalcoli> {

    private static final Logger log = LoggerFactory.getLogger(CcIcalcoliController.class);
    private static final String CCICALCOLOTOTID_REQUEST_PARAM = "codiceI";
    @Autowired
    private CcIcalcoliService ccicalcoliService;
    @Autowired
    private CcTabellaClassiedificioService classiEdificioService;
    @Autowired
    private CcValiditacoefficientiService coeffService;
    @Autowired
    private CcIcalcolototService calcoloTotService;
    @Autowired
    private CcIcalcoloTcontributoService tContributoService;
    @Autowired
    private CcDestinazioniService destinazioniService;
    @Autowired
    private CcTipointerventoService tipiInterventoService;
    @Autowired
    private CcCausaliriduzionitService causaliRiuduzioniTService;
    @Autowired
    private CcCausaliriduzionirService causaliRiuduzioniRService;
    @Autowired
    private CcCoeffcontributoService coeffContributoservice;

    /*
    @Autowired
    private SoftwareService softwareService;
    */
    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<CcIcalcoli> ccicalcoliList = ccicalcoliService.findAll(null, null);
    // ModelMap model = new ModelMap(ccicalcoliList);
    // boolean export = createJMesaExport(request, response, ccicalcoliList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("ccicalcoliList", ccicalcoliList);
    // return model;
    // }
    //
    @RequestMapping
    public String calcoloCostoCostruzione(HttpServletRequest request, Model model, @ModelAttribute("ccicalcoli") CcIcalcoliHelper dati,
	    BindingResult result) {

	String retValOk = "ccicalcoli/calcoloCostoCostruzione";
	//TODO valorizzare la variabile sotto con il path della pagina chiamante.
	String retValKo = "ccicalcoli/calcoloCostoCostruzione";
	String retVal = retValOk;
	String errMsg = null;
	String idCalcoloTot = request.getParameter(CCICALCOLOTOTID_REQUEST_PARAM);
	//la chiamata riceve sempre come parametro un ID di CC_ICALCOLOTOT. Derivare da li sia l'ID dell'istanza sia il listino CC_VALIDITACOEFFICIENTI da utilizzare nel calcolo,
	CcIcalcolotot icalcolotot = null;
	if (StringUtils.isBlank(idCalcoloTot)) {
	    errMsg = "Specificare un riferimento alla testata del calcolo degli oneri utilizzando il parametro 'codice' dell'URL.";
	} else {
	    Integer paramId = null;
	    try {
		paramId = Integer.parseInt(idCalcoloTot);
	    } catch (NumberFormatException e) {
		errMsg = "L'ID della testata del calcolo degli oneri deve essere espresso come un numero intero.";
	    }
	    icalcolotot = calcoloTotService.findById(new PkId(paramId));
	    if (icalcolotot == null) {
		errMsg = MessageFormat.format(
			"Il riferimento alla testata del calcolo oneri non è valido. CC_ICALCOLOTOT.ID = {0} non esiste per questo comune.", paramId);
	    }
	}
	if (errMsg == null) {
	    //TODO recuperare ccicalcoli da ccicalcolotot se esiste.
	    CcIcalcoloTcontributo contributoCalcolo = null;
	    //CcIcalcoliHelper dati = null;
	    Set<CcIcalcoloTcontributo> contributiCalcolo = icalcolotot.getCcIcalcoloTcontributos();
	    CcValiditacoefficienti listino = icalcolotot.getCcValiditacoefficienti();
	    if (contributiCalcolo != null && contributiCalcolo.size() > 0) {
		contributoCalcolo = contributiCalcolo.iterator().next();
		dati = caricaFoglioCalcolo(icalcolotot, listino, dati);
	    } else {
		contributoCalcolo = new CcIcalcoloTcontributo();
		contributoCalcolo.setCostocEdificio(BigDecimal.ZERO);
		dati = preparaFoglioCalcoloVuoto(listino, dati);
	    }
	    fixRenderEntityProperty(dati.getTotali());
	    model.addAttribute("icalcolotcontributo", contributoCalcolo);
	    model.addAttribute("testatacalcoli", icalcolotot);
	    model.addAttribute("classiedificio", classiEdificioService.listByIntervallo());
	    //model.addAttribute("listini", coeffService.listByDataValidita());
	    //model.addAttribute("listinodefault", coeffService.findValidoAllaData(new Date()));
	    model.addAttribute("listino", listino);
	} else {
	    retVal = retValKo;
	    result.reject(errMsg);
	}
	setPageAttributes(model);
	return retVal;
    }

    @RequestMapping
    public String salvaCalcoloCostoCostruzione(Model model, HttpServletRequest request) {

	String errMsg = null;
	String calcoloTotIdParam = request.getParameter("ccicalcolotot_id");
	String retVal = "redirect:caricaContributo.htm?ccicalcolotot_id=" + calcoloTotIdParam;
	Integer calcoloTotId = null;
	if (!StringUtils.isBlank(calcoloTotIdParam)) {
	    try {
		calcoloTotId = Integer.parseInt(calcoloTotIdParam);
	    } catch (NumberFormatException e) {
		errMsg = "L'id della testata del calcolo non ha un formato numerico valido.";
	    }
	} else {
	    errMsg = "Riferimento alla testata dei calcoli mancante.";
	}
	CcIcalcolotot icalcolotot = null;
	if (errMsg == null) {
	    icalcolotot = calcoloTotService.findById(new PkId(calcoloTotId));
	    if (icalcolotot == null) {
		errMsg = "Impossibile trovare nella base dati la testata calcoli avente id = {0}";
		errMsg = MessageFormat.format(errMsg, calcoloTotId);
	    }
	}
	if (StringUtils.isBlank(errMsg)) {
	    //salvo o aggiorno il record in CC_ICALCOLO_TCONTRIBUTO
	    CcValiditacoefficienti coeff = icalcolotot.getCcValiditacoefficienti();
	    CcIcalcoloTcontributo contributoCalcolo = null;
	    CcIcalcoli calcolo = null;
	    Set<CcIcalcoloTcontributo> contributiCalcolo = icalcolotot.getCcIcalcoloTcontributos();
	    if (contributiCalcolo != null && contributiCalcolo.size() > 0) {
		contributoCalcolo = contributiCalcolo.iterator().next();
		calcolo = contributoCalcolo.getCcIcalcoli();
	    } else {
		contributoCalcolo = new CcIcalcoloTcontributo();
		contributoCalcolo.setCcIcalcolotot(icalcolotot);
		contributoCalcolo.setStato("P");
		contributoCalcolo.setCoefficiente(coeff.getCostomq());
		contributoCalcolo.setIstanze(icalcolotot.getIstanze());
		calcolo = new CcIcalcoli();
		calcolo.setIstanze(icalcolotot.getIstanze());
		Set<CcIcalcoloTcontributo> tContributi = new HashSet<CcIcalcoloTcontributo>();
		tContributi.add(contributoCalcolo);
		calcolo.setCcIcalcoloTcontributos(tContributi);
	    }
	    Integer fkClasseEdificio = getIntegerParameter("fkclasseedificio", request);
	    CcTabellaClassiedificio classeEdif = classiEdificioService.findById(new PkId(fkClasseEdificio));
	    calcolo.setCcTabellaClassiedificio(classeEdif);
	    calcolo.setCostocmq(getBigDecimalParameter("costomq", request));
	    calcolo.setCostocmqMaggiorato(getBigDecimalParameter("costomqmaggiorato", request));
	    calcolo.setI1(getBigDecimalParameter("tab1_tot1", request));
	    calcolo.setI2(getBigDecimalParameter("tab3_tot0", request));
	    calcolo.setI3(getBigDecimalParameter("tab4_tot0", request));
	    calcolo.setMaggiorazione(getBigDecimalParameter("maggiorazione", request));
	    calcolo.setSu(getBigDecimalParameter("tab1_tot0", request));
	    calcolo.setSnr(getBigDecimalParameter("tab2_tot0", request));
	    calcolo.setSc(getBigDecimalParameter("tab4_sc", request));
	    calcolo.setSuArt9(getBigDecimalParameter("tab5_sn", request));
	    calcolo.setSa(getBigDecimalParameter("tab5_sa", request));
	    calcolo.setSt(getBigDecimalParameter("tab5_st", request));
	    contributoCalcolo.setCostocEdificio(getBigDecimalParameter("costocostruzione", request));
	    List<CcItabella1> tab1 = leggiTabella1(request, icalcolotot.getIstanze());
	    List<CcItabella2> tab2 = leggiTabella2(request, icalcolotot.getIstanze());
	    List<CcItabella3> tab3 = leggiTabella3(request, icalcolotot.getIstanze());
	    List<CcItabella4> tab4 = leggiTabella4(request, icalcolotot.getIstanze());
	    calcolo.setCcItabella1s(new HashSet<CcItabella1>(tab1));
	    calcolo.setCcItabella2s(new HashSet<CcItabella2>(tab2));
	    calcolo.setCcItabella3s(new HashSet<CcItabella3>(tab3));
	    calcolo.setCcItabella4s(new HashSet<CcItabella4>(tab4));
	    ccicalcoliService.insertOrUpdate(calcolo);
	    //TODO INTERCETTARE ECCEZIONE E EGESTIRLA
	} else {
	    retVal = "ccicalcoli/calcoloCostoCostruzione";
	}
	return retVal;
    }

    @RequestMapping
    public String caricaContributo(Model model, HttpServletRequest request) {

	String retVal = "ccicalcoli/calcoloAliquota";
	String errMsg = null;
	String calcoloTotIdParam = request.getParameter("ccicalcolotot_id");
	Integer calcoloTotId = null;
	if (!StringUtils.isBlank(calcoloTotIdParam)) {
	    try {
		calcoloTotId = Integer.parseInt(calcoloTotIdParam);
	    } catch (NumberFormatException e) {
		errMsg = "L'id della testata del calcolo non ha un formato numerico valido.";
	    }
	} else {
	    errMsg = "Riferimento alla testata dei calcoli mancante.";
	}
	CcIcalcolotot icalcolotot = null;
	if (errMsg == null) {
	    icalcolotot = calcoloTotService.findById(new PkId(calcoloTotId));
	    if (icalcolotot == null) {
		errMsg = "Impossibile trovare nella base dati la testata calcoli avente id = {0}";
		errMsg = MessageFormat.format(errMsg, calcoloTotId);
	    }
	}
	if (StringUtils.isBlank(errMsg)) {
	    //carico i dati relativi al calcolo dei coefficienti e il totale contributo eventualmente già presenti nel DB
	    CcValiditacoefficienti coeff = icalcolotot.getCcValiditacoefficienti();
	    CcIcalcoloTcontributo contributoCalcolo = null;
	    Set<CcIcalcoloTcontributo> contributiCalcolo = icalcolotot.getCcIcalcoloTcontributos();
	    if (contributiCalcolo != null && contributiCalcolo.size() > 0) {
		contributoCalcolo = contributiCalcolo.iterator().next();
	    } else {
		//TODO condizione di errore: il record in CC_ICALCOLO_TCONTRIBUTO deve già esistere per caricare questa pagina
	    }
	    //caricamento lista destinazioni per pagina coefficienti 
	    List<CcDestinazioni> destinazioni = destinazioniService.findAll(0, Integer.MAX_VALUE);
	    //caricamento dei tipi intervento per la pagina dei coefficienti
	    List<CcTipointervento> tipiIntervento = tipiInterventoService.findAll(0, Integer.MAX_VALUE);
	    //caricamento di CC_CAUSALIRIDUZIONIT e CC_CAUSALIRIDUZIONIR
	    List<CcCausaliriduzionit> causaliRiduzioniT = causaliRiuduzioniTService.findAll(0, Integer.MAX_VALUE);
	    //causaliRiuduzioniRService.
	    model.addAttribute("destinazioni", destinazioni);
	    model.addAttribute("tipiintervento", tipiIntervento);
	    model.addAttribute("causaliriduzionit", causaliRiduzioniT);
	    model.addAttribute("validitacoefficiente", coeff);
	    model.addAttribute("calcolotcontributo", contributoCalcolo);
	    Set<CcIcalcoloDcontributo> calcoliDContributi = contributoCalcolo.getCcIcalcoloDcontributos();
	    Integer idTipoIntervento = 0;
	    if (calcoliDContributi.size() > 0) {
		CcIcalcoloDcontributo dContrib = calcoliDContributi.iterator().next();
		if (dContrib.getCcTipointervento() != null) {
		    idTipoIntervento = dContrib.getCcTipointervento().getId().getCodice();
		}
	    }
	    model.addAttribute("idtipointervento", idTipoIntervento);
	} else {
	    retVal = "ccicalcoli/calcoloCostoCostruzione";
	}
	return retVal;
    }

    @RequestMapping
    public void caricaCoefficiente(@ModelAttribute("calcolocoefficiente") CalcoloCoefficientiCommand cmd, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {

	CcDestinazioni dest = destinazioniService.findById(new PkId(cmd.getIdDestinazione()));
	CcValiditacoefficienti validCoeff = coeffService.findById(new PkId(cmd.getIdValiditaCoefficiente()));
	CcTipointervento tipoInter = tipiInterventoService.findById(new PkId(cmd.getIdTipoIntervento()));
	BigDecimal retCoeff = BigDecimal.ZERO;
	boolean searchCoeff = true;
	if (dest == null) {
	    log.warn("caricaCoefficiente() - impossibile trovare nella base dati la destinazione con id {} sarà applicato il coefficiente 0.0",
		    new Object[] { cmd.getIdDestinazione() });
	    searchCoeff = false;
	}
	if (validCoeff == null) {
	    log.warn(
		    "caricaCoefficiente() - impossibile trovare nella base dati il record di validità coefficienti con id {} sarà applicato il coefficiente 0.0",
		    new Object[] { cmd.getIdValiditaCoefficiente() });
	    searchCoeff = false;
	}
	if (tipoInter == null) {
	    log.warn("caricaCoefficiente() - impossibile trovare nella base dati il tipo intervento con id {} sarà applicato il coefficiente 0.0",
		    new Object[] { cmd.getIdTipoIntervento() });
	    searchCoeff = false;
	}
	if (searchCoeff) {
	    CcCoeffcontributo coeffContributo = coeffContributoservice.findByCoefficienteAndccDestinazioniAndccTipointervento(validCoeff, dest,
		    tipoInter);
	    if (coeffContributo != null) {
		retCoeff = coeffContributo.getCoefficiente();
	    } else {
		log.warn(
			"caricaCoefficiente() - impossibile trovare nella base dati il coefficiente associato a destinazione:{}, tipo intervento:{} e validità coefficienti:{}. Sarà utilizzato un coefficiente 0.0",
			new Object[] { cmd.getIdDestinazione(), cmd.getIdTipoIntervento(), cmd.getIdValiditaCoefficiente() });
	    }
	}
	StringBuilder sbOut = new StringBuilder("{coefficiente:").append(retCoeff.toPlainString()).append("}");
	response.setDateHeader("Expires", (System.currentTimeMillis() + 1000));
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setContentType("application/json");
	response.setContentLength(sbOut.length());
	PrintWriter pw = response.getWriter();
	pw.write(sbOut.toString());
	pw.flush();
	pw.close();
    }

    @RequestMapping
    public String salvaContributo(@ModelAttribute("calcolocoefficiente") CalcoloCoefficientiCommand cmd, HttpServletRequest request) {

	//String retVal = "ccicalcoli/calcoloAliquota";
	CcIcalcoloTcontributo tContrib = tContributoService.findById(new PkId(cmd.getIdContributoCalcolo()));
	String retVal = "redirect:caricaContributo.htm?ccicalcolotot_id=";
	if (tContrib != null) {
	    tContrib.setCoefficiente(new BigDecimal(cmd.getCoefficientetot()));
	    CcDestinazioni dest = destinazioniService.findById(new PkId(cmd.getIdDestinazione()));
	    tContrib.setCcDestinazioni(dest);
	    CcIcalcoloDcontributo dContrib = null;
	    Set<CcIcalcoloDcontributo> dContibs = tContrib.getCcIcalcoloDcontributos();
	    if (dContibs.size() > 0) {
		dContrib = dContibs.iterator().next();
	    } else {
		dContrib = new CcIcalcoloDcontributo();
		dContrib.setCcIcalcoloTcontributo(tContrib);
		dContrib.setIstanze(tContrib.getIstanze());
		dContibs.add(dContrib);
	    }
	    CcTipointervento tipoInt = tipiInterventoService.findById(new PkId(cmd.getIdTipoIntervento()));
	    dContrib.setCcTipointervento(tipoInt);
	    dContrib.setCoefficiente(new BigDecimal(cmd.getCoefficiente()));
	    //imposto il totale contributo in CcIcalcolotot
	    CcIcalcolotot calcoloTot = tContrib.getCcIcalcolotot();
	    calcoloTot.setQuotacontribTotale(new BigDecimal(cmd.getContributotot()));
	    retVal += calcoloTot.getId().getCodice();
	    //leggo le causaliriduzioniR selezionate dall'utente
	    List<CcCausaliriduzionit> causaliRiduzioniT = causaliRiuduzioniTService.findAll(0, Integer.MAX_VALUE);
	    CcCausaliriduzionit causaleRidT = null;
	    CcCausaliriduzionir causaleRidR = null;
	    Set<CcIcalcolotcontributoRiduz> riduzioniCoefficiente = new HashSet<CcIcalcolotcontributoRiduz>();
	    CcIcalcolotcontributoRiduz riduz = null;
	    for (int i = 0; i < causaliRiduzioniT.size(); i++) {
		causaleRidT = causaliRiduzioniT.get(i);
		String paramName = "idcausaleriduzioner_" + causaleRidT.getId().getCodice().intValue();
		String parVal = request.getParameter(paramName);
		if (StringUtils.isNotBlank(parVal)) {
		    Integer idCausaleRiduzioneR = null;
		    idCausaleRiduzioneR = Integer.parseInt(parVal);
		    causaleRidR = new CcCausaliriduzionir();
		    causaleRidR.setId(new PkId(idCausaleRiduzioneR));
		    riduz = new CcIcalcolotcontributoRiduz();
		    riduz.setCcCausaliriduzionir(causaleRidR);
		    riduz.setCcIcalcoloTcontributo(tContrib);
		    riduz.setIstanze(tContrib.getIstanze());
		    String riduzCoeffParVal = request.getParameter("riduzione_" + causaleRidT.getId().getCodice());
		    BigDecimal riduzCoeff = BigDecimal.ONE;
		    if (StringUtils.isNotEmpty(riduzCoeffParVal)) {
			riduzCoeff = new BigDecimal(riduzCoeffParVal);
		    }
		    riduz.setRiduzioneperc(riduzCoeff);
		    riduzioniCoefficiente.add(riduz);
		}
	    }
	    //model.addAttribute("message", "Il calcolo del contributo è stato salvato con successo.");
	    ccicalcoliService.salvaCoefficienteContributo(tContrib, riduzioniCoefficiente);
	    retVal = retVal.concat("&status_msg=01");
	} else {
	    throw new RuntimeException(MessageFormat.format("Impossibile trovare i la testata del calcolo contributo avente Id = {}",
		    new Object[] { cmd.getIdContributoCalcolo() }));
	}
	return retVal;
    }

    private CcIcalcoliHelper preparaFoglioCalcoloVuoto(CcValiditacoefficienti listino, CcIcalcoliHelper populateThis) {

	//predispongo tutte le righe delle tabelle in relazione, solo quelle che servono per la demo.
	CcIcalcoliHelper retVal = populateThis == null ? new CcIcalcoliHelper() : populateThis;
	CcIcalcoli calcoli = new CcIcalcoli();
	calcoli.setSu(BigDecimal.ZERO);
	calcoli.setSnr(BigDecimal.ZERO);
	calcoli.setSc(BigDecimal.ZERO);
	calcoli.setSuArt9(BigDecimal.ZERO);
	calcoli.setSa(BigDecimal.ZERO);
	calcoli.setSt(BigDecimal.ZERO);
	calcoli.setI1(BigDecimal.ZERO);
	calcoli.setI2(BigDecimal.ZERO);
	calcoli.setI3(BigDecimal.ZERO);
	BigDecimal costomq = BigDecimal.ZERO;
	if (listino != null && listino.getCostomq() != null) {
	    costomq = listino.getCostomq();
	}
	calcoli.setCostocmq(costomq);
	calcoli.setCostocmqMaggiorato(costomq);
	calcoli.setMaggiorazione(BigDecimal.ZERO);
	retVal.setTotali(calcoli);
	retVal.setTab1(preparaTabella1Vuota());
	retVal.setTab2(preparaTabella2Vuota());
	retVal.setTab3(preparaTabella3Vuota());
	retVal.setTab4(preparaTabella4Vuota());
	return retVal;
    }

    //TODO attualmente la funzionalità è allo stato di DEMO perciò le righe sono statiche e non caricate dinamicamente
    private List<CcItabella1> preparaTabella1Vuota() {

	List<CcItabella1> tab1 = new ArrayList<CcItabella1>(5);
	CcItabella1 tabRow = null;
	CcClassisuperfici classeSup = null;
	int[] incrementi = new int[] { 0, 5, 15, 30, 50 };
	for (int i = 0; i < incrementi.length; i++) {
	    tabRow = new CcItabella1();
	    tabRow.setAlloggi((short) 0);
	    tabRow.setSu(BigDecimal.ZERO);
	    tabRow.setRapportoSu(BigDecimal.ZERO);
	    tabRow.setIncremento(new BigDecimal(incrementi[i]));
	    tabRow.setIncrementoxclassi(BigDecimal.ZERO);
	    classeSup = new CcClassisuperfici();
	    //classi superfici fisse ==> devono esistere con ID 1,2,3,4,5 e incrementi 0,5,15,30,50
	    classeSup.setId(new PkId(i));
	    tabRow.setCcClassisuperfici(classeSup);
	    tab1.add(tabRow);
	}
	return tab1;
    }

    private List<CcItabella2> preparaTabella2Vuota() {

	List<CcItabella2> tab2 = new ArrayList<CcItabella2>();
	CcItabella2 tabRow = null;
	CcDettaglisuperficie detSup = null;
	int[] dettSupIds = new int[] { 4, 3, 2, 1, 5 };
	for (int i = 0; i < dettSupIds.length; i++) {
	    tabRow = new CcItabella2();
	    tabRow.setSuperficie(BigDecimal.ZERO);
	    detSup = new CcDettaglisuperficie();
	    detSup.setId(new PkId(dettSupIds[i]));
	    tabRow.setCcDettaglisuperficie(detSup);
	    tab2.add(tabRow);
	}
	return tab2;
    }

    private List<CcItabella3> preparaTabella3Vuota() {

	List<CcItabella3> tab3 = new ArrayList<CcItabella3>();
	CcItabella3 tabRow = null;
	CcTabella3 tab3Cfg = null;
	int[] tab3CfgIds = new int[] { 1, 2, 3, 4 };
	for (int i = 0; i < tab3CfgIds.length; i++) {
	    tabRow = new CcItabella3();
	    tabRow.setIncremento(new BigDecimal(i * 10));
	    tabRow.setIpotesichericorre(false);
	    tab3Cfg = new CcTabella3();
	    tab3Cfg.setId(new PkId(tab3CfgIds[i]));
	    tabRow.setCcTabella3(tab3Cfg);
	    tab3.add(tabRow);
	}
	return tab3;
    }

    private List<CcItabella4> preparaTabella4Vuota() {

	List<CcItabella4> tab4 = new ArrayList<CcItabella4>();
	CcItabella4 tabRow = null;
	CcTabellaCaratterist caratt = null;
	int[] carattIds = new int[] { 5, 3, 4, 1, 2 };
	for (int i = 0; i < carattIds.length; i++) {
	    tabRow = new CcItabella4();
	    tabRow.setIncremento(BigDecimal.TEN);
	    tabRow.setSelezionata(false);
	    caratt = new CcTabellaCaratterist();
	    caratt.setId(new PkId(carattIds[i]));
	    tabRow.setCcTabellaCaratterist(caratt);
	    tab4.add(tabRow);
	}
	return tab4;
    }

    private CcIcalcoliHelper caricaFoglioCalcolo(CcIcalcolotot testataCalcolo, CcValiditacoefficienti listino, CcIcalcoliHelper populateThis) {

	CcIcalcoliHelper retVal = populateThis == null ? new CcIcalcoliHelper() : populateThis;
	if (testataCalcolo == null || testataCalcolo.getCcIcalcoloTcontributos().size() == 0) {
	    retVal = preparaFoglioCalcoloVuoto(listino, populateThis);
	}
	//caricamento totali, e righe tabelle dal DB
	else {
	    //retVal = new CcIcalcoliHelper();
	    Iterator<CcIcalcoloTcontributo> iterContributiCalcolo = testataCalcolo.getCcIcalcoloTcontributos().iterator();
	    CcIcalcoloTcontributo contributoCalcolo = iterContributiCalcolo.next();
	    CcIcalcoli calcoliTot = contributoCalcolo.getCcIcalcoli();
	    retVal.setTotali(calcoliTot);
	    List<CcItabella1> tab1 = ccicalcoliService.findRigheTabella1(calcoliTot);
	    retVal.setTab1(tab1);
	    List<CcItabella2> tab2 = ccicalcoliService.findRigheTabella2(calcoliTot);
	    retVal.setTab2(tab2);
	    List<CcItabella3> tab3 = ccicalcoliService.findRigheTabella3(calcoliTot);
	    retVal.setTab3(tab3);
	    List<CcItabella4> tab4 = ccicalcoliService.findRigheTabella4(calcoliTot);
	    retVal.setTab4(tab4);
	}
	return retVal;
    }

    private List<CcItabella1> leggiTabella1(HttpServletRequest request, Istanze istanza) {

	List<CcItabella1> tab = new ArrayList<CcItabella1>();
	CcItabella1 tabRow = null;
	for (int i = 0; i < 5; i++) {
	    tabRow = new CcItabella1();
	    Integer rowId = getIntegerParameter("tab1_" + i + "id", request);
	    if (rowId != null && rowId.intValue() > 0) {
		tabRow.getId().setCodice(rowId);
	    }
	    tabRow.setAlloggi(getIntegerParameter("tab1_" + i + "0", request).shortValue());
	    tabRow.setSu(getBigDecimalParameter("tab1_" + i + "1", request));
	    tabRow.setRapportoSu(getBigDecimalParameter("tab1_" + i + "2", request));
	    tabRow.setIncremento(getBigDecimalParameter("tab1_" + i + "3", request));
	    tabRow.setIncrementoxclassi(getBigDecimalParameter("tab1_" + i + "4", request));
	    Integer idClasseSup = getIntegerParameter("tab1_" + i + "csid", request);
	    CcClassisuperfici classeSup = new CcClassisuperfici();
	    classeSup.setId(new PkId(idClasseSup));
	    tabRow.setCcClassisuperfici(classeSup);
	    tabRow.setIstanze(istanza);
	    tab.add(tabRow);
	}
	return tab;
    }

    private List<CcItabella2> leggiTabella2(HttpServletRequest request, Istanze istanza) {

	List<CcItabella2> tab = new ArrayList<CcItabella2>();
	CcItabella2 tabRow = null;
	for (int i = 0; i < 5; i++) {
	    tabRow = new CcItabella2();
	    Integer rowId = getIntegerParameter("tab2_" + i + "id", request);
	    if (rowId != null && rowId.intValue() > 0) {
		tabRow.getId().setCodice(rowId);
	    }
	    tabRow.setSuperficie(getBigDecimalParameter("tab2_" + i + "0", request));
	    Integer idDettSup = getIntegerParameter("tab2_" + i + "dsid", request);
	    CcDettaglisuperficie dettSup = new CcDettaglisuperficie();
	    dettSup.setId(new PkId(idDettSup));
	    tabRow.setCcDettaglisuperficie(dettSup);
	    tabRow.setIstanze(istanza);
	    tab.add(tabRow);
	}
	return tab;
    }

    private List<CcItabella3> leggiTabella3(HttpServletRequest request, Istanze istanza) {

	List<CcItabella3> tab = new ArrayList<CcItabella3>();
	CcItabella3 tabRow = null;
	for (int i = 0; i < 4; i++) {
	    tabRow = new CcItabella3();
	    Integer rowId = getIntegerParameter("tab3_" + i + "id", request);
	    if (rowId != null && rowId.intValue() > 0) {
		tabRow.getId().setCodice(rowId);
	    }
	    tabRow.setIncremento(getBigDecimalParameter("tab3_" + i + "2", request));
	    BigDecimal valoreIpotesi = getBigDecimalParameter("tab3_" + i + "1", request);
	    Boolean ipotesiCheRicorre = false;
	    if (valoreIpotesi != null && valoreIpotesi.doubleValue() > 0.0) {
		ipotesiCheRicorre = true;
	    }
	    tabRow.setIpotesichericorre(ipotesiCheRicorre);
	    CcTabella3 confTab3 = new CcTabella3();
	    confTab3.setId(new PkId(i + 1));
	    tabRow.setCcTabella3(confTab3);
	    tabRow.setIstanze(istanza);
	    tab.add(tabRow);
	}
	return tab;
    }

    private List<CcItabella4> leggiTabella4(HttpServletRequest request, Istanze istanza) {

	List<CcItabella4> tab = new ArrayList<CcItabella4>();
	CcItabella4 tabRow = null;
	for (int i = 0; i < 5; i++) {
	    tabRow = new CcItabella4();
	    Integer rowId = getIntegerParameter("tab4_" + i + "id", request);
	    if (rowId != null && rowId.intValue() > 0) {
		tabRow.getId().setCodice(rowId);
	    }
	    tabRow.setIncremento(getBigDecimalParameter("tab4_" + i + "1", request));
	    Integer idDettSup = getIntegerParameter("tab4_" + i + "carattid", request);
	    CcTabellaCaratterist tabCaratt = new CcTabellaCaratterist();
	    tabCaratt.setId(new PkId(idDettSup));
	    tabRow.setCcTabellaCaratterist(tabCaratt);
	    BigDecimal valoreIpotesi = getBigDecimalParameter("tab4_" + i + "0", request);
	    Boolean selected = false;
	    if (valoreIpotesi != null) {
		selected = true;
	    }
	    tabRow.setSelezionata(selected);
	    tabRow.setIstanze(istanza);
	    tab.add(tabRow);
	}
	return tab;
    }

    private BigDecimal getBigDecimalParameter(String paramName, HttpServletRequest request) {

	BigDecimal retVal = null;
	String paramVal = request.getParameter(paramName);
	if (paramVal != null) {
	    if (StringUtils.isBlank(paramVal)) {
		log.warn("Attenzione Il parametro della request {} è vuoto, gli sarà assegnato valore 0.0", new Object[] { paramName });
	    } else {
		retVal = new BigDecimal(paramVal);
	    }
	} else {
	    log.warn("Attenzione Il parametro della request {} non esiste!", new Object[] { paramName });
	}
	return retVal;
    }

    private Integer getIntegerParameter(String paramName, HttpServletRequest request) {

	Integer retVal = null;
	String paramVal = request.getParameter(paramName);
	if (paramVal != null) {
	    if (StringUtils.isBlank(paramVal)) {
		log.warn("Attenzione Il parametro della request {} è vuoto, gli sarà assegnato valore 0", new Object[] { paramName });
	    } else {
		retVal = Integer.parseInt(paramVal);
	    }
	} else {
	    log.warn("Attenzione Il parametro della request {} non esiste!", new Object[] { paramName });
	}
	return retVal;
    }

    //
    // @RequestMapping
    // public String insert(@ModelAttribute("ccicalcoli") CcIcalcoli ccicalcoli, BindingResult result, SessionStatus
    // status) {
    //
    // fixMergeEntityProperty(ccicalcoli);
    // ccicalcoli.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
    // try {
    // ccicalcoliService.insert(ccicalcoli);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(ccicalcoliService.getValidationMessages(), result, ccicalcoli, e.getMessage());
    // fixRenderEntityProperty(ccicalcoli);
    // return "ccicalcoli/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + ccicalcoli.getId().getCodice() + "&status_msg=01";
    // }
    //
    // @RequestMapping
    // public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    // PkId id = new PkId(codice);
    // CcIcalcoli ccicalcoli = ccicalcoliService.findById(id);
    // fixRenderEntityProperty(ccicalcoli);
    // model.addAttribute("ccicalcoli", ccicalcoli);
    // setPageAttributes(model);
    // return "ccicalcoli/form";
    // }
    //
    // @RequestMapping
    // public String update(@ModelAttribute("ccicalcoli") CcIcalcoli ccicalcoli, BindingResult result, SessionStatus
    // status, HttpServletRequest request) {
    //
    // fixMergeEntityProperty(ccicalcoli);
    // try {
    // ccicalcoliService.update(ccicalcoli);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(ccicalcoliService.getValidationMessages(), result, ccicalcoli, e.getMessage());
    // fixRenderEntityProperty(ccicalcoli);
    // return "ccicalcoli/form";
    // }
    // status.setComplete();
    // return "redirect:view.htm?codice=" + ccicalcoli.getId().getCodice() + "&status_msg=02";
    // }
    //
    // @RequestMapping
    // public String delete(@ModelAttribute("ccicalcoli") CcIcalcoli ccicalcoli, BindingResult result, SessionStatus
    // status) {
    //
    // CcIcalcoli objToDelete = ccicalcoliService.findById(ccicalcoli.getId());
    // try {
    // ccicalcoliService.delete(objToDelete);
    // } catch (Exception e) {
    // copyErrorsToBindingResult(ccicalcoliService.getValidationMessages(), result, objToDelete, e.getMessage());
    // fixRenderEntityProperty(ccicalcoli);
    // return "ccicalcoli/form";
    // }
    // status.setComplete();
    // return "redirect:list.htm";
    // }
    @Override
    protected void fixMergeEntityProperty(CcIcalcoli entity) {

    }

    @Override
    protected void fixRenderEntityProperty(CcIcalcoli entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
