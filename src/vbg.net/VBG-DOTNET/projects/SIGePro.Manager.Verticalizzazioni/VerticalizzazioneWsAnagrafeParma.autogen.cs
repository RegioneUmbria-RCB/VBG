using SIGePro.Manager.VerticalizzazioniBase;

namespace SIGePro.Manager.Verticalizzazioni
{
    public class VerticalizzazioneWssnagrafeParma : Verticalizzazione
    {
        private const string NOME_VERTICALIZZAZIONE = "WSANAGRAFE_PARMA";

        public override string NomeVerticalizzazione => NOME_VERTICALIZZAZIONE;

        public VerticalizzazioneWssnagrafeParma()
        {

        }

        public VerticalizzazioneWssnagrafeParma(string idComuneAlias, string software) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software) { }

        public string UrlWs_Base
        {
            get { return this.GetString("URLWS_BASE"); }
            set { this.SetString("URLWS_BASE", value); }
        }

        public string Username
        {
            get { return this.GetString("USERNAME"); }
            set { this.SetString("USERNAME", value); }
        }

        public string Password
        {
            get { return this.GetString("PASSWORD"); }
            set { this.SetString("PASSWORD", value); }
        }
    }
}





