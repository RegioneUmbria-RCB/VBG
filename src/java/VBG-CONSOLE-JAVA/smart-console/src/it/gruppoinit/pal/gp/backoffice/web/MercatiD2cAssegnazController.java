package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiD2cAssegnaz;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.service.Dyn2CampiService;
import it.gruppoinit.pal.gp.core.service.MercatiD2cAssegnazService;
import it.gruppoinit.pal.gp.core.service.MercatiService;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

/**
 * 
 * @author
 */
//DAELIMINARE @Controller
@SessionAttributes("mercatid2cassegnaz")
public class MercatiD2cAssegnazController extends BaseController<MercatiD2cAssegnaz> {

    @Autowired
    private Dyn2CampiService dyn2CampiService;
    @Autowired
    private MercatiD2cAssegnazService mercatid2cassegnazService;
    @Autowired
    private MercatiService mercatiService;

    @RequestMapping
    public ModelMap list(@RequestParam("codiceMercato") Integer codiceMercato, HttpServletRequest request, HttpServletResponse response) {

	List<MercatiD2cAssegnaz> mercatid2cassegnazList = mercatid2cassegnazService.findByMercato(codiceMercato);
	ModelMap model = new ModelMap(mercatid2cassegnazList);
	boolean export = createJMesaExport(request, response, mercatid2cassegnazList);
	if (export) {
	    return null;
	}
	Mercati mercato = mercatiService.findById(new PkId(codiceMercato));
	if (EntityUtils.getNestedProperty(mercato.getDyn2Campi(), "id.codice") == null) {
	    mercato.setDyn2Campi(new Dyn2Campi());
	}
	if (EntityUtils.getNestedProperty(mercato.getDyn2CampiPrefPosteggio(), "id.codice") == null) {
	    mercato.setDyn2CampiPrefPosteggio(new Dyn2Campi());
	}
	model.addAttribute("mercato", mercato);
	MercatiD2cAssegnaz mercatiD2cAssegnaz = new MercatiD2cAssegnaz();
	mercatiD2cAssegnaz.setMercati(mercato);
	//	mercatiD2cAssegnaz.setMercati(mercato.get);
	//	if(EntityUtils.getNestedProperty( mercato.getDyn2Campi(), "id.codice")!=null )
	//	{
	//	    PkId id=new PkId(mercato.getDyn2Campi())
	//	}
	model.addAttribute("mercatid2cassegnaz", mercatiD2cAssegnaz);
	model.addAttribute("mercatid2cassegnazList", mercatid2cassegnazList);
	return model;
    }

