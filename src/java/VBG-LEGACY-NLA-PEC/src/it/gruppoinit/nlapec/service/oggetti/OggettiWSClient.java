package it.gruppoinit.nlapec.service.oggetti;

import it.gruppoinit.sigepro.schemas.messages.oggetti.OggettiInsertRequest;
import it.gruppoinit.sigepro.schemas.messages.oggetti.OggettiInsertResponse;

import java.io.File;
import java.util.Iterator;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.MimetypesFileTypeMap;
import javax.xml.transform.dom.DOMResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapFaultDetail;
import org.springframework.ws.soap.SoapFaultDetailElement;
import org.springframework.ws.soap.client.SoapFaultClientException;

public class OggettiWSClient {

    private static final Logger log = LoggerFactory.getLogger(OggettiWSClient.class);
    private WebServiceTemplate webServiceTemplate;

    public OggettiInsertResponse oggettiInsert(String url, String token, String fullPathFileName) {

	try {
	    MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
	    File f = new File(fullPathFileName);
	    DataSource source = new FileDataSource(f);
	    DataHandler dh = new DataHandler(source);
	    OggettiInsertRequest request = new OggettiInsertRequest();
	    request.setBinaryData(dh);
	    request.setFileName(f.getName());
	    request.setMimeType(mimeTypesMap.getContentType(f));
	    request.setToken(token);
	    OggettiInsertResponse oggettiInsertResponse = (OggettiInsertResponse) webServiceTemplate.marshalSendAndReceive(url, request);
	    return oggettiInsertResponse;
	} catch (Exception e) {
	    String soapErr = getSOAPFAULT(e);
	    log.error("oggettiInsert: token={}, url={}, error={}", new Object[] { token, url, soapErr });
	    throw new RuntimeException(e.getMessage());
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
