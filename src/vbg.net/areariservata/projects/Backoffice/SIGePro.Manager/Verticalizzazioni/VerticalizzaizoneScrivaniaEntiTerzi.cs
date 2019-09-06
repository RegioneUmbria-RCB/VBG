using Init.SIGePro.Verticalizzazioni;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Verticalizzazioni
{
    public class VerticalizzaizoneScrivaniaEntiTerzi : Verticalizzazione
    {
        private static class Constants
        {
            public const string NomeVerticalizzazione = "SCRIVANIA_ENTI_TERZI";

            public const string SoftwareAttivazione = "SOFTWARE_ATTIVAZIONE";
        }

        public VerticalizzaizoneScrivaniaEntiTerzi() { }

        public VerticalizzaizoneScrivaniaEntiTerzi(string idComuneAlias, string software) : base(idComuneAlias, Constants.NomeVerticalizzazione, software) { }

        public string SoftwareAttivazione => GetString(Constants.SoftwareAttivazione);
    }
}
