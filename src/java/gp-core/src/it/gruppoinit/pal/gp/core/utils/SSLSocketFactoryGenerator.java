package it.gruppoinit.pal.gp.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509KeyManager;

public class SSLSocketFactoryGenerator {

    private SSLSocketFactoryGenerator() {

	super();
    }

    public static SSLSocketFactory getSSLSocketFactory(String certAlias, String keyStoreLocation, String trustStoreLocation, String keyStorePassword,
	    String trustStorePassword, String keyStoreFormat) throws IOException, GeneralSecurityException, URISyntaxException {

	File keyStoreURL = new File(keyStoreLocation);
	File trustStoreURL = new File(trustStoreLocation);
	KeyManager[] keyManagers = getKeyManagers(keyStoreURL, keyStorePassword, keyStoreFormat);
	TrustManager[] trustManagers = getTrustManagers(trustStoreURL, trustStorePassword, "jks");
	//For each key manager, check if it is a X509KeyManager because we will override its functionality
	for (int i = 0; i < keyManagers.length; i++) {
	    if (keyManagers[i] instanceof X509KeyManager) {
		keyManagers[i] = new AliasSelectorKeyManager((X509KeyManager) keyManagers[i], certAlias);
	    }
	}
	SSLContext context = SSLContext.getInstance("SSL");
	context.init(keyManagers, trustManagers, null);
	SSLSocketFactory ssf = context.getSocketFactory();
	return ssf;
    }

    private static KeyManager[] getKeyManagers(File keyStoreLocation, String keyStorePassword, String keyStoreFormat)
	    throws IOException, GeneralSecurityException {

	//Init a key store with the given file.
	String alg = KeyManagerFactory.getDefaultAlgorithm();
	KeyManagerFactory kmFact = KeyManagerFactory.getInstance(alg);
	FileInputStream fis = new FileInputStream(keyStoreLocation);
	KeyStore ks = KeyStore.getInstance(keyStoreFormat);
	ks.load(fis, keyStorePassword.toCharArray());
	fis.close();
	//Init the key manager factory with the loaded key store
	kmFact.init(ks, keyStorePassword.toCharArray());
	KeyManager[] kms = kmFact.getKeyManagers();
	return kms;
    }

    private static TrustManager[] getTrustManagers(File trustStoreLocation, String trustStorePassword, String trustStoreFormat)
	    throws IOException, GeneralSecurityException {

	String alg = TrustManagerFactory.getDefaultAlgorithm();
	TrustManagerFactory tmFact = TrustManagerFactory.getInstance(alg);
	FileInputStream fis = new FileInputStream(trustStoreLocation);
	KeyStore ks = KeyStore.getInstance(trustStoreFormat);
	ks.load(fis, trustStorePassword.toCharArray());
	fis.close();
	tmFact.init(ks);
	TrustManager[] tms = tmFact.getTrustManagers();
	return tms;
    }
}