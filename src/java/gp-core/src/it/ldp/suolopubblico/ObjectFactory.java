
package it.ldp.suolopubblico;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.ldp.suolopubblico package. 
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

    private final static QName _ComplexTypeAreaArray_QNAME = new QName("https://ws.ldpgis.it/", "ComplexTypeAreaArray");
    private final static QName _ComplexTypeStringa_QNAME = new QName("https://ws.ldpgis.it/", "ComplexTypeStringa");
    private final static QName _ComplexTypeStatoOccupazione_QNAME = new QName("https://ws.ldpgis.it/", "ComplexTypeStatoOccupazione");
    private final static QName _ComplexTypeAreeUsoPubblico_QNAME = new QName("https://ws.ldpgis.it/", "ComplexTypeAreeUsoPubblico");
    private final static QName _ComplexTypeStringaArray_QNAME = new QName("https://ws.ldpgis.it/", "ComplexTypeStringaArray");
    private final static QName _ComplexTypePeriodoArray_QNAME = new QName("https://ws.ldpgis.it/", "ComplexTypePeriodoArray");
    private final static QName _ComplexTypeArea_QNAME = new QName("https://ws.ldpgis.it/", "ComplexTypeArea");
    private final static QName _ComplexTypePeriodo_QNAME = new QName("https://ws.ldpgis.it/", "ComplexTypePeriodo");
    private final static QName _ComplexTypePeriodo2Wkt_QNAME = new QName("https://ws.ldpgis.it/", "ComplexTypePeriodo2Wkt");
    private final static QName _ComplexTypePraticaIdentificativi_QNAME = new QName("https://ws.ldpgis.it/", "ComplexTypePraticaIdentificativi");
    private final static QName _ComplexTypePraticaIdentificativiDelete_QNAME = new QName("https://ws.ldpgis.it/", "ComplexTypePraticaIdentificativiDelete");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.ldp.suolopubblico
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ComplexTypePraticaIdentificativiDelete }
     * 
     */
    public ComplexTypePraticaIdentificativiDelete createComplexTypePraticaIdentificativiDelete() {
        return new ComplexTypePraticaIdentificativiDelete();
    }

    /**
     * Create an instance of {@link ComplexTypePraticaIdentificativi }
     * 
     */
    public ComplexTypePraticaIdentificativi createComplexTypePraticaIdentificativi() {
        return new ComplexTypePraticaIdentificativi();
    }

    /**
     * Create an instance of {@link ComplexTypeArea }
     * 
     */
    public ComplexTypeArea createComplexTypeArea() {
        return new ComplexTypeArea();
    }

    /**
     * Create an instance of {@link ComplexTypePeriodo2Wkt }
     * 
     */
    public ComplexTypePeriodo2Wkt createComplexTypePeriodo2Wkt() {
        return new ComplexTypePeriodo2Wkt();
    }

    /**
     * Create an instance of {@link ComplexTypePeriodo }
     * 
     */
    public ComplexTypePeriodo createComplexTypePeriodo() {
        return new ComplexTypePeriodo();
    }

    /**
     * Create an instance of {@link ComplexTypeAreeUsoPubblico }
     * 
     */
    public ComplexTypeAreeUsoPubblico createComplexTypeAreeUsoPubblico() {
        return new ComplexTypeAreeUsoPubblico();
    }

    /**
     * Create an instance of {@link ArrayOfComplexTypePeriodo }
     * 
     */
    public ArrayOfComplexTypePeriodo createArrayOfComplexTypePeriodo() {
        return new ArrayOfComplexTypePeriodo();
    }

    /**
     * Create an instance of {@link ArrayOfComplexTypeStringa }
     * 
     */
    public ArrayOfComplexTypeStringa createArrayOfComplexTypeStringa() {
        return new ArrayOfComplexTypeStringa();
    }

    /**
     * Create an instance of {@link ArrayOfComplexTypeArea }
     * 
     */
    public ArrayOfComplexTypeArea createArrayOfComplexTypeArea() {
        return new ArrayOfComplexTypeArea();
    }

    /**
     * Create an instance of {@link ComplexTypeStatoOccupazione }
     * 
     */
    public ComplexTypeStatoOccupazione createComplexTypeStatoOccupazione() {
        return new ComplexTypeStatoOccupazione();
    }

    /**
     * Create an instance of {@link ComplexTypeStringa }
     * 
     */
    public ComplexTypeStringa createComplexTypeStringa() {
        return new ComplexTypeStringa();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfComplexTypeArea }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://ws.ldpgis.it/", name = "ComplexTypeAreaArray")
    public JAXBElement<ArrayOfComplexTypeArea> createComplexTypeAreaArray(ArrayOfComplexTypeArea value) {
        return new JAXBElement<ArrayOfComplexTypeArea>(_ComplexTypeAreaArray_QNAME, ArrayOfComplexTypeArea.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ComplexTypeStringa }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://ws.ldpgis.it/", name = "ComplexTypeStringa")
    public JAXBElement<ComplexTypeStringa> createComplexTypeStringa(ComplexTypeStringa value) {
        return new JAXBElement<ComplexTypeStringa>(_ComplexTypeStringa_QNAME, ComplexTypeStringa.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ComplexTypeStatoOccupazione }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://ws.ldpgis.it/", name = "ComplexTypeStatoOccupazione")
    public JAXBElement<ComplexTypeStatoOccupazione> createComplexTypeStatoOccupazione(ComplexTypeStatoOccupazione value) {
        return new JAXBElement<ComplexTypeStatoOccupazione>(_ComplexTypeStatoOccupazione_QNAME, ComplexTypeStatoOccupazione.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ComplexTypeAreeUsoPubblico }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://ws.ldpgis.it/", name = "ComplexTypeAreeUsoPubblico")
    public JAXBElement<ComplexTypeAreeUsoPubblico> createComplexTypeAreeUsoPubblico(ComplexTypeAreeUsoPubblico value) {
        return new JAXBElement<ComplexTypeAreeUsoPubblico>(_ComplexTypeAreeUsoPubblico_QNAME, ComplexTypeAreeUsoPubblico.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfComplexTypeStringa }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://ws.ldpgis.it/", name = "ComplexTypeStringaArray")
    public JAXBElement<ArrayOfComplexTypeStringa> createComplexTypeStringaArray(ArrayOfComplexTypeStringa value) {
        return new JAXBElement<ArrayOfComplexTypeStringa>(_ComplexTypeStringaArray_QNAME, ArrayOfComplexTypeStringa.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfComplexTypePeriodo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://ws.ldpgis.it/", name = "ComplexTypePeriodoArray")
    public JAXBElement<ArrayOfComplexTypePeriodo> createComplexTypePeriodoArray(ArrayOfComplexTypePeriodo value) {
        return new JAXBElement<ArrayOfComplexTypePeriodo>(_ComplexTypePeriodoArray_QNAME, ArrayOfComplexTypePeriodo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ComplexTypeArea }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://ws.ldpgis.it/", name = "ComplexTypeArea")
    public JAXBElement<ComplexTypeArea> createComplexTypeArea(ComplexTypeArea value) {
        return new JAXBElement<ComplexTypeArea>(_ComplexTypeArea_QNAME, ComplexTypeArea.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ComplexTypePeriodo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://ws.ldpgis.it/", name = "ComplexTypePeriodo")
    public JAXBElement<ComplexTypePeriodo> createComplexTypePeriodo(ComplexTypePeriodo value) {
        return new JAXBElement<ComplexTypePeriodo>(_ComplexTypePeriodo_QNAME, ComplexTypePeriodo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ComplexTypePeriodo2Wkt }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://ws.ldpgis.it/", name = "ComplexTypePeriodo2Wkt")
    public JAXBElement<ComplexTypePeriodo2Wkt> createComplexTypePeriodo2Wkt(ComplexTypePeriodo2Wkt value) {
        return new JAXBElement<ComplexTypePeriodo2Wkt>(_ComplexTypePeriodo2Wkt_QNAME, ComplexTypePeriodo2Wkt.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ComplexTypePraticaIdentificativi }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://ws.ldpgis.it/", name = "ComplexTypePraticaIdentificativi")
    public JAXBElement<ComplexTypePraticaIdentificativi> createComplexTypePraticaIdentificativi(ComplexTypePraticaIdentificativi value) {
        return new JAXBElement<ComplexTypePraticaIdentificativi>(_ComplexTypePraticaIdentificativi_QNAME, ComplexTypePraticaIdentificativi.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ComplexTypePraticaIdentificativiDelete }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://ws.ldpgis.it/", name = "ComplexTypePraticaIdentificativiDelete")
    public JAXBElement<ComplexTypePraticaIdentificativiDelete> createComplexTypePraticaIdentificativiDelete(ComplexTypePraticaIdentificativiDelete value) {
        return new JAXBElement<ComplexTypePraticaIdentificativiDelete>(_ComplexTypePraticaIdentificativiDelete_QNAME, ComplexTypePraticaIdentificativiDelete.class, null, value);
    }

}
