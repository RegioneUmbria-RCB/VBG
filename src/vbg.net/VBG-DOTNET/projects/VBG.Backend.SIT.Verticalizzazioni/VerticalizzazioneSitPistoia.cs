
using SIGePro.Manager.VerticalizzazioniBase;
using System;
using System.Collections.Generic;
using System.Text;

namespace VBG.Backend.SIT.Verticalizzazioni
{
    public class VerticalizzazioneSitPistoia : Verticalizzazione
    {
        public static class Constants
        {
            public const string NomeVerticalizzazione = "SIT_PISTOIA";
            public const string UrlCartografiaDaCivico = "URL_CARTOGRAFIA_DA_CIVICO";
            public const string UrlCartografiaDaMappale = "URL_CARTOGRAFIA_DA_MAPPALE";
        }

        public override string NomeVerticalizzazione => Constants.NomeVerticalizzazione;

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
            get { return this.GetString(Constants.UrlCartografiaDaCivico); }
            set { this.SetString(Constants.UrlCartografiaDaCivico, value); }
        }

        public string UrlCartografiaDaMappale
        {
            get { return this.GetString(Constants.UrlCartografiaDaMappale); }
            set { this.SetString(Constants.UrlCartografiaDaMappale, value); }
        }
    }
}
