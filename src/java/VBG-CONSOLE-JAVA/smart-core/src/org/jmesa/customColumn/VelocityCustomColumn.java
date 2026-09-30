package org.jmesa.customColumn;

import it.gruppoinit.pal.gp.core.service.VelocityRendererService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.util.Map;

import org.jmesa.view.editor.AbstractCellEditor;

public class VelocityCustomColumn extends AbstractCellEditor {

    private VelocityRendererService velocityRendererService;
    private Map<Object, Object> contextData;
    private String template;

    public VelocityCustomColumn(VelocityRendererService velocityRendererService, Map<Object, Object> contextData, String template) {

	this.velocityRendererService = velocityRendererService;
	this.contextData = contextData;
	this.template = template;
    }

    @Override
    public Object getValue(Object item, String property, int countRecords) {

	contextData.put("itemEntity", item);
	contextData.put("Utilities", new Utilities());
	String htmlModulo = velocityRendererService.renderTemplate(contextData, template);
	return htmlModulo;
    }
}
