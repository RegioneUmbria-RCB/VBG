package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeStorico;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.MercatiDService;
import it.gruppoinit.pal.gp.core.service.MercatiService;
import it.gruppoinit.pal.gp.core.service.MercatipresenzeStoricoService;
import it.gruppoinit.pal.gp.core.service.MercatipresenzeTService;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

// DAELIMINARE @Controller
@SessionAttributes("mercatipresenzeStorico")
public class MercatipresenzeStoricoController extends BaseController<MercatipresenzeStorico> {

    @Autowired
    private MercatipresenzeStoricoService mercatipresenzeStoricoService;
    @Autowired
    private MercatipresenzeTService mercatipresenzeTService;
    @Autowired
    private MercatiService mercatiService;
    @Autowired
    private MercatiDService mercatiDService;
    private final String PRESENZEINREQUEST = "presenzeInRequest";

    @RequestMapping
    public String view(Model model, @RequestParam("codice") Integer codice, HttpServletRequest request) {

	MercatipresenzeStorico mercatipresenzeStorico = mercatipresenzeStoricoService.findById(new PkId(codice));
	String[] catMercList = mercatipresenzeTService.findComboCategorieMerceologiche();
	fixRenderEntityProperty(mercatipresenzeStorico);
	model.addAttribute("mercatipresenzeStorico", mercatipresenzeStorico);
	model.addAttribute("catMercList", catMercList);
	setPageAttributes(model);
	return "mercatipresenzestorico/form";
    }

    @RequestMapping
    public String createSearch(Model model, @RequestParam(value = "resetAttrs", required = false) String resetAttrs, HttpServletRequest request) {

	if (request.getSession().getAttribute(PRESENZEINREQUEST) != null) {
	    request.getSession().removeAttribute(PRESENZEINREQUEST);
	}
	MercatipresenzeStorico mercatipresenzeStorico = null;
	Set<Integer> anni = mercatipresenzeStoricoService.findAnni();
	String[] catMercList = mercatipresenzeTService.findComboCategorieMerceologiche();
	if (StringUtils.isNotBlank(resetAttrs) && resetAttrs.equalsIgnoreCase("true")) {
	    mercatipresenzeStorico = new MercatipresenzeStorico();
	} else {
	    mercatipresenzeStorico = (MercatipresenzeStorico) request.getSession(false).getAttribute("mercatipresenzeStorico");
	    if (mercatipresenzeStorico == null) {
		mercatipresenzeStorico = new MercatipresenzeStorico();
	    } else {
		Mercati mercato = mercatiService.findById(mercatipresenzeStorico.getMercato().getId());
		List<MercatiD> posteggi = mercatiDService.findAllByMercato(mercato);
		model.addAttribute("posteggiMercato", posteggi);
	    }
	}
	fixRenderEntityProperty(mercatipresenzeStorico);
	model.addAttribute("mercatipresenzeStorico", mercatipresenzeStorico);
	model.addAttribute("catMercList", catMercList);
	model.addAttribute("anni", anni);
	setPageAttributes(model);
	return "mercatipresenzestorico/search";
    }

    @Override
    protected void fixMergeEntityProperty(MercatipresenzeStorico entity) {

	if (entity.getAnagrafe() != null && entity.getAnagrafe().getId().getCodice() == null) {
	    entity.setAnagrafe(null);
	}
	if (entity.getMercato() != null && entity.getMercato().getId().getCodice() == null) {
	    entity.setMercato(null);
	}
	if (entity.getMercatoUso() != null && entity.getMercatoUso().getId().getCodice() == null) {
	    entity.setMercatoUso(null);
	}
	if (entity.getPosteggio() != null && entity.getPosteggio().getId().getCodice() == null) {
	    entity.setPosteggio(null);
	}
    }

    @Override
    protected void fixRenderEntityProperty(MercatipresenzeStorico entity) {

	if (entity.getAnagrafe() == null) {
	    entity.setAnagrafe(new Anagrafe());
	}
	if (entity.getMercato() == null) {
	    entity.setMercato(new Mercati());
	}
	if (entity.getMercatoUso() == null) {
	    entity.setMercatoUso(new MercatiUso());
	}
	if (entity.getPosteggio() == null) {
	    entity.setPosteggio(new MercatiD());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
