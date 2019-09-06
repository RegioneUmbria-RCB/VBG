// -----------------------------------------------------------------------
// <copyright file="DatiCatastaliDenunciaTares.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Bari.DenunceTARES.DTOs
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.Bari.Core.SharedDTOs;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class DatiCatastaliDenunciaTares : DatiCatastaliDto
	{
		public new static DatiCatastaliDenunciaTares Vuoto()
		{
			return new DatiCatastaliDenunciaTares(String.Empty, String.Empty, String.Empty, String.Empty);
		}

		[Obsolete("Utilizzare solo per la serializzazione")]
		public DatiCatastaliDenunciaTares()
		{

		}

		public DatiCatastaliDenunciaTares(string sezione, string foglio, string particella, string subalterno)
		{
			this.Sezione = sezione;
			this.Foglio = foglio;
			this.Particella = particella;
			this.Subalterno = subalterno;
		}

		public override string ToString()
		{
			return String.Format("Sez: {0}, Foglio: {1}, Particella: {2}, Subalterno: {3}", this.Sezione, this.Foglio, this.Particella, this.Subalterno);
		}
	}
}
