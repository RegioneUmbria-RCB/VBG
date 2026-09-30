package it.gruppoinit.pal.gp.core.features.movimenti.allegati.resolver;

import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.Movimentiallegati;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.service.MovimentiallegatiService;

public class MovimentiAllegatiODTResolver implements IAllegatiResolver {

    private MovimentiService movimentiService;
    private MovimentiallegatiService movimentiallegatiService;
    private DocumentMergeService documentMergeService;

    public static MovimentiAllegatiResolverEnum resolverType() {

	return MovimentiAllegatiResolverEnum.ODT;
    }

    public MovimentiAllegatiODTResolver(MovimentiService movimentiService, MovimentiallegatiService movimentiallegatiService,
	    DocumentMergeService documentMergeService) {

	this.movimentiService = movimentiService;
	this.movimentiallegatiService = movimentiallegatiService;
	this.documentMergeService = documentMergeService;
    }

    @Override
    public Integer generaAllegato(MovimentiAllegatiResolverRequest request) throws MovimentiAllegatiResolverException {

	try {
	    Oggetti oggetto = this.documentMergeService.createAllegatoDaDocumentoTipo(request.getCodiceLettera(), request.getCodiceIstanza(),
		    request.getCodiceMovimento(), request.getDocumentMergeHelper());
	    Movimentiallegati movimentiallegati = new Movimentiallegati();
	    Movimenti movimento = this.movimentiService.findById(new PkId(request.getCodiceMovimento()));
	    movimentiallegati.setMovimento(movimento);
	    movimentiallegati.setOggetto(oggetto);
	    movimentiallegati.setDescrizione(request.getDescrizione());
	    this.movimentiallegatiService.insert(movimentiallegati);
	    // Chiamo la applet per la gestionedei file
	    return oggetto.getId().getCodice();
	} catch (Exception e) {
	    throw new MovimentiAllegatiResolverException(e);
	}
    }
}
