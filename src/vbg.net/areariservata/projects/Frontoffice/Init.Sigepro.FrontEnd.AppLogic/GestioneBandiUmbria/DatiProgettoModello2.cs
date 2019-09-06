// -----------------------------------------------------------------------
// <copyright file="DatiProgettoModello2.cs" company="">
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
	public class DatiProgettoModello2
	{
		public DatiProgettoModello2(DatiPdfCompilabile datiModello2, ConfigurazioneValidazione.ImpostazioniModello2 configurazione)
		{
			this.TitoloProgetto = datiModello2.Valore(configurazione.TitoloProgetto);
			this.Acronimo = datiModello2.Valore(configurazione.Acronimo);
			this.TipologiaAggregazione = datiModello2.Valore(configurazione.TipologiaAggregazione);
			this.DenominazioneCapofila = datiModello2.Valore(configurazione.DenominazioneCapofila);
			this.DurataIniziativa = datiModello2.Valore(configurazione.DurataIniziativa);
		}

		public string TitoloProgetto { get; private set; }
		public string Acronimo { get; private set; }
		public string TipologiaAggregazione { get; private set; }
		public string DenominazioneCapofila { get; private set; }
		public string DurataIniziativa { get; private set; }
	}
}
