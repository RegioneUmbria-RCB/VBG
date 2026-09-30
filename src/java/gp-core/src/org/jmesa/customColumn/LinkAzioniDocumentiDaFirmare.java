package org.jmesa.customColumn;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.OggettiMetadati;
import it.gruppoinit.pal.gp.core.domain.OggettiMetadatiId;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.features.firmadigitale.DocumentiDaFirmareService;
import it.gruppoinit.pal.gp.core.features.oggetti.metadati.OggettiMetadatiService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.jmesa.core.message.SpringMessages;
import org.jmesa.view.editor.AbstractCellEditor;
import org.jmesa.web.SpringWebContext;

public class LinkAzioniDocumentiDaFirmare extends AbstractCellEditor {

    private DocumentiDaFirmareService documentiDaFirmareService;
    private OggettiMetadatiService oggettiMetadatiService;
    private Integer codiceUtenteLoggato;
    private ResponsabiliService responsabiliService;
    private HttpServletRequest request;

    public LinkAzioniDocumentiDaFirmare(DocumentiDaFirmareService documentiDaFirmareService, OggettiMetadatiService oggettiMetadatiService,
	    Integer codiceUtenteLoggato, ResponsabiliService responsabiliService, HttpServletRequest request) {

	super();
	this.documentiDaFirmareService = documentiDaFirmareService;
	this.oggettiMetadatiService = oggettiMetadatiService;
	this.codiceUtenteLoggato = codiceUtenteLoggato;
	this.responsabiliService = responsabiliService;
	this.request = request;
    }

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	// TODO VERIFICARE CHE IL FILE NON SIA BLOCCATO
	Object oggetto = (Object) item;
	// String firma = "firma";//getLabel("label.firma");
	Integer codiceDDF = (Integer) UtilityJmesa.getParametro(oggetto, "id.codice");
	Integer codiceOggetto = (Integer) UtilityJmesa.getParametro(oggetto, "oggetti.id.codice");
	OggettiMetadatiId id = new OggettiMetadatiId(codiceOggetto, WebConstants.OGGETTI_FILE_LOCKED_BY);
	OggettiMetadati omdt = oggettiMetadatiService.findById(id);
	Boolean operatoreLoggatoBloccaFile = Boolean.TRUE;
	String codiceOperatoreCheBlocca = "";
	if (omdt != null) {
	    codiceOperatoreCheBlocca = StringUtils.defaultString(omdt.getValore()).trim();
	    String codiceOpeLoggato = codiceUtenteLoggato.toString();
	    if (!codiceOpeLoggato.equalsIgnoreCase(codiceOperatoreCheBlocca)) {
		operatoreLoggatoBloccaFile = Boolean.FALSE;
	    }
	}
	if (operatoreLoggatoBloccaFile) {
	    documentiDaFirmareService.findReportHTMLOggettoDaFirmare(codiceOggetto);
	    String firmaSingola = "";
	    if (StringUtils.isNotBlank(codiceOperatoreCheBlocca)) {
		String bloccatoDa = getLabel("label.operatore_loggato_sta_bloccando_il_file", null);
		firmaSingola = "&nbsp;<img src=\"" + request.getSession().getServletContext().getContextPath()
			+ "/images/warning.gif\" style=\"cursor: pointer;\" alt=\"" + bloccatoDa + "\" title=\"" + bloccatoDa + "\"/>";
	    }
	    String firmaMultipla = "&nbsp;<input type=\"checkbox\" value=\"" + codiceDDF.toString() + "\" id=\"ddf_id_" + codiceDDF.toString()
		    + "\" class=\"documenti_da_firmare_cls\" onclick=\"addToDocDaFirmare();\"/>";
	    //  
	    return firmaMultipla + firmaSingola;
	} else {
	    String bloccatoDa = "";
	    String descrizione = "";
	    if (StringUtils.isNotBlank(codiceOperatoreCheBlocca)) {
		if (Utilities.isInteger(codiceOperatoreCheBlocca)) {
		    CodiceDescrizioneBean r = responsabiliService.findDescrizioneById(Integer.parseInt(codiceOperatoreCheBlocca));
		    if (r != null) {
			descrizione = r.getDescrizione() + " (" + r.getCodice() + ")";
		    }
		}
	    }
	    bloccatoDa = getLabel("label.file_bloccato_da", new Object[] { descrizione });
	    String result = "&nbsp;<img src=\"" + request.getSession().getServletContext().getContextPath()
		    + "/images/block-icon.gif\" style=\"cursor: pointer;\" alt=\"" + bloccatoDa + "\" title=\"" + bloccatoDa + "\"/>";
	    return result;
	}
    }

    // recupera la label dal CoreContext
    private String getLabel(String label, Object[] params) {

	SpringMessages messages = new SpringMessages(null, (SpringWebContext) getWebContext());
	String message = messages.getMessage(label, params);
	if (message == null) {
	    message = "???" + label + "???";
	}
	return message;
    }
}
