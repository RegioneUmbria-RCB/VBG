/**
 * 
 */
package it.gruppoinit.pal.gp.backoffice.web;

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
import org.springframework.web.multipart.MultipartFile;

import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.Oggettiinfo;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipologieoggetto;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.service.OggettiinfoService;
import it.gruppoinit.pal.gp.core.service.TipologieoggettoService;

/**
 * @author francescop
 * 
 */
@Controller
@SessionAttributes("oggettiinfo")
public class OggettiinfoController extends BaseController<Oggettiinfo> {

    @Autowired
    private OggettiinfoService oggettiinfoService;
    @Autowired
    private TipologieoggettoService tipologieoggettoService;
    @Autowired
    private OggettiService oggettiService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Oggettiinfo> oggettiinfoList = oggettiinfoService.findAll(null, null);
	ModelMap model = new ModelMap(oggettiinfoList);
	boolean export = createJMesaExport(request, response, oggettiinfoList);
	if (export)
	    return null;
	model.addAttribute("oggettiinfoList", oggettiinfoList);
	return model;
    }

    @RequestMapping
    public String delete(@ModelAttribute("oggettiinfo") Oggettiinfo oggettiinfo, BindingResult result, SessionStatus status) {

	Oggettiinfo objToDelete = oggettiinfoService.findById(oggettiinfo.getId());
	Oggetti oggetto = oggettiService.findById(new PkId(oggettiinfo.getId().getCodice()));
	objToDelete.setOggetto(oggetto);
	try {
	    oggettiinfoService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(oggettiinfo);
	    return "oggettiinfo/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @RequestMapping
    public String insert(@RequestParam(value = "fileUpload", required = false) MultipartFile mpFile, Model model,
	    @ModelAttribute("oggettiinfo") Oggettiinfo oggettiinfo, BindingResult result, SessionStatus status) throws Exception {

	List<Tipologieoggetto> tipologieoggettoList = tipologieoggettoService.findAll(null, null);
	model.addAttribute("tipologieoggettoList", tipologieoggettoList);
	fixMergeEntityProperty(oggettiinfo);
	byte fileContent[] = null;
	String fileName = null;
	if (mpFile != null) {
	    fileContent = mpFile.getBytes();
	    fileName = mpFile.getOriginalFilename();
	}
	try {
	    oggettiinfoService.insertOggettiLibreria(oggettiinfo, fileContent, fileName);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, oggettiinfo, e);
	    fixRenderEntityProperty(oggettiinfo);
	    return "oggettiinfo/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + oggettiinfo.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String update(@RequestParam(value = "fileUpload", required = false) MultipartFile mpFile, Model model,
	    @ModelAttribute("oggettiinfo") Oggettiinfo oggettiinfo, BindingResult result, SessionStatus status, HttpServletRequest request)
	    throws Exception {

	List<Tipologieoggetto> tipologieoggettoList = tipologieoggettoService.findAll(null, null);
	model.addAttribute("tipologieoggettoList", tipologieoggettoList);
	fixMergeEntityProperty(oggettiinfo);
	byte fileContent[] = null;
	String fileName = null;
	if (mpFile != null) {
	    fileContent = mpFile.getBytes();
	    fileName = mpFile.getOriginalFilename();
	}
	try {
	    oggettiinfoService.updateOggettiLibreria(oggettiinfo, fileContent, fileName);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, oggettiinfo, e);
	    fixRenderEntityProperty(oggettiinfo);
	    return "oggettiinfo/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + oggettiinfo.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String create(Model model) {

	List<Tipologieoggetto> tipologieoggettoList = tipologieoggettoService.findAll(null, null);
	Oggettiinfo oggettiinfo = new Oggettiinfo();
	fixRenderEntityProperty(oggettiinfo);
	model.addAttribute("oggettiinfo", oggettiinfo);
	model.addAttribute("tipologieoggettoList", tipologieoggettoList);
	setPageAttributes(model);
	return "oggettiinfo/form";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	List<Tipologieoggetto> tipologieoggettoList = tipologieoggettoService.findAll(null, null);
	model.addAttribute("tipologieoggettoList", tipologieoggettoList);
	Oggetti oggetto = oggettiService.findById(new PkId(codice));
	PkId id = new PkId(codice);
	Oggettiinfo oggettiinfo = oggettiinfoService.findById(id);
	oggettiinfo.setOggetto(oggetto);
	fixRenderEntityProperty(oggettiinfo);
	model.addAttribute("oggettiinfo", oggettiinfo);
	setPageAttributes(model);
	return "oggettiinfo/form";
    }

    @Override
    protected void fixMergeEntityProperty(Oggettiinfo entity) {

	if (entity.getTipologieoggetto() != null && entity.getTipologieoggetto().getId() != null
		&& entity.getTipologieoggetto().getId().getCodice() == null) {
	    entity.setTipologieoggetto(null);
	}
	if (entity.getOggetto() != null && entity.getOggetto().getId() != null && entity.getOggetto().getId().getCodice() == null) {
	    entity.getId().setCodice(null);
	}
    }

    @Override
    protected void fixRenderEntityProperty(Oggettiinfo entity) {

	if (entity.getTipologieoggetto() == null) {
	    entity.setTipologieoggetto(new Tipologieoggetto());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
