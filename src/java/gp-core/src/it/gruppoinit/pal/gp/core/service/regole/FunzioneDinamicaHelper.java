/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.regole;

import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * @author francol
 * 
 */
public class FunzioneDinamicaHelper {

    private String nomeFunzione;
    private String corpoFunzione;
    private List<String> argomentiFunzione;
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
    
    public List<Dyn2Campi> getCampiOnChange(){
	
	return this.campiOnChange;
    }
    
    public String definizioneFunzione(){
	
	StringBuilder js = new StringBuilder();
	if(StringUtils.isNotBlank(this.nomeFunzione)){
	    js.append(this.nomeFunzione).append(" = ");
	}
	js.append("function(");
	appendiArgomenti(this.argomentiFunzione, js);
	js.append("){\r\n");
	Dyn2RegoleHelper.indent(js, 3).append(this.corpoFunzione).append("\r\n}");
	if(StringUtils.isNotBlank(this.nomeFunzione)){
	    js.append(";\r\n");
	}
	return js.toString();
    }
    
    public String chiamataFunzione(List<String> argomenti){
	
	StringBuilder js = new StringBuilder(this.nomeFunzione);
	js.append("(");
	appendiArgomenti(argomenti, js).append(")");
	return js.toString();
    }
    
    private StringBuilder appendiArgomenti(List<String> argomenti, StringBuilder js){
	
	if(argomenti != null){
	    for (int i = 0; i < argomenti.size(); i++) {
		if(i > 0){
		    js.append(",");
		}
		js.append(argomenti.get(i));
	    }
	}
	return js;
    }
}
