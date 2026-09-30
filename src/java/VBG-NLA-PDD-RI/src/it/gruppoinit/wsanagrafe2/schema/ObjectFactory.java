
package it.gruppoinit.wsanagrafe2.schema;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.gruppoinit.wsanagrafe2.schema package. 
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

    private final static QName _Anagrafe_QNAME = new QName("http://init.sigepro.it", "Anagrafe");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.gruppoinit.wsanagrafe2.schema
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetPersonaGiuridica }
     * 
     */
    public GetPersonaGiuridica createGetPersonaGiuridica() {
        return new GetPersonaGiuridica();
    }

    /**
     * Create an instance of {@link GetPersonaFisicaResponse }
     * 
     */
    public GetPersonaFisicaResponse createGetPersonaFisicaResponse() {
        return new GetPersonaFisicaResponse();
    }

    /**
     * Create an instance of {@link Anagrafe }
     * 
     */
    public Anagrafe createAnagrafe() {
        return new Anagrafe();
    }

    /**
     * Create an instance of {@link GetPersonaGiuridicaResponse }
     * 
     */
    public GetPersonaGiuridicaResponse createGetPersonaGiuridicaResponse() {
        return new GetPersonaGiuridicaResponse();
    }

    /**
     * Create an instance of {@link GetPersonaFisica }
     * 
     */
    public GetPersonaFisica createGetPersonaFisica() {
        return new GetPersonaFisica();
    }

    /**
     * Create an instance of {@link ElencoInpsBase }
     * 
     */
    public ElencoInpsBase createElencoInpsBase() {
        return new ElencoInpsBase();
    }

    /**
     * Create an instance of {@link Titoli }
     * 
     */
    public Titoli createTitoli() {
        return new Titoli();
    }

    /**
     * Create an instance of {@link AnagrafeDocumenti }
     * 
     */
    public AnagrafeDocumenti createAnagrafeDocumenti() {
        return new AnagrafeDocumenti();
    }

    /**
     * Create an instance of {@link AnagrafeDyn2Dati }
     * 
     */
    public AnagrafeDyn2Dati createAnagrafeDyn2Dati() {
        return new AnagrafeDyn2Dati();
    }

    /**
     * Create an instance of {@link ElenchiProfessionaliBase }
     * 
     */
    public ElenchiProfessionaliBase createElenchiProfessionaliBase() {
        return new ElenchiProfessionaliBase();
    }

    /**
     * Create an instance of {@link MercatiPresenzeStorico }
     * 
     */
    public MercatiPresenzeStorico createMercatiPresenzeStorico() {
        return new MercatiPresenzeStorico();
    }

    /**
     * Create an instance of {@link VwProvince }
     * 
     */
    public VwProvince createVwProvince() {
        return new VwProvince();
    }

    /**
     * Create an instance of {@link BaseDataClass }
     * 
     */
    public BaseDataClass createBaseDataClass() {
        return new BaseDataClass();
    }

    /**
     * Create an instance of {@link Cittadinanza }
     * 
     */
    public Cittadinanza createCittadinanza() {
        return new Cittadinanza();
    }

    /**
     * Create an instance of {@link Oggetti }
     * 
     */
    public Oggetti createOggetti() {
        return new Oggetti();
    }

    /**
     * Create an instance of {@link FormeGiuridiche }
     * 
     */
    public FormeGiuridiche createFormeGiuridiche() {
        return new FormeGiuridiche();
    }

    /**
     * Create an instance of {@link DataClass }
     * 
     */
    public DataClass createDataClass() {
        return new DataClass();
    }

    /**
     * Create an instance of {@link ElencoInailBase }
     * 
     */
    public ElencoInailBase createElencoInailBase() {
        return new ElencoInailBase();
    }

    /**
     * Create an instance of {@link Comuni }
     * 
     */
    public Comuni createComuni() {
        return new Comuni();
    }

    /**
     * Create an instance of {@link AnagrafeDyn2ModelliT }
     * 
     */
    public AnagrafeDyn2ModelliT createAnagrafeDyn2ModelliT() {
        return new AnagrafeDyn2ModelliT();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Anagrafe }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://init.sigepro.it", name = "Anagrafe")
    public JAXBElement<Anagrafe> createAnagrafe(Anagrafe value) {
        return new JAXBElement<Anagrafe>(_Anagrafe_QNAME, Anagrafe.class, null, value);
    }

}
