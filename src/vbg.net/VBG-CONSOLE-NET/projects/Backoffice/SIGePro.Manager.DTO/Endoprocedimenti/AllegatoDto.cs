using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.DTO.Endoprocedimenti
{
	public class AllegatoDto: BaseDto<int,string>
	{
		public int? CodiceOggetto { get; set; }
		public string Link { get; set; }
		public string FormatiDownload { get; set; }
	}
}
