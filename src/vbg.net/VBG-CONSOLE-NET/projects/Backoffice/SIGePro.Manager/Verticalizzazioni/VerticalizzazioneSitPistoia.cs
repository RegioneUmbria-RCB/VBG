using Init.SIGePro.Verticalizzazioni;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Verticalizzazioni
{
    public class VerticalizzazioneSitPistoia : Verticalizzazione
    {
        public static class Constants
        {
            public const string NomeVerticalizzazione = "SIT_PISTOIA";
            public const string UrlCartografiaDaCivico = "URL_CARTOGRAFIA_DA_CIVICO";
            public const string UrlCartografiaDaMappale = "URL_CARTOGRAFIA_DA_MAPPALE";
        }


        public VerticalizzazioneSitPistoia()
            : base()
        {
        }

        public VerticalizzazioneSitPistoia(bool attiva)
            : base()
        {
            base.Attiva = attiva;
        }

        public VerticalizzazioneSitPistoia(string idComuneAlias, string software)
            : base(idComuneAlias, Constants.NomeVerticalizzazione, software)
        {
        }

        public string UrlCartografiaDaCivico
        {
            get { return GetString(Constants.UrlCartografiaDaCivico); }
            set { SetString(Constants.UrlCartografiaDaCivico, value); }
        }

        public string UrlCartografiaDaMappale
        {
            get { return GetString(Constants.UrlCartografiaDaMappale); }
            set { SetString(Constants.UrlCartografiaDaMappale, value); }
        }
    }
}
