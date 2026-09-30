package it.gruppoinit.pal.gp.core.service.helper;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class IrideMessageUtil {

    public static final String opFirma = "firma";
    public static final String opProtocollazione = "protocollazione";
    public static final String opSpedizioneMail = "spedizioneMail";
    public static final String opAcquisizioneMail = "acquisizioneMail";
    private final Map<String, Map<String, String>> messages = new HashMap<String, Map<String, String>>();

    public IrideMessageUtil() {

	Map<String, String> firmaEsiti = new HashMap<String, String>();
	firmaEsiti.put("0", "firma apposta senza commenti");
	firmaEsiti.put("1", "firma apposta con commenti");
	firmaEsiti.put("2", "firma apposta su documento revisionato");
	firmaEsiti.put("3", "firma non apposta");
	messages.put(opFirma, firmaEsiti);
	Map<String, String> protEsiti = new HashMap<String, String>();
	protEsiti.put("0", "doc. protocollato");
	protEsiti.put("1", "doc. non protocollato");
	messages.put(opProtocollazione, protEsiti);
	Map<String, String> spedMailEsiti = new HashMap<String, String>();
	spedMailEsiti.put("0", "messaggio inviato correttamente");
	spedMailEsiti.put("1", "messaggio non inviato");
	messages.put(opSpedizioneMail, spedMailEsiti);
	Map<String, String> acqMailEsiti = new HashMap<String, String>();
	acqMailEsiti.put("0", "messaggio acquisito via mail");
	messages.put(opAcquisizioneMail, acqMailEsiti);
    }

    public String getMessaggio(String operazione, String esito, String messaggio) {

	String _messaggio = "Operazione: " + operazione + ", Esito: " + esito + ", Messaggio: " + messaggio;
	if (this.messages.containsKey(operazione)) {
	    if (this.messages.get(operazione).containsKey(esito)) {
		_messaggio = this.messages.get(operazione).get(esito);
		_messaggio += StringUtils.isBlank(messaggio) ? "" : " (" + messaggio + ")";
	    }
	}
	return _messaggio;
    }

    public static void main(String[] args) {

	IrideMessageUtil util = new IrideMessageUtil();
	System.out.println(util.getMessaggio("protocollazione", "0", "2011/1234567"));
    }
}
