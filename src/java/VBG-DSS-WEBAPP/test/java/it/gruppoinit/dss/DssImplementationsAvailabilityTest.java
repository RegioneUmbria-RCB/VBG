package it.gruppoinit.dss;

import org.junit.Test;

import eu.europa.esig.dss.cms.CMSUtils;
import eu.europa.esig.dss.pdf.ServiceLoaderPdfObjFactory;
import eu.europa.esig.dss.utils.Utils;

/**
 * Verifica preventiva che tutte le implementazioni opzionali DSS richieste a runtime
 * siano presenti in classpath. Se manca un modulo (IUtils, IPdfObjFactory, ICMSUtils),
 * il caricamento della classe lancia ExceptionInInitializerError con "No implementation found".
 * Eseguire con: mvn test
 */
public class DssImplementationsAvailabilityTest {

	@Test
	public void IUtils_implementation_available() {
		// Carica la classe Utils: se manca dss-utils-apache-commons (o dss-utils-google-guava)
		// viene lanciato ExceptionInInitializerError "No implementation found for IUtils"
		Utils.toBase64(new byte[0]);
	}

	@Test
	public void ICMSUtils_implementation_available() {
		// Carica CMSUtils: se manca dss-cms-object (o dss-cms-stream) viene lanciato
		// ExceptionInInitializerError "No implementation found for ICMSUtils"
		CMSUtils.class.getName();
	}

	@Test
	public void IPdfObjFactory_implementation_available() {
		// Istanzia la factory PAdES: se manca dss-pades-pdfbox (o dss-pades-openpdf)
		// viene lanciato ExceptionInInitializerError "No implementation found for IPdfObjFactory"
		new ServiceLoaderPdfObjFactory();
	}
}
