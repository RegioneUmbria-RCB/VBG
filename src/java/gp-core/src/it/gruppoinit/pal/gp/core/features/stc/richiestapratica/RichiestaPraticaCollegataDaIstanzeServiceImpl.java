package it.gruppoinit.pal.gp.core.features.stc.richiestapratica;

import it.gruppoinit.pal.gp.core.service.StcService;
import it.init.sigepro.rte.RichiestaPraticaCollegataResponse;
import it.init.sigepro.rte.types.SportelloType;

public class RichiestaPraticaCollegataDaIstanzeServiceImpl implements IRichiestaPraticaCollegata {

    private StcService service;
    private SportelloType mittente;
    private SportelloType destinatario;
    private Integer codiceIstanza;
    private String idProcedimento;

    public RichiestaPraticaCollegataDaIstanzeServiceImpl(StcService service, SportelloType mittente, SportelloType destinatario,
	    Integer codiceIstanza, String idProcedimento) {

	this.service = service;
	this.codiceIstanza = codiceIstanza;
	this.mittente = mittente;
	this.destinatario = destinatario;
	this.idProcedimento = idProcedimento;
    }

    @Override
    public RichiestaPraticaCollegataResponse getPratica() {

	return this.service.richiestaPraticaCollegata(this.mittente, this.destinatario, this.codiceIstanza, idProcedimento);
    }
}
