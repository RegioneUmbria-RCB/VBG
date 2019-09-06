using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.Bari.Core.SharedDTOs;

namespace Init.Sigepro.FrontEnd.Bari.TARES.DTOs
{
	public class DatiUtenzaDto
	{
		public string DataInizioUtenza { get; set; }
		public string DataVariazioneUtenza { get; set; }
		public DatiCatastaliDto DatiCatastali { get; set; }
		public DatiIndirizzoDto DatiIndirizzo { get; set; }
		public string IdentificativoUtenza { get; set; }
		public ushort Superficie { get; set; }
		public TipoUtenzaTaresEnum TipoUtenza { get; set; }

		public static DatiUtenzaDto Vuoto()
		{
			return new DatiUtenzaDto
			{
				DataInizioUtenza = String.Empty,
				DataVariazioneUtenza = String.Empty,
				DatiCatastali = DatiCatastaliDto.Vuoto(),
				DatiIndirizzo = DatiIndirizzoDto.Vuoto(),
				IdentificativoUtenza = String.Empty,
				Superficie = 0
			};
		}
		
	}
}
