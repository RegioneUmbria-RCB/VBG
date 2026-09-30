using SIGePro.Manager.VerticalizzazioniBase;

namespace SIGePro.Manager.Verticalizzazioni
{
    public class VerticalizzazioneAreaRiservataSsu : Verticalizzazione
    {
        private static class Constants
        {
            public const string BaseUrlApiCatalogoServizi = "BASE_URL_API_CATALOGO_SERVIZI";
            public const string BaseUrlApiValidator = "BASE_URL_API_VALIDATOR";
            public const string IdEnteDestinatario = "ID_ENTE_DESTINATARIO";
            public const string IdNodoDestinatario = "ID_NODO_DESTINATARIO";
            public const string IdSportelloDestinatario = "ID_SPORTELLO_DESTINATARIO";
        }


        private const string NOME_VERTICALIZZAZIONE = "AREA_RISERVATA_SSU";

        public override string NomeVerticalizzazione => NOME_VERTICALIZZAZIONE;

        public VerticalizzazioneAreaRiservataSsu() { }

        public VerticalizzazioneAreaRiservataSsu(string idComuneAlias, string software) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software) { }

        public string BaseUrlApiCatalogoServizi => this.GetString(Constants.BaseUrlApiCatalogoServizi);
        public string IdEnteDestinatario => this.GetString(Constants.IdEnteDestinatario);
        public string IdNodoDestinatario => this.GetString(Constants.IdNodoDestinatario);
        public string IdSportelloDestinatario => this.GetString(Constants.IdSportelloDestinatario);
        public string BaseUrlApiValidator => this.GetString(Constants.BaseUrlApiValidator);
    }
}
