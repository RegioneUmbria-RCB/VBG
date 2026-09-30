package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Formegiuridiche;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Tempificazioni;
import it.gruppoinit.pal.gp.core.report.model.ReportBase;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.AmministrazionireferentiService;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.FormegiuridicheService;
import it.gruppoinit.pal.gp.core.service.ReportService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.TempificazioniService;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {

    private AnagrafeService anagrafeService;
    private AmministrazioniService amministrazioniService;
    private AmministrazionireferentiService amministrazionireferentiService;
    private FormegiuridicheService formegiuridicheService;
    private ResponsabiliService responsabiliService;
    private TempificazioniService tempificazioniService;
    private static final Logger log = LoggerFactory.getLogger(ReportServiceImpl.class);

    @Autowired
    public void setAnagrafeService(AnagrafeService anagrafeService) {

	this.anagrafeService = anagrafeService;
    }

    @Autowired
    public void setAmministrazioniService(AmministrazioniService amministrazioniService) {

	this.amministrazioniService = amministrazioniService;
    }

    @Autowired
    public void setAmministrazionireferentiService(AmministrazionireferentiService amministrazionireferentiService) {

	this.amministrazionireferentiService = amministrazionireferentiService;
    }

    @Autowired
    public void setFormegiuridicheService(FormegiuridicheService formegiuridicheService) {

	this.formegiuridicheService = formegiuridicheService;
    }

    @Autowired
    public void setResponsabiliService(ResponsabiliService responsabiliService) {

	this.responsabiliService = responsabiliService;
    }

    @Autowired
    public void setTempificazioniService(TempificazioniService tempificazioniService) {

	this.tempificazioniService = tempificazioniService;
    }

    @Override
    public void doReport(Map map, HttpServletRequest request, HttpServletResponse response) {

    }

    @SuppressWarnings("unchecked")
    private void createReport(InputStream in, Map mappa, HttpServletRequest request, HttpServletResponse response) throws ServletException,
	    IOException, JRException {

	// 1- il datasource
	// 2- la mappa di parametri
	Map hm = new HashMap();
	log.debug("Inserisco la collezione di dati nell'oggetto compatibile con jasper report");
	JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource((Collection) mappa.get("datasource"));
	mappa.remove("datasource");
	Set k = mappa.keySet();
	for (Object object : k) {
	    hm.put(object, mappa.get(object));
	}
	// Esportazione con tool esterno possibilità di stampa e export in varie formati	
	//	JasperPrint jasperPrint = new JasperPrint();
	//	try {
	//	    jasperPrint = JasperFillManager.fillReport(in, hm, dataSource);
	//	    JasperViewer.viewReport(jasperPrint, false);
	//	} catch (Exception e) {
	//	    e.printStackTrace();
	//	}
	//
	// Visualizazione in pdf utilizzando il browser
	ServletOutputStream servletOutputStream = response.getOutputStream();
	byte[] bytes = null;
	log.debug("Creo il report come array di Byte");
	bytes = JasperRunManager.runReportToPdf(in, hm, dataSource);
	log.debug("Setto il content type");
	response.setContentType("application/pdf");
	if (bytes == null) {
	    log.error("L'array di byte contenete il Report è nullo");
	}
	log.debug("Setto il content length sulla response");
	response.setContentLength(bytes.length);
	log.debug("Mostro il report sul brower");
	servletOutputStream.write(bytes, 0, bytes.length);
	log.debug("Chiudo lo stream");
	servletOutputStream.flush();
	servletOutputStream.close();
    }

    @Override
    public void doReportDiBase(ReportBase reportBase, HttpServletRequest request, HttpServletResponse response) {

	try {
	    InputStream in = null;
	    Map<String, Collection<?>> map = new HashMap<String, Collection<?>>();
	    switch (reportBase.getTypeReport()) {
	    case OPERATORI:
		String pathTemplateReport = "";
		//in = this.getClass().getClassLoader().getResourceAsStream("reports/lista_operatori_v.jasper");
		in = this.getClass().getClassLoader().getResourceAsStream("reports/lista_operatori_v.jasper");
		if (in == null) {
		    log.error("Non è stato trovato il file");
		}
		//Link per testo locale
		//		pathTemplateReport = "C://sviluppo//report/compilati/lista_operatori_v.jasper";
		//in = new FileInputStream("C://sviluppo//report/compilati/lista_operatori_v.jasper");
		List<Responsabili> responsabilis = responsabiliService.findAll(null, null);
		map.put("datasource", responsabilis);
		createReport(in, map, request, response);
		break;
	    case FORME_GIURIDICHE:
		in = this.getClass().getClassLoader().getResourceAsStream("reports/lista_forme_giuridiche_v.jasper");
		if (in == null) {
		    log.error("Non è stato trovato il file");
		}
		//Link per testo locale
		//		pathTemplateReport = "C://sviluppo//report/compilati/lista_forme_giuridiche_v.jasper";
		//in = new FileInputStream("C://sviluppo//report/compilati/lista_forme_giuridiche_v.jasper");
		List<Formegiuridiche> formegiuridiches = formegiuridicheService.findAll(null, null);
		map.put("datasource", formegiuridiches);
		createReport(in, map, request, response);
		break;
	    case TEMPIFICAZIONI:
		in = this.getClass().getClassLoader().getResourceAsStream("reports/lista_tempificazioni_v.jasper");
		//Link per testo locale
		//		pathTemplateReport = "C://sviluppo//report/compilati/lista_tempificazioni_v.jasper";
		//in = new FileInputStream("C://sviluppo//report/compilati/lista_tempificazioni_v.jasper");
		if (in == null) {
		    log.error("Non è stato trovato il file");
		}
		List<Tempificazioni> tempificazionis = tempificazioniService.findAll(null, null);
		map.put("datasource", tempificazionis);
		createReport(in, map, request, response);
		break;
	    case AMMINISTRAZIONI:
		in = this.getClass().getClassLoader().getResourceAsStream("reports/lista_amministrazioni_v.jasper");
		//Link per testo locale
		//		pathTemplateReport = "C://sviluppo//report/compilati/lista_amministrazioni_v.jasper";
		//in = new FileInputStream("C://sviluppo//report/compilati/lista_amministrazioni_v.jasper");
		List<Amministrazioni> amministrazionis = amministrazioniService.findAll(null, null);
		map.put("datasource", amministrazionis);
		createReport(in, map, request, response);
		//		break;
		//	    case AMMINISTRAZIONE:
		////				in = this.getClass().getClassLoader().getResourceAsStream("../reports/amministrazione_v.jasper");
		//		//Link per testo locale
		////		pathTemplateReport = "C://sviluppo//report/compilati/amministrazione_v.jasper";
		//		List<Amministrazionireferenti> amministrazionireferentis = new ArrayList<Amministrazionireferenti>();
		//		if (EntityUtils.getNestedProperty(reportBase.getAmministrazioni(), "id.codice") != null) {
		//		    Amministrazioni amministrazioni = amministrazioniService.findById(new PkId(reportBase.getAmministrazioni().getId().getCodice()));
		//		    amministrazionireferentis = amministrazionireferentiService.findByAmministrazioni(amministrazioni);
		//		} else {
		//		    amministrazionireferentis = amministrazionireferentiService.findAll(null, null);
		//		}
		//		map.put("datasource", amministrazionireferentis);
		//		createReport(pathTemplateReport, map);
		//		break;
	    case RICHIEDENTI:
		in = this.getClass().getClassLoader().getResourceAsStream("reports/lista_richiedenti_tecnici.jasper");
		//Link per testo locale
		//in = new FileInputStream("C://sviluppo//report/compilati/lista_richiedenti_tecnici.jasper");//		pathTemplateReport = "C://sviluppo//report/compilati/lista_richiedenti_tecnici.jasper";
		if (in == null) {
		    log.error("Non è stato trovato il file");
		}
		List<Anagrafe> anagrafes = anagrafeService.findByFilter(reportBase.getAnagrafeFilter());
		map.put("datasource", anagrafes);
		createReport(in, map, request, response);
		break;
	    default:
	    }
	} catch (Exception e) {
	    log.error("Catch del metodo : doReportDiBase()");
	    throw new RuntimeException("Errore nella creazione del report: " + e.getMessage(), e);
	}
    }
}
