package it.gruppoinit.pal.gp.core.ws.client;

public class BaseWsClient {

    protected String getSOAPFAULT(Exception e) {

	String errore = "";
	try {
	    javax.xml.soap.Detail detail = ((javax.xml.ws.soap.SOAPFaultException) e).getFault().getDetail();
	    java.util.Iterator<javax.xml.soap.DetailEntry> it = detail.getDetailEntries();
	    while (it.hasNext()) {
		javax.xml.soap.DetailEntry de = (javax.xml.soap.DetailEntry) it.next();
		if (de != null) {
		    if ("MessaggioDiErroreApplicativo".equalsIgnoreCase(org.apache.commons.lang.StringUtils.defaultString(de.getLocalName()))) {
			String xml = org.apache.cxf.helpers.XMLUtils.toString(de);
			errore += "\nDettagli Errore Applicativo:\n " + xml;
		    }
		    if ("ErroreValidazione".equalsIgnoreCase(org.apache.commons.lang.StringUtils.defaultString(de.getLocalName()))) {
			String xml = org.apache.cxf.helpers.XMLUtils.toString(de);
			errore += "\nDettagli Errore Validazione:\n " + xml;
		    }
		}
	    }
	} catch (Exception ex) {
	    errore = e.getMessage();
	}
	return errore;
    }
}
