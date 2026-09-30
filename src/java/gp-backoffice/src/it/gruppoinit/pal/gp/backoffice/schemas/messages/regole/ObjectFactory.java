
package it.gruppoinit.pal.gp.backoffice.schemas.messages.regole;

import it.gruppoinit.pal.gp.backoffice.schemas.messages.base.AllegatoBaseType;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.base.ErroreBackofficeType;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.base.EsitoOperazioneType;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.gruppoinit.pal.gp.backoffice.schemas.messages.regole package. 
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

    private final static QName _GetRegolaResponse_QNAME = new QName("http://gruppoinit.it/sigepro/schemas/messages/regole", "GetRegolaResponse");
    private final static QName _GetParametroRegolaResponse_QNAME = new QName("http://gruppoinit.it/sigepro/schemas/messages/regole", "GetParametroRegolaResponse");
    private final static QName _GetParametroRegolaRequest_QNAME = new QName("http://gruppoinit.it/sigepro/schemas/messages/regole", "GetParametroRegolaRequest");
    private final static QName _GetComuniESoftwarePerRegolaRequest_QNAME = new QName("http://gruppoinit.it/sigepro/schemas/messages/regole", "GetComuniESoftwarePerRegolaRequest");
    private final static QName _GetComuniESoftwarePerRegolaResponse_QNAME = new QName("http://gruppoinit.it/sigepro/schemas/messages/regole", "GetComuniESoftwarePerRegolaResponse");
    private final static QName _GetRegolaRequest_QNAME = new QName("http://gruppoinit.it/sigepro/schemas/messages/regole", "GetRegolaRequest");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.gruppoinit.pal.gp.backoffice.schemas.messages.regole
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RegolaResponse }
     * 
     */
    public RegolaResponse createRegolaResponse() {
        return new RegolaResponse();
    }

    /**
     * Create an instance of {@link ParametroRegolaResponse }
     * 
     */
    public ParametroRegolaResponse createParametroRegolaResponse() {
        return new ParametroRegolaResponse();
    }

    /**
     * Create an instance of {@link ParametroRegolaRequest }
     * 
     */
    public ParametroRegolaRequest createParametroRegolaRequest() {
        return new ParametroRegolaRequest();
    }

    /**
     * Create an instance of {@link ComuniESoftwarePerRegolaRequest }
     * 
     */
    public ComuniESoftwarePerRegolaRequest createComuniESoftwarePerRegolaRequest() {
        return new ComuniESoftwarePerRegolaRequest();
    }

    /**
     * Create an instance of {@link ComuniESoftwarePerRegolaResponse }
     * 
     */
    public ComuniESoftwarePerRegolaResponse createComuniESoftwarePerRegolaResponse() {
        return new ComuniESoftwarePerRegolaResponse();
    }

    /**
     * Create an instance of {@link RegolaRequest }
     * 
     */
    public RegolaRequest createRegolaRequest() {
        return new RegolaRequest();
    }

    /**
     * Create an instance of {@link ComuniESoftwareType }
     * 
     */
    public ComuniESoftwareType createComuniESoftwareType() {
        return new ComuniESoftwareType();
    }

    /**
     * Create an instance of {@link ParametroType }
     * 
     */
    public ParametroType createParametroType() {
        return new ParametroType();
    }

    /**
     * Create an instance of {@link EsitoOperazioneType }
     * 
     */
    public EsitoOperazioneType createEsitoOperazioneType() {
        return new EsitoOperazioneType();
    }

    /**
     * Create an instance of {@link ErroreBackofficeType }
     * 
     */
    public ErroreBackofficeType createErroreBackofficeType() {
        return new ErroreBackofficeType();
    }

    /**
     * Create an instance of {@link AllegatoBaseType }
     * 
     */
    public AllegatoBaseType createAllegatoBaseType() {
        return new AllegatoBaseType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegolaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://gruppoinit.it/sigepro/schemas/messages/regole", name = "GetRegolaResponse")
    public JAXBElement<RegolaResponse> createGetRegolaResponse(RegolaResponse value) {
        return new JAXBElement<RegolaResponse>(_GetRegolaResponse_QNAME, RegolaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ParametroRegolaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://gruppoinit.it/sigepro/schemas/messages/regole", name = "GetParametroRegolaResponse")
    public JAXBElement<ParametroRegolaResponse> createGetParametroRegolaResponse(ParametroRegolaResponse value) {
        return new JAXBElement<ParametroRegolaResponse>(_GetParametroRegolaResponse_QNAME, ParametroRegolaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ParametroRegolaRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://gruppoinit.it/sigepro/schemas/messages/regole", name = "GetParametroRegolaRequest")
    public JAXBElement<ParametroRegolaRequest> createGetParametroRegolaRequest(ParametroRegolaRequest value) {
        return new JAXBElement<ParametroRegolaRequest>(_GetParametroRegolaRequest_QNAME, ParametroRegolaRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ComuniESoftwarePerRegolaRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://gruppoinit.it/sigepro/schemas/messages/regole", name = "GetComuniESoftwarePerRegolaRequest")
    public JAXBElement<ComuniESoftwarePerRegolaRequest> createGetComuniESoftwarePerRegolaRequest(ComuniESoftwarePerRegolaRequest value) {
        return new JAXBElement<ComuniESoftwarePerRegolaRequest>(_GetComuniESoftwarePerRegolaRequest_QNAME, ComuniESoftwarePerRegolaRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ComuniESoftwarePerRegolaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://gruppoinit.it/sigepro/schemas/messages/regole", name = "GetComuniESoftwarePerRegolaResponse")
    public JAXBElement<ComuniESoftwarePerRegolaResponse> createGetComuniESoftwarePerRegolaResponse(ComuniESoftwarePerRegolaResponse value) {
        return new JAXBElement<ComuniESoftwarePerRegolaResponse>(_GetComuniESoftwarePerRegolaResponse_QNAME, ComuniESoftwarePerRegolaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegolaRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://gruppoinit.it/sigepro/schemas/messages/regole", name = "GetRegolaRequest")
    public JAXBElement<RegolaRequest> createGetRegolaRequest(RegolaRequest value) {
        return new JAXBElement<RegolaRequest>(_GetRegolaRequest_QNAME, RegolaRequest.class, null, value);
    }

}
