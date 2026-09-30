package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.service.TokenPartnerAppService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.io.InputStream;
import java.security.PrivateKey;
import java.security.Signature;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

@Service
public class TokenPartnerAppServiceImpl implements TokenPartnerAppService {

    @Override
    public String getUrl(String querystring) {

	String hash = Utilities.getHashText(querystring, Utilities.ALGORITHM_SHA256, false);
	byte[] sign = createSignature(hash);
	String output = Base64.encodeBase64URLSafeString(sign);
	output = querystring + "&tokenPartnerApp=" + output;
	return output;
    }

    private byte[] createSignature(String hash) {

	byte[] signature = null;
	try {
	    java.security.KeyStore keyStoreFile = java.security.KeyStore.getInstance("JKS");
	    InputStream is = TokenPartnerAppService.class.getClassLoader().getResourceAsStream("keystoreRT.jks");
	    keyStoreFile.load(is, "regionetoscana".toCharArray());
	    PrivateKey privateKey = (PrivateKey) keyStoreFile.getKey("accettatoreRTUnico", "accettatoreunico".toCharArray());
	    Signature dsa = Signature.getInstance("SHA1withRSA");
	    dsa.initSign(privateKey);
	    dsa.update(hash.getBytes());
	    signature = dsa.sign();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return signature;
    }
}
