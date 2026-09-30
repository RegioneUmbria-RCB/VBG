package org.jmesa.customColumn;

import java.math.BigInteger;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.jmesa.core.message.SpringMessages;
import org.jmesa.view.editor.AbstractCellEditor;
import org.jmesa.web.SpringWebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LinkTrasformaInSpuntistaPerMercati extends AbstractCellEditor {

    private static final Logger log = LoggerFactory.getLogger(LinkTrasformaInSpuntistaPerMercati.class);
    private HttpServletRequest request;
    private Integer codiceIstanza;
    private String nomeCampoAutId;

    // Costruttore che gestisce la history back
    public LinkTrasformaInSpuntistaPerMercati(Integer codiceIstanza, String nomeCampoAutId, HttpServletRequest request) {

	super();
	this.codiceIstanza = codiceIstanza;
	this.request = request;
	this.nomeCampoAutId = nomeCampoAutId;
    }

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	if (StringUtils.isBlank(nomeCampoAutId)) {
	    nomeCampoAutId = "id.codice";
	}
	Object oggetto = (Object) item;
	Integer idAutorizzazione = null;
	if ("conc_id".equals(nomeCampoAutId)) {
	    BigInteger _idAutorizzazione = (BigInteger) UtilityJmesa.getParametro(oggetto, nomeCampoAutId);
	    idAutorizzazione = _idAutorizzazione.intValue();
	} else {
	    Integer _idAutorizzazione = (Integer) UtilityJmesa.getParametro(oggetto, nomeCampoAutId);
	    idAutorizzazione = _idAutorizzazione;
	}
	String valueItem = "";
	String gestioneSpuntista = getLabel("label.gestione_spuntista");
	StringBuffer funzionejavascript = new StringBuffer("javascript:visualizzaDettaglioAutorizSpuntista(").append(idAutorizzazione).append(",")
		.append(codiceIstanza).append(")");
	valueItem = "<a style=\"cursor: pointer;\" class=\"addColumn\" href=\"javascript:void 0\" title=\"" + gestioneSpuntista + "\" onclick=\""
		+ funzionejavascript + "\"><label>" + gestioneSpuntista + "</label></a>";
	return valueItem;
    }

    // recupera la label dal CoreContext
    private String getLabel(String label) {

	String message = "";
	try {
	    SpringMessages messages = new SpringMessages(null, (SpringWebContext) getWebContext());
	    message = messages.getMessage(label);
	    if (message == null) {
		message = "???" + label + "???";
	    }
	} catch (NullPointerException npe) {
	    log.warn("getLabel# Key label = {} non presente", label);
	    message = "???" + label + "???";
	}
	return message;
    }
}
