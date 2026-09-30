
//------------------------------------------------------------------------------
// WCF-based proxy for QuaestioFlorenzia service
// Converted from SoapHttpClientProtocol to WCF ClientBase
//------------------------------------------------------------------------------

using System;
using System.ServiceModel;
using System.ServiceModel.Channels;

namespace Init.SIGePro.Sit.QuestioFlorentia
{
    /// <summary>
    /// WCF Service Contract for QuaestioFlorenzia
    /// </summary>
    [ServiceContract(Namespace = "urn:localhost-QuaestioFlorenzia", ConfigurationName = "IQuaestioFlorenzia")]
    [XmlSerializerFormat(Style = OperationFormatStyle.Rpc, Use = OperationFormatUse.Encoded)]
    public interface IQuaestioFlorenzia
    {
        [OperationContract(Action = "urn:localhost-QuaestioFlorenzia#testSoap", ReplyAction = "*")]
        [return: MessageParameter(Name = "strOut")]
        string testSoap(string strIn);

        [OperationContract(Action = "urn:localhost-geoposflorenzia#getMsg", ReplyAction = "*")]
        [return: MessageParameter(Name = "strOut")]
        string getMsg();

        [OperationContract(Action = "urn:localhost-QuaestioFlorenzia#tpnUnixTimeUltimoAggiornamento", ReplyAction = "*")]
        [return: MessageParameter(Name = "intOut")]
        int tpnUnixTimeUltimoAggiornamento(string strIn);

        [OperationContract(Action = "urn:localhost-QuaestioFlorenzia#aciUnixTimeUltimoAggiornamento", ReplyAction = "*")]
        [return: MessageParameter(Name = "intOut")]
        int aciUnixTimeUltimoAggiornamento(string strIn);

        [OperationContract(Action = "urn:localhost-QuaestioFlorenzia#ctsElencoParticellaDaFoglio", ReplyAction = "*")]
        [return: MessageParameter(Name = "aStrOut")]
        string[] ctsElencoParticellaDaFoglio(int intIn, string strIn);

        [OperationContract(Action = "urn:localhost-QuaestioFlorenzia#ctsSuperficieDaFoglioParticellaSubalterno", ReplyAction = "*")]
        [return: MessageParameter(Name = "floatOut")]
        float ctsSuperficieDaFoglioParticellaSubalterno(int intIn1, string strIn1, string strIn2, string strIn3);

        [OperationContract(Action = "urn:localhost-QuaestioFlorenzia#ctsElencoFoglioDaFoglioParziale", ReplyAction = "*")]
        [return: MessageParameter(Name = "aStrOut")]
        string[] ctsElencoFoglioDaFoglioParziale(int intIn, string strIn);

        [OperationContract(Action = "urn:localhost-QuaestioFlorenzia#ctsElencoSubalternoDaFoglioParticella", ReplyAction = "*")]
        [return: MessageParameter(Name = "aStrOut")]
        string[] ctsElencoSubalternoDaFoglioParticella(int intIn1, string strIn1, string strIn2);

        [OperationContract(Action = "urn:localhost-QuaestioFlorenzia#ctsEsisteFoglioParticellaSubalterno", ReplyAction = "*")]
        [return: MessageParameter(Name = "boolOut")]
        bool ctsEsisteFoglioParticellaSubalterno(int intIn1, string strIn1, string strIn2, string strIn3);

        [OperationContract(Action = "urn:localhost-QuaestioFlorenzia#aciElencoIdImmobileDaCodFabbricato", ReplyAction = "*")]
        [return: MessageParameter(Name = "aIntOut")]
        int[] aciElencoIdImmobileDaCodFabbricato(string strIn);

        [OperationContract(Action = "urn:localhost-QuaestioFlorenzia#aciCodFabbricatoDaIdImmobile", ReplyAction = "*")]
        [return: MessageParameter(Name = "strOut")]
        string aciCodFabbricatoDaIdImmobile(int intIn);

        [OperationContract(Action = "urn:localhost-QuaestioFlorenzia#aciIdImmobileDaCodCivico", ReplyAction = "*")]
        [return: MessageParameter(Name = "intOut")]
        int aciIdImmobileDaCodCivico(string strIn);

