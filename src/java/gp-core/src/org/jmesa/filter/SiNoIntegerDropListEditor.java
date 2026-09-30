package org.jmesa.filter;

import java.util.ArrayList;
import java.util.List;

import org.jmesa.core.message.SpringMessages;
import org.jmesa.view.html.editor.DroplistFilterEditor;
import org.jmesa.web.SpringWebContext;

public class SiNoIntegerDropListEditor extends DroplistFilterEditor {
    
    private Integer valoreCodificaSi;
    private Integer valoreCodificaNo;
    
    
    public SiNoIntegerDropListEditor(Integer valoreCodificaSi, Integer valoreCodificaNo) {

	super();
	this.valoreCodificaSi = valoreCodificaSi;
	this.valoreCodificaNo = valoreCodificaNo;
    }


    @Override
    protected List<Option> getOptions() {

	List<Option> options = new ArrayList<Option>();
	SpringMessages messages = new SpringMessages(null, (SpringWebContext) getWebContext());
	String si = messages.getMessage("label.si");
	String no = messages.getMessage("label.no");
	if (si == null) {
	    si = "???label.si???";
	}
	if (no == null) {
	    no = "???label.no???";
	}
	options.add(new Option(String.valueOf(valoreCodificaSi.intValue()), si));
	options.add(new Option(String.valueOf(valoreCodificaNo.intValue()), no));
	return options;
    }
}
