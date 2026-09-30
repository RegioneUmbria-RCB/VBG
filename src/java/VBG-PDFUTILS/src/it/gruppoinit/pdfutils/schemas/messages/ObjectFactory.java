package it.gruppoinit.pdfutils.schemas.messages;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/**
 * This object contains factory methods for each Java content interface and Java element interface generated in the
 * it.gruppoinit.pdfutils.schemas.messages package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the Java representation for XML content.
 * The Java representation of XML content can consist of schema derived interfaces and classes representing the binding
 * of schema type definitions, element declarations and model groups. Factory methods for each of these are provided in
 * this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ApplicaTextLayerPDFResponse_QNAME = new QName("http://gruppoinit.it/schemas/messages/pdfutils",
	    "ApplicaTextLayerPDFResponse");
    private final static QName _PrecompilaPDFRequest_QNAME = new QName("http://gruppoinit.it/schemas/messages/pdfutils", "PrecompilaPDFRequest");
    private final static QName _ApplicaTextLayerPDFRequest_QNAME = new QName("http://gruppoinit.it/schemas/messages/pdfutils",
	    "ApplicaTextLayerPDFRequest");
    private final static QName _PrecompilaPDFResponse_QNAME = new QName("http://gruppoinit.it/schemas/messages/pdfutils", "PrecompilaPDFResponse");
    private final static QName _RecuperaDatiDaPDFRequest_QNAME = new QName("http://gruppoinit.it/schemas/messages/pdfutils",
	    "RecuperaDatiDaPDFRequest");
    private final static QName _RecuperaDatiDaPDFResponse_QNAME = new QName("http://gruppoinit.it/schemas/messages/pdfutils",
	    "RecuperaDatiDaPDFResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package:
     * it.gruppoinit.pdfutils.schemas.messages
     * 
     */
    public ObjectFactory() {

    }

    /**
     * Create an instance of {@link ApplicaTextLayerPDFResponse }
     * 
     */
    public ApplicaTextLayerPDFResponse createApplicaTextLayerPDFResponse() {

	return new ApplicaTextLayerPDFResponse();
    }

    /**
     * Create an instance of {@link PrecompilaPDFResponseType }
     * 
     */
    public PrecompilaPDFResponseType createPrecompilaPDFResponseType() {

	return new PrecompilaPDFResponseType();
    }

    /**
     * Create an instance of {@link ApplicaTextLayerPDFRequest }
     * 
     */
    public ApplicaTextLayerPDFRequest createApplicaTextLayerPDFRequest() {

	return new ApplicaTextLayerPDFRequest();
    }

    /**
     * Create an instance of {@link PrecompilaPDFRequestType }
     * 
     */
    public PrecompilaPDFRequestType createPrecompilaPDFRequestType() {

	return new PrecompilaPDFRequestType();
    }

    /**
     * Create an instance of {@link XmlFileType }
     * 
     */
    public XmlFileType createXmlFileType() {

	return new XmlFileType();
    }

    /**
     * Create an instance of {@link DatiPDFType }
     * 
     */
    public DatiPDFType createDatiPDFType() {

	return new DatiPDFType();
    }

    /**
     * Create an instance of {@link PDFFileType }
     * 
     */
    public PDFFileType createPDFFileType() {

	return new PDFFileType();
    }

    /**
     * Create an instance of {@link Layer }
     * 
     */
    public Layer createLayer() {

	return new Layer();
    }

    /**
     * Create an instance of {@link Font }
     * 
     */
    public Font createFont() {

	return new Font();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ApplicaTextLayerPDFResponse }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://gruppoinit.it/schemas/messages/pdfutils", name = "ApplicaTextLayerPDFResponse")
    public JAXBElement<ApplicaTextLayerPDFResponse> createApplicaTextLayerPDFResponse(ApplicaTextLayerPDFResponse value) {

	return new JAXBElement<ApplicaTextLayerPDFResponse>(_ApplicaTextLayerPDFResponse_QNAME, ApplicaTextLayerPDFResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PrecompilaPDFRequestType }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://gruppoinit.it/schemas/messages/pdfutils", name = "PrecompilaPDFRequest")
    public JAXBElement<PrecompilaPDFRequestType> createPrecompilaPDFRequest(PrecompilaPDFRequestType value) {

	return new JAXBElement<PrecompilaPDFRequestType>(_PrecompilaPDFRequest_QNAME, PrecompilaPDFRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ApplicaTextLayerPDFRequest }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://gruppoinit.it/schemas/messages/pdfutils", name = "ApplicaTextLayerPDFRequest")
    public JAXBElement<ApplicaTextLayerPDFRequest> createApplicaTextLayerPDFRequest(ApplicaTextLayerPDFRequest value) {

	return new JAXBElement<ApplicaTextLayerPDFRequest>(_ApplicaTextLayerPDFRequest_QNAME, ApplicaTextLayerPDFRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PrecompilaPDFResponseType }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://gruppoinit.it/schemas/messages/pdfutils", name = "PrecompilaPDFResponse")
    public JAXBElement<PrecompilaPDFResponseType> createPrecompilaPDFResponse(PrecompilaPDFResponseType value) {

	return new JAXBElement<PrecompilaPDFResponseType>(_PrecompilaPDFResponse_QNAME, PrecompilaPDFResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link RecuperaDatiDaPDFResponseType }
     * 
     */
    public RecuperaDatiDaPDFResponseType createRecuperaDatiDaPDFResponseType() {

	return new RecuperaDatiDaPDFResponseType();
    }

    /**
     * Create an instance of {@link RecuperaDatiDaPDFRequestType }
     * 
     */
    public RecuperaDatiDaPDFRequestType createRecuperaDatiDaPDFRequestType() {

	return new RecuperaDatiDaPDFRequestType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RecuperaDatiDaPDFRequestType }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://gruppoinit.it/schemas/messages/pdfutils", name = "RecuperaDatiDaPDFRequest")
    public JAXBElement<RecuperaDatiDaPDFRequestType> createRecuperaDatiDaPDFRequest(RecuperaDatiDaPDFRequestType value) {

	return new JAXBElement<RecuperaDatiDaPDFRequestType>(_RecuperaDatiDaPDFRequest_QNAME, RecuperaDatiDaPDFRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RecuperaDatiDaPDFResponseType }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://gruppoinit.it/schemas/messages/pdfutils", name = "RecuperaDatiDaPDFResponse")
    public JAXBElement<RecuperaDatiDaPDFResponseType> createRecuperaDatiDaPDFResponse(RecuperaDatiDaPDFResponseType value) {

	return new JAXBElement<RecuperaDatiDaPDFResponseType>(_RecuperaDatiDaPDFResponse_QNAME, RecuperaDatiDaPDFResponseType.class, null, value);
    }
}
