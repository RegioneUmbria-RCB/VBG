/**
 * 
 */
package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.infocamera.schema.serviziocommercio.Risposta.Messaggio;
import it.gruppoinit.infocamera.schema.serviziocommercio.Risposta.Messaggio.Errore;
import it.gruppoinit.infocamera.schema.sigeproexport.InfoCameraConstans;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.service.InfoCameraService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * WS INFOCAMERA - INTEGRAZIONE CAMERA DI COMMERCIO
 * 
 * @author francescop
 * 
 */
@Controller
public class InfoCameraController extends BaseController {

    @Autowired
    private InfoCameraService infoCameraService;

    @RequestMapping
    public String infocamera(HttpServletRequest request, HttpServletResponse response) {

	// §§§BEGIN§§§
	String sToken = (String) request.getSession().getAttribute(WebConstants.TOKEN);
	// DATA SISTEMA
	Date date = new Date();
	DateFormat myDateFormatOut = new SimpleDateFormat("dd/MM/yyyy");
	String outdate = myDateFormatOut.format(date);
	// ////////////
	String codiceistanza = request.getParameter("codiceistanza");
	String codicemovimento = request.getParameter("codicemovimento");
	try {
	    Messaggio messaggio = infoCameraService.invioInfocameraWS(sToken, codiceistanza, outdate, codicemovimento);
	    String esito = messaggio.getEsito().value();
	    if (esito.contains(InfoCameraConstans.INFOCAMERA_RISPOSTA_ESITO_OK)) {
		request.setAttribute(InfoCameraConstans.INFOCAMERA_RISPOSTA_ESITO_PARAMETROREQUEST_OK, esito + " - Inviato a Infocamere");
	    } else if (esito.contains(InfoCameraConstans.INFOCAMERA_RISPOSTA_ESITO_KO)) {
		Errore errore = messaggio.getErrore();
		if (errore != null) {
		    request.setAttribute(InfoCameraConstans.INFOCAMERA_RISPOSTA_ESITO_PARAMETROREQUEST_KO,
			    "(" + errore.getCodice() + ") - " + errore.getDescrizione());
		} else {
		    request.setAttribute(InfoCameraConstans.INFOCAMERA_RISPOSTA_ESITO_PARAMETROREQUEST_KO, esito);
		}
	    }
	} catch (Exception e) {
	    request.setAttribute("error", e.getMessage());
	    return "infocamera/infocamera";
	}
	return "infocamera/infocamera";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
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
