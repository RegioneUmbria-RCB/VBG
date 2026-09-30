package it.gruppoinit.pal.gp.core.features.movimenti.ziplogico.segnaposto;

import java.util.HashMap;
import java.util.Map;

import it.gruppoinit.pal.gp.core.features.movimenti.ziplogico.MovimentiZipLogicoService;
import it.gruppoinit.pal.gp.core.features.segnaposto.ISegnapostoResolver;
import it.gruppoinit.pal.gp.core.features.segnaposto.ISegnapostoService;
import it.gruppoinit.pal.gp.core.features.segnaposto.ISegnapostoTemplateWrapper;
import it.gruppoinit.pal.gp.core.features.segnaposto.SegnapostoTemplateWrapperFactory;
import it.gruppoinit.pal.gp.core.features.segnaposto.TipoFileEnum;

public class ZipLogicoSegnapostoFactory {

    private MovimentiZipLogicoService movimentiZipLogicoService;
    private ISegnapostoService segnapostoService;

    public ZipLogicoSegnapostoFactory(MovimentiZipLogicoService movimentiZipLogicoService, ISegnapostoService segnapostoService) {

	this.movimentiZipLogicoService = movimentiZipLogicoService;
	this.segnapostoService = segnapostoService;
    }

    public Map<String, ISegnapostoResolver> getResolvers(TipoFileEnum tipoFile, Integer codiceMovimento) {

	ISegnapostoTemplateWrapper wrapperService = SegnapostoTemplateWrapperFactory.getWrapperDaTipoFile(this.segnapostoService, tipoFile);
	Map<String, ISegnapostoResolver> retVal = new HashMap<String, ISegnapostoResolver>();
	retVal.put(ZipLogicoNumFileResolver.TAG, new ZipLogicoNumFileResolver(this.movimentiZipLogicoService, codiceMovimento));
	if (!TipoFileEnum.ODT.equals(tipoFile)) {
	    retVal.put(ZipLogicoTabellaHashRTFResolver.TAG,
		    new ZipLogicoTabellaHashRTFResolver(wrapperService, this.movimentiZipLogicoService, codiceMovimento));
	}
	return retVal;
    }
}
