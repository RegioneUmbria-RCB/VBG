package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.AlberoprocGruppiSmist;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.service.AlberoprocGruppiSmistService;
import it.gruppoinit.pal.gp.core.service.GruppiEndoprocedimentiTService;
import it.gruppoinit.pal.gp.core.service.TipiprocedureService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("gruppiSmistamentoCommand")
public class AlberoprocGruppiSmistController extends BaseController<GruppiSmistamentoCommand> {

    @Autowired
    private AlberoprocGruppiSmistService alberoprocGruppiSmistService;
    @Autowired
    private TipiprocedureService tipiprocedureService;
    @Autowired
    private GruppiEndoprocedimentiTService gruppiEndoprocedimentiTService;

    @RequestMapping
    public String view(Model model, HttpServletRequest request) {

	GruppiSmistamentoCommand command = new GruppiSmistamentoCommand();
	command.setDisplayMode(GruppiSmistamentoCommand.VIEW);
	model.addAttribute("gruppiSmistamentoCommand", command);
	setPageAttributes(model);
	return "alberoprocgruppismist/form";
    }

    @RequestMapping
    public String ajaxDettaglioGruppi(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

	List<AlberoprocGruppiSmist> gds = alberoprocGruppiSmistService.findAll(null, null);
	model.addAttribute("gruppis", gds);
	return "alberoprocgruppismist/ajaxDettaglioGruppi";
    }

    @Override
    protected void setPageAttributes(Model model) {

    }

    @Override
    protected void fixMergeEntityProperty(GruppiSmistamentoCommand entity) {

    }

    @Override
    protected void fixRenderEntityProperty(GruppiSmistamentoCommand entity) {

	if (entity == null) {
	    entity = new GruppiSmistamentoCommand();
	}
	entity.inizializzaCampi();
    }

    @RequestMapping
    public void ajaxSalvaRiga(@ModelAttribute("gruppiSmistamentoCommand") GruppiSmistamentoCommand gruppiSmistamentoCommand, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws Exception {

	Integer codiceGruppo1 = (Integer) EntityUtils.getNestedProperty(gruppiSmistamentoCommand, "gruppo1.id.codice");
	Integer codiceGruppo2 = (Integer) EntityUtils.getNestedProperty(gruppiSmistamentoCommand, "gruppo2.id.codice");
	Integer codiceGruppo3 = (Integer) EntityUtils.getNestedProperty(gruppiSmistamentoCommand, "gruppo3.id.codice");
	Integer codiceAlberoproc = (Integer) EntityUtils.getNestedProperty(gruppiSmistamentoCommand, "alberoproc.id.codice");
	Integer codiceProceduraScia = (Integer) EntityUtils.getNestedProperty(gruppiSmistamentoCommand, "proceduraScia.id.codice");
	Integer codiceProceduraOrdinario = (Integer) EntityUtils.getNestedProperty(gruppiSmistamentoCommand, "proceduraOrdinario.id.codice");
	Set<Integer> cs = new HashSet<Integer>();
	if (codiceGruppo1 != null) {
	    cs.add(codiceGruppo1);
	}
	if (codiceGruppo2 != null) {
	    cs.add(codiceGruppo2);
	}
	if (codiceGruppo3 != null) {
	    cs.add(codiceGruppo3);
	}
	try {
	    alberoprocGruppiSmistService.insertConfigurazione(cs, codiceAlberoproc, codiceProceduraScia, codiceProceduraOrdinario);
	    response.getOutputStream().write("OK".getBytes());
	} catch (Exception e) {
	    response.getOutputStream().write(e.getMessage().getBytes());
	}
    }

    @RequestMapping
    public void ajaxEliminaConfigurazione(@RequestParam("idRiga") Integer idRiga, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {

	AlberoprocGruppiSmist entity = alberoprocGruppiSmistService.findById(new PkId(idRiga));
	try {
	    alberoprocGruppiSmistService.delete(entity);
	    response.getOutputStream().write("OK".getBytes());
	} catch (Exception e) {
	    response.getOutputStream().write(e.getMessage().getBytes());
	}
    }
}
