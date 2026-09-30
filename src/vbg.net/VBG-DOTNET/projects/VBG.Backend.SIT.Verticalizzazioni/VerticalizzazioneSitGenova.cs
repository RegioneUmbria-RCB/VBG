using SIGePro.Manager.VerticalizzazioniBase;

namespace VBG.Backend.SIT.Verticalizzazioni
{
    public class VerticalizzazioneSitGenova : Verticalizzazione
    {
        public static class Constants
        {
            public const string NomeVerticalizzazione = "SIT_GENOVA";
            public const string UrlWsToponomastica = "URL_WS_TOPONOMASTICA";

            public const string Wso2BaseUrl = "WSO2_BASE_URL";
            public const string Wso2Key = "WSO2_KEY";
            public const string Wso2Secret = "WSO2_SECRET";
        }

        public override string NomeVerticalizzazione => Constants.NomeVerticalizzazione;

        public VerticalizzazioneSitGenova()
            : base()
        {
        }

        public VerticalizzazioneSitGenova(bool attiva)
            : base()
        {
            base.Attiva = attiva;
        }

        public VerticalizzazioneSitGenova(string idComuneAlias, string software)
            : base(idComuneAlias, Constants.NomeVerticalizzazione, software)
        {
        }

        public string UrlWsToponomastica => this.GetString(Constants.UrlWsToponomastica);
        public string Wso2Key => this.GetString(Constants.Wso2Key);
        public string Wso2Secret => this.GetString(Constants.Wso2Secret);
        public string Wso2BaseUrl => this.GetString(Constants.Wso2BaseUrl);
    }
}
