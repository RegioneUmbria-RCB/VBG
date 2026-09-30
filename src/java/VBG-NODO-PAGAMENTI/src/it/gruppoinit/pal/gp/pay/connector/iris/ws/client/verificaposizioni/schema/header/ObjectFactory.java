
package it.gruppoinit.pal.gp.pay.connector.iris.ws.client.verificaposizioni.schema.header;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.gruppoinit.pal.gp.pay.connector.iris.ws.client.verificaposizioni.schema.header package. 
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

    private final static QName _IdpHeader_QNAME = new QName("http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpHeader", "IdpHeader");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.gruppoinit.pal.gp.pay.connector.iris.ws.client.verificaposizioni.schema.header
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link IdpHeader }
     * 
     */
    public IdpHeader createIdpHeader() {
        return new IdpHeader();
    }

    /**
     * Create an instance of {@link IdpOTF }
     * 
     */
    public IdpOTF createIdpOTF() {
        return new IdpOTF();
    }

    /**
     * Create an instance of {@link DATIVERSANTEType }
     * 
     */
    public DATIVERSANTEType createDATIVERSANTEType() {
        return new DATIVERSANTEType();
    }

    /**
     * Create an instance of {@link HeaderTRT }
     * 
     */
    public HeaderTRT createHeaderTRT() {
        return new HeaderTRT();
    }

    /**
     * Create an instance of {@link TRTSender }
     * 
     */
    public TRTSender createTRTSender() {
        return new TRTSender();
    }

    /**
     * Create an instance of {@link TRTReceiver }
     * 
     */
    public TRTReceiver createTRTReceiver() {
        return new TRTReceiver();
    }

    /**
     * Create an instance of {@link HeaderE2E }
     * 
     */
    public HeaderE2E createHeaderE2E() {
        return new HeaderE2E();
    }

    /**
     * Create an instance of {@link E2ESender }
     * 
     */
    public E2ESender createE2ESender() {
        return new E2ESender();
    }

    /**
     * Create an instance of {@link E2EReceiver }
     * 
     */
    public E2EReceiver createE2EReceiver() {
        return new E2EReceiver();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IdpHeader }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpHeader", name = "IdpHeader")
    public JAXBElement<IdpHeader> createIdpHeader(IdpHeader value) {
        return new JAXBElement<IdpHeader>(_IdpHeader_QNAME, IdpHeader.class, null, value);
    }

}
