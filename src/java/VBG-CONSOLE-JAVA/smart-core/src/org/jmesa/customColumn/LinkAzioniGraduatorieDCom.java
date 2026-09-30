package org.jmesa.customColumn;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.jmesa.core.message.SpringMessages;
import org.jmesa.view.editor.AbstractCellEditor;
import org.jmesa.web.SpringWebContext;

public class LinkAzioniGraduatorieDCom extends AbstractCellEditor {

    private HttpServletRequest request;

    public LinkAzioniGraduatorieDCom(HttpServletRequest request) {

	super();
	this.request = request;
    }

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	String codiceGraduatoriat = (String) request.getParameter("codice");
	// Recupero il valore da passare come parametro da passare al controllor
	Object oggetto = (Object) item;
	//	String title = getLabel("label.edit.record");
	String confirmDelete = getLabel("javascript.confirm.delete");
	String rielabora = getLabel("label.rielabora");
	//	Integer codiceistanza = (Integer) UtilityJmesa.getParametro(oggetto, "graduatoried.istanza.id.codice");
	//	String codicesoftware = (String) UtilityJmesa.getParametro(oggetto, "graduatoried.istanza.software");
	Integer codiceGraduatoriaDCom = (Integer) UtilityJmesa.getParametro(oggetto, "id.codice");
//	String goToElabora = "../history/set.htm?ReturnTo=../graduatorietcom/view.htm?codice=" + codiceGraduatoriat
//		+ "&GoTo=../graduatorietcom/elaboraGraduatoriadcom.htm?codice=" + codiceGraduatoriaDCom;
	String goToElabora = "../graduatorietcom/elaboraGraduatoriadcom.htm?codice=" + codiceGraduatoriaDCom;
	String goToElimina = "../graduatorietcom/deleteGraduatoriadcom.htm?codice=" + codiceGraduatoriaDCom;
	String goToElaboraEncode = "";
	String valueItem = "";
	String valueItemElabora = "";
	String valueItemdelete = "";
	try {
	    goToElaboraEncode = URLEncoder.encode(goToElabora, "UTF-8");
	} catch (UnsupportedEncodingException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	/*	
		<a class="rielabora" href="javascript:historySet('${_urlback}', '../graduatorietcom/elaboraGraduatoriadcom.htm?codice=${graduatoriedComDTO_var.id.codice}', '')" title="<fmt:message key="label.rielabora" />${graduatorietcom_var.codice}">
		<label><fmt:message key="label.rielabora" /></label>
	</a>
		*/
	valueItemElabora = "<a class=\"rielabora\"  href=\"javascript:doHref('" + goToElabora + "','')\" title=\"" + rielabora + "\"><label>"
		+ rielabora + "</label></a>";
	valueItemdelete = "<a class=\"eliminaRiga\"  href=\"javascript:doHref('" + goToElimina + "','" + confirmDelete
		+ "')\" title=\"Elimina\"><label>Elimina</label></a>";
	valueItem = valueItemElabora + valueItemdelete;
	return valueItem;
    }

    // recupera la label dal CoreContext
    private String getLabel(String label) {

	SpringMessages messages = new SpringMessages(null, (SpringWebContext) getWebContext());
	String message = messages.getMessage(label);
	if (message == null) {
	    message = "???" + label + "???";
	}
	return message;
    }
}
