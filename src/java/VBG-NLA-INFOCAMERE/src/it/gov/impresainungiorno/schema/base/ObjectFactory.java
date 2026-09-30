
package it.gov.impresainungiorno.schema.base;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.gov.impresainungiorno.schema.base package. 
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

    private final static QName _Ricevuta_QNAME = new QName("http://www.impresainungiorno.gov.it/schema/base", "ricevuta");
    private final static QName _DatiMittente_QNAME = new QName("http://www.impresainungiorno.gov.it/schema/base", "dati-mittente");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.gov.impresainungiorno.schema.base
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link InvioAdempimento }
     * 
     */
    public InvioAdempimento createInvioAdempimento() {
        return new InvioAdempimento();
    }

    /**
     * Create an instance of {@link AnagraficaCompleta }
     * 
     */
    public AnagraficaCompleta createAnagraficaCompleta() {
        return new AnagraficaCompleta();
    }

    /**
     * Create an instance of {@link UtentePortale }
     * 
     */
    public UtentePortale createUtentePortale() {
        return new UtentePortale();
    }

    /**
     * Create an instance of {@link Ricevuta }
     * 
     */
    public Ricevuta createRicevuta() {
        return new Ricevuta();
    }

    /**
     * Create an instance of {@link EMail }
     * 
     */
    public EMail createEMail() {
        return new EMail();
    }

    /**
     * Create an instance of {@link Ammontare }
     * 
     */
    public Ammontare createAmmontare() {
        return new Ammontare();
    }

    /**
     * Create an instance of {@link AmmontareNonNegativo }
     * 
     */
    public AmmontareNonNegativo createAmmontareNonNegativo() {
        return new AmmontareNonNegativo();
    }

    /**
     * Create an instance of {@link AmmontarePositivo }
     * 
     */
    public AmmontarePositivo createAmmontarePositivo() {
        return new AmmontarePositivo();
    }

    /**
     * Create an instance of {@link Anagrafica }
     * 
     */
    public Anagrafica createAnagrafica() {
        return new Anagrafica();
    }

    /**
     * Create an instance of {@link AnagraficaImpresa }
     * 
     */
    public AnagraficaImpresa createAnagraficaImpresa() {
        return new AnagraficaImpresa();
    }

    /**
     * Create an instance of {@link AttivitaEsercitata }
     * 
     */
    public AttivitaEsercitata createAttivitaEsercitata() {
        return new AttivitaEsercitata();
    }

    /**
     * Create an instance of {@link AttivitaISTAT }
     * 
     */
    public AttivitaISTAT createAttivitaISTAT() {
        return new AttivitaISTAT();
    }

    /**
     * Create an instance of {@link CodiceREA }
     * 
     */
    public CodiceREA createCodiceREA() {
        return new CodiceREA();
    }

    /**
     * Create an instance of {@link Comune }
     * 
     */
    public Comune createComune() {
        return new Comune();
    }

    /**
     * Create an instance of {@link ComuneRI }
     * 
     */
    public ComuneRI createComuneRI() {
        return new ComuneRI();
    }

    /**
     * Create an instance of {@link DatiPersona }
     * 
     */
    public DatiPersona createDatiPersona() {
        return new DatiPersona();
    }

    /**
     * Create an instance of {@link Errore }
     * 
     */
    public Errore createErrore() {
        return new Errore();
    }

    /**
     * Create an instance of {@link FormaGiuridica }
     * 
     */
    public FormaGiuridica createFormaGiuridica() {
        return new FormaGiuridica();
    }

    /**
     * Create an instance of {@link Indirizzo }
     * 
     */
    public Indirizzo createIndirizzo() {
        return new Indirizzo();
    }

    /**
     * Create an instance of {@link IndirizzoRI }
     * 
     */
    public IndirizzoRI createIndirizzoRI() {
        return new IndirizzoRI();
    }

    /**
     * Create an instance of {@link IndirizzoConRecapiti }
     * 
     */
    public IndirizzoConRecapiti createIndirizzoConRecapiti() {
        return new IndirizzoConRecapiti();
    }

    /**
     * Create an instance of {@link IndirizzoConRecapitiRI }
     * 
     */
    public IndirizzoConRecapitiRI createIndirizzoConRecapitiRI() {
        return new IndirizzoConRecapitiRI();
    }

    /**
     * Create an instance of {@link IndirizzoConRecapitiObbligatori }
     * 
     */
    public IndirizzoConRecapitiObbligatori createIndirizzoConRecapitiObbligatori() {
        return new IndirizzoConRecapitiObbligatori();
    }

    /**
     * Create an instance of {@link IndirizzoItaliano }
     * 
     */
    public IndirizzoItaliano createIndirizzoItaliano() {
        return new IndirizzoItaliano();
    }

    /**
     * Create an instance of {@link IndirizzoItalianoRI }
     * 
     */
    public IndirizzoItalianoRI createIndirizzoItalianoRI() {
        return new IndirizzoItalianoRI();
    }

    /**
     * Create an instance of {@link IndirizzoItalianoConRecapiti }
     * 
     */
    public IndirizzoItalianoConRecapiti createIndirizzoItalianoConRecapiti() {
        return new IndirizzoItalianoConRecapiti();
    }

    /**
     * Create an instance of {@link IndirizzoStraniero }
     * 
     */
    public IndirizzoStraniero createIndirizzoStraniero() {
        return new IndirizzoStraniero();
    }

    /**
     * Create an instance of {@link Provincia }
     * 
     */
    public Provincia createProvincia() {
        return new Provincia();
    }

    /**
     * Create an instance of {@link ProvinciaRI }
     * 
     */
    public ProvinciaRI createProvinciaRI() {
        return new ProvinciaRI();
    }

    /**
     * Create an instance of {@link Stato }
     * 
     */
    public Stato createStato() {
        return new Stato();
    }

    /**
     * Create an instance of {@link Telefono }
     * 
     */
    public Telefono createTelefono() {
        return new Telefono();
    }

    /**
     * Create an instance of {@link Carica }
     * 
     */
    public Carica createCarica() {
        return new Carica();
    }

    /**
     * Create an instance of {@link LegaleRappresentante }
     * 
     */
    public LegaleRappresentante createLegaleRappresentante() {
        return new LegaleRappresentante();
    }

    /**
     * Create an instance of {@link AnagraficaRappresentante }
     * 
     */
    public AnagraficaRappresentante createAnagraficaRappresentante() {
        return new AnagraficaRappresentante();
    }

    /**
     * Create an instance of {@link EntitaComposta }
     * 
     */
    public EntitaComposta createEntitaComposta() {
        return new EntitaComposta();
    }

    /**
     * Create an instance of {@link DettaglioImpresa }
     * 
     */
    public DettaglioImpresa createDettaglioImpresa() {
        return new DettaglioImpresa();
    }

    /**
     * Create an instance of {@link EnteRicevuta }
     * 
     */
    public EnteRicevuta createEnteRicevuta() {
        return new EnteRicevuta();
    }

    /**
     * Create an instance of {@link DocumentoAllegato }
     * 
     */
    public DocumentoAllegato createDocumentoAllegato() {
        return new DocumentoAllegato();
    }

    /**
     * Create an instance of {@link InvioAdempimento.Messaggio }
     * 
     */
    public InvioAdempimento.Messaggio createInvioAdempimentoMessaggio() {
        return new InvioAdempimento.Messaggio();
    }

    /**
     * Create an instance of {@link AnagraficaCompleta.Nascita }
     * 
     */
    public AnagraficaCompleta.Nascita createAnagraficaCompletaNascita() {
        return new AnagraficaCompleta.Nascita();
    }

    /**
     * Create an instance of {@link UtentePortale.Delegato }
     * 
     */
    public UtentePortale.Delegato createUtentePortaleDelegato() {
        return new UtentePortale.Delegato();
    }

    /**
     * Create an instance of {@link Ricevuta.ListaAdempimenti }
     * 
     */
    public Ricevuta.ListaAdempimenti createRicevutaListaAdempimenti() {
        return new Ricevuta.ListaAdempimenti();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Ricevuta }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Ricevuta }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.impresainungiorno.gov.it/schema/base", name = "ricevuta")
    public JAXBElement<Ricevuta> createRicevuta(Ricevuta value) {
        return new JAXBElement<Ricevuta>(_Ricevuta_QNAME, Ricevuta.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UtentePortale }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link UtentePortale }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.impresainungiorno.gov.it/schema/base", name = "dati-mittente")
    public JAXBElement<UtentePortale> createDatiMittente(UtentePortale value) {
        return new JAXBElement<UtentePortale>(_DatiMittente_QNAME, UtentePortale.class, null, value);
    }

}
