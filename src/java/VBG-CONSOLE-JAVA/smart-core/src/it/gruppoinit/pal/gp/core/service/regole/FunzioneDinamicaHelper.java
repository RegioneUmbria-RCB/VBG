/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.regole;

import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.StringUtils;

/**
 * @author francol
 * 
 */
@XmlRootElement(name = "FunzioneDinamicaHelper")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FunzioneDinamicaHelper", propOrder = { "nomeFunzione", "corpoFunzione", "argomentiFunzione", "campiOnChange" })
public class FunzioneDinamicaHelper {

    @XmlElement(name = "nomeFunzione", required = true)
    private String nomeFunzione;
    @XmlElement(name = "corpoFunzione", required = false)
    private String corpoFunzione;
    @XmlElement(name = "argomentiFunzione", required = false)
    private List<String> argomentiFunzione;
    @XmlElement(name = "campiOnChange", required = false)
    private List<Dyn2Campi> campiOnChange = new ArrayList<Dyn2Campi>();

    public String getNomeFunzione() {

	return nomeFunzione;
    }

    public void setNomeFunzione(String nomeFunzione) {

	this.nomeFunzione = nomeFunzione;
    }

    public String getCorpoFunzione() {

	return corpoFunzione;
    }

    public void setCorpoFunzione(String corpoFunzione) {

	this.corpoFunzione = corpoFunzione;
    }

    public List<String> getArgomentiFunzione() {

	return argomentiFunzione;
    }

    public void setArgomentiFunzione(List<String> argomentiFunzione) {

	this.argomentiFunzione = argomentiFunzione;
    }

    public List<Dyn2Campi> getCampiOnChange() {

	return this.campiOnChange;
    }

    public String definizioneFunzione() {

	StringBuilder js = new StringBuilder();
	if (StringUtils.isNotBlank(this.nomeFunzione)) {
	    js.append(this.nomeFunzione).append(" = ");
	}
	js.append("function(");
	appendiArgomenti(this.argomentiFunzione, js);
	js.append("){\r\n");
	Dyn2RegoleHelper.indent(js, 3).append(this.corpoFunzione).append("\r\n}");
	if (StringUtils.isNotBlank(this.nomeFunzione)) {
	    js.append(";\r\n");
	}
	return js.toString();
    }

    public String chiamataFunzione(List<String> argomenti) {

	StringBuilder js = new StringBuilder(this.nomeFunzione);
	js.append("(");
	appendiArgomenti(argomenti, js).append(")");
	return js.toString();
    }

    private StringBuilder appendiArgomenti(List<String> argomenti, StringBuilder js) {

	if (argomenti != null) {
	    for (int i = 0; i < argomenti.size(); i++) {
		if (i > 0) {
		    js.append(",");
		}
		js.append(argomenti.get(i));
	    }
	}
	return js;
    }
}
