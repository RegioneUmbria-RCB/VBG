package it.gruppoinit.pal.gp.core.ws.client;

import it.cassaedileweb.serviziodurc.authentication.DURCAuthentication;
import it.cassaedileweb.serviziodurc.check.in.DURCCheckRequest;
import it.cassaedileweb.serviziodurc.check.out.DURCCheckResult;
import it.cassaedileweb.serviziodurc.insert.in.DURCInsertRequest;
import it.cassaedileweb.serviziodurc.insert.out.DURCInsertResult;
import it.cassaedileweb.www.OsservatorioCantieri.DurcServiceLocator;
import it.cassaedileweb.www.OsservatorioCantieri.DurcServiceSoap;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.net.URL;
import java.rmi.RemoteException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InfoDurcWSClient extends BaseWsClient {

    private static final Logger log = LoggerFactory.getLogger(InfoDurcWSClient.class);

    private static DurcServiceSoap getWsDurcPort(String wsUrl) {

	DurcServiceLocator locator = new DurcServiceLocator();
	DurcServiceSoap port = null;
	try {
	    port = locator.getDurcServiceSoap(new URL(wsUrl));
	} catch (Exception e) {
	    log.error("getWsDurcPort: Errore nell'inizializzazione [{}] {}", wsUrl, e.getMessage());
	    throw new RuntimeException("getWsSigeprExportPort: Errore nell'inizializzazione [" + wsUrl + "]: " + e.getMessage(), e);
	}
	return port;
    }

    public DURCCheckResult checkDurcExistence(String wsUrl, DURCAuthentication authentication, DURCCheckRequest durcCheckRequest)
	    throws FunzioneBusinessRemotaException {

	DurcServiceSoap port = getWsDurcPort(wsUrl);
	String authString = Utilities.marshallObject(authentication);
	log.debug("xml auth: {}", authString);
	String requestString = Utilities.marshallObject(durcCheckRequest);
	log.debug("xml dati: {}", requestString);
	try {
	    String result = port.checkDURCExistence(authString, requestString);
	    log.debug("xml response dati: {}", result);
	    DURCCheckResult response = (DURCCheckResult) Utilities.unMarshallString(result, DURCCheckResult.class, "UTF-16");
	    return response;
	} catch (RemoteException e) {
	    throw new FunzioneBusinessRemotaException(e);
	}
    }

    public DURCInsertResult sendDurcRequest(String wsUrl, DURCAuthentication authentication, DURCInsertRequest durcInsertRequest)
	    throws FunzioneBusinessRemotaException {

	DurcServiceSoap port = getWsDurcPort(wsUrl);
	String authString = Utilities.marshallObject(authentication);
	log.debug("xml auth: {}", authString);
	String requestString = Utilities.marshallObject(durcInsertRequest);
	log.debug("xml dati: {}", requestString);
	try {
	    String result = port.sendDurcRequest(authString, requestString);
	    log.debug("xml response dati: {}", result);
	    DURCInsertResult response = (DURCInsertResult) Utilities.unMarshallString(result, DURCInsertResult.class, "UTF-16");
	    return response;
	} catch (RemoteException e) {
	    throw new FunzioneBusinessRemotaException(e);
	}
    }
}
