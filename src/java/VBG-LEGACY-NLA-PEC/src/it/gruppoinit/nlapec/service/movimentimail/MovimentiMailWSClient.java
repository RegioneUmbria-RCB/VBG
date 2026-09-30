package it.gruppoinit.nlapec.service.movimentimail;

import it.gruppoinit.nlapec.util.AllegatiUtil;
import it.gruppoinit.nlapec.util.PECMessage;
import it.gruppoinit.sigepro.schemas.messages.movimentimail.AllegatoType;
import it.gruppoinit.sigepro.schemas.messages.movimentimail.MovimentiMailRequest2;
import it.gruppoinit.sigepro.schemas.messages.movimentimail.MovimentiMailResponse2;

import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;

import javax.activation.DataHandler;
import javax.activation.MimetypesFileTypeMap;
import javax.mail.Address;
import javax.mail.internet.InternetAddress;
import javax.xml.transform.dom.DOMResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapFaultDetail;
import org.springframework.ws.soap.SoapFaultDetailElement;
import org.springframework.ws.soap.client.SoapFaultClientException;

public class MovimentiMailWSClient {

    private static final Logger log = LoggerFactory.getLogger(MovimentiMailWSClient.class);
    private WebServiceTemplate webServiceTemplate;

    //    public MovimentiMailResponse movimentiMail(String url, String token, String software, PECMessage pecMessage, String tmpPath,
    //	    ArrayList<String> listaFileAttachment, String codiceMovimento, ArrayList<String> idOggettiList) throws Exception {
    //
    //	log.debug("       --> movimentiMail: token={}, software={}, action={}, url={}", new Object[] { token, software, "", url });
    //	MovimentiMailRequest movimentiMailRequest = new MovimentiMailRequest();
    //	movimentiMailRequest.setCorpo(pecMessage.getBody());
    //	String destinatario = "";
    //	for (Address addres : pecMessage.getTo()) {
    //	    InternetAddress ia = (InternetAddress) addres;
    //	    destinatario = destinatario + ia.getAddress() + ";";
    //	}
    //	movimentiMailRequest.setDestinatario(destinatario);
    //	String messageId = pecMessage.getRifMsgId();
    //	if (messageId == null || messageId.equalsIgnoreCase("")) {
    //	    messageId = pecMessage.getId();
    //	}
    //	if (messageId != null) {
    //	    if (messageId.startsWith("<")) {
    //		messageId = messageId.substring(1);
    //	    }
    //	    if (messageId.endsWith(">")) {
    //		messageId = messageId.substring(0, messageId.length() - 1);
    //	    }
    //	}
    //	movimentiMailRequest.setMessageId(messageId);
    //	String mittente = "";
    //	for (Address addres : pecMessage.getFrom()) {
    //	    InternetAddress ia = (InternetAddress) addres;
    //	    mittente = mittente + ia.getAddress() + ";";
    //	}
    //	movimentiMailRequest.setMittente(mittente);
    //	movimentiMailRequest.setOggetto(pecMessage.getSubject());
    //	movimentiMailRequest.setSoftware(software);
    //	movimentiMailRequest.setToken(token);
    //	if (codiceMovimento != null) {
    //	    movimentiMailRequest.setCodicemovimento(codiceMovimento);
    //	}
    //	try {
    //	    if (listaFileAttachment != null) {
    //		if (idOggettiList == null || idOggettiList.size() == 0) {
    //		    for (String nomeFile : listaFileAttachment) {
    //			AllegatoType allegato = new AllegatoType();
    //			File f = new File(tmpPath + nomeFile);
    //			String longString = Long.valueOf(f.length()).toString();
    //			DataHandler dh = AllegatiUtil.bytesToDataHandler(AllegatiUtil.getBinaryData(tmpPath, nomeFile, new BigInteger(longString)));
    //			allegato.setBinaryData(dh);
    //			allegato.setDescrizione(nomeFile);
    //			allegato.setFileName(nomeFile);
    //			String mimeType = new MimetypesFileTypeMap().getContentType(f);
    //			allegato.setMimeType(mimeType);
    //			movimentiMailRequest.getAllegati().add(allegato);
    //		    }
    //		} else {
    //		    int index = 0;
    //		    for (String nomeFile : listaFileAttachment) {
    //			AllegatoType allegato = new AllegatoType();
    //			allegato.setId(new BigInteger(idOggettiList.get(index)));
    //			allegato.setDescrizione(nomeFile);
    //			allegato.setFileName(nomeFile);
    //			allegato.setMimeType(MimetypesFileTypeMap.getDefaultFileTypeMap().getContentType(nomeFile));
    //			movimentiMailRequest.getAllegati().add(allegato);
    //			index++;
    //		    }
    //		}
    //	    }
    //	} catch (Exception e) {
    //	    log.error("mailConfig: token={}, software={}, action={}, url={}, error={}", new Object[] { token, software, "", url, e.getMessage() });
    //	    throw e;//new RuntimeException(e.getMessage());
    //	}
    //	try {
    //	    MovimentiMailResponse movimentiMailResponse = (MovimentiMailResponse) webServiceTemplate.marshalSendAndReceive(url, movimentiMailRequest);
    //	    return movimentiMailResponse;
    //	} catch (Exception e) {
    //	    String soapErr = getSOAPFAULT(e);
    //	    log.error("mailConfig: token={}, software={}, action={}, url={}, error={}", new Object[] { token, software, "", url, e.getMessage() });
    //	    throw e;//new RuntimeException(e.getMessage());
    //	}
    //    }
    public MovimentiMailResponse2 movimentiMail2(String url, String token, String software, BigInteger idAccount, PECMessage pecMessage,
	    String tmpPath, ArrayList<String> listaFileAttachment, String codiceMovimento, ArrayList<String> idOggettiList) throws Exception {

	log.debug("       --> movimentiMail: token={}, software={}, idAccount={}, action={}, url={}", new Object[] { token, software, idAccount, "",
		url });
	MovimentiMailRequest2 movimentiMailRequest2 = new MovimentiMailRequest2();
	movimentiMailRequest2.setCorpo(pecMessage.getBody());
	String destinatario = "";
	for (Address addres : pecMessage.getTo()) {
	    InternetAddress ia = (InternetAddress) addres;
	    destinatario = destinatario + ia.getAddress() + ";";
	}
	movimentiMailRequest2.setDestinatario(destinatario);
	String messageId = pecMessage.getRifMsgId();
	log.debug("       --> movimentiMail: token={}, getRifMsgId={}", new Object[] { token, messageId });
	if (messageId == null || messageId.equalsIgnoreCase("")) {
	    messageId = pecMessage.getId();
	    log.debug("       --> movimentiMail: token={}, getId={}", new Object[] { token, messageId });
	}
	if (messageId != null) {
	    if (messageId.startsWith("<")) {
		messageId = messageId.substring(1);
	    }
	    if (messageId.endsWith(">")) {
		messageId = messageId.substring(0, messageId.length() - 1);
	    }
	}
	log.debug("       --> movimentiMail: token={}, messageId elaborato={}", new Object[] { token, messageId });
	movimentiMailRequest2.setMessageId(messageId);
	String mittente = "";
	for (Address addres : pecMessage.getFrom()) {
	    InternetAddress ia = (InternetAddress) addres;
	    mittente = mittente + ia.getAddress() + ";";
	}
	movimentiMailRequest2.setMittente(mittente);
	movimentiMailRequest2.setOggetto(pecMessage.getSubject());
	movimentiMailRequest2.setSoftware(software);
	movimentiMailRequest2.setToken(token);
	if (codiceMovimento != null) {
	    movimentiMailRequest2.setCodicemovimento(codiceMovimento);
	}
	movimentiMailRequest2.setIdaccount(idAccount);
	try {
	    if (listaFileAttachment != null) {
		if (idOggettiList == null || idOggettiList.size() == 0) {
		    for (String nomeFile : listaFileAttachment) {
			AllegatoType allegato = new AllegatoType();
			File f = new File(tmpPath + nomeFile);
			String longString = Long.valueOf(f.length()).toString();
			DataHandler dh = AllegatiUtil.bytesToDataHandler(AllegatiUtil.getBinaryData(tmpPath, nomeFile, new BigInteger(longString)));
			allegato.setBinaryData(dh);
			allegato.setDescrizione(nomeFile);
			allegato.setFileName(nomeFile);
			String mimeType = new MimetypesFileTypeMap().getContentType(f);
			allegato.setMimeType(mimeType);
			movimentiMailRequest2.getAllegati().add(allegato);
		    }
		} else {
		    int index = 0;
		    for (String nomeFile : listaFileAttachment) {
			AllegatoType allegato = new AllegatoType();
			allegato.setId(new BigInteger(idOggettiList.get(index)));
			allegato.setDescrizione(nomeFile);
			allegato.setFileName(nomeFile);
			allegato.setMimeType(MimetypesFileTypeMap.getDefaultFileTypeMap().getContentType(nomeFile));
			movimentiMailRequest2.getAllegati().add(allegato);
			index++;
		    }
		}
	    }
	} catch (Exception e) {
	    log.error("mailConfig: token={}, software={}, action={}, url={}, error={}", new Object[] { token, software, "", url, e.getMessage() });
	    throw e;//new RuntimeException(e.getMessage());
	}
	try {
	    MovimentiMailResponse2 movimentiMailResponse2 = (MovimentiMailResponse2) webServiceTemplate.marshalSendAndReceive(url,
		    movimentiMailRequest2);
	    return movimentiMailResponse2;
	} catch (Exception e) {
	    String soapErr = getSOAPFAULT(e);
	    log.error("mailConfig: token={}, software={}, action={}, url={}, error={}", new Object[] { token, software, "", url, e.getMessage() });
	    throw e;//new RuntimeException(e.getMessage());
	}
    }

    @SuppressWarnings("rawtypes")
    protected String getSOAPFAULT(Exception e) {

	StringBuffer details = new StringBuffer();
	details.append(" ");
	if (e instanceof SoapFaultClientException) {
	    SoapFaultClientException we = (SoapFaultClientException) e;
	    SoapFaultDetail detail = we.getSoapFault().getFaultDetail();
	    if (detail != null) {
		for (Iterator iterator = detail.getDetailEntries(); iterator.hasNext();) {
		    SoapFaultDetailElement el = (SoapFaultDetailElement) iterator.next();
		    if (el.getResult() != null && el.getResult() instanceof DOMResult) {
			DOMResult res = (DOMResult) el.getResult();
			if (res.getNode() != null) {
			    details.append("\n").append(res.getNode().getTextContent());
			}
		    }
		}
	    }
	}
	return details.toString();
    }

    public WebServiceTemplate getWebServiceTemplate() {

	return webServiceTemplate;
    }

    public void setWebServiceTemplate(WebServiceTemplate webServiceTemplate) {

	this.webServiceTemplate = webServiceTemplate;
    }
}
