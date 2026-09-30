
package it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.feed;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.feed package. 
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

    private final static QName _RichiestaCaricaPosizione_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "RichiestaCaricaPosizione");
    private final static QName _FeedAuthenticatedRequestBase_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "FeedAuthenticatedRequestBase");
    private final static QName _RichiestaCaricaPosizioni_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "RichiestaCaricaPosizioni");
    private final static QName _ArrayOfPosizione_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "ArrayOfPosizione");
    private final static QName _Posizione_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "Posizione");
    private final static QName _ArrayOfAccertamento_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "ArrayOfAccertamento");
    private final static QName _Accertamento_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "Accertamento");
    private final static QName _Creditore_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "Creditore");
    private final static QName _Debitore_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "Debitore");
    private final static QName _Nazione_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "Nazione");
    private final static QName _ArrayOfParametroPosizione_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "ArrayOfParametroPosizione");
    private final static QName _ParametroPosizione_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "ParametroPosizione");
    private final static QName _RispostaCaricaPosizione_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "RispostaCaricaPosizione");
    private final static QName _EsitoDiCaricamento_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "EsitoDiCaricamento");
    private final static QName _ArrayOfErroreDiValidazionePosizione_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "ArrayOfErroreDiValidazionePosizione");
    private final static QName _ErroreDiValidazionePosizione_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "ErroreDiValidazionePosizione");
    private final static QName _Esito_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "Esito");
    private final static QName _SoapFaultOperazioneProibita_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "SoapFaultOperazioneProibita");
    private final static QName _SoapFault_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "SoapFault");
    private final static QName _SoapFaultErroreInterno_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "SoapFaultErroreInterno");
    private final static QName _SoapFaultErroreEasyPA_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "SoapFaultErroreEasyPA");
    private final static QName _RispostaCaricaPosizioneConEsitoArricchito_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "RispostaCaricaPosizioneConEsitoArricchito");
    private final static QName _EsitoDiCaricamentoArricchito_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "EsitoDiCaricamentoArricchito");
    private final static QName _RispostaCaricaPosizioni_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "RispostaCaricaPosizioni");
    private final static QName _ArrayOfEsitoDiCaricamento_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "ArrayOfEsitoDiCaricamento");
    private final static QName _RichiestaValidaPosizioni_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "RichiestaValidaPosizioni");
    private final static QName _RispostaValidaPosizioni_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "RispostaValidaPosizioni");
    private final static QName _ArrayOfEsitoDiValidazione_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "ArrayOfEsitoDiValidazione");
    private final static QName _EsitoDiValidazione_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "EsitoDiValidazione");
    private final static QName _RichiestaRettificaPosizione_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "RichiestaRettificaPosizione");
    private final static QName _RispostaRettificaPosizione_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "RispostaRettificaPosizione");
    private final static QName _RichiestaRimuoviPosizione_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "RichiestaRimuoviPosizione");
    private final static QName _RispostaRimuoviPosizione_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "RispostaRimuoviPosizione");
    private final static QName _ArrayOfRipartizioneDiImportoInAccertamento_QNAME = new QName("http://schemas.datacontract.org/2004/07/PlugAndPay.Feed.Service.Contracts", "ArrayOfRipartizioneDiImportoInAccertamento");
    private final static QName _RipartizioneDiImportoInAccertamento_QNAME = new QName("http://schemas.datacontract.org/2004/07/PlugAndPay.Feed.Service.Contracts", "RipartizioneDiImportoInAccertamento");
    private final static QName _CaricaPosizioneRequest_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "request");
    private final static QName _CaricaPosizioneResponseCaricaPosizioneResult_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "CaricaPosizioneResult");
    private final static QName _CaricaPosizioneConEsitoArricchitoResponseCaricaPosizioneConEsitoArricchitoResult_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "CaricaPosizioneConEsitoArricchitoResult");
    private final static QName _CaricaPosizioniResponseCaricaPosizioniResult_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "CaricaPosizioniResult");
    private final static QName _ValidaPosizioniResponseValidaPosizioniResult_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "ValidaPosizioniResult");
    private final static QName _RettificaPosizioneResponseRettificaPosizioneResult_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "RettificaPosizioneResult");
    private final static QName _RimuoviPosizioneResponseRimuoviPosizioneResult_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "RimuoviPosizioneResult");
    private final static QName _RipartizioneDiImportoInAccertamentoCodice_QNAME = new QName("http://schemas.datacontract.org/2004/07/PlugAndPay.Feed.Service.Contracts", "Codice");
    private final static QName _RipartizioneDiImportoInAccertamentoDescrizione_QNAME = new QName("http://schemas.datacontract.org/2004/07/PlugAndPay.Feed.Service.Contracts", "Descrizione");
    private final static QName _RipartizioneDiImportoInAccertamentoPeriodoDiRiferimento_QNAME = new QName("http://schemas.datacontract.org/2004/07/PlugAndPay.Feed.Service.Contracts", "PeriodoDiRiferimento");
    private final static QName _RichiestaRettificaPosizioneCausale_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "Causale");
    private final static QName _RichiestaRettificaPosizioneDataScandenza_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "DataScandenza");
    private final static QName _RichiestaRettificaPosizioneImportoInCentesimi_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "ImportoInCentesimi");
    private final static QName _RichiestaRettificaPosizioneRipartizioniDiImporto_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "RipartizioniDiImporto");
    private final static QName _EsitoDiValidazioneCodiceRiferimentoCreditore_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "CodiceRiferimentoCreditore");
    private final static QName _EsitoDiValidazioneErroriDiValidazione_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "ErroriDiValidazione");
    private final static QName _EsitoDiValidazioneIdentificativoPosizione_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "IdentificativoPosizione");
    private final static QName _EsitoDiValidazioneTipoRiferimentoCreditore_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "TipoRiferimentoCreditore");
    private final static QName _RispostaValidaPosizioniEsitiDiCaricamento_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "EsitiDiCaricamento");
    private final static QName _EsitoDiCaricamentoArricchitoNumeroAvviso_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "NumeroAvviso");
    private final static QName _ErroreDiValidazionePosizioneCodice_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "Codice");
    private final static QName _ErroreDiValidazionePosizioneDescrizione_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "Descrizione");
    private final static QName _ParametroPosizioneChiave_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "Chiave");
    private final static QName _ParametroPosizioneValore_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "Valore");
    private final static QName _NazioneCodiceIsoNazione_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "CodiceIsoNazione");
    private final static QName _NazioneNomeNazione_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "NomeNazione");
    private final static QName _DebitoreCellulare_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "Cellulare");
    private final static QName _DebitoreCivico_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "Civico");
    private final static QName _DebitoreCodiceAvviamentoPostale_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "CodiceAvviamentoPostale");
    private final static QName _DebitoreEmail_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "Email");
    private final static QName _DebitoreIndirizzo_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "Indirizzo");
    private final static QName _DebitoreLocalita_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "Localita");
    private final static QName _DebitoreProvincia_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "Provincia");
    private final static QName _CreditoreCodiceFiscalePartitaIva_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "CodiceFiscalePartitaIva");
    private final static QName _CreditoreIBAN_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "IBAN");
    private final static QName _PosizioneAccertamenti_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "Accertamenti");
    private final static QName _PosizioneDataScadenza_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "DataScadenza");
    private final static QName _PosizioneParametriPosizione_QNAME = new QName("http://e-fil.eu/PnP/PlugAndPayFeed", "ParametriPosizione");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.feed
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CaricaPosizione }
     * 
     */
    public CaricaPosizione createCaricaPosizione() {
        return new CaricaPosizione();
    }

    /**
     * Create an instance of {@link RichiestaCaricaPosizione }
     * 
     */
    public RichiestaCaricaPosizione createRichiestaCaricaPosizione() {
        return new RichiestaCaricaPosizione();
    }

    /**
     * Create an instance of {@link FeedAuthenticatedRequestBase }
     * 
     */
    public FeedAuthenticatedRequestBase createFeedAuthenticatedRequestBase() {
        return new FeedAuthenticatedRequestBase();
    }

    /**
     * Create an instance of {@link RichiestaCaricaPosizioni }
     * 
     */
    public RichiestaCaricaPosizioni createRichiestaCaricaPosizioni() {
        return new RichiestaCaricaPosizioni();
    }

    /**
     * Create an instance of {@link ArrayOfPosizione }
     * 
     */
    public ArrayOfPosizione createArrayOfPosizione() {
        return new ArrayOfPosizione();
    }

    /**
     * Create an instance of {@link Posizione }
     * 
     */
    public Posizione createPosizione() {
        return new Posizione();
    }

    /**
     * Create an instance of {@link ArrayOfAccertamento }
     * 
     */
    public ArrayOfAccertamento createArrayOfAccertamento() {
        return new ArrayOfAccertamento();
    }

    /**
     * Create an instance of {@link Accertamento }
     * 
     */
    public Accertamento createAccertamento() {
        return new Accertamento();
    }

    /**
     * Create an instance of {@link Creditore }
     * 
     */
    public Creditore createCreditore() {
        return new Creditore();
    }

    /**
     * Create an instance of {@link Debitore }
     * 
     */
    public Debitore createDebitore() {
        return new Debitore();
    }

    /**
     * Create an instance of {@link Nazione }
     * 
     */
    public Nazione createNazione() {
        return new Nazione();
    }

    /**
     * Create an instance of {@link ArrayOfParametroPosizione }
     * 
     */
    public ArrayOfParametroPosizione createArrayOfParametroPosizione() {
        return new ArrayOfParametroPosizione();
    }

    /**
     * Create an instance of {@link ParametroPosizione }
     * 
     */
    public ParametroPosizione createParametroPosizione() {
        return new ParametroPosizione();
    }

    /**
     * Create an instance of {@link CaricaPosizioneResponse }
     * 
     */
    public CaricaPosizioneResponse createCaricaPosizioneResponse() {
        return new CaricaPosizioneResponse();
    }

    /**
     * Create an instance of {@link RispostaCaricaPosizione }
     * 
     */
    public RispostaCaricaPosizione createRispostaCaricaPosizione() {
        return new RispostaCaricaPosizione();
    }

    /**
     * Create an instance of {@link EsitoDiCaricamento }
     * 
     */
    public EsitoDiCaricamento createEsitoDiCaricamento() {
        return new EsitoDiCaricamento();
    }

    /**
     * Create an instance of {@link ArrayOfErroreDiValidazionePosizione }
     * 
     */
    public ArrayOfErroreDiValidazionePosizione createArrayOfErroreDiValidazionePosizione() {
        return new ArrayOfErroreDiValidazionePosizione();
    }

    /**
     * Create an instance of {@link ErroreDiValidazionePosizione }
     * 
     */
    public ErroreDiValidazionePosizione createErroreDiValidazionePosizione() {
        return new ErroreDiValidazionePosizione();
    }

    /**
     * Create an instance of {@link SoapFaultOperazioneProibita }
     * 
     */
    public SoapFaultOperazioneProibita createSoapFaultOperazioneProibita() {
        return new SoapFaultOperazioneProibita();
    }

    /**
     * Create an instance of {@link SoapFault }
     * 
     */
    public SoapFault createSoapFault() {
        return new SoapFault();
    }

    /**
     * Create an instance of {@link SoapFaultErroreInterno }
     * 
     */
    public SoapFaultErroreInterno createSoapFaultErroreInterno() {
        return new SoapFaultErroreInterno();
    }

    /**
     * Create an instance of {@link SoapFaultErroreEasyPA }
     * 
     */
    public SoapFaultErroreEasyPA createSoapFaultErroreEasyPA() {
        return new SoapFaultErroreEasyPA();
    }

    /**
     * Create an instance of {@link CaricaPosizioneConEsitoArricchito }
     * 
     */
    public CaricaPosizioneConEsitoArricchito createCaricaPosizioneConEsitoArricchito() {
        return new CaricaPosizioneConEsitoArricchito();
    }

    /**
     * Create an instance of {@link CaricaPosizioneConEsitoArricchitoResponse }
     * 
     */
    public CaricaPosizioneConEsitoArricchitoResponse createCaricaPosizioneConEsitoArricchitoResponse() {
        return new CaricaPosizioneConEsitoArricchitoResponse();
    }

    /**
     * Create an instance of {@link RispostaCaricaPosizioneConEsitoArricchito }
     * 
     */
    public RispostaCaricaPosizioneConEsitoArricchito createRispostaCaricaPosizioneConEsitoArricchito() {
        return new RispostaCaricaPosizioneConEsitoArricchito();
    }

    /**
     * Create an instance of {@link EsitoDiCaricamentoArricchito }
     * 
     */
    public EsitoDiCaricamentoArricchito createEsitoDiCaricamentoArricchito() {
        return new EsitoDiCaricamentoArricchito();
    }

    /**
     * Create an instance of {@link CaricaPosizioni }
     * 
     */
    public CaricaPosizioni createCaricaPosizioni() {
        return new CaricaPosizioni();
    }

    /**
     * Create an instance of {@link CaricaPosizioniResponse }
     * 
     */
    public CaricaPosizioniResponse createCaricaPosizioniResponse() {
        return new CaricaPosizioniResponse();
    }

    /**
     * Create an instance of {@link RispostaCaricaPosizioni }
     * 
     */
    public RispostaCaricaPosizioni createRispostaCaricaPosizioni() {
        return new RispostaCaricaPosizioni();
    }

    /**
     * Create an instance of {@link ArrayOfEsitoDiCaricamento }
     * 
     */
    public ArrayOfEsitoDiCaricamento createArrayOfEsitoDiCaricamento() {
        return new ArrayOfEsitoDiCaricamento();
    }

    /**
     * Create an instance of {@link ValidaPosizioni }
     * 
     */
    public ValidaPosizioni createValidaPosizioni() {
        return new ValidaPosizioni();
    }

    /**
     * Create an instance of {@link RichiestaValidaPosizioni }
     * 
     */
    public RichiestaValidaPosizioni createRichiestaValidaPosizioni() {
        return new RichiestaValidaPosizioni();
    }

    /**
     * Create an instance of {@link ValidaPosizioniResponse }
     * 
     */
    public ValidaPosizioniResponse createValidaPosizioniResponse() {
        return new ValidaPosizioniResponse();
    }

    /**
     * Create an instance of {@link RispostaValidaPosizioni }
     * 
     */
    public RispostaValidaPosizioni createRispostaValidaPosizioni() {
        return new RispostaValidaPosizioni();
    }

    /**
     * Create an instance of {@link ArrayOfEsitoDiValidazione }
     * 
     */
    public ArrayOfEsitoDiValidazione createArrayOfEsitoDiValidazione() {
        return new ArrayOfEsitoDiValidazione();
    }

    /**
     * Create an instance of {@link EsitoDiValidazione }
     * 
     */
    public EsitoDiValidazione createEsitoDiValidazione() {
        return new EsitoDiValidazione();
    }

    /**
     * Create an instance of {@link RettificaPosizione }
     * 
     */
    public RettificaPosizione createRettificaPosizione() {
        return new RettificaPosizione();
    }

    /**
     * Create an instance of {@link RichiestaRettificaPosizione }
     * 
     */
    public RichiestaRettificaPosizione createRichiestaRettificaPosizione() {
        return new RichiestaRettificaPosizione();
    }

    /**
     * Create an instance of {@link RettificaPosizioneResponse }
     * 
     */
    public RettificaPosizioneResponse createRettificaPosizioneResponse() {
        return new RettificaPosizioneResponse();
    }

    /**
     * Create an instance of {@link RispostaRettificaPosizione }
     * 
     */
    public RispostaRettificaPosizione createRispostaRettificaPosizione() {
        return new RispostaRettificaPosizione();
    }

    /**
     * Create an instance of {@link RimuoviPosizione }
     * 
     */
    public RimuoviPosizione createRimuoviPosizione() {
        return new RimuoviPosizione();
    }

    /**
     * Create an instance of {@link RichiestaRimuoviPosizione }
     * 
     */
    public RichiestaRimuoviPosizione createRichiestaRimuoviPosizione() {
        return new RichiestaRimuoviPosizione();
    }

    /**
     * Create an instance of {@link RimuoviPosizioneResponse }
     * 
     */
    public RimuoviPosizioneResponse createRimuoviPosizioneResponse() {
        return new RimuoviPosizioneResponse();
    }

    /**
     * Create an instance of {@link RispostaRimuoviPosizione }
     * 
     */
    public RispostaRimuoviPosizione createRispostaRimuoviPosizione() {
        return new RispostaRimuoviPosizione();
    }

    /**
     * Create an instance of {@link ArrayOfRipartizioneDiImportoInAccertamento }
     * 
     */
    public ArrayOfRipartizioneDiImportoInAccertamento createArrayOfRipartizioneDiImportoInAccertamento() {
        return new ArrayOfRipartizioneDiImportoInAccertamento();
    }

    /**
     * Create an instance of {@link RipartizioneDiImportoInAccertamento }
     * 
     */
    public RipartizioneDiImportoInAccertamento createRipartizioneDiImportoInAccertamento() {
        return new RipartizioneDiImportoInAccertamento();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RichiestaCaricaPosizione }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "RichiestaCaricaPosizione")
    public JAXBElement<RichiestaCaricaPosizione> createRichiestaCaricaPosizione(RichiestaCaricaPosizione value) {
        return new JAXBElement<RichiestaCaricaPosizione>(_RichiestaCaricaPosizione_QNAME, RichiestaCaricaPosizione.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FeedAuthenticatedRequestBase }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "FeedAuthenticatedRequestBase")
    public JAXBElement<FeedAuthenticatedRequestBase> createFeedAuthenticatedRequestBase(FeedAuthenticatedRequestBase value) {
        return new JAXBElement<FeedAuthenticatedRequestBase>(_FeedAuthenticatedRequestBase_QNAME, FeedAuthenticatedRequestBase.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RichiestaCaricaPosizioni }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "RichiestaCaricaPosizioni")
    public JAXBElement<RichiestaCaricaPosizioni> createRichiestaCaricaPosizioni(RichiestaCaricaPosizioni value) {
        return new JAXBElement<RichiestaCaricaPosizioni>(_RichiestaCaricaPosizioni_QNAME, RichiestaCaricaPosizioni.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfPosizione }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "ArrayOfPosizione")
    public JAXBElement<ArrayOfPosizione> createArrayOfPosizione(ArrayOfPosizione value) {
        return new JAXBElement<ArrayOfPosizione>(_ArrayOfPosizione_QNAME, ArrayOfPosizione.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Posizione }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "Posizione")
    public JAXBElement<Posizione> createPosizione(Posizione value) {
        return new JAXBElement<Posizione>(_Posizione_QNAME, Posizione.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfAccertamento }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "ArrayOfAccertamento")
    public JAXBElement<ArrayOfAccertamento> createArrayOfAccertamento(ArrayOfAccertamento value) {
        return new JAXBElement<ArrayOfAccertamento>(_ArrayOfAccertamento_QNAME, ArrayOfAccertamento.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Accertamento }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "Accertamento")
    public JAXBElement<Accertamento> createAccertamento(Accertamento value) {
        return new JAXBElement<Accertamento>(_Accertamento_QNAME, Accertamento.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Creditore }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "Creditore")
    public JAXBElement<Creditore> createCreditore(Creditore value) {
        return new JAXBElement<Creditore>(_Creditore_QNAME, Creditore.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Debitore }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "Debitore")
    public JAXBElement<Debitore> createDebitore(Debitore value) {
        return new JAXBElement<Debitore>(_Debitore_QNAME, Debitore.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Nazione }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "Nazione")
    public JAXBElement<Nazione> createNazione(Nazione value) {
        return new JAXBElement<Nazione>(_Nazione_QNAME, Nazione.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfParametroPosizione }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "ArrayOfParametroPosizione")
    public JAXBElement<ArrayOfParametroPosizione> createArrayOfParametroPosizione(ArrayOfParametroPosizione value) {
        return new JAXBElement<ArrayOfParametroPosizione>(_ArrayOfParametroPosizione_QNAME, ArrayOfParametroPosizione.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ParametroPosizione }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "ParametroPosizione")
    public JAXBElement<ParametroPosizione> createParametroPosizione(ParametroPosizione value) {
        return new JAXBElement<ParametroPosizione>(_ParametroPosizione_QNAME, ParametroPosizione.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RispostaCaricaPosizione }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "RispostaCaricaPosizione")
    public JAXBElement<RispostaCaricaPosizione> createRispostaCaricaPosizione(RispostaCaricaPosizione value) {
        return new JAXBElement<RispostaCaricaPosizione>(_RispostaCaricaPosizione_QNAME, RispostaCaricaPosizione.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EsitoDiCaricamento }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "EsitoDiCaricamento")
    public JAXBElement<EsitoDiCaricamento> createEsitoDiCaricamento(EsitoDiCaricamento value) {
        return new JAXBElement<EsitoDiCaricamento>(_EsitoDiCaricamento_QNAME, EsitoDiCaricamento.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfErroreDiValidazionePosizione }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "ArrayOfErroreDiValidazionePosizione")
    public JAXBElement<ArrayOfErroreDiValidazionePosizione> createArrayOfErroreDiValidazionePosizione(ArrayOfErroreDiValidazionePosizione value) {
        return new JAXBElement<ArrayOfErroreDiValidazionePosizione>(_ArrayOfErroreDiValidazionePosizione_QNAME, ArrayOfErroreDiValidazionePosizione.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ErroreDiValidazionePosizione }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "ErroreDiValidazionePosizione")
    public JAXBElement<ErroreDiValidazionePosizione> createErroreDiValidazionePosizione(ErroreDiValidazionePosizione value) {
        return new JAXBElement<ErroreDiValidazionePosizione>(_ErroreDiValidazionePosizione_QNAME, ErroreDiValidazionePosizione.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Esito }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "Esito")
    public JAXBElement<Esito> createEsito(Esito value) {
        return new JAXBElement<Esito>(_Esito_QNAME, Esito.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SoapFaultOperazioneProibita }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "SoapFaultOperazioneProibita")
    public JAXBElement<SoapFaultOperazioneProibita> createSoapFaultOperazioneProibita(SoapFaultOperazioneProibita value) {
        return new JAXBElement<SoapFaultOperazioneProibita>(_SoapFaultOperazioneProibita_QNAME, SoapFaultOperazioneProibita.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SoapFault }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "SoapFault")
    public JAXBElement<SoapFault> createSoapFault(SoapFault value) {
        return new JAXBElement<SoapFault>(_SoapFault_QNAME, SoapFault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SoapFaultErroreInterno }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "SoapFaultErroreInterno")
    public JAXBElement<SoapFaultErroreInterno> createSoapFaultErroreInterno(SoapFaultErroreInterno value) {
        return new JAXBElement<SoapFaultErroreInterno>(_SoapFaultErroreInterno_QNAME, SoapFaultErroreInterno.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SoapFaultErroreEasyPA }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "SoapFaultErroreEasyPA")
    public JAXBElement<SoapFaultErroreEasyPA> createSoapFaultErroreEasyPA(SoapFaultErroreEasyPA value) {
        return new JAXBElement<SoapFaultErroreEasyPA>(_SoapFaultErroreEasyPA_QNAME, SoapFaultErroreEasyPA.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RispostaCaricaPosizioneConEsitoArricchito }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "RispostaCaricaPosizioneConEsitoArricchito")
    public JAXBElement<RispostaCaricaPosizioneConEsitoArricchito> createRispostaCaricaPosizioneConEsitoArricchito(RispostaCaricaPosizioneConEsitoArricchito value) {
        return new JAXBElement<RispostaCaricaPosizioneConEsitoArricchito>(_RispostaCaricaPosizioneConEsitoArricchito_QNAME, RispostaCaricaPosizioneConEsitoArricchito.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EsitoDiCaricamentoArricchito }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "EsitoDiCaricamentoArricchito")
    public JAXBElement<EsitoDiCaricamentoArricchito> createEsitoDiCaricamentoArricchito(EsitoDiCaricamentoArricchito value) {
        return new JAXBElement<EsitoDiCaricamentoArricchito>(_EsitoDiCaricamentoArricchito_QNAME, EsitoDiCaricamentoArricchito.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RispostaCaricaPosizioni }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "RispostaCaricaPosizioni")
    public JAXBElement<RispostaCaricaPosizioni> createRispostaCaricaPosizioni(RispostaCaricaPosizioni value) {
        return new JAXBElement<RispostaCaricaPosizioni>(_RispostaCaricaPosizioni_QNAME, RispostaCaricaPosizioni.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfEsitoDiCaricamento }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "ArrayOfEsitoDiCaricamento")
    public JAXBElement<ArrayOfEsitoDiCaricamento> createArrayOfEsitoDiCaricamento(ArrayOfEsitoDiCaricamento value) {
        return new JAXBElement<ArrayOfEsitoDiCaricamento>(_ArrayOfEsitoDiCaricamento_QNAME, ArrayOfEsitoDiCaricamento.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RichiestaValidaPosizioni }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "RichiestaValidaPosizioni")
    public JAXBElement<RichiestaValidaPosizioni> createRichiestaValidaPosizioni(RichiestaValidaPosizioni value) {
        return new JAXBElement<RichiestaValidaPosizioni>(_RichiestaValidaPosizioni_QNAME, RichiestaValidaPosizioni.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RispostaValidaPosizioni }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "RispostaValidaPosizioni")
    public JAXBElement<RispostaValidaPosizioni> createRispostaValidaPosizioni(RispostaValidaPosizioni value) {
        return new JAXBElement<RispostaValidaPosizioni>(_RispostaValidaPosizioni_QNAME, RispostaValidaPosizioni.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfEsitoDiValidazione }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "ArrayOfEsitoDiValidazione")
    public JAXBElement<ArrayOfEsitoDiValidazione> createArrayOfEsitoDiValidazione(ArrayOfEsitoDiValidazione value) {
        return new JAXBElement<ArrayOfEsitoDiValidazione>(_ArrayOfEsitoDiValidazione_QNAME, ArrayOfEsitoDiValidazione.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EsitoDiValidazione }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "EsitoDiValidazione")
    public JAXBElement<EsitoDiValidazione> createEsitoDiValidazione(EsitoDiValidazione value) {
        return new JAXBElement<EsitoDiValidazione>(_EsitoDiValidazione_QNAME, EsitoDiValidazione.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RichiestaRettificaPosizione }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "RichiestaRettificaPosizione")
    public JAXBElement<RichiestaRettificaPosizione> createRichiestaRettificaPosizione(RichiestaRettificaPosizione value) {
        return new JAXBElement<RichiestaRettificaPosizione>(_RichiestaRettificaPosizione_QNAME, RichiestaRettificaPosizione.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RispostaRettificaPosizione }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "RispostaRettificaPosizione")
    public JAXBElement<RispostaRettificaPosizione> createRispostaRettificaPosizione(RispostaRettificaPosizione value) {
        return new JAXBElement<RispostaRettificaPosizione>(_RispostaRettificaPosizione_QNAME, RispostaRettificaPosizione.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RichiestaRimuoviPosizione }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "RichiestaRimuoviPosizione")
    public JAXBElement<RichiestaRimuoviPosizione> createRichiestaRimuoviPosizione(RichiestaRimuoviPosizione value) {
        return new JAXBElement<RichiestaRimuoviPosizione>(_RichiestaRimuoviPosizione_QNAME, RichiestaRimuoviPosizione.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RispostaRimuoviPosizione }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "RispostaRimuoviPosizione")
    public JAXBElement<RispostaRimuoviPosizione> createRispostaRimuoviPosizione(RispostaRimuoviPosizione value) {
        return new JAXBElement<RispostaRimuoviPosizione>(_RispostaRimuoviPosizione_QNAME, RispostaRimuoviPosizione.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfRipartizioneDiImportoInAccertamento }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/PlugAndPay.Feed.Service.Contracts", name = "ArrayOfRipartizioneDiImportoInAccertamento")
    public JAXBElement<ArrayOfRipartizioneDiImportoInAccertamento> createArrayOfRipartizioneDiImportoInAccertamento(ArrayOfRipartizioneDiImportoInAccertamento value) {
        return new JAXBElement<ArrayOfRipartizioneDiImportoInAccertamento>(_ArrayOfRipartizioneDiImportoInAccertamento_QNAME, ArrayOfRipartizioneDiImportoInAccertamento.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RipartizioneDiImportoInAccertamento }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/PlugAndPay.Feed.Service.Contracts", name = "RipartizioneDiImportoInAccertamento")
    public JAXBElement<RipartizioneDiImportoInAccertamento> createRipartizioneDiImportoInAccertamento(RipartizioneDiImportoInAccertamento value) {
        return new JAXBElement<RipartizioneDiImportoInAccertamento>(_RipartizioneDiImportoInAccertamento_QNAME, RipartizioneDiImportoInAccertamento.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RichiestaCaricaPosizione }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "request", scope = CaricaPosizione.class)
    public JAXBElement<RichiestaCaricaPosizione> createCaricaPosizioneRequest(RichiestaCaricaPosizione value) {
        return new JAXBElement<RichiestaCaricaPosizione>(_CaricaPosizioneRequest_QNAME, RichiestaCaricaPosizione.class, CaricaPosizione.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RispostaCaricaPosizione }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "CaricaPosizioneResult", scope = CaricaPosizioneResponse.class)
    public JAXBElement<RispostaCaricaPosizione> createCaricaPosizioneResponseCaricaPosizioneResult(RispostaCaricaPosizione value) {
        return new JAXBElement<RispostaCaricaPosizione>(_CaricaPosizioneResponseCaricaPosizioneResult_QNAME, RispostaCaricaPosizione.class, CaricaPosizioneResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RichiestaCaricaPosizione }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "request", scope = CaricaPosizioneConEsitoArricchito.class)
    public JAXBElement<RichiestaCaricaPosizione> createCaricaPosizioneConEsitoArricchitoRequest(RichiestaCaricaPosizione value) {
        return new JAXBElement<RichiestaCaricaPosizione>(_CaricaPosizioneRequest_QNAME, RichiestaCaricaPosizione.class, CaricaPosizioneConEsitoArricchito.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RispostaCaricaPosizioneConEsitoArricchito }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "CaricaPosizioneConEsitoArricchitoResult", scope = CaricaPosizioneConEsitoArricchitoResponse.class)
    public JAXBElement<RispostaCaricaPosizioneConEsitoArricchito> createCaricaPosizioneConEsitoArricchitoResponseCaricaPosizioneConEsitoArricchitoResult(RispostaCaricaPosizioneConEsitoArricchito value) {
        return new JAXBElement<RispostaCaricaPosizioneConEsitoArricchito>(_CaricaPosizioneConEsitoArricchitoResponseCaricaPosizioneConEsitoArricchitoResult_QNAME, RispostaCaricaPosizioneConEsitoArricchito.class, CaricaPosizioneConEsitoArricchitoResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RichiestaCaricaPosizioni }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "request", scope = CaricaPosizioni.class)
    public JAXBElement<RichiestaCaricaPosizioni> createCaricaPosizioniRequest(RichiestaCaricaPosizioni value) {
        return new JAXBElement<RichiestaCaricaPosizioni>(_CaricaPosizioneRequest_QNAME, RichiestaCaricaPosizioni.class, CaricaPosizioni.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RispostaCaricaPosizioni }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "CaricaPosizioniResult", scope = CaricaPosizioniResponse.class)
    public JAXBElement<RispostaCaricaPosizioni> createCaricaPosizioniResponseCaricaPosizioniResult(RispostaCaricaPosizioni value) {
        return new JAXBElement<RispostaCaricaPosizioni>(_CaricaPosizioniResponseCaricaPosizioniResult_QNAME, RispostaCaricaPosizioni.class, CaricaPosizioniResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RichiestaValidaPosizioni }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "request", scope = ValidaPosizioni.class)
    public JAXBElement<RichiestaValidaPosizioni> createValidaPosizioniRequest(RichiestaValidaPosizioni value) {
        return new JAXBElement<RichiestaValidaPosizioni>(_CaricaPosizioneRequest_QNAME, RichiestaValidaPosizioni.class, ValidaPosizioni.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RispostaValidaPosizioni }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "ValidaPosizioniResult", scope = ValidaPosizioniResponse.class)
    public JAXBElement<RispostaValidaPosizioni> createValidaPosizioniResponseValidaPosizioniResult(RispostaValidaPosizioni value) {
        return new JAXBElement<RispostaValidaPosizioni>(_ValidaPosizioniResponseValidaPosizioniResult_QNAME, RispostaValidaPosizioni.class, ValidaPosizioniResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RichiestaRettificaPosizione }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "request", scope = RettificaPosizione.class)
    public JAXBElement<RichiestaRettificaPosizione> createRettificaPosizioneRequest(RichiestaRettificaPosizione value) {
        return new JAXBElement<RichiestaRettificaPosizione>(_CaricaPosizioneRequest_QNAME, RichiestaRettificaPosizione.class, RettificaPosizione.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RispostaRettificaPosizione }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "RettificaPosizioneResult", scope = RettificaPosizioneResponse.class)
    public JAXBElement<RispostaRettificaPosizione> createRettificaPosizioneResponseRettificaPosizioneResult(RispostaRettificaPosizione value) {
        return new JAXBElement<RispostaRettificaPosizione>(_RettificaPosizioneResponseRettificaPosizioneResult_QNAME, RispostaRettificaPosizione.class, RettificaPosizioneResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RichiestaRimuoviPosizione }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "request", scope = RimuoviPosizione.class)
    public JAXBElement<RichiestaRimuoviPosizione> createRimuoviPosizioneRequest(RichiestaRimuoviPosizione value) {
        return new JAXBElement<RichiestaRimuoviPosizione>(_CaricaPosizioneRequest_QNAME, RichiestaRimuoviPosizione.class, RimuoviPosizione.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RispostaRimuoviPosizione }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "RimuoviPosizioneResult", scope = RimuoviPosizioneResponse.class)
    public JAXBElement<RispostaRimuoviPosizione> createRimuoviPosizioneResponseRimuoviPosizioneResult(RispostaRimuoviPosizione value) {
        return new JAXBElement<RispostaRimuoviPosizione>(_RimuoviPosizioneResponseRimuoviPosizioneResult_QNAME, RispostaRimuoviPosizione.class, RimuoviPosizioneResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/PlugAndPay.Feed.Service.Contracts", name = "Codice", scope = RipartizioneDiImportoInAccertamento.class)
    public JAXBElement<String> createRipartizioneDiImportoInAccertamentoCodice(String value) {
        return new JAXBElement<String>(_RipartizioneDiImportoInAccertamentoCodice_QNAME, String.class, RipartizioneDiImportoInAccertamento.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/PlugAndPay.Feed.Service.Contracts", name = "Descrizione", scope = RipartizioneDiImportoInAccertamento.class)
    public JAXBElement<String> createRipartizioneDiImportoInAccertamentoDescrizione(String value) {
        return new JAXBElement<String>(_RipartizioneDiImportoInAccertamentoDescrizione_QNAME, String.class, RipartizioneDiImportoInAccertamento.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/PlugAndPay.Feed.Service.Contracts", name = "PeriodoDiRiferimento", scope = RipartizioneDiImportoInAccertamento.class)
    public JAXBElement<String> createRipartizioneDiImportoInAccertamentoPeriodoDiRiferimento(String value) {
        return new JAXBElement<String>(_RipartizioneDiImportoInAccertamentoPeriodoDiRiferimento_QNAME, String.class, RipartizioneDiImportoInAccertamento.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "Causale", scope = RichiestaRettificaPosizione.class)
    public JAXBElement<String> createRichiestaRettificaPosizioneCausale(String value) {
        return new JAXBElement<String>(_RichiestaRettificaPosizioneCausale_QNAME, String.class, RichiestaRettificaPosizione.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "DataScandenza", scope = RichiestaRettificaPosizione.class)
    public JAXBElement<XMLGregorianCalendar> createRichiestaRettificaPosizioneDataScandenza(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_RichiestaRettificaPosizioneDataScandenza_QNAME, XMLGregorianCalendar.class, RichiestaRettificaPosizione.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "ImportoInCentesimi", scope = RichiestaRettificaPosizione.class)
    public JAXBElement<Long> createRichiestaRettificaPosizioneImportoInCentesimi(Long value) {
        return new JAXBElement<Long>(_RichiestaRettificaPosizioneImportoInCentesimi_QNAME, Long.class, RichiestaRettificaPosizione.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfRipartizioneDiImportoInAccertamento }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "RipartizioniDiImporto", scope = RichiestaRettificaPosizione.class)
    public JAXBElement<ArrayOfRipartizioneDiImportoInAccertamento> createRichiestaRettificaPosizioneRipartizioniDiImporto(ArrayOfRipartizioneDiImportoInAccertamento value) {
        return new JAXBElement<ArrayOfRipartizioneDiImportoInAccertamento>(_RichiestaRettificaPosizioneRipartizioniDiImporto_QNAME, ArrayOfRipartizioneDiImportoInAccertamento.class, RichiestaRettificaPosizione.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "CodiceRiferimentoCreditore", scope = EsitoDiValidazione.class)
    public JAXBElement<String> createEsitoDiValidazioneCodiceRiferimentoCreditore(String value) {
        return new JAXBElement<String>(_EsitoDiValidazioneCodiceRiferimentoCreditore_QNAME, String.class, EsitoDiValidazione.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfErroreDiValidazionePosizione }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "ErroriDiValidazione", scope = EsitoDiValidazione.class)
    public JAXBElement<ArrayOfErroreDiValidazionePosizione> createEsitoDiValidazioneErroriDiValidazione(ArrayOfErroreDiValidazionePosizione value) {
        return new JAXBElement<ArrayOfErroreDiValidazionePosizione>(_EsitoDiValidazioneErroriDiValidazione_QNAME, ArrayOfErroreDiValidazionePosizione.class, EsitoDiValidazione.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "IdentificativoPosizione", scope = EsitoDiValidazione.class)
    public JAXBElement<String> createEsitoDiValidazioneIdentificativoPosizione(String value) {
        return new JAXBElement<String>(_EsitoDiValidazioneIdentificativoPosizione_QNAME, String.class, EsitoDiValidazione.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "TipoRiferimentoCreditore", scope = EsitoDiValidazione.class)
    public JAXBElement<String> createEsitoDiValidazioneTipoRiferimentoCreditore(String value) {
        return new JAXBElement<String>(_EsitoDiValidazioneTipoRiferimentoCreditore_QNAME, String.class, EsitoDiValidazione.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfEsitoDiValidazione }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "EsitiDiCaricamento", scope = RispostaValidaPosizioni.class)
    public JAXBElement<ArrayOfEsitoDiValidazione> createRispostaValidaPosizioniEsitiDiCaricamento(ArrayOfEsitoDiValidazione value) {
        return new JAXBElement<ArrayOfEsitoDiValidazione>(_RispostaValidaPosizioniEsitiDiCaricamento_QNAME, ArrayOfEsitoDiValidazione.class, RispostaValidaPosizioni.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfEsitoDiCaricamento }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "EsitiDiCaricamento", scope = RispostaCaricaPosizioni.class)
    public JAXBElement<ArrayOfEsitoDiCaricamento> createRispostaCaricaPosizioniEsitiDiCaricamento(ArrayOfEsitoDiCaricamento value) {
        return new JAXBElement<ArrayOfEsitoDiCaricamento>(_RispostaValidaPosizioniEsitiDiCaricamento_QNAME, ArrayOfEsitoDiCaricamento.class, RispostaCaricaPosizioni.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "CodiceRiferimentoCreditore", scope = EsitoDiCaricamentoArricchito.class)
    public JAXBElement<String> createEsitoDiCaricamentoArricchitoCodiceRiferimentoCreditore(String value) {
        return new JAXBElement<String>(_EsitoDiValidazioneCodiceRiferimentoCreditore_QNAME, String.class, EsitoDiCaricamentoArricchito.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfErroreDiValidazionePosizione }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "ErroriDiValidazione", scope = EsitoDiCaricamentoArricchito.class)
    public JAXBElement<ArrayOfErroreDiValidazionePosizione> createEsitoDiCaricamentoArricchitoErroriDiValidazione(ArrayOfErroreDiValidazionePosizione value) {
        return new JAXBElement<ArrayOfErroreDiValidazionePosizione>(_EsitoDiValidazioneErroriDiValidazione_QNAME, ArrayOfErroreDiValidazionePosizione.class, EsitoDiCaricamentoArricchito.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "IdentificativoPosizione", scope = EsitoDiCaricamentoArricchito.class)
    public JAXBElement<String> createEsitoDiCaricamentoArricchitoIdentificativoPosizione(String value) {
        return new JAXBElement<String>(_EsitoDiValidazioneIdentificativoPosizione_QNAME, String.class, EsitoDiCaricamentoArricchito.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "NumeroAvviso", scope = EsitoDiCaricamentoArricchito.class)
    public JAXBElement<String> createEsitoDiCaricamentoArricchitoNumeroAvviso(String value) {
        return new JAXBElement<String>(_EsitoDiCaricamentoArricchitoNumeroAvviso_QNAME, String.class, EsitoDiCaricamentoArricchito.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "TipoRiferimentoCreditore", scope = EsitoDiCaricamentoArricchito.class)
    public JAXBElement<String> createEsitoDiCaricamentoArricchitoTipoRiferimentoCreditore(String value) {
        return new JAXBElement<String>(_EsitoDiValidazioneTipoRiferimentoCreditore_QNAME, String.class, EsitoDiCaricamentoArricchito.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EsitoDiCaricamentoArricchito }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "EsitoDiCaricamentoArricchito", scope = RispostaCaricaPosizioneConEsitoArricchito.class)
    public JAXBElement<EsitoDiCaricamentoArricchito> createRispostaCaricaPosizioneConEsitoArricchitoEsitoDiCaricamentoArricchito(EsitoDiCaricamentoArricchito value) {
        return new JAXBElement<EsitoDiCaricamentoArricchito>(_EsitoDiCaricamentoArricchito_QNAME, EsitoDiCaricamentoArricchito.class, RispostaCaricaPosizioneConEsitoArricchito.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "Codice", scope = ErroreDiValidazionePosizione.class)
    public JAXBElement<String> createErroreDiValidazionePosizioneCodice(String value) {
        return new JAXBElement<String>(_ErroreDiValidazionePosizioneCodice_QNAME, String.class, ErroreDiValidazionePosizione.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "Descrizione", scope = ErroreDiValidazionePosizione.class)
    public JAXBElement<String> createErroreDiValidazionePosizioneDescrizione(String value) {
        return new JAXBElement<String>(_ErroreDiValidazionePosizioneDescrizione_QNAME, String.class, ErroreDiValidazionePosizione.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "CodiceRiferimentoCreditore", scope = EsitoDiCaricamento.class)
    public JAXBElement<String> createEsitoDiCaricamentoCodiceRiferimentoCreditore(String value) {
        return new JAXBElement<String>(_EsitoDiValidazioneCodiceRiferimentoCreditore_QNAME, String.class, EsitoDiCaricamento.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfErroreDiValidazionePosizione }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "ErroriDiValidazione", scope = EsitoDiCaricamento.class)
    public JAXBElement<ArrayOfErroreDiValidazionePosizione> createEsitoDiCaricamentoErroriDiValidazione(ArrayOfErroreDiValidazionePosizione value) {
        return new JAXBElement<ArrayOfErroreDiValidazionePosizione>(_EsitoDiValidazioneErroriDiValidazione_QNAME, ArrayOfErroreDiValidazionePosizione.class, EsitoDiCaricamento.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "IdentificativoPosizione", scope = EsitoDiCaricamento.class)
    public JAXBElement<String> createEsitoDiCaricamentoIdentificativoPosizione(String value) {
        return new JAXBElement<String>(_EsitoDiValidazioneIdentificativoPosizione_QNAME, String.class, EsitoDiCaricamento.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "TipoRiferimentoCreditore", scope = EsitoDiCaricamento.class)
    public JAXBElement<String> createEsitoDiCaricamentoTipoRiferimentoCreditore(String value) {
        return new JAXBElement<String>(_EsitoDiValidazioneTipoRiferimentoCreditore_QNAME, String.class, EsitoDiCaricamento.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EsitoDiCaricamento }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "EsitoDiCaricamento", scope = RispostaCaricaPosizione.class)
    public JAXBElement<EsitoDiCaricamento> createRispostaCaricaPosizioneEsitoDiCaricamento(EsitoDiCaricamento value) {
        return new JAXBElement<EsitoDiCaricamento>(_EsitoDiCaricamento_QNAME, EsitoDiCaricamento.class, RispostaCaricaPosizione.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "Chiave", scope = ParametroPosizione.class)
    public JAXBElement<String> createParametroPosizioneChiave(String value) {
        return new JAXBElement<String>(_ParametroPosizioneChiave_QNAME, String.class, ParametroPosizione.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "Valore", scope = ParametroPosizione.class)
    public JAXBElement<String> createParametroPosizioneValore(String value) {
        return new JAXBElement<String>(_ParametroPosizioneValore_QNAME, String.class, ParametroPosizione.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "CodiceIsoNazione", scope = Nazione.class)
    public JAXBElement<String> createNazioneCodiceIsoNazione(String value) {
        return new JAXBElement<String>(_NazioneCodiceIsoNazione_QNAME, String.class, Nazione.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "NomeNazione", scope = Nazione.class)
    public JAXBElement<String> createNazioneNomeNazione(String value) {
        return new JAXBElement<String>(_NazioneNomeNazione_QNAME, String.class, Nazione.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "Cellulare", scope = Debitore.class)
    public JAXBElement<String> createDebitoreCellulare(String value) {
        return new JAXBElement<String>(_DebitoreCellulare_QNAME, String.class, Debitore.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "Civico", scope = Debitore.class)
    public JAXBElement<String> createDebitoreCivico(String value) {
        return new JAXBElement<String>(_DebitoreCivico_QNAME, String.class, Debitore.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "CodiceAvviamentoPostale", scope = Debitore.class)
    public JAXBElement<String> createDebitoreCodiceAvviamentoPostale(String value) {
        return new JAXBElement<String>(_DebitoreCodiceAvviamentoPostale_QNAME, String.class, Debitore.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "Email", scope = Debitore.class)
    public JAXBElement<String> createDebitoreEmail(String value) {
        return new JAXBElement<String>(_DebitoreEmail_QNAME, String.class, Debitore.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "Indirizzo", scope = Debitore.class)
    public JAXBElement<String> createDebitoreIndirizzo(String value) {
        return new JAXBElement<String>(_DebitoreIndirizzo_QNAME, String.class, Debitore.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "Localita", scope = Debitore.class)
    public JAXBElement<String> createDebitoreLocalita(String value) {
        return new JAXBElement<String>(_DebitoreLocalita_QNAME, String.class, Debitore.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "Provincia", scope = Debitore.class)
    public JAXBElement<String> createDebitoreProvincia(String value) {
        return new JAXBElement<String>(_DebitoreProvincia_QNAME, String.class, Debitore.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "CodiceFiscalePartitaIva", scope = Creditore.class)
    public JAXBElement<String> createCreditoreCodiceFiscalePartitaIva(String value) {
        return new JAXBElement<String>(_CreditoreCodiceFiscalePartitaIva_QNAME, String.class, Creditore.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "IBAN", scope = Creditore.class)
    public JAXBElement<String> createCreditoreIBAN(String value) {
        return new JAXBElement<String>(_CreditoreIBAN_QNAME, String.class, Creditore.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfAccertamento }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "Accertamenti", scope = Posizione.class)
    public JAXBElement<ArrayOfAccertamento> createPosizioneAccertamenti(ArrayOfAccertamento value) {
        return new JAXBElement<ArrayOfAccertamento>(_PosizioneAccertamenti_QNAME, ArrayOfAccertamento.class, Posizione.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "DataScadenza", scope = Posizione.class)
    public JAXBElement<XMLGregorianCalendar> createPosizioneDataScadenza(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_PosizioneDataScadenza_QNAME, XMLGregorianCalendar.class, Posizione.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "IdentificativoPosizione", scope = Posizione.class)
    public JAXBElement<String> createPosizioneIdentificativoPosizione(String value) {
        return new JAXBElement<String>(_EsitoDiValidazioneIdentificativoPosizione_QNAME, String.class, Posizione.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfParametroPosizione }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", name = "ParametriPosizione", scope = Posizione.class)
    public JAXBElement<ArrayOfParametroPosizione> createPosizioneParametriPosizione(ArrayOfParametroPosizione value) {
        return new JAXBElement<ArrayOfParametroPosizione>(_PosizioneParametriPosizione_QNAME, ArrayOfParametroPosizione.class, Posizione.class, value);
    }

}
