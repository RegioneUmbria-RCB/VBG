using Init.SIGePro.Verticalizzazioni;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Verticalizzazioni
{
    public class VerticalizzazioneLivornoServiziCittadino : Verticalizzazione
    {
        private static class Constants
        {
            public static string UrlWsModulisticaDrupal = "URL_WS_MODULISTICA_DRUPAL";
        }

        private const string NOME_VERTICALIZZAZIONE = "LIVORNO_SERVIZI_CITTADINO";

        public VerticalizzazioneLivornoServiziCittadino()
        {

        }

        public VerticalizzazioneLivornoServiziCittadino(string idComuneAlias, string software) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software) { }

        public string UrlWsModulisticaDrupal
        {
            get { return GetString(Constants.UrlWsModulisticaDrupal); }
            set { SetString(Constants.UrlWsModulisticaDrupal, value); }
        }
    }
}
