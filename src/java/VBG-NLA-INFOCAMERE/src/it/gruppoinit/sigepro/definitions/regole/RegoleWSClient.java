package it.gruppoinit.sigepro.definitions.regole;

import it.gruppoinit.sigepro.schemas.messages.regole.ParametroRegolaRequest;
import it.gruppoinit.sigepro.schemas.messages.regole.ParametroRegolaResponse;
import it.gruppoinit.sigepro.schemas.messages.regole.RegolaRequest;
import it.gruppoinit.sigepro.schemas.messages.regole.RegolaResponse;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class RegoleWSClient {

    private static final Logger log = LoggerFactory.getLogger(RegoleWSClient.class);
    private String backendWsHostURL;

    public void setBackendWsHostURL(String backendWsHostURL) {

	this.backendWsHostURL = backendWsHostURL;
    }

    public String getParametroRegola(ParametroRegolaRequest request) throws Exception {

	try {
	    RegoleWsService ss = new RegoleWsService(new URL(backendWsHostURL));
	    Regole port = ss.getRegoleWs();
	    ParametroRegolaResponse response = port.getParametroRegola(request);
	    if (response.getErrore() != null) {
		throw new Exception(response.getErrore().getCodice() + " " + response.getErrore().getDescrizione());
	    }
	    return response.getParametro().getValore();
	} catch (Exception e) {
	    log.error("getParametroRegola", e);
	    throw e;
	}
    }

    //    public List<ComuniESoftwareType> getComuniESoftwarePerRegola(String token) throws Exception {
    //
    //	//	try {
    //	//	    RegoleWsService ss = new RegoleWsService(new URL(backendWsHostURL));
    //	//	    Regole port = ss.getRegoleWs();
    //	//	    ComuniESoftwarePerRegolaRequest request = new ComuniESoftwarePerRegolaRequest();
    //	//	    request.setToken(token);
    //	//	    request.setNomeRegola(Constants.REGOLA_SIEDER);
    //	//	    request.setNomeParametro(Constants.PARAMETRO_URL_WS_GESTIONALE);
    //	//	    ComuniESoftwarePerRegolaResponse comuniESoftwarePerRegola = port.getComuniESoftwarePerRegola(request);
    //	//	    return comuniESoftwarePerRegola.getConfigurazioni();
    //	//	} catch (Exception e) {
    //	//	    log.error("getRegole", e);
    //	//	    throw e;
    //	//	}
    //	return null;
    //    }
    //
    public RegolaResponse getRegole(RegolaRequest regolaRequest) throws Exception {

	try {
	    RegoleWsService ss = new RegoleWsService(new URL(backendWsHostURL));
	    Regole port = ss.getRegoleWs();
	    RegolaResponse resp = port.getRegola(regolaRequest);
	    return resp;
	} catch (Exception e) {
	    log.error("getRegole", e);
	    throw e;
	}
    }
}
