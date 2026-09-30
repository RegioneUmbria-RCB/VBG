package org.jmesa.filter;

import it.gruppoinit.pal.gp.core.domain.CommedilizieTipopareri;
import it.gruppoinit.pal.gp.core.service.CommedilizieTipopareriService;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jmesa.core.message.SpringMessages;
import org.jmesa.view.html.editor.DroplistFilterEditor;
import org.jmesa.web.SpringWebContext;
import org.springframework.web.context.ContextLoader;

public class CommedilizieTipopareriDropListEditor extends DroplistFilterEditor {

    @Override
    protected List<Option> getOptions() {

	CommedilizieTipopareriService commedilizieTipopareriService = (CommedilizieTipopareriService) ContextLoader.getCurrentWebApplicationContext()
		.getBean("commedilizieTipopareriServiceImpl", CommedilizieTipopareriService.class);
	List<CommedilizieTipopareri> list = commedilizieTipopareriService.findAll(null, null);
	List<Option> options = new ArrayList<Option>();
	for (CommedilizieTipopareri commedilizieTipopareri : list) {
	    options.add(new Option(commedilizieTipopareri.getDescrizione(), commedilizieTipopareri.getDescrizione()));
	}
	SpringMessages messages = new SpringMessages(null, (SpringWebContext) getWebContext());
	String esitoMessage = messages.getMessage("label.esito");
	String esito = null;
	if (StringUtils.isNotBlank(esitoMessage)) {
	    esito = "???label.esito???";
	}
	options.add(new Option(esito, "Esito"));
	return options;
    }
}