        [OperationContract(Action = "urn:localhost-QuaestioFlorenzia#aciCodFabbricatoDaCodCivico", ReplyAction = "*")]
        [return: MessageParameter(Name = "strOut")]
        string aciCodFabbricatoDaCodCivico(string strIn);

        [OperationContract(Action = "urn:localhost-QuaestioFlorenzia#aciIdImmobileCodFabbricatoDaCodCivico", ReplyAction = "*")]
        [return: MessageParameter(Name = "aStrOut")]
        string[] aciIdImmobileCodFabbricatoDaCodCivico(string strIn);

        [OperationContract(Action = "urn:localhost-QuaestioFlorenzia#aciCodFabbricatoDaNomeStradaDescrCivico", ReplyAction = "*")]
        [return: MessageParameter(Name = "strOut")]
        string aciCodFabbricatoDaNomeStradaDescrCivico(string strIn1, string strIn2);

        [OperationContract(Action = "urn:localhost-QuaestioFlorenzia#aciIdImmobileDaNomeStradaDescrCivico", ReplyAction = "*")]
        [return: MessageParameter(Name = "intOut")]
        int aciIdImmobileDaNomeStradaDescrCivico(string strIn1, string strIn2);

        [OperationContract(Action = "urn:localhost-QuaestioFlorenzia#aciFoglioParticellaDaCodFabbricato", ReplyAction = "*")]
        [return: MessageParameter(Name = "aStrOut")]
        string[] aciFoglioParticellaDaCodFabbricato(string strIn);

        [OperationContract(Action = "urn:localhost-QuaestioFlorenzia#aciFoglioParticellaDaIdImmobile", ReplyAction = "*")]
        [return: MessageParameter(Name = "aStrOut")]
        string[] aciFoglioParticellaDaIdImmobile(string strIn);

        [OperationContract(Action = "urn:localhost-QuaestioFlorenzia#aciElencoCodFabbricatoDaFoglioParticella", ReplyAction = "*")]
        [return: MessageParameter(Name = "aStrOut")]
        string[] aciElencoCodFabbricatoDaFoglioParticella(int intIn, string strIn);

        [OperationContract(Action = "urn:localhost-QuaestioFlorenzia#aciElencoIdImmobileDaFoglioParticella", ReplyAction = "*")]
        [return: MessageParameter(Name = "aIntOut")]
        int[] aciElencoIdImmobileDaFoglioParticella(int intIn, string strIn);

        [OperationContract(Action = "urn:localhost-QuaestioFlorenzia#aciElencoCodCivicoDaCodFabbricato", ReplyAction = "*")]
        [return: MessageParameter(Name = "aStrOut")]
        string[] aciElencoCodCivicoDaCodFabbricato(string strIn);

        [OperationContract(Action = "urn:localhost-QuaestioFlorenzia#aciElencoCodCivicoDaIdImmobile", ReplyAction = "*")]
        [return: MessageParameter(Name = "aStrOut")]
        string[] aciElencoCodCivicoDaIdImmobile(int intIn);

        [OperationContract(Action = "urn:localhost-QuaestioFlorenzia#aciElencoCodCivicoEtNomeStradaEtDescrCivicoDaCodFabbricato", ReplyAction = "*")]
        [return: MessageParameter(Name = "aStrOut")]
        string[] aciElencoCodCivicoEtNomeStradaEtDescrCivicoDaCodFabbricato(string strIn);

        [OperationContract(Action = "urn:localhost-QuaestioFlorenzia#aciElencoCodCivicoEtNomeStradaEtDescrCivicoDaIdImmobile", ReplyAction = "*")]
        [return: MessageParameter(Name = "aStrOut")]
        string[] aciElencoCodCivicoEtNomeStradaEtDescrCivicoDaIdImmobile(int intIn);

        [OperationContract(Action = "urn:localhost-QuaestioFlorenzia#aciElencoCodCivicoEtNomeStradaEtDescrCivicoDaFoglioParticella", ReplyAction = "*")]
        [return: MessageParameter(Name = "aStrOut")]
        string[] aciElencoCodCivicoEtNomeStradaEtDescrCivicoDaFoglioParticella(int intIn, string strIn);

