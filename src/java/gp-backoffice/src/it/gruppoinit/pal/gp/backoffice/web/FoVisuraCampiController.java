package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.FoVisuraCampi;
import it.gruppoinit.pal.gp.core.domain.FoVisuraCampiBase;
import it.gruppoinit.pal.gp.core.domain.FoVisuraCampiId;
import it.gruppoinit.pal.gp.core.domain.FoVisuraContestiBase;
import it.gruppoinit.pal.gp.core.domain.FoVisuraContestiCampiBase;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.helper.FoVisuraCampiHelper;
import it.gruppoinit.pal.gp.core.domain.web.FoVisuraCampiCommand;
import it.gruppoinit.pal.gp.core.service.FoVisuraCampiService;
import it.gruppoinit.pal.gp.core.service.FoVisuraContestiBaseService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

/**
 * 
 * @author Luca Proietti
 */
@Controller
@SessionAttributes("fovisuracampi")
public class FoVisuraCampiController extends BaseController<FoVisuraCampi> {

    @Autowired
    private FoVisuraCampiService fovisuracampiService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private FoVisuraContestiBaseService foVisuraContestiBaseService;

    @RequestMapping
    public String create(Model model) {

	FoVisuraCampiCommand fovisuracampi = new FoVisuraCampiCommand();
	fovisuracampi.setDisplayMode(FoVisuraCampiCommand.NEW);
	fovisuracampi.getEntity().setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	fixRenderEntityProperty(fovisuracampi.getEntity());
	model.addAttribute("fovisuracampi", fovisuracampi);
	setPageAttributes(model);
	setCommandAttributes(fovisuracampi);
	return "fovisuracampi/form";
    }

    @RequestMapping
    public String view(@RequestParam("idContesto") String idContesto, Model model, HttpServletRequest request) {

	FoVisuraCampiCommand fovisuracampi = new FoVisuraCampiCommand();
	fovisuracampi.setDisplayMode(FoVisuraCampiCommand.VIEW);
	FoVisuraCampiHelper foVisuraCampiHelper = new FoVisuraCampiHelper();
	FoVisuraContestiBase foVisuraContestiBase = foVisuraContestiBaseService.findById(idContesto);
	foVisuraCampiHelper.setContestoBase(foVisuraContestiBase);
	List<FoVisuraCampi> foVisuraCampis = fovisuracampiService.findByContestiBase(foVisuraContestiBase);
	List<FoVisuraCampiBase> foVisuraCampiBases = new ArrayList<FoVisuraCampiBase>();
	for (FoVisuraContestiCampiBase foVisuraContestiCampiBase : foVisuraContestiBase.getFoVisuraContestiCampiBases()) {
	    foVisuraCampiBases.add(foVisuraContestiCampiBase.getFoVisuraCampiBase());
	}
	List<FoVisuraCampi> foVisuraCampisTemp = new ArrayList<FoVisuraCampi>();
	for (FoVisuraCampiBase foVisuraCampiBase : foVisuraCampiBases) {
	    FoVisuraCampi foVisuraCampiTemp = new FoVisuraCampi();
	    for (FoVisuraCampi foVisuraCampi2 : foVisuraCampis) {
		if (foVisuraCampiBase.getId().equals(foVisuraCampi2.getFoVisuraCampiBase().getId())) {
		    foVisuraCampiTemp = foVisuraCampi2;
		}
	    }
	    if (foVisuraCampiTemp.getPosizione() == null || foVisuraCampiTemp.getPosizione() == 0) {
		FoVisuraCampiId fId = new FoVisuraCampiId(foVisuraContestiBase.getId(), foVisuraCampiBase.getId());
		foVisuraCampiTemp.setFoVisuraCampiBase(foVisuraCampiBase);
		foVisuraCampiTemp.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
		foVisuraCampiTemp.setFoVisuraContestiBase(foVisuraContestiBase);
		foVisuraCampiTemp.setId(fId);
	    }
	    foVisuraCampisTemp.add(foVisuraCampiTemp);
	}
	foVisuraCampiHelper.setFoVisuraCampis(foVisuraCampisTemp);
	fovisuracampi.setFoVisuraCampiHelper(foVisuraCampiHelper);
	model.addAttribute("fovisuracampi", fovisuracampi);
	fixRenderEntityProperty(fovisuracampi.getEntity());
	setPageAttributes(model);
	setCommandAttributes(fovisuracampi);
	return "fovisuracampi/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("fovisuracampi") FoVisuraCampiCommand fovisuracampi, BindingResult result, SessionStatus status,
	    Model model, HttpServletRequest request) {

	fixMergeEntityProperty(fovisuracampi.getEntity());
	try {
	    fovisuracampiService.updateCampiFromFoVisuraCampiHelper(fovisuracampi.getFoVisuraCampiHelper());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, fovisuracampi, true, e);
	    fixRenderEntityProperty(fovisuracampi.getEntity());
	    return "fovisuracampi/form";
	}
	status.setComplete();
	return "redirect:view.htm?idContesto=" + fovisuracampi.getFoVisuraCampiHelper().getContestoBase().getId() + "&status_msg=02";
    }

    private void setCommandAttributes(FoVisuraCampiCommand command) {

	List<FoVisuraContestiBase> foVisuraContestiBases = foVisuraContestiBaseService.findAll(null, null);
	command.setFoVisuraContestiBases(foVisuraContestiBases);
    }

    @Override
    protected void fixMergeEntityProperty(FoVisuraCampi entity) {

    }

    @Override
    protected void fixRenderEntityProperty(FoVisuraCampi entity) {

	if (entity.getFoVisuraCampiBase() == null) {
	    entity.setFoVisuraCampiBase(new FoVisuraCampiBase());
	}
	if (entity.getFoVisuraContestiBase() == null) {
	    entity.setFoVisuraContestiBase(new FoVisuraContestiBase());
	}
	if (entity.getSoftware() == null) {
	    entity.setSoftware(new Software());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
