package it.lineacomune.ws;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReportWSClient {

    private static final Logger log = LoggerFactory.getLogger(ReportWSClient.class);
    private String urlWS;
    private ReportWS portWS;

    /**
     * Ritorna un codice transazione
     * 
     * @param String
     *            id_ente (codice istat dell'ente)
     * @param int id_canale (codice identificativo canale utilizzato)
     * @param int id_servizio (identificativo del servizio)
     * @param int id_versione
     * @param String
     *            host
     * @param String
     *            referer
     * @param String
     *            agent
     * @param String
     *            codice_fiscale (codice fiscale utente che ha fatto l'accesso)
     * @param String
     *            session_id
     * 
     * @return String <br>
     *         restituisce il codice transazione (id della riga tabella + session_id)da utilizzare nelle chiamatate
     *         successive
     * 
     */
    public String createAccesso(String idEnte, int idCanale, int idServizio, int idVersione, String url, String referer, String host, String agent,
	    String sessionId, String cf) throws Exception {

	ReportWS port = getPort();
	log.info(
		"createAccesso:idEnte={},idCanale={},idServizio={},idVersione={},url={},referer={},host={},agent={},sessionId={},cf={}",
		new Object[] { idEnte, idCanale, idServizio, idVersione, url, referer, host, agent, sessionId, cf });
	String codiceTransazione = port.createAccesso(idEnte, idCanale, idServizio, idVersione, url, referer, host, agent, sessionId, cf);
	log.info("createAccesso: return {}", codiceTransazione);
	return codiceTransazione;
    }

    /**
     * Ritorna un booleano se la scrittura è riuscita
     * 
     * @param String
     *            cod_transazione
     * @param String
     *            id_step (numero di step)
     * @param String
     *            step_tipo (codice della tipologia di transazione - inizio, intermedio ... fine)
     * @param String
     *            step_desc (descrizione dello step)
     * @param String
     *            step_time
     * 
     * @return boolean <br>
     *         Ritorna un booleano se la scrittura è riuscita
     */
    public boolean createTransazione(String cod_transazione, int id_step, String step_tipo, String step_desc, String step_time) throws Exception {

	ReportWS port = getPort();
	log.info("createTransazione:cod_transazione={},id_step={},step_tipo={},step_desc={},step_time={}", new Object[] { cod_transazione,
		id_step, step_tipo, step_desc, step_time });
	boolean success = port.createTransazione(cod_transazione, id_step, step_tipo, step_desc, step_time);
	log.info("createTransazione: return {}", success);
	return success;
    }

    private ReportWS getPort() throws Exception {

	log.debug("getPort: url={}", getUrlWS());
	if (portWS == null) {
	    URL wsdlURL = new URL(getUrlWS());
	    ReporImplPortService ss = new ReporImplPortService(wsdlURL);
	    portWS = ss.getReporImplPort();
	}
	return portWS;
    }

    public String getUrlWS() {

	return urlWS;
    }

    public void setUrlWS(String urlWS) {

	this.urlWS = urlWS;
    }
}
