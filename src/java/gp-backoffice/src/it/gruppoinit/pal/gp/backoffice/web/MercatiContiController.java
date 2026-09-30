package it.gruppoinit.pal.gp.backoffice.web;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Conti;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiCfgAttivita;
import it.gruppoinit.pal.gp.core.domain.MercatiConti;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiDConti;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeD;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.ConfigurazioneContiMercato;
import it.gruppoinit.pal.gp.core.domain.helper.MercatiContiHelper;
import it.gruppoinit.pal.gp.core.domain.helper.MercatiFormuleCalcoloContestoEnum;
import it.gruppoinit.pal.gp.core.domain.helper.PosteggioImportoHelper;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calcolo.ICalcoloCostoPosteggiService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.MercatipresenzeDService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.configurazione.MercatiDService;
import it.gruppoinit.pal.gp.core.features.oneri.ContiService;
import it.gruppoinit.pal.gp.core.service.MercatiCfgAttivitaService;
import it.gruppoinit.pal.gp.core.service.MercatiContiService;
import it.gruppoinit.pal.gp.core.service.MercatiDContiService;
import it.gruppoinit.pal.gp.core.service.MercatiService;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;

@Controller
@SessionAttributes(value = { "mercatiConti", "configurazioneContiMercato" })
public class MercatiContiController extends BaseController<MercatiConti> {

    private static final Logger log = LoggerFactory.getLogger(MercatiContiController.class);
    @Autowired
    private MercatiContiService mercatiContiService;
    @Autowired
    private MercatiDContiService mercatiDContiService;
    @Autowired
    private MercatiService mercatiService;
    @Autowired
    private ContiService contiService;
    @Autowired
    private MercatiDService mercatiDService;
    @Autowired
    private MercatiCfgAttivitaService mercatiCfgAttivitaService;
    @Autowired
    private MercatipresenzeDService mercatipresenzeDService;
    @Autowired
    private ICalcoloCostoPosteggiService calcoloCostoPosteggiService;

    @RequestMapping
    public ModelMap list(@RequestParam("mercati.id.codice") Integer codiceMercato, HttpServletRequest request, HttpServletResponse response) {

	if (codiceMercato == null) {
	    log.error("Il parametro Codice Mercato è obbligatorio");
	    throw new SecurityException("Il parametro Codice Mercato è obbligatorio");
	}
	Mercati mercati = mercatiService.findById(new PkId(codiceMercato));
	MercatiConti entity = new MercatiConti();
	entity.setMercati(mercati);
	List<MercatiConti> mercatiContiList = mercatiContiService.findByMercati(entity);
	ModelMap model = new ModelMap(mercatiContiList);
	boolean export = createJMesaExport(request, response, mercatiContiList);
	if (export)
	    return null;
	model.addAttribute("mercatiContiList", mercatiContiList);
	model.addAttribute("mercati", mercati);
	return model;
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("mercatiConti") MercatiConti mercatiConti, BindingResult result, SessionStatus status) {

	Integer codiceMercato = mercatiConti.getMercati().getId().getCodice();
	Integer codice = mercatiConti.getId().getCodice();
	MercatiConti objToDelete = mercatiContiService.findById(mercatiConti.getId());
	try {
	    mercatiContiService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    Map<String, String> map = new HashMap<String, String>();
	    map.put("codice", codice.toString());
	    model.addAttribute("commandName", "mercatiConti");
	    model.addAttribute("method", "view.htm");
	    model.addAttribute("queryStringParams", map);
	    return "error";
	}
	status.setComplete();
	return "redirect:list.htm?mercati.id.codice=" + codiceMercato;
    }

    @RequestMapping
    public String insert(@ModelAttribute("mercatiConti") MercatiConti mercatiConti, BindingResult result, SessionStatus status) {

	// se necessario inserire parte di codice che deve ricercare altri attribbuti da settare
	// all'oggetto del dominio
	fixMergeEntityProperty(mercatiConti);
	try {
	    mercatiContiService.insert(mercatiConti);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, mercatiConti, e);
	    fixRenderEntityProperty(mercatiConti);
	    return "mercaticonti/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + mercatiConti.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String update(@ModelAttribute("mercatiConti") MercatiConti mercatiConti, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	// se necessario inserire parte di codice che deve ricercare altri attribbuti da settare
	// all'oggetto del dominio
	fixMergeEntityProperty(mercatiConti);
	try {
	    mercatiContiService.update(mercatiConti);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, mercatiConti, e);
	    fixRenderEntityProperty(mercatiConti);
	    return "mercaticonti/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + mercatiConti.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String create(@RequestParam("mercati.id.codice") Integer codiceMercato, Model model) {

	MercatiConti mercatiConti = new MercatiConti();
	PkId idMercato = new PkId(codiceMercato);
	Mercati mercati = mercatiService.findById(idMercato);
	mercatiConti.setMercati(mercati);
	fixRenderEntityProperty(mercatiConti);
	model.addAttribute("mercatiConti", mercatiConti);
	setPageAttributes(model);
	return "mercaticonti/form";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	MercatiConti mercatiConti = mercatiContiService.findById(id);
	fixRenderEntityProperty(mercatiConti);
	model.addAttribute("mercatiConti", mercatiConti);
	setPageAttributes(model);
	return "mercaticonti/form";
    }

