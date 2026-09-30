
package it.gruppoinit.pal.gp.backoffice.schemas.messages.attivita;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.gruppoinit.pal.gp.backoffice.schemas.messages.attivita package. 
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

    private final static QName _SnapShotRequest_QNAME = new QName("http://gruppoinit.it/sigepro/schemas/messages/attivita", "SnapShotRequest");
    private final static QName _AggiornaCampiSchedeRequest_QNAME = new QName("http://gruppoinit.it/sigepro/schemas/messages/attivita", "AggiornaCampiSchedeRequest");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.gruppoinit.pal.gp.backoffice.schemas.messages.attivita
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SnapShotResponse }
     * 
     */
    public SnapShotResponse createSnapShotResponse() {
        return new SnapShotResponse();
    }

    /**
     * Create an instance of {@link AggiornaCampiSchedeRequestType }
     * 
     */
    public AggiornaCampiSchedeRequestType createAggiornaCampiSchedeRequestType() {
        return new AggiornaCampiSchedeRequestType();
    }

    /**
     * Create an instance of {@link AggiornaCampiSchedeResponse }
     * 
     */
    public AggiornaCampiSchedeResponse createAggiornaCampiSchedeResponse() {
        return new AggiornaCampiSchedeResponse();
    }

    /**
     * Create an instance of {@link SnapShotRequestType }
     * 
     */
    public SnapShotRequestType createSnapShotRequestType() {
        return new SnapShotRequestType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SnapShotRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://gruppoinit.it/sigepro/schemas/messages/attivita", name = "SnapShotRequest")
    public JAXBElement<SnapShotRequestType> createSnapShotRequest(SnapShotRequestType value) {
        return new JAXBElement<SnapShotRequestType>(_SnapShotRequest_QNAME, SnapShotRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AggiornaCampiSchedeRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://gruppoinit.it/sigepro/schemas/messages/attivita", name = "AggiornaCampiSchedeRequest")
    public JAXBElement<AggiornaCampiSchedeRequestType> createAggiornaCampiSchedeRequest(AggiornaCampiSchedeRequestType value) {
        return new JAXBElement<AggiornaCampiSchedeRequestType>(_AggiornaCampiSchedeRequest_QNAME, AggiornaCampiSchedeRequestType.class, null, value);
    }

}