    @RequestMapping
    public String create(@RequestParam("codiceMercato") Integer codiceMercato, Model model) {

	MercatiD2cAssegnaz mercatid2cassegnaz = new MercatiD2cAssegnaz();
	fixRenderEntityProperty(mercatid2cassegnaz);
	Mercati mercato = mercatiService.findById(new PkId(codiceMercato));
	mercatid2cassegnaz.setMercati(mercato);
	model.addAttribute("mercatid2cassegnaz", mercatid2cassegnaz);
	setPageAttributes(model);
	return "mercatid2cassegnaz/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("mercatid2cassegnaz") MercatiD2cAssegnaz mercatid2cassegnaz, BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(mercatid2cassegnaz);
	try {
	    mercatid2cassegnazService.insert(mercatid2cassegnaz);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, mercatid2cassegnaz, e);
	    fixRenderEntityProperty(mercatid2cassegnaz);
	    return "mercatid2cassegnaz/form";
	}
	status.setComplete();
	return "redirect:list.htm?codiceMercato=" + mercatid2cassegnaz.getMercati().getId().getCodice();
    }

    @RequestMapping
    public String insertDyn2CampiAssegnazioni(@RequestParam("codiceCampo") Integer codiceCampo,
	    @ModelAttribute("mercatid2cassegnaz") MercatiD2cAssegnaz mercatid2cassegnaz, BindingResult result, SessionStatus status) {

	Dyn2Campi dyn2Campi = dyn2CampiService.findById(new PkId(codiceCampo));
	mercatid2cassegnaz.setDyn2Campi(dyn2Campi);
	fixMergeEntityProperty(mercatid2cassegnaz);
	try {
	    mercatid2cassegnazService.insert(mercatid2cassegnaz);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, mercatid2cassegnaz, e);
	    fixRenderEntityProperty(mercatid2cassegnaz);
	    return "mercatid2cassegnaz/form";
	}
	status.setComplete();
	return "redirect:list.htm?codiceMercato=" + mercatid2cassegnaz.getMercati().getId().getCodice() + "&status_msg=02";
    }

    //    @RequestMapping
    //    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {
    //
    //	PkId id = new PkId(codice);
    //	MercatiD2cAssegnaz mercatid2cassegnaz = mercatid2cassegnazService.findById(id);
    //	fixRenderEntityProperty(mercatid2cassegnaz);
    //	model.addAttribute("mercatid2cassegnaz", mercatid2cassegnaz);
    //	setPageAttributes(model);
    //	return "mercatid2cassegnaz/form";
    //    }
    //    @RequestMapping
    //    public String update(@ModelAttribute("mercatid2cassegnaz") MercatiD2cAssegnaz mercatid2cassegnaz, BindingResult result, SessionStatus status,
    //	    HttpServletRequest request) {
    //
    //	fixMergeEntityProperty(mercatid2cassegnaz);
    //	try {
    //	    mercatid2cassegnazService.update(mercatid2cassegnaz);
    //	} catch (Exception e) {
    //	    copyErrorsToBindingResult(result, mercatid2cassegnaz, e);
    //	    fixRenderEntityProperty(mercatid2cassegnaz);
    //	    return "mercatid2cassegnaz/form";
    //	}
    //	status.setComplete();
    //	return "redirect:view.htm?codice=" + mercatid2cassegnaz.getId().getCodice() + "&status_msg=02";
    //    }
    //    @RequestMapping
    //    public String delete(@ModelAttribute("mercatid2cassegnaz") MercatiD2cAssegnaz mercatid2cassegnaz, BindingResult result, SessionStatus status) {
    //
    //	MercatiD2cAssegnaz objToDelete = mercatid2cassegnazService.findById(mercatid2cassegnaz.getId());
    //	try {
    //	    mercatid2cassegnazService.delete(objToDelete);
    //	} catch (Exception e) {
    //	    copyErrorsToBindingResult(result, mercatid2cassegnaz, e);
    //	    fixRenderEntityProperty(mercatid2cassegnaz);
    //	    return "mercatid2cassegnaz/form";
    //	}
    //	status.setComplete();
    //	return "redirect:list.htm";
    //    }
    @RequestMapping
    public String deleteDyn2Campi(@RequestParam("codice") Integer codice,
	    @ModelAttribute("mercatid2cassegnaz") MercatiD2cAssegnaz mercatid2cassegnaz, BindingResult result, SessionStatus status) {

	MercatiD2cAssegnaz objToDelete = mercatid2cassegnazService.findById(new PkId(codice));
	try {
	    mercatid2cassegnazService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, mercatid2cassegnaz, e);
	    fixRenderEntityProperty(mercatid2cassegnaz);
	    return "mercatid2cassegnaz/list";
	}
	status.setComplete();
	return "redirect:list.htm?codiceMercato=" + objToDelete.getMercati().getId().getCodice();
    }

    @Override
    protected void fixMergeEntityProperty(MercatiD2cAssegnaz entity) {

    }

    @Override
    protected void fixRenderEntityProperty(MercatiD2cAssegnaz entity) {

	if (entity.getDyn2Campi() == null) {
	    entity.setDyn2Campi(new Dyn2Campi());
	}
	if (entity.getMercati() == null) {
	    entity.setMercati(new Mercati());
	}
	if (entity.getDyn2Modellit() == null) {
	    entity.setDyn2Modellit(new Dyn2Modellit());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
