using Init.SIGePro.Verticalizzazioni;

namespace Init.SIGePro.Sit.Forli
{
    public class VerticalizzazioneSitForli : Verticalizzazione
    {
        public static class Constants
        {
            public const string NomeVerticalizzazione = "SIT_FORLI";
            public const string ConnectionstringToponomastica = "CONNECTIONSTRING_TOPONOMASTICA";
            public const string ConnectionstringCatasto = "CONNECTIONSTRING_CATASTO";
            public const string UrlCartografiaDaCivico = "URL_CARTOGRAFIA_DA_CIVICO";
            public const string UrlCartografiaDaMappale = "URL_CARTOGRAFIA_DA_MAPPALE";
        }

        public override string NomeVerticalizzazione => Constants.NomeVerticalizzazione;

        public VerticalizzazioneSitForli()
            : base()
        {
        }

        public VerticalizzazioneSitForli(bool attiva)
            : base()
        {
            base.Attiva = attiva;
        }

        public VerticalizzazioneSitForli(string idComuneAlias, string software)
            : base(idComuneAlias, Constants.NomeVerticalizzazione, software)
        {
        }

        public string ConnectionstringToponomastica => this.GetString(Constants.ConnectionstringToponomastica);
        public string ConnectionstringCatasto => this.GetString(Constants.ConnectionstringCatasto);

        public string UrlCartografiaDaCivico => this.GetString(Constants.UrlCartografiaDaCivico);
        public string UrlCartografiaDaMappale => this.GetString(Constants.UrlCartografiaDaMappale);
    }
}
