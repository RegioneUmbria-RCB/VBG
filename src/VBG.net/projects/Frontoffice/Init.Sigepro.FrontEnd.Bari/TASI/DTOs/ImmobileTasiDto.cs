using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.Bari.Core.SharedDTOs;
using System.Xml.Serialization;

namespace Init.Sigepro.FrontEnd.Bari.TASI.DTOs
{
	public class ImmobileTasiDto
	{
		public IndirizzoTasiDto Ubicazione{ get; set;}
		public DatiCatastaliTasiDto RiferimentiCatastali{ get; set;}
		public string DataInizioDecorrenza{ get; set;}
		public string PercentualePossesso{ get; set;}
		public string IdImmobile{ get; set;}

		[Obsolete("Utilizzare solo per la serializzazione")]
		public ImmobileTasiDto()
		{
		}

		public static ImmobileTasiDto Vuoto()
		{
			return new ImmobileTasiDto(IndirizzoTasiDto.Vuoto(), DatiCatastaliTasiDto.Vuoto(), String.Empty, String.Empty, String.Empty);
		}

		public ImmobileTasiDto(IndirizzoTasiDto ubicazione, DatiCatastaliTasiDto riferimentiCatastali, string dataInizioDecorrenza, string percentualePossesso, string idImmobile)
		{
			this.Ubicazione			 = ubicazione;
			this.RiferimentiCatastali= riferimentiCatastali;
			this.DataInizioDecorrenza= dataInizioDecorrenza;
			this.PercentualePossesso = percentualePossesso;
			this.IdImmobile = idImmobile;
		}

		[XmlIgnore]
		public string TipoImmobile
		{
			get {
				return this.RiferimentiCatastali.CategoriaCatastale.StartsWith("A") ? "Abitazione" : "Pertinenza";
			}
		}
	}
}
