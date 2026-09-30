package it.gruppoinit.pal.gp.core.features.segnaposto;

public class SegnapostoTemplateMailWrapper {

    private ISegnapostoService service;

    public SegnapostoTemplateMailWrapper(ISegnapostoService service) {

	this.service = service;
    }

    public String getTemplate(String segnaposto) {

	return this.service.getTemplateHTML(segnaposto);
    }
}
