package it.gruppoinit.pal.gp.pay.connector.mip.ws.client;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorConfigParams.ConfigParamNames;
import it.gruppoinit.pal.gp.pay.exception.PayConfigurationException;
import it.gruppoinit.pal.gp.pay.service.PayConnectorConfigValuesService;

public class ServiziJwtGenerator {

    private static final Logger log = LoggerFactory.getLogger(ServiziJwtGenerator.class);

    public String generateJwtForAppCaller(PayConnectorConfigValuesService payConnectorConfigValuesService) throws PayConfigurationException {

	String publicKeyStorelocationPEM = payConnectorConfigValuesService
		.getValoreParametroConfigurazione(ConfigParamNames.SSL_TRUST_STORE_LOCATION);
	if (StringUtils.isNotBlank(publicKeyStorelocationPEM)) {
	    String subject = payConnectorConfigValuesService.getValoreParametroConfigurazione(ConfigParamNames.MIPGE_GENOVA_SUBJECT);
	    String appCode = payConnectorConfigValuesService.getValoreParametroConfigurazione(ConfigParamNames.MIPGE_GENOVA_APP_CODE);
	    String iis = payConnectorConfigValuesService.getValoreParametroConfigurazione(ConfigParamNames.MIPGE_GENOVA_IIS);
	    String aud = payConnectorConfigValuesService.getValoreParametroConfigurazione(ConfigParamNames.MIPGE_GENOVA_AUD);
	    String clientId = payConnectorConfigValuesService.getValoreParametroConfigurazione(ConfigParamNames.MIPGE_GENOVA_CLIENT_ID);
	    String privateKeyStoreLocation = payConnectorConfigValuesService
		    .getValoreParametroConfigurazione(ConfigParamNames.SSL_KEY_STORE_LOCATION);
	    log.debug("**** Starting JWT Generation ****");
	    Map<String, Object> headerMap = new HashMap<>();
	    headerMap.put(Header.TYPE, Header.JWT_TYPE);
	    headerMap.put(JwsHeader.ALGORITHM, SignatureAlgorithm.RS256);
	    try {
		byte[] pbKeyBytes = getPublicCertBytes(publicKeyStorelocationPEM);
		if (log.isDebugEnabled()) {
		    log.debug("publicKey.getEncoded() {}", new String(pbKeyBytes));
		}
		headerMap.put(JwsHeader.X509_CERT_CHAIN, pbKeyBytes);
	    } catch (IOException e1) {
		throw new PayConfigurationException(e1);
	    }
	    Calendar myCalendar = Calendar.getInstance();
	    Date dateNow = myCalendar.getTime();
	    Date dateNotBefore = myCalendar.getTime();
	    myCalendar.add(Calendar.HOUR, 4);
	    Date dateExp = myCalendar.getTime();
	    log.debug("Printing dates");
	    log.debug("iat: {}", dateNow);
	    log.debug("nbf: {}", dateNotBefore);
	    log.debug("exp: {}", dateExp);
	    try {
		byte[] encodedPv = Base64.decodeBase64(getPublicCertBytes(privateKeyStoreLocation));
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encodedPv);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = kf.generatePrivate(keySpec);
		String compactJws = Jwts.builder() //
			.setHeader(headerMap) //
			.setAudience(aud) //
			.setSubject(subject) // Inserisce dinamicamente la matricola dell'utente/ID applicazione scatenante la chiamata 
			.setExpiration(dateExp) //
			.setIssuedAt(dateNow) //
			.setNotBefore(dateNotBefore) //
			.setId(UUID.randomUUID().toString()) //
			.setIssuer(iis) //
			.claim("app_code", appCode) // Inserisce dinamicamente l'app code
			.claim("client_id", clientId) //
			.signWith(SignatureAlgorithm.RS256, privateKey).compact();
		log.debug("Printing generated Jwt in string form:");
		log.debug(compactJws);
		log.debug("**** JWT Generation Ended ****");
		return compactJws;
	    } catch (Exception e) {
		log.error("errore: " + e.getMessage(), e);
		throw new PayConfigurationException(e);
	    }
	}
	return null;
    }

    private byte[] getPublicCertBytes(String percorsoFile) throws IOException {

	return Files.readAllBytes(Paths.get(percorsoFile));
    }
}
