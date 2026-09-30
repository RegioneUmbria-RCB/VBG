using VBG.Shared.Infrastructure.ServiceModel;
using Init.SIGePro.Manager.Utils;
using System.Security.Cryptography;
using System.ServiceModel;
using System.Text;
using VBG.Backend.Protocollo.AppLogic.Core.Auriga.ServiceProxy;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.Auriga
{
    public class ProxyServiceWrapper
    {
        protected readonly string _titolo;
        protected readonly string _serviceName;
        protected readonly ParametriRegoleInfo _parametri;
        protected readonly ProtocolloSerializer _serializer;
        protected readonly ProtocolloLogs _logs;
        protected readonly ProxyRequestInfo _request;
        private readonly IBindingFactory _bindingFactory;

        protected ProxyServiceWrapper(ParametriRegoleInfo parametri, ProtocolloSerializer serializer, ProtocolloLogs logs, ProxyRequestInfo request, IBindingFactory bindingFactory, string titolo, string serviceName)
        {
            this._bindingFactory = bindingFactory;
            this._parametri = parametri;
            this._serializer = serializer;
            this._logs = logs;
            this._request = request;
            this._titolo = titolo;
            this._serviceName = serviceName;
        }

        protected AurigaProxyClient CreaWebService()
        {
            try
            {
                var endPointAddress = new EndpointAddress(this._parametri.ProxyUrl);
                var binding = this._bindingFactory.CreateAndConfigure("defaultHttpBinding");
                binding.MessageEncoding = WSMessageEncoding.Mtom;
                if (String.IsNullOrEmpty(this._parametri.Url))
                    throw new Exception("IL PARAMETRO URL DELLA VERTICALIZZAZIONE PROTOCOLLO_AURIGA NON È STATO VALORIZZATO.");

                if (endPointAddress.Uri.Scheme.ToLower() == ProtocolloConstants.HTTPS)
                {
                    binding.Security = new BasicHttpSecurity { Mode = BasicHttpSecurityMode.Transport };
                }

                return new AurigaProxyClient(binding, endPointAddress);
            }
            catch (Exception ex)
            {
                this._logs.ErrorFormat("ERRORE DURANTE LA CREAZIONE DEL WEB SERVICE {0}: {1}", this._titolo, ex.ToString());
                throw;
            }
        }

        protected AurigaProxyRequestType CreateServiceRequest(string nameSpace, string requestServiceName, TipoOperazione operazione)
        {
            return new AurigaProxyRequestType()
            {
                codiceApplicazione = this._request.codiceApplicazione,
                hash = this._request.hash,
                istanzaApplicazione = this._request.istanzaApplicazione,
                password = this._request.password,
                userName = this._request.userName,
                xml = this._request.xml,
                @namespace = nameSpace,
                token = this._request.token,
                codiciOggetto = (this._request.codiciOggetto != null) ? this._request.codiciOggetto.ToArray() : null,
                wsUrl = this._parametri.Url.Replace("{servicename}", this._serviceName),
                serviceName = requestServiceName,
                codicecomune = this._request.codicecomune,
                software = this._request.software,
                operazione = operazione.ToSerializedString()
            };
        }

        protected string getHashSHA1()
        {
            var hash = new SHA1CryptoServiceProvider().ComputeHash(Encoding.Default.GetBytes(this._request.xml));
            return Convert.ToBase64String(hash);
        }

        protected string getBase64FromXMLString(string logsFolder, string xmlFileName)
        {
            var buffer = File.ReadAllBytes(Path.Combine(logsFolder, xmlFileName));
            return Base64Utils.Base64Encode(buffer);
        }

        public void LogInfoRequestWS(string xmlInviato)
        {
            this._logs.Info($"RICHIESTA {this._titolo}: USERNAME WS: {this._request.userName}, PASSWORD WS: {this._request.password}, ISTANZAAPPLICAZIONE: {this._request.istanzaApplicazione}, CODAPPLICAZIONE: {this._request.codiceApplicazione}, REQUEST: {xmlInviato}");
        }

        public void LogInfoResponseWS(string xmlInviato)
        {
            this._logs.Info($"RISPOSTA {this._titolo}: USERNAME WS: {this._request.userName}, PASSWORD WS: {this._request.password}, ISTANZAAPPLICAZIONE: {this._request.istanzaApplicazione}, CODAPPLICAZIONE: {this._request.codiceApplicazione}, REQUEST: {xmlInviato}");
        }

        public void LogSuccess()
        {
            this._logs.Info($"CHIAMATA A {this._titolo} AVVENUTA CORRETTAMENTE");
        }
    }
}
