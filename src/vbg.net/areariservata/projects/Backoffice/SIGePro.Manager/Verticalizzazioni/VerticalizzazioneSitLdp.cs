using Init.SIGePro.Verticalizzazioni;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Verticalizzazioni
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
        }

        public VerticalizzazioneSitLdp()
            : base()
        {
        }

        public VerticalizzazioneSitLdp(bool attiva)
            : base()
        {
            base.Attiva = attiva;
        }

        public VerticalizzazioneSitLdp(string idComuneAlias, string software)
            : base(idComuneAlias, Constants.NomeVerticalizzazione, software)
        {
        }

        public string UrlServizioCivici
        {
            get { return GetString(Constants.UrlServizioCivici); }
            set { SetString(Constants.UrlServizioCivici, value); }
        }

        public string UrlServizioCatasto
        {
            get { return GetString(Constants.UrlServizioCatasto); }
            set { SetString(Constants.UrlServizioCatasto, value); }
        }

        public string UrlServizioDomande
        {
            get { return GetString(Constants.UrlServizioDomande); }
            set { SetString(Constants.UrlServizioDomande, value); }
        }
        public string Username
        {
            get { return GetString(Constants.Username); }
            set { SetString(Constants.Username, value); }
        }

        public string Password
        {
            get { return GetString(Constants.Password); }
            set { SetString(Constants.Password, value); }
        }

        public string UrlPresentazioneDomanda
        {
            get { return GetString(Constants.UrlPresentazioneDomanda); }
            set { SetString(Constants.UrlPresentazioneDomanda, value); }
        }

        public string UrlGenerazionePdfDomanda
        {
            get { return GetString(Constants.UrlGenerazionePdfDomanda); }
            set { SetString(Constants.UrlGenerazionePdfDomanda, value); }
        }

        public string AbilitaPassiCarrai
        {
            get { return GetString(Constants.AbilitaPassiCarrai); }
            set { SetString(Constants.AbilitaPassiCarrai, value); }
        }
    }
}
