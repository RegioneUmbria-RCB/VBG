package it.gruppoinit.pal.gp.core.features.movimenti.allegati.resolver;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeService;

public class MovimentiAllegatiRTFLegacyResolver implements IAllegatiResolver {

    private static final Logger logger = LoggerFactory.getLogger(MovimentiAllegatiRTFLegacyResolver.class);
    private DocumentMergeService documentMergeService;

    public static MovimentiAllegatiResolverEnum resolverType() {

	return MovimentiAllegatiResolverEnum.LEGACY;
    }

    public MovimentiAllegatiRTFLegacyResolver(DocumentMergeService documentMergeService) {

	this.documentMergeService = documentMergeService;
    }

    @Override
    public Integer generaAllegato(MovimentiAllegatiResolverRequest request) throws MovimentiAllegatiResolverException {

	try {
	    // Creo l'URL della chiamata alla pagina ASP
	    String urlcreaallegato = this.documentMergeService.getUrlGeneraAllegato() +
		    "?codiceDocumento=" +
		    request.getCodiceLettera() +
		    "&codiceIstanza=" +
		    request.getCodiceIstanza() +
		    "&codiceMovimento=" +
		    request.getCodiceMovimento() +
		    "&TipoMovimento=" +
		    request.getTipoMovimento() +
		    "&" +
		    WebConstants.SOFTWARE +
		    "=" +
		    ORMHelper.getSoftware() +
		    "&" +
		    WebConstants.TOKEN +
		    "=" +
		    ORMHelper.getToken();
	    logger.debug("generaAllegato {}", urlcreaallegato);
	    // Recupero tramite HTTP cliet il contenuto della pagina e lo metto su uno stream	
	    HttpClient cli = new HttpClient();
	    HttpMethod method = null;
	    int status = 0;
	    try {
		method = new GetMethod(urlcreaallegato);
		status = cli.executeMethod(method);
	    } catch (Exception e) {
		logger.error("generaAllegato errore: ", e);
		String message = "<b>Si e' verificato un errore nella stampa del documento contattare l'assistenza</b>.<p /> " +
				 "<i style=\"color: red\">[Funzionalita': MOVIMENTI.ajaxCreateLettereTipo,Dettaglio errore: " + e + "]</i> ";
		throw new MovimentiAllegatiResolverException(message);
	    }
	    InputStream inputStream = method.getResponseBodyAsStream();
	    // Trasformo lo stream in una stringa (conterrà solo il codice)
	    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
	    StringBuilder sb = new StringBuilder();
	    String line = null;
	    while ((line = reader.readLine()) != null) {
		sb.append(line);
	    }
	    inputStream.close();
	    String co = sb.toString();
	    logger.debug("generaAllegato sb.toString() {}", co);
	    if (status == 500) {
		throw new MovimentiAllegatiResolverException(sb.toString());
	    }
	    // Chiamo la applet per la gestionedei file
	    Integer codiceOggetto = Integer.parseInt(co);
	    logger.debug("generaAllegato Integer codiceOggetto  {} chiamo verificaConvertiRtfInOdt ", codiceOggetto);
	    documentMergeService.verificaConvertiRtfInOdt(codiceOggetto, true);
	    logger.debug("generaAllegato Integer codiceOggetto  {} chiamato verificaConvertiRtfInOdt ", codiceOggetto);
	    return codiceOggetto;
	} catch (Exception e) {
	    logger.error("Errore nella creazione dell'allegato", e);
	    throw new MovimentiAllegatiResolverException(e);
	}
    }
}
