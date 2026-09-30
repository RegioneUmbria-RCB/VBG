
package it.gruppoinit.wssit;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.gruppoinit.wssit package. 
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

    private final static QName _SitFeatures_QNAME = new QName("http://init.sigepro.it", "SitFeatures");
    private final static QName _ArrayOfString_QNAME = new QName("http://init.sigepro.it", "ArrayOfString");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.gruppoinit.wssit
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetFeaturesResponse }
     * 
     */
    public GetFeaturesResponse createGetFeaturesResponse() {
        return new GetFeaturesResponse();
    }

    /**
     * Create an instance of {@link SitFeatures }
     * 
     */
    public SitFeatures createSitFeatures() {
        return new SitFeatures();
    }

    /**
     * Create an instance of {@link GetDetailFieldResponse }
     * 
     */
    public GetDetailFieldResponse createGetDetailFieldResponse() {
        return new GetDetailFieldResponse();
    }

    /**
     * Create an instance of {@link DetailSit }
     * 
     */
    public DetailSit createDetailSit() {
        return new DetailSit();
    }

    /**
     * Create an instance of {@link GetCampiGestiti }
     * 
     */
    public GetCampiGestiti createGetCampiGestiti() {
        return new GetCampiGestiti();
    }

    /**
     * Create an instance of {@link GetCampiGestitiResponse }
     * 
     */
    public GetCampiGestitiResponse createGetCampiGestitiResponse() {
        return new GetCampiGestitiResponse();
    }

    /**
     * Create an instance of {@link ArrayOfString }
     * 
     */
    public ArrayOfString createArrayOfString() {
        return new ArrayOfString();
    }

    /**
     * Create an instance of {@link GetFeatures }
     * 
     */
    public GetFeatures createGetFeatures() {
        return new GetFeatures();
    }

    /**
     * Create an instance of {@link ValidateFieldResponse }
     * 
     */
    public ValidateFieldResponse createValidateFieldResponse() {
        return new ValidateFieldResponse();
    }

    /**
     * Create an instance of {@link ValidateSit }
     * 
     */
    public ValidateSit createValidateSit() {
        return new ValidateSit();
    }

    /**
     * Create an instance of {@link GetListaVieResponse }
     * 
     */
    public GetListaVieResponse createGetListaVieResponse() {
        return new GetListaVieResponse();
    }

    /**
     * Create an instance of {@link ArrayOfDettagliVia }
     * 
     */
    public ArrayOfDettagliVia createArrayOfDettagliVia() {
        return new ArrayOfDettagliVia();
    }

    /**
     * Create an instance of {@link ValidateField }
     * 
     */
    public ValidateField createValidateField() {
        return new ValidateField();
    }

    /**
     * Create an instance of {@link Sit }
     * 
     */
    public Sit createSit() {
        return new Sit();
    }

    /**
     * Create an instance of {@link EffettuaValidazioneFormale }
     * 
     */
    public EffettuaValidazioneFormale createEffettuaValidazioneFormale() {
        return new EffettuaValidazioneFormale();
    }

    /**
     * Create an instance of {@link GetDetailField }
     * 
     */
    public GetDetailField createGetDetailField() {
        return new GetDetailField();
    }

    /**
     * Create an instance of {@link GetListField }
     * 
     */
    public GetListField createGetListField() {
        return new GetListField();
    }

    /**
     * Create an instance of {@link GetListaVie }
     * 
     */
    public GetListaVie createGetListaVie() {
        return new GetListaVie();
    }

    /**
     * Create an instance of {@link GetListFieldResponse }
     * 
     */
    public GetListFieldResponse createGetListFieldResponse() {
        return new GetListFieldResponse();
    }

    /**
     * Create an instance of {@link ListSit }
     * 
     */
    public ListSit createListSit() {
        return new ListSit();
    }

    /**
     * Create an instance of {@link EffettuaValidazioneFormaleResponse }
     * 
     */
    public EffettuaValidazioneFormaleResponse createEffettuaValidazioneFormaleResponse() {
        return new EffettuaValidazioneFormaleResponse();
    }

    /**
     * Create an instance of {@link ArrayOfBaseDtoOfTipoVisualizzazioneString }
     * 
     */
    public ArrayOfBaseDtoOfTipoVisualizzazioneString createArrayOfBaseDtoOfTipoVisualizzazioneString() {
        return new ArrayOfBaseDtoOfTipoVisualizzazioneString();
    }

    /**
     * Create an instance of {@link DettagliVia }
     * 
     */
    public DettagliVia createDettagliVia() {
        return new DettagliVia();
    }

    /**
     * Create an instance of {@link BaseDtoOfTipoVisualizzazioneString }
     * 
     */
    public BaseDtoOfTipoVisualizzazioneString createBaseDtoOfTipoVisualizzazioneString() {
        return new BaseDtoOfTipoVisualizzazioneString();
    }

    /**
     * Create an instance of {@link DetailField }
     * 
     */
    public DetailField createDetailField() {
        return new DetailField();
    }

    /**
     * Create an instance of {@link ArrayOfDetailField }
     * 
     */
    public ArrayOfDetailField createArrayOfDetailField() {
        return new ArrayOfDetailField();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SitFeatures }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://init.sigepro.it", name = "SitFeatures")
    public JAXBElement<SitFeatures> createSitFeatures(SitFeatures value) {
        return new JAXBElement<SitFeatures>(_SitFeatures_QNAME, SitFeatures.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfString }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://init.sigepro.it", name = "ArrayOfString")
    public JAXBElement<ArrayOfString> createArrayOfString(ArrayOfString value) {
        return new JAXBElement<ArrayOfString>(_ArrayOfString_QNAME, ArrayOfString.class, null, value);
    }

}
