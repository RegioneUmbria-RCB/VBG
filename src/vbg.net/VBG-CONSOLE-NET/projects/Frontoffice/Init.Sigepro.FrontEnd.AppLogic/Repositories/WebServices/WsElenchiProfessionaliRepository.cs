using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using CuttingEdge.Conditions;

namespace Init.Sigepro.FrontEnd.AppLogic.Repositories.WebServices
{
	internal class WsElenchiProfessionaliRepository : IElenchiProfessionaliRepository
	{

		AreaRiservataServiceCreator _serviceCreator;

		public WsElenchiProfessionaliRepository(AreaRiservataServiceCreator serviceCreator)
		{
			Condition.Requires(serviceCreator, "serviceCreator").IsNotNull();

			_serviceCreator = serviceCreator;
		}

		public List<ElenchiProfessionaliBase> GetList(string aliasComune)
		{
			using (var ws = _serviceCreator.CreateClient(aliasComune))
			{
				return new List<ElenchiProfessionaliBase>(ws.Service.GetElenchiProfessionali(ws.Token));
			}
		}
	}
}
