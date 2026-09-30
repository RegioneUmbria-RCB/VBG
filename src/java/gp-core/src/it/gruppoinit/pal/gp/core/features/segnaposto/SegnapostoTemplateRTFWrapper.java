package it.gruppoinit.pal.gp.core.features.segnaposto;

public class SegnapostoTemplateRTFWrapper implements ISegnapostoTemplateWrapper {

    private ISegnapostoService service;

    public SegnapostoTemplateRTFWrapper(ISegnapostoService service) {

	this.service = service;
    }

    @Override
    public String getTemplate(String segnaposto) {

	return this.service.getTemplateRTF(segnaposto);
    }
}
