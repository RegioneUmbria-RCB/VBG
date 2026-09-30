package it.gruppoinit.service;

import it.gov.impresainungiorno.schema.suap.ente.CooperazioneSUAPEnte;
import it.gov.impresainungiorno.schema.suap.pratica.RiepilogoPraticaSUAP;
import it.gruppoinit.constants.AttivitaDaEseguireEnum;
import it.gruppoinit.domain.helper.ParametriHelper;
import it.gruppoinit.domain.nla.Allegato;
import it.init.sigepro.rte.types.DettaglioAttivitaType;
import it.init.sigepro.rte.types.DettaglioPraticaType;

import java.io.File;
import java.util.List;

public interface DettaglioPraticaAndAttivitaTypeHelperService {

    public DettaglioPraticaType getDettaglioPraticaType(CooperazioneSUAPEnte cooperazioneSUAPEnte, RiepilogoPraticaSUAP r,
	    List<Allegato> listAllegati, ParametriHelper parametriHelper, File rpsuap_file);

    public DettaglioAttivitaType getDettaglioAttivitaType(CooperazioneSUAPEnte cooperazioneSUAPEnte, File rpsuap_file, List<Allegato> listAllegati,
	    ParametriHelper parametriHelper);

    public AttivitaDaEseguireResult selectAttivitaDaSvolgere(CooperazioneSUAPEnte cooperazioneSUAPEnte, ParametriHelper parametriHelper)
	    throws Exception;
}
