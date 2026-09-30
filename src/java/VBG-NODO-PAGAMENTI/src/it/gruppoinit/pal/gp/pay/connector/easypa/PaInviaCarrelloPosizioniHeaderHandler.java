package it.gruppoinit.pal.gp.pay.connector.easypa;

import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.jaxb.JAXBDataBinding;
import org.apache.cxf.phase.Phase;

import it.gruppoinit.pal.gp.pay.connector.easypa.ws.schema.head.PaInviaCarrelloPosizioniHeader;

public class PaInviaCarrelloPosizioniHeaderHandler extends AbstractSoapInterceptor {

    private PaInviaCarrelloPosizioniHeader header;

    public PaInviaCarrelloPosizioniHeaderHandler(String p) {

	super(p);
    }

    public PaInviaCarrelloPosizioniHeaderHandler(PaInviaCarrelloPosizioniHeader header) {

	this(Phase.PRE_LOGICAL);
	this.header = header;
    }

    @Override
    public void handleMessage(SoapMessage message) throws Fault {

	try {
	    message.getHeaders().add(new Header(new QName("http://services.sia.eu/head", "paInviaCarrelloPosizioniHeader"), this.header,
		    new JAXBDataBinding(PaInviaCarrelloPosizioniHeader.class)));
	} catch (JAXBException e) {
	    e.printStackTrace();
	}
    };
}
