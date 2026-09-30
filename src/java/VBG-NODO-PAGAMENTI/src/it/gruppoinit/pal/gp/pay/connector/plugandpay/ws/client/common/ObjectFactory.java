
package it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.common;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.common package. 
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

    private final static QName _TipoPagatore_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayCommon", "TipoPagatore");
    private final static QName _Servizio_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayCommon", "Servizio");
    private final static QName _ServizioDescrizione_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayCommon", "Descrizione");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.common
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Servizio }
     * 
     */
    public Servizio createServizio() {
        return new Servizio();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TipoPagatore }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayCommon", name = "TipoPagatore")
    public JAXBElement<TipoPagatore> createTipoPagatore(TipoPagatore value) {
        return new JAXBElement<TipoPagatore>(_TipoPagatore_QNAME, TipoPagatore.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Servizio }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayCommon", name = "Servizio")
    public JAXBElement<Servizio> createServizio(Servizio value) {
        return new JAXBElement<Servizio>(_Servizio_QNAME, Servizio.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayCommon", name = "Descrizione", scope = Servizio.class)
    public JAXBElement<String> createServizioDescrizione(String value) {
        return new JAXBElement<String>(_ServizioDescrizione_QNAME, String.class, Servizio.class, value);
    }

}
