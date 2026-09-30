package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellid;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.Mappature;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.helper.Dyn2CampiHelper;
import it.gruppoinit.pal.gp.core.domain.helper.Dyn2ModellitHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.MappatureCommand;
import it.gruppoinit.pal.gp.core.service.Dyn2CampiService;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService;
import it.gruppoinit.pal.gp.core.service.MappatureService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
@SessionAttributes("mappature")
public class MappatureController extends BaseController<Mappature> {

    @Autowired
    private MappatureService mappatureService;
    @Autowired
    private Dyn2ModellitService dyn2ModellitService;
    @Autowired
    private Dyn2CampiService dyn2CampiService;
    @Autowired
    private SoftwareService softwareService;

    @RequestMapping
    public String create(Model model) {

	MappatureCommand mappature = new MappatureCommand();
	mappature.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	fixRenderEntityProperty(mappature.getEntity());
	mappature.setDisplayMode(MappatureCommand.SEARCH);
	model.addAttribute("mappature", mappature);
	setPageAttributes(model);
	return "mappature/form";
    }

    @RequestMapping
    public String view(@RequestParam("codiceScheda") Integer codiceScheda,
	    @RequestParam(value = "nometagpeople", required = false) String nometagpeople, Model model, HttpServletRequest request) {

	MappatureCommand mappature = new MappatureCommand();
	setCommandAttributes(mappature, codiceScheda, nometagpeople);
	fixRenderCommandProperty(mappature);
	mappature.setDisplayMode(MappatureCommand.VIEW);
	model.addAttribute("mappature", mappature);
	setPageAttributes(model);
	return "mappature/form";
    }

    @RequestMapping
    public String aggiungi(@RequestParam("codicecampo") Integer codicecampo, @ModelAttribute("mappature") MappatureCommand mappature,
	    BindingResult result, SessionStatus status, Model model, HttpServletRequest request, HttpServletResponse response) {

	List<Dyn2CampiHelper> dyn2CampiHelpers = mappature.getModellitHelper().getCampiHelpers();
	for (Dyn2CampiHelper dyn2CampiHelper : dyn2CampiHelpers) {
	    if (dyn2CampiHelper.getCampo().getId().getCodice().equals(codicecampo)) {
		Mappature map = new Mappature();
		map.setDyn2Campi(dyn2CampiHelper.getCampo());
		map.setDyn2Modellit(mappature.getModellitHelper().getScheda());
		//int lastindex = dyn2CampiHelper.getMappatures().size();
		dyn2CampiHelper.getMappatures().add(map);
	    }
	}
	return "mappature/form";
    }

    @RequestMapping
    public String elimina(@RequestParam("codicecampo") Integer codicecampo, @RequestParam("codicemap") Integer codicemap,
	    @RequestParam(value = "indicemap", required = false) Integer indicemap, @ModelAttribute("mappature") MappatureCommand mappature,
	    BindingResult result, SessionStatus status, Model model, HttpServletRequest request, HttpServletResponse response) {

	if (codicemap == null) {
	    List<Dyn2CampiHelper> dyn2CampiHelpers = mappature.getModellitHelper().getCampiHelpers();
	    for (Dyn2CampiHelper dyn2CampiHelper : dyn2CampiHelpers) {
		if (dyn2CampiHelper.getCampo().getId().getCodice().equals(codicecampo)) {
		    dyn2CampiHelper.getMappatures().remove(dyn2CampiHelper.getMappatures().get(indicemap));
		}
	    }
	    return "mappature/form";
	} else {
	    List<Dyn2CampiHelper> dyn2CampiHelpers = mappature.getModellitHelper().getCampiHelpers();
	    for (Dyn2CampiHelper dyn2CampiHelper : dyn2CampiHelpers) {
		if (dyn2CampiHelper.getCampo().getId().getCodice().equals(codicecampo)) {
		    for (Mappature mappature2 : dyn2CampiHelper.getMappatures()) {
			if (mappature2.getId().getCodice() != null && mappature2.getId().getCodice().equals(codicemap)) {
			    dyn2CampiHelper.getMappatures().remove(mappature2);
			    break;
			}
		    }
		}
	    }
	    return "mappature/form";
	}
    }

