using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.DTO.DatiDinamici
{
	public class SchedaDinamicaDto : BaseDto<int,string>
	{
		public TipoFirmaEnum TipoFirma { get; set; }
		public bool Facoltativa { get; set; }
		public int? Ordine { get; set; }

		public SchedaDinamicaDto():base()
		{
			TipoFirma = TipoFirmaEnum.NessunaFirma;
			Facoltativa = false;
		}

		public SchedaDinamicaDto(int codice, string descrizione, TipoFirmaEnum tipoFirma)
			: base()
		{
			TipoFirma = tipoFirma;
		}
	}
}
