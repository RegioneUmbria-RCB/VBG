using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using AutoMapper;
using Init.Sigepro.FrontEnd.Bari.TARES.DTOs;
using Init.Sigepro.FrontEnd.Bari.TARES.ServicesProxies;
using Init.Sigepro.FrontEnd.Bari.Core.SharedDTOs;

namespace Init.Sigepro.FrontEnd.Bari.TARES
{
	public class TaresAutomapperConfiguration
	{
		public static void Bootstrap()
		{
			Mapper.CreateMap<utenzeResponseType, UtenzeDto>();
			Mapper.CreateMap<datiContribuenteType, DatiContribuenteDto>();
			Mapper.CreateMap<datiAnagraficiType, DatiAnagraficiDto>();
			Mapper.CreateMap<datiIndirizzoType, DatiIndirizzoDto>();
			Mapper.CreateMap<datiUtenzaType, DatiUtenzaDto>().ForMember(dest => dest.TipoUtenza , opt => opt.Ignore());
			Mapper.CreateMap<datiCatastaliType, DatiCatastaliDto>();			

			Mapper.AssertConfigurationIsValid();
		}
	}
}
