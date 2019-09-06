using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using log4net;
using Rgls.Cig.SecretCode;
using Rgls.Cig.Utility;
using System;
using System.Collections.Generic;
using Init.Sigepro.FrontEnd.Infrastructure.Serialization;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.Pagamenti.MIP.Client
{
    public class PayServerClientWrapperNative : IPayServerClient
    {
        private static class Constants
        {
            public const string Request2RID = "Request2RID.jsp";
            public const string PagamentoEsterno = "pagamentoesterno.do";
            public const string PID2Data = "PID2Data.jsp";
            public const string StatoPagamento = "PaymentStatusRequest.jsp";
        }

        PayServerClientSettings _settings;
        ILog _log = LogManager.GetLogger(typeof(PayServerClientWrapperNative));
        IUrlEncoder _urlEncoder = new HttpContextUrlEncoder();

        Dictionary<int, string> _messaggiErrore = new Dictionary<int, string>();

        internal PayServerClientWrapperNative(PayServerClientSettings settings, IUrlEncoder urlEncoder)
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

                this._log.DebugFormat("chiamata a PS2S_PC_Request2RID con xmlRequest={0}", xmlRequest);

                var result = client.PS2S_PC_Request2RID(xmlRequest, this._settings.ComponentName, DateTime.Now);

                if (result != 0)
                {
                    var errMsg = client.GetErrorDescr(result);

                    this._log.DebugFormat("chiamata a PS2S_PC_Request2RID fallita. Result: {0}, Error message: {1}", result, errMsg);
                    
                    throw new PaymentServiceException(errMsg);
                }

                var clientBuffer = client.PS2S_NetBuffer;
                var urlChiamata = String.Format("{0}{1}?buffer={2}", this._settings.UrlServizi, Constants.PagamentoEsterno, this._urlEncoder.UrlEncode(clientBuffer));

                this._log.DebugFormat("Url da utilizzare per redirect: {0}", urlChiamata);

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

            var r = client.PS2S_PC_PID2Data(buffer ?? String.Empty, this._settings.ComponentName, DateTime.Now, this._settings.WindowMinutes);

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

                this._log.DebugFormat("GetStatoPagamento: chiamata a PS2S_PC_Request2RID con xmlRequest={0}", xmlRequest);

                var result = client.PS2S_PC_Request2RID(xmlRequest, this._settings.ComponentName, DateTime.Now);

                if (result != 0)
                {
                    var errMsg = client.GetErrorDescr(result);
                    throw new PaymentServiceException(errMsg);
                }

                var clientBuffer = client.PS2S_NetBuffer;
                var urlChiamata = String.Format("{0}{1}?buffer={2}", this._settings.UrlServizi, Constants.StatoPagamento, clientBuffer);

                this._log.DebugFormat("Url da utilizzare per verificare lo stato del pagamento: {0}", urlChiamata);

                var uw = CreaUrlWorker(urlChiamata);
                var errore = uw.DoGet();
                if (errore != null)
                {
                    var errMsg = String.Format("Impossibile chiamare l'indirizzo {0} per ottenere informazioni sul pagamento: {1}", urlChiamata, errore);
                    throw new PaymentServiceException(errMsg);
                }

                var buffer = EstraiBuffer(uw.BufferOut);


                return buffer;
            }
            catch(Exception ex)
            {
                _log.ErrorFormat("Errore durante la verifica dello stato di un pagamento (GetStatoPagamento): {1}", ex.ToString());

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
            var client = new PayServerClient(this._settings.ChiaveSegreta, PayServerClient.PS2S_KT_CLEAR);

            this._log.DebugFormat("PayServerClient inizializzato con ServerUrl={0}", urlAddress);

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

                this._log.DebugFormat("PayServerClient inizializzato con urlAddress={0}, proxyserver={1} e proxyport={2}",urlAddress, parts[0], parts[1]);

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

                this._log.DebugFormat("PayServerClient inizializzato con proxyserver={0} e proxyport={1}", parts[0], parts[1]);

                urlWorker.ProxyServer = parts[0];
                urlWorker.ProxyPort = parts[1];
            }


            return urlWorker;
        }
    }
}