    @RequestMapping
    public String configuraContiView(@RequestParam("mercati.id.codice") Integer codiceMercato, @RequestParam("anno") Integer anno, Model model,
	    HttpServletRequest request) {

	PkId id = new PkId(codiceMercato);
	Mercati mercato = mercatiService.findById(id);
	ConfigurazioneContiMercato configurazioneContiMercato = new ConfigurazioneContiMercato();
	configurazioneContiMercato.setMercati(mercato);
	configurazioneContiMercato.setAnno(anno);
	Set<MercatiConti> mercatiContis = mercato.getMercatiContis();
	List<MercatiContiHelper> listaConti = new ArrayList<MercatiContiHelper>();
	for (MercatiConti mercatiConti : mercatiContis) {
	    if (mercatiConti.getAnno().intValue() == (anno.intValue() - 1)) {
		MercatiContiHelper mercatiContiHelper = new MercatiContiHelper();
		mercatiContiHelper.setContoNew(mercatiConti.getConti());
		mercatiContiHelper.setContoOld(mercatiConti.getConti());
		mercatiContiHelper.setUsa(true);
		mercatiContiHelper.setMercatiConti(mercatiConti);
		listaConti.add(mercatiContiHelper);
	    }
	}
	configurazioneContiMercato.setContiHelperList(listaConti);
	List<Conti> contis = contiService.findAll(null, null);
	model.addAttribute("configurazioneContiMercato", configurazioneContiMercato);
	model.addAttribute("contis", contis);
	setPageAttributes(model);
	return "mercaticonti/formConfiguraConti";
    }

    @RequestMapping
    public String configuraContiInsert(Model model,
	    @ModelAttribute("configurazioneContiMercato") ConfigurazioneContiMercato configurazioneContiMercato, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	// se necessario inserire parte di codice che deve ricercare altri attributi da settare
	// all'oggetto del dominio
	List<MercatiContiHelper> list = configurazioneContiMercato.getContiHelperList();
	List<MercatiContiHelper> target = new ArrayList<MercatiContiHelper>();
	int idx = 0;
	boolean almenoUnContoDaUsare = false;
	for (MercatiContiHelper mercatiContiHelper : list) {
	    log.debug("request.getParameter(contiHelperList[" +
		    idx +
		    "].contoNew.id.codice)" +
		    request.getParameter("contiHelperList[" + idx + "].contoNew.id.codice"));
	    log.debug("request.getParameter(contiHelperList[" +
		    idx +
		    "].contoOld.id.codice)" +
		    request.getParameter("contiHelperList[" + idx + "].contoOld.id.codice"));
	    PkId contoOldId = new PkId(new Integer(request.getParameter("contiHelperList[" + idx + "].contoOld.id.codice")));
	    Conti contoOld = contiService.findById(contoOldId);
	    PkId contoNewId = new PkId(new Integer(request.getParameter("contiHelperList[" + idx + "].contoNew.id.codice")));
	    Conti contoNew = contiService.findById(contoNewId);
	    mercatiContiHelper.setContoNew(contoNew);
	    mercatiContiHelper.setContoOld(contoOld);
	    target.add(mercatiContiHelper);
	    if (mercatiContiHelper.isUsa()) {
		almenoUnContoDaUsare = true;
	    }
	    idx++;
	}
	configurazioneContiMercato.setContiHelperList(target);
	if (!almenoUnContoDaUsare) {
	    List<Conti> contis = contiService.findAll(null, null);
	    // TODO verificare se funziona
	    // ResourceBundle resource = ResourceBundle.getBundle("messages", LocaleContextHolder.getLocale());
	    // result.reject("", resource.getString("errors.validator.mercaticonti.configuraconti.select.line"));
	    result.reject("", getMessageFromBundle("errors.validator.mercaticonti.configuraconti.select.line", null));
	    model.addAttribute("configurazioneContiMercato", configurazioneContiMercato);
	    model.addAttribute("contis", contis);
	    setPageAttributes(model);
	    return "mercaticonti/formConfiguraConti";
	}
	try {
	    mercatiContiService.sistemaContiPerAnno(configurazioneContiMercato);
	} catch (Exception e) {
	    log.error("errore nell'operazione MercatiController.sistemaContiPerAnno:" + e.getMessage());
	    copyErrorsToBindingResult(result, configurazioneContiMercato, e);
	    return "redirect:configuraContiView.htm?mercati.id.codice=" +
		    configurazioneContiMercato.getMercati().getId().getCodice() +
		    "&anno=" +
		    configurazioneContiMercato.getAnno().intValue() +
		    "&status_msg=03";
	}
	status.setComplete();
	return "redirect:../history/back.htm?" + WebConstants.GOTO + "=%2F";
    }

