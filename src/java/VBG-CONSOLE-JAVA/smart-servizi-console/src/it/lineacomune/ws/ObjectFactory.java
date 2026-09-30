
package it.lineacomune.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.lineacomune.ws package. 
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

    private final static QName _CreateAccessoResponse_QNAME = new QName("http://ws.lineacomune.it/", "createAccessoResponse");
    private final static QName _CreateAccesso_QNAME = new QName("http://ws.lineacomune.it/", "createAccesso");
    private final static QName _CreateTransazioneResponse_QNAME = new QName("http://ws.lineacomune.it/", "createTransazioneResponse");
    private final static QName _CreateTransazione_QNAME = new QName("http://ws.lineacomune.it/", "createTransazione");
    private final static QName _CreatePratica_QNAME = new QName("http://ws.lineacomune.it/", "createPratica");
    private final static QName _CreatePraticaResponse_QNAME = new QName("http://ws.lineacomune.it/", "createPraticaResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.lineacomune.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CreateAccessoResponse }
     * 
     */
    public CreateAccessoResponse createCreateAccessoResponse() {
        return new CreateAccessoResponse();
    }

    /**
     * Create an instance of {@link CreateTransazione }
     * 
     */
    public CreateTransazione createCreateTransazione() {
        return new CreateTransazione();
    }

    /**
     * Create an instance of {@link CreateTransazioneResponse }
     * 
     */
    public CreateTransazioneResponse createCreateTransazioneResponse() {
        return new CreateTransazioneResponse();
    }

    /**
     * Create an instance of {@link CreateAccesso }
     * 
     */
    public CreateAccesso createCreateAccesso() {
        return new CreateAccesso();
    }

    /**
     * Create an instance of {@link CreatePraticaResponse }
     * 
     */
    public CreatePraticaResponse createCreatePraticaResponse() {
        return new CreatePraticaResponse();
    }

    /**
     * Create an instance of {@link CreatePratica }
     * 
     */
    public CreatePratica createCreatePratica() {
        return new CreatePratica();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateAccessoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.lineacomune.it/", name = "createAccessoResponse")
    public JAXBElement<CreateAccessoResponse> createCreateAccessoResponse(CreateAccessoResponse value) {
        return new JAXBElement<CreateAccessoResponse>(_CreateAccessoResponse_QNAME, CreateAccessoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateAccesso }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.lineacomune.it/", name = "createAccesso")
    public JAXBElement<CreateAccesso> createCreateAccesso(CreateAccesso value) {
        return new JAXBElement<CreateAccesso>(_CreateAccesso_QNAME, CreateAccesso.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateTransazioneResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.lineacomune.it/", name = "createTransazioneResponse")
    public JAXBElement<CreateTransazioneResponse> createCreateTransazioneResponse(CreateTransazioneResponse value) {
        return new JAXBElement<CreateTransazioneResponse>(_CreateTransazioneResponse_QNAME, CreateTransazioneResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateTransazione }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.lineacomune.it/", name = "createTransazione")
    public JAXBElement<CreateTransazione> createCreateTransazione(CreateTransazione value) {
        return new JAXBElement<CreateTransazione>(_CreateTransazione_QNAME, CreateTransazione.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreatePratica }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.lineacomune.it/", name = "createPratica")
    public JAXBElement<CreatePratica> createCreatePratica(CreatePratica value) {
        return new JAXBElement<CreatePratica>(_CreatePratica_QNAME, CreatePratica.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreatePraticaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.lineacomune.it/", name = "createPraticaResponse")
    public JAXBElement<CreatePraticaResponse> createCreatePraticaResponse(CreatePraticaResponse value) {
        return new JAXBElement<CreatePraticaResponse>(_CreatePraticaResponse_QNAME, CreatePraticaResponse.class, null, value);
    }

}
