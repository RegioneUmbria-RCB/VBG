using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.DTO.Endoprocedimenti
{
	public class TestiEstesiDto: BaseDto<int, string>
	{
		public string Normativa { get; set; }
		public int? CodiceOggetto { get; set; }
		public string Link { get; set; }
	}
}
