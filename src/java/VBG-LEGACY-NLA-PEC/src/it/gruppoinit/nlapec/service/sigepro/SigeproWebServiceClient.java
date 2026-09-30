package it.gruppoinit.nlapec.service.sigepro;

import it.gruppoinit.sigepro.schemas.messages.mailconfig.ActionType;
import it.gruppoinit.sigepro.schemas.messages.mailconfig.MailConfigRequest2;
import it.gruppoinit.sigepro.schemas.messages.mailconfig.MailConfigResponse2;

import java.math.BigInteger;
import java.util.Iterator;

import javax.xml.transform.dom.DOMResult;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapFaultDetail;
import org.springframework.ws.soap.SoapFaultDetailElement;
import org.springframework.ws.soap.client.SoapFaultClientException;

public class SigeproWebServiceClient {

    private static final Logger log = LoggerFactory.getLogger(SigeproWebServiceClient.class);
    private WebServiceTemplate webServiceTemplate;

    //    public MailConfigResponse mailConfig(String url, String token, String software, ActionType action) {
    //
    //	MailConfigRequest mailConfigRequest = new MailConfigRequest();
    //	mailConfigRequest.setToken(token);
    //	mailConfigRequest.setSoftware(software);
    //	mailConfigRequest.setAction(action);
    //	log.debug("mailConfig: token={}, software={}, action={}, url={}", new Object[] { token, software, action.toString(), url });
    //	try {
    //	    MailConfigResponse mailConfigResponse = (MailConfigResponse) webServiceTemplate.marshalSendAndReceive(url, mailConfigRequest);
    //	    return mailConfigResponse;
    //	} catch (Exception e) {
    //	    String soapErr = getSOAPFAULT(e);
    //	    log.error("mailConfig: token={}, software={}, action={}, url={}", new Object[] { token, software, action.toString(), url });
    //	    throw new RuntimeException(e.getMessage());
    //	}
    //    }
    public MailConfigResponse2 mailConfig2(String url, String token, String software, String codicecomune, BigInteger idAccount, ActionType action) {

	MailConfigRequest2 mailConfigRequest2 = new MailConfigRequest2();
	mailConfigRequest2.setToken(token);
	mailConfigRequest2.setSoftware(software);
	mailConfigRequest2.setAction(action);
	if (StringUtils.isNotBlank(codicecomune)) {
	    mailConfigRequest2.setCodicecomune(codicecomune);
	} else {
	    mailConfigRequest2.setCodicecomune(null);
	}
	if (idAccount != null) {
	    mailConfigRequest2.setIdAccount(idAccount);
	}
	log.debug("mailConfig: token={}, software={},codicecomune={},idAccount={}, action={}, url={}", new Object[] { token, software, codicecomune,
		idAccount, action.toString(), url });
	try {
	    MailConfigResponse2 mailConfigResponse2 = (MailConfigResponse2) webServiceTemplate.marshalSendAndReceive(url, mailConfigRequest2);
	    return mailConfigResponse2;
	} catch (Exception e) {
	    String soapErr = getSOAPFAULT(e);
	    log.error("mailConfig: token={}, software={},codicecomune={},idAccount={}, action={}, url={}", new Object[] { token, software,
		    codicecomune, idAccount, action.toString(), url });
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
