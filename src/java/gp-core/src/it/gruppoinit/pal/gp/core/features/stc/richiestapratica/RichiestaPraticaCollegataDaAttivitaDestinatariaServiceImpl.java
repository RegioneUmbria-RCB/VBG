package it.gruppoinit.pal.gp.core.features.stc.richiestapratica;

import it.gruppoinit.pal.gp.core.service.StcService;
import it.init.sigepro.rte.RichiestaPraticaCollegataResponse;
import it.init.sigepro.rte.types.SportelloType;

public class RichiestaPraticaCollegataDaAttivitaDestinatariaServiceImpl implements IRichiestaPraticaCollegata {

    private StcService service;
    private SportelloType sportello;
    private Integer codiceMovimento;

    public RichiestaPraticaCollegataDaAttivitaDestinatariaServiceImpl(StcService service, SportelloType sportello, Integer codiceMovimento) {

	this.service = service;
	this.codiceMovimento = codiceMovimento;
	this.sportello = sportello;
    }

    @Override
    public RichiestaPraticaCollegataResponse getPratica() {

	return this.service.richiestaPraticaCollegataDaAttivitaDestinataria(this.sportello, this.codiceMovimento);
    }
}