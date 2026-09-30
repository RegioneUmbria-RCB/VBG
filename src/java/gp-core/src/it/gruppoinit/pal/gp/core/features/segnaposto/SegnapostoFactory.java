package it.gruppoinit.pal.gp.core.features.segnaposto;

import java.util.HashMap;
import java.util.Map;

import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.features.movimenti.ziplogico.MovimentiZipLogicoService;
import it.gruppoinit.pal.gp.core.features.movimenti.ziplogico.segnaposto.LinkAllegatiNoHyperlinkFactory;
import it.gruppoinit.pal.gp.core.features.movimenti.ziplogico.segnaposto.ZipLogicoSegnapostoFactory;
import it.gruppoinit.pal.gp.core.service.TempLinkallegatiService;

public class SegnapostoFactory {

    private ISegnapostoService segnapostoService;
    private MovimentiZipLogicoService movimentiZipLogicoService;
    private TempLinkallegatiService tempLinkallegatiService;

    public SegnapostoFactory(MovimentiZipLogicoService movimentiZipLogicoService, ISegnapostoService segnapostoService,
	    TempLinkallegatiService tempLinkallegatiService) {

	this.movimentiZipLogicoService = movimentiZipLogicoService;
	this.segnapostoService = segnapostoService;
	this.tempLinkallegatiService = tempLinkallegatiService;
    }

    public Map<String, ISegnapostoResolver> getResolvers(TipoFileEnum tipoFile, Movimenti movimento, DocumentMergeHelper userData) {

	ZipLogicoSegnapostoFactory factory = new ZipLogicoSegnapostoFactory(this.movimentiZipLogicoService, this.segnapostoService);
	LinkAllegatiNoHyperlinkFactory linkFactory = new LinkAllegatiNoHyperlinkFactory(this.segnapostoService, tempLinkallegatiService);
	Map<String, ISegnapostoResolver> resolvers = new HashMap<String, ISegnapostoResolver>();
	if (movimento != null) {
	    resolvers.putAll(factory.getResolvers(tipoFile, movimento.getId().getCodice()));
	    resolvers.putAll(linkFactory.getResolvers(tipoFile, userData));
	}
	return resolvers;
    }
}
