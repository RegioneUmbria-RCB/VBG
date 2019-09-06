using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.Bari.DenunceTARES.DTOs;
using Init.Sigepro.FrontEnd.Bari.TARES.ServicesProxies;
using Init.Sigepro.FrontEnd.Bari.DenunceTARES.ServiceProxies;
using Init.Sigepro.FrontEnd.Infrastructure.Mapping;

namespace Init.Sigepro.FrontEnd.Bari.DenunceTARES
{
	public class DenunceTaresService : IDenunceTaresService
	{
		IDenunceTaresServiceProxy _serviceProxy;
		IMapTo<datiContribuenteResponseType, DatiAnagraficiContribuenteDenunciaTares> _mapper;

		public DenunceTaresService(IDenunceTaresServiceProxy serviceProxy, IMapTo<datiContribuenteResponseType, DatiAnagraficiContribuenteDenunciaTares> mapper)
		{
			this._serviceProxy = serviceProxy;
			this._mapper = mapper;
		}


		public DatiAnagraficiContribuenteDenunciaTares GetUtenze(DatiOperatore operatore, DatiUtenza utenza)
		{
			var datiContribuente = this._serviceProxy.GetUtenze(operatore, utenza);

			return _mapper.Map(datiContribuente);
		}


		public bool IsContribuenteEsistente(DatiOperatore operatore, DatiUtenza utenza)
		{
			return this._serviceProxy.IsContribuenteEsistente(operatore, utenza);
		}
	}
}
