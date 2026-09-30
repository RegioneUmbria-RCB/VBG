
using SIGePro.Manager.VerticalizzazioniBase;

namespace SIGePro.Manager.Verticalizzazioni
{
    public partial class VerticalizzazioneGoogleMaps : Verticalizzazione
    {
        private static class Constants
        {
            public const string ApiKey = "API_KEY";
            public const string MapBounds = "MAP_BOUNDS";
        }

        private const string NOME_VERTICALIZZAZIONE = "GOOGLE_MAPS";

        public override string NomeVerticalizzazione => NOME_VERTICALIZZAZIONE;

        public VerticalizzazioneGoogleMaps()
        {

        }

        public VerticalizzazioneGoogleMaps(string idComuneAlias, string software) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software) { }

        public string ApiKey => this.GetString(Constants.ApiKey);
        public string MapBounds => this.GetString(Constants.MapBounds);
    }
}
