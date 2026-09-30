package it.gruppoinit.nlaproxy.web;

import it.gruppoinit.impresainungiorno.schema.suap.ri.messages.ProtocolloSUAP;
import it.gruppoinit.impresainungiorno.schema.suap.ri.messages.RichiestaIscrizioneImpresaRiSPC;
import it.gruppoinit.pdd.ri.ws.InterazioniRIClient;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    // private static final Logger log = LoggerFactory.getLogger(MainController.class);
    @Autowired
    private InterazioniRIClient interazioniRIClient;

    @RequestMapping
    public String index(HttpServletRequest request, HttpServletResponse response) throws IOException {

	return "main/index";
    }

    @RequestMapping
    public String reloadConfiguration(HttpServletRequest request, HttpServletResponse response) throws IOException {

	interazioniRIClient.reloadPort();
	request.setAttribute("MSG", "Operazione avvenuta correttamente");
	return "main/index";
    }

    @RequestMapping
    public String test(HttpServletRequest request, HttpServletResponse response) throws IOException {

	request.setAttribute("MSG", "Invocato il metodo test");
	return "main/index";
    }
}
