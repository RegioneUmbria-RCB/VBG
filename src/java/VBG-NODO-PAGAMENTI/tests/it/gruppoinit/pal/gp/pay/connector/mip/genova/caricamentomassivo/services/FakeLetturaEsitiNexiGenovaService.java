package it.gruppoinit.pal.gp.pay.connector.mip.genova.caricamentomassivo.services;

import java.util.Map;

import it.gruppoinit.pal.gp.pay.connector.IPayConnector;
import it.gruppoinit.pal.gp.pay.connector.mip.genova.ILetturaEsitiNexiGenovaService;
import it.gruppoinit.pal.gp.pay.service.helper.EsitoElaborazione;

public class FakeLetturaEsitiNexiGenovaService implements ILetturaEsitiNexiGenovaService {

    private boolean esito;
    private String messaggio;

    public FakeLetturaEsitiNexiGenovaService(boolean esito, String messaggio) {

	this.esito = esito;
	this.messaggio = messaggio;
    }

    @Override
    public EsitoElaborazione leggiEsiti(IPayConnector connector, Map<String, String> params) {

	return new EsitoElaborazione(this.esito, this.messaggio);
    }
}
