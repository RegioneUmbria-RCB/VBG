package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDettaglioDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.ListaParametriprotocolloPerEnteHelper;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.ParametriprotocolloPerEnteHelper;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.IParametriProtocolloPerEnteHelper;
import it.gruppoinit.pal.gp.core.features.protocollazione.verticalizzazione.VerticalizzazioneProtocolloAttivoServiceImpl;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

public abstract class ComunicazioniBaseController<T> extends BaseController<T> {

    @Autowired
    protected AnagrafeService anagrafeService;
    @Autowired
    protected AmministrazioniService amministrazioniService;
    @Autowired
    protected ResponsabiliService responsabiliService;
    @Autowired
    protected VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private IComunicazioniMassiveDettaglioDAO comunicazioniMassiveDettaglioDAO;

    protected String parametriProtocolloPerEnteHelperToJson(List<IParametriProtocolloPerEnteHelper> parametriProtocolloPerEnteHelper)
	    throws JAXBException {

	return Utilities.marshalJsonObject(parametriProtocolloPerEnteHelper, ParametriprotocolloPerEnteHelper.class, false,
		Utilities.JAXB_ENCODING_UTF_8);
    }

    protected ListaParametriprotocolloPerEnteHelper jsonToParametriProtocolloPerEnteHelper(HttpServletRequest request)
	    throws JAXBException, IOException {

	return Utilities.unMarshallJsonStream(request.getInputStream(), ListaParametriprotocolloPerEnteHelper.class, false);
    }

    @RequestMapping
    public void ajaxAggiornaMailOPec(@RequestParam(value = "codiceAnagrafe", required = false) Integer codiceAnagrafe,
	    @RequestParam(value = "codiceAmministrazione", required = false) Integer codiceAmministrazione,
	    @RequestParam(value = "codiceResponsabile", required = false) Integer codiceResponsabile,
	    @RequestParam(value = "email", required = false) String email, @RequestParam(value = "pec", required = false) String pec,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	String risultato = "{\"aggiorna_mail\":{\"codice\":\"CODE_MAIL\",\"descrizione\":\"DESC_MAIL\"}}";
	String codeMail = "OK";
	String descMail = "";
	if (codiceAnagrafe != null) {
	    try {
		anagrafeService.aggiornaMailEPec(codiceAnagrafe, email, pec);
	    } catch (Exception e) {
		codeMail = "KO";
		descMail = "Si sono verificati degli errori nell'aggiornamento della mail";
	    }
	} else if (codiceAmministrazione != null) {
	    try {
		amministrazioniService.aggiornaMailEPec(codiceAmministrazione, email, pec);
	    } catch (Exception e) {
		codeMail = "KO";
		descMail = "Si sono verificati degli errori nell'aggiornamento della mail";
	    }
	} else if (codiceResponsabile != null) {
	    try {
		responsabiliService.aggiornaMail(codiceResponsabile, email);
	    } catch (Exception e) {
		codeMail = "KO";
		descMail = "Si sono verificati degli errori nell'aggiornamento della mail";
	    }
	}
	risultato = risultato //
		.replace("CODE_MAIL", codeMail) //
		.replace("DESC_MAIL", descMail);
	response.setContentType("application/json");
	response.getOutputStream().write(risultato.getBytes());
    }

    protected void setParametriInModel(Model model, HttpServletRequest request, String codiceComune, String software) {

	/*
	 * Controllo se è attiva la verticalizzazione con il modulo PROTOCOLLO_ATTIVO
	 */
	if (verticalizzazioniService.isAttiva(VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE)) {
	    model.addAttribute("vert_prot_attivo", true);
	} else {
	    model.addAttribute("vert_prot_attivo", false);
	}
    }

    @RequestMapping
    public void ajaxStatoMailRiga(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException, JAXBException {

	Integer idRiga = Integer.parseInt(request.getParameter("idRiga"));
	int ris = comunicazioniMassiveDettaglioDAO.contaRicevuteMailPerDettaglio(idRiga.intValue());
	StringWriter sw = new StringWriter();
	sw.append("{\"ricevutePresenti\": " + ris + "}");
	response.setContentType("application/json");
	response.getOutputStream().write(sw.toString().getBytes("utf-8"));
    }
}
