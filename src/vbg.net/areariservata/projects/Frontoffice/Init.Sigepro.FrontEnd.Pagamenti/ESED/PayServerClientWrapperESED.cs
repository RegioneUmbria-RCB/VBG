using Init.Sigepro.FrontEnd.Pagamenti.MIP.Client;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.Pagamenti.MIP;
using log4net;
using System.Net;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.Infrastructure.Serialization;

namespace Init.Sigepro.FrontEnd.Pagamenti.ESED
{
    public class PayServerClientWrapperESED : IPayServerClient
    {
        private static class Constants
        {
            public const string Request2RID = "extS2SRID.do";
            public const string PagamentoEsterno = "extCart.do";
            public const string PID2Data = "extS2SPID.do";
            //public const string StatoPagamento = "PaymentStatusRequest.jsp";
        }

        PayServerClientSettings _settings;
        ILog _log = LogManager.GetLogger(typeof(PayServerClientWrapperESED));
        IUrlEncoder _urlEncoder = new HttpContextUrlEncoder();
        IGetStatoPagamento _getStatoPagamento;

        public PayServerClientWrapperESED(PayServerClientSettings settings, IUrlEncoder urlEncoder, IGetStatoPagamento getStatoPagamento)
        {
            _settings = settings;
            this._urlEncoder = urlEncoder;
            this._getStatoPagamento = getStatoPagamento;
        }

        public string EstraiBuffer(string buffer)
        {
            var client = new ClientESED(_settings.ChiaveIV, _settings.ChiaveSegreta, _settings.IdPortale);

            _log.InfoFormat("PID: {0}", buffer);
            var bufferPID = client.GetBufferPID(buffer);
            _log.InfoFormat("bufferPID: {0}", bufferPID);

            var url = this._settings.UrlServizi + Constants.PID2Data;
            _log.InfoFormat("invio conclusione pagamento url: {0}", url);
            var encryptedResult = this.InviaPaymentRequestS2SPID(bufferPID, url);
            _log.InfoFormat("encrypted result conclusione pagamento restituito dalla url: {0}, windows minutes: {1}", encryptedResult, this._settings.WindowMinutes);
            var decrypted = client.GetPaymentData(encryptedResult, this._settings.WindowMinutes);
            _log.InfoFormat("risultato decriptato conclusione pagamento: {0}", decrypted);

            return decrypted;
        }

        private string InviaPaymentRequestS2S(string buffer, string url)
        {
            var client = new WebClient();
            client.QueryString.Add("buffer", System.Uri.EscapeDataString(buffer));
            client.Headers.Add(HttpRequestHeader.UserAgent, "Mozilla/5.0 (Windows NT 6.3; Trident/7.0; rv:11.0) like Gecko");
            var res = client.DownloadString(url);

            if (res.StartsWith("<"))
            {
                throw new Exception(res);
            }
            
            return res;
        }

        private string InviaPaymentRequestS2SPID(string buffer, string url)
        {
            var client = new WebClient();
            client.QueryString.Add("buffer", System.Uri.EscapeDataString(buffer));
            client.Headers.Add(HttpRequestHeader.UserAgent, "Mozilla/5.0 (Windows NT 6.3; Trident/7.0; rv:11.0) like Gecko");
            var res = client.DownloadString(url);

            return res;
        }

        public string GeneraUrlRedirect(PaymentRequest request)
        {
            
            var requestESED = new PaymentRequestESED(request);

            var url = this._settings.UrlServizi + Constants.Request2RID;
            var client = new ClientESED(_settings.ChiaveIV, _settings.ChiaveSegreta, _settings.IdPortale);
            var xmlRequest = requestESED.ToXmlString();

            this._log.DebugFormat("chiamata a GetBufferPaymentRequest con xmlRequest={0}", xmlRequest);

            string buffer = client.GetBufferPaymentRequest(xmlRequest);

            this._log.DebugFormat("buffer restituito da GetBufferPaymentRequest={0}", buffer);
            
            var rid = this.InviaPaymentRequestS2S(buffer, url);
            var bufferRid = client.GetBufferRID(rid);

            var urlChiamata = $"{this._settings.UrlServizi}{Constants.PagamentoEsterno}?buffer={System.Uri.EscapeDataString(bufferRid)}";

            return urlChiamata;

        }

        public string GetStatoPagamento(MIPPaymentStatusRequest request)
        {
            return this._getStatoPagamento.GetDatiPagamento(request.NumeroOperazione);
        }

        public ESEDPaymentData GetDatiDaNotificaPagamento(string bufferPID)
        {
            _log.InfoFormat("settings, chiave IV: {0}, chiave segreta: {1}, id portale: {2}", _settings.ChiaveIV, _settings.ChiaveSegreta, _settings.IdPortale);
            var client = new ClientESED(_settings.ChiaveIV, _settings.ChiaveSegreta, _settings.IdPortale);

            _log.InfoFormat("chiamata a GetBufferPID: {0}", bufferPID);
            var buffer = client.GetBufferPID(bufferPID);
            _log.InfoFormat("Buffer restituito: {0}", buffer);
            var url = this._settings.UrlServizi + Constants.PID2Data;
            _log.InfoFormat("Invio buffer a Url: {0}", url);
            var encryptedResult = this.InviaPaymentRequestS2SPID(buffer, url);
            _log.InfoFormat("encrypted result restituito dalla url: {0}, windows minutes: {1}", encryptedResult, this._settings.WindowMinutes);
            var decrypted = client.GetPaymentData(encryptedResult, this._settings.WindowMinutes);
            _log.InfoFormat("risultato decriptato: {0}", decrypted);
            _log.InfoFormat("deserializzazione del risultato decriptato");
            var paymentData = decrypted.ClassFromXmlString<ESEDPaymentData>();
            _log.InfoFormat("deserializzazione del risultato decriptato avvenuta con successo");

            return paymentData;
        }

        public ESEDCommitMessage CreateCommitMessage(ESEDPaymentData paymentData)
        {
            throw new NotImplementedException();
        }
    }
}
