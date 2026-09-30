using Init.Sigepro.FrontEnd.Infrastructure.Serialization;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using log4net;
using System;
using VBG.Pagamenti.Legacy.MIP.Client;

namespace VBG.Pagamenti.Legacy.MIP
{
    public class MIPPaymentService
    {
        private readonly PagamentiSettings _settings;
        private readonly ILog _log = LogManager.GetLogger(typeof(MIPPaymentService));
        private readonly IMIPPaymentRequestFactory _paymentRequestFactory;
        private readonly PayServerClientFactory _payServerClientFactory;

        public MIPPaymentService(IPagamentiSettingsReader settingsReader, IMIPPaymentRequestFactory paymentRequestFactory, PayServerClientFactory payServerClientFactory)
        {
            this._settings = settingsReader.GetSettings();
            this._paymentRequestFactory = paymentRequestFactory;
            this._payServerClientFactory = payServerClientFactory;
        }

        public string IniziaPagamento(IniziaPagamentoRequest request)
        {
            var paymentRequest = this._paymentRequestFactory.Create(request);
            var client = this._payServerClientFactory.CreateClient(new PayServerClientSettings(this._settings), new HttpUtilityUrlEncoder(), request.ClientType);

            return client.GeneraUrlRedirect(paymentRequest);
        }

        public MIPError GetRagioneAnnullamentoPagamento(string buffer, string clientType)
        {
            try
            {
                var client = this._payServerClientFactory.CreateClient(new PayServerClientSettings(this._settings), new HttpUtilityUrlEncoder(), clientType);

                var errString = client.EstraiBuffer(buffer);

                return MIPError.FromXmlString(errString);
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore durante il recupero dei dati dal buffer {0}: {1}", buffer, ex.ToString());

                throw;
            }
        }


        public MIPEsitoPagamento GetStatoPagamento(string numeroOperazione, string clientType)
        {
            try
            {
                var client = this._payServerClientFactory.CreateClient(new PayServerClientSettings(this._settings), new HttpUtilityUrlEncoder(), clientType);
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
                this._log.ErrorFormat("Errore durante la lettura dello stato di pagamento per l'operazione {0}: {1}", numeroOperazione, ex.ToString());

                throw;
            }
        }


        public MIPEsitoPagamento DatiPagamento(string mipBuffer, string clientType)
        {
            try
            {
                var client = this._payServerClientFactory.CreateClient(new PayServerClientSettings(this._settings), new HttpUtilityUrlEncoder(), clientType);

                var esito = client.EstraiBuffer(mipBuffer);

                this._log.InfoFormat("Buffer dati pagamento: {0}", esito);

                return esito.ClassFromXmlString<MIPEsitoPagamento>();
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore durante il recupero dei dati del pagamento con buffer {0}: {1}", mipBuffer, ex.ToString());

                throw;
            }
        }
    }
}
