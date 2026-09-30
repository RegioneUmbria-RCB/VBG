
package it.gruppoinit.pal.gp.backoffice.schemas.messages.rateizzazioni;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.gruppoinit.pal.gp.backoffice.schemas.messages.rateizzazioni package. 
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


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.gruppoinit.pal.gp.backoffice.schemas.messages.rateizzazioni
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RateizzazioniResponse }
     * 
     */
    public RateizzazioniResponse createRateizzazioniResponse() {
        return new RateizzazioniResponse();
    }

    /**
     * Create an instance of {@link ImportoRateizzatoXML }
     * 
     */
    public ImportoRateizzatoXML createImportoRateizzatoXML() {
        return new ImportoRateizzatoXML();
    }

    /**
     * Create an instance of {@link RateizzazioniRequest }
     * 
     */
    public RateizzazioniRequest createRateizzazioniRequest() {
        return new RateizzazioniRequest();
    }

}
