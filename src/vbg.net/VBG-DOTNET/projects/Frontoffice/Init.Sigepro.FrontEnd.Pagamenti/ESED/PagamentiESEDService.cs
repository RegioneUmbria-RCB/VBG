//using log4net;
//using System;
//using System.Collections.Generic;
//using System.Linq;
//using System.Net;
//using System.Text;
//using Init.Sigepro.FrontEnd.Infrastructure.Serialization;

//namespace Init.Sigepro.FrontEnd.Pagamenti.ESED
//{
//    public class PagamentiESEDService
//    {
//        private static class Constants
//        {
//            public const string PID2Data = "extS2SPID.do";
//        }

//        PagamentiSettings _settings;
//        ClientESED _client;

//        ILog _log = LogManager.GetLogger(typeof(PagamentiESEDService));

//        public PagamentiESEDService(IPagamentiSettingsReader settingsReader)
//        {
//            this._settings = settingsReader.GetSettings();
//            this._client = new ClientESED(_settings.IV, this._settings.ChiaveSegreta, this._settings.IdPortale);
//        }

//        public ESEDCommitMessage NotificaPagamento(string pid, string idDomanda)
//        {

//            var client = new ClientESED(_settings.IV, _settings.ChiaveSegreta, _settings.IdPortale);
//            var buffer = client.GetBufferPID(pid);
//            var url = this._settings.UrlServizi + Constants.PID2Data;
//            _log.InfoFormat("invio dati a url pid: {0}, buffer: {1}", url, buffer);
//            var encryptedResult = this.InviaPaymentRequestS2SPID(buffer, url);
//            _log.InfoFormat("risposta: {0}", encryptedResult);
//            var decrypted = client.GetPaymentData(encryptedResult, Convert.ToInt32(this._settings.WindowMinutes));
//            _log.InfoFormat("risposta decriptata: {0}", decrypted);
//            var paymentData = decrypted.ClassFromXmlString<ESEDPaymentData>();

//            _log.Info("preparazione commit message");
//            var commitMsg = paymentData.ToCommitMessage();
//            _log.InfoFormat("commit message: {0}", commitMsg.ToXmlString());

//            return commitMsg;
//        }

//        private string InviaPaymentRequestS2SPID(string buffer, string url)
//        {
//            var client = new WebClient();
//            client.QueryString.Add("buffer", System.Uri.EscapeDataString(buffer));
//            client.Headers.Add(HttpRequestHeader.UserAgent, "Mozilla/5.0 (Windows NT 6.3; Trident/7.0; rv:11.0) like Gecko");
//            var res = client.DownloadString(url);

//            if (res.StartsWith("<"))
//            {
//                throw new Exception(res);
//            }

//            return res;
//        }

//    }
//}
