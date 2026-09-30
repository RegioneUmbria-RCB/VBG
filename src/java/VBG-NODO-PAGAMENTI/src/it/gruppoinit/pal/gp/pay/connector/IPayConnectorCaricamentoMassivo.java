package it.gruppoinit.pal.gp.pay.connector;

import java.util.Map;

import it.gruppoinit.pal.gp.pay.service.helper.EsitoElaborazione;

public interface IPayConnectorCaricamentoMassivo {

    enum PARAMS_ELABORAZIONE {
	CODICE_PROFILO,
	CODICE_CONNETTORE
    }

    EsitoElaborazione elaboraCaricamentoMassivoPosizioni(Map<String, String> params);
}
