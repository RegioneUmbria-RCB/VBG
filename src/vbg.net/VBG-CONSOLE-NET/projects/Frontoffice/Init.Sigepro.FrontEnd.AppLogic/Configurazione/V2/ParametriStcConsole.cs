namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2
{
    public class ParametriStcConsole : IParametriConfigurazione
    {

        public class RiferimentiSportello
        {
            public int IdNodo { get; set; }
            public string IdEnte { get; set; }
            public string IdSportello { get; set; }

        }

        public string UrlStc { get; set; }
        public string Username { get; set; }
        public string Password { get; set; }
        public RiferimentiSportello SportelloMittente { get; set; }
        public RiferimentiSportello SportelloDestinatario { get; set; }


        public ParametriStcConsole(string urlStc, string username, string password, RiferimentiSportello sportelloMittente, RiferimentiSportello sportelloDestinatario)
        {
            this.UrlStc = urlStc;
            this.Username = username;
            this.Password = password;

            this.SportelloMittente = sportelloMittente;
            this.SportelloDestinatario = sportelloDestinatario;
        }

    }
}
