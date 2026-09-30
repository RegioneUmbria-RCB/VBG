using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni;
using Init.Sigepro.FrontEnd.AppLogic.Utils.SerializationExtensions;
using VBG.Shared.Infrastructure.ServiceModel;
using Init.Sigepro.FrontEnd.Infrastructure.Web;
using log4net;
using System;
using System.ServiceModel;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneIntegrazioneLDP.PresentazionePraticheEdilizieSiena
{
    public class LDPServiceProxy
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(LDPServiceProxy));
        private readonly Uri _serviceUrl;
        private readonly BasicSoapAuthenticationCredentials _credentials;
        private readonly IConfigurazione<ParametriIntegrazioneLDP> _cfg;
        private readonly ILocalizzazioniService _localizzazioniService;
        private readonly Tls12Utils _tls12Utils;
        private readonly IBindingFactory _bindingFactory;

        public LDPServiceProxy(IConfigurazione<ParametriIntegrazioneLDP> cfg, ILocalizzazioniService localizzazioniService, Tls12Utils tls12Utils, IBindingFactory bindingFactory)
        {
            this._serviceUrl = new Uri(cfg.Parametri.UrlServizioDomanda);
            this._credentials = new BasicSoapAuthenticationCredentials(cfg.Parametri.ServiceUsername, cfg.Parametri.ServicePassword);
            this._cfg = cfg;
            this._localizzazioniService = localizzazioniService;
            this._tls12Utils = tls12Utils;
            this._bindingFactory = bindingFactory;
        }

        public LocalizzazioneInterventoLDP GetDatiPratica(string identificativoPratica)
        {
            var dati = this.CallServiceMethod(ws =>
            {
                return ws.getDatiTerritorialiByIdentificativoTemporaneo(new ComplexTypeStringa { testo = identificativoPratica });
            });
            this._log.DebugFormat("Dati della pratica {0}: {1}", identificativoPratica, dati.ToXmlString());
            return new LocalizzazioneInterventoLDP(dati, this._localizzazioniService);
        }

        private R CallServiceMethod<R>(Func<PresentazionePraticheEdilizieSoapClient, R> operation)
        {
            using (var ws = this.CreateClient())
            {
                try
                {
                    using (var scope = new OperationContextScope(ws.InnerChannel))
                    {
                        this._tls12Utils.ApplicaImpostazioniTls12(ws.Endpoint.Address.Uri.ToString());
                        this._credentials.AggiungiCredenzialiAContextScope();
                        return operation(ws);
                    }
                }
                catch (Exception)
                {
                    ws.Abort();
                    throw;
                }
            }
        }

        private PresentazionePraticheEdilizieSoapClient CreateClient()
        {
            var endpoint = new EndpointAddress(this._serviceUrl);
            var binding = this._bindingFactory.CreateAndConfigure("LDPService");
            binding.MaxBufferSize = 1024000;
            binding.MaxReceivedMessageSize = 1024000;
            if (this._serviceUrl.Scheme.ToUpper() == "HTTPS")
            {
                binding.Security.Mode = BasicHttpSecurityMode.Transport;
            }

            // binding.Security.Transport.ClientCredentialType = HttpClientCredentialType.Basic;

            var client = new PresentazionePraticheEdilizieSoapClient(binding, endpoint);
            // client.ChannelFactory.Credentials.UserName.UserName = this._cfg.Parametri.ServiceUsername;
            // client.ChannelFactory.Credentials.UserName.Password = this._cfg.Parametri.ServicePassword;


            return client;
        }
    }
}