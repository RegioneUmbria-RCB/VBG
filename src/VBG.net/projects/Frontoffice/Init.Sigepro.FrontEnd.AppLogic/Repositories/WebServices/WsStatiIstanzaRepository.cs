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
	internal class WsStatiIstanzaRepository : IStatiIstanzaRepository
	{
		AreaRiservataServiceCreator _serviceCreator;

		public WsStatiIstanzaRepository(AreaRiservataServiceCreator serviceCreator)
		{
			Condition.Requires(serviceCreator, "serviceCreator").IsNotNull();

			_serviceCreator = serviceCreator;
		}


		public StatiIstanza[] GetList(string aliasComune, string software)
		{
			using (var ws = _serviceCreator.CreateClient(aliasComune))
			{
				return ws.Service.GetStatiIstanza(ws.Token, software);
			}
		}

		public StatiIstanza GetById(string aliasComune, string software, string codiceStato)
		{
			using (var ws = _serviceCreator.CreateClient(aliasComune))
			{
				return ws.Service.GetStatoIstanza(ws.Token, software, codiceStato);
			}
		}
	}
}
