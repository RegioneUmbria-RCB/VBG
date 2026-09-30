
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
    private final static QName _EnumStatusType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "EnumStatusType");
    private final static QName _Char_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "char");
    private final static QName _ArrayOfProtocolloAnagrafe_QNAME = new QName("http://schemas.datacontract.org/2004/07/VBG.Backend.Protocollo.AppLogic.Shared.Data", "ArrayOfProtocolloAnagrafe");
    private final static QName _DatiProtocolloResponseType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "DatiProtocolloResponseType");
    private final static QName _ArrayOfMetadatoType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "ArrayOfMetadatoType");
    private final static QName _Float_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "float");
    private final static QName _Long_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "long");
    private final static QName _DatiMittentiXmlType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "DatiMittentiXmlType");
    private final static QName _EseguiAccettazioneResponseType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "EseguiAccettazioneResponseType");
    private final static QName _Base64Binary_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "base64Binary");
    private final static QName _Byte_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "byte");
    private final static QName _Boolean_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "boolean");
    private final static QName _ArrayOfFirmatario_QNAME = new QName("http://it.gruppoinit/Protocollazione", "ArrayOfFirmatario");
    private final static QName _DatiRequestType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "DatiRequestType");
    private final static QName _EnumEsitatoType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "EnumEsitatoType");
    private final static QName _UnsignedByte_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedByte");
    private final static QName _AnyType_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "anyType");
    private final static QName _Int_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "int");
    private final static QName _MetadatoType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "MetadatoType");
    private final static QName _EtichetteResponseType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "EtichetteResponseType");
    private final static QName _AllegatoType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "AllegatoType");
    private final static QName _CreaUnitaDocumentaleRequestType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "CreaUnitaDocumentaleRequestType");
    private final static QName _ProtocollazioneMovimentoXmlRequestType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "ProtocollazioneMovimentoXmlRequestType");
    private final static QName _AllegatoResponseType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "AllegatoResponseType");
    private final static QName _DatiMailType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "DatiMailType");
    private final static QName _Double_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "double");
    private final static QName _DatiFascicoloResponseType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "DatiFascicoloResponseType");
    private final static QName _ArrayOfint_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "ArrayOfint");
    private final static QName _ListaMotiviAnnullamentoResponseType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "ListaMotiviAnnullamentoResponseType");
    private final static QName _LeggiProtocolloRequest_QNAME = new QName("http://schemas.datacontract.org/2004/07/Init.SIGePro.Protocollo.WsDataClass", "LeggiProtocolloRequest");
    private final static QName _ProtocolloAnagrafe_QNAME = new QName("http://schemas.datacontract.org/2004/07/VBG.Backend.Protocollo.AppLogic.Shared.Data", "ProtocolloAnagrafe");
    private final static QName _DatiDestinatariXmlType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "DatiDestinatariXmlType");
    private final static QName _ProtocolloAmministrazioni_QNAME = new QName("http://schemas.datacontract.org/2004/07/VBG.Backend.Protocollo.AppLogic.Shared.Data", "ProtocolloAmministrazioni");
    private final static QName _ArrayOfAllegatoType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "ArrayOfAllegatoType");
    private final static QName _DateTime_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "dateTime");
    private final static QName _ListaTipiClassificaClassifica_QNAME = new QName("http://it.gruppoinit/Protocollazione", "ListaTipiClassificaClassifica");
    private final static QName _ArrayOfDatiProtocolloLettoResponseType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "ArrayOfDatiProtocolloLettoResponseType");
    private final static QName _QName_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "QName");
    private final static QName _ArrayOfDatiAnagraficiType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "ArrayOfDatiAnagraficiType");
    private final static QName _UnsignedShort_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedShort");
    private final static QName _Short_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "short");
    private final static QName _ListaTipiDocumentoResponseType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "ListaTipiDocumentoResponseType");
    private final static QName _ListaTipiClassificaType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "ListaTipiClassificaType");
    private final static QName _EnumAnnullatoType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "EnumAnnullatoType");
    private final static QName _DatiAnagraficiType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "DatiAnagraficiType");
    private final static QName _Firmatario_QNAME = new QName("http://it.gruppoinit/Protocollazione", "Firmatario");
    private final static QName _DatiProtocolloFascicolatoResponseType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "DatiProtocolloFascicolatoResponseType");
    private final static QName _ListaFascicoliResponseType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "ListaFascicoliResponseType");
    private final static QName _ListaTipiDocumentoDocumentoType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "ListaTipiDocumentoDocumentoType");
    private final static QName _ArrayOfMittDestOutType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "ArrayOfMittDestOutType");
    private final static QName _DatiFascType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "DatiFascType");
    private final static QName _ProtocolloComune_QNAME = new QName("http://schemas.datacontract.org/2004/07/VBG.Backend.Protocollo.AppLogic.Shared.Data", "ProtocolloComune");
    private final static QName _UnsignedInt_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedInt");
    private final static QName _ArrayOfListaTipiClassificaClassifica_QNAME = new QName("http://it.gruppoinit/Protocollazione", "ArrayOfListaTipiClassificaClassifica");
    private final static QName _ArrayOfListaTipiDocumentoDocumentoType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "ArrayOfListaTipiDocumentoDocumentoType");
    private final static QName _ListaFirmatari_QNAME = new QName("http://it.gruppoinit/Protocollazione", "ListaFirmatari");
    private final static QName _ErroreProtocolloType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "ErroreProtocolloType");
    private final static QName _Decimal_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "decimal");
    private final static QName _ListaMotiviAnnullamentoMotivoAnnullamentoType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "ListaMotiviAnnullamentoMotivoAnnullamentoType");
    private final static QName _ArrayOfDatiFascType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "ArrayOfDatiFascType");
    private final static QName _DatiProtocolloAnnullatoResponseType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "DatiProtocolloAnnullatoResponseType");
    private final static QName _DatiProtocolloEsitatoResponseType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "DatiProtocolloEsitatoResponseType");
    private final static QName _DatiMittentiType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "DatiMittentiType");
    private final static QName _Guid_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "guid");
    private final static QName _ArrayOfAllegatoResponseType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "ArrayOfAllegatoResponseType");
    private final static QName _Duration_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "duration");
    private final static QName _String_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "string");
    private final static QName _UnsignedLong_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedLong");
    private final static QName _DatiProtocolloLettoResponseType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "DatiProtocolloLettoResponseType");
    private final static QName _CreaUnitaDocumentaleResponseType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "CreaUnitaDocumentaleResponseType");
    private final static QName _ArrayOfProtocolloAmministrazioni_QNAME = new QName("http://schemas.datacontract.org/2004/07/VBG.Backend.Protocollo.AppLogic.Shared.Data", "ArrayOfProtocolloAmministrazioni");
    private final static QName _MittDestOutType_QNAME = new QName("http://it.gruppoinit/Protocollazione", "MittDestOutType");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.gruppoinit.protocollo.schemas.messages
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link LeggiProtocolloRequest }
     * 
     */
    public LeggiProtocolloRequest createLeggiProtocolloRequest() {
        return new LeggiProtocolloRequest();
    }

    /**
     * Create an instance of {@link ProtocolloAnagrafe }
     * 
     */
    public ProtocolloAnagrafe createProtocolloAnagrafe() {
        return new ProtocolloAnagrafe();
    }

    /**
     * Create an instance of {@link ArrayOfProtocolloAmministrazioni }
     * 
     */
    public ArrayOfProtocolloAmministrazioni createArrayOfProtocolloAmministrazioni() {
        return new ArrayOfProtocolloAmministrazioni();
    }

    /**
     * Create an instance of {@link ArrayOfProtocolloAnagrafe }
     * 
     */
    public ArrayOfProtocolloAnagrafe createArrayOfProtocolloAnagrafe() {
        return new ArrayOfProtocolloAnagrafe();
    }

    /**
     * Create an instance of {@link ProtocolloComune }
     * 
     */
    public ProtocolloComune createProtocolloComune() {
        return new ProtocolloComune();
    }

    /**
     * Create an instance of {@link ProtocolloAmministrazioni }
     * 
     */
    public ProtocolloAmministrazioni createProtocolloAmministrazioni() {
        return new ProtocolloAmministrazioni();
    }

    /**
     * Create an instance of {@link ArrayOfint }
     * 
     */
    public ArrayOfint createArrayOfint() {
        return new ArrayOfint();
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
     * Create an instance of {@link ArrayOfDatiProtocolloLettoResponseType }
     * 
     */
    public ArrayOfDatiProtocolloLettoResponseType createArrayOfDatiProtocolloLettoResponseType() {
        return new ArrayOfDatiProtocolloLettoResponseType();
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
     * Create an instance of {@link LeggiProtocolloUORuolo }
     * 
     */
    public LeggiProtocolloUORuolo createLeggiProtocolloUORuolo() {
        return new LeggiProtocolloUORuolo();
    }

    /**
     * Create an instance of {@link EseguiAccettazione }
     * 
     */
    public EseguiAccettazione createEseguiAccettazione() {
        return new EseguiAccettazione();
    }

    /**
     * Create an instance of {@link CreaCopieResponse }
     * 
     */
    public CreaCopieResponse createCreaCopieResponse() {
        return new CreaCopieResponse();
    }

    /**
     * Create an instance of {@link DatiProtocolloResponseType }
     * 
     */
    public DatiProtocolloResponseType createDatiProtocolloResponseType() {
        return new DatiProtocolloResponseType();
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
     * Create an instance of {@link InvioPecResponse }
     * 
     */
    public InvioPecResponse createInvioPecResponse() {
        return new InvioPecResponse();
    }

    /**
     * Create an instance of {@link LeggiProtocolloUORuoloResponse }
     * 
     */
    public LeggiProtocolloUORuoloResponse createLeggiProtocolloUORuoloResponse() {
        return new LeggiProtocolloUORuoloResponse();
    }

    /**
     * Create an instance of {@link LeggiProtocolloConDataResponse }
     * 
     */
    public LeggiProtocolloConDataResponse createLeggiProtocolloConDataResponse() {
        return new LeggiProtocolloConDataResponse();
    }

    /**
     * Create an instance of {@link ArrayOfListaTipiClassificaClassifica }
     * 
     */
    public ArrayOfListaTipiClassificaClassifica createArrayOfListaTipiClassificaClassifica() {
        return new ArrayOfListaTipiClassificaClassifica();
    }

    /**
     * Create an instance of {@link ProtocollazioneIstanzaXmlResponse }
     * 
     */
    public ProtocollazioneIstanzaXmlResponse createProtocollazioneIstanzaXmlResponse() {
        return new ProtocollazioneIstanzaXmlResponse();
    }

    /**
     * Create an instance of {@link SearchFascicoli }
     * 
     */
    public SearchFascicoli createSearchFascicoli() {
        return new SearchFascicoli();
    }

    /**
     * Create an instance of {@link DatiFascType }
     * 
     */
    public DatiFascType createDatiFascType() {
        return new DatiFascType();
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
     * Create an instance of {@link ArrayOfAllegatoResponseType }
     * 
     */
    public ArrayOfAllegatoResponseType createArrayOfAllegatoResponseType() {
        return new ArrayOfAllegatoResponseType();
    }

    /**
     * Create an instance of {@link IsAnnullato }
     * 
     */
    public IsAnnullato createIsAnnullato() {
        return new IsAnnullato();
    }

    /**
     * Create an instance of {@link DatiProtocolloEsitatoResponseType }
     * 
     */
    public DatiProtocolloEsitatoResponseType createDatiProtocolloEsitatoResponseType() {
        return new DatiProtocolloEsitatoResponseType();
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
     * Create an instance of {@link DatiMittentiXmlType }
     * 
     */
    public DatiMittentiXmlType createDatiMittentiXmlType() {
        return new DatiMittentiXmlType();
    }

    /**
     * Create an instance of {@link CreaCopie }
     * 
     */
    public CreaCopie createCreaCopie() {
        return new CreaCopie();
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
     * Create an instance of {@link IsEsitato }
     * 
     */
    public IsEsitato createIsEsitato() {
        return new IsEsitato();
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
     * Create an instance of {@link AnnullaProtocolloResponse }
     * 
     */
    public AnnullaProtocolloResponse createAnnullaProtocolloResponse() {
        return new AnnullaProtocolloResponse();
    }

    /**
     * Create an instance of {@link SearchFascicoliResponse }
     * 
     */
    public SearchFascicoliResponse createSearchFascicoliResponse() {
        return new SearchFascicoliResponse();
    }

    /**
     * Create an instance of {@link FascicolazioneMovimentoResponse }
     * 
     */
    public FascicolazioneMovimentoResponse createFascicolazioneMovimentoResponse() {
        return new FascicolazioneMovimentoResponse();
    }

    /**
     * Create an instance of {@link FascicolazioneMovimentoXmlResponse }
     * 
     */
    public FascicolazioneMovimentoXmlResponse createFascicolazioneMovimentoXmlResponse() {
        return new FascicolazioneMovimentoXmlResponse();
    }

    /**
     * Create an instance of {@link CreaUnitaDocumentaleIstanzaResponse }
     * 
     */
    public CreaUnitaDocumentaleIstanzaResponse createCreaUnitaDocumentaleIstanzaResponse() {
        return new CreaUnitaDocumentaleIstanzaResponse();
    }

    /**
     * Create an instance of {@link ProtocollazioneMovimentoXml }
     * 
     */
    public ProtocollazioneMovimentoXml createProtocollazioneMovimentoXml() {
        return new ProtocollazioneMovimentoXml();
    }

    /**
     * Create an instance of {@link ProtocollazioneMovimentoXmlRequestType }
     * 
     */
    public ProtocollazioneMovimentoXmlRequestType createProtocollazioneMovimentoXmlRequestType() {
        return new ProtocollazioneMovimentoXmlRequestType();
    }

    /**
     * Create an instance of {@link LeggiProtocolloResponse }
     * 
     */
    public LeggiProtocolloResponse createLeggiProtocolloResponse() {
        return new LeggiProtocolloResponse();
    }

    /**
     * Create an instance of {@link AggiungiAllegatiResponse }
     * 
     */
    public AggiungiAllegatiResponse createAggiungiAllegatiResponse() {
        return new AggiungiAllegatiResponse();
    }

    /**
     * Create an instance of {@link EtichetteResponseType }
     * 
     */
    public EtichetteResponseType createEtichetteResponseType() {
        return new EtichetteResponseType();
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
     * Create an instance of {@link CreaUnitaDocumentaleRequestType }
     * 
     */
    public CreaUnitaDocumentaleRequestType createCreaUnitaDocumentaleRequestType() {
        return new CreaUnitaDocumentaleRequestType();
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
     * Create an instance of {@link DatiDestinatariXmlType }
     * 
     */
    public DatiDestinatariXmlType createDatiDestinatariXmlType() {
        return new DatiDestinatariXmlType();
    }

    /**
     * Create an instance of {@link ArrayOfAllegatoType }
     * 
     */
    public ArrayOfAllegatoType createArrayOfAllegatoType() {
        return new ArrayOfAllegatoType();
    }

    /**
     * Create an instance of {@link CambiaFascicoloIstanzaXml }
     * 
     */
    public CambiaFascicoloIstanzaXml createCambiaFascicoloIstanzaXml() {
        return new CambiaFascicoloIstanzaXml();
    }

    /**
     * Create an instance of {@link ArrayOfDatiAnagraficiType }
     * 
     */
    public ArrayOfDatiAnagraficiType createArrayOfDatiAnagraficiType() {
        return new ArrayOfDatiAnagraficiType();
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
     * Create an instance of {@link InvioPec }
     * 
     */
    public InvioPec createInvioPec() {
        return new InvioPec();
    }

    /**
     * Create an instance of {@link GetFirmatari }
     * 
     */
    public GetFirmatari createGetFirmatari() {
        return new GetFirmatari();
    }

    /**
     * Create an instance of {@link ProtocollazioneIstanza }
     * 
     */
    public ProtocollazioneIstanza createProtocollazioneIstanza() {
        return new ProtocollazioneIstanza();
    }

    /**
     * Create an instance of {@link DatiMittentiType }
     * 
     */
    public DatiMittentiType createDatiMittentiType() {
        return new DatiMittentiType();
    }

    /**
     * Create an instance of {@link GetFirmatariResponse }
     * 
     */
    public GetFirmatariResponse createGetFirmatariResponse() {
        return new GetFirmatariResponse();
    }

    /**
     * Create an instance of {@link ListaFirmatari }
     * 
     */
    public ListaFirmatari createListaFirmatari() {
        return new ListaFirmatari();
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
     * Create an instance of {@link ProtocollazioneComunicazioneGraduatoriaResponse }
     * 
     */
    public ProtocollazioneComunicazioneGraduatoriaResponse createProtocollazioneComunicazioneGraduatoriaResponse() {
        return new ProtocollazioneComunicazioneGraduatoriaResponse();
    }

    /**
     * Create an instance of {@link LeggiAllegatoUORuoloResponse }
     * 
     */
    public LeggiAllegatoUORuoloResponse createLeggiAllegatoUORuoloResponse() {
        return new LeggiAllegatoUORuoloResponse();
    }

    /**
     * Create an instance of {@link ListaTipiClassificaType }
     * 
     */
    public ListaTipiClassificaType createListaTipiClassificaType() {
        return new ListaTipiClassificaType();
    }

    /**
     * Create an instance of {@link FascicolazioneIstanzaXml }
     * 
     */
    public FascicolazioneIstanzaXml createFascicolazioneIstanzaXml() {
        return new FascicolazioneIstanzaXml();
    }

    /**
     * Create an instance of {@link ProtocollazionePecXmlResponse }
     * 
     */
    public ProtocollazionePecXmlResponse createProtocollazionePecXmlResponse() {
        return new ProtocollazionePecXmlResponse();
    }

    /**
     * Create an instance of {@link ArrayOfMittDestOutType }
     * 
     */
    public ArrayOfMittDestOutType createArrayOfMittDestOutType() {
        return new ArrayOfMittDestOutType();
    }

    /**
     * Create an instance of {@link LeggiAllegatoUORuolo }
     * 
     */
    public LeggiAllegatoUORuolo createLeggiAllegatoUORuolo() {
        return new LeggiAllegatoUORuolo();
    }

    /**
     * Create an instance of {@link ListaTipiDocumentoDocumentoType }
     * 
     */
    public ListaTipiDocumentoDocumentoType createListaTipiDocumentoDocumentoType() {
        return new ListaTipiDocumentoDocumentoType();
    }

    /**
     * Create an instance of {@link DatiProtocolloFascicolatoResponseType }
     * 
     */
    public DatiProtocolloFascicolatoResponseType createDatiProtocolloFascicolatoResponseType() {
        return new DatiProtocolloFascicolatoResponseType();
    }

    /**
     * Create an instance of {@link Firmatario }
     * 
     */
    public Firmatario createFirmatario() {
        return new Firmatario();
    }

    /**
     * Create an instance of {@link DatiAnagraficiType }
     * 
     */
    public DatiAnagraficiType createDatiAnagraficiType() {
        return new DatiAnagraficiType();
    }

    /**
     * Create an instance of {@link LeggiAllegatoStoricoResponse }
     * 
     */
    public LeggiAllegatoStoricoResponse createLeggiAllegatoStoricoResponse() {
        return new LeggiAllegatoStoricoResponse();
    }

    /**
     * Create an instance of {@link IsFascicolato }
     * 
     */
    public IsFascicolato createIsFascicolato() {
        return new IsFascicolato();
    }

    /**
     * Create an instance of {@link RecuperaMetadati }
     * 
     */
    public RecuperaMetadati createRecuperaMetadati() {
        return new RecuperaMetadati();
    }

    /**
     * Create an instance of {@link ErroreProtocolloType }
     * 
     */
    public ErroreProtocolloType createErroreProtocolloType() {
        return new ErroreProtocolloType();
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
     * Create an instance of {@link FascicolazioneIstanza }
     * 
     */
    public FascicolazioneIstanza createFascicolazioneIstanza() {
        return new FascicolazioneIstanza();
    }

    /**
     * Create an instance of {@link ProtocollazioneIstanzaResponse }
     * 
     */
    public ProtocollazioneIstanzaResponse createProtocollazioneIstanzaResponse() {
        return new ProtocollazioneIstanzaResponse();
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
     * Create an instance of {@link DatiProtocolloAnnullatoResponseType }
     * 
     */
    public DatiProtocolloAnnullatoResponseType createDatiProtocolloAnnullatoResponseType() {
        return new DatiProtocolloAnnullatoResponseType();
    }

    /**
     * Create an instance of {@link GetClassificheResponse }
     * 
     */
    public GetClassificheResponse createGetClassificheResponse() {
        return new GetClassificheResponse();
    }

    /**
     * Create an instance of {@link LeggiAllegatoStorico }
     * 
     */
    public LeggiAllegatoStorico createLeggiAllegatoStorico() {
        return new LeggiAllegatoStorico();
    }

    /**
     * Create an instance of {@link FascicolazioneMovimentoXml }
     * 
     */
    public FascicolazioneMovimentoXml createFascicolazioneMovimentoXml() {
        return new FascicolazioneMovimentoXml();
    }

    /**
     * Create an instance of {@link DatiProtocolloLettoResponseType }
     * 
     */
    public DatiProtocolloLettoResponseType createDatiProtocolloLettoResponseType() {
        return new DatiProtocolloLettoResponseType();
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
     * Create an instance of {@link ArrayOfMetadatoType }
     * 
     */
    public ArrayOfMetadatoType createArrayOfMetadatoType() {
        return new ArrayOfMetadatoType();
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
     * Create an instance of {@link IsAnnullatoResponse }
     * 
     */
    public IsAnnullatoResponse createIsAnnullatoResponse() {
        return new IsAnnullatoResponse();
    }

    /**
     * Create an instance of {@link ProtocollazioneMovimento }
     * 
     */
    public ProtocollazioneMovimento createProtocollazioneMovimento() {
        return new ProtocollazioneMovimento();
    }

    /**
     * Create an instance of {@link EseguiAccettazioneResponseType }
     * 
     */
    public EseguiAccettazioneResponseType createEseguiAccettazioneResponseType() {
        return new EseguiAccettazioneResponseType();
    }

    /**
     * Create an instance of {@link RecuperaMetadatiResponse }
     * 
     */
    public RecuperaMetadatiResponse createRecuperaMetadatiResponse() {
        return new RecuperaMetadatiResponse();
    }

    /**
     * Create an instance of {@link RegistrazioneIstanzaXmlResponse }
     * 
     */
    public RegistrazioneIstanzaXmlResponse createRegistrazioneIstanzaXmlResponse() {
        return new RegistrazioneIstanzaXmlResponse();
    }

    /**
     * Create an instance of {@link FascicolazioneXmlResponse }
     * 
     */
    public FascicolazioneXmlResponse createFascicolazioneXmlResponse() {
        return new FascicolazioneXmlResponse();
    }

    /**
     * Create an instance of {@link ArrayOfFirmatario }
     * 
     */
    public ArrayOfFirmatario createArrayOfFirmatario() {
        return new ArrayOfFirmatario();
    }

    /**
     * Create an instance of {@link EseguiAccettazioneResponse }
     * 
     */
    public EseguiAccettazioneResponse createEseguiAccettazioneResponse() {
        return new EseguiAccettazioneResponse();
    }

    /**
     * Create an instance of {@link MettiAllaFirmaXmlResponse }
     * 
     */
    public MettiAllaFirmaXmlResponse createMettiAllaFirmaXmlResponse() {
        return new MettiAllaFirmaXmlResponse();
    }

    /**
     * Create an instance of {@link IsEsitatoResponse }
     * 
     */
    public IsEsitatoResponse createIsEsitatoResponse() {
        return new IsEsitatoResponse();
    }

    /**
     * Create an instance of {@link GetClassifiche }
     * 
     */
    public GetClassifiche createGetClassifiche() {
        return new GetClassifiche();
    }

    /**
     * Create an instance of {@link ProtocollazioneComunicazioneGraduatoria }
     * 
     */
    public ProtocollazioneComunicazioneGraduatoria createProtocollazioneComunicazioneGraduatoria() {
        return new ProtocollazioneComunicazioneGraduatoria();
    }

    /**
     * Create an instance of {@link AllegatoType }
     * 
     */
    public AllegatoType createAllegatoType() {
        return new AllegatoType();
    }

    /**
     * Create an instance of {@link MetadatoType }
     * 
     */
    public MetadatoType createMetadatoType() {
        return new MetadatoType();
    }

    /**
     * Create an instance of {@link ProtocollazionePecXml }
     * 
     */
    public ProtocollazionePecXml createProtocollazionePecXml() {
        return new ProtocollazionePecXml();
    }

    /**
     * Create an instance of {@link AggiungiAllegati }
     * 
     */
    public AggiungiAllegati createAggiungiAllegati() {
        return new AggiungiAllegati();
    }

    /**
     * Create an instance of {@link CreaUnitaDocumentaleIstanza }
     * 
     */
    public CreaUnitaDocumentaleIstanza createCreaUnitaDocumentaleIstanza() {
        return new CreaUnitaDocumentaleIstanza();
    }

    /**
     * Create an instance of {@link DatiMailType }
     * 
     */
    public DatiMailType createDatiMailType() {
        return new DatiMailType();
    }

    /**
     * Create an instance of {@link StampaEtichetteResponse }
     * 
     */
    public StampaEtichetteResponse createStampaEtichetteResponse() {
        return new StampaEtichetteResponse();
    }

    /**
     * Create an instance of {@link FascicolazioneXml }
     * 
     */
    public FascicolazioneXml createFascicolazioneXml() {
        return new FascicolazioneXml();
    }

    /**
     * Create an instance of {@link LeggiProtocolloConData }
     * 
     */
    public LeggiProtocolloConData createLeggiProtocolloConData() {
        return new LeggiProtocolloConData();
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
     * Create an instance of {@link JAXBElement }{@code <}{@link EnumStatusType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://it.gruppoinit/Protocollazione", name = "EnumStatusType")
    public JAXBElement<EnumStatusType> createEnumStatusType(EnumStatusType value) {
        return new JAXBElement<EnumStatusType>(_EnumStatusType_QNAME, EnumStatusType.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfProtocolloAnagrafe }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VBG.Backend.Protocollo.AppLogic.Shared.Data", name = "ArrayOfProtocolloAnagrafe")
    public JAXBElement<ArrayOfProtocolloAnagrafe> createArrayOfProtocolloAnagrafe(ArrayOfProtocolloAnagrafe value) {
        return new JAXBElement<ArrayOfProtocolloAnagrafe>(_ArrayOfProtocolloAnagrafe_QNAME, ArrayOfProtocolloAnagrafe.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfMetadatoType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://it.gruppoinit/Protocollazione", name = "ArrayOfMetadatoType")
    public JAXBElement<ArrayOfMetadatoType> createArrayOfMetadatoType(ArrayOfMetadatoType value) {
        return new JAXBElement<ArrayOfMetadatoType>(_ArrayOfMetadatoType_QNAME, ArrayOfMetadatoType.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link DatiMittentiXmlType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://it.gruppoinit/Protocollazione", name = "DatiMittentiXmlType")
    public JAXBElement<DatiMittentiXmlType> createDatiMittentiXmlType(DatiMittentiXmlType value) {
        return new JAXBElement<DatiMittentiXmlType>(_DatiMittentiXmlType_QNAME, DatiMittentiXmlType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EseguiAccettazioneResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://it.gruppoinit/Protocollazione", name = "EseguiAccettazioneResponseType")
    public JAXBElement<EseguiAccettazioneResponseType> createEseguiAccettazioneResponseType(EseguiAccettazioneResponseType value) {
        return new JAXBElement<EseguiAccettazioneResponseType>(_EseguiAccettazioneResponseType_QNAME, EseguiAccettazioneResponseType.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfFirmatario }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://it.gruppoinit/Protocollazione", name = "ArrayOfFirmatario")
    public JAXBElement<ArrayOfFirmatario> createArrayOfFirmatario(ArrayOfFirmatario value) {
        return new JAXBElement<ArrayOfFirmatario>(_ArrayOfFirmatario_QNAME, ArrayOfFirmatario.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link EnumEsitatoType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://it.gruppoinit/Protocollazione", name = "EnumEsitatoType")
    public JAXBElement<EnumEsitatoType> createEnumEsitatoType(EnumEsitatoType value) {
        return new JAXBElement<EnumEsitatoType>(_EnumEsitatoType_QNAME, EnumEsitatoType.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link MetadatoType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://it.gruppoinit/Protocollazione", name = "MetadatoType")
    public JAXBElement<MetadatoType> createMetadatoType(MetadatoType value) {
        return new JAXBElement<MetadatoType>(_MetadatoType_QNAME, MetadatoType.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link CreaUnitaDocumentaleRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://it.gruppoinit/Protocollazione", name = "CreaUnitaDocumentaleRequestType")
    public JAXBElement<CreaUnitaDocumentaleRequestType> createCreaUnitaDocumentaleRequestType(CreaUnitaDocumentaleRequestType value) {
        return new JAXBElement<CreaUnitaDocumentaleRequestType>(_CreaUnitaDocumentaleRequestType_QNAME, CreaUnitaDocumentaleRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProtocollazioneMovimentoXmlRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://it.gruppoinit/Protocollazione", name = "ProtocollazioneMovimentoXmlRequestType")
    public JAXBElement<ProtocollazioneMovimentoXmlRequestType> createProtocollazioneMovimentoXmlRequestType(ProtocollazioneMovimentoXmlRequestType value) {
        return new JAXBElement<ProtocollazioneMovimentoXmlRequestType>(_ProtocollazioneMovimentoXmlRequestType_QNAME, ProtocollazioneMovimentoXmlRequestType.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link DatiMailType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://it.gruppoinit/Protocollazione", name = "DatiMailType")
    public JAXBElement<DatiMailType> createDatiMailType(DatiMailType value) {
        return new JAXBElement<DatiMailType>(_DatiMailType_QNAME, DatiMailType.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link DatiFascicoloResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://it.gruppoinit/Protocollazione", name = "DatiFascicoloResponseType")
    public JAXBElement<DatiFascicoloResponseType> createDatiFascicoloResponseType(DatiFascicoloResponseType value) {
        return new JAXBElement<DatiFascicoloResponseType>(_DatiFascicoloResponseType_QNAME, DatiFascicoloResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfint }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/Arrays", name = "ArrayOfint")
    public JAXBElement<ArrayOfint> createArrayOfint(ArrayOfint value) {
        return new JAXBElement<ArrayOfint>(_ArrayOfint_QNAME, ArrayOfint.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link LeggiProtocolloRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Init.SIGePro.Protocollo.WsDataClass", name = "LeggiProtocolloRequest")
    public JAXBElement<LeggiProtocolloRequest> createLeggiProtocolloRequest(LeggiProtocolloRequest value) {
        return new JAXBElement<LeggiProtocolloRequest>(_LeggiProtocolloRequest_QNAME, LeggiProtocolloRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProtocolloAnagrafe }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VBG.Backend.Protocollo.AppLogic.Shared.Data", name = "ProtocolloAnagrafe")
    public JAXBElement<ProtocolloAnagrafe> createProtocolloAnagrafe(ProtocolloAnagrafe value) {
        return new JAXBElement<ProtocolloAnagrafe>(_ProtocolloAnagrafe_QNAME, ProtocolloAnagrafe.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DatiDestinatariXmlType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://it.gruppoinit/Protocollazione", name = "DatiDestinatariXmlType")
    public JAXBElement<DatiDestinatariXmlType> createDatiDestinatariXmlType(DatiDestinatariXmlType value) {
        return new JAXBElement<DatiDestinatariXmlType>(_DatiDestinatariXmlType_QNAME, DatiDestinatariXmlType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProtocolloAmministrazioni }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VBG.Backend.Protocollo.AppLogic.Shared.Data", name = "ProtocolloAmministrazioni")
    public JAXBElement<ProtocolloAmministrazioni> createProtocolloAmministrazioni(ProtocolloAmministrazioni value) {
        return new JAXBElement<ProtocolloAmministrazioni>(_ProtocolloAmministrazioni_QNAME, ProtocolloAmministrazioni.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfDatiProtocolloLettoResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://it.gruppoinit/Protocollazione", name = "ArrayOfDatiProtocolloLettoResponseType")
    public JAXBElement<ArrayOfDatiProtocolloLettoResponseType> createArrayOfDatiProtocolloLettoResponseType(ArrayOfDatiProtocolloLettoResponseType value) {
        return new JAXBElement<ArrayOfDatiProtocolloLettoResponseType>(_ArrayOfDatiProtocolloLettoResponseType_QNAME, ArrayOfDatiProtocolloLettoResponseType.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfDatiAnagraficiType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://it.gruppoinit/Protocollazione", name = "ArrayOfDatiAnagraficiType")
    public JAXBElement<ArrayOfDatiAnagraficiType> createArrayOfDatiAnagraficiType(ArrayOfDatiAnagraficiType value) {
        return new JAXBElement<ArrayOfDatiAnagraficiType>(_ArrayOfDatiAnagraficiType_QNAME, ArrayOfDatiAnagraficiType.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link ListaTipiClassificaType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://it.gruppoinit/Protocollazione", name = "ListaTipiClassificaType")
    public JAXBElement<ListaTipiClassificaType> createListaTipiClassificaType(ListaTipiClassificaType value) {
        return new JAXBElement<ListaTipiClassificaType>(_ListaTipiClassificaType_QNAME, ListaTipiClassificaType.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link Firmatario }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://it.gruppoinit/Protocollazione", name = "Firmatario")
    public JAXBElement<Firmatario> createFirmatario(Firmatario value) {
        return new JAXBElement<Firmatario>(_Firmatario_QNAME, Firmatario.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link ProtocolloComune }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VBG.Backend.Protocollo.AppLogic.Shared.Data", name = "ProtocolloComune")
    public JAXBElement<ProtocolloComune> createProtocolloComune(ProtocolloComune value) {
        return new JAXBElement<ProtocolloComune>(_ProtocolloComune_QNAME, ProtocolloComune.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link ListaFirmatari }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://it.gruppoinit/Protocollazione", name = "ListaFirmatari")
    public JAXBElement<ListaFirmatari> createListaFirmatari(ListaFirmatari value) {
        return new JAXBElement<ListaFirmatari>(_ListaFirmatari_QNAME, ListaFirmatari.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link DatiProtocolloEsitatoResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://it.gruppoinit/Protocollazione", name = "DatiProtocolloEsitatoResponseType")
    public JAXBElement<DatiProtocolloEsitatoResponseType> createDatiProtocolloEsitatoResponseType(DatiProtocolloEsitatoResponseType value) {
        return new JAXBElement<DatiProtocolloEsitatoResponseType>(_DatiProtocolloEsitatoResponseType_QNAME, DatiProtocolloEsitatoResponseType.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "guid")
    public JAXBElement<String> createGuid(String value) {
        return new JAXBElement<String>(_Guid_QNAME, String.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfProtocolloAmministrazioni }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VBG.Backend.Protocollo.AppLogic.Shared.Data", name = "ArrayOfProtocolloAmministrazioni")
    public JAXBElement<ArrayOfProtocolloAmministrazioni> createArrayOfProtocolloAmministrazioni(ArrayOfProtocolloAmministrazioni value) {
        return new JAXBElement<ArrayOfProtocolloAmministrazioni>(_ArrayOfProtocolloAmministrazioni_QNAME, ArrayOfProtocolloAmministrazioni.class, null, value);
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
