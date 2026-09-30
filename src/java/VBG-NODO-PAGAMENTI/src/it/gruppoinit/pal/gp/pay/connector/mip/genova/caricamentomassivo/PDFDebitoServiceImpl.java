package it.gruppoinit.pal.gp.pay.connector.mip.genova.caricamentomassivo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;
import javax.ws.rs.core.Response.StatusType;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.utils.IOUtils;
import it.gruppoinit.pal.gp.pay.connector.mip.genova.PdfFile;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.exception.PayConfigurationException;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.service.helper.PayConfigurationHelper;

@Service
public class PDFDebitoServiceImpl implements IPDFDebitoService {

    private static final Logger log = LoggerFactory.getLogger(PDFDebitoServiceImpl.class);
    private static final String FILE_NAME_TYPE_SEPARATOR = "_";
    private static final String PDF_SUBFOLDER = "pdf";
    private static final int CONNECTION_TIMEOUT = 20000;
    private static final int RECEIVE_TIMEOUT = 35000;

    @Override
    public PdfFile generaPdfDebito(String urlbase, String token, PayPosizioniDebitorie pos, String idDebito, String connectorId, String idLotto)
	    throws PayException {

	if (StringUtils.isBlank(urlbase)) {
	    throw new PayConfigurationException(
		    "Impossibile generare il pdf del debito perché il parametro DOCUMENTI_SERVICE non è configurato per il connettore NEXI");
	}
	Integer idPos = pos.getId().getCodice();
	WebClient client = WebClient.create(urlbase)
		.path("{cf_ente_creditore}", PayConfigurationHelper.getProfiloEnteCreditore().getCfCodiceProfilo())
		.path("{id_posizione_debitoria}", idPos);
	HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
	conduit.getClient().setConnectionTimeout(CONNECTION_TIMEOUT);
	conduit.getClient().setReceiveTimeout(RECEIVE_TIMEOUT);
	client.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_OCTET_STREAM);
	client.header("Authorization", token);
	Response response = null;
	try {
	    response = client.get();
	} catch (Exception e) {
	    String message = MessageFormat.format(
		    "Errore nella chiamata al servizio di download lettera di accompagnamento: {0} per la posizione {1}", e.getMessage(),
		    PkId.toStringId(pos.getId()));
	    log.error(message, e);
	    throw new PayException(message, e);
	}
	StatusType st = response.getStatusInfo();
	if (st.getFamily().equals(Family.SUCCESSFUL)) {
	    InputStream is = ((InputStream) response.getEntity());
	    return this.addPdf(idDebito, is, connectorId, idLotto);
	} else {
	    String message = MessageFormat.format(
		    "Errore HTTP {0} {1} nella chiamata al servizio di download lettera di accompagnamento per la posizione {2}", st.getStatusCode(),
		    st.getReasonPhrase(), PkId.toStringId(pos.getId()));
	    log.error("generaPdfDebito - {}", message);
	    throw new PayException(message, true, st.getStatusCode() + "");
	}
    }

    /**
     * Riceve in input lo stream da cui leggere i dati di un file pdf, li copia in un file che viene aggiunto alla
     * cartella dei PDF da zippare e restituisce il nome del file generato secondo lo schema
     * <idLotto>-<idDebito>-debito.pdf
     * 
     * @param idDebito
     * @param pdfBytes
     * @throws PayException
     */
    private PdfFile addPdf(String idDebito, InputStream pdfBytes, String connectorId, String idLotto) throws PayException {

	if (StringUtils.isBlank(idDebito)) {
	    throw new PayException("per la generazione del documento di debito è necessario specificare id del debito ");
	}
	StringBuilder sb = new StringBuilder(idDebito).append(FILE_NAME_TYPE_SEPARATOR).append("debito.pdf");
	String pdfName = sb.toString();
	File pdfDir = new File(getTempDir(connectorId, idLotto), PDF_SUBFOLDER);
	File pdf = new File(pdfDir, pdfName);
	try {
	    pdfDir.mkdirs();
	    if (!pdf.createNewFile()) {
		throw new PayException("Impossibile creare il file " + pdfName + " nella cartella " + pdfDir);
	    }
	    IOUtils.copyStreamToFile(pdfBytes, pdf);
	    log.debug("addPdf - determino il numero di pagine del file {}", pdf);
	    PDDocument doc = PDDocument.load(pdf);
	    int count = doc.getNumberOfPages();
	    log.debug("addPdf - il numero di pagine del file {} è {}", pdf, count);
	    this.closePdf(doc);
	    return new PdfFile(pdfName, count);
	} catch (Exception ioe) {
	    String msg = "errore nella copia dei dati del pdf nella cartella da zippare. File di destinazione: " + pdf.getAbsolutePath();
	    log.error("addPdf - " + msg, ioe);
	    throw new PayException(msg, ioe);
	} finally {
	    if (pdfBytes != null) {
		try {
		    pdfBytes.close();
		} catch (IOException e) {
		    log.error("addPdf - chiusura pdfBytes ", e);
		}
	    }
	}
    }

    private File getTempDir(String connectorId, String idLotto) {

	File tmp = IOUtils.getSystemTempDir();
	tmp = new File(tmp, connectorId);
	tmp = new File(tmp, idLotto); //Timestamp condiviso 
	if (!tmp.exists()) {
	    tmp.mkdirs();
	}
	return tmp;
    }

    private void closePdf(PDDocument doc) {

	try {
	    doc.close();
	} catch (Exception e) {
	    log.error("closePdf - errore nella chiusura del doc", e);
	}
    }
}
