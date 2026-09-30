package it.gruppoinit.pal.gp.core.features.movimenti.allegati.resolver;

import it.gruppoinit.pal.gp.core.domain.Movimentiallegati;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;

public interface MovimentiAllegatiResolverFactoryService {

    Movimentiallegati build(DocumentMergeHelper documentMergeHelper, Integer codiceMovimento, Integer codiceLettera)
	    throws MovimentiAllegatiResolverException;
}
