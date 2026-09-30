package it.gruppoinit.pal.gp.pay.connector.payer.web.client;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

import it.gruppoinit.pal.gp.pay.exception.PayException;

public class AutenticazioneHeaderGenerator {

    private String apiSecret;
    private String apiChiave;
    private String firma;
    private Date dataOraRichiesta;

    public AutenticazioneHeaderGenerator(String apiSecret, String apiChiave) {

	super();
	this.apiSecret = apiSecret;
	this.apiChiave = apiChiave;
	//this.apiChiave = "uTzE5c6cXBk806xUyUb0YN41YSYFmSvd";
    }

    public void generateAuthHeaders() throws Exception {

	String firmaConc = this.apiChiave + this.apiSecret;
	Date now = new Date();
	this.dataOraRichiesta = now;
	long epochSec = now.getTime() / 1000;
	firmaConc = firmaConc + String.valueOf(epochSec);
	try {
	    Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
	    SecretKeySpec secret_key = new SecretKeySpec(this.apiSecret.getBytes("UTF-8"), "HmacSHA256");
	    sha256_HMAC.init(secret_key);
	    this.firma = Hex.encodeHexString(sha256_HMAC.doFinal(firmaConc.getBytes("UTF-8")));
	    //this.firma = "29de21afabcdf555584a06f74e4f00ac5213d6b25ca329f5d9c87c311fff0420";
	} catch (Exception e) {
	    throw new Exception("Errore: {}", e);
	}
    }

    public static void main(String[] args) throws ParseException {

	String date = "2022-11-21T14:59:03+01:00";
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
	Date dataora = sdf.parse(date);
	System.out.println(dataora);	
	// validaChiamataFirma("8rKW5Hb7e8VDHDw9sOpoT4r5D5es2hm5", "pEL5bCz8ZHU3S1oxgSAODuL3p44I6urH", date, date);
    }

    public static void validaChiamataFirma(String chiave, String secret, String dataOraRichiesta, String firma)
	    throws PayException, ParseException, Exception {

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
	Date dataora = sdf.parse(dataOraRichiesta);
	long time = dataora.getTime();
	long epochSec = time / 1000;
	String firmaConc = chiave + secret + String.valueOf(epochSec);
	Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
	SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256");
	sha256_HMAC.init(secret_key);
	String firmaDaVerificare = Hex.encodeHexString(sha256_HMAC.doFinal(firmaConc.getBytes("UTF-8")));
	if (!firmaDaVerificare.equalsIgnoreCase(firma)) {
	    throw new PayException("Credenziali non valide");
	}
    }

    public String getApiChiave() {

	return apiChiave;
    }

    public void setApiChiave(String apiChiave) {

	this.apiChiave = apiChiave;
    }

    public String getFirma() {

	return firma;
    }

    public void setFirma(String firma) {

	this.firma = firma;
    }

    public Date getDataOraRichiesta() {

	return dataOraRichiesta;
    }

    public void setDataOraRichiesta(Date dataOraRichiesta) {

	this.dataOraRichiesta = dataOraRichiesta;
    }
}
