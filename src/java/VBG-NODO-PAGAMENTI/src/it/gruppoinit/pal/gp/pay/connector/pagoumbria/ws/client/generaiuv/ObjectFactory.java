
package it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.generaiuv;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.generaiuv package. 
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

    private final static QName _ElencoIdentificativiType_QNAME = new QName("http://idp.tasgroup.it/GenerazioneIUV/", "ElencoIdentificativiType");
    private final static QName _GeneraIUVResponse_QNAME = new QName("http://idp.tasgroup.it/GenerazioneIUV/", "GeneraIUVResponse");
    private final static QName _GeneraIUVResponseType_QNAME = new QName("http://idp.tasgroup.it/GenerazioneIUV/", "GeneraIUVResponseType");
    private final static QName _GeneraLottoIUVResponse_QNAME = new QName("http://idp.tasgroup.it/GenerazioneIUV/", "GeneraLottoIUVResponse");
    private final static QName _GeneraLottoIUVResponseType_QNAME = new QName("http://idp.tasgroup.it/GenerazioneIUV/", "GeneraLottoIUVResponseType");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.generaiuv
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ElencoIdentificativiType }
     * 
     */
    public ElencoIdentificativiType createElencoIdentificativiType() {
        return new ElencoIdentificativiType();
    }

    /**
     * Create an instance of {@link GeneraIUVRequest }
     * 
     */
    public GeneraIUVRequest createGeneraIUVRequest() {
        return new GeneraIUVRequest();
    }

    /**
     * Create an instance of {@link GeneraIUVResponseType }
     * 
     */
    public GeneraIUVResponseType createGeneraIUVResponseType() {
        return new GeneraIUVResponseType();
    }

    /**
     * Create an instance of {@link GeneraLottoIUVRequest }
     * 
     */
    public GeneraLottoIUVRequest createGeneraLottoIUVRequest() {
        return new GeneraLottoIUVRequest();
    }

    /**
     * Create an instance of {@link GeneraLottoIUVResponseType }
     * 
     */
    public GeneraLottoIUVResponseType createGeneraLottoIUVResponseType() {
        return new GeneraLottoIUVResponseType();
    }

    /**
     * Create an instance of {@link ResponseBase }
     * 
     */
    public ResponseBase createResponseBase() {
        return new ResponseBase();
    }

    /**
     * Create an instance of {@link GeneraIUVResponseBodyType }
     * 
     */
    public GeneraIUVResponseBodyType createGeneraIUVResponseBodyType() {
        return new GeneraIUVResponseBodyType();
    }

    /**
     * Create an instance of {@link FaultType }
     * 
     */
    public FaultType createFaultType() {
        return new FaultType();
    }

    /**
     * Create an instance of {@link GeneraLottoIUVResponseBodyType }
     * 
     */
    public GeneraLottoIUVResponseBodyType createGeneraLottoIUVResponseBodyType() {
        return new GeneraLottoIUVResponseBodyType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ElencoIdentificativiType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://idp.tasgroup.it/GenerazioneIUV/", name = "ElencoIdentificativiType")
    public JAXBElement<ElencoIdentificativiType> createElencoIdentificativiType(ElencoIdentificativiType value) {
        return new JAXBElement<ElencoIdentificativiType>(_ElencoIdentificativiType_QNAME, ElencoIdentificativiType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GeneraIUVResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://idp.tasgroup.it/GenerazioneIUV/", name = "GeneraIUVResponse")
    public JAXBElement<GeneraIUVResponseType> createGeneraIUVResponse(GeneraIUVResponseType value) {
        return new JAXBElement<GeneraIUVResponseType>(_GeneraIUVResponse_QNAME, GeneraIUVResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GeneraIUVResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://idp.tasgroup.it/GenerazioneIUV/", name = "GeneraIUVResponseType")
    public JAXBElement<GeneraIUVResponseType> createGeneraIUVResponseType(GeneraIUVResponseType value) {
        return new JAXBElement<GeneraIUVResponseType>(_GeneraIUVResponseType_QNAME, GeneraIUVResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GeneraLottoIUVResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://idp.tasgroup.it/GenerazioneIUV/", name = "GeneraLottoIUVResponse")
    public JAXBElement<GeneraLottoIUVResponseType> createGeneraLottoIUVResponse(GeneraLottoIUVResponseType value) {
        return new JAXBElement<GeneraLottoIUVResponseType>(_GeneraLottoIUVResponse_QNAME, GeneraLottoIUVResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GeneraLottoIUVResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://idp.tasgroup.it/GenerazioneIUV/", name = "GeneraLottoIUVResponseType")
    public JAXBElement<GeneraLottoIUVResponseType> createGeneraLottoIUVResponseType(GeneraLottoIUVResponseType value) {
        return new JAXBElement<GeneraLottoIUVResponseType>(_GeneraLottoIUVResponseType_QNAME, GeneraLottoIUVResponseType.class, null, value);
    }

}
