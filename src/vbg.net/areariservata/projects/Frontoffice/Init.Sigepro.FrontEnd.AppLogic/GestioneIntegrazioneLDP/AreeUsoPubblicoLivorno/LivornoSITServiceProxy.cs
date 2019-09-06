using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni;
using log4net;
using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.Utils.SerializationExtensions;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneIntegrazioneLDP.AreeUsoPubblicoLivorno
{
    public static class ComplexTypeAreeUsoPubblicoExtensions
    {

        public static string GetStringaRipetizioni(this ComplexTypeAreeUsoPubblico aup)
        { 
            if (String.IsNullOrEmpty(aup.ripetizione))
            {
                return String.Empty;
            }

            return $"{aup.ripetizione} ({aup.giorni_settimana})";
        }

        public static IEnumerable<IntervalloOccupazioneLDP> TointervalliOccupazione(this ComplexTypeAreeUsoPubblico aup)
        {
            var stringaRipetizioni = String.Empty;

            if (aup.a_periodi == null || aup.a_periodi.Length == 0)
            {
                return Enumerable.Empty<IntervalloOccupazioneLDP>();
            }


            return aup.a_periodi.Select(x => {
                // Formato data: "2017-01-23 00:00:00"
                var dataInizio = DateTime.ParseExact(x.inizio, "yyyy-MM-dd HH:mm:ss", null);
                var dataFine = DateTime.ParseExact(x.fine, "yyyy-MM-dd HH:mm:ss", null);
                var descrizioni = x.a_aree == null ? new[] { String.Empty } : x.a_aree.Select(a => $"{a.identificativo} mq.{a.metri_quadrati}");
                var descrizione = String.Join(Environment.NewLine, descrizioni.ToArray());


                return new IntervalloOccupazioneLDP(dataInizio, dataFine, descrizione);
            });
        }
    }

    public class LivornoSITServiceProxy
    {
        ILog _log = LogManager.GetLogger(typeof(LivornoSITServiceProxy)); 
        Uri _serviceUrl;
        BasicSoapAuthenticationCredentials _credentials;
        ILocalizzazioniService _localizzazioniService;

        internal LivornoSITServiceProxy(IConfigurazione<ParametriIntegrazioneLDP> cfg, ILocalizzazioniService localizzazioniService)
        {
            this._serviceUrl = new Uri(cfg.Parametri.UrlServizioDomanda);
            this._credentials = new BasicSoapAuthenticationCredentials(cfg.Parametri.ServiceUsername, cfg.Parametri.ServicePassword);
            this._localizzazioniService = localizzazioniService;
        }
        
        public DatiOccupazioneLDP GetDatiOccupazione(string identificativoPratica)
        {
            var dati = CallServiceMethod(ws =>
            {
                return ws.getDatiOccupazioneSuoloByIdentificativoTemporaneo(new ComplexTypeStringa { testo = identificativoPratica });
            });

            this._log.DebugFormat("Dati della pratica {0}: {1}", identificativoPratica, dati.ToXmlString());

            var intervalli = dati.TointervalliOccupazione();
            var stringaRipetizioni = dati.GetStringaRipetizioni();

            return new DatiOccupazioneLDP(stringaRipetizioni, intervalli);
        }

        private R CallServiceMethod<R>(Func<PresentazioneAreeUsoPubblicoSoapClient, R> operation)
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

        private PresentazioneAreeUsoPubblicoSoapClient CreateClient()
        {
            var endpoint = new EndpointAddress(this._serviceUrl);

            var binding = new BasicHttpBinding();

            binding.MaxBufferSize = 1024000;
            binding.MaxReceivedMessageSize = 1024000;

            if (this._serviceUrl.Scheme.ToUpper() == "HTTPS")
            {
                binding.Security.Mode = BasicHttpSecurityMode.Transport;
            }

            return new PresentazioneAreeUsoPubblicoSoapClient(binding, endpoint);
        }
    }
}
