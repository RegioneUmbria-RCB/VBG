
package it.gruppoinit.pal.gp.backoffice.schemas.messages.autorizzazioniaccessi;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.gruppoinit.sigepro.schemas.messages.autorizzazioniaccessi package. 
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

    private final static QName _RicercaAutorizzazioniAccessiResponse_QNAME = new QName("http://gruppoinit.it/sigepro/schemas/messages/autorizzazioniaccessi", "RicercaAutorizzazioniAccessiResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.gruppoinit.sigepro.schemas.messages.autorizzazioniaccessi
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RicercaAutorizzazioniAccessiResponseType }
     * 
     */
    public RicercaAutorizzazioniAccessiResponseType createRicercaAutorizzazioniAccessiResponseType() {
        return new RicercaAutorizzazioniAccessiResponseType();
    }

    /**
     * Create an instance of {@link RicercaAutorizzazioniAccessiRequest }
     * 
     */
    public RicercaAutorizzazioniAccessiRequest createRicercaAutorizzazioniAccessiRequest() {
        return new RicercaAutorizzazioniAccessiRequest();
    }

    /**
     * Create an instance of {@link OperazioneAutorizzazioneType }
     * 
     */
    public OperazioneAutorizzazioneType createOperazioneAutorizzazioneType() {
        return new OperazioneAutorizzazioneType();
    }

    /**
     * Create an instance of {@link DatiAutorizzazioneType }
     * 
     */
    public DatiAutorizzazioneType createDatiAutorizzazioneType() {
        return new DatiAutorizzazioneType();
    }

    /**
     * Create an instance of {@link OperazioniPermesseType }
     * 
     */
    public OperazioniPermesseType createOperazioniPermesseType() {
        return new OperazioniPermesseType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RicercaAutorizzazioniAccessiResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://gruppoinit.it/sigepro/schemas/messages/autorizzazioniaccessi", name = "RicercaAutorizzazioniAccessiResponse")
    public JAXBElement<RicercaAutorizzazioniAccessiResponseType> createRicercaAutorizzazioniAccessiResponse(RicercaAutorizzazioniAccessiResponseType value) {
        return new JAXBElement<RicercaAutorizzazioniAccessiResponseType>(_RicercaAutorizzazioniAccessiResponse_QNAME, RicercaAutorizzazioniAccessiResponseType.class, null, value);
    }

}
