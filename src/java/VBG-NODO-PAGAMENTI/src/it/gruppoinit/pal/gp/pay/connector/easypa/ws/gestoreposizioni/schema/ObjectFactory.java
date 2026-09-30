
package it.gruppoinit.pal.gp.pay.connector.easypa.ws.gestoreposizioni.schema;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the eu.sia.services package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _InserimentoPosizioneRequest_QNAME = new QName("http://services.sia.eu/", "inserimentoPosizioneRequest");
    private final static QName _InserimentoPosizioneResponse_QNAME = new QName("http://services.sia.eu/", "inserimentoPosizioneResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: eu.sia.services
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link InserimentoPosizioneRequest }
     * 
     */
    public InserimentoPosizioneRequest createInserimentoPosizioneRequest() {
        return new InserimentoPosizioneRequest();
    }

    /**
     * Create an instance of {@link InserimentoPosizioneResponse }
     * 
     */
    public InserimentoPosizioneResponse createInserimentoPosizioneResponse() {
        return new InserimentoPosizioneResponse();
    }

    /**
     * Create an instance of {@link InserimentoPosizioneInputType }
     * 
     */
    public InserimentoPosizioneInputType createInserimentoPosizioneInputType() {
        return new InserimentoPosizioneInputType();
    }

    /**
     * Create an instance of {@link InserimentoPosizioneOutputType }
     * 
     */
    public InserimentoPosizioneOutputType createInserimentoPosizioneOutputType() {
        return new InserimentoPosizioneOutputType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InserimentoPosizioneRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.sia.eu/", name = "inserimentoPosizioneRequest")
    public JAXBElement<InserimentoPosizioneRequest> createInserimentoPosizioneRequest(InserimentoPosizioneRequest value) {
        return new JAXBElement<InserimentoPosizioneRequest>(_InserimentoPosizioneRequest_QNAME, InserimentoPosizioneRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InserimentoPosizioneResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.sia.eu/", name = "inserimentoPosizioneResponse")
    public JAXBElement<InserimentoPosizioneResponse> createInserimentoPosizioneResponse(InserimentoPosizioneResponse value) {
        return new JAXBElement<InserimentoPosizioneResponse>(_InserimentoPosizioneResponse_QNAME, InserimentoPosizioneResponse.class, null, value);
    }

}
