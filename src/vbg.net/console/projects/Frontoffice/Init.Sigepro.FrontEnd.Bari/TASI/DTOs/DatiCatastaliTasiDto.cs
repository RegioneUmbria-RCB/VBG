// -----------------------------------------------------------------------
// <copyright file="DatiCatastaliTasiDto.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Bari.TASI.DTOs
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.Bari.Core.SharedDTOs;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class DatiCatastaliTasiDto : DatiCatastaliDto
	{
		public string CategoriaCatastale { get; set; }

		public static DatiCatastaliTasiDto Vuoto()
		{
			return new DatiCatastaliTasiDto(String.Empty, String.Empty, String.Empty, String.Empty, String.Empty);
		}

		[Obsolete("Utilizzare solo per la serializzazione")]
		public DatiCatastaliTasiDto()
		{

		}

		public DatiCatastaliTasiDto(string sezione, string foglio, string particella, string subalterno, string categoriaCatastale)
		{
			this.Sezione = sezione;
			this.Foglio = foglio;
			this.Particella = particella;
			this.Subalterno = subalterno;
			this.CategoriaCatastale = categoriaCatastale;
		}

		public override string ToString()
		{
			return String.Format("Sez: {0}, Foglio: {1}, Particella: {2}, Subalterno: {3}, Categoria: {4}", this.Sezione, this.Foglio, this.Particella, this.Subalterno, this.CategoriaCatastale);
		}
	}
}
