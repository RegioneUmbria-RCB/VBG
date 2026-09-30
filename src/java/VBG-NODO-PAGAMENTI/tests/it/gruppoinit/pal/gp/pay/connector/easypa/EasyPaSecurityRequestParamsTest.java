package it.gruppoinit.pal.gp.pay.connector.easypa;

import org.junit.Assert;
import org.junit.Test;

import it.gruppoinit.pal.gp.pay.connector.easypa.ws.rest.security.EasyPaSecurityRequestParams;

public class EasyPaSecurityRequestParamsTest {

    @Test()
    public void validateParamsEsegueLeVerificheCorretteDeiParametriDelCostruttore() {

	Assert.assertFalse("Tutti i parametri sono nulli", new EasyPaSecurityRequestParams(null, null, null, null, null).validateParams());
	Assert.assertFalse("Primo parametro valido", new EasyPaSecurityRequestParams("1", null, null, null, null).validateParams());
	Assert.assertFalse("Secondo parametro valido", new EasyPaSecurityRequestParams("1", "1", null, null, null).validateParams());
	Assert.assertFalse("TErzo parametro valido", new EasyPaSecurityRequestParams("1", "1", "1", null, null).validateParams());
	Assert.assertFalse("Quarto parametro valido", new EasyPaSecurityRequestParams("1", "1", "1", "1", null).validateParams());
	Assert.assertTrue("Tutti i parametri validi torna true", new EasyPaSecurityRequestParams("1", "1", "1", "1", "1").validateParams());
    }

    private static final String CONFRONTA = "grant_type=p&codiceIstituto=02&codiceEnte=000&idEnte=C8&idDominio=02";

    @Test()
    public void buidlQueryStringTornaValoreCorretto() {

	Assert.assertTrue("Le stringhe non sono uguali",
		CONFRONTA.equals(new EasyPaSecurityRequestParams("p", "02", "000", "C8", "02").buildQueryString()));
    }
}