        [OperationContract(Action = "urn:localhost-QuaestioFlorenzia#aciElencoCodStradaEtNomeStradaDaFoglioParticella", ReplyAction = "*")]
        [return: MessageParameter(Name = "aStrOut")]
        string[] aciElencoCodStradaEtNomeStradaDaFoglioParticella(int intIn, string strIn);

        [OperationContract(Action = "urn:localhost-QuaestioFlorenzia#aciElencoCodFabbricatoEtFoglioEtParticellaDaCodStrada", ReplyAction = "*")]
        [return: MessageParameter(Name = "aStrOut")]
        string[] aciElencoCodFabbricatoEtFoglioEtParticellaDaCodStrada(string strIn);

        [OperationContract(Action = "urn:localhost-QuaestioFlorenzia#aciKMLDaCodFabbricato", ReplyAction = "*")]
        [return: MessageParameter(Name = "strOut")]
        string aciKMLDaCodFabbricato(string strIn);

        [OperationContract(Action = "urn:localhost-QuaestioFlorenzia#aciKMLDaIdImmobile", ReplyAction = "*")]
        [return: MessageParameter(Name = "strOut")]
        string aciKMLDaIdImmobile(int intIn);

        [OperationContract(Action = "urn:localhost-QuaestioFlorenzia#tpnElencoCodiceEtNomeStrada", ReplyAction = "*")]
        [return: MessageParameter(Name = "aStrOut")]
        string[] tpnElencoCodiceEtNomeStrada();

        [OperationContract(Action = "urn:localhost-QuaestioFlorenzia#tpnElencoCodStradaDaNomeStrada", ReplyAction = "*")]
        [return: MessageParameter(Name = "aStrOut")]
        string[] tpnElencoCodStradaDaNomeStrada(string strIn);

        [OperationContract(Action = "urn:localhost-QuaestioFlorenzia#tpnElencoCodEtNomeStradaDaNomeStrada", ReplyAction = "*")]
        [return: MessageParameter(Name = "aStrOut")]
        string[] tpnElencoCodEtNomeStradaDaNomeStrada(string strIn);

        [OperationContract(Action = "urn:localhost-QuaestioFlorenzia#tpnElencoCodEtNomeEtProbStradaDaNomeStrada", ReplyAction = "*")]
        [return: MessageParameter(Name = "aStrOut")]
        string[] tpnElencoCodEtNomeEtProbStradaDaNomeStrada(string strIn);

        [OperationContract(Action = "urn:localhost-QuaestioFlorenzia#tpnElencoCodEtNomeStradaDaNomeStrada_QueryLike", ReplyAction = "*")]
        [return: MessageParameter(Name = "aStrOut")]
        string[] tpnElencoCodEtNomeStradaDaNomeStrada_QueryLike(string strIn1, string strIn2);

        [OperationContract(Action = "urn:localhost-QuaestioFlorenzia#tpnElencoDescrCivicoDaNomeStrada", ReplyAction = "*")]
        [return: MessageParameter(Name = "aStrOut")]
        string[] tpnElencoDescrCivicoDaNomeStrada(string strIn);

        [OperationContract(Action = "urn:localhost-QuaestioFlorenzia#tpnNomeStradaDaCodStrada", ReplyAction = "*")]
        [return: MessageParameter(Name = "strOut")]
        string tpnNomeStradaDaCodStrada(string strIn);

        [OperationContract(Action = "urn:localhost-QuaestioFlorenzia#tpnElencoDescrCivicoDaCodStrada", ReplyAction = "*")]
        [return: MessageParameter(Name = "aStrOut")]
        string[] tpnElencoDescrCivicoDaCodStrada(string strIn);

        [OperationContract(Action = "urn:localhost-QuaestioFlorenzia#tpnElencoCodEtDescrCivicoDaCodStrada", ReplyAction = "*")]
        [return: MessageParameter(Name = "aStrOut")]
        string[] tpnElencoCodEtDescrCivicoDaCodStrada(string strIn);

        [OperationContract(Action = "urn:localhost-QuaestioFlorenzia#tpnElencoCodEtDescrCivicoEtCodQuartiereDaCodStrada", ReplyAction = "*")]
        [return: MessageParameter(Name = "aStrOut")]
        string[] tpnElencoCodEtDescrCivicoEtCodQuartiereDaCodStrada(string strIn);

