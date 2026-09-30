package it.gruppoinit.pal.gp.pay.connector.mip.genova;

import it.gruppoinit.pal.gp.pay.connector.IPayConnector;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorWsEndpoint;

public class BaseOperazioniMassiveService {

    private static final String CARICO_POSTALIZZATORE_FOLDER = "CaricoPostalizzatore";

    protected BaseFolderCaricamento getWsCaricamentoMassivo(IPayConnector connector) {

	PayConnectorWsEndpoint ret1 = connector.getWsCaricamentoMassivoConfig();
	if (ret1 == null) {
	    return null;
	}
	return new BaseFolderCaricamento(ret1.getEndpointUrl() + CARICO_POSTALIZZATORE_FOLDER, ret1.getUtente(), ret1.getPassword());
    }

    protected BaseFolderCaricamento getWsCaricamentoLetturaEsiti(IPayConnector connector) {

	PayConnectorWsEndpoint ret1 = connector.getWsCaricamentoMassivoConfig();
	if (ret1 == null) {
	    return null;
	}
	return new BaseFolderCaricamento(ret1.getEndpointUrl(), ret1.getUtente(), ret1.getPassword());
    }
}
