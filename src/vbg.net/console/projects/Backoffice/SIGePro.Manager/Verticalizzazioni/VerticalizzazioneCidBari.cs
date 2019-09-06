// -----------------------------------------------------------------------
// <copyright file="VerticalizzazioneCidBari.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.SIGePro.Manager.Verticalizzazioni
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.SIGePro.Verticalizzazioni;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class VerticalizzazioneCidBari : Verticalizzazione
	{
		private static class Constants
		{
			public static string NomeVerticalizzazione = "CID_BARI";
			public static string UrlServizio = "URL_SERVIZIO";
			public static string IdentificativoUtente = "IDENTIFICATIVO_UTENTE";
			public static string Password = "PASSWORD";
		}

        public VerticalizzazioneCidBari(string aliasComune, string software) : base(aliasComune, Constants.NomeVerticalizzazione, software) { }

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
	}
}
