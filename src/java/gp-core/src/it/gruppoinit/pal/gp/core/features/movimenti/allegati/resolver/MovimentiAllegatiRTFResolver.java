package it.gruppoinit.pal.gp.core.features.movimenti.allegati.resolver;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.Movimentiallegati;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.service.MovimentiallegatiService;

public class MovimentiAllegatiRTFResolver implements IAllegatiResolver {

    private static final Logger logger = LoggerFactory.getLogger(MovimentiAllegatiRTFResolver.class);
    private MovimentiService movimentiService;
    private MovimentiallegatiService movimentiallegatiService;
    private DocumentMergeService documentMergeService;

    public static MovimentiAllegatiResolverEnum resolverType() {

	return MovimentiAllegatiResolverEnum.RTF;
    }

    public MovimentiAllegatiRTFResolver(MovimentiService movimentiService, MovimentiallegatiService movimentiallegatiService,
	    DocumentMergeService documentMergeService) {

	this.movimentiService = movimentiService;
	this.movimentiallegatiService = movimentiallegatiService;
	this.documentMergeService = documentMergeService;
    }

    @Override
    public Integer generaAllegato(MovimentiAllegatiResolverRequest request) throws MovimentiAllegatiResolverException {

	try {
	    if (logger.isDebugEnabled()) {
		logger.debug("Prima della creazione dell'allegato {}", ReflectionToStringBuilder.toString(request, ToStringStyle.SIMPLE_STYLE));
	    }
	    Oggetti oggetto = this.documentMergeService.createAllegatoDaDocumentoTipo(request.getCodiceLettera(), request.getCodiceIstanza(),
		    request.getCodiceMovimento(), request.getDocumentMergeHelper());
	    if (logger.isDebugEnabled()) {
		logger.debug("Dopo la creazione dell'allegato {}: oggetto {}",
			ReflectionToStringBuilder.toString(request, ToStringStyle.SIMPLE_STYLE), oggetto.getId());
	    }
	    Movimentiallegati movimentiallegati = new Movimentiallegati();
	    Movimenti movimento = this.movimentiService.findById(new PkId(request.getCodiceMovimento()));
	    movimentiallegati.setMovimento(movimento);
	    movimentiallegati.setOggetto(oggetto);
	    movimentiallegati.setDescrizione(request.getDescrizione());
	    this.movimentiallegatiService.insert(movimentiallegati);
	    logger.debug("MovimentiAllegati Salvato");
	    return oggetto.getId().getCodice();
	} catch (Exception e) {
	    logger.error("Errore nella creazione dell'allegato", e);
	    throw new MovimentiAllegatiResolverException(e);
	}
    }
}
