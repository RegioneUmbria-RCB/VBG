package it.gruppoinit.pal.gp.core.features.segnaposto;

import java.util.HashMap;
import java.util.Map;

import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.features.movimenti.ziplogico.MovimentiZipLogicoService;
import it.gruppoinit.pal.gp.core.features.movimenti.ziplogico.segnaposto.ZipLogicoSegnapostoMailFactory;

public class SegnapostoMailFactory {

    private ISegnapostoService segnapostoService;
    private MovimentiZipLogicoService movimentiZipLogicoService;

    public SegnapostoMailFactory(MovimentiZipLogicoService movimentiZipLogicoService, ISegnapostoService segnapostoService) {

	this.movimentiZipLogicoService = movimentiZipLogicoService;
	this.segnapostoService = segnapostoService;
    }

    public Map<String, ISegnapostoMailResolver> getResolvers(Movimenti movimento) {

	ZipLogicoSegnapostoMailFactory factory = new ZipLogicoSegnapostoMailFactory(this.movimentiZipLogicoService, this.segnapostoService);
	Map<String, ISegnapostoMailResolver> resolvers = new HashMap<String, ISegnapostoMailResolver>();
	if (movimento != null) {
	    resolvers.putAll(factory.getResolvers(movimento.getId().getCodice()));
	}
	return resolvers;
    }
}
