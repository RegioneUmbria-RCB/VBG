using Init.SIGePro.Verticalizzazioni;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Init.SIGePro.Manager.Verticalizzazioni
{

    public class VerticalizzazioneSitConsole : Verticalizzazione
    {
        private static class Constants
        {
            public const string NomeVerticalizzazione = "SIT_CONSOLE";
            public const string AliasBackendLocale = "ALIAS_BACKEND_LOCALE";
            public const string UrlWsSit = "URL_WSSIT";
            public const string RedirectToSitPage = "REDIRECT_TO_SIT_PAGE";
        }

        public VerticalizzazioneSitConsole()
        {

        }

        public VerticalizzazioneSitConsole(string idComuneAlias, string software) : base(idComuneAlias, Constants.NomeVerticalizzazione, software) { }

        public VerticalizzazioneSitConsole(string idComuneAlias, string software, string codiceComune) : base(idComuneAlias, Constants.NomeVerticalizzazione, software, codiceComune) { }

        /// <summary>
        /// Alias da utilizzare per interrogare il comune locale. Es. E256
        /// </summary>
        public string AliasBackendLocale => GetString(Constants.AliasBackendLocale);

        /// <summary>
        /// URL del servizio SIT per l'installazione aspnet di backend per il comune comunelocale. Es. https://devel3.vbg.community/aspnet/webservices/wssigepro/wssit.asmx
        /// </summary>
        public string UrlWssit => GetString(Constants.UrlWsSit);

        /// <summary>
        /// 'Se impostato a 1 (e se la verticalizzazione è attiva) permette il redirect automatico dallo step GestioniLocalizzazioni allo step GestioniLocalizzazioniSit.'
        /// </summary>
        public bool RedirectToSitPage => GetBool(Constants.RedirectToSitPage);

    }
}
