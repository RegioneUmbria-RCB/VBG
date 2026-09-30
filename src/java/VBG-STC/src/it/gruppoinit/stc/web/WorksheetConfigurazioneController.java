package it.gruppoinit.stc.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.jmesa.model.AllItems;
import org.jmesa.model.TableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import it.gruppoinit.stc.domain.Configurazione;
import it.gruppoinit.stc.service.ConfigurazioneService;
import it.gruppoinit.stc.service.SicurezzaService;
import it.gruppoinit.stc.web.helper.WorksheetConfigurazione;
import it.gruppoinit.stc.web.helper.WorksheetSaverImpl;
import it.gruppoinit.stc.ws.client.NlaWebServiceClient;
import it.init.sigepro.rte.TestNLARequest;
import it.init.sigepro.rte.TestNLAResponse;
import it.init.sigepro.rte.XsdNlaVersion;
import it.init.sigepro.rte.XsdStcVersion;
import it.init.sigepro.rte.types.XsdTypesVersion;

@Controller
public class WorksheetConfigurazioneController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(WorksheetConfigurazioneController.class);
    @Autowired
    private ConfigurazioneService configurazioneService;
    @Autowired
    private NlaWebServiceClient nlaWebServiceClient;
    @Autowired
    private SicurezzaService sicurezzaService;

    @RequestMapping
    protected String list(HttpServletRequest request, HttpServletResponse response) throws Exception {

	// §§§BEGIN§§§
	if (request.getParameter("test") != null) {
	    if (request.getParameter("nodoDaTestare") == null) {
		request.setAttribute("testnodi", testNodiNLA(null));
	    } else {
		request.setAttribute("testnodi", testNodiNLA(request.getParameter("nodoDaTestare")));
	    }
	}
	String html = getTable(request, response);
	if (html == null) {
	    return null;
	}
	request.setAttribute("webapp_version", getVersionFromMANIFEST(request));
	request.setAttribute("stc_xsd_version", XsdStcVersion.V_1_14);
	request.setAttribute("nla_xsd_version", XsdNlaVersion.V_1_13);
	request.setAttribute("types_xsd_version", XsdTypesVersion.V_1_13);
	request.setAttribute("configurazioni", html);
	return "worksheetconfigurazione";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    protected String login(HttpServletRequest request, HttpServletResponse response) throws Exception {

	return "login";
    }

    private String getTable(HttpServletRequest request, HttpServletResponse response) {

	// §§§BEGIN§§§
	TableModel tableModel = new TableModel("worksheet", request, response);
	// tableModel.setItems(configurazioneService.findAll(null, null));
	tableModel.setItems(new AllItems() {

	    public Collection<?> getItems() {

		return configurazioneService.findAll(null, null);
	    }
	});
	WorksheetSaverImpl worksheetSaver = new WorksheetSaverImpl(configurazioneService);
	tableModel.saveWorksheet(worksheetSaver);
	tableModel.addRowObject(new Configurazione());
	WorksheetConfigurazione.setTableProperties(tableModel);
	String html = tableModel.render();
	request.setAttribute("saveResults", worksheetSaver.getSaveResults());
	return html;
    }

    private List<Configurazione> testNodiNLA(String nodoDaTestare) {

	if (StringUtils.isNotBlank(nodoDaTestare)) {
	    Integer codiceNodo = null;
	    try {
		codiceNodo = Integer.parseInt(nodoDaTestare);
	    } catch (NumberFormatException e) {
		throw new RuntimeException("Il nodo [" + nodoDaTestare + "] non è censito.");
	    }
	    Configurazione conf = configurazioneService.findById(codiceNodo);
	    if (conf != null) {
		try {
		    String nlaTypesVersion = testSingleNode(conf);
		    conf.setTransientTestNLASuccessMessage(nlaTypesVersion);
		} catch (Exception e) {
		    conf.setTransientTestNLAErrorMessage(e.getMessage());
		}
		List<Configurazione> configurazioni = new ArrayList<Configurazione>();
		configurazioni.add(conf);
		return configurazioni;
	    } else {
		throw new RuntimeException("Il nodo [" + nodoDaTestare + "] non è censito.");
	    }
	}
	List<Configurazione> configurazioni = configurazioneService.findAll(null, null);
	for (Configurazione configurazione : configurazioni) {
	    try {
		String nlaTypesVersion = testSingleNode(configurazione);
		configurazione.setTransientTestNLASuccessMessage(nlaTypesVersion);
	    } catch (Exception e) {
		log.error("testNodiNLA(): [url={}, err={}]", configurazione.getWsurl(), e.getMessage());
		configurazione.setTransientTestNLAErrorMessage(e.getMessage());
	    }
	}
	return configurazioni;
    }

    private String testSingleNode(Configurazione configurazione) {

	String token = sicurezzaService.getToken(configurazione);
	TestNLARequest testNLARequest = new TestNLARequest();
	testNLARequest.setTest(token);
	TestNLAResponse testNLAResponse = nlaWebServiceClient.testNLA(testNLARequest, configurazione);
	String vNlaXsd = testNLAResponse.getNlaXsdVersion() != null ? testNLAResponse.getNlaXsdVersion().value() : "Null";
	String vTypesXsd = testNLAResponse.getTypesXsdVersion() != null ? testNLAResponse.getTypesXsdVersion().value() : "Null";
	String nlaTypesVersion = "nla.xsd " + vNlaXsd + " | types.xsd " + vTypesXsd;
	return nlaTypesVersion;
    }

    private String getVersionFromMANIFEST(HttpServletRequest request) {

	ServletContext sContext = request.getSession().getServletContext();
	InputStream is = null;
	Manifest m = null;
	String version = "";
	try {
	    is = sContext.getResourceAsStream("/META-INF/MANIFEST.MF");
	    m = new Manifest(is);
	    Attributes attrs = m.getMainAttributes();
	    if (attrs != null) {
		version = attrs.getValue("Implementation-Version");
	    }
	} catch (Exception e) {
	    log.error("getMANIFEST: {}", e.getMessage());
	} finally {
	    if (is != null)
		try {
		    is.close();
		} catch (IOException e) {
		}
	}
	return version;
    }
}
