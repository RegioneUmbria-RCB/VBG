// -----------------------------------------------------------------------
// <copyright file="SitFeatures.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.SIGePro.Sit.Manager
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.SIGePro.Manager.DTO;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class SitFeatures
	{
		public enum TipoVisualizzazione
		{
			PuntoDaIndirizzo,
			PuntoDaMappale
		}

		public BaseDto<TipoVisualizzazione, string>[] VisualizzazioniFrontoffice { get; set; }
		public BaseDto<TipoVisualizzazione, string>[] VisualizzazioniBackoffice { get; set; }
		public string[] CampiGestiti { get; set; }
	}
}
