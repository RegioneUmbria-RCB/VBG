package it.gruppoinit.pal.gp.core.ws.client;

import it.gruppoinit.infocamera.schema.sigeproexport.LISTAISTANZE;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.constants.WebConstants.SecurityParams;
import it.gruppoinit.sigepro.ws.sigeproexport.stub.CEsportazione;
import it.gruppoinit.sigepro.ws.sigeproexport.stub.CWSSigeproExpLocator;
import it.gruppoinit.sigepro.ws.sigeproexport.stub.CWSSigeproExpSoap;
import it.gruppoinit.sigepro.ws.sigeproexport.stub.Parametro;

import java.io.StringWriter;
import java.net.URL;
import java.rmi.RemoteException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SigeproExportWsClient {

    private static final Logger log = LoggerFactory.getLogger(SigeproExportWsClient.class);

    public static byte[] export(String sToken, LISTAISTANZE listaistanze, int idEsportazione, String idComuneEsportazione, Parametro[] listaParametri) {

	//TODO CONTROLLARE CAMPI OBBLIGATORI
	//CWSSigeproExpSoap port = getWsSigeprExportPort();
	//byte[] result;
	//	String sXmlFile = marshallListaIstanze(listaistanze);
	//	// TODO check sXmlFile NOT NULL
	//	try {
	//	    result = port.export(sToken, sXmlFile, idEsportazione, idComuneEsportazione, listaParametri);
	//	} catch (RemoteException e) {
	//	    log.error("export {}", e.getMessage());
	//	    throw new RuntimeException("export {} " + e.getMessage());
	//	}
	//return result;
	// il metodo è stato esteso aggiungendo il parametro boolenano zipFile ,nel caso della vecchia chiamata per l'export veniva chiamato
	// il metodo  "export(sToken, sXmlFile, idEsportazione, idComuneEsportazione, listaParametri)", quindi di default il parametro zipFile sarà messo a
	//a false
	byte[] result = export(sToken, listaistanze, idEsportazione, idComuneEsportazione, listaParametri, false);
	return result;
    }

    public static byte[] export(String sToken, LISTAISTANZE listaistanze, int idEsportazione, String idComuneEsportazione,
	    Parametro[] listaParametri, boolean zipFile) {

	//TODO CONTROLLARE CAMPI OBBLIGATORI
	CWSSigeproExpSoap port = getWsSigeprExportPort();
	byte[] result;
	String sXmlFile = marshallListaIstanze(listaistanze);
	// TODO check sXmlFile NOT NULL
	try {
	    if (zipFile) {
		result = port.exportZip(sToken, sXmlFile, idEsportazione, idComuneEsportazione, listaParametri);
	    } else {
		result = port.export(sToken, sXmlFile, idEsportazione, idComuneEsportazione, listaParametri);
	    }
	} catch (RemoteException e) {
	    log.error("export {}", e.getMessage());
	    throw new RuntimeException("export {} " + e.getMessage());
	}
	return result;
    }

    public static byte[] exportMail(String sToken, LISTAISTANZE listaistanze, int idEsportazione, String idComuneEsportazione,
	    Parametro[] listaParametri, String emailDestinatario, boolean zipFile) {

	//TODO CONTROLLARE CAMPI OBBLIGATORI
	CWSSigeproExpSoap port = getWsSigeprExportPort();
	byte[] result;
	String sXmlFile = marshallListaIstanze(listaistanze);
	// TODO check sXmlFile NOT NULL
	try {
	    result = port.exportMail(sToken, sXmlFile, idEsportazione, idComuneEsportazione, listaParametri, emailDestinatario, zipFile);
	} catch (RemoteException e) {
	    log.error("exportMail {}", e.getMessage());
	    throw new RuntimeException("exportMail {} " + e.getMessage());
	}
	return result;
    }

    public CEsportazione[] getListExpContext(String sToken, ContestiAmmessi contesto) {

	//TODO CONTROLLARE CAMPI OBBLIGATORI
	CWSSigeproExpSoap port = getWsSigeprExportPort();
	CEsportazione[] result;
	try {
	    String contestoStr = contesto.name().equalsIgnoreCase("ISTANZE") ? "IST" : "ATT";
	    result = port.listExpContext(sToken, contestoStr);
	} catch (RemoteException e) {
	    log.error("export: {}", e.getMessage());
	    throw new RuntimeException("marshallListaIstanze - export: {} " + e.getMessage());
	}
	return result;
    }

    private static CWSSigeproExpSoap getWsSigeprExportPort() {

	CWSSigeproExpLocator locator = new CWSSigeproExpLocator();
	String urlWSSigeproExport = WebConstants.getSecurityParamValue(SecurityParams.WSHOSTURL_FIRMADIGITALE);
	CWSSigeproExpSoap port = null;
	try {
	    port = locator.getCWSSigeproExpSoap(new URL(WebConstants.getSecurityParamValue(SecurityParams.WSHOSTURL_EXPORT)));
	} catch (Exception e) {
	    log.error("getWsSigeprExportPort: Errore nell'inizializzazione [{}] {}", urlWSSigeproExport, e.getMessage());
	    throw new RuntimeException("getWsSigeprExportPort: Errore nell'inizializzazione [" + urlWSSigeproExport + "]: " + e.getMessage(), e);
	}
	return port;
    }

    private static String marshallListaIstanze(LISTAISTANZE listaistanze) {

	// Marshaller dell'oggetto LISTAISTANZE
	try {
	    String sXmlFile = "";
	    JAXBContext jc = JAXBContext.newInstance(LISTAISTANZE.class);
	    Marshaller m = jc.createMarshaller();
	    StringWriter stringWriter = new StringWriter();
	    m.marshal(listaistanze, stringWriter);
	    stringWriter.flush();
	    sXmlFile = stringWriter.toString();
	    if (log.isDebugEnabled()) {
		log.debug("marshallListaIstanze - sXmlFile : " + sXmlFile);
	    }
	    return sXmlFile;
	} catch (Exception e) {
	    log.error("marshallListaIstanze - ERRORE Marshaller sXmlFile per SIGEPROEXPORT:" + e.getMessage());
	    throw new RuntimeException("marshallListaIstanze - ERRORE Marshaller sXmlFile per SIGEPROEXPORT:" + e.getMessage());
	}
    }

    public static enum ContestiAmmessi {
	/**
	 * 
	 */
	ISTANZE,
	/**
	 * 
	 */
	ATTIVITA
    };
}
