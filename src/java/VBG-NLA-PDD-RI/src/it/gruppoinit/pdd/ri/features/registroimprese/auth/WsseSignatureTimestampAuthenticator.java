package it.gruppoinit.pdd.ri.features.registroimprese.auth;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.ws.security.handler.WSHandlerConstants;
import org.apache.wss4j.common.ConfigurationConstants;

import it.gruppoinit.pdd.ri.features.configurazione.Certificato;
import it.gruppoinit.sigeprosecurity.ws.SecurityPwdCallBackHandler;

public class WsseSignatureTimestampAuthenticator implements IAuthenticator {

    private Object port;
    private Certificato certificato;

    public WsseSignatureTimestampAuthenticator(Object port, Certificato certificato) {

	this.port = port;
	this.certificato = certificato;
    }

    @Override
    public void authenticate() {

	Map<String, Object> signatureProperties = new HashMap<>();
	signatureProperties.put(ConfigurationConstants.ACTION, ConfigurationConstants.TIMESTAMP + " " + ConfigurationConstants.SIGNATURE);
	signatureProperties.put(ConfigurationConstants.USER, certificato.getAlias());
	SecurityPwdCallBackHandler pwdCallback = new SecurityPwdCallBackHandler(certificato.getAlias(), certificato.getPassword());
	signatureProperties.put(ConfigurationConstants.SIG_ALGO, "http://www.w3.org/2001/04/xmldsig-more#rsa-sha256");
	signatureProperties.put(ConfigurationConstants.SIG_C14N_ALGO, "http://www.w3.org/2001/10/xml-exc-c14n#");
	signatureProperties.put(ConfigurationConstants.SIG_DIGEST_ALGO, "http://www.w3.org/2001/04/xmlenc#sha256");
	signatureProperties.put(ConfigurationConstants.PW_CALLBACK_REF, pwdCallback);
	signatureProperties.put(ConfigurationConstants.SIG_KEY_ID, "DirectReference");
	signatureProperties.put(ConfigurationConstants.EXPAND_XOP_INCLUDE, "true");
	signatureProperties.put(
		WSHandlerConstants.SIGNATURE_PARTS, "{Element}{http://schemas.xmlsoap.org/soap/envelope/}Body;" +
							"{Element}{http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd}Timestamp");
	//	
	Properties properties = new Properties();
	properties.put("org.apache.ws.security.crypto.provider", "org.apache.ws.security.components.crypto.Merlin");
	properties.put("org.apache.ws.security.crypto.merlin.keystore.alias", certificato.getAlias());
	properties.put("org.apache.ws.security.crypto.merlin.keystore.type", "jks");
	properties.put("org.apache.ws.security.crypto.merlin.keystore.password", certificato.getPassword());
	properties.put("org.apache.ws.security.crypto.merlin.keystore.file", certificato.getPercorsoCertificato());
	//	
	signatureProperties.put("signatureProperties", properties);
	signatureProperties.put(ConfigurationConstants.SIG_PROP_REF_ID, "signatureProperties");
	Client proxy = ClientProxy.getClient(this.port);
	Endpoint cxfEndpoint = proxy.getEndpoint();
	WSS4JOutInterceptor wssOut = new WSS4JOutInterceptor(signatureProperties);
	cxfEndpoint.getOutInterceptors().add(wssOut);
    }
}
