
package it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.include;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.include package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.include
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CIP }
     * 
     */
    public CIP createCIP() {
        return new CIP();
    }

    /**
     * Create an instance of {@link Allegato }
     * 
     */
    public Allegato createAllegato() {
        return new Allegato();
    }

    /**
     * Create an instance of {@link CoordinateBancarie }
     * 
     */
    public CoordinateBancarie createCoordinateBancarie() {
        return new CoordinateBancarie();
    }

    /**
     * Create an instance of {@link DettaglioCanalePagamento }
     * 
     */
    public DettaglioCanalePagamento createDettaglioCanalePagamento() {
        return new DettaglioCanalePagamento();
    }

}
