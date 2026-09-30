package it.gruppoinit.pal.gp.core.features.movimenti.ziplogico.segnaposto;

import java.util.HashMap;
import java.util.Map;

import it.gruppoinit.pal.gp.core.features.movimenti.ziplogico.MovimentiZipLogicoService;
import it.gruppoinit.pal.gp.core.features.segnaposto.ISegnapostoMailResolver;
import it.gruppoinit.pal.gp.core.features.segnaposto.ISegnapostoService;
import it.gruppoinit.pal.gp.core.features.segnaposto.SegnapostoTemplateMailWrapper;

public class ZipLogicoSegnapostoMailFactory {

    private MovimentiZipLogicoService movimentiZipLogicoService;
    private ISegnapostoService segnapostoService;

    public ZipLogicoSegnapostoMailFactory(MovimentiZipLogicoService movimentiZipLogicoService, ISegnapostoService segnapostoService) {

	this.movimentiZipLogicoService = movimentiZipLogicoService;
	this.segnapostoService = segnapostoService;
    }

    public Map<String, ISegnapostoMailResolver> getResolvers(Integer codiceMovimento) {

	SegnapostoTemplateMailWrapper wrapperService = new SegnapostoTemplateMailWrapper(this.segnapostoService);
	Map<String, ISegnapostoMailResolver> retVal = new HashMap<String, ISegnapostoMailResolver>();
	//retVal.put(ZipLogicoNumFileResolver.TAG, new ZipLogicoNumFileResolver(this.movimentiZipLogicoService, codiceMovimento));
	retVal.put(ZipLogicoTabellaHashMailResolver.TAG,
		new ZipLogicoTabellaHashMailResolver(wrapperService, this.movimentiZipLogicoService, codiceMovimento));
	return retVal;
    }
}
