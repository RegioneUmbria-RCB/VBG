using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.DTO.Normative
{
	public class NormativaDto : BaseDto<int,string>
	{
		public string Categoria { get; set; }
		public int? CodiceOggetto { get; set; }
		public string Link { get; set; }
	}
}
