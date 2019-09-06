using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.Pagamenti.EntraNextService;
using Init.Sigepro.FrontEnd.Pagamenti.MIP;
using Init.Sigepro.FrontEnd.Pagamenti.MIP.Client;
using log4net;
using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.Text;

namespace Init.Sigepro.FrontEnd.Pagamenti.ENTRANEXT
{
    public class PayServerClientWrapperEntraNext
    {
        private class Constants
        {
            public const string HTTPS = "https";
            public const string Versione = "1.3";
            public const string OK = "OK";
        }

        ILog _log = LogManager.GetLogger(typeof(PayServerClientWrapperEntraNext));

        PaymentSettingsEntraNext _settings;
        IntestazioneFO _intestazione;
        LoginRequest _loginRequest;

        public PayServerClientWrapperEntraNext(PaymentSettingsEntraNext settings)
        {
            this._settings = settings;
            this._intestazione = new IntestazioneFO
            {
                CodiceFiscaleEnte = _settings.CodiceFiscaleEnte,
                IdentificativoConnettore = _settings.IdentificativoConnettore
            };

            this._loginRequest = new LoginRequest
            {
                Versione = Constants.Versione,
                Identificativo = _settings.Identificativo,
                Username = _settings.Username,
                PasswordMD5 = _settings.Password
            };
        }

        private LinkNextSoapClient CreaWebService()
        {
            try
            {
                var endPointAddress = new EndpointAddress(_settings.UrlWs);
                var binding = new BasicHttpBinding("entraNextServiceBinding");

                if (endPointAddress.Uri.Scheme.ToLower() == Constants.HTTPS)
                {
                    binding.Security = new BasicHttpSecurity { Mode = BasicHttpSecurityMode.Transport };
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
                using (var ws = CreaWebService())
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
                using (var ws = CreaWebService())
                {
                    _log.Info($"Chiamata a Login: Identificativo: {this._loginRequest.Identificativo}, Username: {this._loginRequest.Username}, Password: {this._loginRequest.PasswordMD5}");
                    var loginResponse = ws.Login(this._intestazione, this._loginRequest);
                    _log.Info($"Esito: {loginResponse.Esito}, Descrizione: {loginResponse.Descrizione}");
                    _log.Info($"Token: {loginResponse.TokenAuth}");

                    this._intestazione.TokenAuth = loginResponse.TokenAuth;
                    
                    string xml = Serializer.SerializeToXmlString(request);

                    _log.Info($"Chiamata a InserisciPosizioniInAttesa, request: {xml}");
                    var response = ws.InserisciPosizioniInAttesa(this._intestazione, request);

                    if (response.Esito != Constants.OK)
                    {
                        _log.Error($"Errore generato dall'inserimento della posizione in attesa, {response.Esito}-{response.Descrizione}");
                        throw new Exception($"Errore generato dall'inserimento della posizione in attesa, {response.Esito}-{response.Descrizione}");
                    }

                    _log.Info($"Chiamata a InserisciPosizioniInAttesa avvnuta con successo, url: {response.Url}");

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
                using (var ws = CreaWebService())
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
                using (var ws = CreaWebService())
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
