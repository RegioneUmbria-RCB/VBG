package it.gruppoinit.pal.gp.core.features.stc.richiestapratica;

import it.init.sigepro.rte.RichiestaPraticaCollegataResponse;

public class RichiestaPraticaCollegataFactory {

    private IRichiestaPraticaCollegata service;

    public static RichiestaPraticaCollegataFactory fromMovimentiParams(RichiestaPraticaFromMovimentiParams params) {

	if (params == null || params.getCodiceMovimento() == null) {
	    throw new IllegalArgumentException("Impossibile istanziare la factory senza passare i riferimenti del movimento");
	}
	IRichiestaPraticaCollegata richiestaService;
	if (params.isMittente()) {
	    richiestaService = new RichiestaPraticaCollegataDaAttivitaMittenteServiceImpl(params.getStcService(), params.getSportello(),
		    params.getCodiceMovimento());
	} else {
	    richiestaService = new RichiestaPraticaCollegataDaAttivitaDestinatariaServiceImpl(params.getStcService(), params.getSportello(),
		    params.getCodiceMovimento());
	}
	return new RichiestaPraticaCollegataFactory(richiestaService);
    }

    public static RichiestaPraticaCollegataFactory fromIstanzeParams(RichiestaPraticaFromIstanzeParams params) {

	if (params == null || params.getCodiceIstanza() == null) {
	    throw new IllegalArgumentException("Impossibile istanziare la factory senza passare i riferimenti del codice istanza");
	}
	IRichiestaPraticaCollegata richiestaService = new RichiestaPraticaCollegataDaIstanzeServiceImpl(params.getStcService(), params.getMittente(),
		params.getDestinatario(), params.getCodiceIstanza(), null);
	return new RichiestaPraticaCollegataFactory(richiestaService);
    }

    public static RichiestaPraticaCollegataFactory fromNotificaPraticaStoricaParams(RichiestaPraticaFromNotificaPraticaStoricaParams params) {

	if (params == null || params.getCodiceIstanza() == null) {
	    throw new IllegalArgumentException("Impossibile istanziare la factory senza passare i riferimenti del codice istanza");
	}
	IRichiestaPraticaCollegata richiestaService = new RichiestaPraticaCollegataDaIstanzeServiceImpl(params.getStcService(), params.getMittente(),
		params.getDestinatario(), params.getCodiceIstanza(), params.getIdProcedimento());
	return new RichiestaPraticaCollegataFactory(richiestaService);
    }

    private RichiestaPraticaCollegataFactory(IRichiestaPraticaCollegata service) {

	this.service = service;
    }

    public RichiestaPraticaCollegataResponse getPraticaCollegata() {

	return this.service.getPratica();
    }
}
