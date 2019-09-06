using Init.SIGePro.Verticalizzazioni;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Verticalizzazioni
{
    public class VerticalizzazioneSitJesi : Verticalizzazione
    {
        private static class Constants
        {
            public const string NomeVerticalizzazione = "SIT_JESI";
            public const string UrlPuntoDaIndirizzo = "URL_PUNTO_DA_INDIRIZZO";
        }

        
        public VerticalizzazioneSitJesi()
        {

        }

        public VerticalizzazioneSitJesi(string idComuneAlias, string software) : base(idComuneAlias, Constants.NomeVerticalizzazione, software)
        {

        }

        /// <summary>
        /// Indicare la url base relativamente al servizio rest in ascolto per quanto concerne il Comune di Jesi.
        /// </summary>
        public string UrlWsBase
        {
            get { return GetString("URL_WS_BASE"); }
            set { SetString("URL_WS_BASE", value); }
        }

        /// <summary>
        /// Indicare la password direttamente cripata in SHA1 che viene fornita dal fornitore del SIT, tale parametro servirà per tutte le chiamate che verranno fatte verso il web service
        /// </summary>
        public string Password
        {
            get { return GetString("PASSWORD"); }
            set { SetString("PASSWORD", value); }
        }

        /// <summary>
        /// Restituisce l'url per aprire la cartografia dall'area riservata
        /// </summary>
        public string UrlPuntoDaIndirizzo => GetString(Constants.UrlPuntoDaIndirizzo);
        
    }
}
