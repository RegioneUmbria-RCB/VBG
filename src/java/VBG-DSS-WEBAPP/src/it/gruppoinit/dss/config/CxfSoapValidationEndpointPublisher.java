package it.gruppoinit.dss.config;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.namespace.QName;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.springframework.beans.factory.InitializingBean;

import jakarta.xml.ws.soap.SOAPBinding;

/**
 * Pubblica l'endpoint SOAP di validazione documenti allo stesso path del legacy
 * (CXF su /wservice/*, endpoint /wservice/validation da git history web.xml).
 * Usa {@link it.gruppoinit.dss.ws.LegacyValidationServiceAdapter} per esporre
 * l'operazione <strong>validateDocument</strong> richiesta dai client legacy (es. SIGEPRO).
 * <p>
 * Il nome e namespace del servizio WSDL sono impostati al valore legacy
 * {@code {http://impl.ws.dss.markt.ec.europa.eu/}ValidationService}.
 */
public class CxfSoapValidationEndpointPublisher implements InitializingBean {

	private static final Logger LOG = Logger.getLogger(CxfSoapValidationEndpointPublisher.class.getName());

	/** Path relativo alla servlet /wservice/*: getPathInfo() = /validation → URL finale /wservice/validation. */
	public static final String SOAP_VALIDATION_PATH = "/validationService";

	/** Namespace e nome servizio WSDL legacy (DSS Web App / SIGEPRO). */
	private static final String LEGACY_WS_NAMESPACE = "http://impl.ws.dss.markt.ec.europa.eu/";
	private static final QName LEGACY_SERVICE_QNAME = new QName(LEGACY_WS_NAMESPACE, "ValidationService");
	private static final QName LEGACY_PORT_QNAME = new QName(LEGACY_WS_NAMESPACE, "ValidationServiceImplPort");

	private Bus bus;
	private Object validationEndpoint;

	public void setBus(Bus bus) {
		this.bus = bus;
	}

	/** Bean da esporre (es. {@link it.gruppoinit.dss.ws.LegacyValidationServiceAdapter}). */
	public void setValidationEndpoint(Object validationEndpoint) {
		this.validationEndpoint = validationEndpoint;
	}

	@Override
	public void afterPropertiesSet() {
		if (bus == null || validationEndpoint == null) {
			LOG.warning("[CXF] Endpoint validazione NON pubblicato: bus o validationEndpoint null.");
			return;
		}
		try {
			JaxWsServerFactoryBean factory = new JaxWsServerFactoryBean();
			factory.setBus(bus);
			factory.setServiceBean(validationEndpoint);
			factory.setAddress(SOAP_VALIDATION_PATH);
			factory.setServiceName(LEGACY_SERVICE_QNAME);
			factory.setEndpointName(LEGACY_PORT_QNAME);

			org.apache.cxf.endpoint.Server server = factory.create();

			// MTOM come in precedenza
			if (server != null && server.getEndpoint() != null && server.getEndpoint().getBinding() != null) {
				Object binding = server.getEndpoint().getBinding();
				if (binding instanceof SOAPBinding) {
					((SOAPBinding) binding).setMTOMEnabled(true);
				}
			}

			LOG.info("[CXF] Endpoint SOAP validazione pubblicato su /wservice" + SOAP_VALIDATION_PATH
					+ " (WSDL: .../wservice/validation?wsdl, service=" + LEGACY_SERVICE_QNAME + ")");
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "[CXF] Errore pubblicazione endpoint validazione: " + e.getMessage(), e);
		}
	}
}
