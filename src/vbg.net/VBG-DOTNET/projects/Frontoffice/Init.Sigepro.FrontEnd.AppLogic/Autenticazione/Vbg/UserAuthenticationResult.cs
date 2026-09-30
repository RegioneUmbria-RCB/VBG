using Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche;
using System;


namespace Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg
{
    public class UserAuthenticationResult
    {
        public string Token { get; set; }

        public string Alias { get; set; }

        public string IdComuneDb { get; set; }

        public AnagraficaUtente DatiUtente { get; set; }

        public LivelloAutenticazioneEnum LivelloAutenticazione { get; set; }

        // Usato da pagine .net per fvg
        public UserAuthenticationResult() { }

        public static UserAuthenticationResult Anonimo(string token, string alias, string idComuneDb) => new UserAuthenticationResult(token, alias, idComuneDb, null, LivelloAutenticazioneEnum.Anonimo);

        internal UserAuthenticationResult(string token, string alias, string idComuneDb, AnagraficaUtente datiUtente, LivelloAutenticazioneEnum livelloAutenticazione)
        {
            this.Token = token;
            this.Alias = alias;
            this.IdComuneDb = idComuneDb;
            this.DatiUtente = datiUtente;
            this.LivelloAutenticazione = livelloAutenticazione;
        }

        public static UserAuthenticationResult CreateFake(string alias)
        {
            return new UserAuthenticationResult(Guid.NewGuid().ToString(), alias, alias, new AnagraficaUtente
            {
                Nome = "Utente",
                Nominativo = "Test",
                Codicefiscale = "XXXXXXXXXXXXXXXX"
            },
            LivelloAutenticazioneEnum.Identificato);
        }

        public string FullUserName()
        {
            return $"{this.DatiUtente?.Nome} {this.DatiUtente?.Nominativo}";
        }
    }
}