    @RequestMapping
    public String costoposteggi(@RequestParam("mercati.id.codice") Integer codiceMercato, @RequestParam("anno") String anno, Model model,
	    HttpServletRequest request) {

	Mercati mercati = mercatiService.findById(new PkId(codiceMercato));
	MercatiConti entity = new MercatiConti();
	entity.setMercati(mercati);
	Integer annoInteger = null;
	// se l'anno è null allora usa quello corrento (usato nel caso del primo accesso tramite menù)
	// altrimenti quello che gli viene passato (quando il metodo è chiamato tramite i javascript)
	if (anno.equals("null")) {
	    annoInteger = GregorianCalendar.getInstance().get(Calendar.YEAR);
	} else {
	    annoInteger = Integer.parseInt(anno);
	}
	// ritorna i conti associati al mercatomercato
	List<MercatiConti> mercatiContiList = mercatiContiService.findByMercatiAndAnno(mercati, annoInteger);
	// ritorna la lista dei posteggi del mercato scelto con tutti i conti posteggio per posteggio
	List<MercatiD> mercatiDList = mercatiDService.mostraCostoPosteggio(mercati, annoInteger, mercatiContiList);
	model.addAttribute("mercatiContiList", mercatiContiList);
	model.addAttribute("mercatiDList", mercatiDList);
	model.addAttribute("mercati", mercati);
	model.addAttribute("anno", annoInteger);
	return "mercaticonti/costoposteggi";
    }

    @RequestMapping
    public String adeguamentoIstatStep1(@RequestParam("codiceMercato") Integer codiceMercato, @RequestParam("anno") Integer anno, Model model,
	    HttpServletRequest request) {

	Mercati mercati = mercatiService.findById(new PkId(codiceMercato));
	model.addAttribute("mercati", mercati);
	model.addAttribute("anno", anno);
	List<MercatiConti> mercatiContis = mercatiContiService.findByMercatiAndAnno(mercati, anno);
	List<MercatiDConti> mercatidContis = mercatiDContiService.findByMercatiAndAnno(mercati, anno);
	Map<Integer, Conti> contisM = new HashMap<Integer, Conti>();
	for (MercatiConti mercatiConti : mercatiContis) {
	    Conti c = mercatiConti.getConti();
	    Conti contoPresente = contisM.get(c.getId().getCodice());
	    if (contoPresente == null) {
		contisM.put(c.getId().getCodice(), c);
	    }
	}
	for (MercatiDConti mercatiConti : mercatidContis) {
	    Conti c = mercatiConti.getConto();
	    Conti contoPresente = contisM.get(c.getId().getCodice());
	    if (contoPresente == null) {
		contisM.put(c.getId().getCodice(), c);
	    }
	}
	List<Conti> contis = new ArrayList<Conti>();
	for (Entry<Integer, Conti> entry : contisM.entrySet()) {
	    contis.add(entry.getValue());
	}
	model.addAttribute("listaConti", contis);
	return "mercaticonti/adeguamentoIstatStep1";
    }

