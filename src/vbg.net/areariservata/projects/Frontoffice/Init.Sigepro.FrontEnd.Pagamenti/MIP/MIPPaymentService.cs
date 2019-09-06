using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.Infrastructure.Serialization;
using log4net;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Init.Sigepro.FrontEnd.Pagamenti.MIP.Client;

namespace Init.Sigepro.FrontEnd.Pagamenti.MIP
{
    public class MIPPaymentService
    {
        PagamentiSettings _settings;
        ILog _log = LogManager.GetLogger(typeof(MIPPaymentService));

        public MIPPaymentService(IPagamentiSettingsReader settingsReader)
        {
            this._settings = settingsReader.GetSettings();
        }

        public string IniziaPagamento(IniziaPagamentoRequest request)
        {
            var paymentRequest = new PaymentRequest(this._settings, request);
            var client = new PayServerClientFactory().CreateClient(new PayServerClientSettings(this._settings), new HttpContextUrlEncoder());

            return client.GeneraUrlRedirect(paymentRequest);
        }

        public MIPError GetRagioneAnnullamentoPagamento(string buffer)
        {
            try
            {
                var client = new PayServerClientFactory().CreateClient(new PayServerClientSettings(this._settings), new HttpContextUrlEncoder());

                var errString = client.EstraiBuffer(buffer);

                return MIPError.FromXmlString(errString);
            }
            catch (Exception ex)
            {
                _log.ErrorFormat("Errore durante il recupero dei dati dal buffer {0}: {1}", buffer, ex.ToString());

                throw;
            }
        }


        public MIPEsitoPagamento GetStatoPagamento(string numeroOperazione)
        {
            try
            {
                var client = new PayServerClientFactory().CreateClient(new PayServerClientSettings(this._settings), new HttpContextUrlEncoder());
                var request = new MIPPaymentStatusRequest
                {
                    NumeroOperazione = numeroOperazione,
                    PortaleID = this._settings.IdPortale,
                    RitornaDatiSpecifici = "N"
                };

                var stato = client.GetStatoPagamento(request);

                return stato.ClassFromXmlString<MIPEsitoPagamento>();
            }
            catch (Exception ex)
            {
                _log.ErrorFormat("Errore durante la lettura dello stato di pagamento per l'operazione {0}: {1}", numeroOperazione, ex.ToString());

                throw;
            }
        }


        public MIPEsitoPagamento DatiPagamento(string mipBuffer)
        {
            try
            {
                var client = new PayServerClientFactory().CreateClient(new PayServerClientSettings(this._settings), new HttpContextUrlEncoder());

                var esito = client.EstraiBuffer(mipBuffer);

                _log.InfoFormat("Buffer dati pagamento: {0}", esito);

                return esito.ClassFromXmlString<MIPEsitoPagamento>();
            }
            catch (Exception ex)
            {
                _log.ErrorFormat("Errore durante il recupero dei dati del pagamento con buffer {0}: {1}", mipBuffer, ex.ToString());

                throw;
            }
        }
    }
}
