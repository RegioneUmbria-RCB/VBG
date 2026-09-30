package it.gruppoinit.pal.gp.backoffice.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.BooleanUtils;
import org.jmesa.web.GenerateTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Archiviazioni;
import it.gruppoinit.pal.gp.core.domain.ArchiviazioniIstanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.jmesa.ArchiviazioniHelperTable;
import it.gruppoinit.pal.gp.core.service.ArchiviazioniIstanzeService;
import it.gruppoinit.pal.gp.core.service.ArchiviazioniManager;
import it.gruppoinit.pal.gp.core.service.ArchiviazioniService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

/**
 * Funzionalità di archiviazione documentale secondo specifiche Infocert Legaldocs Versione/Release n° : 1.1 Data
 * Versione/Release : 28/08/14
 * 
 * @author fabrizioc
 */
@Controller
public class ArchiviazioniController extends BaseController<Archiviazioni> {

    public static final Logger log = LoggerFactory.getLogger(ArchiviazioniController.class);
    @Autowired
    private ArchiviazioniManager archiviazioniManager;
    @Autowired
    private ArchiviazioniService archiviazioniService;
    @Autowired
    private ArchiviazioniIstanzeService archiviazioniIstanzeService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;

    @RequestMapping
    public String archivia(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

	try {
	    //archiviazioniManager.eseguiArchiviazione(null, null);
	    String[] softwares = { ORMHelper.getSoftware() };
	    archiviazioniManager.archiviazione(ORMHelper.getIdcomuneAlias(), softwares);
	} catch (Exception e) {
	    log.error("archivia", e);
	    return "redirect:list.htm?status_msg=03&error_msg=" + e.getMessage();
	}
	return "redirect:list.htm";
    }

    //    @RequestMapping
    //    public String archiviaPerIstanza(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
    //
    //	try {
    //	    archiviazioniManager.eseguiArchiviazionePerIstanza(null, null);
    //	} catch (Exception e) {
    //	    log.error("archivia", e);
    //	    return "redirect:list.htm?status_msg=03&error_msg=" + e.getMessage();
    //	}
    //	return "redirect:list.htm";
    //    }
    @RequestMapping
    public String archiviaPerOggetto(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

	try {
	    String dataDA = (String) request.getParameter("dataDa");
	    String dataA = (String) request.getParameter("dataA");
	    Date _dataDa = Utilities.parseDateString(dataDA, false);
	    Date _dataA = Utilities.parseDateString(dataA, false);
	    archiviazioniManager.eseguiArchiviazionePerOggetto(ORMHelper.getIdcomuneAlias(), ORMHelper.getSoftware(), _dataDa, _dataA);
	    // archiviazioniManager.eseguiArchiviazionePerOggetto(null, null);
	} catch (Exception e) {
	    log.error("archivia", e);
	    return "redirect:list.htm?status_msg=03&error_msg=" + e.getMessage();
	}
	return "redirect:list.htm";
    }

    @RequestMapping
    public String list(Model model, @RequestParam(value = "isSoloConErrori", required = false) Boolean isSoloConErrori, HttpServletRequest request,
	    HttpServletResponse response) {

	if (isSoloConErrori != null) {
	    isSoloConErrori = BooleanUtils.toBoolean(isSoloConErrori);
	} else {
	    isSoloConErrori = false;
	}
	model.addAttribute("htmlTable", getHtmlTable(request, response, isSoloConErrori));
	String tipoAlg = "";
	if (verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_ARCHIVIAZIONE_DOC_LEGALDOC)) {
	    tipoAlg = verticalizzazioniService.getVerticalizzazioniparametriValore(WebConstants.VERTICALIZZAZIONE_ARCHIVIAZIONE_DOC_LEGALDOC,
		    WebConstants.VERTICALIZZAZIONE_ARCHIVIAZIONE_DOC_LEGALDOC_ALGORITMO_ARCHIVIAZIONE);
	}
	String _dataDa = Utilities.formatDate(new Date(), false);
	model.addAttribute("da", _dataDa);
	model.addAttribute("tipoAlgoritmo", tipoAlg);
	model.addAttribute("isSoloConErrori", isSoloConErrori);
	return "archiviazioni/list";
    }

    @RequestMapping
    public String listEscluse(Model model, @RequestParam("archId") Integer archId, HttpServletRequest request, HttpServletResponse response) {

	List<ArchiviazioniIstanze> list = archiviazioniIstanzeService.findEscluse(archId);
	String _dataDa = Utilities.formatDate(new Date(), false);
	model.addAttribute("da", _dataDa);
	model.addAttribute("archId", archId);
	model.addAttribute("list", list);
	return "archiviazioni/listEscluse";
    }

    @RequestMapping
    public String listIstanze(Model model, @RequestParam("archId") Integer archId, HttpServletRequest request, HttpServletResponse response) {

	List<ArchiviazioniIstanze> list = archiviazioniIstanzeService.findIstanzaOggettoArchiviato(archId);
	String _dataDa = Utilities.formatDate(new Date(), false);
	model.addAttribute("da", _dataDa);
	model.addAttribute("archId", archId);
	model.addAttribute("list", list);
	return "archiviazioni/listEscluse";
    }

    @RequestMapping
    public String deleteEsclusa(HttpServletRequest request, @RequestParam("archId") Integer archId, @RequestParam("archIstId") Integer archIstId,
	    HttpServletResponse response) {

	ArchiviazioniIstanze archiviazioniIstanze = archiviazioniIstanzeService.findById(new PkId(archIstId));
	archiviazioniIstanzeService.delete(archiviazioniIstanze);
	return "redirect:listEscluse.htm?archId=" + archId;
    }

    @RequestMapping
    public String delete(HttpServletRequest request, @RequestParam("archId") Integer archId, HttpServletResponse response) {

	Archiviazioni entity = archiviazioniService.findById(new PkId(archId));
	archiviazioniService.delete(entity);
	return "redirect:list.htm";
    }

    @RequestMapping
    public String ajaxUpdateCorretto(HttpServletRequest request, @RequestParam("archId") Integer archId, HttpServletResponse response)
	    throws Exception {

	Archiviazioni entity = archiviazioniService.findById(new PkId(archId));
	if (BooleanUtils.isTrue(entity.getCorretto())) {
	    entity.setCorretto(null);
	} else {
	    entity.setCorretto(Boolean.TRUE);
	}
	archiviazioniService.update(entity);
	return "redirect:list.htm";
    }

    private String getHtmlTable(HttpServletRequest request, HttpServletResponse response, Boolean isSoloConErrori) {

	GenerateTable<Archiviazioni> gt = new ArchiviazioniHelperTable(isSoloConErrori);
	return gt.createJMesaList(request, response, "label.lista_archiviazioni", "arch_id", false);
    }

    @Override
    protected void fixMergeEntityProperty(Archiviazioni entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Archiviazioni entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
