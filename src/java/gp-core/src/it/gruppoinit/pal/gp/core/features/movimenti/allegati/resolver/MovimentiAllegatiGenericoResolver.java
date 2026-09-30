package it.gruppoinit.pal.gp.core.features.movimenti.allegati.resolver;

import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.Movimentiallegati;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.service.MovimentiallegatiService;

public class MovimentiAllegatiGenericoResolver implements IAllegatiResolver {

    private MovimentiService movimentiService;
    private MovimentiallegatiService movimentiallegatiService;
    private OggettiService oggettiService;

    public static MovimentiAllegatiResolverEnum resolverType() {

	return MovimentiAllegatiResolverEnum.GENERICO;
    }

    public MovimentiAllegatiGenericoResolver(MovimentiService movimentiService, MovimentiallegatiService movimentiallegatiService,
	    OggettiService oggettiService) {

	this.movimentiService = movimentiService;
	this.movimentiallegatiService = movimentiallegatiService;
	this.oggettiService = oggettiService;
    }

    @Override
    public Integer generaAllegato(MovimentiAllegatiResolverRequest request) throws MovimentiAllegatiResolverException {

	Oggetti template = this.oggettiService.findById(new PkId(request.getCodiceOggetto()));
	Movimentiallegati movimentiallegati = new Movimentiallegati();
	Movimenti movimento = this.movimentiService.findById(new PkId(request.getCodiceMovimento()));
	movimentiallegati.setMovimento(movimento);
	Oggetti oggetto = new Oggetti();
	oggetto.setOggetto(template.getOggetto());
	oggetto.setNomefile(template.getNomefile());
	this.oggettiService.insert(oggetto);
	movimentiallegati.setOggetto(oggetto);
	movimentiallegati.setDescrizione(request.getDescrizione());
	this.movimentiallegatiService.insert(movimentiallegati);
	return oggetto.getId().getCodice();
    }
}
