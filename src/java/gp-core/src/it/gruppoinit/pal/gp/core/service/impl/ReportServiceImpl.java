package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Esportazioni;
import it.gruppoinit.pal.gp.core.domain.Formegiuridiche;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Tempificazioni;
import it.gruppoinit.pal.gp.core.domain.TmpEsportazioni;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.report.model.ReportBase;
import it.gruppoinit.pal.gp.core.report.model.ReportIstanze;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.AmministrazionireferentiService;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.FormegiuridicheService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.service.ReportService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.TempificazioniService;
import it.gruppoinit.pal.gp.core.service.TmpEsportazioniService;
import it.gruppoinit.pal.gp.core.service.helper.TipicontestoesportazioniEnum;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
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
    private IstanzeService istanzeService;
    private MovimentiService movimentiService;
    private ResponsabiliService responsabiliService;
    private TempificazioniService tempificazioniService;
    private TmpEsportazioniService tmpEsportazioniService;
    private static final Logger log = LoggerFactory.getLogger(ReportServiceImpl.class);

    @Autowired
    public void setTmpEsportazioniService(TmpEsportazioniService tmpEsportazioniService) {

	this.tmpEsportazioniService = tmpEsportazioniService;
    }

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
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setFormegiuridicheService(FormegiuridicheService formegiuridicheService) {

	this.formegiuridicheService = formegiuridicheService;
    }

    @Autowired
    public void setMovimentiService(MovimentiService movimentiService) {

	this.movimentiService = movimentiService;
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

    @Override
    public void doReportAndStatisticheIstanze(ReportIstanze reportIstanze, HttpServletRequest request, HttpServletResponse response) {

	try {
	    InputStream in = null;
	    Map<String, Collection<?>> map = new HashMap<String, Collection<?>>();
	    String pathTemplateReport = "";
	    Istanze istanze = null;
	    switch (reportIstanze.getTypeReport()) {
	    case RICEVUTA_ISTANZA:
		in = this.getClass().getClassLoader().getResourceAsStream("reports/ricevuta_istanza.jasper");
		//Link per testo locale
		//		pathTemplateReport = "C://sviluppo//report/compilati/ricevuta_istanza.jasper";
		//in = new FileInputStream("C://sviluppo//report/compilati/ricevuta_istanza.jasper");
		if (in == null) {
		    log.error("Non è stato trovato il file");
		}
		List<Istanze> istanzes = new ArrayList<Istanze>();
		istanze = istanzeService.findById(new PkId(reportIstanze.getIstanze().getId().getCodice()));
		istanzes.add(istanze);
		map.put("datasource", istanzes);
		createReport(in, map, request, response);
		break;
	    case ITER_ISTANZA:
		in = this.getClass().getClassLoader().getResourceAsStream("reports/iter_istanze.jasper");
		//Link per testo locale
		//in = new FileInputStream("C://sviluppo//report/compilati/iter_istanze.jasper");
		//		pathTemplateReport = "C://sviluppo//report/compilati/iter_istanze.jasper";
		if (in == null) {
		    log.error("Non è stato trovato il file");
		}
		List<Movimenti> movimentis = new ArrayList<Movimenti>();
		if (EntityUtils.getNestedProperty(reportIstanze.getIstanze(), "id.codice") != null) {
		    istanze = istanzeService.findById(new PkId(reportIstanze.getIstanze().getId().getCodice()));
		    movimentis = movimentiService.findEseguitiByIstanza(istanze);
		    map.put("datasource", movimentis);
		} else {
		    istanzes = istanzeService.findByFilter(reportIstanze.getIstanzeFilter(), null, null);
		    Set<Movimenti> movimentiSet = movimentiService.findEseguitiByIstanze(istanzes);
		    map.put("datasource", movimentiSet);
		}
		createReport(in, map, request, response);
		break;
	    case REPORT_ISTANZA:
		in = this.getClass().getClassLoader().getResourceAsStream("reports/report_istanze.jasper");
		//Link per testo locale
		//		pathTemplateReport = "C://sviluppo//report/compilati/report_istanze.jasper";
		//in = new FileInputStream("C://sviluppo//report/compilati/report_istanze.jasper");
		if (in == null) {
		    log.error("Non è stato trovato il file");
		}
		List<Istanze> istanzes1 = new ArrayList<Istanze>();
		if (EntityUtils.getNestedProperty(reportIstanze.getIstanze(), "id.codice") != null) {
		    istanze = istanzeService.findById(new PkId(reportIstanze.getIstanze().getId().getCodice()));
		    istanzes1.add(istanze);
		} else {
		    istanzes1 = istanzeService.findByFilter(reportIstanze.getIstanzeFilter(), null, null);
		}
		map.put("datasource", istanzes1);
		createReport(in, map, request, response);
		break;
	    case STATISTICHE_ISTANZA_OPERATORE:
		in = this.getClass().getClassLoader().getResourceAsStream("reports/report_statistiche_istanze_operatori.jasper");
		//Link per testo locale
		//pathTemplateReport = "C://sviluppo//report/compilati/report_statistiche_istanze_operatori.jasper";
		if (in == null) {
		    log.error("Non è stato trovato il file");
		}
		List<Istanze> istanzesStatistiche = new ArrayList<Istanze>();
		istanzesStatistiche = istanzeService.findByFilter(reportIstanze.getIstanzeFilter(), null, null);
		map.put("datasource", istanzesStatistiche);
		createReport(in, map, request, response);
		break;
	    case STATISTICHE_ISTANZA_STATO:
		in = this.getClass().getClassLoader().getResourceAsStream("reports/report_statistiche_istanze_stato.jasper");
		//Link per testo locale
		//		pathTemplateReport = "C://sviluppo//report/compilati/report_statistiche_istanze_stato.jasper";
		if (in == null) {
		    log.error("Non è stato trovato il file");
		}
		List<Istanze> istanzesStatisticheStato = new ArrayList<Istanze>();
		istanzesStatisticheStato = istanzeService.findByFilter(reportIstanze.getIstanzeFilter(), null, null);
		map.put("datasource", istanzesStatisticheStato);
		createReport(in, map, request, response);
		break;
	    case STATISTICHE_ISTANZA_PROCEDURA:
		in = this.getClass().getClassLoader().getResourceAsStream("reports/report_statistica_istanze_procedura.jasper");
		//Link per testo locale
		//pathTemplateReport = "C://sviluppo//report/compilati/report_statistica_istanze_procedura.jasper";
		if (in == null) {
		    log.error("Non è stato trovato il file");
		}
		List<Istanze> istanzesStatisticheProcedura = new ArrayList<Istanze>();
		istanzesStatisticheProcedura = istanzeService.findByFilter(reportIstanze.getIstanzeFilter(), null, null);
		map.put("datasource", istanzesStatisticheProcedura);
		createReport(in, map, request, response);
		break;
	    case STATISTICHE_ISTANZA_PROCEDIMENTO:
		in = this.getClass().getClassLoader().getResourceAsStream("reports/report_statistiche_istanze_procedimento.jasper");
		//Link per testo locale
		//in = new FileInputStream("C://sviluppo//report/compilati/report_statistiche_istanze_procedimento.jasper");
		if (in == null) {
		    log.error("Non è stato trovato il file");
		}
		List<Istanze> istanzesStatisticheProcedimento = new ArrayList<Istanze>();
		istanzesStatisticheProcedimento = istanzeService.findByFilter(reportIstanze.getIstanzeFilter(), null, null);
		map.put("datasource", istanzesStatisticheProcedimento);
		createReport(in, map, request, response);
		break;
	    default:
		throw new RuntimeException("Non è stato scelto alcun report");
	    }
	} catch (Exception e) {
	    throw new RuntimeException("Errore nella creazione del report: " + e.getMessage(), e);
	}
    }

    @Override
    public String exportReportGenerico(Esportazioni esportazioni, String emailResponsabile, String codiceComune) {

	log.debug("exportModalitaPentaho# Start export pentaho, contesto : {}", TipicontestoesportazioniEnum.GENERICI);
	String sessionId = ORMHelper.getToken();
	log.debug("exportModalitaPentaho# Cancello i record su tmp_esportazioni con sessionId: {}", sessionId);
	tmpEsportazioniService.deleteBysessionId(sessionId);
	TmpEsportazioni entity = new TmpEsportazioni();
	entity.setCodice(-1);
	entity.setCodicecomune(codiceComune);
	entity.setData(Calendar.getInstance().getTime());
	entity.setIdcomune(ORMHelper.getIdcomune());
	entity.setSessionid(ORMHelper.getToken());
	tmpEsportazioniService.insert(entity);
	return sessionId;
    }
}
