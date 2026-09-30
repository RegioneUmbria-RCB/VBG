
package it.gruppoinit.pal.gp.backoffice.schemas.messages.movimenti;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.gruppoinit.pal.gp.backoffice.schemas.messages.movimenti package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.gruppoinit.pal.gp.backoffice.schemas.messages.movimenti
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link MovimentiAllegatiInsertRequest }
     * 
     */
    public MovimentiAllegatiInsertRequest createMovimentiAllegatiInsertRequest() {
        return new MovimentiAllegatiInsertRequest();
    }

    /**
     * Create an instance of {@link MovimentiAllegatiInsertResponse }
     * 
     */
    public MovimentiAllegatiInsertResponse createMovimentiAllegatiInsertResponse() {
        return new MovimentiAllegatiInsertResponse();
    }
    
    /**
     * Create an instance of {@link MovimentiDownloadZipLogicoRequest }
     * 
     */
    public MovimentiDownloadZipLogicoRequest createMovimentiDownloadZipLogicoRequest() {
        return new MovimentiDownloadZipLogicoRequest();
    }

    /**
     * Create an instance of {@link MovimentiDownloadZipLogicoResponse }
     * 
     */
    public MovimentiDownloadZipLogicoResponse createMovimentiDownloadZipLogicoResponse() {
        return new MovimentiDownloadZipLogicoResponse();
    }

}