        [OperationContract(Action = "urn:localhost-QuaestioFlorenzia#tpnElencoDescrCivicoDaCodStradaDescrCivicoParziale", ReplyAction = "*")]
        [return: MessageParameter(Name = "aStrOut")]
        string[] tpnElencoDescrCivicoDaCodStradaDescrCivicoParziale(string strIn1, string strIn2);

        [OperationContract(Action = "urn:localhost-QuaestioFlorenzia#tpnCodCivicoDaCodStradaDescrCivico", ReplyAction = "*")]
        [return: MessageParameter(Name = "strOut")]
        string tpnCodCivicoDaCodStradaDescrCivico(string strIn1, string strIn2);

        [OperationContract(Action = "urn:localhost-QuaestioFlorenzia#tpnNomeStradaDescrCivicoDaCodCivico", ReplyAction = "*")]
        [return: MessageParameter(Name = "aStrOut")]
        string[] tpnNomeStradaDescrCivicoDaCodCivico(string strIn);

        [OperationContract(Action = "urn:localhost-QuaestioFlorenzia#tpnCodiceStradaNomeStradaDescrCivicoDaCodCivico", ReplyAction = "*")]
        [return: MessageParameter(Name = "aStrOut")]
        string[] tpnCodiceStradaNomeStradaDescrCivicoDaCodCivico(string strIn);

        [OperationContract(Action = "urn:localhost-QuaestioFlorenzia#tpnCodQuartiereDaCodCivico", ReplyAction = "*")]
        [return: MessageParameter(Name = "strOut")]
        string tpnCodQuartiereDaCodCivico(string strIn);

        [OperationContract(Action = "urn:localhost-QuaestioFlorenzia#tpnCodNumNomeUTOEDaCodCivico", ReplyAction = "*")]
        [return: MessageParameter(Name = "aStrOut")]
        string[] tpnCodNumNomeUTOEDaCodCivico(string strIn);

        [OperationContract(Action = "urn:localhost-QuaestioFlorenzia#tpnInAreaUnescoDaCodCivico", ReplyAction = "*")]
        [return: MessageParameter(Name = "intOut")]
        int tpnInAreaUnescoDaCodCivico(string strIn);

        [OperationContract(Action = "urn:localhost-QuaestioFlorenzia#tpnCodNumSezioneCensimentoDaCodCivico", ReplyAction = "*")]
        [return: MessageParameter(Name = "aStrOut")]
        string[] tpnCodNumSezioneCensimentoDaCodCivico(string strIn);

        [OperationContract(Action = "urn:localhost-QuaestioFlorenzia#tpnCoordinateDaCodCivico", ReplyAction = "*")]
        [return: MessageParameter(Name = "aFloatOut")]
        float[] tpnCoordinateDaCodCivico(string strIn);

        [OperationContract(Action = "urn:localhost-QuaestioFlorenzia#tpnCoordinateDaCodStradaDescrCivico", ReplyAction = "*")]
        [return: MessageParameter(Name = "aFloatOut")]
        float[] tpnCoordinateDaCodStradaDescrCivico(string strIn1, string strIn2);

        [OperationContract(Action = "urn:localhost-QuaestioFlorenzia#anaCoordinateDaCodFiscale", ReplyAction = "*")]
        [return: MessageParameter(Name = "aFloatOut")]
        float[] anaCoordinateDaCodFiscale(string strIn);

        [OperationContract(Action = "urn:localhost-QuaestioFlorenzia#anaCodCivicoDaCodFiscale", ReplyAction = "*")]
        [return: MessageParameter(Name = "strIn")]
        string anaCodCivicoDaCodFiscale(string strIn);

        [OperationContract(Action = "urn:localhost-QuaestioFlorenzia#varInAreaUnescoDaCoordinate", ReplyAction = "*")]
        [return: MessageParameter(Name = "intOut")]
        int varInAreaUnescoDaCoordinate(string strIn);

        [OperationContract(Action = "urn:localhost-QuaestioFlorenzia#varQuartiereDaCoordinate", ReplyAction = "*")]
        [return: MessageParameter(Name = "strIn")]
        string varQuartiereDaCoordinate(string strIn);
    }

