package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.report.helper.TypeReport;
import it.gruppoinit.pal.gp.core.report.model.ReportBase;
import it.gruppoinit.pal.gp.core.service.ReportService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@SessionAttributes(value = { "reportbase", "reportistanze" })
public class ReportController extends BaseController<Object> {

    @Autowired
    private ReportService reportService;
    @Autowired
    private SoftwareService softwareService;
    private final static String STATISTICHE = "STATISTICHE";
    private final static String STAMPE = "STAMPE";

    @RequestMapping
    public String createReportBase(Model model) {

	ReportBase reportbase = new ReportBase();
	reportbase.setTypeReport(TypeReport.OPERATORI);
	model.addAttribute("reportbase", reportbase);
	return "report/reportbase";
    }

    @RequestMapping
    public void printReportBase(Model model, @ModelAttribute("reportbase") ReportBase reportBase, BindingResult result, SessionStatus status,
	    HttpServletRequest request, HttpServletResponse response) {

	reportService.doReportDiBase(reportBase, request, response);
    }

    private void setConfigurazioneUtente(Model model, HttpServletRequest request) {

	// -----INIZIO GESTIONE CONFIGURAZIONE UTENTE----------------------
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SEARCH_DATI_CONC_AUT, "1", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SEARCH_DATI_LOCALIZZ, "1", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SEARCH_DATI_PROGETTO, "1", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_CERCAISTANZA_ALTRI_INDIRIZZI, "1", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_ORIDIMANENTO_ISTANZE, DAOOrderTypeEnum.ASC.toString(), request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_CAMPO_ORDINAMENTO_ISTANZE, "data", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_VALORE_STATO_ISTANZA, "stato_tutte", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISISTANZA, "1", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_GESTIONE_RICERCHE, "0", request);
	model.addAttribute("CONF_UTENTE_LISTISTANZA_VISISTANZA_CHECKED",
		toCheckedString(leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISISTANZA, "1", request)));
    }

    private String toCheckedString(String valore) {

	String result = "";
	if (StringUtils.defaultIfEmpty(valore, "0").equalsIgnoreCase("1")) {
	    result = " checked ";
	}
	return result;
    }

    @Override
    protected void setPageAttributes(Model model) {

	// TODO Auto-generated method stub
    }

    @Override
    protected void fixMergeEntityProperty(Object entity) {

	// TODO Auto-generated method stub
    }

    @Override
    protected void fixRenderEntityProperty(Object entity) {

	// TODO Auto-generated method stub
    }
}
