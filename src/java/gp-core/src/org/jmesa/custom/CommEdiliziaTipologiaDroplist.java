package org.jmesa.custom;

import it.gruppoinit.pal.gp.core.domain.CommedilizieTipologie;
import it.gruppoinit.pal.gp.core.service.CommedilizieTipologieService;

import java.util.ArrayList;
import java.util.List;

import org.jmesa.view.html.editor.DroplistFilterEditor;
import org.springframework.web.context.ContextLoader;

public class CommEdiliziaTipologiaDroplist extends DroplistFilterEditor {

    @Override
    protected List<Option> getOptions() {

	CommedilizieTipologieService commedilizieTipologieService = (CommedilizieTipologieService) ContextLoader.getCurrentWebApplicationContext()
		.getBean("commedilizieTipologieServiceImpl", CommedilizieTipologieService.class);
	List<Option> options = new ArrayList<Option>();
	List<CommedilizieTipologie> list = commedilizieTipologieService.findAll(null, null);
	for (CommedilizieTipologie commedilizieTipologie : list) {
	    options.add(new Option(commedilizieTipologie.getDescrizione(), commedilizieTipologie.getDescrizione()));
	}
	return options;
    }
}