    /// <summary>
    /// WCF Client proxy for QuaestioFlorenzia service
    /// </summary>
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    public partial class ProxyQuaestioFlorenzia : ClientBase<IQuaestioFlorenzia>, IQuaestioFlorenzia, IDisposable
    {
        private static readonly string DefaultUrl = "http://wsrd.comune.intranet/qfservice/server.php";

        /// <summary>
        /// Creates a new instance with default endpoint
        /// </summary>
        public ProxyQuaestioFlorenzia()
            : base(CreateDefaultBinding(), new EndpointAddress(DefaultUrl))
        {
        }

        /// <summary>
        /// Creates a new instance with specified URL
        /// </summary>
        public ProxyQuaestioFlorenzia(string url)
            : base(CreateDefaultBinding(), new EndpointAddress(url))
        {
        }

        /// <summary>
        /// Creates a new instance with specified binding and endpoint
        /// </summary>
        public ProxyQuaestioFlorenzia(Binding binding, EndpointAddress remoteAddress)
            : base(binding, remoteAddress)
        {
        }

        /// <summary>
        /// Gets or sets the service URL
        /// </summary>
        public string Url
        {
            get { return this.Endpoint.Address.Uri.ToString(); }
            set { this.Endpoint.Address = new EndpointAddress(value); }
        }

        /// <summary>
        /// Creates the default binding configured for RPC-encoded SOAP
        /// </summary>
        private static Binding CreateDefaultBinding()
        {
            var binding = new CustomBinding();

            // Text message encoding for SOAP 1.1
            var textEncoding = new TextMessageEncodingBindingElement
            {
                MessageVersion = MessageVersion.Soap11
            };
            binding.Elements.Add(textEncoding);

            // HTTP transport
            var httpTransport = new HttpTransportBindingElement
            {
                MaxReceivedMessageSize = 65536000,
                MaxBufferSize = 65536000
            };
            binding.Elements.Add(httpTransport);

            return binding;
        }

        #region Service Operations

        public string testSoap(string strIn)
        {
            return this.Channel.testSoap(strIn);
        }

        public string getMsg()
        {
            return this.Channel.getMsg();
        }

        public int tpnUnixTimeUltimoAggiornamento(string strIn)
        {
            return this.Channel.tpnUnixTimeUltimoAggiornamento(strIn);
        }

        public int aciUnixTimeUltimoAggiornamento(string strIn)
        {
            return this.Channel.aciUnixTimeUltimoAggiornamento(strIn);
        }

        public string[] ctsElencoParticellaDaFoglio(int intIn, string strIn)
        {
            return this.Channel.ctsElencoParticellaDaFoglio(intIn, strIn);
        }

        public float ctsSuperficieDaFoglioParticellaSubalterno(int intIn1, string strIn1, string strIn2, string strIn3)
        {
            return this.Channel.ctsSuperficieDaFoglioParticellaSubalterno(intIn1, strIn1, strIn2, strIn3);
        }

        public string[] ctsElencoFoglioDaFoglioParziale(int intIn, string strIn)
        {
            return this.Channel.ctsElencoFoglioDaFoglioParziale(intIn, strIn);
        }

        public string[] ctsElencoSubalternoDaFoglioParticella(int intIn1, string strIn1, string strIn2)
        {
            return this.Channel.ctsElencoSubalternoDaFoglioParticella(intIn1, strIn1, strIn2);
        }

        public bool ctsEsisteFoglioParticellaSubalterno(int intIn1, string strIn1, string strIn2, string strIn3)
        {
            return this.Channel.ctsEsisteFoglioParticellaSubalterno(intIn1, strIn1, strIn2, strIn3);
        }

        public int[] aciElencoIdImmobileDaCodFabbricato(string strIn)
        {
            return this.Channel.aciElencoIdImmobileDaCodFabbricato(strIn);
        }

        public string aciCodFabbricatoDaIdImmobile(int intIn)
        {
            return this.Channel.aciCodFabbricatoDaIdImmobile(intIn);
        }

        public int aciIdImmobileDaCodCivico(string strIn)
        {
            return this.Channel.aciIdImmobileDaCodCivico(strIn);
        }

        public string aciCodFabbricatoDaCodCivico(string strIn)
        {
            return this.Channel.aciCodFabbricatoDaCodCivico(strIn);
        }

        public string[] aciIdImmobileCodFabbricatoDaCodCivico(string strIn)
        {
            return this.Channel.aciIdImmobileCodFabbricatoDaCodCivico(strIn);
        }

        public string aciCodFabbricatoDaNomeStradaDescrCivico(string strIn1, string strIn2)
        {
            return this.Channel.aciCodFabbricatoDaNomeStradaDescrCivico(strIn1, strIn2);
        }

        public int aciIdImmobileDaNomeStradaDescrCivico(string strIn1, string strIn2)
        {
            return this.Channel.aciIdImmobileDaNomeStradaDescrCivico(strIn1, strIn2);
        }

        public string[] aciFoglioParticellaDaCodFabbricato(string strIn)
        {
            return this.Channel.aciFoglioParticellaDaCodFabbricato(strIn);
        }

        public string[] aciFoglioParticellaDaIdImmobile(string strIn)
        {
            return this.Channel.aciFoglioParticellaDaIdImmobile(strIn);
        }

        public string[] aciElencoCodFabbricatoDaFoglioParticella(int intIn, string strIn)
        {
            return this.Channel.aciElencoCodFabbricatoDaFoglioParticella(intIn, strIn);
        }

        public int[] aciElencoIdImmobileDaFoglioParticella(int intIn, string strIn)
        {
            return this.Channel.aciElencoIdImmobileDaFoglioParticella(intIn, strIn);
        }

        public string[] aciElencoCodCivicoDaCodFabbricato(string strIn)
        {
            return this.Channel.aciElencoCodCivicoDaCodFabbricato(strIn);
        }

        public string[] aciElencoCodCivicoDaIdImmobile(int intIn)
        {
            return this.Channel.aciElencoCodCivicoDaIdImmobile(intIn);
        }

        public string[] aciElencoCodCivicoEtNomeStradaEtDescrCivicoDaCodFabbricato(string strIn)
        {
            return this.Channel.aciElencoCodCivicoEtNomeStradaEtDescrCivicoDaCodFabbricato(strIn);
        }

        public string[] aciElencoCodCivicoEtNomeStradaEtDescrCivicoDaIdImmobile(int intIn)
        {
            return this.Channel.aciElencoCodCivicoEtNomeStradaEtDescrCivicoDaIdImmobile(intIn);
        }

        public string[] aciElencoCodCivicoEtNomeStradaEtDescrCivicoDaFoglioParticella(int intIn, string strIn)
        {
            return this.Channel.aciElencoCodCivicoEtNomeStradaEtDescrCivicoDaFoglioParticella(intIn, strIn);
        }

        public string[] aciElencoCodStradaEtNomeStradaDaFoglioParticella(int intIn, string strIn)
        {
            return this.Channel.aciElencoCodStradaEtNomeStradaDaFoglioParticella(intIn, strIn);
        }

        public string[] aciElencoCodFabbricatoEtFoglioEtParticellaDaCodStrada(string strIn)
        {
            return this.Channel.aciElencoCodFabbricatoEtFoglioEtParticellaDaCodStrada(strIn);
        }

        public string aciKMLDaCodFabbricato(string strIn)
        {
            return this.Channel.aciKMLDaCodFabbricato(strIn);
        }

        public string aciKMLDaIdImmobile(int intIn)
        {
            return this.Channel.aciKMLDaIdImmobile(intIn);
        }

        public string[] tpnElencoCodiceEtNomeStrada()
        {
            return this.Channel.tpnElencoCodiceEtNomeStrada();
        }

        public string[] tpnElencoCodStradaDaNomeStrada(string strIn)
        {
            return this.Channel.tpnElencoCodStradaDaNomeStrada(strIn);
        }

        public string[] tpnElencoCodEtNomeStradaDaNomeStrada(string strIn)
        {
            return this.Channel.tpnElencoCodEtNomeStradaDaNomeStrada(strIn);
        }

        public string[] tpnElencoCodEtNomeEtProbStradaDaNomeStrada(string strIn)
        {
            return this.Channel.tpnElencoCodEtNomeEtProbStradaDaNomeStrada(strIn);
        }

        public string[] tpnElencoCodEtNomeStradaDaNomeStrada_QueryLike(string strIn1, string strIn2)
        {
            return this.Channel.tpnElencoCodEtNomeStradaDaNomeStrada_QueryLike(strIn1, strIn2);
        }

        public string[] tpnElencoDescrCivicoDaNomeStrada(string strIn)
        {
            return this.Channel.tpnElencoDescrCivicoDaNomeStrada(strIn);
        }

        public string tpnNomeStradaDaCodStrada(string strIn)
        {
            return this.Channel.tpnNomeStradaDaCodStrada(strIn);
        }

        public string[] tpnElencoDescrCivicoDaCodStrada(string strIn)
        {
            return this.Channel.tpnElencoDescrCivicoDaCodStrada(strIn);
        }

        public string[] tpnElencoCodEtDescrCivicoDaCodStrada(string strIn)
        {
            return this.Channel.tpnElencoCodEtDescrCivicoDaCodStrada(strIn);
        }

        public string[] tpnElencoCodEtDescrCivicoEtCodQuartiereDaCodStrada(string strIn)
        {
            return this.Channel.tpnElencoCodEtDescrCivicoEtCodQuartiereDaCodStrada(strIn);
        }

        public string[] tpnElencoDescrCivicoDaCodStradaDescrCivicoParziale(string strIn1, string strIn2)
        {
            return this.Channel.tpnElencoDescrCivicoDaCodStradaDescrCivicoParziale(strIn1, strIn2);
        }

        public string tpnCodCivicoDaCodStradaDescrCivico(string strIn1, string strIn2)
        {
            return this.Channel.tpnCodCivicoDaCodStradaDescrCivico(strIn1, strIn2);
        }

        public string[] tpnNomeStradaDescrCivicoDaCodCivico(string strIn)
        {
            return this.Channel.tpnNomeStradaDescrCivicoDaCodCivico(strIn);
        }

        public string[] tpnCodiceStradaNomeStradaDescrCivicoDaCodCivico(string strIn)
        {
            return this.Channel.tpnCodiceStradaNomeStradaDescrCivicoDaCodCivico(strIn);
        }

        public string tpnCodQuartiereDaCodCivico(string strIn)
        {
            return this.Channel.tpnCodQuartiereDaCodCivico(strIn);
        }

        public string[] tpnCodNumNomeUTOEDaCodCivico(string strIn)
        {
            return this.Channel.tpnCodNumNomeUTOEDaCodCivico(strIn);
        }

        public int tpnInAreaUnescoDaCodCivico(string strIn)
        {
            return this.Channel.tpnInAreaUnescoDaCodCivico(strIn);
        }

        public string[] tpnCodNumSezioneCensimentoDaCodCivico(string strIn)
        {
            return this.Channel.tpnCodNumSezioneCensimentoDaCodCivico(strIn);
        }

        public float[] tpnCoordinateDaCodCivico(string strIn)
        {
            return this.Channel.tpnCoordinateDaCodCivico(strIn);
        }

        public float[] tpnCoordinateDaCodStradaDescrCivico(string strIn1, string strIn2)
        {
            return this.Channel.tpnCoordinateDaCodStradaDescrCivico(strIn1, strIn2);
        }

        public float[] anaCoordinateDaCodFiscale(string strIn)
        {
            return this.Channel.anaCoordinateDaCodFiscale(strIn);
        }

        public string anaCodCivicoDaCodFiscale(string strIn)
        {
            return this.Channel.anaCodCivicoDaCodFiscale(strIn);
        }

        public int varInAreaUnescoDaCoordinate(string strIn)
        {
            return this.Channel.varInAreaUnescoDaCoordinate(strIn);
        }

        public string varQuartiereDaCoordinate(string strIn)
        {
            return this.Channel.varQuartiereDaCoordinate(strIn);
        }

        #endregion

        #region IDisposable

        public void Dispose()
        {
            try
            {
                if (this.State != CommunicationState.Faulted)
                {
                    this.Close();
                }
                else
                {
                    this.Abort();
                }
            }
            catch
            {
                this.Abort();
            }
        }

        #endregion
    }
}
