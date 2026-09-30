using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.DTO.Endoprocedimenti
{
	public class ListaEndoDaIdInterventoDto
	{
		public List<FamigliaEndoprocedimentoDto> EndoIntervento { get; set; }
		public List<FamigliaEndoprocedimentoDto> EndoFacoltativi { get; set; }
	}
}
