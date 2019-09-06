using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Data;

namespace Init.SIGePro.Manager.DTO.StradarioComune
{
	public class StradarioDto
	{
		public int CodiceStradario { get; set; }
		public string NomeVia { get; set; }
		public string CodViario { get; set; }

		public StradarioDto()
		{
		}

		public StradarioDto(Stradario stradario)
		{
			var toponimo = stradario.PREFISSO;
			var via = stradario.DESCRIZIONE;

			if (!String.IsNullOrEmpty(stradario.LOCFRAZ))
				via += " (" + stradario.LOCFRAZ + ")";

			this.CodiceStradario = Convert.ToInt32(stradario.CODICESTRADARIO);
			this.NomeVia = String.IsNullOrEmpty(toponimo) ? via : toponimo + " " + via;
			this.CodViario = stradario.CODVIARIO;
		}

	}
}
