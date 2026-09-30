package org.jmesa.custom;

import it.gruppoinit.pal.gp.core.domain.Normative;
import it.gruppoinit.pal.gp.core.service.NormativeService;

import java.util.ArrayList;
import java.util.List;

import org.jmesa.view.html.editor.DroplistFilterEditor;
import org.springframework.web.context.ContextLoader;


public class NormativaDroplist extends DroplistFilterEditor {

    @Override
    protected List<Option> getOptions() {

	NormativeService normativeService = (NormativeService) ContextLoader.getCurrentWebApplicationContext().getBean("normativeServiceImpl",
		NormativeService.class);
	List<Option> options = new ArrayList<Option>();
	List<Normative> list = normativeService.findAll(null, null);
	for (Normative normative : list) {
	    options.add(new Option(normative.getNormativa(), normative.getNormativa()));
	}
	return options;
    }
}
