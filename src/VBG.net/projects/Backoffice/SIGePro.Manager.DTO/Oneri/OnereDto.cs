using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.DTO.Oneri
{
	public class OnereDto : CausaleOnereDto
	{
		public float Importo { get; set; }
		public string OrigineOnere { get; set; }
		public int CodiceInterventoOEndoOrigine { get; set; }
		public string InterventoOEndoOrigine { get; set; }
		public string Note { get; set; }

		public OnereDto()
		{

		}
	}
}
