using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using log4net;
using System;
using System.Collections.Generic;
using Init.Sigepro.FrontEnd.Infrastructure.Serialization;
using Rgls.Cig.Utility;
using Rgls.Cig.SecretCode;

namespace Init.Sigepro.FrontEnd.Pagamenti.MIP.Client
{
    internal class PayServerClientWrapper : IPayServerClient
    {
        private static class Constants
        {
            public const string Request2RID = "Request2RID.jsp";
            public const string PagamentoEsterno = "pagamentoesterno.do";
            public const string PID2Data = "PID2Data.jsp";
            public const string StatoPagamento = "PaymentStatusRequest.jsp";
        }

        PayServerClientSettings _settings;
        ILog _log = LogManager.GetLogger(typeof(PayServerClientWrapper));
        IUrlEncoder _urlEncoder = new HttpUtilityUrlEncoder();

        Dictionary<int, string> _messaggiErrore = new Dictionary<int, string>();

        internal PayServerClientWrapper(PayServerClientSettings settings, IUrlEncoder urlEncoder)
        {
            this._settings = settings;
            this._urlEncoder = urlEncoder;

            this._messaggiErrore = new Dictionary<int, string>
            {
                { CodiciErroreMIP.PS2S_COMPERROR, "Fallita Inizializzazione Applicazione"},
                { CodiciErroreMIP.PS2S_DATAERROR, "Impossibile estrarre buffer dati" },
                { CodiciErroreMIP.PS2S_DATEERROR, "Data non accettabile" },
                { CodiciErroreMIP.PS2S_HASHERROR, "Fallita Verifica Hash" },
                { CodiciErroreMIP.PS2S_HASHNOTFOUND, "HASH non trovato" },
                { CodiciErroreMIP.PS2S_CREATEHASHERROR, "Impossibile creare buffer HASH" },
                { CodiciErroreMIP.PS2S_TIMEELAPSED, "Finestra temporale scaduta" },
                { CodiciErroreMIP.PS2S_XMLERROR, "Documento XML non valido: " },
                { CodiciErroreMIP.PS2S_HTTPCONNECTION, "Connessione verso il server non effettuata, verificare i parametri di connessione" }
            };
        }

        public string GeneraUrlRedirect(PaymentRequest request)
        {
            try
            {
                var url = this._settings.UrlServizi + Constants.Request2RID;
                var client = CreateClient(url);
                var xmlRequest = request.ToXmlString();

                this._log.Debug($"chiamata a PS2S_PC_Request2RID con xmlRequest={xmlRequest}");

                var result = client.PS2S_PC_Request2RID(xmlRequest, this._settings.ComponentName, new DateTime());

                if (result != 0)
                {
                    var errMsg = client.GetErrorDescr(result);

                    this._log.Debug($"chiamata a PS2S_PC_Request2RID fallita. Result: {result}, Error message: {errMsg}");
                    
                    throw new PaymentServiceException(errMsg);
                }

                var clientBuffer = client.PS2S_NetBuffer;
                var urlChiamata = $"{this._settings.UrlServizi}{Constants.PagamentoEsterno}?buffer={this._urlEncoder.UrlEncode(clientBuffer)}";

                this._log.Debug($"Url da utilizzare per redirect: {urlChiamata}");

                return urlChiamata;
            }
            catch (Exception ex)
            {
                _log.ErrorFormat("Errore durante la creazione dell'url di redirezione (PS2S_PC_Request2RID): {0}", ex.ToString());

                throw;
            }
        }

        public string EstraiBuffer(string buffer)
        {
            var url = this._settings.UrlServizi + Constants.PID2Data;
            var client = CreateClient(url);

            var r = client.PS2S_PC_PID2Data(buffer ?? String.Empty, this._settings.ComponentName, new DateTime(), this._settings.WindowMinutes);

            if (r != 0)
            {
                var errore = DecodificaErrore(r);

                throw new MipException(errore);
            }

            return client.PS2S_DataBuffer;      
        }

        public string GetStatoPagamento(MIPPaymentStatusRequest request)
        {
            try
            {
                var url = this._settings.UrlServizi + Constants.Request2RID;
                var client = CreateClient(url);
                var xmlRequest = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" + request.ToXmlString();

                xmlRequest = xmlRequest.Replace(" xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\"", "");

                this._log.Debug($"GetStatoPagamento: chiamata a PS2S_PC_Request2RID con xmlRequest={xmlRequest}");

                var result = client.PS2S_PC_Request2RID(xmlRequest, this._settings.ComponentName, new DateTime());

                if (result != 0)
                {
                    var errMsg = client.GetErrorDescr(result);
                    throw new PaymentServiceException(errMsg);
                }

                var clientBuffer = client.PS2S_NetBuffer;
                var urlChiamata = $"{this._settings.UrlServizi}{Constants.StatoPagamento}?buffer={clientBuffer}";

                this._log.Debug($"Url da utilizzare per verificare lo stato del pagamento: {urlChiamata}");

                var uw = CreaUrlWorker(urlChiamata);
                var messaggioErrore = uw.DoGet();

                if (!String.IsNullOrEmpty(messaggioErrore))
                {
                    var errMsg = $"Impossibile chiamare l'indirizzo {urlChiamata} per ottenere informazioni sul pagamento: {messaggioErrore}";
                    throw new PaymentServiceException(errMsg);
                }

                var buffer = EstraiBuffer(uw.BufferOut);


                return buffer;
            }
            catch(Exception ex)
            {
                _log.Error($"Errore durante la verifica dello stato di un pagamento (GetStatoPagamento): {ex}");

                throw;
            }
        }


        private string DecodificaErrore(int r)
        {
            if (this._messaggiErrore.ContainsKey(r))
            {
                return this._messaggiErrore[r];
            }

            return String.Format("Errore non definito: {0}", r);
        }

        private PayServerClient CreateClient(string urlAddress)
        {
            var client = new PayServerClient(this._settings.ChiaveSegreta, PayServerClient.PS2S_KT_CLEAR, true);

            this._log.Debug($"PayServerClient inizializzato con ServerUrl={urlAddress}");

            client.ServerURL = urlAddress;

            if (!String.IsNullOrEmpty(this._settings.ProxyAddress))
            {
                var parts = this._settings.ProxyAddress.Split(':');

                if (parts.Length == 1)
                {
                    parts = new string[]{
                        parts[0],
                        "80"
                    };
                }

                this._log.Debug($"PayServerClient inizializzato con urlAddress={urlAddress}, proxyserver={parts[0]} e proxyport={parts[1]}");

                client.ProxyServer = parts[0];
                client.ProxyPort = parts[1];
            }

            return client;
        }

        private URLWorker CreaUrlWorker(string url)
        {
            var urlWorker = new URLWorker();
            urlWorker.URL = url;

            if (!String.IsNullOrEmpty(this._settings.ProxyAddress))
            {
                var parts = this._settings.ProxyAddress.Split(':');

                if (parts.Length == 1)
                {
                    parts = new string[]{
                        parts[0],
                        "80"
                    };
                }

                this._log.Debug($"PayServerClient inizializzato con proxyserver={parts[0]} e proxyport={parts[1]}");

                urlWorker.ProxyServer = parts[0];
                urlWorker.ProxyPort = parts[1];
            }


            return urlWorker;
        }
    }
}
