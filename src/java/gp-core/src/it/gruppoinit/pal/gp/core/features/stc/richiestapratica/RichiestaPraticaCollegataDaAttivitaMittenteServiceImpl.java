package it.gruppoinit.pal.gp.core.features.stc.richiestapratica;

import it.gruppoinit.pal.gp.core.service.StcService;
import it.init.sigepro.rte.RichiestaPraticaCollegataResponse;
import it.init.sigepro.rte.types.SportelloType;

public class RichiestaPraticaCollegataDaAttivitaMittenteServiceImpl implements IRichiestaPraticaCollegata {

    private StcService service;
    private SportelloType sportello;
    private Integer codiceMovimento;

    public RichiestaPraticaCollegataDaAttivitaMittenteServiceImpl(StcService service, SportelloType sportello, Integer codiceMovimento) {

	this.service = service;
	this.codiceMovimento = codiceMovimento;
	this.sportello = sportello;
    }

    @Override
    public RichiestaPraticaCollegataResponse getPratica() {

	return this.service.richiestaPraticaCollegataDaAttivitaMittente(this.sportello, this.codiceMovimento);
    }
}
