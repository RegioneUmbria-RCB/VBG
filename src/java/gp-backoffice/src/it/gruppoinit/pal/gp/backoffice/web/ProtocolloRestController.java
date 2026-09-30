package it.gruppoinit.pal.gp.backoffice.web;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.ProtocollazioneService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;

@Controller
public class ProtocolloRestController extends BaseJsonController<String>{
    
    private ProtocollazioneService protocollazioneService;
    
    @Autowired
    public void setProtocollazioneService(ProtocollazioneService protocollazioneService) {

	this.protocollazioneService = protocollazioneService;
    }
    
    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private MovimentiService movimentiService;
    
    @RequestMapping
    public void ajaxAccettaProtocollo(@RequestParam("codiceIstanza") Integer codiceIstanza, HttpServletRequest request, HttpServletResponse response)
	    throws Exception {

	try {
	    Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	    String token = ORMHelper.getToken();
	    String numeroprotocollo = istanza.getNumeroprotocollo();
	    String annoprotocollo;
	    if (istanza.getDataprotocollo() != null) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(istanza.getDataprotocollo());
		annoprotocollo = calendar.get(Calendar.YEAR) + "";
	    } else {
		annoprotocollo = null;
	    }
	    String idprotocollo = istanza.getFkidprotocollo();
	    String software;
	    if (istanza.getSoftware() == null) {
		software = null;
	    } else {
		software = istanza.getSoftware().getCodice();
	    }
	    String codicecomune;
	    if (istanza.getComune() == null) {
		codicecomune = null;
	    } else {
		codicecomune = istanza.getComune().getCodicecomune();
	    }
	    protocollazioneService.eseguiAccettazione(token, numeroprotocollo, annoprotocollo, idprotocollo, software, codicecomune);
	    response.setContentType("application/json");
	    response.getOutputStream().write("{\"status\": \"OK\"}".getBytes());
	} catch (Exception ex) {
	    super.writeJsonError(response, ex.getMessage());
	}
    }
    
    @RequestMapping
    public void ajaxAccettaMoProtocollo(@RequestParam("codiceMovimento") Integer codiceMovimento, HttpServletRequest request, HttpServletResponse response)
	    throws Exception {

	try {
	    Movimenti movimento = movimentiService.findById(new PkId(codiceMovimento));
	    String token = ORMHelper.getToken();
	    String numeroprotocollo = movimento.getNumeroprotocollo();
	    String annoprotocollo;
	    if (movimento.getDataprotocollo() != null) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(movimento.getDataprotocollo());
		annoprotocollo = calendar.get(Calendar.YEAR) + "";
	    } else {
		annoprotocollo = null;
	    }
	    String idprotocollo = movimento.getFkidprotocollo();
	    String software;
	    Istanze istanza = movimento.getIstanza();
	    
	    if (istanza == null) {
		software = null;
	    } else if (istanza.getSoftware() == null) {
		software = null;
	    } else {
		software = istanza.getSoftware().getCodice();
	    }
	    
	    String codicecomune;
	    if (istanza == null) {
		codicecomune = null;
	    } else if (istanza.getComune() == null) {
		codicecomune = null;
	    } else {
		codicecomune = istanza.getComune().getCodicecomune();
	    }
	    
	    protocollazioneService.eseguiAccettazione(token, numeroprotocollo, annoprotocollo, idprotocollo, software, codicecomune);
	    response.setContentType("application/json");
	    response.getOutputStream().write("{\"status\": \"OK\"}".getBytes());
	} catch (Exception ex) {
	    super.writeJsonError(response, ex.getMessage());
	}
    }
}
