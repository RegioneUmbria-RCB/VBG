using SIGePro.Manager.VerticalizzazioniBase;
using System;

namespace SIGePro.Manager.Verticalizzazioni
{
    public class VerticalizzazioneCosapParma : Verticalizzazione
    {
        public static class Constants
        {
            public const string NomeVerticalizzazione = "COSAP_PARMA";
            public const string WebServiceUrl = "WEB_SERVICE_URL";
            public const string UtenteInserimento = "UTENTE_INSERIMENTO";
            public const string Fonte = "FONTE";
            public const string Anno = "ANNO";
            public const string User = "WS_USER";
            public const string Password = "WS_PASSWORD";

        }

        public override string NomeVerticalizzazione => Constants.NomeVerticalizzazione;

        public VerticalizzazioneCosapParma()
        {
        }

        public VerticalizzazioneCosapParma(string idComuneAlias, string software) : base(idComuneAlias, Constants.NomeVerticalizzazione, software) { }

        public string WebServiceUrl => this.GetString(Constants.WebServiceUrl);
        public string UtenteInserimento => this.GetString(Constants.UtenteInserimento);
        public string Fonte => this.GetString(Constants.Fonte);
        public string User => this.GetString(Constants.User);
        public string Password => this.GetString(Constants.Password);
        public int Anno => this.GetInt(Constants.Anno).GetValueOrDefault(DateTime.Now.Year);
    }
}
