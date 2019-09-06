using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.DTO.Endoprocedimenti
{
	public class FamigliaEndoprocedimentoDto: BaseDto<int,string>
	{
		public List<TipoEndoprocedimentoDto> TipiEndoprocedimenti { get; set; }

		public FamigliaEndoprocedimentoDto():base()
		{
			TipiEndoprocedimenti = new List<TipoEndoprocedimentoDto>();
		}
	}
}
