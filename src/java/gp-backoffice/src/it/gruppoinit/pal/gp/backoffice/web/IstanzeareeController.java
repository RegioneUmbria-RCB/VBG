package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Aree;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzearee;
import it.gruppoinit.pal.gp.core.domain.IstanzeareeId;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.RicalcoloAree;
import it.gruppoinit.pal.gp.core.domain.RicalcoloAreeId;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.IstanzeStrRicalcoloRestClient;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.IstanzeareeService;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.RespSessionData;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.RicalcoloAreeBean;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.RicalcoloMaxReqParams;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.RicalcoloMaxRequest;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.RicalcoloMaxResponse;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.aree.sottoscrittori.StatoElaborazioneEnum;
import it.gruppoinit.pal.gp.core.service.AreeService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.RicalcoloAreeService;

@Controller
@SessionAttributes("istanzearee")
public class IstanzeareeController extends BaseController<Istanzearee> {

    @Autowired
    private IstanzeareeService istanzeareeService;
    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private AreeService areeService;
    @Autowired
    private RicalcoloAreeService ricalcoloAreeService;
    private static final Logger log = LoggerFactory.getLogger(IstanzeareeController.class);

    @RequestMapping
    public String createRicalcola(Model model, HttpServletRequest request) {

	// §§§BEGIN§§§
	Istanzearee istanzearee = new Istanzearee();
	fixRenderEntityProperty(istanzearee);
	model.addAttribute("istanzearee", istanzearee);
	setPageAttributes(model);
	boolean isRicalcoloSuccessCase = "08".equals(request.getParameter("status_msg"))
		&& request.getParameter(RicalcoloAreeBean.ID_RICALCOLO_AREE) != null;
	List<RicalcoloAree> ricalcoloAreeDAOList;
	ricalcoloAreeDAOList = ricalcoloAreeService.getMonitorRicalcolaAree();
	List<RicalcoloAreeBean> ricalcoloAreeList = new ArrayList<RicalcoloAreeBean>();
	List<RicalcoloAreeBean> ricalcoloAreeInCorso = new ArrayList<RicalcoloAreeBean>();
	List<RicalcoloAreeBean> ricalcoloAreeOther = new ArrayList<RicalcoloAreeBean>();
	
	boolean isSpecificInCorso = false;
	
	if (ricalcoloAreeDAOList != null) {
	    for (RicalcoloAree ra : ricalcoloAreeDAOList) {
		RicalcoloAreeBean bean = new RicalcoloAreeBean();
		bean.setId(ra.getPk().getId());
		bean.setStato(ra.getStato());
		
		if(isRicalcoloSuccessCase && 
			request.getParameter(RicalcoloAreeBean.ID_RICALCOLO_AREE).equals(bean.getId()) &&
			RicalcoloAreeBean.IN_CORSO.equals(bean.getStato())){
		    isSpecificInCorso = true;
		}
		
		
		bean.setDafare(ra.getDafare() + RicalcoloAreeBean.EMPTY);
		bean.setFatti(ra.getFatti() + RicalcoloAreeBean.EMPTY);
		bean.setTotali(ra.getTotali() + RicalcoloAreeBean.EMPTY);
		if (RicalcoloAreeBean.STATUS_COMPLETATA.equals(ra.getStato())) {
		    bean.setFontAwesomeButton(RicalcoloAreeBean.OK_FAS);
		    bean.setClassDisabledButton(RicalcoloAreeBean.DISABLED_BUTTON);
		} else if (RicalcoloAreeBean.CON_SCARTI.equals(ra.getStato())) {
		    bean.setFontAwesomeButton(RicalcoloAreeBean.OK_FAS);
		    bean.setClassDisabledButton(RicalcoloAreeBean.DISABLED_BUTTON);
		} else if (RicalcoloAreeBean.IN_ERRORE.equals(ra.getStato())) {
		    bean.setFontAwesomeButton(RicalcoloAreeBean.KO_FAS);
		    bean.setClassDisabledButton(RicalcoloAreeBean.DISABLED_BUTTON);
		} else if (ra.getDescrizione() == null) {
		    bean.setDafare(RicalcoloAreeBean.EMPTY);
		    bean.setFatti(RicalcoloAreeBean.EMPTY);
		    bean.setTotali(RicalcoloAreeBean.EMPTY);
		}
		if (RicalcoloAreeBean.IN_CORSO.equals(bean.getStato())) {
		    bean.setBoldStyle(RicalcoloAreeBean.BOLD_STYLE);
		    ricalcoloAreeInCorso.add(bean);
		} else {
		    if (StatoElaborazioneEnum.DA_ESEGUIRE.value().equals(bean.getStato())) {
			bean.setFontAwesomeButton(RicalcoloAreeBean.NO_ACC_FAS);
			bean.setClassDisabledButton(RicalcoloAreeBean.DISABLED_BUTTON);
		    }
		    ricalcoloAreeOther.add(bean);
		}
	    }
	}
	ricalcoloAreeList.addAll(ricalcoloAreeInCorso);
	ricalcoloAreeList.addAll(ricalcoloAreeOther);
	model.addAttribute("ricalcoloAreeList", ricalcoloAreeList);
	if (isRicalcoloSuccessCase) {
	    if (isSpecificInCorso) {		
		model.addAttribute("disabledriclink", "disabledriclink");
	    } else {
		model.addAttribute("disabledriclink", RicalcoloAreeBean.EMPTY);
	    }
	} else {
	    model.addAttribute("disabledriclink", RicalcoloAreeBean.EMPTY);
	}
	return "istanzearee/ricalcola";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String ricalcola(Model model, @RequestParam("dallaData") Date dallaData, @RequestParam("allaData") Date allaData,
	    @RequestParam("areeric") String areeric, @ModelAttribute("istanzearee") Istanzearee istanzearee, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	log.debug("Ricalcolo aree avviato");
	RicalcoloMaxRequest req = null;
	RicalcoloMaxResponse resPost = null;
	// §§§BEGIN§§§
	try {
	    //istanzeareeService.ricalcolaAree(dallaData,allaData); NON PIU' QUESTO
	    req = new RicalcoloMaxRequest();
	    SimpleDateFormat sdf = new SimpleDateFormat(IstanzeStrRicalcoloRestClient.FORMAT_DD_MM_YYYY);
	    if (dallaData != null) {
		req.setDataPresentazioneDa(sdf.format(dallaData));
	    }
	    if (allaData != null) {
		req.setDataPresentazioneA(sdf.format(allaData));
	    }
	    if (areeric != null && !areeric.trim().isEmpty()) {
		ObjectMapper mapper = new ObjectMapper();
		int[] resultt = mapper.readValue(areeric, int[].class);
		if (resultt != null && resultt.length > 0) {
		    List<Integer> idaree = new ArrayList<Integer>();
		    for (int a : resultt) {
			idaree.add(a);
		    }
		    req.setIdAree(idaree);
		}
	    }
	    RicalcoloMaxReqParams params = new RicalcoloMaxReqParams();
	    params.setToken(ORMHelper.getToken()); //Prendo questo
	    params.setIdcomune(ORMHelper.getIdcomune());
	    params.setSoftware(ORMHelper.getSoftware());
	    resPost = new IstanzeStrRicalcoloRestClient().ricalcolaArea(req, params);
	    
	    log.debug("richiesta ricevuta con getRicalcoloAreeId {} ", resPost.getRicalcoloAreeIdList().get(0));
	} catch (Exception e) {
	    model.addAttribute("ricalcoloAreeList", new ArrayList<RicalcoloAreeBean>()); //mettiamolo vuoto
	    model.addAttribute("disabledriclink", RicalcoloAreeBean.EMPTY);
	    copyErrorsToBindingResult(result, istanzearee, e);
	    return "istanzearee/ricalcola";
	}
	status.setComplete();
	return "redirect:createRicalcola.htm?status_msg=08&" + RicalcoloAreeBean.ID_RICALCOLO_AREE + "=" + resPost.getRicalcoloAreeIdList().get(0);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    // @RequestMapping
    // public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    // List<Aree> areeList = areeList.findAll(null, null);
    // ModelMap model = new ModelMap(anagrafeList);
    // boolean export = createJMesaExport(request, response, anagrafeList);
    // if (export) {
    // return null;
    // }
    // model.addAttribute("anagrafeList", anagrafeList);
    // return model;
    // }
    @RequestMapping
    public String create(Model model, @RequestParam("codiceIstanza") Integer coticeIstanza, HttpServletRequest request) {

	Istanzearee istanzeAree = new Istanzearee();
	Istanze istanze = istanzeService.findById(new PkId(coticeIstanza));
	checkAccessoInformazioni(istanze, true);
	istanzeAree.setIstanza(istanze);
	model.addAttribute("istanzearee", istanzeAree);
	model.addAttribute("istanze", istanze);
	return "istanzearee/form";
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("istanzearee") Istanzearee istanzearee, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	checkAccessoInformazioni(istanzearee.getIstanza(), true);
	if (EntityUtils.getNestedProperty(istanzearee.getArea(), "id.codice") != null) {
	    Aree aree = areeService.findById(istanzearee.getArea().getId());
	    istanzearee.setArea(aree);
	}
	try {
	    istanzeareeService.insertAltraAreea(istanzearee);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, istanzearee, e);
	    fixRenderEntityProperty(istanzearee);
	    return "istanzearee/form";
	}
	status.setComplete();
	return "redirect:../istanze/view.htm?codice=" + istanzearee.getIstanza().getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String ajaxlistAree(@RequestParam("codiceIstanza") String codiceistanza, Model model, HttpServletResponse response) throws IOException {

	Istanze istanze = istanzeService.findById(new PkId(Integer.parseInt(codiceistanza)));
	List<Istanzearee> listIstanzearee = istanzeareeService.findByIstanza(istanze);
	model.addAttribute("listIstanzearee", listIstanzearee);
	if (log.isDebugEnabled())
	    log.debug("call dettaglio istanze procedimenti with codiceIstanza: " + codiceistanza);
	return "ajax/listaAreeistanza";
    }

    @RequestMapping
    public String changePrimario(@RequestParam("codiceIstanza") Integer codiceIstanza, @RequestParam("codiceArea") Integer codiceArea,
	    HttpServletResponse response) throws IOException {

	IstanzeareeId id = new IstanzeareeId();
	id.setCodicearea(codiceArea);
	id.setCodiceistanza(codiceIstanza);
	Istanzearee istanzearee = istanzeareeService.findById(id);
	checkAccessoInformazioni(istanzearee.getIstanza(), true);
	istanzeareeService.updatePrimario(istanzearee);
	return "redirect:../istanze/view.htm?codice=" + istanzearee.getIstanza().getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String deleteIstanzaarea(@RequestParam("codiceIstanza") Integer codiceIstanza, @RequestParam("codiceArea") Integer codiceArea,
	    HttpServletResponse response) throws IOException {

	IstanzeareeId id = new IstanzeareeId();
	id.setCodicearea(codiceArea);
	id.setCodiceistanza(codiceIstanza);
	Istanzearee istanzearee = istanzeareeService.findById(id);
	checkAccessoInformazioni(istanzearee.getIstanza(), true);
	istanzeareeService.delete(istanzearee);
	return "redirect:../istanze/view.htm?codice=" + istanzearee.getIstanza().getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public void getDataProcessFromRicalcoloAreeId(@RequestParam("ricalcoloAreeId") String ricalcoloAreeId, HttpServletRequest request,
	    HttpServletResponse response) {

	RespSessionData res = null;
	try {
	    res = new IstanzeStrRicalcoloRestClient().getStatusRicalcolo(ricalcoloAreeId, ORMHelper.getToken());
	} catch (FunzioneBusinessRemotaException e1) {
	    e1.printStackTrace();
	    throw new RuntimeException("Error eseguendo la get");
	}
	ObjectMapper objectMapper = new ObjectMapper();
	String json;
	try {
	    json = objectMapper.writeValueAsString(res);
	} catch (JsonProcessingException e1) {
	    e1.printStackTrace();
	    throw new RuntimeException("Error eseguendo la get sul parsing");
	}
	response.setContentType("application/json");
	response.setCharacterEncoding("UTF-8");
	try {
	    response.getWriter().write(json);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    @RequestMapping
    public String goToDettaglioElaborazione(Model model, HttpServletRequest request) {

	RicalcoloAree ricalcoloAree = ricalcoloAreeService.findById(new RicalcoloAreeId(request.getParameter(RicalcoloAreeBean.ID_RICALCOLO_AREE)));
	model.addAttribute("dettelaborazione", ricalcoloAree);
	if (ricalcoloAree.getDescrizione() != null) {
	    model.addAttribute("ricalcoloAreeDesc", ricalcoloAree.getDescrizione());
	} else if (StatoElaborazioneEnum.DA_ESEGUIRE.value().equals(ricalcoloAree.getStato())) {
	    model.addAttribute("ricalcoloAreeDesc", "Elaborazione su istanza da eseguire");
	} else {
	    model.addAttribute("ricalcoloAreeDesc", "In fase di ricerca aree");
	}
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	model.addAttribute("formattedDate", sdf.format(ricalcoloAree.getDatafine()));
	return "istanzearee/dettaglioric";
    }

    @Override
    protected void setPageAttributes(Model model) {

    }

    @Override
    protected void fixMergeEntityProperty(Istanzearee entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Istanzearee entity) {

	if (entity.getIstanza() == null) {
	    entity.setIstanza(new Istanze());
	}
	if (entity.getArea() == null) {
	    entity.setArea(new Aree());
	}
    }
}
