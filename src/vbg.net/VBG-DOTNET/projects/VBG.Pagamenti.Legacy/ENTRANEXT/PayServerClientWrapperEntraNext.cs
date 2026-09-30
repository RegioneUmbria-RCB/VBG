using VBG.Shared.Infrastructure.ServiceModel;
using log4net;
using System;
using System.Net;
using System.ServiceModel;
using VBG.Pagamenti.Legacy.EntraNextService;

namespace VBG.Pagamenti.Legacy.ENTRANEXT
{
    public class PayServerClientWrapperEntraNext
    {
        private class Constants
        {
            public const string HTTPS = "https";
            public const string Versione = "1.3";
            public const string OK = "OK";
        }

        private readonly ILog _log = LogManager.GetLogger(typeof(PayServerClientWrapperEntraNext));
        private readonly PaymentSettingsEntraNext _settings;
        private readonly IBindingFactory _bindingFactory;
        private readonly IntestazioneFO _intestazione;
        private readonly LoginRequest _loginRequest;

        public PayServerClientWrapperEntraNext(PaymentSettingsEntraNext settings, IBindingFactory bindingFactory)
        {
            this._settings = settings;
            this._bindingFactory = bindingFactory;
            this._intestazione = new IntestazioneFO
            {
                CodiceFiscaleEnte = this._settings.CodiceFiscaleEnte,
                IdentificativoConnettore = this._settings.IdentificativoConnettore
            };

            this._loginRequest = new LoginRequest
            {
                Versione = Constants.Versione,
                Identificativo = this._settings.Identificativo,
                Username = this._settings.Username,
                PasswordMD5 = this._settings.Password
            };
        }

        private LinkNextSoapClient CreaWebService()
        {
            try
            {
                var endPointAddress = new EndpointAddress(this._settings.UrlWs);
                var binding = this._bindingFactory.CreateAndConfigure("entraNextServiceBinding");

                if (endPointAddress.Uri.Scheme.ToLower() == Constants.HTTPS)
                {
                    binding.Security.Mode = BasicHttpSecurityMode.Transport;

                    ServicePointManager.Expect100Continue = true;
                    ServicePointManager.SecurityProtocol = SecurityProtocolType.Tls
                                                       | (SecurityProtocolType)3072
                                                       | SecurityProtocolType.Ssl3;
                }

                return new LinkNextSoapClient(binding, endPointAddress);
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE DURANTE LA CREAZIONE DEL WEB SERVICE PEC, {0}", ex.Message), ex);
            }
        }

        public RiceviEsitoTransazioneResponse GetEsitoTransazione(RiceviEsitoTransazioneRequest request)
        {
            try
            {
                using (var ws = this.CreaWebService())
                {
                    var loginResponse = ws.Login(this._intestazione, this._loginRequest);
                    this._intestazione.TokenAuth = loginResponse.TokenAuth;

                    var response = ws.RiceviEsitoTransazione(this._intestazione, request);
                    if (response.Esito != Constants.OK)
                    {
                        throw new Exception($"Errore generato dall'esito della transazione, {response.Esito}-{response.Descrizione}");
                    }

                    return response;
                }
            }
            catch (Exception)
            {
                throw;
            }
        }

        public InserisciPosizioniInAttesaResponse GeneraUrlRedirect(InserisciPosizioniInAttesaRequest request)
        {
            try
            {
                using (var ws = this.CreaWebService())
                {
                    this._log.Info($"Chiamata a Login: Identificativo: {this._loginRequest.Identificativo}, Username: {this._loginRequest.Username}, Password: {this._loginRequest.PasswordMD5}");
                    var loginResponse = ws.Login(this._intestazione, this._loginRequest);
                    this._log.Info($"Esito: {loginResponse.Esito}, Descrizione: {loginResponse.Descrizione}");
                    this._log.Info($"Token: {loginResponse.TokenAuth}");

                    this._intestazione.TokenAuth = loginResponse.TokenAuth;

                    string xml = Serializer.SerializeToXmlString(request);

                    this._log.Info($"Chiamata a InserisciPosizioniInAttesa, request: {xml}");
                    var response = ws.InserisciPosizioniInAttesa(this._intestazione, request);

                    if (response.Esito != Constants.OK)
                    {
                        this._log.Error($"Errore generato dall'inserimento della posizione in attesa, {response.Esito}-{response.Descrizione}");
                        throw new Exception($"Errore generato dall'inserimento della posizione in attesa, {response.Esito}-{response.Descrizione}");
                    }

                    this._log.Info($"Chiamata a InserisciPosizioniInAttesa avvnuta con successo, url: {response.Url}");

                    return response;
                }
            }
            catch (Exception)
            {
                throw;
            }
        }

        public ScaricaPagamentiRTPosizioniDebitorieResponse GetRicevutaPagamento(ScaricaPagamentiRTPosizioniDebitorieRequest request)
        {
            try
            {
                using (var ws = this.CreaWebService())
                {
                    var loginResponse = ws.Login(this._intestazione, this._loginRequest);
                    this._intestazione.TokenAuth = loginResponse.TokenAuth;

                    var response = ws.ScaricaPagamentiRTPosizioniDebitorie(this._intestazione, request);

                    if (response.Esito != Constants.OK)
                    {
                        throw new Exception($"Errore generato dall'inserimento della posizione in attesa, {response.Esito}-{response.Descrizione}");
                    }

                    return response;
                }
            }
            catch (Exception)
            {
                throw;
            }
        }

        public VerificaPosizioneResponse VerificaPosizione(VerificaPosizioneRequest request)
        {
            try
            {
                using (var ws = this.CreaWebService())
                {
                    var loginResponse = ws.Login(this._intestazione, this._loginRequest);
                    this._intestazione.TokenAuth = loginResponse.TokenAuth;

                    var response = ws.VerificaPosizione(this._intestazione, request);

                    if (response.Esito != Constants.OK)
                    {
                        throw new Exception($"Errore generato dall'inserimento della posizione in attesa, {response.Esito}-{response.Descrizione}");
                    }

                    return response;
                }
            }
            catch (Exception)
            {
                throw;
            }
        }

    }
}
