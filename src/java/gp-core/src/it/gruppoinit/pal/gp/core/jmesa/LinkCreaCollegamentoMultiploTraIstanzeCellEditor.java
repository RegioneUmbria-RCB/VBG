package it.gruppoinit.pal.gp.core.jmesa;

import org.jmesa.core.message.SpringMessages;
import org.jmesa.customColumn.LinkLabelBaseCellEditor;
import org.jmesa.customColumn.UtilityJmesa;
import org.jmesa.view.editor.AbstractCellEditor;
import org.jmesa.web.SpringWebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LinkCreaCollegamentoMultiploTraIstanzeCellEditor extends AbstractCellEditor {

    private static final Logger log = LoggerFactory.getLogger(LinkLabelBaseCellEditor.class);
    //    private String urlGoTo = "";
    //    private Integer codiceIstanzaDaconfigurare;
    private Boolean isPrecedenteIstanzeCollegate;

    public LinkCreaCollegamentoMultiploTraIstanzeCellEditor(Boolean isPrecedenteIstanzeCollegate) {

	super();
	//	this.urlGoTo = urlGoTo;
	//	this.codiceIstanzaDaconfigurare = codiceIstanzaDaconfigurare;
	this.isPrecedenteIstanzeCollegate = isPrecedenteIstanzeCollegate;
    }

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	// Recupero il valore da passare come parametro da passare al controllor
	Object oggetto = (Object) item;
	//	String goTo = "";
	Object parametro = null;
	parametro = UtilityJmesa.getParametro(oggetto, "codiceistanza");
	//	try {
	//	    goTo = urlGoTo + "?codiceIstanzaDaCollegare=" + parametro + "&codiceIstanza=" + codiceIstanzaDaconfigurare;
	//	    goTo = urlGoTo + "?codiceIstanzaDaCollegare=" + parametro + "&codiceIstanza=" + codiceIstanzaDaconfigurare;
	//	    goTo = URLEncoder.encode(goTo, "UTF-8");
	//	} catch (UnsupportedEncodingException e) {
	//	    log.debug("UnsupportedEncodingException: " + e);
	//	    e.printStackTrace();
	//	}
	String valueItem = "";
	String message = getLabel("label.crea_collegamento");
	//	valueItem = "<a style:'display:none' class=\"addColumn1\" href=\"javascript:doHref('" + goTo + "','')\" title=\"" + message + "\"> <label>"
	//		+ message + "</label></a>";
	if(!isPrecedenteIstanzeCollegate)
	{
	valueItem = "<input type=\"checkbox\" value=\"" + parametro + "\" id=\"codice_istanza_id" + parametro + ""
		+ "\" class=\"istanze_da_collegare_cls\" onclick=\"addToIstanzaDacollegare();\"/>";
	}else
	{
	    valueItem = "<input type=\"checkbox\" value=\"" + parametro + "\" id=\"codice_istanza_id" + parametro + ""
			+ "\" class=\"istanze_da_collegare_cls\" onclick=\"addToIstanzaPrecedenteDacollegare();\"/>";
	}
	return valueItem;
    }

    // recupera la label dal CoreContext
    private String getLabel(String label) {

	SpringMessages messages = new SpringMessages(null, (SpringWebContext) getWebContext());
	String message = messages.getMessage(label);
	if (message == null) {
	    message = "???" + label + "???";
	}
	//	String message = getCoreContext().getMessage(label);
	//	if (message == null) {
	//	    message = "???" + label + "???";
	//	}
	return message;
    }
}
