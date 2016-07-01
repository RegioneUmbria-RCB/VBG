using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.Bari.TARES.ServicesProxies;

namespace Init.Sigepro.FrontEnd.Bari.DenunceTARES.DTOs
{
	public class UtenzaCommercialeDenunciaTaresDto
	{
		public string Id { get; set; }

		public Core.SharedDTOs.DataDto DataVariazioneUtenza { get; set; }

		public Core.SharedDTOs.DataDto DataInizioUtenza { get; set; }

		public IndirizzoDenunciaTares Ubicazione { get; set; }

		public DatiCatastaliDenunciaTares DatiCatastali { get; set; }

		public bool AreaScoperta { get; set; }

		public int SuperficieTassabile { get; set; }

		public int? SuperficieTotale { get; set; }

		public int? SuperficieIntassabile { get; set; }

		public int? SuperficieRifiutiSpeciali { get; set; }

		public string CodiceAttivita { get; set; }

		public short? CategoriaUtenza { get; set; }

		public short RiduzioneSuperficie { get; set; }

		public short RiduzioneTariffa { get; set; }

		public riduzioneRaccoltaDifferenziataStoricoType? RiduzioneRaccoltaDifferenziata { get; set; }

        public UtenzaCommercialeDenunciaTaresDto()
        {
            this.SuperficieIntassabile = 0;
            this.SuperficieRifiutiSpeciali = 0;
            this.SuperficieTassabile = 0;
            this.SuperficieTotale = 0;
        }
	}
}
