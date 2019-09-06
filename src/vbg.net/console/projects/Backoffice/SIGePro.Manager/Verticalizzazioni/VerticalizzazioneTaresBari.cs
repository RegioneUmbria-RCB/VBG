using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using PersonalLib2.Data;

namespace Init.SIGePro.Verticalizzazioni
{
	// TARES_BARI
	public partial class VerticalizzazioneTaresBari : Verticalizzazione
	{
		private static class Constants
		{
			public static string NomeVerticalizzazione = "TARES_BARI";
			public static string UrlServizio = "URL_SERVIZIO";
			public static string IdentificativoUtente = "IDENTIFICATIVO_UTENTE";
			public static string Password = "PASSWORD";
			public static string IdRichiesta = "ID_RICHIESTA";
			public static string IdServizio = "ID_SERVIZIO";
			public static string IndirizzoPec = "INDIRIZZO_PEC";
			public static string UrlServizioAgevolazioniTasi = "URL_SERVIZIO_AGEV_TASI";
			public static string UrlServizioAgevolazioniImu = "URL_SERVIZIO_AGEV_IMU";
            public static string CfCafFittizio = "CF_CAF_FITTIZIO";
            public static string EmailCafFittizio = "EMAIL_CAF_FITTIZIO";
			public static string UrlServizioFirmaCid = "URL_SERVIZIO_FIRMA_CID";
		}
		
		public VerticalizzazioneTaresBari(string idComune, string software) : base(idComune, Constants.NomeVerticalizzazione, software) { }

		public string UrlServizio
		{
			get{ return GetString( Constants.UrlServizio ); }
			set { SetString(Constants.UrlServizio, value); }
		}


		public string IdentificativoUtente
		{
			get{ return GetString( Constants.IdentificativoUtente ); }
			set { SetString(Constants.IdentificativoUtente, value); }
		}


		public string Password
		{
			get{ return GetString( Constants.Password ); }
			set { SetString(Constants.Password, value); }
		}


		public string IndirizzoPec
		{
			get{ return GetString( Constants.IndirizzoPec ); }
			set { SetString(Constants.IndirizzoPec, value); }
		}

		public string UrlServizioAgevolazioneTasi
		{
			get { return GetString(Constants.UrlServizioAgevolazioniTasi); }
			set { SetString(Constants.UrlServizioAgevolazioniTasi, value); }
		}

		public string UrlServizioAgevolazioneImu
		{
			get { return GetString(Constants.UrlServizioAgevolazioniImu); }
			set { SetString(Constants.UrlServizioAgevolazioniImu, value); }
		}

        public string CodiceFiscaleCafFittizio
        {
            get { return GetString(Constants.CfCafFittizio); }
            set { SetString(Constants.CfCafFittizio, value); }
        }

        public string EmailCafFittizio
        {
            get { return GetString(Constants.EmailCafFittizio); }
            set { SetString(Constants.EmailCafFittizio, value); }
        }

		public string UrlServizioFirmaCid
		{
			get { return GetString(Constants.UrlServizioFirmaCid); }
			set { SetString(Constants.UrlServizioFirmaCid, value); }
		}
	}
}
