
package it.gruppoinit.pal.gp.pay.connector.easypa.ws.schema;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.gruppoinit.pal.gp.pay.connector.easypa.ws.schema package. 
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

    private final static QName _PaInviaCarrelloPosizioniRequest_QNAME = new QName("http://services.sia.eu/", "paInviaCarrelloPosizioniRequest");
    private final static QName _PaInviaCarrelloPosizioniResponse_QNAME = new QName("http://services.sia.eu/", "paInviaCarrelloPosizioniResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.gruppoinit.pal.gp.pay.connector.easypa.ws.schema
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PaInviaCarrelloPosizioniRequest }
     * 
     */
    public PaInviaCarrelloPosizioniRequest createPaInviaCarrelloPosizioniRequest() {
        return new PaInviaCarrelloPosizioniRequest();
    }

    /**
     * Create an instance of {@link PaInviaCarrelloPosizioniResponse }
     * 
     */
    public PaInviaCarrelloPosizioniResponse createPaInviaCarrelloPosizioniResponse() {
        return new PaInviaCarrelloPosizioniResponse();
    }

    /**
     * Create an instance of {@link PaInviaCarrelloPosizioniOutputType }
     * 
     */
    public PaInviaCarrelloPosizioniOutputType createPaInviaCarrelloPosizioniOutputType() {
        return new PaInviaCarrelloPosizioniOutputType();
    }

    /**
     * Create an instance of {@link MarcaBolloDigitaleType }
     * 
     */
    public MarcaBolloDigitaleType createMarcaBolloDigitaleType() {
        return new MarcaBolloDigitaleType();
    }

    /**
     * Create an instance of {@link PaInviaCarrelloPosizioniListaType }
     * 
     */
    public PaInviaCarrelloPosizioniListaType createPaInviaCarrelloPosizioniListaType() {
        return new PaInviaCarrelloPosizioniListaType();
    }

    /**
     * Create an instance of {@link DocumentoMBDType }
     * 
     */
    public DocumentoMBDType createDocumentoMBDType() {
        return new DocumentoMBDType();
    }

    /**
     * Create an instance of {@link DatiSingoloVersamentoListaType }
     * 
     */
    public DatiSingoloVersamentoListaType createDatiSingoloVersamentoListaType() {
        return new DatiSingoloVersamentoListaType();
    }

    /**
     * Create an instance of {@link IuvListaType }
     * 
     */
    public IuvListaType createIuvListaType() {
        return new IuvListaType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PaInviaCarrelloPosizioniRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.sia.eu/", name = "paInviaCarrelloPosizioniRequest")
    public JAXBElement<PaInviaCarrelloPosizioniRequest> createPaInviaCarrelloPosizioniRequest(PaInviaCarrelloPosizioniRequest value) {
        return new JAXBElement<PaInviaCarrelloPosizioniRequest>(_PaInviaCarrelloPosizioniRequest_QNAME, PaInviaCarrelloPosizioniRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PaInviaCarrelloPosizioniResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.sia.eu/", name = "paInviaCarrelloPosizioniResponse")
    public JAXBElement<PaInviaCarrelloPosizioniResponse> createPaInviaCarrelloPosizioniResponse(PaInviaCarrelloPosizioniResponse value) {
        return new JAXBElement<PaInviaCarrelloPosizioniResponse>(_PaInviaCarrelloPosizioniResponse_QNAME, PaInviaCarrelloPosizioniResponse.class, null, value);
    }

}
