package it.gruppoinit.pdd.ri.features.registroimprese.auth;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.SecureRandom;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;

import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;

public class ClientCertAuthenticator implements IAuthenticator {

    private Object port;
    private String certPath;
    private String certPassword;
    private final String trustStoreName = "cacerts";
    private final String trustStorePass = "changeit";

    public ClientCertAuthenticator(Object port, String certPath, String certPassword) {

	this.port = port;
	this.certPath = certPath;
	this.certPassword = certPassword;
    }

    @Override
    public void authenticate() {

	try {
	    Client proxy = ClientProxy.getClient(this.port);
	    HTTPConduit conduit = (HTTPConduit) proxy.getConduit();
	    TLSClientParameters params = new TLSClientParameters();
	    KeyStore clientStore = KeyStore.getInstance("JKS");
	    FileInputStream fis = new FileInputStream(this.certPath);
	    clientStore.load(fis, this.certPassword.toCharArray());
	    KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
	    kmf.init(clientStore, this.certPassword.toCharArray());
	    KeyManager[] kms = kmf.getKeyManagers();
	    //KeyStore trustStore = KeyStore.getInstance("JKS");
	    //trustStore.load(new FileInputStream(this.trustStoreName), trustStorePass.toCharArray());
	    //TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
	    //tmf.init();
	    //TrustManager[] tms = tmf.getTrustManagers();
	    SSLContext sslContext = null;
	    sslContext = SSLContext.getInstance("TLSv1.2");
	    sslContext.init(kms, null, new SecureRandom());
	    params.setSSLSocketFactory(sslContext.getSocketFactory());
	    conduit.setTlsClientParameters(params);
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }
}
