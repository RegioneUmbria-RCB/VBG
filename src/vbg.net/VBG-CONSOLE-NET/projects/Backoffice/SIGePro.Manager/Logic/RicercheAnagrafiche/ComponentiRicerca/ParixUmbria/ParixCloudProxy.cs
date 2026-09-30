using log4net;
using System;
using System.Net;
using System.Security.Cryptography.X509Certificates;
using System.ServiceModel;
using System.ServiceModel.Channels;
using System.Text;

namespace Init.SIGePro.Manager.Logic.RicercheAnagrafiche.ComponentiRicerca.ParixUmbria
{
    internal class ParixCloudProxy
    {
        private readonly VerticalizzazioneWsAnagrafeParixCloud _configurazione;
        private readonly ILog _log = LogManager.GetLogger(typeof(ParixCloudProxy));

        public ParixCloudProxy(VerticalizzazioneWsAnagrafeParixCloud configurazione)
        {
            this._configurazione = configurazione;
        }

        public string DettaglioRidottoImpresa(string CCIAA, string NREA)
        {
            using (GatePortClient ws = this.CreaWebService())
            {

                using (OperationContextScope scope = new OperationContextScope(ws.InnerChannel))
                {
                    this.AggiungiCredenzialiCartAContextScope(this._configurazione.BasicAuthUser, this._configurazione.BasicAuthPassword);

                    this._log.DebugFormat("DettagliRidottoImpresa, prarametri: CCIAA={0}, NREA={1}, config.Switchcontrol={2}, config.User={3}, config.Password=xxxxxx", CCIAA, NREA, this._configurazione.Switchcontrol, this._configurazione.User);

                    DettaglioRidottoImpresaResponse response = ws.DettaglioRidottoImpresa(new DettaglioRidottoImpresaRequest
                    {
                        sgl_prv_sede = CCIAA,
                        n_rea_sede = int.Parse(NREA),
                        user = this._configurazione.User,
                        password = this._configurazione.Password,
                        switch_control = this._configurazione.Switchcontrol
                    });

                    var result = response?.DettaglioRidottoImpresaReturn ?? "";

                    this._log.DebugFormat("result: {0}", result);

                    return result;
                }
            }
        }

        public string RicercaImpreseNonCessatePerCodiceFiscale(string partitaIva)
        {
            try
            {
                using (GatePortClient ws = this.CreaWebService())
                {
                    using (OperationContextScope scope = new OperationContextScope(ws.InnerChannel))
                    {
                        this.AggiungiCredenzialiCartAContextScope(this._configurazione.BasicAuthUser, this._configurazione.BasicAuthPassword);

                        this._log.DebugFormat("RicercaImpreseNonCessatePerCodiceFiscale: partitaIva={0}, config.Switchcontrol={1}, config.User={2}, config.Password=xxxxxx", partitaIva, this._configurazione.Switchcontrol, this._configurazione.User);

                        RicercaImpreseNonCessatePerCodiceFiscaleResponse response = ws.RicercaImpreseNonCessatePerCodiceFiscale(new RicercaImpreseNonCessatePerCodiceFiscaleRequest
                        {
                            codice_fiscale = partitaIva,
                            password = this._configurazione.Password,
                            user = this._configurazione.User,
                            switch_control = this._configurazione.Switchcontrol
                        });

                        var result = response?.RicercaImpreseNonCessatePerCodiceFiscaleReturn ?? "";

                        this._log.DebugFormat("result: {0}", result);

                        return result;
                    }
                }
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore durante la chiamata al ws Parix: {0}", ex);

                throw;
            }
        }

        private void AggiungiCredenzialiCartAContextScope(string username, string password)
        {
            if (!String.IsNullOrEmpty(username))
            {
                this._log.DebugFormat("Il parametro BasicAuthUser non è vuoto, verrà utilizzata l'autenticazione basic per effettuare la chiamata a parix, username={0}", username);

                var credentials = this.GetCartCredentials(username, password);
                HttpRequestMessageProperty request = new HttpRequestMessageProperty();

                request.Headers[System.Net.HttpRequestHeader.Authorization] = "Basic " + credentials;

                OperationContext.Current.OutgoingMessageProperties.Add(HttpRequestMessageProperty.Name, request);
            }
        }


        private GatePortClient CreaWebService()
        {
            var url = this._configurazione.Url;
            var proxy = this._configurazione.ProxyAddress;
            var usaProxy = this._configurazione.UsaProxy;
            var usaCertificatoClient = this._configurazione.UsaCertificatoClient;
            var pathCertificatoClient = this._configurazione.GetPathCertificatoClient();
            var passwordCertificatoClient = this._configurazione.GetPasswordCertificatoClient();

            EndpointAddress endPointAddress = new EndpointAddress(url);
            BasicHttpBinding binding = new BasicHttpBinding("parixHttpBinding");

            if (!String.IsNullOrEmpty(this._configurazione.TargetNamespace))
            {
                binding.Namespace = this._configurazione.TargetNamespace;
            }

            binding.MaxReceivedMessageSize = 2147483647;
            binding.ReaderQuotas.MaxStringContentLength = 2048000;

            if (usaCertificatoClient)
            {
                binding.Security.Transport.ClientCredentialType = HttpClientCredentialType.Certificate;
            }

            Uri uri = new Uri(url);

            binding.Security.Mode = (uri.Scheme.ToUpperInvariant() == "HTTPS") ? BasicHttpSecurityMode.Transport : BasicHttpSecurityMode.None;

            if (usaProxy)
            {
                this._log.DebugFormat("Ws parix inizializzato con proxy {0}", proxy);

                binding.UseDefaultWebProxy = false;
                binding.ProxyAddress = new Uri(proxy);
            }

            GatePortClient svc = new GatePortClient(binding, endPointAddress);

            if (usaCertificatoClient)
            {
                this._log.DebugFormat("Ws parix inizializzato con il certificato trovato al path {0} {1}", pathCertificatoClient, String.IsNullOrEmpty(passwordCertificatoClient) ? "senza password" : "con password");

                X509Certificate2 certificate = new X509Certificate2(pathCertificatoClient, passwordCertificatoClient,
                    X509KeyStorageFlags.MachineKeySet
                    | X509KeyStorageFlags.PersistKeySet
                    | X509KeyStorageFlags.Exportable);
                svc.ClientCredentials.ClientCertificate.Certificate = certificate;
            }

            ServicePointManager.ServerCertificateValidationCallback += (sender, cert, chain, error) => true;
            ServicePointManager.SecurityProtocol = SecurityProtocolType.Tls12 | SecurityProtocolType.Tls11;

            return svc;
        }

        private string GetCartCredentials(string username, string password)
        {
            var credentials = username + ":" + password;

            return Convert.ToBase64String(Encoding.UTF8.GetBytes(credentials));
        }


    }
}
