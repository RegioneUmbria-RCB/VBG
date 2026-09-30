package it.gruppoinit.pal.gp.core.features.movimenti.ziplogico.segnaposto;

import java.util.HashMap;
import java.util.Map;

import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;
import it.gruppoinit.pal.gp.core.features.segnaposto.ISegnapostoResolver;
import it.gruppoinit.pal.gp.core.features.segnaposto.ISegnapostoService;
import it.gruppoinit.pal.gp.core.features.segnaposto.ISegnapostoTemplateWrapper;
import it.gruppoinit.pal.gp.core.features.segnaposto.SegnapostoTemplateWrapperFactory;
import it.gruppoinit.pal.gp.core.features.segnaposto.TipoFileEnum;
import it.gruppoinit.pal.gp.core.service.TempLinkallegatiService;

public class LinkAllegatiNoHyperlinkFactory {

    private ISegnapostoService segnapostoService;
    private TempLinkallegatiService tempLinkallegatiService;

    public LinkAllegatiNoHyperlinkFactory(ISegnapostoService segnapostoService, TempLinkallegatiService tempLinkallegatiService) {

	this.segnapostoService = segnapostoService;
	this.tempLinkallegatiService = tempLinkallegatiService;
    }

    public Map<String, ISegnapostoResolver> getResolvers(TipoFileEnum tipoFile, DocumentMergeHelper userData) {

	ISegnapostoTemplateWrapper wrapperService = SegnapostoTemplateWrapperFactory.getWrapperDaTipoFile(this.segnapostoService, tipoFile);
	Map<String, ISegnapostoResolver> retVal = new HashMap<String, ISegnapostoResolver>();
	if (!TipoFileEnum.ODT.equals(tipoFile)) {
	    retVal.put(LinkallegatiNoHyperlinkRTFResolver.TAG,
		    new LinkallegatiNoHyperlinkRTFResolver(wrapperService, tempLinkallegatiService, userData));
	}
	return retVal;
    }
}
