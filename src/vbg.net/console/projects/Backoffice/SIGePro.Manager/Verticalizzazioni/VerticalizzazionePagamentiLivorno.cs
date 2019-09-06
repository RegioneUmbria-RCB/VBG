using Init.SIGePro.Verticalizzazioni;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Verticalizzazioni
{
    public class VerticalizzazionePagamentiLivorno : Verticalizzazione
    {
        private static class Constants
        {
            public const string NomeVerticalizzazione = "PAGAMENTI_LIVORNO";

            public const string WsUrl = "WS_URL";
            public const string IdCampoDinamico = "ID_CAMPO_DINAMICO";
        }

        public VerticalizzazionePagamentiLivorno(string alias, string software)
            :base(alias, Constants.NomeVerticalizzazione, software)
        {
        }

        public string WsUrl
        {
            get { return GetString(Constants.WsUrl); }
        }

        public int? IdCampoDinamico
        {
            get { return GetInt(Constants.IdCampoDinamico); }
        }
    }
}
