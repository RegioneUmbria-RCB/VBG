// -----------------------------------------------------------------------
// <copyright file="DatiProgettoReader.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneBandiUmbria
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.AppLogic.PrecompilazionePDF;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class DatiProgettoReader : IDatiProgettoReader
	{
		IConfigurazioneValidazioneReader _configReader;

		public DatiProgettoReader(IConfigurazioneValidazioneReader configReader)
		{
			this._configReader = configReader;
		}

		public DatiProgettoModello2 ReadDatiProgetto(DatiPdfCompilabile datiModello2)
		{
			return new DatiProgettoModello2(datiModello2, this._configReader.Read().Modello2);
		}
	}
}