    @RequestMapping
    public String updateAdeguamentoIstat(@RequestParam("codiceMercato") Integer codiceMercato,
	    @RequestParam("anno_precedente") Integer anno_precedente, @RequestParam("anno_da_adeguare") Integer anno_da_adeguare,
	    @RequestParam("coefficiente_adeguamento") BigDecimal coefficiente_adeguamento, HttpServletRequest request, HttpServletResponse response) {

	Mercati mercato = mercatiService.findById(new PkId(codiceMercato));
	Map<Integer, Integer> vecchioContoNuovoConto = new HashMap<Integer, Integer>();
	Enumeration<?> en = request.getParameterNames();
	while (en.hasMoreElements()) {
	    String paramName = (String) en.nextElement();
	    //System.out.println(paramName + "=" + request.getParameter(paramName));
	    if (paramName.startsWith("conti_hidden_")) {
		String codiceContoNuovo = request.getParameter(paramName);
		if (StringUtils.isNotBlank(codiceContoNuovo)) {
		    String codiceContoVecchio = paramName.substring((paramName.lastIndexOf("_") + 1));
		    vecchioContoNuovoConto.put(Integer.valueOf(codiceContoVecchio), Integer.valueOf(codiceContoNuovo));
		}
	    }
	}
	try {
	    mercatiContiService.adeguaPercentualeIstat(mercato, anno_precedente, anno_da_adeguare, vecchioContoNuovoConto, coefficiente_adeguamento);
	} catch (Exception e) {
	    FlashMessages.getWarnings().add("Errore nell'operazione di adeguamento ISTAT: " + e.getMessage());
	    return "redirect:adeguamentoIstatStep1.htm?codiceMercato=" +
		    codiceMercato.intValue() +
		    "&anno=" +
		    anno_precedente.intValue() +
		    "&status_msg=03";
	}
	return "redirect:costoposteggi.htm?mercati.id.codice=" + codiceMercato.intValue() + "&anno=" + anno_da_adeguare.intValue() + "&status_msg=01";
    }

    @Override
    protected void fixMergeEntityProperty(MercatiConti entity) {

	if (entity.getConti() != null && entity.getConti().getId() != null && entity.getConti().getId().getCodice() == null) {
	    entity.setConti(null);
	}
	if (entity.getMercati() != null && entity.getMercati().getId() != null && entity.getMercati().getId().getCodice() == null) {
	    entity.setMercati(null);
	}
    }

    @Override
    protected void fixRenderEntityProperty(MercatiConti entity) {

	if (entity.getConti() == null) {
	    entity.setConti(new Conti());
	}
	if (entity.getMercati() == null) {
	    entity.setMercati(new Mercati());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }

    @RequestMapping
    public void ajaxCalcolaConto(@RequestParam("idPosteggio") Integer idPosteggio, @RequestParam(value = "anno") Integer anno,
	    @RequestParam(value = "contesto") String contesto, @RequestParam(value = "idPresenza", required = true) Integer idPresenza,
	    @RequestParam(value = "idMercatiUso", required = true) Integer idMercatiUso, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {

	List<MercatiCfgAttivita> listMcfgAttivita = mercatiCfgAttivitaService.findAll(null, null);
	MercatiD posteggio = mercatiDService.findById(new PkId(idPosteggio));
	String codiceIstatOccupante = null;
	MercatipresenzeD pres = null;
	Calendar c = Calendar.getInstance();
	c.set(Calendar.YEAR, anno);
	Date dataDiriferimento = c.getTime();
	if (idPresenza != null) {
	    pres = mercatipresenzeDService.findById(new PkId(idPresenza));
	    dataDiriferimento = pres.getMercatiPresenzeT().getDataRegistrazione();
	    if (pres != null && pres.isSpuntista() && pres.getAttivita() != null) {
		codiceIstatOccupante = pres.getAttivita().getId().getCodiceistat();
	    }
	}
	PosteggioImportoHelper costoPosteggioSpuntista = calcoloCostoPosteggiService.calcolaCostoPosteggio(pres, posteggio, listMcfgAttivita, anno, 1,
		null, contesto, codiceIstatOccupante, idMercatiUso, dataDiriferimento, MercatiFormuleCalcoloContestoEnum.PRESENZA);
	BigDecimal importo = BigDecimal.ZERO;
	if (costoPosteggioSpuntista != null) {
	    importo = costoPosteggioSpuntista.getImporto();
	}
	//	if (idPresenza != null) {
	//	    pres = mercatipresenzeDService.findById(new PkId(idPresenza));
	//	    if (pres != null && pres.getImporto() != null && (pres.getImporto().compareTo(BigDecimal.ZERO) != 0)) {
	//		importo = pres.getImporto();
	//	    }
	//	}
	String format = NumberFormat.getCurrencyInstance(Locale.ITALY).format(importo);
	response.setContentType("text/plain");
	response.getWriter().write(format);
    }
}
