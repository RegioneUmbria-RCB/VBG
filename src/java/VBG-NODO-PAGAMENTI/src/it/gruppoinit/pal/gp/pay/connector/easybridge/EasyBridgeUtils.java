package it.gruppoinit.pal.gp.pay.connector.easybridge;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;
import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import it.gruppoinit.pal.gp.core.utils.IOUtils;
import it.gruppoinit.pal.gp.pay.connector.easybridge.ws.WEBSEasyBridgeInterfaceSoap;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorWsEndpoint;

public class EasyBridgeUtils {

    public enum STATO_ESITO_PAGAMENTO {
	ACCETTATO, //‘ACCETTATO’ = La richiesta di pagamento è stata correttamente inoltrata a NODO SPC.
	ESEGUITO, //‘ESEGUITO’ = Pagamento completato con successo secondo indicazione della RT pervenuta da NODO SPC.
	NON_ESEGUITO//‘NON_ESEGUITO’ = Tentativo di pagamento fallito secondo indicazione della RT pervenuta da NODO SPC.
	/*
	 * 
	‘INOLTRATO’ = Debt RPT has been created and sent to Nodo_SPC by Pdp (we are waiting from Nodo_SPC returned issue).
	‘ACCETTATO’ = Payment request has been successfully forwarded to Nodo_SPC.
	‘AUTORIZZATO’= Immediate payment was authorized by PSP
	‘DIFFERITO’ = Payment issue will be available only after RT will be received
	‘ESEGUITO’ = Payment has been successfully completed (all debts have been payed, according to the received RT)
	‘ESEGUITO_PARZIALMENTE’ = Payment has been partly completed; not all single payments (“singoli versamenti”) inside have been payed, according to the received RT.
	‘NON_ESEGUITO’ = Payment attempt failed, according to the received RT.
	‘NON_ESEGUITO_DECORRENZA’ = Payment no more authorized (out of effective terms)
	‘STORNATO’ = Payment has been diverted (starting from PA request).
	‘REVOCATO’ = Payment has been revoked (starting from PSP request).
	 */
    }

    public STATO_ESITO_PAGAMENTO fromString(String stato) {

	return STATO_ESITO_PAGAMENTO.valueOf(stato);
    }

    public <T> String convertToBase64(T result) {

	try {
	    return Base64.encodeBase64String(IOUtils.marshallObject(result).getBytes("utf-8"));
	} catch (UnsupportedEncodingException e) {
	    throw new RuntimeException(e);
	}
    }

    public <T> T getObjectFromBase64(String result, Class<T> class1) throws UnsupportedEncodingException {

	String internal = new String(Base64.decodeBase64(result.getBytes("utf-8")));
	T obj = (T) IOUtils.unMarshallString(internal, class1);
	return obj;
    }

    public byte[] decodeString(String inputBase64) {

	return Base64.decodeBase64(inputBase64);
    }

    public WEBSEasyBridgeInterfaceSoap getCaricamentoWsPort(PayConnectorWsEndpoint connectorWsEndpoint) {

	JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
	factory.setServiceClass(WEBSEasyBridgeInterfaceSoap.class);
	//codice per compatibilità con vecchia modalita di configurazione degli endpoint dei servizi da eliminare dopo che test ok sul nuovo
	factory.setAddress(connectorWsEndpoint.getEndpointUrl());// <- must be /soap there, otherwise 404
	WEBSEasyBridgeInterfaceSoap info = (WEBSEasyBridgeInterfaceSoap) factory.create();
	Client client = ClientProxy.getClient(info);
	HTTPConduit conduit = (HTTPConduit) client.getConduit();
	conduit.setAuthorization(basicAuthorization(connectorWsEndpoint.getUtente(), connectorWsEndpoint.getPassword()));
	if (connectorWsEndpoint.getTimeout() != null) {
	    HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
	    httpClientPolicy.setConnectionTimeout(connectorWsEndpoint.getTimeout());
	    httpClientPolicy.setReceiveTimeout(connectorWsEndpoint.getTimeout());
	    conduit.setClient(httpClientPolicy);
	}
	return info;
    }

    private AuthorizationPolicy basicAuthorization(String utente, String password) {

	AuthorizationPolicy authPolicy = new AuthorizationPolicy();
	authPolicy.setUserName(utente);
	authPolicy.setPassword(password);
	authPolicy.setAuthorizationType("Basic");
	return authPolicy;
    }
}
