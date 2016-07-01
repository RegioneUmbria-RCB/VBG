using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.DTO.DomndeIndividuazioneEndo
{
	public class AreaIndividuazioneEndoDto: BaseDto<int,string>
	{
		public string Note { get; set; }
		public List<DomandaIndividuazioneEndoDto> Domande { get; set; }
		public int IdPadre { get; set; }
		//public bool HaAreeFiglio { get; set; }
		//public bool HaDomande { get; set; }
		public List<AreaIndividuazioneEndoDto> SottoAree { get; set; }

		public AreaIndividuazioneEndoDto()
		{
			Domande = new List<DomandaIndividuazioneEndoDto>();
			SottoAree = new List<AreaIndividuazioneEndoDto>();
			IdPadre = -1;
		}
	}
}
