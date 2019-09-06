using System;
using System.IO;
using Init.SIGePro.Data;
using PersonalLib2.Data;

namespace Init.SIGePro.Verticalizzazioni
{
    public class VerticalizzazioneWssnagrafeParma : Verticalizzazione
    {
        private const string NOME_VERTICALIZZAZIONE = "WSANAGRAFE_PARMA";

        public VerticalizzazioneWssnagrafeParma()
        {

        }

        public VerticalizzazioneWssnagrafeParma(string idComuneAlias, string software) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software) { }

        public string UrlWs_Base
        {
            get { return GetString("URLWS_BASE"); }
            set { SetString("URLWS_BASE", value); }
        }

        public string Username
        {
            get { return GetString("USERNAME"); }
            set { SetString("USERNAME", value); }
        }

        public string Password
        {
            get { return GetString("PASSWORD"); }
            set { SetString("PASSWORD", value); }
        }
    }
}





