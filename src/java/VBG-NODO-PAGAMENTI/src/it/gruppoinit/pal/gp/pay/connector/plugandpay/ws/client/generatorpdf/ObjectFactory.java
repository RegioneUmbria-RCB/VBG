
package it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.generatorpdf;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.generatorpdf package. 
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

    private final static QName _RichiestaCreaAvvisoPdf_QNAME = new QName("http://e-fil.eu/GeneratorPdf", "RichiestaCreaAvvisoPdf");
    private final static QName _GeneratorPdfBytesAuthenticatedRequestBase_QNAME = new QName("http://e-fil.eu/GeneratorPdf", "GeneratorPdfBytesAuthenticatedRequestBase");
    private final static QName _RispostaCreaAvvisoPdf_QNAME = new QName("http://e-fil.eu/GeneratorPdf", "RispostaCreaAvvisoPdf");
    private final static QName _SoapFaultErroreInterno_QNAME = new QName("http://e-fil.eu/GeneratorPdf", "SoapFaultErroreInterno");
    private final static QName _SoapFault_QNAME = new QName("http://e-fil.eu/GeneratorPdf", "SoapFault");
    private final static QName _RispostaCreaRTPdf_QNAME = new QName("http://e-fil.eu/GeneratorPdf", "RispostaCreaRTPdf");
    private final static QName _RichiestaCreaRTPdf_QNAME = new QName("http://schemas.datacontract.org/2004/07/GeneratorPdfBytes.Service.Contracts", "RichiestaCreaRTPdf");
    private final static QName _CreatePdfAvvisoRequest_QNAME = new QName("http://e-fil.eu/GeneratorPdf", "request");
    private final static QName _CreatePdfAvvisoResponseCreatePdfAvvisoResult_QNAME = new QName("http://e-fil.eu/GeneratorPdf", "CreatePdfAvvisoResult");
    private final static QName _CreatePdfRTResponseCreatePdfRTResult_QNAME = new QName("http://e-fil.eu/GeneratorPdf", "CreatePdfRTResult");
    private final static QName _RispostaCreaRTPdfRTPdf_QNAME = new QName("http://e-fil.eu/GeneratorPdf", "RTPdf");
    private final static QName _RichiestaCreaRTPdfCodiceFiscalePartitaIva_QNAME = new QName("http://schemas.datacontract.org/2004/07/GeneratorPdfBytes.Service.Contracts", "CodiceFiscalePartitaIva");
    private final static QName _RichiestaCreaRTPdfCodiceEnteCreditore_QNAME = new QName("http://schemas.datacontract.org/2004/07/GeneratorPdfBytes.Service.Contracts", "codiceEnteCreditore");
    private final static QName _RichiestaCreaRTPdfIdentificativoPosizione_QNAME = new QName("http://schemas.datacontract.org/2004/07/GeneratorPdfBytes.Service.Contracts", "identificativoPosizione");
    private final static QName _RispostaCreaAvvisoPdfAvvisoPdf_QNAME = new QName("http://e-fil.eu/GeneratorPdf", "AvvisoPdf");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.generatorpdf
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CreatePdfAvviso }
     * 
     */
    public CreatePdfAvviso createCreatePdfAvviso() {
        return new CreatePdfAvviso();
    }

    /**
     * Create an instance of {@link RichiestaCreaAvvisoPdf }
     * 
     */
    public RichiestaCreaAvvisoPdf createRichiestaCreaAvvisoPdf() {
        return new RichiestaCreaAvvisoPdf();
    }

    /**
     * Create an instance of {@link GeneratorPdfBytesAuthenticatedRequestBase }
     * 
     */
    public GeneratorPdfBytesAuthenticatedRequestBase createGeneratorPdfBytesAuthenticatedRequestBase() {
        return new GeneratorPdfBytesAuthenticatedRequestBase();
    }

    /**
     * Create an instance of {@link CreatePdfAvvisoResponse }
     * 
     */
    public CreatePdfAvvisoResponse createCreatePdfAvvisoResponse() {
        return new CreatePdfAvvisoResponse();
    }

    /**
     * Create an instance of {@link RispostaCreaAvvisoPdf }
     * 
     */
    public RispostaCreaAvvisoPdf createRispostaCreaAvvisoPdf() {
        return new RispostaCreaAvvisoPdf();
    }

    /**
     * Create an instance of {@link SoapFaultErroreInterno }
     * 
     */
    public SoapFaultErroreInterno createSoapFaultErroreInterno() {
        return new SoapFaultErroreInterno();
    }

    /**
     * Create an instance of {@link SoapFault }
     * 
     */
    public SoapFault createSoapFault() {
        return new SoapFault();
    }

    /**
     * Create an instance of {@link CreatePdfRT }
     * 
     */
    public CreatePdfRT createCreatePdfRT() {
        return new CreatePdfRT();
    }

    /**
     * Create an instance of {@link RichiestaCreaRTPdf }
     * 
     */
    public RichiestaCreaRTPdf createRichiestaCreaRTPdf() {
        return new RichiestaCreaRTPdf();
    }

    /**
     * Create an instance of {@link CreatePdfRTResponse }
     * 
     */
    public CreatePdfRTResponse createCreatePdfRTResponse() {
        return new CreatePdfRTResponse();
    }

    /**
     * Create an instance of {@link RispostaCreaRTPdf }
     * 
     */
    public RispostaCreaRTPdf createRispostaCreaRTPdf() {
        return new RispostaCreaRTPdf();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RichiestaCreaAvvisoPdf }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/GeneratorPdf", name = "RichiestaCreaAvvisoPdf")
    public JAXBElement<RichiestaCreaAvvisoPdf> createRichiestaCreaAvvisoPdf(RichiestaCreaAvvisoPdf value) {
        return new JAXBElement<RichiestaCreaAvvisoPdf>(_RichiestaCreaAvvisoPdf_QNAME, RichiestaCreaAvvisoPdf.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GeneratorPdfBytesAuthenticatedRequestBase }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/GeneratorPdf", name = "GeneratorPdfBytesAuthenticatedRequestBase")
    public JAXBElement<GeneratorPdfBytesAuthenticatedRequestBase> createGeneratorPdfBytesAuthenticatedRequestBase(GeneratorPdfBytesAuthenticatedRequestBase value) {
        return new JAXBElement<GeneratorPdfBytesAuthenticatedRequestBase>(_GeneratorPdfBytesAuthenticatedRequestBase_QNAME, GeneratorPdfBytesAuthenticatedRequestBase.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RispostaCreaAvvisoPdf }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/GeneratorPdf", name = "RispostaCreaAvvisoPdf")
    public JAXBElement<RispostaCreaAvvisoPdf> createRispostaCreaAvvisoPdf(RispostaCreaAvvisoPdf value) {
        return new JAXBElement<RispostaCreaAvvisoPdf>(_RispostaCreaAvvisoPdf_QNAME, RispostaCreaAvvisoPdf.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SoapFaultErroreInterno }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/GeneratorPdf", name = "SoapFaultErroreInterno")
    public JAXBElement<SoapFaultErroreInterno> createSoapFaultErroreInterno(SoapFaultErroreInterno value) {
        return new JAXBElement<SoapFaultErroreInterno>(_SoapFaultErroreInterno_QNAME, SoapFaultErroreInterno.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SoapFault }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/GeneratorPdf", name = "SoapFault")
    public JAXBElement<SoapFault> createSoapFault(SoapFault value) {
        return new JAXBElement<SoapFault>(_SoapFault_QNAME, SoapFault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RispostaCreaRTPdf }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/GeneratorPdf", name = "RispostaCreaRTPdf")
    public JAXBElement<RispostaCreaRTPdf> createRispostaCreaRTPdf(RispostaCreaRTPdf value) {
        return new JAXBElement<RispostaCreaRTPdf>(_RispostaCreaRTPdf_QNAME, RispostaCreaRTPdf.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RichiestaCreaRTPdf }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/GeneratorPdfBytes.Service.Contracts", name = "RichiestaCreaRTPdf")
    public JAXBElement<RichiestaCreaRTPdf> createRichiestaCreaRTPdf(RichiestaCreaRTPdf value) {
        return new JAXBElement<RichiestaCreaRTPdf>(_RichiestaCreaRTPdf_QNAME, RichiestaCreaRTPdf.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RichiestaCreaAvvisoPdf }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/GeneratorPdf", name = "request", scope = CreatePdfAvviso.class)
    public JAXBElement<RichiestaCreaAvvisoPdf> createCreatePdfAvvisoRequest(RichiestaCreaAvvisoPdf value) {
        return new JAXBElement<RichiestaCreaAvvisoPdf>(_CreatePdfAvvisoRequest_QNAME, RichiestaCreaAvvisoPdf.class, CreatePdfAvviso.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RispostaCreaAvvisoPdf }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/GeneratorPdf", name = "CreatePdfAvvisoResult", scope = CreatePdfAvvisoResponse.class)
    public JAXBElement<RispostaCreaAvvisoPdf> createCreatePdfAvvisoResponseCreatePdfAvvisoResult(RispostaCreaAvvisoPdf value) {
        return new JAXBElement<RispostaCreaAvvisoPdf>(_CreatePdfAvvisoResponseCreatePdfAvvisoResult_QNAME, RispostaCreaAvvisoPdf.class, CreatePdfAvvisoResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RichiestaCreaRTPdf }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/GeneratorPdf", name = "request", scope = CreatePdfRT.class)
    public JAXBElement<RichiestaCreaRTPdf> createCreatePdfRTRequest(RichiestaCreaRTPdf value) {
        return new JAXBElement<RichiestaCreaRTPdf>(_CreatePdfAvvisoRequest_QNAME, RichiestaCreaRTPdf.class, CreatePdfRT.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RispostaCreaRTPdf }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/GeneratorPdf", name = "CreatePdfRTResult", scope = CreatePdfRTResponse.class)
    public JAXBElement<RispostaCreaRTPdf> createCreatePdfRTResponseCreatePdfRTResult(RispostaCreaRTPdf value) {
        return new JAXBElement<RispostaCreaRTPdf>(_CreatePdfRTResponseCreatePdfRTResult_QNAME, RispostaCreaRTPdf.class, CreatePdfRTResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/GeneratorPdf", name = "RTPdf", scope = RispostaCreaRTPdf.class)
    public JAXBElement<byte[]> createRispostaCreaRTPdfRTPdf(byte[] value) {
        return new JAXBElement<byte[]>(_RispostaCreaRTPdfRTPdf_QNAME, byte[].class, RispostaCreaRTPdf.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/GeneratorPdfBytes.Service.Contracts", name = "CodiceFiscalePartitaIva", scope = RichiestaCreaRTPdf.class)
    public JAXBElement<String> createRichiestaCreaRTPdfCodiceFiscalePartitaIva(String value) {
        return new JAXBElement<String>(_RichiestaCreaRTPdfCodiceFiscalePartitaIva_QNAME, String.class, RichiestaCreaRTPdf.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/GeneratorPdfBytes.Service.Contracts", name = "codiceEnteCreditore", scope = RichiestaCreaRTPdf.class)
    public JAXBElement<String> createRichiestaCreaRTPdfCodiceEnteCreditore(String value) {
        return new JAXBElement<String>(_RichiestaCreaRTPdfCodiceEnteCreditore_QNAME, String.class, RichiestaCreaRTPdf.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/GeneratorPdfBytes.Service.Contracts", name = "identificativoPosizione", scope = RichiestaCreaRTPdf.class)
    public JAXBElement<String> createRichiestaCreaRTPdfIdentificativoPosizione(String value) {
        return new JAXBElement<String>(_RichiestaCreaRTPdfIdentificativoPosizione_QNAME, String.class, RichiestaCreaRTPdf.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/GeneratorPdf", name = "AvvisoPdf", scope = RispostaCreaAvvisoPdf.class)
    public JAXBElement<byte[]> createRispostaCreaAvvisoPdfAvvisoPdf(byte[] value) {
        return new JAXBElement<byte[]>(_RispostaCreaAvvisoPdfAvvisoPdf_QNAME, byte[].class, RispostaCreaAvvisoPdf.class, ((byte[]) value));
    }

}
