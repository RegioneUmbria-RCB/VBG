using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.DTO.Endoprocedimenti
{
	public class TipoEndoprocedimentoDto: BaseDto<int,string>
	{
		public List<EndoprocedimentoDto> Endoprocedimenti { get; set; }

		public TipoEndoprocedimentoDto():base()
		{
			Endoprocedimenti = new List<EndoprocedimentoDto>();
		}
	}
}
