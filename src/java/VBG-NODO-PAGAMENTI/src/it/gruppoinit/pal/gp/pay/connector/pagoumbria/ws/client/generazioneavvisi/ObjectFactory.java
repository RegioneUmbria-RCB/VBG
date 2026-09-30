package it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.generazioneavvisi;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/**
 * This object contains factory methods for each Java content interface and Java element interface generated in the
 * it.tasgroup.idp.generazioneavvisi package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the Java representation for XML content.
 * The Java representation of XML content can consist of schema derived interfaces and classes representing the binding
 * of schema type definitions, element declarations and model groups. Factory methods for each of these are provided in
 * this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ElencoAvvisiType_QNAME = new QName("http://idp.tasgroup.it/GenerazioneAvvisi/", "ElencoAvvisiType");
    private final static QName _ElencoIdentificativiType_QNAME = new QName("http://idp.tasgroup.it/GenerazioneAvvisi/", "ElencoIdentificativiType");
    private final static QName _GeneraAvvisoResponse_QNAME = new QName("http://idp.tasgroup.it/GenerazioneAvvisi/", "GeneraAvvisoResponse");
    private final static QName _GeneraAvvisoResponseType_QNAME = new QName("http://idp.tasgroup.it/GenerazioneAvvisi/", "GeneraAvvisoResponseType");
    private final static QName _GeneraLottoAvvisiResponse_QNAME = new QName("http://idp.tasgroup.it/GenerazioneAvvisi/", "GeneraLottoAvvisiResponse");
    private final static QName _GeneraLottoAvvisiResponseType_QNAME = new QName("http://idp.tasgroup.it/GenerazioneAvvisi/",
	    "GeneraLottoAvvisiResponseType");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package:
     * it.tasgroup.idp.generazioneavvisi
     * 
     */
    public ObjectFactory() {

    }

    /**
     * Create an instance of {@link ElencoAvvisiType }
     * 
     */
    public ElencoAvvisiType createElencoAvvisiType() {

	return new ElencoAvvisiType();
    }

    /**
     * Create an instance of {@link ElencoIdentificativiType }
     * 
     */
    public ElencoIdentificativiType createElencoIdentificativiType() {

	return new ElencoIdentificativiType();
    }

    /**
     * Create an instance of {@link GeneraAvvisoRequest }
     * 
     */
    public GeneraAvvisoRequest createGeneraAvvisoRequest() {

	return new GeneraAvvisoRequest();
    }

    /**
     * Create an instance of {@link GeneraAvvisoResponseType }
     * 
     */
    public GeneraAvvisoResponseType createGeneraAvvisoResponseType() {

	return new GeneraAvvisoResponseType();
    }

    /**
     * Create an instance of {@link GeneraLottoAvvisiRequest }
     * 
     */
    public GeneraLottoAvvisiRequest createGeneraLottoAvvisiRequest() {

	return new GeneraLottoAvvisiRequest();
    }

    /**
     * Create an instance of {@link GeneraLottoAvvisiResponseType }
     * 
     */
    public GeneraLottoAvvisiResponseType createGeneraLottoAvvisiResponseType() {

	return new GeneraLottoAvvisiResponseType();
    }

    /**
     * Create an instance of {@link ResponseBase }
     * 
     */
    public ResponseBase createResponseBase() {

	return new ResponseBase();
    }

    /**
     * Create an instance of {@link GeneraLottoAvvisiResponseBodyType }
     * 
     */
    public GeneraLottoAvvisiResponseBodyType createGeneraLottoAvvisiResponseBodyType() {

	return new GeneraLottoAvvisiResponseBodyType();
    }

    /**
     * Create an instance of {@link FaultType }
     * 
     */
    public FaultType createFaultType() {

	return new FaultType();
    }

    /**
     * Create an instance of {@link GeneraAvvisoResponseBodyType }
     * 
     */
    public GeneraAvvisoResponseBodyType createGeneraAvvisoResponseBodyType() {

	return new GeneraAvvisoResponseBodyType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ElencoAvvisiType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://idp.tasgroup.it/GenerazioneAvvisi/", name = "ElencoAvvisiType")
    public JAXBElement<ElencoAvvisiType> createElencoAvvisiType(ElencoAvvisiType value) {

	return new JAXBElement<ElencoAvvisiType>(_ElencoAvvisiType_QNAME, ElencoAvvisiType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ElencoIdentificativiType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://idp.tasgroup.it/GenerazioneAvvisi/", name = "ElencoIdentificativiType")
    public JAXBElement<ElencoIdentificativiType> createElencoIdentificativiType(ElencoIdentificativiType value) {

	return new JAXBElement<ElencoIdentificativiType>(_ElencoIdentificativiType_QNAME, ElencoIdentificativiType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GeneraAvvisoResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://idp.tasgroup.it/GenerazioneAvvisi/", name = "GeneraAvvisoResponse")
    public JAXBElement<GeneraAvvisoResponseType> createGeneraAvvisoResponse(GeneraAvvisoResponseType value) {

	return new JAXBElement<GeneraAvvisoResponseType>(_GeneraAvvisoResponse_QNAME, GeneraAvvisoResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GeneraAvvisoResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://idp.tasgroup.it/GenerazioneAvvisi/", name = "GeneraAvvisoResponseType")
    public JAXBElement<GeneraAvvisoResponseType> createGeneraAvvisoResponseType(GeneraAvvisoResponseType value) {

	return new JAXBElement<GeneraAvvisoResponseType>(_GeneraAvvisoResponseType_QNAME, GeneraAvvisoResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GeneraLottoAvvisiResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://idp.tasgroup.it/GenerazioneAvvisi/", name = "GeneraLottoAvvisiResponse")
    public JAXBElement<GeneraLottoAvvisiResponseType> createGeneraLottoAvvisiResponse(GeneraLottoAvvisiResponseType value) {

	return new JAXBElement<GeneraLottoAvvisiResponseType>(_GeneraLottoAvvisiResponse_QNAME, GeneraLottoAvvisiResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GeneraLottoAvvisiResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://idp.tasgroup.it/GenerazioneAvvisi/", name = "GeneraLottoAvvisiResponseType")
    public JAXBElement<GeneraLottoAvvisiResponseType> createGeneraLottoAvvisiResponseType(GeneraLottoAvvisiResponseType value) {

	return new JAXBElement<GeneraLottoAvvisiResponseType>(_GeneraLottoAvvisiResponseType_QNAME, GeneraLottoAvvisiResponseType.class, null, value);
    }
}
