using Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using log4net;
using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Linq;
using System.Net;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneVbgConsole.Authentication
{
    public class VbgCrossLoginClient : IVbgCrossLoginClient
    {
        private ILog _log = LogManager.GetLogger(typeof(VbgCrossLoginClient));

        public string GetCrossLoginToken(string url, AnagraficaUtente datiUtente)
        {
            using (var wc = new WebClient())
            {
                try
                {
                    this._log.Debug($"Chiamata al web service di cross login all'url {url}. Parametri: nome={datiUtente.Nome}, cognome={datiUtente.Nominativo}, codiceFiscale={datiUtente.Codicefiscale}, sesso={datiUtente.Sesso}");

                    var responsebytes = wc.UploadValues(url, "POST", new NameValueCollection
                    {
                        { "Nome", datiUtente.Nome},
                        { "Cognome", datiUtente.Nominativo},
                        { "CodiceFiscale", datiUtente.Codicefiscale},
                        { "Sesso", datiUtente.Sesso},
                        { "authlevel", "1"}
                    });

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
                    this._log.Error($"Chiamata al web service di cross login all'url {url}. Parametri: nome={datiUtente.Nome}, cognome={datiUtente.Nominativo}, codiceFiscale={datiUtente.Codicefiscale}, sesso={datiUtente.Sesso} fallita. Dettagli dell'errore: {ex.ToString()}");

                    throw;
                }

            }
        }
    }
}
