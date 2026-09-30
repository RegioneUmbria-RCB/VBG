
package it.gov.impresainungiorno.suap.scrivania.ente;

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

    private final static QName _InviaSUAPEnte_QNAME = new QName("http://www.impresainungiorno.gov.it/suap/scrivania", "inviaSUAPEnte");
    private final static QName _InviaSUAPEnteResponse_QNAME = new QName("http://www.impresainungiorno.gov.it/suap/scrivania", "inviaSUAPEnteResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.gov.impresainungiorno.suap.scrivania
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link InviaSUAPEnte }
     * 
     */
    public InviaSUAPEnte createInviaSUAPEnte() {
        return new InviaSUAPEnte();
    }

    /**
     * Create an instance of {@link InviaSUAPEnteResponse }
     * 
     */
    public InviaSUAPEnteResponse createInviaSUAPEnteResponse() {
        return new InviaSUAPEnteResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InviaSUAPEnte }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link InviaSUAPEnte }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.impresainungiorno.gov.it/suap/scrivania", name = "inviaSUAPEnte")
    public JAXBElement<InviaSUAPEnte> createInviaSUAPEnte(InviaSUAPEnte value) {
        return new JAXBElement<InviaSUAPEnte>(_InviaSUAPEnte_QNAME, InviaSUAPEnte.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InviaSUAPEnteResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link InviaSUAPEnteResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.impresainungiorno.gov.it/suap/scrivania", name = "inviaSUAPEnteResponse")
    public JAXBElement<InviaSUAPEnteResponse> createInviaSUAPEnteResponse(InviaSUAPEnteResponse value) {
        return new JAXBElement<InviaSUAPEnteResponse>(_InviaSUAPEnteResponse_QNAME, InviaSUAPEnteResponse.class, null, value);
    }

}
