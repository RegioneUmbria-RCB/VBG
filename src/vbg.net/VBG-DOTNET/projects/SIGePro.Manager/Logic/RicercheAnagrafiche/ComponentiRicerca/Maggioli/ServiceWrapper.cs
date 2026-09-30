using Init.SIGePro.Manager.Logic.RicercheAnagrafiche.ComponentiRicerca.Maggioli.CmnWSSGateway;
using Init.SIGePro.Manager.Utils.Extensions;
using log4net;
using System;
using System.ServiceModel;

namespace Init.SIGePro.Manager.Logic.RicercheAnagrafiche.ComponentiRicerca.Maggioli
{
    public class ServiceWrapper
    {
        private readonly string _url;
        private readonly ILog _log = LogManager.GetLogger(typeof(MaggioliAnagrafeSearcher));

        public ServiceWrapper(string url)
        {
            this._url = url;
        }

        private CmnWSSGatewayClient CreaWebService()
        {
            try
            {
                var endPointAddress = new EndpointAddress(this._url);
#if NET48_OR_GREATER
                var binding = new BasicHttpBinding("defaultHttpBinding");
#endif

#if NET9_0_OR_GREATER
                var binding = new BasicHttpBinding();
#endif
                if (String.IsNullOrEmpty(this._url))
                    throw new Exception("IL PARAMETRO URL DELLA VERTICALIZZAZIONE ANAGRAFE_MAGGIOLI NON È STATO VALORIZZATO.");

                if (endPointAddress.Uri.Scheme.ToLower() == "https")
                {
                    binding.Security = new BasicHttpSecurity { Mode = BasicHttpSecurityMode.Transport };
                }

                return new CmnWSSGatewayClient(binding, endPointAddress);
            }
            catch (Exception ex)
            {
                throw new Exception($"ERRORE DURANTE LA CREAZIONE DEL WEB SERVICE, {ex.Message}", ex);
            }
        }

        public LeggiAnagraficaSikuelRisposta GetAnagrafica(string[] request)
        {
            using (var ws = this.CreaWebService())
            {
                this._log.Debug("CHIAMATA A RICERCA ANAGRAFICA");
                var response = ws.service(request);
                this._log.Debug($"CHIAMATA A RICERCA ANAGRAFICA TERMINATA, response: {response}");
                this._log.Debug("DESERIALIZZAZIONE DELLA RISPOSTA");
                var retVal = SerializationExtensions.XmlDeserializeFromString<LeggiAnagraficaSikuelRisposta>(response);
                this._log.Debug("DESERIALIZZAZIONE DELLA RISPOSTA AVVENUTA CORRETTAMENTE");
                if (retVal.Esito.cod != "0")
                {
                    this._log.Error($"ERRORE RESTITUITO DAL WS: CODICE ERRORE: {retVal.Esito.cod}, DESCRIZIONE ERRORE: {retVal.Esito.messaggio}");
                    if (retVal.Esito.cod != "16")
                    {
                        throw new Exception($"({retVal.Esito.cod}) {retVal.Esito.messaggio}");
                    }
                    return null;
                }

                return retVal;
            }
        }
    }
}