    @RequestMapping
    public String update(@RequestParam(value = "codiceScheda", required = false) Integer codiceScheda,
	    @ModelAttribute("mappature") MappatureCommand mappature, BindingResult result, SessionStatus status, Model model,
	    HttpServletRequest request) {

	fixMergeEntityProperty(mappature.getEntity());
	try {
	    mappatureService.updateMapsFromDyn2ModellitHelper(mappature.getModellitHelper());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, mappature, true, e);
	    fixRenderEntityProperty(mappature.getEntity());
	    return "mappature/form";
	}
	status.setComplete();
	if (codiceScheda == null) {
	    if (mappature.getNometagpeople() != null) {
		return "redirect:view.htm?codiceScheda=" + mappature.getModellitHelper().getScheda().getId().getCodice() + "&nometagpeople="
			+ mappature.getNometagpeople() + "&status_msg=02";
	    } else {
		return "redirect:view.htm?codiceScheda=" + mappature.getModellitHelper().getScheda().getId().getCodice() + "&status_msg=02";
	    }
	} else {
	    return "redirect:view.htm?codiceScheda=" + codiceScheda;
	}
    }

    @Override
    protected void fixMergeEntityProperty(Mappature entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Mappature entity) {

	if (entity.getDyn2Modellit() == null) {
	    entity.setDyn2Modellit(new Dyn2Modellit());
	}
	if (entity.getDyn2Campi() == null) {
	    entity.setDyn2Campi(new Dyn2Campi());
	}
    }

    private void fixRenderCommandProperty(MappatureCommand command) {

	if (command.getDyn2Modellit() == null) {
	    command.setDyn2Modellit(new Dyn2Modellit());
	}
	if (command.getModellitHelper() == null) {
	    command.setModellitHelper(new Dyn2ModellitHelper());
	}
	if (command.getEntity() == null) {
	    command.setEntity(new Mappature());
	}
	if (command.getSoftware() == null) {
	    command.setSoftware(new Software());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }

    protected void setCommandAttributes(MappatureCommand command, Integer codiceScheda, String nometagpeople) {

	if (nometagpeople != null) {
	    command.setNometagpeople(nometagpeople);
	}
	Dyn2ModellitHelper modellitHelper = new Dyn2ModellitHelper();
	Dyn2Modellit scheda = dyn2ModellitService.findById(new PkId(codiceScheda));
	command.setDyn2Modellit(scheda);
	command.setSoftware(scheda.getSoftware());
	modellitHelper.setScheda(scheda);
	List<Dyn2CampiHelper> campiHelpers = new ArrayList<Dyn2CampiHelper>();
	Set<Dyn2Modellid> dyn2Modellids = scheda.getDyn2Modellids();
	if (dyn2Modellids != null && !dyn2Modellids.isEmpty()) {
	    for (Dyn2Modellid dyn2Modellid : dyn2Modellids) {
		Dyn2CampiHelper campiHelper = new Dyn2CampiHelper();
		if (EntityUtils.getNestedProperty(dyn2Modellid.getDyn2Campi(), "id.codice") != null) {
		    Dyn2Campi dyn2Campi = dyn2CampiService.findById(new PkId(dyn2Modellid.getDyn2Campi().getId().getCodice()));
		    campiHelper.setCampo(dyn2Campi);
		    // Il campo presenta una lista di mappature
		    if (EntityUtils.getNestedProperty(dyn2Campi, "id.codice") != null && dyn2Campi.getMappatures() != null
			    && !dyn2Campi.getMappatures().isEmpty()) {
			// Set delle mappature al campo
			List<Mappature> mappatures = new ArrayList<Mappature>();
			for (Mappature mappature2 : dyn2Campi.getMappatures()) {
			    // Occorre aggiungere solo le mappature per quella scheda
			    if (mappature2.getDyn2Modellit().getId().getCodice().equals(scheda.getId().getCodice())) {
				mappatures.add(mappature2);
			    }
			}
			if (!mappatures.isEmpty()) {
			    campiHelper.setMappatures(mappatures);
			} else {
			    List<Mappature> mappatures2 = new ArrayList<Mappature>();
			    Mappature mappatura = new Mappature();
			    mappatura.setDyn2Modellit(scheda);
			    mappatura.setTiporegola(0);
			    mappatures2.add(mappatura);
			    campiHelper.setMappatures(mappatures2);
			}
		    } else {
			// il campo non ha delle mappature configurate
			List<Mappature> mappatures = new ArrayList<Mappature>();
			Mappature mappatura = new Mappature();
			mappatura.setDyn2Modellit(scheda);
			mappatura.setTiporegola(0);
			mappatures.add(mappatura);
			campiHelper.setMappatures(mappatures);
		    }
		    campiHelpers.add(campiHelper);
		}
	    }
	}
	modellitHelper.setCampiHelpers(campiHelpers);
	command.setModellitHelper(modellitHelper);
    }
}
