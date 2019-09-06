using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using log4net;
using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Linq;
using System.Net;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Autenticazione.AidaSmart
{
    public class ASCrossLoginClient : IASCrossLoginClient
    {
        private readonly IConfigurazione<ParametriAidaSmart> _config;
        private ILog _log = LogManager.GetLogger(typeof(ASCrossLoginClient));

        public ASCrossLoginClient(IConfigurazione<ParametriAidaSmart> config)
        {
            this._config = config;
        }

        public string GetCrossLginToken(string nome, string cognome, string codiceFiscale, string sesso)
        {
            using (var wc = new WebClient())
            {
                try
                {
                    //http://devel9:8080/ibcauthenticationgateway/crossloginute?Nome=SUPERMAN&Cognome=MEDIOMAN&CodiceFiscale=MDMSPMrcr73h23g888o&Sesso=M&idcomunealias=E256&authlevel=1

                    this._log.Debug($"Chiamata al web service di cross login all'url {this._config.Parametri.CrossLoginUrl}. Parametri: nome={nome}, cognome={cognome}, codiceFiscale={codiceFiscale}, sesso={sesso}");

                    var reqparm = new NameValueCollection();

                    reqparm.Add("Nome", nome);
                    reqparm.Add("Cognome", cognome);
                    reqparm.Add("CodiceFiscale", codiceFiscale);
                    reqparm.Add("Sesso", sesso);
                    reqparm.Add("authlevel", "1");

                    var responsebytes = wc.UploadValues(this._config.Parametri.CrossLoginUrl, "POST", reqparm);

                    var token = Encoding.UTF8.GetString(responsebytes);

                    this._log.Debug($"Chiamata al web service di cross login riuscita, token={token}");

                    if (String.IsNullOrEmpty(token))
                    {
                        throw new ApplicationException("Non è stato possibile leggere un token dal servizio di cross login, il token restituito è vuoto");
                    }

                    return token;
                }
                catch (Exception ex)
                {
                    this._log.Error($"Chiamata al web service di cross login all'url {this._config.Parametri.CrossLoginUrl}. Parametri: nome={nome}, cognome={cognome}, codiceFiscale={codiceFiscale}, sesso={sesso} fallita. Dettagli dell'errore: {ex.ToString()}");

                    throw;
                }

            }
        }
    }
}
