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
	internal class WsDomandeEndoRepository : IDomandeEndoRepository
	{
		AreaRiservataServiceCreator _serviceCreator;

		public WsDomandeEndoRepository(AreaRiservataServiceCreator serviceCreator)
		{
			Condition.Requires(serviceCreator, "serviceCreator").IsNotNull();

			_serviceCreator = serviceCreator;
		}

		public InfoDomandaAreaTematica[] GetDomandeArea(string alias, int codiceArea)
		{
			using (var ws = _serviceCreator.CreateClient(alias))
				return ws.Service.GetDomandeArea(ws.Token, codiceArea);
		}

		public InfoAreaTematica[] GetAreeTematiche(string alias, string software, int areaPadre)
		{
			using (var ws = _serviceCreator.CreateClient(alias))
				return ws.Service.GetAreeTematiche(ws.Token, software, areaPadre);
		}

		public InfoEndo[] GetEndoDomanda(string alias, int idDomanda)
		{
			using (var ws = _serviceCreator.CreateClient(alias))
				return ws.Service.GetEndoDomanda(ws.Token, idDomanda);
		}

		public AreaIndividuazioneEndoDto[] GetStrutturaDomande(string alias, string software)
		{
			using (var ws = _serviceCreator.CreateClient(alias))
				return ws.Service.GetStrutturaDomande(ws.Token, software);
		}
	}
}
