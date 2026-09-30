
package it.gruppoinit.sigepro.schemas.messages.eventi;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import it.gruppoinit.sigepro.schemas.messages.base.EsitoOperazioneType;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.gruppoinit.sigepro.schemas.messages.eventi package. 
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

    private final static QName _EventoInsertResponse_QNAME = new QName("http://gruppoinit.it/sigepro/schemas/messages/eventi", "EventoInsertResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.gruppoinit.sigepro.schemas.messages.eventi
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link EventoIstanzaInsertRequest }
     * 
     */
    public EventoIstanzaInsertRequest createEventoIstanzaInsertRequest() {
        return new EventoIstanzaInsertRequest();
    }

    /**
     * Create an instance of {@link EventoMovimentoInsertRequest }
     * 
     */
    public EventoMovimentoInsertRequest createEventoMovimentoInsertRequest() {
        return new EventoMovimentoInsertRequest();
    }

    /**
     * Create an instance of {@link EventoBackofficeInsertRequest }
     * 
     */
    public EventoBackofficeInsertRequest createEventoBackofficeInsertRequest() {
        return new EventoBackofficeInsertRequest();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EsitoOperazioneType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://gruppoinit.it/sigepro/schemas/messages/eventi", name = "EventoInsertResponse")
    public JAXBElement<EsitoOperazioneType> createEventoInsertResponse(EsitoOperazioneType value) {
        return new JAXBElement<EsitoOperazioneType>(_EventoInsertResponse_QNAME, EsitoOperazioneType.class, null, value);
    }

}
