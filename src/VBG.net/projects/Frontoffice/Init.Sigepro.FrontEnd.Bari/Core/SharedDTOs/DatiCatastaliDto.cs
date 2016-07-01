// -----------------------------------------------------------------------
// <copyright file="DatiCatastaliDTO.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Bari.Core.SharedDTOs
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;

	public class DatiCatastaliDto
	{
		public string Foglio { get; set; }
		public string Particella { get; set; }
		public string Sezione { get; set; }
		public string Subalterno { get; set; }

		public override string ToString()
		{
			return String.Format("Sezione: {0}, Foglio: {1}, Particella: {2}, Sub: {3}", Sezione, Foglio, Particella, Subalterno);
		}

		internal static DatiCatastaliDto Vuoto()
		{
			return new DatiCatastaliDto
			{
				Foglio = String.Empty,
				Particella = String.Empty,
				Sezione = String.Empty,
				Subalterno = String.Empty
			};
		}
	}
}