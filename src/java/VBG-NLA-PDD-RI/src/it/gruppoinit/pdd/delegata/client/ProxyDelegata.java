package it.gruppoinit.pdd.delegata.client;

import it.gruppoinit.pdd.exceptions.PortaDelegataException;
import it.gruppoinit.pdd.utils.Utilities;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.soap.MTOMFeature;
import javax.xml.ws.soap.SOAPBinding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProxyDelegata implements Serializable {

    private static final long serialVersionUID = -1946469617074176273L;
    private String urlWS;
    private static Logger log = LoggerFactory.getLogger(ProxyDelegata.class);

    private ProxyDelegata() {

	super();
    }

    public ProxyDelegata(String urlWS) {

	this();
	this.urlWS = urlWS;
    }

    public Object invocaPortaDelegata(QName operation, Source input, Object outputAttesto) throws PortaDelegataException {

	if (log.isDebugEnabled()) {
	    log.debug("#invocaPortaDelegata(operation={},urlWs={})", operation, this.urlWS);
	}
	Service svc = Service.create(operation);
	if (log.isDebugEnabled()) {
	    log.debug("#invocaPortaDelegata: servizio creato, aggiungo la porta");
	}
	svc.addPort(operation, SOAPBinding.SOAP11HTTP_MTOM_BINDING, this.urlWS);
	if (log.isDebugEnabled()) {
	    log.debug("#invocaPortaDelegata: creo il dispatch");
	}
	WebServiceFeature mtomFeature = new MTOMFeature(true, 0);
	Dispatch<Source> dispatch = svc.createDispatch(operation, Source.class, Service.Mode.PAYLOAD, mtomFeature);
	// JaxWsClientEndpointImpl client = ((JaxWsClientEndpointImpl) dispatch.getBinding());
	// client.getInInterceptors().add(new LoggingInInterceptor());
	// client.getOutInterceptors().add(new LoggingOutInterceptor());
	if (log.isDebugEnabled()) {
	    log.debug("#invocaPortaDelegata: invoco il WS");
	}
	Source output = dispatch.invoke(input);
	StreamResult result = new StreamResult(new ByteArrayOutputStream());
	Transformer trans;
	try {
	    trans = TransformerFactory.newInstance().newTransformer();
	    trans.transform(output, result);
	} catch (Exception e) {
	    String messaggioErrore = "Errore nella trasformazione del risultato invocazione del WS [" + this.urlWS + "] e operazione [" + operation
		    + "]";
	    throw new PortaDelegataException(messaggioErrore, e);
	}
	ByteArrayOutputStream baos = (ByteArrayOutputStream) result.getOutputStream();
	String responseContent = new String(baos.toByteArray());
	try {
	    outputAttesto = Utilities.unMarshallString(responseContent, outputAttesto.getClass());
	} catch (Exception e) {
	    String messaggioErrore = "Errore nella conversione del risultato invocazione del WS [" + this.urlWS + "] e operazione [" + operation
		    + "] per la classe [" + outputAttesto.getClass() + "]";
	    throw new PortaDelegataException(messaggioErrore, e);
	}
	return outputAttesto;
    }
}
