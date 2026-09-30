using SIGePro.Manager.VerticalizzazioniBase;

namespace Init.SIGePro.Manager.Logic.GestioneIntegrazioneLDP.Verticalizzazioni
{
    public class VerticalizzazioneSitLdp : Verticalizzazione
    {
        public static class Constants
        {
            public const string NomeVerticalizzazione = "SIT_LDP";
            public const string UrlServizioCivici = "URL_SERVIZIO_CIVICI";
            public const string UrlServizioCatasto = "URL_SERVIZIO_CATASTO";
            public const string UrlServizioDomande = "URL_SERVIZIO_DOMANDE";
            public const string UrlGenerazionePdfDomanda = "URL_GENERAZIONE_PDF_DOMANDA";
            public const string Username = "USERNAME";
            public const string Password = "PASSWORD";
            public const string UrlPresentazioneDomanda = "URL_PRESENTAZIONE_DOMANDA";
            public const string AbilitaPassiCarrai = "ABILITA_PASSICARRAI";
            public const string UrlRitornoPraticaGIS = "URL_RITORNO_PRATICA_GIS";
            public const string UrlPresentazioneIntegrazione = "URL_PRESENTAZIONE_INTEGRAZIONE";
        }

        public override string NomeVerticalizzazione => Constants.NomeVerticalizzazione;

        public VerticalizzazioneSitLdp()
            : base()
        {
        }

        public VerticalizzazioneSitLdp(bool attiva)
            : base()
        {
            base.Attiva = attiva;
        }

        public VerticalizzazioneSitLdp(string alias, string software)
            : base(alias, Constants.NomeVerticalizzazione, software)
        {
        }

        public string UrlServizioCivici
        {
            get { return this.GetString(Constants.UrlServizioCivici); }
            set { this.SetString(Constants.UrlServizioCivici, value); }
        }

        public string UrlServizioCatasto
        {
            get { return this.GetString(Constants.UrlServizioCatasto); }
            set { this.SetString(Constants.UrlServizioCatasto, value); }
        }

        public string UrlServizioDomande
        {
            get { return this.GetString(Constants.UrlServizioDomande); }
            set { this.SetString(Constants.UrlServizioDomande, value); }
        }
        public string Username
        {
            get { return this.GetString(Constants.Username); }
            set { this.SetString(Constants.Username, value); }
        }

        public string Password
        {
            get { return this.GetString(Constants.Password); }
            set { this.SetString(Constants.Password, value); }
        }

        public string UrlPresentazioneDomanda
        {
            get { return this.GetString(Constants.UrlPresentazioneDomanda); }
            set { this.SetString(Constants.UrlPresentazioneDomanda, value); }
        }

        public string UrlRitornoPraticaGIS
        {
            get { return this.GetString(Constants.UrlRitornoPraticaGIS); }
            set { this.SetString(Constants.UrlRitornoPraticaGIS, value); }
        }

        public string UrlPresentazioneIntegrazione
        {
            get { return this.GetString(Constants.UrlPresentazioneIntegrazione); }
            set { this.SetString(Constants.UrlPresentazioneIntegrazione, value); }
        }

        public string UrlGenerazionePdfDomanda
        {
            get { return this.GetString(Constants.UrlGenerazionePdfDomanda); }
            set { this.SetString(Constants.UrlGenerazionePdfDomanda, value); }
        }

        public string AbilitaPassiCarrai
        {
            get { return this.GetString(Constants.AbilitaPassiCarrai); }
            set { this.SetString(Constants.AbilitaPassiCarrai, value); }
        }
    }
}
