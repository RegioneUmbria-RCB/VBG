package it.gruppoinit.pal.gp.backoffice.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiDAvvisi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioneri;
import it.gruppoinit.pal.gp.core.domain.helper.PosteggioMercatiHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.features.manifestazioni.configurazione.MercatiDService;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.MercatiDAvvisiService;

/**
 * 
 * @author
 */
@Controller
@SessionAttributes("mercatidavvisi")
public class MercatiDAvvisiController extends BaseController<MercatiDAvvisi> {

    private static final Logger log = LoggerFactory.getLogger(MercatiDAvvisiController.class);
    @Autowired
    private MercatiDService mercatiDService;
    @Autowired
    private MercatiDAvvisiService mercatidavvisiService;
    @Autowired
    private AnagrafeService anagrafeService;

    @RequestMapping
    public ModelMap list(@RequestParam("posteggioId") Integer codice, HttpServletRequest request, HttpServletResponse response) {

	MercatiD mercatiD = mercatiDService.findById(new PkId(codice));
	List<MercatiDAvvisi> mercatidavvisiList = mercatidavvisiService.findAllByPosteggio(codice, null, null);
	ModelMap model = new ModelMap(mercatidavvisiList);
	boolean export = createJMesaExport(request, response, mercatidavvisiList);
	if (export) {
	    return null;
	}
	model.addAttribute("mercatiD", mercatiD);
	model.addAttribute("mercatidavvisiList", mercatidavvisiList);
	return model;
    }

    @RequestMapping
    public String create(@RequestParam("posteggioId") Integer posteggioId, Model model) {

	MercatiD mercatiD = mercatiDService.findById(new PkId(posteggioId));
	List<PosteggioMercatiHelper> _mercatidList = mercatiDService.findMercatiDWithConcessioniSQL(mercatiD.getMercati().getId().getCodice(),
		mercatiD, true, null);
	Anagrafe anagrafe = null;
	if (!_mercatidList.isEmpty()) {
	    Integer codiceAnagrafe = null;
	    if (!_mercatidList.get(0).getIstanzeConcessioniMercatoHelpers().isEmpty()) {
		codiceAnagrafe = _mercatidList.get(0).getIstanzeConcessioniMercatoHelpers().get(0).getIdTitolare();
		anagrafe = anagrafeService.findById(new PkId(codiceAnagrafe));
		model.addAttribute("titolare", anagrafe);
	    }
	}
	MercatiDAvvisi mercatidavvisi = new MercatiDAvvisi();
	mercatidavvisi.setMercatiD(mercatiD);
	mercatidavvisi.setAnagrafe(anagrafe);
	fixRenderEntityProperty(mercatidavvisi);
	model.addAttribute("mercatidavvisi", mercatidavvisi);
	setPageAttributes(model);
	return "mercatidavvisi/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("mercatidavvisi") MercatiDAvvisi mercatidavvisi, BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(mercatidavvisi);
	try {
	    mercatidavvisiService.insert(mercatidavvisi);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, mercatidavvisi, e);
	    fixRenderEntityProperty(mercatidavvisi);
	    return "mercatidavvisi/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + mercatidavvisi.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	MercatiDAvvisi mercatidavvisi = mercatidavvisiService.findById(id);
	fixRenderEntityProperty(mercatidavvisi);
	model.addAttribute("mercatidavvisi", mercatidavvisi);
	setPageAttributes(model);
	return "mercatidavvisi/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("mercatidavvisi") MercatiDAvvisi mercatidavvisi, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	fixMergeEntityProperty(mercatidavvisi);
	try {
	    mercatidavvisiService.update(mercatidavvisi);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, mercatidavvisi, e);
	    fixRenderEntityProperty(mercatidavvisi);
	    return "mercatidavvisi/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + mercatidavvisi.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("mercatidavvisi") MercatiDAvvisi mercatidavvisi, BindingResult result, SessionStatus status) {

	MercatiDAvvisi objToDelete = mercatidavvisiService.findById(mercatidavvisi.getId());
	try {
	    mercatidavvisiService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(mercatidavvisi);
	    return "mercatidavvisi/form";
	}
	status.setComplete();
	return "redirect:list.htm?posteggioId=" + objToDelete.getMercatiD().getId().getCodice();
    }

    @RequestMapping
    public String ajaxFindMercatiDAvvisi(@RequestParam("codicePosteggio") Integer codicePosteggio,
	    @RequestParam("codiceCausaleOnere") Integer codiceCausaleOnere, Model model, HttpServletResponse response) {

	List<MercatiDAvvisi> h = mercatidavvisiService.findAvvisiMercatiD(codicePosteggio, codiceCausaleOnere);
	if (!h.isEmpty()) {
	    model.addAttribute("tipicausalioneri", h.get(0).getTipicausalioneri());
	    model.addAttribute("mercatiD", h.get(0).getMercatiD());
	}
	model.addAttribute("avvisiMercatid", h);
	if (log.isDebugEnabled())
	    log.debug("call lista avvisi per il posteggio {} e per causale onere {}", new Object[] { codicePosteggio, codiceCausaleOnere });
	response.setContentType("text/plain");
	return "mercatidavvisi/listAvvisiPosteggio";
    }

    @Override
    protected void fixMergeEntityProperty(MercatiDAvvisi entity) {

    }

    @Override
    protected void fixRenderEntityProperty(MercatiDAvvisi entity) {

	if (EntityUtils.getNestedProperty(entity.getAnagrafe(), "id.codice") == null) {
	    entity.setAnagrafe(new Anagrafe());
	}
	if (EntityUtils.getNestedProperty(entity.getMercatiD(), "id.codice") == null) {
	    entity.setMercatiD(new MercatiD());
	}
	if (EntityUtils.getNestedProperty(entity.getTipicausalioneri(), "id.codice") == null) {
	    entity.setTipicausalioneri(new Tipicausalioneri());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
