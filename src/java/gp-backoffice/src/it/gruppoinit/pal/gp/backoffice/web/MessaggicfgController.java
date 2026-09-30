/**
 * 
 */
package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Messaggicfg;
import it.gruppoinit.pal.gp.core.domain.Messaggicfgbase;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.service.MessaggicfgService;
import it.gruppoinit.pal.gp.core.service.MessaggicfgbaseService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;

import java.util.ArrayList;
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
 * @author francescop
 * @author lucap
 * 
 */
@Controller
@SessionAttributes("messaggicfg")
public class MessaggicfgController extends BaseController<Messaggicfg> {

    @Autowired
    private MessaggicfgService messaggicfgService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private MessaggicfgbaseService messaggicfgbaseService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Messaggicfg> messaggicfgList = messaggicfgService.findAll(null, null);
	ModelMap model = new ModelMap(messaggicfgList);
	boolean export = createJMesaExport(request, response, messaggicfgList);
	if (export) {
	    return null;
	}
	model.addAttribute("messaggicfgList", messaggicfgList);
	return model;
    }

    @RequestMapping
    public String create(Model model, HttpServletRequest request) {

	List<Messaggicfgbase> messaggicfgbaseList = messaggicfgbaseService.findAll(null, null);
	Messaggicfg messaggicfg = new Messaggicfg();
	messaggicfg.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	fixRenderEntityProperty(messaggicfg);
	model.addAttribute("messaggicfgbasePresent", true);
	model.addAttribute("messaggicfg", messaggicfg);
	model.addAttribute("messaggicfgbaseList", messaggicfgbaseList);
	model.addAttribute(WebConstants.DISPATCH, WebConstants.DISPATCH_CREATE);
	request.setAttribute("tipo_contesto", WebConstants.INVIO_ISTANZA_BACKOFFICE);
	setPageAttributes(model);
	return "messaggicfg/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("messaggicfg") Messaggicfg messaggicfg, BindingResult result, SessionStatus status, Model model,
	    HttpServletRequest request) {

	model.addAttribute(WebConstants.DISPATCH, WebConstants.DISPATCH_VIEW);
	Byte flgInvio = 0;
	List<Byte> flgInvioList = messaggicfg.getFlgInvioList();
	if (flgInvioList != null && !flgInvioList.isEmpty()) {
	    flgInvio = flgInvioList.get(0);
	    for (int i = 1; i < flgInvioList.size(); i++) {
		flgInvio = ((Integer) (flgInvio.byteValue() + flgInvioList.get(i).byteValue())).byteValue();
	    }
	    messaggicfg.setFlgInvio(flgInvio);
	}
	Byte flgTipoInvio = 0;
	List<Byte> flgTipoInvioList = messaggicfg.getFlgTipoinvioList();
	if (flgTipoInvioList != null && !flgTipoInvioList.isEmpty()) {
	    flgTipoInvio = flgTipoInvioList.get(0);
	    for (int i = 1; i < flgTipoInvioList.size(); i++) {
		flgTipoInvio = ((Integer) (flgTipoInvio.byteValue() + flgTipoInvioList.get(i).byteValue())).byteValue();
	    }
	    messaggicfg.setFlgTipoinvio(flgTipoInvio);
	}
	fixMergeEntityProperty(messaggicfg);
	try {
	    messaggicfgService.insert(messaggicfg);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, messaggicfg, e);
	    List<Messaggicfgbase> messaggicfgbaseList = messaggicfgbaseService.findAll(null, null);
	    model.addAttribute("messaggicfgbaseList", messaggicfgbaseList);
	    model.addAttribute(WebConstants.DISPATCH, WebConstants.DISPATCH_CREATE);
	    request.setAttribute("tipo_contesto", messaggicfg.getMessaggicfgbase().getContesto());
	    fixRenderEntityProperty(messaggicfg);
	    return "messaggicfg/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + messaggicfg.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	List<Messaggicfgbase> messaggicfgbaseList = messaggicfgbaseService.findAll(null, null);
	Messaggicfg messaggicfg = messaggicfgService.findById(new PkId(codice));
	byte flgInvio = messaggicfg.getFlgInvio();
	List<Byte> flgInvioList = new ArrayList<Byte>();
	List<Integer> numeri = new ArrayList<Integer>();
	numeri.add(1);
	numeri.add(2);
	numeri.add(4);
	numeri.add(8);
	numeri.add(16);
	numeri.add(32);
	for (Integer n : numeri) {
	    boolean presente = (flgInvio & n.intValue()) == n.intValue() ? true : false;
	    if (presente) {
		flgInvioList.add(n.byteValue());
	    }
	}
	messaggicfg.setFlgInvioList(flgInvioList);
	byte flgTipoInvio = messaggicfg.getFlgTipoinvio();
	List<Byte> flgTipoInvioList = new ArrayList<Byte>();
	List<Integer> numeriTipoInvio = new ArrayList<Integer>();
	numeriTipoInvio.add(1);
	numeriTipoInvio.add(2);
	for (Integer n : numeriTipoInvio) {
	    boolean presente = (flgTipoInvio & n.intValue()) == n.intValue() ? true : false;
	    if (presente) {
		flgTipoInvioList.add(n.byteValue());
	    }
	}
	messaggicfg.setFlgTipoinvioList(flgTipoInvioList);
	fixRenderEntityProperty(messaggicfg);
	model.addAttribute("messaggicfg", messaggicfg);
	model.addAttribute("messaggicfgbaseList", messaggicfgbaseList);
	model.addAttribute(WebConstants.DISPATCH, WebConstants.DISPATCH_VIEW);
	request.setAttribute("tipo_contesto", messaggicfg.getMessaggicfgbase().getContesto());
	setPageAttributes(model);
	return "messaggicfg/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("messaggicfg") Messaggicfg messaggicfg, BindingResult result, SessionStatus status,
	    HttpServletRequest request, Model model) {

	model.addAttribute(WebConstants.DISPATCH, WebConstants.DISPATCH_VIEW);
	Byte flgInvio = 0;
	List<Byte> flgInvioList = messaggicfg.getFlgInvioList();
	if (flgInvioList != null && !flgInvioList.isEmpty()) {
	    flgInvio = flgInvioList.get(0);
	    for (int i = 1; i < flgInvioList.size(); i++) {
		flgInvio = ((Integer) (flgInvio.byteValue() + flgInvioList.get(i).byteValue())).byteValue();
	    }
	}
	Byte flgTipoInvio = 0;
	List<Byte> flgTipoInvioList = messaggicfg.getFlgTipoinvioList();
	if (flgTipoInvioList != null && !flgTipoInvioList.isEmpty()) {
	    flgTipoInvio = flgTipoInvioList.get(0);
	    for (int i = 1; i < flgTipoInvioList.size(); i++) {
		flgTipoInvio = ((Integer) (flgTipoInvio.byteValue() + flgTipoInvioList.get(i).byteValue())).byteValue();
	    }
	}
	messaggicfg.setFlgInvio(flgInvio);
	messaggicfg.setFlgTipoinvio(flgTipoInvio);
	fixMergeEntityProperty(messaggicfg);
	try {
	    messaggicfgService.update(messaggicfg);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, messaggicfg, e);
	    List<Messaggicfgbase> messaggicfgbaseList = messaggicfgbaseService.findAll(null, null);
	    model.addAttribute("messaggicfgbaseList", messaggicfgbaseList);
	    request.setAttribute("tipo_contesto", messaggicfg.getMessaggicfgbase().getContesto());
	    fixRenderEntityProperty(messaggicfg);
	    return "messaggicfg/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + messaggicfg.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("messaggicfg") Messaggicfg messaggicfg, BindingResult result, SessionStatus status, Model model) {

	Messaggicfg objToDelete = messaggicfgService.findById(messaggicfg.getId());
	try {
	    messaggicfgService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(messaggicfg);
	    return "messagicfg/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @Override
    protected void setPageAttributes(Model model) {

    }

    @Override
    protected void fixMergeEntityProperty(Messaggicfg entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Messaggicfg entity) {

	if (entity.getMessaggicfgbase() == null) {
	    entity.setMessaggicfgbase(new Messaggicfgbase());
	}
	if (entity.getSoftware() == null) {
	    entity.setSoftware(new Software());
	}
    }
}
