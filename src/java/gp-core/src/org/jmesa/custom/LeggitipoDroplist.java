package org.jmesa.custom;

import it.gruppoinit.pal.gp.core.domain.Leggitipi;
import it.gruppoinit.pal.gp.core.service.LeggitipiService;

import java.util.ArrayList;
import java.util.List;

import org.jmesa.view.html.editor.DroplistFilterEditor;
import org.springframework.web.context.ContextLoader;

public class LeggitipoDroplist extends DroplistFilterEditor {

    @Override
    protected List<Option> getOptions() {

	LeggitipiService leggitipiService = (LeggitipiService) ContextLoader.getCurrentWebApplicationContext().getBean("leggitipiServiceImpl",
		LeggitipiService.class);
	List<Option> options = new ArrayList<Option>();
	List<Leggitipi> list = leggitipiService.findAll(null, null);
	for (Leggitipi leggitipi : list) {
	    options.add(new Option(leggitipi.getLtDescrizione(), leggitipi.getLtDescrizione()));
	}
	return options;
    }
}
