using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.DTO.Procedure
{
	public class FaseAttuativaDto: BaseDto<int,string>
	{
		public string DescrizioneEstesa { get; set; }
		public string Note { get; set; }
	}
}
