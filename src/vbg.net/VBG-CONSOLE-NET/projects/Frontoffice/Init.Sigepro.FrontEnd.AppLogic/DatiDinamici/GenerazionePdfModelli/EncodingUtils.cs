// -----------------------------------------------------------------------
// <copyright file="EncodingUtils.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.GenerazionePdfModelli
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using System.Configuration;
	using log4net;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class EncodingUtils
	{
		private static class Constants
		{
			public const string SettingsKey = "RiepiloghiSchedeDinamiche.Encoding";
			public static readonly  Encoding DefaultEncoding = Encoding.Default;
		}

		public static Encoding GetEncoding()
		{
			var log = LogManager.GetLogger(typeof(EncodingUtils));

			var settings = ConfigurationManager.AppSettings[Constants.SettingsKey];

			if (String.IsNullOrEmpty(settings))
			{
				log.InfoFormat("La conversione dell'html del riepilogo della scheda dinamica verrà effettuata con il charset {0} (default)", Constants.DefaultEncoding.WebName);
				return Constants.DefaultEncoding;
			}

			log.InfoFormat("La conversione dell'html del riepilogo della scheda dinamica verrà effettuata con il charset {0}", settings);

			return Encoding.GetEncoding(settings);			
		}
	}
}
