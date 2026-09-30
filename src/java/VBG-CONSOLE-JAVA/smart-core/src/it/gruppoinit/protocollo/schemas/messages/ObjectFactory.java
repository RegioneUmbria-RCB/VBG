
package it.gruppoinit.protocollo.schemas.messages;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.gruppoinit.protocollo.schemas.messages package. 
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

    private final static QName _AnyURI_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "anyURI");
    private final static QName _EnumFascicolatoType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "EnumFascicolatoType");
    private final static QName _ArrayOfListaMotiviAnnullamentoMotivoAnnullamentoType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "ArrayOfListaMotiviAnnullamentoMotivoAnnullamentoType");
    private final static QName _Char_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "char");
    private final static QName _DatiProtocolloResponseType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "DatiProtocolloResponseType");
    private final static QName _Float_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "float");
    private final static QName _Long_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "long");
    private final static QName _Base64Binary_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "base64Binary");
    private final static QName _Byte_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "byte");
    private final static QName _Boolean_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "boolean");
    private final static QName _DatiRequestType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "DatiRequestType");
    private final static QName _UnsignedByte_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedByte");
    private final static QName _AnyType_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "anyType");
    private final static QName _Int_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "int");
    private final static QName _EtichetteResponseType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "EtichetteResponseType");
    private final static QName _AllegatoType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "AllegatoType");
    private final static QName _ListaTipiClassifica_QNAME = new QName("http://it.gruppoinit/Protocollazione", "ListaTipiClassifica");
    private final static QName _AllegatoResponseType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "AllegatoResponseType");
    private final static QName _DatiDestinatariType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "DatiDestinatariType");
    private final static QName _CreaUnitaDocumentaleRequestType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "CreaUnitaDocumentaleRequestType");
    private final static QName _DatiFascicoloResponseType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "DatiFascicoloResponseType");
    private final static QName _Double_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "double");
    private final static QName _ListaMotiviAnnullamentoResponseType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "ListaMotiviAnnullamentoResponseType");
    private final static QName _ArrayOfAllegatoType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "ArrayOfAllegatoType");
    private final static QName _DateTime_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "dateTime");
    private final static QName _ListaTipiClassificaClassifica_QNAME = new QName("http://it.gruppoinit/Protocollazione", "ListaTipiClassificaClassifica");
    private final static QName _ArrayOfDatiAnagraficiType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "ArrayOfDatiAnagraficiType");
    private final static QName _QName_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "QName");
    private final static QName _UnsignedShort_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedShort");
    private final static QName _Short_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "short");
    private final static QName _ListaTipiDocumentoResponseType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "ListaTipiDocumentoResponseType");
    private final static QName _EnumAnnullatoType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "EnumAnnullatoType");
    private final static QName _DatiAnagraficiType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "DatiAnagraficiType");
    private final static QName _DatiProtocolloFascicolatoResponseType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "DatiProtocolloFascicolatoResponseType");
    private final static QName _ListaFascicoliResponseType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "ListaFascicoliResponseType");
    private final static QName _ListaTipiDocumentoDocumentoType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "ListaTipiDocumentoDocumentoType");
    private final static QName _ArrayOfMittDestOutType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "ArrayOfMittDestOutType");
    private final static QName _DatiFascType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "DatiFascType");
    private final static QName _UnsignedInt_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedInt");
    private final static QName _ArrayOfListaTipiClassificaClassifica_QNAME = new QName("http://it.gruppoinit/Protocollazione", "ArrayOfListaTipiClassificaClassifica");
    private final static QName _ArrayOfListaTipiDocumentoDocumentoType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "ArrayOfListaTipiDocumentoDocumentoType");
    private final static QName _ErroreProtocolloType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "ErroreProtocolloType");
    private final static QName _Decimal_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "decimal");
    private final static QName _ListaMotiviAnnullamentoMotivoAnnullamentoType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "ListaMotiviAnnullamentoMotivoAnnullamentoType");
    private final static QName _ArrayOfDatiFascType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "ArrayOfDatiFascType");
    private final static QName _DatiProtocolloAnnullatoResponseType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "DatiProtocolloAnnullatoResponseType");
    private final static QName _Guid_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "guid");
    private final static QName _DatiMittentiType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "DatiMittentiType");
    private final static QName _ArrayOfAllegatoResponseType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "ArrayOfAllegatoResponseType");
    private final static QName _Duration_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "duration");
    private final static QName _String_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "string");
    private final static QName _UnsignedLong_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedLong");
    private final static QName _DatiProtocolloLettoResponseType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "DatiProtocolloLettoResponseType");
    private final static QName _CreaUnitaDocumentaleResponseType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "CreaUnitaDocumentaleResponseType");
    private final static QName _MittDestOutType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "MittDestOutType");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.gruppoinit.protocollo.schemas.messages
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ArrayOfAllegatoType }
     * 
     */
    public ArrayOfAllegatoType createArrayOfAllegatoType() {
        return new ArrayOfAllegatoType();
    }

    /**
     * Create an instance of {@link FascicolazioneMovimento }
     * 
     */
    public FascicolazioneMovimento createFascicolazioneMovimento() {
        return new FascicolazioneMovimento();
    }

    /**
     * Create an instance of {@link RegistrazioneMovimentoXml }
     * 
     */
    public RegistrazioneMovimentoXml createRegistrazioneMovimentoXml() {
        return new RegistrazioneMovimentoXml();
    }

    /**
     * Create an instance of {@link DatiRequestType }
     * 
     */
    public DatiRequestType createDatiRequestType() {
        return new DatiRequestType();
    }

    /**
     * Create an instance of {@link CambiaFascicoloIstanzaXml }
     * 
     */
    public CambiaFascicoloIstanzaXml createCambiaFascicoloIstanzaXml() {
        return new CambiaFascicoloIstanzaXml();
    }

    /**
     * Create an instance of {@link DatiFascType }
     * 
     */
    public DatiFascType createDatiFascType() {
        return new DatiFascType();
    }

    /**
     * Create an instance of {@link ArrayOfDatiAnagraficiType }
     * 
     */
    public ArrayOfDatiAnagraficiType createArrayOfDatiAnagraficiType() {
        return new ArrayOfDatiAnagraficiType();
    }

    /**
     * Create an instance of {@link GetFascicoliResponse }
     * 
     */
    public GetFascicoliResponse createGetFascicoliResponse() {
        return new GetFascicoliResponse();
    }

    /**
     * Create an instance of {@link ListaFascicoliResponseType }
     * 
     */
    public ListaFascicoliResponseType createListaFascicoliResponseType() {
        return new ListaFascicoliResponseType();
    }

    /**
     * Create an instance of {@link ListaTipiClassificaClassifica }
     * 
     */
    public ListaTipiClassificaClassifica createListaTipiClassificaClassifica() {
        return new ListaTipiClassificaClassifica();
    }

    /**
     * Create an instance of {@link ProtocollazioneMovimentoResponse }
     * 
     */
    public ProtocollazioneMovimentoResponse createProtocollazioneMovimentoResponse() {
        return new ProtocollazioneMovimentoResponse();
    }

    /**
     * Create an instance of {@link DatiProtocolloResponseType }
     * 
     */
    public DatiProtocolloResponseType createDatiProtocolloResponseType() {
        return new DatiProtocolloResponseType();
    }

    /**
     * Create an instance of {@link CreaUnitaDocumentaleMovimentoResponse }
     * 
     */
    public CreaUnitaDocumentaleMovimentoResponse createCreaUnitaDocumentaleMovimentoResponse() {
        return new CreaUnitaDocumentaleMovimentoResponse();
    }

    /**
     * Create an instance of {@link CreaUnitaDocumentaleResponseType }
     * 
     */
    public CreaUnitaDocumentaleResponseType createCreaUnitaDocumentaleResponseType() {
        return new CreaUnitaDocumentaleResponseType();
    }

    /**
     * Create an instance of {@link InvioPec }
     * 
     */
    public InvioPec createInvioPec() {
        return new InvioPec();
    }

    /**
     * Create an instance of {@link ProtocollazioneIstanza }
     * 
     */
    public ProtocollazioneIstanza createProtocollazioneIstanza() {
        return new ProtocollazioneIstanza();
    }

    /**
     * Create an instance of {@link AnnullaProtocollo }
     * 
     */
    public AnnullaProtocollo createAnnullaProtocollo() {
        return new AnnullaProtocollo();
    }

    /**
     * Create an instance of {@link GetTipiDocumentoResponse }
     * 
     */
    public GetTipiDocumentoResponse createGetTipiDocumentoResponse() {
        return new GetTipiDocumentoResponse();
    }

    /**
     * Create an instance of {@link ListaTipiDocumentoResponseType }
     * 
     */
    public ListaTipiDocumentoResponseType createListaTipiDocumentoResponseType() {
        return new ListaTipiDocumentoResponseType();
    }

    /**
     * Create an instance of {@link CreaCopieResponse }
     * 
     */
    public CreaCopieResponse createCreaCopieResponse() {
        return new CreaCopieResponse();
    }

    /**
     * Create an instance of {@link FascicolazioneIstanzaXml }
     * 
     */
    public FascicolazioneIstanzaXml createFascicolazioneIstanzaXml() {
        return new FascicolazioneIstanzaXml();
    }

    /**
     * Create an instance of {@link FascicolazioneIstanzaResponse }
     * 
     */
    public FascicolazioneIstanzaResponse createFascicolazioneIstanzaResponse() {
        return new FascicolazioneIstanzaResponse();
    }

    /**
     * Create an instance of {@link DatiFascicoloResponseType }
     * 
     */
    public DatiFascicoloResponseType createDatiFascicoloResponseType() {
        return new DatiFascicoloResponseType();
    }

    /**
     * Create an instance of {@link ArrayOfMittDestOutType }
     * 
     */
    public ArrayOfMittDestOutType createArrayOfMittDestOutType() {
        return new ArrayOfMittDestOutType();
    }

    /**
     * Create an instance of {@link InvioPecResponse }
     * 
     */
    public InvioPecResponse createInvioPecResponse() {
        return new InvioPecResponse();
    }

    /**
     * Create an instance of {@link DatiProtocolloFascicolatoResponseType }
     * 
     */
    public DatiProtocolloFascicolatoResponseType createDatiProtocolloFascicolatoResponseType() {
        return new DatiProtocolloFascicolatoResponseType();
    }

    /**
     * Create an instance of {@link ListaTipiDocumentoDocumentoType }
     * 
     */
    public ListaTipiDocumentoDocumentoType createListaTipiDocumentoDocumentoType() {
        return new ListaTipiDocumentoDocumentoType();
    }

    /**
     * Create an instance of {@link DatiAnagraficiType }
     * 
     */
    public DatiAnagraficiType createDatiAnagraficiType() {
        return new DatiAnagraficiType();
    }

    /**
     * Create an instance of {@link ArrayOfListaTipiClassificaClassifica }
     * 
     */
    public ArrayOfListaTipiClassificaClassifica createArrayOfListaTipiClassificaClassifica() {
        return new ArrayOfListaTipiClassificaClassifica();
    }

    /**
     * Create an instance of {@link IsFascicolato }
     * 
     */
    public IsFascicolato createIsFascicolato() {
        return new IsFascicolato();
    }

    /**
     * Create an instance of {@link ErroreProtocolloType }
     * 
     */
    public ErroreProtocolloType createErroreProtocolloType() {
        return new ErroreProtocolloType();
    }

    /**
     * Create an instance of {@link SearchFascicoli }
     * 
     */
    public SearchFascicoli createSearchFascicoli() {
        return new SearchFascicoli();
    }

    /**
     * Create an instance of {@link ProtocollazioneIstanzaXmlResponse }
     * 
     */
    public ProtocollazioneIstanzaXmlResponse createProtocollazioneIstanzaXmlResponse() {
        return new ProtocollazioneIstanzaXmlResponse();
    }

    /**
     * Create an instance of {@link ArrayOfListaTipiDocumentoDocumentoType }
     * 
     */
    public ArrayOfListaTipiDocumentoDocumentoType createArrayOfListaTipiDocumentoDocumentoType() {
        return new ArrayOfListaTipiDocumentoDocumentoType();
    }

    /**
     * Create an instance of {@link ProtocollazioneXml }
     * 
     */
    public ProtocollazioneXml createProtocollazioneXml() {
        return new ProtocollazioneXml();
    }

    /**
     * Create an instance of {@link ArrayOfDatiFascType }
     * 
     */
    public ArrayOfDatiFascType createArrayOfDatiFascType() {
        return new ArrayOfDatiFascType();
    }

    /**
     * Create an instance of {@link ListaMotiviAnnullamentoMotivoAnnullamentoType }
     * 
     */
    public ListaMotiviAnnullamentoMotivoAnnullamentoType createListaMotiviAnnullamentoMotivoAnnullamentoType() {
        return new ListaMotiviAnnullamentoMotivoAnnullamentoType();
    }

    /**
     * Create an instance of {@link FascicolazioneIstanza }
     * 
     */
    public FascicolazioneIstanza createFascicolazioneIstanza() {
        return new FascicolazioneIstanza();
    }

    /**
     * Create an instance of {@link IsAnnullato }
     * 
     */
    public IsAnnullato createIsAnnullato() {
        return new IsAnnullato();
    }

    /**
     * Create an instance of {@link ProtocollazioneIstanzaResponse }
     * 
     */
    public ProtocollazioneIstanzaResponse createProtocollazioneIstanzaResponse() {
        return new ProtocollazioneIstanzaResponse();
    }

    /**
     * Create an instance of {@link DatiMittentiType }
     * 
     */
    public DatiMittentiType createDatiMittentiType() {
        return new DatiMittentiType();
    }

    /**
     * Create an instance of {@link ArrayOfAllegatoResponseType }
     * 
     */
    public ArrayOfAllegatoResponseType createArrayOfAllegatoResponseType() {
        return new ArrayOfAllegatoResponseType();
    }

    /**
     * Create an instance of {@link DatiProtocolloAnnullatoResponseType }
     * 
     */
    public DatiProtocolloAnnullatoResponseType createDatiProtocolloAnnullatoResponseType() {
        return new DatiProtocolloAnnullatoResponseType();
    }

    /**
     * Create an instance of {@link StampaEtichette }
     * 
     */
    public StampaEtichette createStampaEtichette() {
        return new StampaEtichette();
    }

    /**
     * Create an instance of {@link GetMotiviAnnullamento }
     * 
     */
    public GetMotiviAnnullamento createGetMotiviAnnullamento() {
        return new GetMotiviAnnullamento();
    }

    /**
     * Create an instance of {@link FascicolazioneMovimentoXml }
     * 
     */
    public FascicolazioneMovimentoXml createFascicolazioneMovimentoXml() {
        return new FascicolazioneMovimentoXml();
    }

    /**
     * Create an instance of {@link GetClassificheResponse }
     * 
     */
    public GetClassificheResponse createGetClassificheResponse() {
        return new GetClassificheResponse();
    }

    /**
     * Create an instance of {@link ListaTipiClassifica }
     * 
     */
    public ListaTipiClassifica createListaTipiClassifica() {
        return new ListaTipiClassifica();
    }

    /**
     * Create an instance of {@link DatiProtocolloLettoResponseType }
     * 
     */
    public DatiProtocolloLettoResponseType createDatiProtocolloLettoResponseType() {
        return new DatiProtocolloLettoResponseType();
    }

    /**
     * Create an instance of {@link MittDestOutType }
     * 
     */
    public MittDestOutType createMittDestOutType() {
        return new MittDestOutType();
    }

    /**
     * Create an instance of {@link LeggiAllegato }
     * 
     */
    public LeggiAllegato createLeggiAllegato() {
        return new LeggiAllegato();
    }

    /**
     * Create an instance of {@link ArrayOfListaMotiviAnnullamentoMotivoAnnullamentoType }
     * 
     */
    public ArrayOfListaMotiviAnnullamentoMotivoAnnullamentoType createArrayOfListaMotiviAnnullamentoMotivoAnnullamentoType() {
        return new ArrayOfListaMotiviAnnullamentoMotivoAnnullamentoType();
    }

    /**
     * Create an instance of {@link FascicolazioneIstanzaXmlResponse }
     * 
     */
    public FascicolazioneIstanzaXmlResponse createFascicolazioneIstanzaXmlResponse() {
        return new FascicolazioneIstanzaXmlResponse();
    }

    /**
     * Create an instance of {@link ProtocollazioneMovimentoXmlResponse }
     * 
     */
    public ProtocollazioneMovimentoXmlResponse createProtocollazioneMovimentoXmlResponse() {
        return new ProtocollazioneMovimentoXmlResponse();
    }

    /**
     * Create an instance of {@link LeggiAllegatoResponse }
     * 
     */
    public LeggiAllegatoResponse createLeggiAllegatoResponse() {
        return new LeggiAllegatoResponse();
    }

    /**
     * Create an instance of {@link AllegatoResponseType }
     * 
     */
    public AllegatoResponseType createAllegatoResponseType() {
        return new AllegatoResponseType();
    }

    /**
     * Create an instance of {@link ProtocollazioneXmlResponse }
     * 
     */
    public ProtocollazioneXmlResponse createProtocollazioneXmlResponse() {
        return new ProtocollazioneXmlResponse();
    }

    /**
     * Create an instance of {@link IsFascicolatoResponse }
     * 
     */
    public IsFascicolatoResponse createIsFascicolatoResponse() {
        return new IsFascicolatoResponse();
    }

    /**
     * Create an instance of {@link GetFascicoli }
     * 
     */
    public GetFascicoli createGetFascicoli() {
        return new GetFascicoli();
    }

    /**
     * Create an instance of {@link CreaUnitaDocumentaleMovimento }
     * 
     */
    public CreaUnitaDocumentaleMovimento createCreaUnitaDocumentaleMovimento() {
        return new CreaUnitaDocumentaleMovimento();
    }

    /**
     * Create an instance of {@link CreaUnitaDocumentaleRequestType }
     * 
     */
    public CreaUnitaDocumentaleRequestType createCreaUnitaDocumentaleRequestType() {
        return new CreaUnitaDocumentaleRequestType();
    }

    /**
     * Create an instance of {@link CreaCopie }
     * 
     */
    public CreaCopie createCreaCopie() {
        return new CreaCopie();
    }

    /**
     * Create an instance of {@link IsAnnullatoResponse }
     * 
     */
    public IsAnnullatoResponse createIsAnnullatoResponse() {
        return new IsAnnullatoResponse();
    }

    /**
     * Create an instance of {@link CambiaFascicoloIstanzaXmlResponse }
     * 
     */
    public CambiaFascicoloIstanzaXmlResponse createCambiaFascicoloIstanzaXmlResponse() {
        return new CambiaFascicoloIstanzaXmlResponse();
    }

    /**
     * Create an instance of {@link MettiAllaFirmaXml }
     * 
     */
    public MettiAllaFirmaXml createMettiAllaFirmaXml() {
        return new MettiAllaFirmaXml();
    }

    /**
     * Create an instance of {@link ProtocollazioneMovimento }
     * 
     */
    public ProtocollazioneMovimento createProtocollazioneMovimento() {
        return new ProtocollazioneMovimento();
    }

    /**
     * Create an instance of {@link RegistrazioneIstanzaXmlResponse }
     * 
     */
    public RegistrazioneIstanzaXmlResponse createRegistrazioneIstanzaXmlResponse() {
        return new RegistrazioneIstanzaXmlResponse();
    }

    /**
     * Create an instance of {@link RegistrazioneIstanzaXml }
     * 
     */
    public RegistrazioneIstanzaXml createRegistrazioneIstanzaXml() {
        return new RegistrazioneIstanzaXml();
    }

    /**
     * Create an instance of {@link RegistrazioneMovimentoXmlResponse }
     * 
     */
    public RegistrazioneMovimentoXmlResponse createRegistrazioneMovimentoXmlResponse() {
        return new RegistrazioneMovimentoXmlResponse();
    }

    /**
     * Create an instance of {@link MettiAllaFirmaXmlResponse }
     * 
     */
    public MettiAllaFirmaXmlResponse createMettiAllaFirmaXmlResponse() {
        return new MettiAllaFirmaXmlResponse();
    }

    /**
     * Create an instance of {@link AnnullaProtocolloResponse }
     * 
     */
    public AnnullaProtocolloResponse createAnnullaProtocolloResponse() {
        return new AnnullaProtocolloResponse();
    }

    /**
     * Create an instance of {@link FascicolazioneMovimentoXmlResponse }
     * 
     */
    public FascicolazioneMovimentoXmlResponse createFascicolazioneMovimentoXmlResponse() {
        return new FascicolazioneMovimentoXmlResponse();
    }

    /**
     * Create an instance of {@link FascicolazioneMovimentoResponse }
     * 
     */
    public FascicolazioneMovimentoResponse createFascicolazioneMovimentoResponse() {
        return new FascicolazioneMovimentoResponse();
    }

    /**
     * Create an instance of {@link SearchFascicoliResponse }
     * 
     */
    public SearchFascicoliResponse createSearchFascicoliResponse() {
        return new SearchFascicoliResponse();
    }

    /**
     * Create an instance of {@link CreaUnitaDocumentaleIstanzaResponse }
     * 
     */
    public CreaUnitaDocumentaleIstanzaResponse createCreaUnitaDocumentaleIstanzaResponse() {
        return new CreaUnitaDocumentaleIstanzaResponse();
    }

    /**
     * Create an instance of {@link GetClassifiche }
     * 
     */
    public GetClassifiche createGetClassifiche() {
        return new GetClassifiche();
    }

    /**
     * Create an instance of {@link ProtocollazioneMovimentoXml }
     * 
     */
    public ProtocollazioneMovimentoXml createProtocollazioneMovimentoXml() {
        return new ProtocollazioneMovimentoXml();
    }

    /**
     * Create an instance of {@link AllegatoType }
     * 
     */
    public AllegatoType createAllegatoType() {
        return new AllegatoType();
    }

    /**
     * Create an instance of {@link LeggiProtocolloResponse }
     * 
     */
    public LeggiProtocolloResponse createLeggiProtocolloResponse() {
        return new LeggiProtocolloResponse();
    }

    /**
     * Create an instance of {@link EtichetteResponseType }
     * 
     */
    public EtichetteResponseType createEtichetteResponseType() {
        return new EtichetteResponseType();
    }

    /**
     * Create an instance of {@link CreaUnitaDocumentaleIstanza }
     * 
     */
    public CreaUnitaDocumentaleIstanza createCreaUnitaDocumentaleIstanza() {
        return new CreaUnitaDocumentaleIstanza();
    }

    /**
     * Create an instance of {@link GetTipiDocumento }
     * 
     */
    public GetTipiDocumento createGetTipiDocumento() {
        return new GetTipiDocumento();
    }

    /**
     * Create an instance of {@link ProtocollazioneIstanzaXml }
     * 
     */
    public ProtocollazioneIstanzaXml createProtocollazioneIstanzaXml() {
        return new ProtocollazioneIstanzaXml();
    }

    /**
     * Create an instance of {@link DatiDestinatariType }
     * 
     */
    public DatiDestinatariType createDatiDestinatariType() {
        return new DatiDestinatariType();
    }

    /**
     * Create an instance of {@link StampaEtichetteResponse }
     * 
     */
    public StampaEtichetteResponse createStampaEtichetteResponse() {
        return new StampaEtichetteResponse();
    }

    /**
     * Create an instance of {@link LeggiProtocollo }
     * 
     */
    public LeggiProtocollo createLeggiProtocollo() {
        return new LeggiProtocollo();
    }

    /**
     * Create an instance of {@link GetMotiviAnnullamentoResponse }
     * 
     */
    public GetMotiviAnnullamentoResponse createGetMotiviAnnullamentoResponse() {
        return new GetMotiviAnnullamentoResponse();
    }

    /**
     * Create an instance of {@link ListaMotiviAnnullamentoResponseType }
     * 
     */
    public ListaMotiviAnnullamentoResponseType createListaMotiviAnnullamentoResponseType() {
        return new ListaMotiviAnnullamentoResponseType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "anyURI")
    public JAXBElement<String> createAnyURI(String value) {
        return new JAXBElement<String>(_AnyURI_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EnumFascicolatoType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://it.gruppoinit/Protocollazione", name = "EnumFascicolatoType")
    public JAXBElement<EnumFascicolatoType> createEnumFascicolatoType(EnumFascicolatoType value) {
        return new JAXBElement<EnumFascicolatoType>(_EnumFascicolatoType_QNAME, EnumFascicolatoType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfListaMotiviAnnullamentoMotivoAnnullamentoType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://it.gruppoinit/Protocollazione", name = "ArrayOfListaMotiviAnnullamentoMotivoAnnullamentoType")
    public JAXBElement<ArrayOfListaMotiviAnnullamentoMotivoAnnullamentoType> createArrayOfListaMotiviAnnullamentoMotivoAnnullamentoType(ArrayOfListaMotiviAnnullamentoMotivoAnnullamentoType value) {
        return new JAXBElement<ArrayOfListaMotiviAnnullamentoMotivoAnnullamentoType>(_ArrayOfListaMotiviAnnullamentoMotivoAnnullamentoType_QNAME, ArrayOfListaMotiviAnnullamentoMotivoAnnullamentoType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "char")
    public JAXBElement<Integer> createChar(Integer value) {
        return new JAXBElement<Integer>(_Char_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DatiProtocolloResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://it.gruppoinit/Protocollazione", name = "DatiProtocolloResponseType")
    public JAXBElement<DatiProtocolloResponseType> createDatiProtocolloResponseType(DatiProtocolloResponseType value) {
        return new JAXBElement<DatiProtocolloResponseType>(_DatiProtocolloResponseType_QNAME, DatiProtocolloResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Float }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "float")
    public JAXBElement<Float> createFloat(Float value) {
        return new JAXBElement<Float>(_Float_QNAME, Float.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "long")
    public JAXBElement<Long> createLong(Long value) {
        return new JAXBElement<Long>(_Long_QNAME, Long.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "base64Binary")
    public JAXBElement<byte[]> createBase64Binary(byte[] value) {
        return new JAXBElement<byte[]>(_Base64Binary_QNAME, byte[].class, null, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Byte }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "byte")
    public JAXBElement<Byte> createByte(Byte value) {
        return new JAXBElement<Byte>(_Byte_QNAME, Byte.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "boolean")
    public JAXBElement<Boolean> createBoolean(Boolean value) {
        return new JAXBElement<Boolean>(_Boolean_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DatiRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://it.gruppoinit/Protocollazione", name = "DatiRequestType")
    public JAXBElement<DatiRequestType> createDatiRequestType(DatiRequestType value) {
        return new JAXBElement<DatiRequestType>(_DatiRequestType_QNAME, DatiRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Short }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedByte")
    public JAXBElement<Short> createUnsignedByte(Short value) {
        return new JAXBElement<Short>(_UnsignedByte_QNAME, Short.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "anyType")
    public JAXBElement<Object> createAnyType(Object value) {
        return new JAXBElement<Object>(_AnyType_QNAME, Object.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "int")
    public JAXBElement<Integer> createInt(Integer value) {
        return new JAXBElement<Integer>(_Int_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EtichetteResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://it.gruppoinit/Protocollazione", name = "EtichetteResponseType")
    public JAXBElement<EtichetteResponseType> createEtichetteResponseType(EtichetteResponseType value) {
        return new JAXBElement<EtichetteResponseType>(_EtichetteResponseType_QNAME, EtichetteResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AllegatoType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://it.gruppoinit/Protocollazione", name = "AllegatoType")
    public JAXBElement<AllegatoType> createAllegatoType(AllegatoType value) {
        return new JAXBElement<AllegatoType>(_AllegatoType_QNAME, AllegatoType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListaTipiClassifica }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://it.gruppoinit/Protocollazione", name = "ListaTipiClassifica")
    public JAXBElement<ListaTipiClassifica> createListaTipiClassifica(ListaTipiClassifica value) {
        return new JAXBElement<ListaTipiClassifica>(_ListaTipiClassifica_QNAME, ListaTipiClassifica.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AllegatoResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://it.gruppoinit/Protocollazione", name = "AllegatoResponseType")
    public JAXBElement<AllegatoResponseType> createAllegatoResponseType(AllegatoResponseType value) {
        return new JAXBElement<AllegatoResponseType>(_AllegatoResponseType_QNAME, AllegatoResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DatiDestinatariType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://it.gruppoinit/Protocollazione", name = "DatiDestinatariType")
    public JAXBElement<DatiDestinatariType> createDatiDestinatariType(DatiDestinatariType value) {
        return new JAXBElement<DatiDestinatariType>(_DatiDestinatariType_QNAME, DatiDestinatariType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreaUnitaDocumentaleRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://it.gruppoinit/Protocollazione", name = "CreaUnitaDocumentaleRequestType")
    public JAXBElement<CreaUnitaDocumentaleRequestType> createCreaUnitaDocumentaleRequestType(CreaUnitaDocumentaleRequestType value) {
        return new JAXBElement<CreaUnitaDocumentaleRequestType>(_CreaUnitaDocumentaleRequestType_QNAME, CreaUnitaDocumentaleRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DatiFascicoloResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://it.gruppoinit/Protocollazione", name = "DatiFascicoloResponseType")
    public JAXBElement<DatiFascicoloResponseType> createDatiFascicoloResponseType(DatiFascicoloResponseType value) {
        return new JAXBElement<DatiFascicoloResponseType>(_DatiFascicoloResponseType_QNAME, DatiFascicoloResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "double")
    public JAXBElement<Double> createDouble(Double value) {
        return new JAXBElement<Double>(_Double_QNAME, Double.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListaMotiviAnnullamentoResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://it.gruppoinit/Protocollazione", name = "ListaMotiviAnnullamentoResponseType")
    public JAXBElement<ListaMotiviAnnullamentoResponseType> createListaMotiviAnnullamentoResponseType(ListaMotiviAnnullamentoResponseType value) {
        return new JAXBElement<ListaMotiviAnnullamentoResponseType>(_ListaMotiviAnnullamentoResponseType_QNAME, ListaMotiviAnnullamentoResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfAllegatoType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://it.gruppoinit/Protocollazione", name = "ArrayOfAllegatoType")
    public JAXBElement<ArrayOfAllegatoType> createArrayOfAllegatoType(ArrayOfAllegatoType value) {
        return new JAXBElement<ArrayOfAllegatoType>(_ArrayOfAllegatoType_QNAME, ArrayOfAllegatoType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "dateTime")
    public JAXBElement<XMLGregorianCalendar> createDateTime(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_DateTime_QNAME, XMLGregorianCalendar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListaTipiClassificaClassifica }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://it.gruppoinit/Protocollazione", name = "ListaTipiClassificaClassifica")
    public JAXBElement<ListaTipiClassificaClassifica> createListaTipiClassificaClassifica(ListaTipiClassificaClassifica value) {
        return new JAXBElement<ListaTipiClassificaClassifica>(_ListaTipiClassificaClassifica_QNAME, ListaTipiClassificaClassifica.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfDatiAnagraficiType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://it.gruppoinit/Protocollazione", name = "ArrayOfDatiAnagraficiType")
    public JAXBElement<ArrayOfDatiAnagraficiType> createArrayOfDatiAnagraficiType(ArrayOfDatiAnagraficiType value) {
        return new JAXBElement<ArrayOfDatiAnagraficiType>(_ArrayOfDatiAnagraficiType_QNAME, ArrayOfDatiAnagraficiType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QName }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "QName")
    public JAXBElement<QName> createQName(QName value) {
        return new JAXBElement<QName>(_QName_QNAME, QName.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedShort")
    public JAXBElement<Integer> createUnsignedShort(Integer value) {
        return new JAXBElement<Integer>(_UnsignedShort_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Short }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "short")
    public JAXBElement<Short> createShort(Short value) {
        return new JAXBElement<Short>(_Short_QNAME, Short.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListaTipiDocumentoResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://it.gruppoinit/Protocollazione", name = "ListaTipiDocumentoResponseType")
    public JAXBElement<ListaTipiDocumentoResponseType> createListaTipiDocumentoResponseType(ListaTipiDocumentoResponseType value) {
        return new JAXBElement<ListaTipiDocumentoResponseType>(_ListaTipiDocumentoResponseType_QNAME, ListaTipiDocumentoResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EnumAnnullatoType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://it.gruppoinit/Protocollazione", name = "EnumAnnullatoType")
    public JAXBElement<EnumAnnullatoType> createEnumAnnullatoType(EnumAnnullatoType value) {
        return new JAXBElement<EnumAnnullatoType>(_EnumAnnullatoType_QNAME, EnumAnnullatoType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DatiAnagraficiType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://it.gruppoinit/Protocollazione", name = "DatiAnagraficiType")
    public JAXBElement<DatiAnagraficiType> createDatiAnagraficiType(DatiAnagraficiType value) {
        return new JAXBElement<DatiAnagraficiType>(_DatiAnagraficiType_QNAME, DatiAnagraficiType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DatiProtocolloFascicolatoResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://it.gruppoinit/Protocollazione", name = "DatiProtocolloFascicolatoResponseType")
    public JAXBElement<DatiProtocolloFascicolatoResponseType> createDatiProtocolloFascicolatoResponseType(DatiProtocolloFascicolatoResponseType value) {
        return new JAXBElement<DatiProtocolloFascicolatoResponseType>(_DatiProtocolloFascicolatoResponseType_QNAME, DatiProtocolloFascicolatoResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListaFascicoliResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://it.gruppoinit/Protocollazione", name = "ListaFascicoliResponseType")
    public JAXBElement<ListaFascicoliResponseType> createListaFascicoliResponseType(ListaFascicoliResponseType value) {
        return new JAXBElement<ListaFascicoliResponseType>(_ListaFascicoliResponseType_QNAME, ListaFascicoliResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListaTipiDocumentoDocumentoType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://it.gruppoinit/Protocollazione", name = "ListaTipiDocumentoDocumentoType")
    public JAXBElement<ListaTipiDocumentoDocumentoType> createListaTipiDocumentoDocumentoType(ListaTipiDocumentoDocumentoType value) {
        return new JAXBElement<ListaTipiDocumentoDocumentoType>(_ListaTipiDocumentoDocumentoType_QNAME, ListaTipiDocumentoDocumentoType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfMittDestOutType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://it.gruppoinit/Protocollazione", name = "ArrayOfMittDestOutType")
    public JAXBElement<ArrayOfMittDestOutType> createArrayOfMittDestOutType(ArrayOfMittDestOutType value) {
        return new JAXBElement<ArrayOfMittDestOutType>(_ArrayOfMittDestOutType_QNAME, ArrayOfMittDestOutType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DatiFascType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://it.gruppoinit/Protocollazione", name = "DatiFascType")
    public JAXBElement<DatiFascType> createDatiFascType(DatiFascType value) {
        return new JAXBElement<DatiFascType>(_DatiFascType_QNAME, DatiFascType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedInt")
    public JAXBElement<Long> createUnsignedInt(Long value) {
        return new JAXBElement<Long>(_UnsignedInt_QNAME, Long.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfListaTipiClassificaClassifica }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://it.gruppoinit/Protocollazione", name = "ArrayOfListaTipiClassificaClassifica")
    public JAXBElement<ArrayOfListaTipiClassificaClassifica> createArrayOfListaTipiClassificaClassifica(ArrayOfListaTipiClassificaClassifica value) {
        return new JAXBElement<ArrayOfListaTipiClassificaClassifica>(_ArrayOfListaTipiClassificaClassifica_QNAME, ArrayOfListaTipiClassificaClassifica.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfListaTipiDocumentoDocumentoType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://it.gruppoinit/Protocollazione", name = "ArrayOfListaTipiDocumentoDocumentoType")
    public JAXBElement<ArrayOfListaTipiDocumentoDocumentoType> createArrayOfListaTipiDocumentoDocumentoType(ArrayOfListaTipiDocumentoDocumentoType value) {
        return new JAXBElement<ArrayOfListaTipiDocumentoDocumentoType>(_ArrayOfListaTipiDocumentoDocumentoType_QNAME, ArrayOfListaTipiDocumentoDocumentoType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ErroreProtocolloType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://it.gruppoinit/Protocollazione", name = "ErroreProtocolloType")
    public JAXBElement<ErroreProtocolloType> createErroreProtocolloType(ErroreProtocolloType value) {
        return new JAXBElement<ErroreProtocolloType>(_ErroreProtocolloType_QNAME, ErroreProtocolloType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "decimal")
    public JAXBElement<BigDecimal> createDecimal(BigDecimal value) {
        return new JAXBElement<BigDecimal>(_Decimal_QNAME, BigDecimal.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListaMotiviAnnullamentoMotivoAnnullamentoType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://it.gruppoinit/Protocollazione", name = "ListaMotiviAnnullamentoMotivoAnnullamentoType")
    public JAXBElement<ListaMotiviAnnullamentoMotivoAnnullamentoType> createListaMotiviAnnullamentoMotivoAnnullamentoType(ListaMotiviAnnullamentoMotivoAnnullamentoType value) {
        return new JAXBElement<ListaMotiviAnnullamentoMotivoAnnullamentoType>(_ListaMotiviAnnullamentoMotivoAnnullamentoType_QNAME, ListaMotiviAnnullamentoMotivoAnnullamentoType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfDatiFascType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://it.gruppoinit/Protocollazione", name = "ArrayOfDatiFascType")
    public JAXBElement<ArrayOfDatiFascType> createArrayOfDatiFascType(ArrayOfDatiFascType value) {
        return new JAXBElement<ArrayOfDatiFascType>(_ArrayOfDatiFascType_QNAME, ArrayOfDatiFascType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DatiProtocolloAnnullatoResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://it.gruppoinit/Protocollazione", name = "DatiProtocolloAnnullatoResponseType")
    public JAXBElement<DatiProtocolloAnnullatoResponseType> createDatiProtocolloAnnullatoResponseType(DatiProtocolloAnnullatoResponseType value) {
        return new JAXBElement<DatiProtocolloAnnullatoResponseType>(_DatiProtocolloAnnullatoResponseType_QNAME, DatiProtocolloAnnullatoResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "guid")
    public JAXBElement<String> createGuid(String value) {
        return new JAXBElement<String>(_Guid_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DatiMittentiType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://it.gruppoinit/Protocollazione", name = "DatiMittentiType")
    public JAXBElement<DatiMittentiType> createDatiMittentiType(DatiMittentiType value) {
        return new JAXBElement<DatiMittentiType>(_DatiMittentiType_QNAME, DatiMittentiType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfAllegatoResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://it.gruppoinit/Protocollazione", name = "ArrayOfAllegatoResponseType")
    public JAXBElement<ArrayOfAllegatoResponseType> createArrayOfAllegatoResponseType(ArrayOfAllegatoResponseType value) {
        return new JAXBElement<ArrayOfAllegatoResponseType>(_ArrayOfAllegatoResponseType_QNAME, ArrayOfAllegatoResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Duration }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "duration")
    public JAXBElement<Duration> createDuration(Duration value) {
        return new JAXBElement<Duration>(_Duration_QNAME, Duration.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "string")
    public JAXBElement<String> createString(String value) {
        return new JAXBElement<String>(_String_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedLong")
    public JAXBElement<BigInteger> createUnsignedLong(BigInteger value) {
        return new JAXBElement<BigInteger>(_UnsignedLong_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DatiProtocolloLettoResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://it.gruppoinit/Protocollazione", name = "DatiProtocolloLettoResponseType")
    public JAXBElement<DatiProtocolloLettoResponseType> createDatiProtocolloLettoResponseType(DatiProtocolloLettoResponseType value) {
        return new JAXBElement<DatiProtocolloLettoResponseType>(_DatiProtocolloLettoResponseType_QNAME, DatiProtocolloLettoResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreaUnitaDocumentaleResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://it.gruppoinit/Protocollazione", name = "CreaUnitaDocumentaleResponseType")
    public JAXBElement<CreaUnitaDocumentaleResponseType> createCreaUnitaDocumentaleResponseType(CreaUnitaDocumentaleResponseType value) {
        return new JAXBElement<CreaUnitaDocumentaleResponseType>(_CreaUnitaDocumentaleResponseType_QNAME, CreaUnitaDocumentaleResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MittDestOutType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://it.gruppoinit/Protocollazione", name = "MittDestOutType")
    public JAXBElement<MittDestOutType> createMittDestOutType(MittDestOutType value) {
        return new JAXBElement<MittDestOutType>(_MittDestOutType_QNAME, MittDestOutType.class, null, value);
    }

}
