using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.Bari.Core.SharedDTOs;

namespace Init.Sigepro.FrontEnd.Bari.DenunceTARES.DTOs
{
	public class UtenzaDomesticaDenunciaTaresDto
	{
		public string Id { get; set; }
		public DataDto DataInizioUtenza { get; set; }
		public DataDto DataVariazioneUtenza { get; set; }

		public IndirizzoDenunciaTares Ubicazione { get; set; }
		public DatiCatastaliDenunciaTares DatiCatastali { get; set; }

		public string CategoriaCatastale { get; set; }

		public int Superficie { get; set; }

		public string RiduzioneTariffa { get; set; }

		public List<OccupanteImmobileDenunciaTares> OccupantiImmobile { get; set; }

		public string NumeroComponentiNumeroFamiliare { get; set; }
	}
}
