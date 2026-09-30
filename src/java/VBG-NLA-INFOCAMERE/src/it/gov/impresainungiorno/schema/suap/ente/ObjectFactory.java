
package it.gov.impresainungiorno.schema.suap.ente;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.gov.impresainungiorno.schema.suap.ente package. 
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

    private final static QName _CooperazioneEnteSuap_QNAME = new QName("http://www.impresainungiorno.gov.it/schema/suap/ente", "cooperazione-ente-suap");
    private final static QName _CooperazioneSuapEnte_QNAME = new QName("http://www.impresainungiorno.gov.it/schema/suap/ente", "cooperazione-suap-ente");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.gov.impresainungiorno.schema.suap.ente
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CooperazioneSUAPEnte }
     * 
     */
    public CooperazioneSUAPEnte createCooperazioneSUAPEnte() {
        return new CooperazioneSUAPEnte();
    }

    /**
     * Create an instance of {@link CooperazioneEnteSUAP }
     * 
     */
    public CooperazioneEnteSUAP createCooperazioneEnteSUAP() {
        return new CooperazioneEnteSUAP();
    }

    /**
     * Create an instance of {@link OggettoCooperazione }
     * 
     */
    public OggettoCooperazione createOggettoCooperazione() {
        return new OggettoCooperazione();
    }

    /**
     * Create an instance of {@link AllegatoCooperazione }
     * 
     */
    public AllegatoCooperazione createAllegatoCooperazione() {
        return new AllegatoCooperazione();
    }

    /**
     * Create an instance of {@link CooperazioneSUAPEnte.InfoSchema }
     * 
     */
    public CooperazioneSUAPEnte.InfoSchema createCooperazioneSUAPEnteInfoSchema() {
        return new CooperazioneSUAPEnte.InfoSchema();
    }

    /**
     * Create an instance of {@link CooperazioneSUAPEnte.Intestazione }
     * 
     */
    public CooperazioneSUAPEnte.Intestazione createCooperazioneSUAPEnteIntestazione() {
        return new CooperazioneSUAPEnte.Intestazione();
    }

    /**
     * Create an instance of {@link CooperazioneEnteSUAP.InfoSchema }
     * 
     */
    public CooperazioneEnteSUAP.InfoSchema createCooperazioneEnteSUAPInfoSchema() {
        return new CooperazioneEnteSUAP.InfoSchema();
    }

    /**
     * Create an instance of {@link CooperazioneEnteSUAP.Intestazione }
     * 
     */
    public CooperazioneEnteSUAP.Intestazione createCooperazioneEnteSUAPIntestazione() {
        return new CooperazioneEnteSUAP.Intestazione();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CooperazioneEnteSUAP }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CooperazioneEnteSUAP }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.impresainungiorno.gov.it/schema/suap/ente", name = "cooperazione-ente-suap")
    public JAXBElement<CooperazioneEnteSUAP> createCooperazioneEnteSuap(CooperazioneEnteSUAP value) {
        return new JAXBElement<CooperazioneEnteSUAP>(_CooperazioneEnteSuap_QNAME, CooperazioneEnteSUAP.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CooperazioneSUAPEnte }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CooperazioneSUAPEnte }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.impresainungiorno.gov.it/schema/suap/ente", name = "cooperazione-suap-ente")
    public JAXBElement<CooperazioneSUAPEnte> createCooperazioneSuapEnte(CooperazioneSUAPEnte value) {
        return new JAXBElement<CooperazioneSUAPEnte>(_CooperazioneSuapEnte_QNAME, CooperazioneSUAPEnte.class, null, value);
    }

}
