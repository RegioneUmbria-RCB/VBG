// -----------------------------------------------------------------------
// <copyright file="ConfigurazioneValidazioneReader.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneBandiUmbria
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using System.IO;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class ConfigurazioneValidazioneReader : IConfigurazioneValidazioneReader
	{
		private static class Constants
		{
			public const string PathFileConfigurazione = "~/configurazioneBandi.xml";
		}

		public ConfigurazioneValidazione Read()
		{
			var configurazione = ConfigurazioneValidazione.Load(Constants.PathFileConfigurazione);

			if (configurazione == null)
			{
				throw new FileNotFoundException(String.Format("Impossibile trovare il file {0}", Constants.PathFileConfigurazione));
			}

			return configurazione;
		}
	}
}
