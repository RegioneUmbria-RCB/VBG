package it.gruppoinit.pal.gp.backoffice.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.InventarioprocEndo;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.service.InventarioprocEndoService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;

@Controller
@SessionAttributes(value = { "inventarioprocendo" })
public class InventarioprocEndoController extends BaseController<InventarioprocEndo> {

	@Autowired
	private InventarioprocEndoService inventarioprocEndoService;
	@Autowired
	private InventarioprocedimentiService inventarioprocedimentiService;
	@Autowired
	private ComuniassociatiService comuniassociatiService;

	@RequestMapping
	public ModelMap list(@RequestParam("codiceT") Integer codiceInventarioprocT,
			@RequestParam(required = false, value = "codicecomune") String codicecomune, HttpServletRequest request,
			HttpServletResponse response) {

		List<InventarioprocEndo> inventarioprocendoList = new ArrayList<InventarioprocEndo>();
		if (StringUtils.isNotBlank(codicecomune)) {
			inventarioprocendoList = inventarioprocEndoService.findByInventarioprocT(codicecomune,
					codiceInventarioprocT, null, null, null, null, false);
		} else {
			inventarioprocendoList = inventarioprocEndoService.findByInventarioprocT(ORMHelper.getIdcomune(),
					codiceInventarioprocT, null, null, null, null, false);
		}
		Inventarioprocedimenti inventarioprocedimentiT = inventarioprocedimentiService
				.findById(new PkId(codiceInventarioprocT));
		ModelMap model = new ModelMap(inventarioprocendoList);
		boolean export = createJMesaExport(request, response, inventarioprocendoList);
		if (export) {
			return null;
		}
		model.addAttribute("inventarioprocendoList", inventarioprocendoList);
		model.addAttribute("inventarioprocedimentiT", inventarioprocedimentiT);
		boolean isComuniAssociati = comuniassociatiService.isComuniassociati(ORMHelper.getIdcomune());
		model.addAttribute(WebConstants.COMUNIASSOCIATI_REQUEST_VARIABLE, Boolean.valueOf(isComuniAssociati));
		return model;
	}

	@RequestMapping
	public String create(@RequestParam("codiceT") Integer codiceInventarioprocT, Model model) {

		PkId id = new PkId(codiceInventarioprocT);
		Inventarioprocedimenti inventarioprocedimentiT = inventarioprocedimentiService.findById(id);
		InventarioprocEndo inventarioprocEndo = new InventarioprocEndo();
		inventarioprocEndo.setInventarioprocEndoT(inventarioprocedimentiT);
		fixRenderEntityProperty(inventarioprocEndo);
		model.addAttribute("inventarioprocendo", inventarioprocEndo);
		model.addAttribute("inventarioprocedimentiT", inventarioprocedimentiT);
		setPageAttributes(model);
		return "inventarioprocendo/form";
	}

	@RequestMapping
	public String insert(@ModelAttribute("inventarioprocendo") InventarioprocEndo inventarioprocEndo,
			BindingResult result, SessionStatus status, HttpServletRequest request) {

		if (!StringUtils.defaultIfEmpty(request.getParameter("inventarioprocEndoD.id.codice"), "").equals("")) {
			Integer codice = Integer.parseInt(request.getParameter("inventarioprocEndoD.id.codice"));
			PkId codiceId = new PkId(codice);
			Inventarioprocedimenti inventarioprocedimentiD = inventarioprocedimentiService.findById(codiceId);
			inventarioprocEndo.setInventarioprocEndoD(inventarioprocedimentiD);
		} else {
			inventarioprocEndo.setInventarioprocEndoD(null);
		}
		fixMergeEntityProperty(inventarioprocEndo);
		try {
			inventarioprocEndoService.insert(inventarioprocEndo);
		} catch (Exception e) {
			copyErrorsToBindingResult(result, inventarioprocEndo, e);
			fixRenderEntityProperty(inventarioprocEndo);
			return "inventarioprocendo/form";
		}
		status.setComplete();
		return "redirect:list.htm?codiceT=" + inventarioprocEndo.getInventarioprocEndoT().getId().getCodice();
	}

	@RequestMapping
	public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

		PkId id = new PkId(codice);
		InventarioprocEndo inventarioprocEndo = inventarioprocEndoService.findById(id);
		fixRenderEntityProperty(inventarioprocEndo);
		model.addAttribute("inventarioprocendo", inventarioprocEndo);
		setPageAttributes(model);
		return "inventarioprocendo/form";
	}

	@RequestMapping
	public String update(@ModelAttribute("inventarioprocendo") InventarioprocEndo inventarioprocEndo,
			BindingResult result, SessionStatus status, HttpServletRequest request) {

		fixMergeEntityProperty(inventarioprocEndo);
		try {
			inventarioprocEndoService.update(inventarioprocEndo);
		} catch (Exception e) {
			copyErrorsToBindingResult(result, inventarioprocEndo, e);
			fixRenderEntityProperty(inventarioprocEndo);
			return "inventarioprocendo/form";
		}
		status.setComplete();
		return "redirect:list.htm?codiceT=" + inventarioprocEndo.getInventarioprocEndoT().getId().getCodice();
	}

	@RequestMapping
	public String delete(@ModelAttribute("inventarioprocendo") InventarioprocEndo inventarioprocEndo,
			BindingResult result, SessionStatus status) {

		InventarioprocEndo objToDelete = inventarioprocEndoService.findById(inventarioprocEndo.getId());
		Integer codiceendoT = objToDelete.getInventarioprocEndoT().getId().getCodice();
		try {
			inventarioprocEndoService.delete(objToDelete);
		} catch (Exception e) {
			copyErrorsToBindingResult(result, objToDelete, e);
			fixRenderEntityProperty(inventarioprocEndo);
			return "inventarioprocendo/form";
		}
		status.setComplete();
		return "redirect:list.htm?codiceT=" + codiceendoT;
	}

	@Override
	protected void setPageAttributes(Model model) {

		boolean isComuniAssociati = comuniassociatiService.isComuniassociati(ORMHelper.getIdcomune());
		model.addAttribute(WebConstants.COMUNIASSOCIATI_REQUEST_VARIABLE, Boolean.valueOf(isComuniAssociati));
	}

	@Override
	protected void fixMergeEntityProperty(InventarioprocEndo entity) {

		if (entity.getComune() == null || (StringUtils.isBlank(entity.getComune().getCodicecomune()))) {
			entity.setComune(null);
		}
	}

	@Override
	protected void fixRenderEntityProperty(InventarioprocEndo entity) {

		if (entity.getInventarioprocEndoT() == null) {
			entity.setInventarioprocEndoT(new Inventarioprocedimenti());
		}
		if (entity.getInventarioprocEndoD() == null) {
			entity.setInventarioprocEndoD(new Inventarioprocedimenti());
		}
		if (entity.getComune() == null) {
			entity.setComune(new Comuni());
		}
	}
}
