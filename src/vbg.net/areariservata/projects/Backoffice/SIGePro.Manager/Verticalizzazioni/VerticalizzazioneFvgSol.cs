using Init.SIGePro.Verticalizzazioni;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Verticalizzazioni
{
    public class VerticalizzazioneFvgSol: Verticalizzazione
    {
        private static class Constants
        {
            public static string WebServiceUrl = "WEB_SERVICE_URL";
            public static string WebServiceUsername = "WEB_SERVICE_USERNAME";
            public static string WebServicePassword = "WEB_SERVICE_PASSWORD";

            public static string CheckboxEtichettaADestra = "D2_CHK_DEF_ETICHETTA_DESTRA";
        }

        private const string NOME_VERTICALIZZAZIONE = "FVG_SOL";

        public VerticalizzazioneFvgSol()
        {

        }

        public VerticalizzazioneFvgSol(string idComuneAlias, string software) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software) { }

        public string WebServiceUrl => GetString(Constants.WebServiceUrl);
        public string WebServiceUsername => GetString(Constants.WebServiceUsername);
        public string WebServicePassword => GetString(Constants.WebServicePassword);
        public bool CheckboxEtichettaADestra => GetInt(Constants.CheckboxEtichettaADestra).GetValueOrDefault(0) == 1;
    }
}
