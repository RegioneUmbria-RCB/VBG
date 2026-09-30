using SIGePro.Manager.VerticalizzazioniBase;

namespace VBG.Backend.SIT.Verticalizzazioni
{
    public partial class VerticalizzazioneSitBari : Verticalizzazione
    {
        private static class Constants
        {
            public static string NomeVerticalizzazione = "SIT_BARI";
            public static string AttributoCodEnte = "COD_ENTE";
            public static string AttributoRequestFrom = "REQUEST_FROM";
            public static string AttributoTipoIndirizzoCercato = "TIPO_INDIRIZZO_CERCATO";
            public static string AttributoWsUrl = "WS_URL_VALIDAZIONE_CIVICO";
        }

        public override string NomeVerticalizzazione => Constants.NomeVerticalizzazione;
        public VerticalizzazioneSitBari(string idComune, string software) : base(idComune, Constants.NomeVerticalizzazione, software)
        {


        }

        public string CodEnte
        {
            get { return this.GetString(Constants.AttributoCodEnte); }
            set { this.SetString(Constants.AttributoCodEnte, value); }
        }


        public string RequestFrom
        {
            get { return this.GetString(Constants.AttributoRequestFrom); }
            set { this.SetString(Constants.AttributoRequestFrom, value); }
        }

        public string TipoIndirizzoCercato
        {
            get { return this.GetString(Constants.AttributoTipoIndirizzoCercato); }
            set { this.SetString(Constants.AttributoTipoIndirizzoCercato, value); }
        }

        public string WsUrlValidazioneCivico
        {
            get { return this.GetString(Constants.AttributoWsUrl); }
            set { this.SetString(Constants.AttributoWsUrl, value); }
        }
    }
}
