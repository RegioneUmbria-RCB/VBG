using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni;
using log4net;
using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.Utils.SerializationExtensions;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneIntegrazioneLDP.PresentazionePraticheEdilizieSiena
{
    internal class LDPServiceProxy
    {
        ILog _log = LogManager.GetLogger(typeof(LDPServiceProxy));
        Uri _serviceUrl;
        BasicSoapAuthenticationCredentials _credentials;
        ILocalizzazioniService _localizzazioniService;

        internal LDPServiceProxy(IConfigurazione<ParametriIntegrazioneLDP> cfg, ILocalizzazioniService localizzazioniService)
        {
            this._serviceUrl = new Uri(cfg.Parametri.UrlServizioDomanda);
            this._credentials = new BasicSoapAuthenticationCredentials(cfg.Parametri.ServiceUsername, cfg.Parametri.ServicePassword);
            this._localizzazioniService = localizzazioniService;
        }

        public LocalizzazioneInterventoLDP GetDatiPratica(string identificativoPratica)
        {
            var dati = CallServiceMethod(ws =>
            {
                return ws.getDatiTerritorialiByIdentificativoTemporaneo(new ComplexTypeStringa { testo = identificativoPratica });
            });

            this._log.DebugFormat("Dati della pratica {0}: {1}", identificativoPratica, dati.ToXmlString());

            return new LocalizzazioneInterventoLDP(dati, this._localizzazioniService);
        }

        private R CallServiceMethod<R>(Func<PresentazionePraticheEdilizieSoapClient, R> operation)
        {
            using (var ws = CreateClient())
            {
                try
                {
                    using (var scope = new OperationContextScope(ws.InnerChannel))
                    {
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

            var binding = new BasicHttpBinding();

            binding.MaxBufferSize = 1024000;
            binding.MaxReceivedMessageSize = 1024000;

            if (this._serviceUrl.Scheme.ToUpper() == "HTTPS")
            {
                binding.Security.Mode = BasicHttpSecurityMode.Transport;
            }

            return new PresentazionePraticheEdilizieSoapClient(binding, endpoint);
        }
    }
}
