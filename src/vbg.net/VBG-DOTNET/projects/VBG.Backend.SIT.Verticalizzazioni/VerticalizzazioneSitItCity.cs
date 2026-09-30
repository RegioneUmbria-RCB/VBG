
using SIGePro.Manager.VerticalizzazioniBase;
using System;
using System.Collections.Generic;
using System.Text;

namespace VBG.Backend.SIT.Verticalizzazioni
{
    public class VerticalizzazioneSitItCity : Verticalizzazione
    {
        public static class Constants
        {
            public const string NomeVerticalizzazione = "SIT_ITCITY";
            public const string UrlServizioCivici = "URL_SERVIZIO_CIVICI";
            public const string Username = "USERNAME";
            public const string Password = "PASSWORD";
        }

        public override string NomeVerticalizzazione => Constants.NomeVerticalizzazione;

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
            get { return this.GetString(Constants.UrlServizioCivici); }
            set { this.SetString(Constants.UrlServizioCivici, value); }
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
    }
}
