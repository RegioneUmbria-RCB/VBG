using Init.SIGePro.Verticalizzazioni;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Verticalizzazioni
{
    public class VerticalizzazioneSitItCity: Verticalizzazione
    {
        public static class Constants
        {
            public const string NomeVerticalizzazione = "SIT_ITCITY";
            public const string UrlServizioCivici = "URL_SERVIZIO_CIVICI";
            public const string Username = "USERNAME";
            public const string Password = "PASSWORD";
        }

        public VerticalizzazioneSitItCity()
    : base()
        {
        }

        public VerticalizzazioneSitItCity(bool attiva)
    : base()
        {
            base.Attiva = attiva;
        }

        public VerticalizzazioneSitItCity(string idComuneAlias, string software)
    : base(idComuneAlias, Constants.NomeVerticalizzazione, software)
        {
        }

        public string UrlServizioCivici
        {
            get { return GetString(Constants.UrlServizioCivici); }
            set { SetString(Constants.UrlServizioCivici, value); }
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
    }
}
