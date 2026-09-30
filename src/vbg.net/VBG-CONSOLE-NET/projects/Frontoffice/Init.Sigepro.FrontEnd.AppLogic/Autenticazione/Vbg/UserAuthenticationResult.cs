using Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche;


namespace Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg
{
	public class UserAuthenticationResult
	{
		public string Token{get;private set;}

		public string IdComune { get; private set; }

		public AnagraficaUtente DatiUtente { get; private set; }

        public LivelloAutenticazioneEnum LivelloAutenticazione { get; private set; }

		internal UserAuthenticationResult(string token, string idComune, AnagraficaUtente datiUtente, LivelloAutenticazioneEnum livelloAutenticazione)
		{
			this.Token = token;
			this.IdComune = idComune;
			this.DatiUtente = datiUtente;
            this.LivelloAutenticazione = livelloAutenticazione;
		}
	}
}
