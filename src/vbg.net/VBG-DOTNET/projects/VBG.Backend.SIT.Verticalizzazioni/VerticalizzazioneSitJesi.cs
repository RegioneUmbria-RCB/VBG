
using SIGePro.Manager.VerticalizzazioniBase;
using System;
using System.Collections.Generic;
using System.Text;

namespace VBG.Backend.SIT.Verticalizzazioni
{
    public class VerticalizzazioneSitJesi : Verticalizzazione
    {
        private static class Constants
        {
            public const string NomeVerticalizzazione = "SIT_JESI";

            public const string UrlPuntoDaIndirizzo = "URL_PUNTO_DA_INDIRIZZO";
            public const string UrlWsBase = "URL_WS_BASE";
            public const string Username = "USERNAME";
            public const string Password = "PASSWORD";
        }

        public override string NomeVerticalizzazione => Constants.NomeVerticalizzazione;
        public string PasswordParameterName => Constants.Password;


        public VerticalizzazioneSitJesi()
        {

        }

        public VerticalizzazioneSitJesi(string idComuneAlias, string software) : base(idComuneAlias, Constants.NomeVerticalizzazione, software)
        {

        }


        /// <summary>
        /// Indicare la url base relativamente al servizio rest in ascolto per quanto concerne il Comune di Jesi.
        /// </summary>
        public string UrlWsBase => this.GetString(Constants.UrlWsBase);

        /// <summary>
        /// Indicare lo username che viene fornito dal fornitore del SIT
        /// </summary>
        public string Username => this.GetString(Constants.Username);

        /// <summary>
        /// Indicare la password direttamente cripata in SHA1 che viene fornita dal fornitore del SIT, tale parametro servirà per tutte le chiamate che verranno fatte verso il web service
        /// </summary>
        public string Password => this.GetString(Constants.Password);

        /// <summary>
        /// Restituisce l'url per aprire la cartografia dall'area riservata
        /// </summary>
        public string UrlPuntoDaIndirizzo => this.GetString(Constants.UrlPuntoDaIndirizzo);

    }
}
