
package it.gov.impresainungiorno.suap.scrivania;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.gov.impresainungiorno.suap.scrivania package. 
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

    private final static QName _InviaEnteSUAPResponse_QNAME = new QName("http://www.impresainungiorno.gov.it/suap/scrivania", "inviaEnteSUAPResponse");
    private final static QName _InviaEnteSUAP_QNAME = new QName("http://www.impresainungiorno.gov.it/suap/scrivania", "inviaEnteSUAP");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.gov.impresainungiorno.suap.scrivania
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link InviaEnteSUAP }
     * 
     */
    public InviaEnteSUAP createInviaEnteSUAP() {
        return new InviaEnteSUAP();
    }

    /**
     * Create an instance of {@link InviaEnteSUAPResponse }
     * 
     */
    public InviaEnteSUAPResponse createInviaEnteSUAPResponse() {
        return new InviaEnteSUAPResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InviaEnteSUAPResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.impresainungiorno.gov.it/suap/scrivania", name = "inviaEnteSUAPResponse")
    public JAXBElement<InviaEnteSUAPResponse> createInviaEnteSUAPResponse(InviaEnteSUAPResponse value) {
        return new JAXBElement<InviaEnteSUAPResponse>(_InviaEnteSUAPResponse_QNAME, InviaEnteSUAPResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InviaEnteSUAP }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.impresainungiorno.gov.it/suap/scrivania", name = "inviaEnteSUAP")
    public JAXBElement<InviaEnteSUAP> createInviaEnteSUAP(InviaEnteSUAP value) {
        return new JAXBElement<InviaEnteSUAP>(_InviaEnteSUAP_QNAME, InviaEnteSUAP.class, null, value);
    }

}
