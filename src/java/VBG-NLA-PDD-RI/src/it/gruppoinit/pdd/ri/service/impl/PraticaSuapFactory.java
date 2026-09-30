package it.gruppoinit.pdd.ri.service.impl;

import it.gruppoinit.pdd.ri.service.PraticaXmlHelperService;
import it.gruppoinit.sigeprosecurity.ws.SigeproSecurityWebServiceClient;

public class PraticaSuapFactory {

    PraticaXmlHelperService getCreatePraticaSUAPService(VERSIONE_PRATICA_SUAP versione, SigeproSecurityWebServiceClient sigeproSecurityWebServiceClient) {

	if (versione.equals(VERSIONE_PRATICA_SUAP.V_1_0)) {
	    return new PraticaXmlV1HelperServiceImpl(sigeproSecurityWebServiceClient);
	} else if (versione.equals(VERSIONE_PRATICA_SUAP.V_2_0)) {
	    return new PraticaXmlV2HelperServiceImpl(sigeproSecurityWebServiceClient);
	}
	throw new RuntimeException("Versione pratica suap " + versione + " non implementata");
    }
}
