using Init.SIGePro.Manager.AdrierService;
using log4net;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.ServiceModel;
using System.ServiceModel.Channels;
using System.Text;

namespace Init.SIGePro.Manager.Logic.RicercheAnagrafiche.Adrier
{
    public class AdrierProxy
    {
        ConfigurazioneAdrier _verticalizzazione;
        ILog _log = LogManager.GetLogger(typeof(AdrierProxy));

        public AdrierProxy(ConfigurazioneAdrier verticalizzazione)
        {
            this._verticalizzazione = verticalizzazione;
        }

        private RicercaImpreseClient CreaWebService()
        {
            var config = this._verticalizzazione.Get;

            var endPointAddress = new EndpointAddress(config.Url);
            var binding = new BasicHttpBinding("parixHttpBinding");

            binding.MaxReceivedMessageSize = 2147483647;
            binding.ReaderQuotas.MaxStringContentLength = 2048000;

            var uri = new Uri(config.Url);

            binding.Security.Mode = (uri.Scheme.ToUpperInvariant() == "HTTPS") ? BasicHttpSecurityMode.Transport : BasicHttpSecurityMode.None;

            if (!String.IsNullOrEmpty(config.ProxyAddress))
            {
                binding.UseDefaultWebProxy = false;
                binding.ProxyAddress = new Uri(config.ProxyAddress);
            }

            var ric = new RicercaImpreseClient(binding, endPointAddress);

            return ric;
        }

        /// <summary>
        /// Il web method indica di passare il codice fiscale ma prende anche la partita iva, come in questo caso.
        /// </summary>
        /// <param name="partitaIva"></param>
        /// <returns></returns>
        public string RicercaImpreseNonCessatePerPartitaIva(string partitaIva)
        {
            var config = this._verticalizzazione.Get;

            using (var ws = CreaWebService())
            {
                _log.DebugFormat("RicercaImpreseNonCessatePerCodiceFiscale: codiceFiscale={0}, config.User={1}, config.Password={2}", partitaIva, config.Username, config.Password);
                string result = ws.RicercaImpreseNonCessatePerCodiceFiscale(partitaIva, "", config.Username, config.Password);
                _log.Debug("result: " + result);
                return result;
            }
        }

        public string DettaglioRidottoImpresa(string CCIAA, string NREA)
        {
            var config = this._verticalizzazione.Get;

            using (var ws = CreaWebService())
            {
                _log.DebugFormat("DettagliRidottoImpresa, prarametri: CCIAA={0}, NREA={1}, config.Switchcontrol={2}, config.User={3}, config.Password={4}", CCIAA, NREA, config.SwitchControl, config.Username, config.Password);
                string result = ws.DettaglioRidottoImpresa(CCIAA, NREA, "", config.Username, config.Password);
                _log.Debug("result: " + result);
                return result;
            }
        }

    }
}
