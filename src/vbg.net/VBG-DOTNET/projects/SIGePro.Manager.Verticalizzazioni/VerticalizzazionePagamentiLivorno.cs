

using SIGePro.Manager.VerticalizzazioniBase;

namespace SIGePro.Manager.Verticalizzazioni
{
    public class VerticalizzazionePagamentiLivorno : Verticalizzazione
    {
        private static class Constants
        {
            public const string NomeVerticalizzazione = "PAGAMENTI_LIVORNO";

            public const string WsUrl = "WS_URL";
            public const string IdCampoDinamico = "ID_CAMPO_DINAMICO";
        }

        public override string NomeVerticalizzazione => Constants.NomeVerticalizzazione;

        public VerticalizzazionePagamentiLivorno()
        {
        }
        public VerticalizzazionePagamentiLivorno(string alias, string software)
            : base(alias, Constants.NomeVerticalizzazione, software)
        {
        }

        public string WsUrl
        {
            get { return this.GetString(Constants.WsUrl); }
        }

        public int? IdCampoDinamico
        {
            get { return this.GetInt(Constants.IdCampoDinamico); }
        }
    }
}
