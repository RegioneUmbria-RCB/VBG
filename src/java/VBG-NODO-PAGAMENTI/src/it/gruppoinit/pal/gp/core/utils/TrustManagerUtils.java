package it.gruppoinit.pal.gp.core.utils;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

public class TrustManagerUtils {

    public static TrustManager[] getTrustManagers(KeyStore trustStore) throws NoSuchAlgorithmException, KeyStoreException {

	String alg = KeyManagerFactory.getDefaultAlgorithm();
	TrustManagerFactory fac = TrustManagerFactory.getInstance(alg);
	fac.init(trustStore);
	return fac.getTrustManagers();
    }

    public static KeyManager[] getKeyManagers(KeyStore keyStore, String keyPassword) throws GeneralSecurityException, IOException {

	String alg = KeyManagerFactory.getDefaultAlgorithm();
	char[] keyPass = keyPassword != null ? keyPassword.toCharArray() : null;
	KeyManagerFactory fac = KeyManagerFactory.getInstance(alg);
	fac.init(keyStore, keyPass);
	return fac.getKeyManagers();
    }
}
