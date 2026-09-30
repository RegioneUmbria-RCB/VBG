using SIGePro.Manager.VerticalizzazioniBase;

namespace SIGePro.Manager.Verticalizzazioni
{
    public class VerticalizzaizoneScrivaniaEntiTerzi : Verticalizzazione
    {
        private static class Constants
        {
            public const string NomeVerticalizzazione = "SCRIVANIA_ENTI_TERZI";

            public const string SoftwareAttivazione = "SOFTWARE_ATTIVAZIONE";
        }

        public override string NomeVerticalizzazione => Constants.NomeVerticalizzazione;

        public VerticalizzaizoneScrivaniaEntiTerzi() { }

        public VerticalizzaizoneScrivaniaEntiTerzi(string idComuneAlias, string software) : base(idComuneAlias, Constants.NomeVerticalizzazione, software) { }

        public string SoftwareAttivazione => this.GetString(Constants.SoftwareAttivazione);
    }
}
