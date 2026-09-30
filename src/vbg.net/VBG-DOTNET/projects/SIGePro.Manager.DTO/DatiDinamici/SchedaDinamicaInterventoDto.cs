using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;

namespace Init.SIGePro.Manager.DTO.DatiDinamici
{
	[DataContract]
	public class SchedaDinamicaInterventoDto : SchedaDinamicaDto
	{
		public int CodiceIntervento { get; set; }
	}
}
